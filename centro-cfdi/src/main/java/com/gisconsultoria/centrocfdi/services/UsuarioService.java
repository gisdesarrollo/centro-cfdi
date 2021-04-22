package com.gisconsultoria.centrocfdi.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gisconsultoria.centrocfdi.dao.IUsuariosDao;
import com.gisconsultoria.centrocfdi.model.Usuarios;

@Service
public class UsuarioService implements UserDetailsService {

	private static final Logger LOG = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private IUsuariosDao usuariosDao;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuarios usuario = usuariosDao.findByUsername(username);
		if(usuario == null) {
			LOG.error("Error en el login: no existe el usuario "+username+" en el sistema");
			throw new UsernameNotFoundException("Error en el login: no existe el usuario "+username+" en el sistema");
		}
		List<GrantedAuthority> authority = usuario.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getNombre()))
				.collect(Collectors.toList());
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEstatus(), true, true, true, authority);
	}

}
