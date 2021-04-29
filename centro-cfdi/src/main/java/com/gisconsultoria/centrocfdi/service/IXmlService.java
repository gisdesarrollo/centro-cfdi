package com.gisconsultoria.centrocfdi.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.gisconsultoria.centrocfdi.model.Xml;

public interface IXmlService {
	
	public void saveXml(Xml xml);
	
	public Xml getFileByIdCfdi(Long idCfdi);
	
	public List<Xml> getAllFileXmlByCliente(Long id);
	
	public List<Xml> getAllFileXml();
	
	public List<Xml> getAllFilePdfByParameter(String fechaInicial,String fechaFinal ,
			List<String> tipoComprobante,List<Long> clienteId);
	
	public List<Xml> getAllfileXmlByComprobanteAndCliente(List<String> tipoComprobante,List<Long> clienteId);
}
