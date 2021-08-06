package com.gisconsultoria.centrocfdi.sheduled;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.ssl.PKCS8Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.gisconsultoria.centrocfdi.model.Clientes;
import com.gisconsultoria.centrocfdi.model.Solicitudes;
import com.gisconsultoria.centrocfdi.service.IClientesService;
import com.gisconsultoria.centrocfdi.service.ISolicitudService;
import com.gisconsultoria.centrocfdi.services.GetCfdiSatService;
import com.gisconsultoria.centrocfdi.util.IDescargaMasivaSat;

@Configuration
@EnableScheduling
public class SatConnector {

	private static final Logger LOG = LoggerFactory.getLogger(SatConnector.class);

	@Value("${url.ws.sat.autenticacion}")
	private String urlWsAutenticacion;

	@Value("${url.soap.action.autenticacion}")
	private String soapActionAutenticacion;

	@Value("${url.ws.sat.solicitud}")
	private String urlWsSolicitud;

	@Value("${url.soap.action.solicitud}")
	private String soapActionSolicitud;

	@Value("${url.ws.sat.verificacion}")
	private String urlWsVerificacion;

	@Value("${url.soap.action.verificacion}")
	private String soapActionVerificacion;

	@Value("${url.ws.sat.descarga}")
	private String urlWsDescarga;

	@Value("${url.soap.action.descarga}")
	private String soapActionDescarga;

	@Autowired
	private IClientesService clienteService;

	@Autowired
	private IDescargaMasivaSat descargaMasivaSat;

	@Autowired
	private GetCfdiSatService cfdiSatService;

	@Autowired
	private ISolicitudService solicitudService;

	@Scheduled(cron = "05 43 15 * * *", zone = "America/Mexico_City")
	public void dowloadCfdiSat() throws CertificateEncodingException, IOException {

		byte[] cerByte = null;
		byte[] keyByte = null;
		String password = null;
		String rfcEmisor = null;
		String tipoSolicitud = "CFDI";
		String token = null;
		String idSolicitud = null;
		List<String> idPaquete = new ArrayList<String>();
		String zipString = null;
		int maxMinutos = 5;
		try {
			LOG.info("INICIANDO DESCARGAS MASIVA DE CFDIs POR SAT...");
			List<Clientes> clientes = clienteService.getActiveClientes();
			// File cerFile = new File("C:\\FIEL\\00001000000410170380.cer");
			// File keyFile = new
			// File("C:\\FIEL\\Claveprivada_FIEL_GGI0807079QA_20180403_112608.key");
			// password = "Quatro414";
			// rfcEmisor = "GGI0807079QA";
			for (Clientes cliente : clientes) {
				if (cliente.getServidor() != null) {
					cerByte = cliente.getCer();
					keyByte = cliente.getKey();
					password = cliente.getPasswordKey();
					rfcEmisor = cliente.getRfc();
					// cerByte = FileUtils.readFileToByteArray(cerFile);
					// keyByte = FileUtils.readFileToByteArray(keyFile);
					// get fecha inicial y final authenticacion
					String fechaInicialA = getFechaAuthenticacion(0);
					String fechaFinalA = getFechaAuthenticacion(maxMinutos);
					// randomUUID
					UUID uuid = UUID.randomUUID();
					// Authenticacion
					String messageAuthenticacion = descargaMasivaSat.getAuthenticacion(uuid, fechaInicialA, fechaFinalA,
							cerByte, keyByte, password);
					token = cfdiSatService.connectionSat(urlWsAutenticacion, soapActionAutenticacion,
							messageAuthenticacion, null, "authenticacion");
					if (token != null) {
						// Solicitud
						String fechaInicialS = getFechaSolicitud(cliente.getFechaInicialSat(), true);
						String fechaFinalS = getFechaSolicitud(new Date(), false);

						String messageSolicitud = descargaMasivaSat.getSolicitud(fechaInicialS, fechaFinalS, rfcEmisor,
								tipoSolicitud, cerByte, keyByte, password);
						idSolicitud = cfdiSatService.connectionSat(urlWsSolicitud, soapActionSolicitud,
								messageSolicitud, token, "solicitud");
						if (idSolicitud != null) {
							// Verifcación
							String messageVerificacion = descargaMasivaSat.getverificacion(rfcEmisor, idSolicitud,
									cerByte, keyByte, password);
							idPaquete = cfdiSatService.connectSatWSVerificacion(urlWsVerificacion,
									soapActionVerificacion, messageVerificacion, token, "verificacion", idSolicitud,
									rfcEmisor);
							if (!idPaquete.isEmpty()) {
								// Descarga
								if (idPaquete.size() > 0) {
									for (String paquete : idPaquete) {
										String messageDescarga = descargaMasivaSat.getDescarga(rfcEmisor, paquete,
												cerByte, keyByte, password);
										zipString = cfdiSatService.connectionSat(urlWsDescarga, soapActionDescarga,
												messageDescarga, token, "descarga");
										if (zipString != null) {
											descargaMasivaSat.decodeZip(zipString, rfcEmisor);
										} else {
											LOG.error("No se encontraron CFDIs para descargar");
										}
									}
								}
							} else {
								LOG.error("Error idPaquete null");
							}
						} else {
							LOG.error("Error idSolicitud null");
						}

					} else {
						LOG.error("Error Error token null");
					}
				}
			}
			LOG.info("BUSCANDO SOLICITUDES PENDIENTES PARA DESCARGA");
			List<Solicitudes> solicitudes = solicitudService.getSolicitudesWithEstatusEnProceso();
			for (Solicitudes solicitud : solicitudes) {
				dowloadCfdiByIdSolicitud(solicitud);
			}
			LOG.info("DESCARGAS CFDIs SAT FINALIZADO CORRECTAMENTE...");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Error al iniciar las descargas Masivas CFDIs SAT");
		}
	}

	public String getFechaAuthenticacion(int maxMinutos) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.000Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		if (maxMinutos > 0) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + maxMinutos);
			return sdf.format(cal.getTime());
		}

		return sdf.format(new Date());
	}

	public String getFechaSolicitud(Date fecha, boolean fechaInicial) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		if (fechaInicial) {
			cal.setTime(fecha);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			// cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 30);
			return sdf.format(cal.getTime());

		}
		return sdf.format(fecha);
	}

	public void dowloadCfdiByIdSolicitud(Solicitudes solicitud) {
		byte[] cerByte = null;
		byte[] keyByte = null;
		String password = null;
		String rfcEmisor = null;
		int maxMinutos = 5;
		String token = null;
		String zipString = null;
		List<String> idPaquete = new ArrayList<String>();
		try {
		if (solicitud.getIdSolicitud() != null) {
			String fechaInicialA = getFechaAuthenticacion(0);
			String fechaFinalA = getFechaAuthenticacion(maxMinutos);
			// randomUUID
			UUID uuid = UUID.randomUUID();
			Clientes cliente = clienteService.getClienteByRfc(solicitud.getRfcCliente());
				cerByte = cliente.getCer();
				keyByte = cliente.getKey();
				password = cliente.getPasswordKey();
				rfcEmisor = cliente.getRfc();

					// Authenticacion
					String messageAuthenticacion = descargaMasivaSat.getAuthenticacion(uuid, fechaInicialA, fechaFinalA,
							cerByte, keyByte, password);
					token = cfdiSatService.connectionSat(urlWsAutenticacion, soapActionAutenticacion,
							messageAuthenticacion, null, "authenticacion");
					if (token != null) {
						// Verifcación
						String messageVerificacion = descargaMasivaSat.getverificacion(rfcEmisor, solicitud.getIdSolicitud(), cerByte,
								keyByte, password);
						idPaquete = cfdiSatService.connectSatWSVerificacion(urlWsVerificacion, soapActionVerificacion,
								messageVerificacion, token, "verificacion", solicitud.getIdSolicitud(), rfcEmisor);
						if (!idPaquete.isEmpty()) {
							// Descarga
							if (idPaquete.size() > 0) {
								for (String paquete : idPaquete) {
									String messageDescarga = descargaMasivaSat.getDescarga(rfcEmisor, paquete,
											cerByte, keyByte, password);
									zipString = cfdiSatService.connectionSat(urlWsDescarga, soapActionDescarga,
											messageDescarga, token, "descarga");
									if (zipString != null) {
										descargaMasivaSat.decodeZip(zipString, rfcEmisor);
									} else {
										LOG.error("No se encontraron CFDIs para descargar");
									}
								}
								solicitudService.deleteById(solicitud.getId());
							}
						} else {
							LOG.error("Error idPaquete null");
						}
					}else {
						LOG.error("No se pudo obtener un token, token null");
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
				LOG.error("Error en tiempo de solicitudes pendientes de descarga..");
			}
		
	}
}
