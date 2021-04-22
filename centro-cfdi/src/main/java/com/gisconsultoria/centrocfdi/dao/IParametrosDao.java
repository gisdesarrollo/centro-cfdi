package com.gisconsultoria.centrocfdi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.gisconsultoria.centrocfdi.model.Parametros;

public interface IParametrosDao extends CrudRepository<Parametros, Long>{

	@Query("select p from Parametros p where p.clienteId.id=:id ")
	public List<Parametros> getParametrosByIdCliente(@Param("id") Long id);
}
