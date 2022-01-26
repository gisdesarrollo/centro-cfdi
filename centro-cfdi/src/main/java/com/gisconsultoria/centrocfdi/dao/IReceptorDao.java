package com.gisconsultoria.centrocfdi.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.gisconsultoria.centrocfdi.model.Receptor;

public interface IReceptorDao extends CrudRepository<Receptor, Long>{
	
	@Query("select r from Receptor r where r.idCfdi.id=?1 ")
	public Receptor findReceptorByIdCfdi(Long id);

}
