package com.gisconsultoria.centrocfdi.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gisconsultoria.centrocfdi.model.CfdiRecibidos;
import com.gisconsultoria.centrocfdi.model.Clientes;
import com.gisconsultoria.centrocfdi.model.Parametros;
import com.gisconsultoria.centrocfdi.service.ICfdiRecibidosService;
import com.gisconsultoria.centrocfdi.service.IClientesService;
import com.gisconsultoria.centrocfdi.service.IParametrosService;

@Service
public class GetCfdiRecibidos {

	private static final Logger LOG = LoggerFactory.getLogger(GetCfdiRecibidos.class);


	@Autowired
	private ICfdiRecibidosService cfdiRecibidosService;

	@Autowired
	private IClientesService clientesService;

	@Autowired
	private IParametrosService parametroService;

	public void getcfdiRecibidos() {
		
		try {
			LOG.info("OBTENIENDO CFDI RECIBIDOS");
			List<Parametros> parametros = new ArrayList<Parametros>();
			String urlParams=null;
			String tokenParams=null;
			List<Clientes> clientes = clientesService.getActiveClientes();
			for(Clientes cliente : clientes) {
				parametros = parametroService.getParametrosByIdCliente(cliente.getId());
				if(parametros.size()>0) {
					for(Parametros params : parametros) {
						if(params.getReferencia().equals("cfdi_recibidos")) {
							if(params.getNombreParametro().equals("url")) {
								urlParams =params.getValor();
							}
							if(params.getNombreParametro().equals("token")) {
								tokenParams= params.getValor();
							}
						}
				
					}
					
				
				// conexion URL web service
				URL urlApi = new URL("https://".concat(urlParams).concat(":9050").concat("/")
						.concat(tokenParams).concat("/").concat(cliente.getRfc()).concat("/cfdisRecibidos?")
						.concat("fechaInicial=2019-03-21&").concat("fechaFinal=2019-03-22"));
				System.out.println(urlApi.toString());
				HttpsURLConnection conexion = (HttpsURLConnection) urlApi.openConnection();
				conexion.setRequestProperty("Accept", "UTF-8");
				conexion.setRequestMethod("GET");
				if (conexion.getResponseCode() != 200) {
					LOG.error("Failed : Http Error code : " + conexion.getResponseCode());
				}

				// Almacenamos la respuesta
				InputStreamReader in = new InputStreamReader(conexion.getInputStream());
				// lectura de la respuesta
				BufferedReader br = new BufferedReader(in);
				String output;
				List<String> listaCfdi = new ArrayList<String>();
				while ((output = br.readLine()) != null) {
					System.out.println(output);
					if (!output.isEmpty()) {
						listaCfdi.add(output);
					}
				}
				conexion.disconnect();
				guardarCfdiRecibidos(listaCfdi, cliente);
			}
			}
		}catch(MalformedURLException e)
	{
		LOG.error("ERROR EN LA RUTA DE LA URL");
		e.printStackTrace();
	}catch(
	Exception e)
	{
			LOG.error("ERROR A REALIZAR LA CONSULTA");
			e.printStackTrace();
		}
	}

	public void guardarCfdiRecibidos(List<String> output, Clientes sucursal) {

		CfdiRecibidos cfdiRecibidos = new CfdiRecibidos();
		try {
			LOG.info("GUARDANDO DATOS DE CFDI RECIBIDOS EN LA BD");
			String[] datos = null;
			// CfdiRecibidos cfdiRecibidos = new CfdiRecibidos();
			DateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			System.out.println(output.size());
			for (String cfdi : output) {
				cfdiRecibidos = new CfdiRecibidos();
				datos = cfdi.split("\\|");
				if (datos.length != 1 && datos.length == 32 && sucursal.getNombre().equals("Probiomed")) {
					if (!datos[0].isEmpty()) {
						cfdiRecibidos.setFolio(datos[0]);
					}
					cfdiRecibidos.setUuid(datos[1]);
					cfdiRecibidos.setEstatusF(datos[2]);
					cfdiRecibidos.setEstatusC(datos[3]);
					System.out.println(datos[4]);
					cfdiRecibidos.setRfcEmisor(datos[4]);
					cfdiRecibidos.setFechaEmision(fecha.parse(datos[5]));
					cfdiRecibidos.setFechaCreacion(fecha.parse(datos[6]));
					cfdiRecibidos.setSubtotal(datos[7]);
					cfdiRecibidos.setTotal(Double.parseDouble(datos[8]));
					cfdiRecibidos.setRfcReceptor(datos[9]);
					cfdiRecibidos.setRazonSocial(datos[10]);
					cfdiRecibidos.setMoneda(datos[11]);
					cfdiRecibidos.setDivision(datos[12]);
					if (!datos[13].isEmpty()) {
						cfdiRecibidos.setTotalImpTrasl(datos[13]);
					}
					cfdiRecibidos.setTipoComprobante(datos[14]);
					cfdiRecibidos.setCertificado(datos[15]);
					cfdiRecibidos.setFormaPago(datos[16]);
					cfdiRecibidos.setVersion(datos[17]);
					if (!datos[18].isEmpty()) {
						cfdiRecibidos.setMetodoPago(datos[18]);
					}
					if (!datos[19].isEmpty()) {
						cfdiRecibidos.setTotalImpRet(datos[19]);
					}
					cfdiRecibidos.setRegimenfiscal(datos[20]);
					cfdiRecibidos.setExpedicion(datos[21]);
					if (!datos[22].isEmpty()) {
						cfdiRecibidos.setITIva(datos[22]);
					}
					cfdiRecibidos.setITEps(datos[23]);
					if (!datos[24].isEmpty()) {
						cfdiRecibidos.setTIvaTraslado(datos[24]);
					}
					if (!datos[25].isEmpty()) {
						cfdiRecibidos.setIRIva(datos[25]);
					}
					cfdiRecibidos.setIRIsr(datos[26]);
					if (!datos[27].isEmpty()) {
						cfdiRecibidos.setTipoCambio(datos[27]);
					}
					cfdiRecibidos.setWarning(datos[28]);
					if (!datos[29].isEmpty()) {
						cfdiRecibidos.setTipoRelacion(datos[29]);
					}
					if (!datos[30].isEmpty()) {
						cfdiRecibidos.setListaNegra(datos[30]);
					}
					cfdiRecibidos.setCFDIRel(datos[31]);

					cfdiRecibidosService.save(cfdiRecibidos);
					LOG.info("CFDI RECIBIDO FOLIO" + cfdiRecibidos.getFolio() + " GUARDADO CORRECTAMENTE");
					cfdiRecibidos = null;
				}
				if (datos.length != 1 && datos.length == 14 && sucursal.getNombre().equals("Tecpetrol")) {
					cfdiRecibidos.setFolio(datos[0]);
					cfdiRecibidos.setUuid(datos[1]);
					cfdiRecibidos.setEstatusF(datos[2]);
					cfdiRecibidos.setEstatusC(datos[3]);
					cfdiRecibidos.setRfcEmisor(datos[4]);
					cfdiRecibidos.setFechaEmision(fecha.parse(datos[5]));
					cfdiRecibidos.setFechaCreacion(fecha.parse(datos[6]));
					cfdiRecibidos.setTotal(Double.parseDouble(datos[7]));
					cfdiRecibidos.setRfcReceptor(datos[8]);
					cfdiRecibidos.setRazonSocial(datos[9]);
					cfdiRecibidos.setMoneda(datos[10]);
					cfdiRecibidos.setVersion(datos[11]);
					cfdiRecibidos.setTipoCambio(datos[12]);
					cfdiRecibidos.setTipoRelacion(datos[13]);

					cfdiRecibidosService.save(cfdiRecibidos);
					LOG.info("CFDI RECIBIDO FOLIO" + cfdiRecibidos.getFolio() + " GUARDADO CORRECTAMENTE");
					cfdiRecibidos = null;
				}
			}
		} catch (Exception e) {
			LOG.error("ERROR AL MOMENTO DE GUARDAR EN LA BD CON FOLIO " + cfdiRecibidos.getFolio());
			e.printStackTrace();
		}
	}

}
