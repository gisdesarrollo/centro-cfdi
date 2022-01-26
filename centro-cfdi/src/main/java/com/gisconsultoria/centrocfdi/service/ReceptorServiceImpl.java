package com.gisconsultoria.centrocfdi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gisconsultoria.centrocfdi.dao.IReceptorDao;
import com.gisconsultoria.centrocfdi.model.Receptor;

@Service
public class ReceptorServiceImpl implements IReceptorService {

	@Autowired
	private IReceptorDao receptorDao;
	
	@Override
	public void save(Receptor receptor) {
		receptorDao.save(receptor);
		
	}

	@Override
	public Receptor findReceptorByIdCfdi(Long id) {
		return receptorDao.findReceptorByIdCfdi(id);
	}

}
