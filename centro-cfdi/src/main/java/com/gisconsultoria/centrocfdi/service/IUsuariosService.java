package com.gisconsultoria.centrocfdi.service;

import java.util.List;

import com.gisconsultoria.centrocfdi.model.Usuarios;

public interface IUsuariosService {
	
	public Usuarios save(Usuarios usuario);

	public Usuarios getUserById(Long id);
	
	public List<Usuarios> getAllUsuarios();
	
	public Usuarios findByUsername(String username);
	
	public void delete(Long id);
	
	
}
