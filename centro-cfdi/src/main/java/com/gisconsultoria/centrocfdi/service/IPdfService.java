package com.gisconsultoria.centrocfdi.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.gisconsultoria.centrocfdi.model.Pdf;
import com.gisconsultoria.centrocfdi.model.Xml;

public interface IPdfService {

	public void savePdf(Pdf pdf);
	
	public Pdf getFileByIdCfdi(Long idCfdi);
	
	public List<Pdf> getAllFilePdfByCliente(Long id);
	
	public List<Pdf> getAllFilePdf();
	
	public List<Pdf> getAllFilePdfByParameter(String fechaInicial,String fechaFinal ,
			String tipoComprobante,String clienteId);
}
