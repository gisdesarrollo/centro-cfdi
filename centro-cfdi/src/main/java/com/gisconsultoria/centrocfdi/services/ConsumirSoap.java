package com.gisconsultoria.centrocfdi.services;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.gisconsultoria.centrocfdi.model.CfdiPrincipal;
import com.gisconsultoria.centrocfdi.model.Clientes;
import com.gisconsultoria.centrocfdi.model.Receptor;
import com.gisconsultoria.centrocfdi.model.StatusWs;
import com.gisconsultoria.centrocfdi.service.IClientesService;
import com.gisconsultoria.centrocfdi.service.IReceptorService;
import com.gisconsultoria.centrocfdi.service.IStatusWsService;
import com.gisconsultoria.centrocfdi.sheduled.XsaConnector;
import com.gisconsultoria.centrocfdi.util.IConsumirSoap;

@Service
public class ConsumirSoap implements IConsumirSoap {

	protected final Logger LOG = Logger.getLogger(ConsumirSoap.class);
	
	@Value("${soap.endpoint.url}")
	private String endpoint;

	@Value("${soap.action.url}")
	private String action;

	@Value("${soap.namespace.url}")
	private String namespaceURL;

	@Value("${soap.namespace}")
	private String namespace;
	
	@Autowired
	private IReceptorService receptorService;
	
	@Autowired
	private IClientesService clienteService;

	@Autowired
	private IStatusWsService statusWsService;

	@Override
	public void connectionSoapWs(CfdiPrincipal cfdi) {

		callSoapWebService(endpoint, action, cfdi, namespaceURL, namespace);
	}

	public void callSoapWebService(String soapEndpointUrl, String soapAction, CfdiPrincipal cfdi, String namespaceUrl,
			String Mynamespace) {
		try {
			StatusWs statusData = statusWsService.findStatusByIdCfdi(cfdi.getId());
			LOG.info("Entramos a la conexion soapWs");
			// conexion soapWS
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();
			// Request soapWS
			SOAPMessage soapResponse = soapConnection.call(SOAPRequest(soapAction, cfdi, namespaceUrl, Mynamespace,statusData),
					soapEndpointUrl);

			// Response soapWS
			SOAPResponse(soapResponse, cfdi,statusData);

			soapConnection.close();

		} catch (Exception e) {
			LOG.error("Error al enviar SOAP Request to Server");
			e.printStackTrace();
		}
	}

	public SOAPMessage SOAPRequest(String soapAction, CfdiPrincipal cfdi, String namespaceUrl, String Mynamespace,StatusWs statusData)
			throws Exception {
		LOG.info("Creando SOAP message Request ");
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();

		Double subtotalCfdi = (double) 0;
		Double iva = (double) 0;
		Double tipoCambio = (double) 1;
		String dPEmpId=null;
		String dDlcNumDocto=null;
		String dDlcFolioDocto=null;
		//get rfcReceptor
		Receptor receptor=receptorService.findReceptorByIdCfdi(cfdi.getId());
		//get rfcEmisor
		Clientes  cliente = clienteService.findById(cfdi.getEmpresaId().getId());
		
		
		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration(Mynamespace, namespaceUrl);
		// parsear fechas
		SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss.SSS");
		String fecha = parseador.format(cfdi.getFecha());
		//String fechaTimbrado = parseador.format(cfdi.getFechaTimbrado());
		String status = "ENVIADO";
		String[] parametro = null;
		boolean resultado = validaTipodocumento(cfdi);
		//split condiciones de pago para obtener (1)pEmpId y (2)DlcNumDocto
		
		if(statusData.getReferencia().contains("-")) {
			parametro = statusData.getReferencia().split("-");
				dPEmpId = parametro[0];
				dDlcNumDocto = parametro[1];
				if(resultado) {
					dDlcNumDocto = "0";
					dDlcFolioDocto = parametro[1];
				}else {
					dDlcFolioDocto = cfdi.getFolio();
				}
		}
		
		if (!resultado) {
			subtotalCfdi = cfdi.getSubtotal();
			//iva = cfdi.getTotalImpuestoTrasladado();
			tipoCambio = cfdi.getTipoCambio();
			//dDlcFolioDocto = cfdi.getFolio();
		}
		if(dDlcNumDocto==null && dPEmpId==null) {
			LOG.error("Error DlcNumDocto y dPEmpId no pueden ser null");
			throw new Exception("Error DlcNumDocto y dPEmpId no pueden ser null");
		}
		// SOAP Body
		SOAPBody soapBody = envelope.getBody();
		SOAPElement wmIngresaFolioFiscal = soapBody.addChildElement("wmIngresaFolioFiscal", Mynamespace);
		SOAPElement ListaFolios = wmIngresaFolioFiscal.addChildElement("ListaFolios", Mynamespace);
		SOAPElement Documentos = ListaFolios.addChildElement("Documentos", Mynamespace);
		SOAPElement Documento = Documentos.addChildElement("Documento", Mynamespace);
		SOAPElement pEmpId = Documento.addChildElement("pEmpId", Mynamespace).addTextNode(dPEmpId);//41
		SOAPElement dlcNumDocto = Documento.addChildElement("DlcNumDocto", Mynamespace).addTextNode(dDlcNumDocto);//784
		SOAPElement serie = Documento.addChildElement("FFisSerie03", Mynamespace).addTextNode(cfdi.getSerie());
		SOAPElement folio = Documento.addChildElement("DlcFolioDocto", Mynamespace).addTextNode(dDlcFolioDocto);
		SOAPElement fechaemision = Documento.addChildElement("FFisFechaEmision", Mynamespace).addTextNode(fecha);
		SOAPElement rfcReceptor = Documento.addChildElement("FFisReceptorRFC40", Mynamespace)
				.addTextNode(receptor.getRfc());
		SOAPElement rfcEmisor = Documento.addChildElement("FFisEmisorRFC", Mynamespace)
				.addTextNode(cliente.getRfc());
		SOAPElement uuid = Documento.addChildElement("FFisUUID", Mynamespace).addTextNode(cfdi.getTfdUuid());
		SOAPElement estadoCFDI = Documento.addChildElement("FFisEstadoCFDI", Mynamespace).addTextNode(status);
		/*SOAPElement fechaTimbradoCfdi = Documento.addChildElement("FFisFechaTimbrado", Mynamespace)
				.addTextNode(fechaTimbrado);
		SOAPElement monto = Documento.addChildElement("FFisMonto", Mynamespace).addTextNode(cfdi.getTotal().toString());
		SOAPElement subtotal = Documento.addChildElement("FFisSubTotal", Mynamespace)
				.addTextNode(subtotalCfdi.toString());
		SOAPElement total = Documento.addChildElement("FFisIVA", Mynamespace).addTextNode(iva.toString());
		SOAPElement FisTipoCambio = Documento.addChildElement("FFisTipoCambio", Mynamespace)
				.addTextNode(tipoCambio.toString());*/

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", soapAction);
		soapMessage.saveChanges();

		// Check the input
		/*
		 * LOG.info("Request SOAP Message = "); soapMessage.writeTo(System.out);
		 * System.out.println();
		 */

		return soapMessage;
	}

	private void SOAPResponse(SOAPMessage soapResponse, CfdiPrincipal cfdi,StatusWs statusData) {

		try {
			Source sourceContent = soapResponse.getSOAPPart().getContent();

			if (soapResponse.getSOAPBody().hasFault()) {
				SOAPFault fault = soapResponse.getSOAPBody().getFault();
				LOG.error("Error SOAP Cliente:" + fault.getFaultString());
			} else {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				soapResponse.writeTo(out);
				String outputString = new String(out.toByteArray());
				Document document = parseXml(outputString);

				NodeList nodeLst = document.getElementsByTagName("GlosaObservacion");
				NodeList nodoEstadoProceso = document.getElementsByTagName("EstadoProceso");
				int status = Integer.parseInt(nodoEstadoProceso.item(0).getTextContent());
				//StatusWs statusWs = new StatusWs();
				//statusWs.setCfdi(cfdi);
				//statusWs.setFolio(cfdi.getFolio());
				//statusWs.setSerie(cfdi.getSerie());
			
				for (int x = 0; x < nodeLst.getLength(); x++) {
					String elementValue = nodeLst.item(x).getTextContent();
					if (status == 1) {
						LOG.info("Response SOAP Message:" + elementValue);
						LOG.info("CFDI enviado correctamente con folio :" + cfdi.getFolio() +" y serie:"+cfdi.getSerie());			
						statusData.setStatus("ENVIADO");
					}
					else if (status == 2) {
						LOG.info("Response SOAP Message:" + elementValue);
						statusData.setStatus("ERROR");
					}else {
						LOG.info("Response SOAP Message:" + elementValue);
						statusData.setStatus("ERROR");
					}
				}
				statusWsService.save(statusData);
			}
		} catch (SOAPException | IOException e) {
			LOG.error("Error al momento de enviar en Request SOAP WS .." + e);
			e.printStackTrace();
		}

	}

	public Document parseXml(String in) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(in));
			return db.parse(is);
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean validaTipodocumento(CfdiPrincipal cfdi) {

		Boolean status = false;
		String serie = cfdi.getSerie().toLowerCase();
		if (serie.equals("cp") || serie.equals("CP")) {
			status = true;
		}

		return status;
	}
}
