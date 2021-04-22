package com.gisconsultoria.centrocfdi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gisconsultoria.centrocfdi.dao.IParametrosDao;
import com.gisconsultoria.centrocfdi.model.Parametros;

@Service
public class ParametrosServiceImpl implements IParametrosService {
	
	@Autowired
	private IParametrosDao parametrosDao;

	@Override
	public List<Parametros> getParametrosByIdCliente(Long id) {
		return parametrosDao.getParametrosByIdCliente(id);
	}
	
	

}
