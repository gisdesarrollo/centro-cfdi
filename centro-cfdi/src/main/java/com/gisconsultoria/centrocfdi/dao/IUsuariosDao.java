package com.gisconsultoria.centrocfdi.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.gisconsultoria.centrocfdi.model.Usuarios;

public interface IUsuariosDao extends CrudRepository<Usuarios, Long>{
	
	@Query("select u from Usuarios u where u.username= ?1")
	public Usuarios findByUsername(String username);
	
}
