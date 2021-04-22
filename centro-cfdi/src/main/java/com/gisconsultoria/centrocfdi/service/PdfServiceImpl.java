package com.gisconsultoria.centrocfdi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gisconsultoria.centrocfdi.dao.IPdfDao;
import com.gisconsultoria.centrocfdi.model.Pdf;
import com.gisconsultoria.centrocfdi.model.Xml;

@Service
public class PdfServiceImpl implements IPdfService {

	@Autowired
	private IPdfDao pdfDao;
	
	@Override
	public void savePdf(Pdf pdf) {
		pdfDao.save(pdf);
	}

	@Override
	public Pdf getFileByIdCfdi(Long idCfdi) {
		return pdfDao.getFileByIdCfdi(idCfdi);
	}

	@Override
	public List<Pdf> getAllFilePdfByCliente(Long id) {
		return pdfDao.getAllFilePdfByCliente(id);
	}

	@Override
	public List<Pdf> getAllFilePdf() {
		return (List<Pdf>) pdfDao.findAll();
	}

	@Override
	public List<Pdf> getAllFilePdfByParameter(String fechaInicial, String fechaFinal, String tipoComprobante,
			String clienteId) {
		return pdfDao.getAllFilePdfByParameter(fechaInicial, fechaFinal, tipoComprobante, clienteId);
	}

}
