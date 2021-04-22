package com.gisconsultoria.centrocfdi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gisconsultoria.centrocfdi.dao.ICfdiRecibidosDao;
import com.gisconsultoria.centrocfdi.model.CfdiRecibidos;

@Service
public class CfdiRecibidosServiceImpl implements ICfdiRecibidosService{

	@Autowired
	private ICfdiRecibidosDao cfdiRecibidosDao;
	
	@Override
	public void save(CfdiRecibidos cfdiRecibido) {
		cfdiRecibidosDao.save(cfdiRecibido);
		
	}

}
