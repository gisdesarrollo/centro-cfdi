package com.gisconsultoria.centrocfdi.sheduled;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import javax.xml.bind.DatatypeConverter;

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

import com.gisconsultoria.centrocfdi.model.Clientes;
import com.gisconsultoria.centrocfdi.service.IClientesService;
import com.gisconsultoria.centrocfdi.services.GetCfdiSatService;
import com.gisconsultoria.centrocfdi.util.IDescargaMasivaSat;
import com.gisconsultoria.centrocfdi.util.IMessagesSat;

//@Configuration
//@EnableScheduling
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

	//@Scheduled(cron = "0 0 0 * * *", zone = "America/Mexico_City")
	public void dowloadCfdiSat() throws CertificateEncodingException, IOException {

		byte[] cerByte = null;
		byte[] keyByte = null;
		String password = null;
		String rfcEmisor = null;
		String tipoSolicitud = "Metadata";//Metadata
		String token = null;
		String idSolicitud=null;
		String idPaquete=null;
		String zipString = null;
		int maxMinutos = 5;
		try {
			List<Clientes> clientes = clienteService.getActiveEmpresas();
			File cerFile = new File("C:\\FIEL\\00001000000410170380.cer");
			File keyFile = new File("C:\\FIEL\\Claveprivada_FIEL_GGI0807079QA_20180403_112608.key");
			password = "Quatro414";
			rfcEmisor = "GGI0807079QA";
			/*for (Clientes cliente : clientes) {*/
				/*if (cliente.getServidor() != null) {*/
					//cerByte = cliente.getCer();
					//keyByte = cliente.getKey();
					//password = cliente.getPasswordKey();
					//rfcEmisor = cliente.getRfc();
					cerByte = FileUtils.readFileToByteArray(cerFile);
					keyByte = FileUtils.readFileToByteArray(keyFile);
					// get fecha inicial y final authenticacion
					String fechaInicialA = getFechaAuthenticacion(0);
					String fechaFinalA = getFechaAuthenticacion(maxMinutos);
					// randomUUID
					UUID uuid = UUID.randomUUID();
					// Authenticacion
					String messageAuthenticacion = descargaMasivaSat.getAuthenticacion(uuid, fechaInicialA, fechaFinalA, cerByte,
							keyByte, password);
					token = cfdiSatService.connectionSat(urlWsAutenticacion, soapActionAutenticacion, messageAuthenticacion,null,"authenticacion");
					if(token==null) {
						throw new Exception("Error token null");
					}
					// Solicitud
					
					//String fechaInicialS = getFechaSolicitud(cliente.getFechaInicial(),false);
					String fechaInicialS = getFechaSolicitud(new Date(), true);
					String fechaFinalS = getFechaSolicitud(new Date(),false);
					
					String messageSolicitud = descargaMasivaSat.getSolicitud(fechaInicialS, fechaFinalS, rfcEmisor, tipoSolicitud, cerByte,keyByte,password);
					idSolicitud = cfdiSatService.connectionSat(urlWsSolicitud, soapActionSolicitud, messageSolicitud,token,"solicitud");
					if(idSolicitud==null) {
						throw new Exception("Error idSolicitud null");
					}
					//idSolicitud="25eb224d-5202-4814-96eb-54b059462450";
					// Verifcación
					String messageVerificacion = descargaMasivaSat.getverificacion(rfcEmisor,idSolicitud, cerByte, keyByte, password);
					idPaquete = cfdiSatService.connectionSat(urlWsVerificacion, soapActionVerificacion, messageVerificacion, token, "verificacion");
					if(idPaquete==null) {
						throw new Exception("Error idPaquete null");
					}
					// Descarga
					//idPaquete="61DA1590-5789-40CE-B4C2-3E80DE25981B_01";
					String messageDescarga = descargaMasivaSat.getDescarga(rfcEmisor, idPaquete, cerByte, keyByte, password);
					zipString = cfdiSatService.connectionSat(urlWsDescarga, soapActionDescarga, messageDescarga, token, "descarga"); 
					if(zipString==null) {
						throw new Exception("Error no se encontraron CFDIs para descargar");
					}
					
				/*}

			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getFechaAuthenticacion(int maxMinutos) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.000Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		if(maxMinutos > 0) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + maxMinutos);
			return sdf.format(cal.getTime());
		}
		
		return sdf.format(new Date());
	}
	
	public String getFechaSolicitud(Date fecha,boolean fechaInicial) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		if(fechaInicial) {
			cal.setTime(fecha);
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 18);
			System.out.println("fechaInicial: "+sdf.format(cal.getTime()));
			return sdf.format(cal.getTime());
			
		}
		System.out.println("fechaFinal: "+sdf.format(fecha));
		return sdf.format(fecha);
	}

	public String geFechaInicial() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 20);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		//sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		System.out.println("menos 20 dias: "+sdf.format(cal.getTime()));
		return sdf.format(cal.getTime());
	}

}
