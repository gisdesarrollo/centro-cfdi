package com.gisconsultoria.centrocfdi.util;

import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.UploadContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.gisconsultoria.centrocfdi.model.CfdiPrincipal;
import com.gisconsultoria.centrocfdi.model.Clientes;
import com.gisconsultoria.centrocfdi.model.Pdf;
import com.gisconsultoria.centrocfdi.model.Receptor;
import com.gisconsultoria.centrocfdi.model.StatusWs;
import com.gisconsultoria.centrocfdi.model.Xml;
import com.gisconsultoria.centrocfdi.model.dto.ComprobanteXmlDto;
import com.gisconsultoria.centrocfdi.model.dto.TimbreFiscalDto;
import com.gisconsultoria.centrocfdi.service.ICfdiPrincipalService;
import com.gisconsultoria.centrocfdi.service.IClientesService;
import com.gisconsultoria.centrocfdi.service.IPdfService;
import com.gisconsultoria.centrocfdi.service.IReceptorService;
import com.gisconsultoria.centrocfdi.service.IStatusWsService;
import com.gisconsultoria.centrocfdi.service.IXmlService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class LogicaFacade implements ILogicaFacade{
	protected final Logger LOG = Logger.getLogger(LogicaFacade.class);
	
	@Autowired
	private IClientesService sucursalService;

	@Autowired
	private ICfdiPrincipalService cfdiPrincipalService;
	
	@Autowired
	private IXmlService xmlService;
	
	@Autowired
	private IPdfService pdfService;
	
	@Autowired
	private IReceptorService receptorService;
	
	@Autowired
	private IStatusWsService statusWsService;

	@Override
	public boolean checarUuidRepetidoBD(TimbreFiscalDto timbreFiscal, File file, String xml) throws ParseException {

		LOG.info("Verificando si existe el uuid: ".concat(timbreFiscal.getUuid()).concat(" en la base de datos"));

		File archivoXml = new File(file.getAbsolutePath() + File.separator + xml);
		String pdf = FilenameUtils.removeExtension(xml)+".pdf";
		File archivoPdf = new  File(file.getAbsolutePath()+File.separator+pdf); 
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD");
		Date fechaTimbrado = format.parse(timbreFiscal.getFechaTimbrado());
		calendar.setTime(fechaTimbrado);

		List<CfdiPrincipal> cfdiDoc = cfdiPrincipalService.findFirstCfdiByUuid(timbreFiscal.getUuid());

		if (cfdiDoc != null) {
			for (CfdiPrincipal cfdi : cfdiDoc) {
				if (cfdi != null) {
					LOG.error("El uuid ".concat(cfdi.getTfdUuid().concat(" ya se encuentra registrado en la BD")));
					
					  if (archivoXml.delete() && archivoPdf.delete()) {
					  LOG.info("Archivo repetido "+xml+" y "+pdf+", se eliminó correctamente");
					 
					 }
					  return false;
				}
			}
		}

		return true;
	}

	@Override
	public boolean checarRfcReceptor(ComprobanteXmlDto comprobante) throws Exception {

		Clientes empresa = sucursalService.getClienteByRfc(comprobante.getEmisor().getRfc());

		if (empresa == null) {
			throw new Exception("El RFC del emisor: ".concat(comprobante.getEmisor().getRfc())
					.concat(" no fue encontrado en la base de datos"));
		}
		
		return true;
	}

	@Override
    public boolean guardarComprobanteBd(ComprobanteXmlDto comprobante, File file, String xml, TimbreFiscalDto timbrefiscal) throws Exception {
    	
    	CfdiPrincipal cfdi = null;
    	FileInputStream input = null;
    	StatusWs statusWs = new StatusWs();
    	try {
    	//obtener la sucursal 
    	Clientes sucursal = sucursalService.getClienteByRfc(comprobante.getEmisor().getRfc());
         if (sucursal == null) {
             throw new Exception("No se encontró el RFC del emisor: "
                     .concat(comprobante.getEmisor().getRfc()));
         }
         
    	        
    	//formateo de fecha DOC
    	SimpleDateFormat parseFecha = new SimpleDateFormat("yyyy-MM-dd");
    	SimpleDateFormat parseHora = new SimpleDateFormat("HH:mm:ss");
        String fechaDoc = parseFecha.format(comprobante.getFecha());
        String horaDoc = parseHora.format(comprobante.getFecha());
        String comprobanteCfdi=null;
        if(comprobante.getMetodoPago()!=null) {
        	comprobanteCfdi=comprobante.getMetodoPago().toString();
        }
        
    	cfdi = new CfdiPrincipal(sucursal,comprobante.getVersion().toString(),comprobante.getSerie(),comprobante.getFolio(),comprobante.getFecha()
    			,horaDoc,comprobante.getSello(),comprobante.getFormaPago(),comprobante.getNoCertificado(),comprobante.getCertificado(),comprobante.getCondicionesPago()
    			,comprobante.getSubTotal(),comprobante .getDescuento(),comprobante.getMoneda().toString(),comprobante.getTipoCambio(),comprobante.getTotal()
    			,comprobante.getTipoComprobante().toString(),comprobanteCfdi,comprobante.getLugarExpedicion(),comprobante.getConfirmacion()
    			,true,timbrefiscal.getVersion(),timbrefiscal.getUuid(),timbrefiscal.getFechaTimbrado(),timbrefiscal.getTimeTimbre(),timbrefiscal.getSelloCfd(),timbrefiscal.getCertificadoSat(),timbrefiscal.getSelloSat());
    	
    	cfdiPrincipalService.save(cfdi);
    	//guardar Receptor
        Receptor receptor = new Receptor();
        	receptor.setIdCfdi(cfdi);
        	receptor.setNombre(comprobante.getReceptor().getNombre());
        	
        	receptor.setNumRegiDTrib(comprobante.getReceptor().getNumRegIdTrib());
        	if(comprobante.getReceptor().getResidenciaFiscal()!=null) {
        		receptor.setResidenciaFiscal(comprobante.getReceptor().getResidenciaFiscal().toString());
        	}
        	receptor.setRfc(comprobante.getReceptor().getRfc());
        	receptor.setUsoCfdi(comprobante.getReceptor().getUsoCfdi());
        	receptorService.save(receptor);
        if(!comprobante.getAddenda().getAvla().getReferencia().isEmpty()) {	
        	LOG.info("GUARDANDO ESTATUS DE "+cfdi.getEmpresaId().getRfc() +" PARA ENVIO DE WEB SERVICE CHILENOS");
			statusWs.setCfdi(cfdi);
			statusWs.setFolio(cfdi.getFolio());
			statusWs.setSerie(cfdi.getSerie());
			statusWs.setReferencia(comprobante.getAddenda().getAvla().getReferencia());
			statusWs.setStatus("ENPROCESO");
			statusWsService.save(statusWs);
        }
    	LOG.info("OBTENIENDO ARCHIVOS PARA GUARDAR");
    	uploadFilesBD(file, xml, cfdi);
    	
    	}catch (Exception ex) {
    		 LOG.error("Ocurrió un error al momento de guardar el documento", ex);
             throw new Exception("Occurió un error al momento de guardar el documento", ex);
		}
    	return true;
    }
	

	private void uploadFilesBD(File file, String xml, CfdiPrincipal cfdi) throws Exception {
		Xml archivosXml = new Xml();
		Pdf archivosPdf = new  Pdf();
		String pdf = FilenameUtils.removeExtension(xml)+".pdf";
		Date fechaCreacion = new Date();
		
		byte [] archivoXml= getByteXml(file, xml);
		byte [] archivoPdf = getBytePdf(file, pdf);
		if(archivoXml!=null) {
			archivosXml.setNameXml(xml);
			archivosXml.setXml(archivoXml);
			archivosXml.setCfdi(cfdi);
			archivosXml.setFecha(fechaCreacion);
			xmlService.saveXml(archivosXml);
		} 
		if(archivoPdf!=null) {
			archivosPdf.setNamePdf(pdf);
			archivosPdf.setPdf(archivoPdf);
			archivosPdf.setCfdi(cfdi);
			archivosPdf.setFecha(fechaCreacion);
			pdfService.savePdf(archivosPdf);

		}
		
		
		if(archivosXml.getId()!=null && archivosPdf.getId()!=null) {
			LOG.info("ARCHIVOS "+ xml +" y "+ pdf +" GUARDADO CORRECTAMENTE ");
			File pathXmlDelete = new  File(file.toString()+File.separator+xml);
			File pathPdfDelete = new  File(file.toString()+File.separator+pdf); 
			if (pathXmlDelete.delete()) {
				  LOG.info("Eliminando archivo "+pdf+ ", ya guardados en la BD");
			}
			else {
				LOG.error("Error al eliminar el archivo XML");
			}
			if(pathPdfDelete.delete()) {
				LOG.info("Eliminando archivo "+pdf+ ", ya guardados en la BD");
			}else {
				LOG.error("Error al eliminar el archivo PDF");
			}
		}
		
			
		
	}
	
	private byte[] getByteXml(File file, String xml) throws Exception {
		byte[] fileContentXml = null;
		File pathCliente = new  File(file.toString()+File.separator+xml);
		if(pathCliente.exists() && !pathCliente.isDirectory()) {
			try {
				fileContentXml = Files.readAllBytes(pathCliente.toPath());
			} catch (IOException e) {
				e.printStackTrace();
				LOG.error("Error al momento de parsear el archivo:" + xml);
			}
		}else {
			LOG.error("El archivo "+xml+" no existe ");
			throw new Exception("El archivo "+xml+" no existe");
			
		}
			
		return fileContentXml;
	}
	
	private byte[] getBytePdf(File file, String pdf) throws Exception {
		byte[] fileContentPdf = null;
		File pathCliente = new  File(file.toString()+File.separator+pdf); 
		if(pathCliente.exists() && !pathCliente.isDirectory()) {
		 try {
			fileContentPdf = Files.readAllBytes(pathCliente.toPath());
		} catch (IOException e) {
			e.printStackTrace();
			LOG.error("Error al momento de parsear el archivo:" + pdf);
		}
		}else {
			LOG.error("El archivo "+pdf+" no existe ");
			//throw new Exception("El archivo "+pdf+" no existe");
		}
		return fileContentPdf;
	}
 }
