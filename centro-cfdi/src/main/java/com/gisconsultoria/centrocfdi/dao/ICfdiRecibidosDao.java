package com.gisconsultoria.centrocfdi.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.gisconsultoria.centrocfdi.model.CfdiRecibidos;
import com.gisconsultoria.centrocfdi.model.Parametros;

public interface ICfdiRecibidosDao extends CrudRepository<CfdiRecibidos, Long>{

	
}
