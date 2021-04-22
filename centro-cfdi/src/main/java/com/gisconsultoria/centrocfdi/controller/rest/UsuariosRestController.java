package com.gisconsultoria.centrocfdi.controller.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gisconsultoria.centrocfdi.model.Clientes;
import com.gisconsultoria.centrocfdi.model.Comprobantes;
import com.gisconsultoria.centrocfdi.model.Roles;
import com.gisconsultoria.centrocfdi.model.Usuarios;
import com.gisconsultoria.centrocfdi.service.IClientesService;
import com.gisconsultoria.centrocfdi.service.IComprobanteService;
import com.gisconsultoria.centrocfdi.service.IRolesService;
import com.gisconsultoria.centrocfdi.service.IUsuariosService;

@RestController
@RequestMapping("/api")
public class UsuariosRestController {

	@Autowired
	private IUsuariosService usuarioService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private IRolesService rolesService;
	
	@Autowired
	private IClientesService clienteService;
	
	@Autowired
	private IComprobanteService comprobanteService;
	
	@GetMapping("/usuarios")
	public List<Usuarios> getAllClientes(){	
		return usuarioService.getUsuarios();
	}
	
	//@Secured("ROLE_ADMIN") 
	@PostMapping("/usuarios/crear")
	public ResponseEntity<?> createUsuario( @RequestBody Usuarios usuario) {
		
		String passwordUser = null;
		Usuarios usuarioNew = null;
		Usuarios usuarioActual = null;
		Clientes cliente=null;
		Comprobantes comprobante=null;
		Map<String, Object> response = new HashMap<>();
		List<Clientes> listCliente = new ArrayList<>();
		List<Comprobantes> listComprobante = new ArrayList<>();
		try {
			usuarioActual = usuarioService.findByUsername(usuario.getUsername());
			if(usuarioActual!= null) {
				response.put("mensaje", "Error el username ya existe");
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
			}
			
			//codifica la contraseña
			for (int i = 0; i < 2; i++) {
				passwordUser = passwordEncoder.encode(usuario.getPassword());
			}
			List<Roles> roles = rolesService.getAllRoles();
			List<Roles> obtengoRolAsignar = new ArrayList<>();
			for(Roles rol : roles) {
				if(rol.getNombre().equals("ROLE_USER")) {
					obtengoRolAsignar.add(rol);
					usuario.setRoles(obtengoRolAsignar);
				}
			}
			
			if(usuario.getClienteId().length>0) {
				for(Long id: usuario.getClienteId()) {
					 cliente = clienteService.findById(id);
					 if(cliente!=null) {
						 listCliente.add(cliente);
					 }
				}
				usuario.setClientes(listCliente);
			}
			if(usuario.getComprobante().length>0) {
				for(String name: usuario.getComprobante()) {
					comprobante = comprobanteService.getComprobanteByName(name);
					if(comprobante!=null) {
						listComprobante.add(comprobante);
					}
				}
				usuario.setComprobantes(listComprobante);
			}
			usuario.setPassword(passwordUser);
			usuario.setEstatus(true);
			
			usuarioNew = usuarioService.save(usuario);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al insertar el usuario");
			response.put("error",e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El Usuario ha sido creado con éxito!");
		response.put("usuario", usuarioNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/usuarios/{username}")
	public ResponseEntity<?> getUsuarios(@PathVariable String username){
		
		Usuarios usuario = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			usuario = usuarioService.findByUsername(username);
			if(usuario == null) {
				response.put("mensaje", "El username: ".concat(username.toString().concat(" no existe en la base de datos!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			usuario.setPassword(null);
			//response.put("usuario", usuario);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Usuarios>(usuario, HttpStatus.OK);
	}
}
