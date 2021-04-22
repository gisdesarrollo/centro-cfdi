package com.gisconsultoria.centrocfdi.controller.rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gisconsultoria.centrocfdi.model.Pdf;
import com.gisconsultoria.centrocfdi.model.Xml;
import com.gisconsultoria.centrocfdi.service.IClientesService;
import com.gisconsultoria.centrocfdi.service.IPdfService;
import com.gisconsultoria.centrocfdi.service.IXmlService;


@RestController
@RequestMapping("/api")
public class DownloadFilesRestController {

	private static final Log LOG = LogFactory.getLog(DownloadFilesRestController.class);

	@Autowired
	private IXmlService xmlService;

	@Autowired
	private IPdfService pdfService;

	@Autowired
	private IClientesService clienteService;
	
	@Autowired
	ServletContext context;

	//@Secured({ "ROLE_ADMIN", "ROLE_USUARIO" })
	@RequestMapping(value="/downloadXml/{id}" , method = RequestMethod.GET,  produces = "application/xml")
	public ResponseEntity<?> downloadXml(@PathVariable Long id) {

		HttpHeaders headers = new HttpHeaders();
		Xml xml = new Xml();
		//ByteArrayResource resource = null;
		InputStreamResource resource = null;
		Map<String, Object> response = new HashMap<>();
		try {
			xml = xmlService.getFileByIdCfdi(id);
			if (xml != null) {
				InputStream in= new ByteArrayInputStream(xml.getXml());
				resource =  new InputStreamResource(in);
				headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + xml.getNameXml());
				headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
				LOG.info("Archivo descargado: " + xml.getNameXml());

			} else {
				LOG.error("Error el archivo no existe");
				response.put("mensaje", "Error el archivo no existe!");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Error al momento de ejecución");
		}
		return ResponseEntity.ok().headers(headers).contentLength(xml.getXml().length)
				.contentType(MediaType.parseMediaType("application/xml")).body(resource);
	}
	
	
	
	@RequestMapping(value = "/downloadPdf/{id}", method = RequestMethod.GET, produces = "application/pdf")
	 public ResponseEntity<?> download(@PathVariable("id") Long id) throws IOException {
	 
	  Pdf pdf = new Pdf();
	  Map<String, Object> response = new HashMap<>();
	  HttpHeaders headers = new HttpHeaders();
	  InputStreamResource resource = null;
	  try {
		  pdf = pdfService.getFileByIdCfdi(id);
		  if (pdf != null) {
			  InputStream in= new ByteArrayInputStream(pdf.getPdf());
			  resource =  new InputStreamResource(in);
			  headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+pdf.getNamePdf());
			  headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
		  }else {
			  LOG.error("Error el archivo no existe");
				response.put("mensaje", "Error el archivo no existe!");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		  }
	  }catch (Exception e) {
		LOG.info("Error al momento de ejecución:" +e);
	  }
	  LOG.info("Archivo descargado: " + pdf.getNamePdf());
	  
	  return ResponseEntity.ok().headers(headers)
              .contentType(MediaType.parseMediaType("application/pdf"))
              .contentLength(pdf.getPdf().length) //
              .body(resource);
		
	 }

	
	
	@RequestMapping(value="/downloadZip",method = RequestMethod.GET, produces = "application/zip")
	public ResponseEntity<?> downloadZip(@RequestParam(value="inicial",required = false) String fechaInicial,
										@RequestParam(value="final" ,required=false)String fechaFinal ,
										@RequestParam(value="clienteId", required=false) String clienteId,
										@RequestParam(value="tipoComp", required = false)String tipoComprobante) {
		List<Pdf> pdf = new ArrayList<>();
		List<Xml> xml = new ArrayList<>();
		//ByteArrayResource resource = null;
		InputStreamResource resource = null;
		HttpHeaders headers = new HttpHeaders();
		byte[] zipFiles = null;
		Date fecha = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if(!fechaInicial.contains("undefined") || !fechaFinal.contains("undefined")) {
				 if(tipoComprobante.contains("undefined")) {
					 tipoComprobante="";
				 } if(clienteId.contains("undefined")) {
					 clienteId="";
				 }
				 pdf = pdfService.getAllFilePdfByParameter(fechaInicial, fechaFinal, tipoComprobante, clienteId);
				 xml = xmlService.getAllFilePdfByParameter(fechaInicial, fechaFinal, tipoComprobante, clienteId);
			}else {
				pdf = pdfService.getAllFilePdf();
				xml = xmlService.getAllFileXml();
			}
			String fechaformat = format.format(fecha);
			zipFiles = compressZip(pdf, xml);
			InputStream in= new ByteArrayInputStream(zipFiles);
			resource =  new InputStreamResource(in);
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fechaformat + ".zip");
			headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

			LOG.info("Archivo zip creado: " + fechaformat);
			LOG.info("compress successful!");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Error al momento de ejecucion");
		}
		return ResponseEntity.ok().headers(headers).contentLength(zipFiles.length)
				.contentType(MediaType.parseMediaType("application/zip")).body(resource);

	}

	public byte[] compressZip(List<Pdf> dataPdf, List<Xml> dataXml) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try {
			ZipOutputStream zous = new ZipOutputStream(outputStream);

			for (Pdf pdf : dataPdf) {
				LOG.info("comprimiendo archivo: " + pdf.getNamePdf());
				ZipEntry entry = new ZipEntry(pdf.getNamePdf());
				entry.setSize(pdf.getPdf().length);
				zous.putNextEntry(entry);
				zous.write(pdf.getPdf());
				
			}
			for (Xml xml : dataXml) {
				LOG.info("comprimiendo archivo: " + xml.getNameXml());
				ZipEntry entry = new ZipEntry(xml.getNameXml());
				entry.setSize(xml.getXml().length);
				zous.putNextEntry(entry);
				zous.write(xml.getXml());

			}

			zous.closeEntry();
			zous.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return outputStream.toByteArray();

	}
}
