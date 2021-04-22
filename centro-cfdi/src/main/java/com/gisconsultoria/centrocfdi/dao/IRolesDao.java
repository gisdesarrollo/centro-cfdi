package com.gisconsultoria.centrocfdi.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.gisconsultoria.centrocfdi.model.Roles;

public interface IRolesDao extends CrudRepository<Roles, Long>{

	@Query("select r from Roles r where r.nombre= ?1")
	public Roles getRoleByNombre(String nombreRol);
}
