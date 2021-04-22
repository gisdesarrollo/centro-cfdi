package com.gisconsultoria.centrocfdi.service;

import java.util.List;

import com.gisconsultoria.centrocfdi.model.Roles;

public interface IRolesService {

	public Roles getRoleByNombre(String nombreRol);
	
	public List<Roles> getAllRoles();
}
