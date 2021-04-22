package com.gisconsultoria.centrocfdi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gisconsultoria.centrocfdi.dao.IXmlDao;
import com.gisconsultoria.centrocfdi.model.Xml;

@Service
public class XmlServiceImpl implements IXmlService {

	@Autowired
	private IXmlDao xmlDao;

	@Override
	public void saveXml(Xml xml) {
		xmlDao.save(xml);
	}

	@Override
	public Xml getFileByIdCfdi(Long idCfdi) {
		return xmlDao.getFileByIdCfdi(idCfdi);
	}

	@Override
	public List<Xml> getAllFileXmlByCliente(Long id) {
		return xmlDao.getAllFileXmlByCliente(id);
	}

	@Override
	public List<Xml> getAllFileXml() {
		return (List<Xml>) xmlDao.findAll();
	}

	@Override
	public List<Xml> getAllFilePdfByParameter(String fechaInicial, String fechaFinal, String tipoComprobante,
			String clienteId) {
		return xmlDao.getAllFileXmlByParameter(fechaInicial, fechaFinal, tipoComprobante, clienteId);
	}

	

}
