package com.gisconsultoria.centrocfdi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gisconsultoria.centrocfdi.dao.IComprobanteDao;
import com.gisconsultoria.centrocfdi.model.Comprobantes;

@Service
public class ComprobanteServiceImpl implements IComprobanteService {

	@Autowired
	private IComprobanteDao comprobanteDao;
	
	@Override
	public Comprobantes getComprobanteByName(String nombre) {
		return comprobanteDao.getComprobanteByName(nombre);
	}

	@Override
	public List<String> getNameComprobanteByUsername(String username) {
		return comprobanteDao.getNameComprobanteByUsername(username);
	}

	@Override
	public List<Comprobantes> getComprobanteWithIn(List<String> comprobante) {
		return comprobanteDao.getComprobanteWithIn(comprobante);
	}

	@Override
	public List<Comprobantes> getAllComprobantes() {
		return (List<Comprobantes>) comprobanteDao.findAll();
	}

}
