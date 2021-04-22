package com.gisconsultoria.centrocfdi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gisconsultoria.centrocfdi.dao.IRolesDao;
import com.gisconsultoria.centrocfdi.model.Roles;

@Service
public class RolesServiceImpl implements IRolesService {

	@Autowired
	private IRolesDao rolesDao;

	@Override
	public Roles getRoleByNombre(String nombreRol) {
		return rolesDao.getRoleByNombre(nombreRol);
	}

	@Override
	public List<Roles> getAllRoles() {
		return (List<Roles>) rolesDao.findAll();
	}

	
	
}
