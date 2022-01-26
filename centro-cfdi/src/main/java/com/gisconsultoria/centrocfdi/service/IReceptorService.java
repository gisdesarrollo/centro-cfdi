package com.gisconsultoria.centrocfdi.service;

import com.gisconsultoria.centrocfdi.model.Receptor;

public interface IReceptorService {

	public void save(Receptor receptor);
	
	public Receptor findReceptorByIdCfdi(Long id);
}
