package com.gisconsultoria.centrocfdi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gisconsultoria.centrocfdi.dao.IUsuariosDao;
import com.gisconsultoria.centrocfdi.model.Usuarios;

@Service
public class UsuariosServiceImpl implements IUsuariosService{

	@Autowired
	private IUsuariosDao usuarioDao;
	
	@Override
	public Usuarios save(Usuarios usuario) {
		return usuarioDao.save(usuario);
	}

	@Override
	public Usuarios getUserById(Long id) {
		return usuarioDao.findById(id).orElse(null);
	}

	@Override
	public List<Usuarios> getUsuarios() {
		return usuarioDao.getUsuarios();
	}

	@Override
	public Usuarios findByUsername(String username) {
		return usuarioDao.findByUsername(username);
	}

}
