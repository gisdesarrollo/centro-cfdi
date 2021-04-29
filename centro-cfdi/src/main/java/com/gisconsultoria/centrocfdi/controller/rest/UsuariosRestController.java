package com.gisconsultoria.centrocfdi.controller.rest;

import java.io.File;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gisconsultoria.centrocfdi.model.Clientes;
import com.gisconsultoria.centrocfdi.model.Comprobantes;
import com.gisconsultoria.centrocfdi.model.Roles;
import com.gisconsultoria.centrocfdi.model.Usuarios;
import com.gisconsultoria.centrocfdi.service.IClientesService;
import com.gisconsultoria.centrocfdi.service.IComprobanteService;
import com.gisconsultoria.centrocfdi.service.IRolesService;
import com.gisconsultoria.centrocfdi.service.IUsuariosService;

import ch.qos.logback.classic.Logger;

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
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/usuarios")
	public List<Usuarios> getAllClientes(){	
		return usuarioService.getAllUsuarios();
	}
	
	//@Secured("ROLE_ADMIN") 
	@PostMapping("/usuarios/crear")
	public ResponseEntity<?> createUsuari(@RequestBody Usuarios usuario) {
		
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
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CONFLICT);
			}
			
			//codifica la contraseña
			for (int i = 0; i < 2; i++) {
				passwordUser = passwordEncoder.encode(usuario.getPassword());
			}
			if(usuario.getRoles().size()<1) {
			List<Roles> roles = rolesService.getAllRoles();
			List<Roles> obtengoRolAsignar = new ArrayList<>();
			for(Roles rol : roles) {
				if(rol.getNombre().equals("ROLE_USER")) {
					obtengoRolAsignar.add(rol);
					usuario.setRoles(obtengoRolAsignar);
				}
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
	@DeleteMapping("/usuario/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			Usuarios usuario = usuarioService.getUserById(id);
			if (usuario.getId() == null) {
				response.put("mensaje",
						"Error al eliminar el usuario con ID :".concat(id.toString().concat("no existe en la BD!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			usuarioService.delete(id);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el usuario");
			response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	//@Secured("ROLE_ADMIN" )
	@GetMapping("/usuario/{id}")
	public @ResponseBody ResponseEntity<?> getUsuarios(@PathVariable Long id) {

		Usuarios usuario = null;
		Map<String, Object> response = new HashMap<>();

		try {
			usuario = usuarioService.getUserById(id);
			if (usuario == null) {
				response.put("mensaje",
						"El usuario con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Usuarios>(usuario, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN" )
	@PutMapping("/usuario/{id}")
	public ResponseEntity<?> updateUsuario(@RequestBody Usuarios usuario,@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		Usuarios user = null;
		String passwordUser = null; 
		Clientes cliente=null;
		Comprobantes comprobante=null;
		List<Clientes> listCliente = new ArrayList<>();
		List<Comprobantes> listComprobante = new ArrayList<>();
		try {
			user = usuarioService.getUserById(id);
			if(user!=null) {
				if(usuario.getNuevaPassword()!=null) {
					//codifica la contraseña
					for (int i = 0; i < 2; i++) {
						passwordUser = passwordEncoder.encode(usuario.getNuevaPassword());
					}
					user.setPassword(passwordUser);
				}
				user.setNombre(usuario.getNombre());
				user.setApellidos(usuario.getApellidos());
				user.setEmail(usuario.getEmail());
				user.setEstatus(usuario.getEstatus());
				user.setUsername(usuario.getUsername());
				user.setRoles(usuario.getRoles());
				if(usuario.getClienteId().length>0 ) {
					for(Long clienteId: usuario.getClienteId()) {
						 cliente = clienteService.findById(clienteId);
						 if(cliente!=null) {
							 listCliente.add(cliente);
						 }
					}
					user.setClientes(listCliente);
				}else {
					user.setClientes(null);
				}
				
				if(usuario.getComprobante().length>0) {
					for(String name: usuario.getComprobante()) {
						comprobante = comprobanteService.getComprobanteByName(name);
						if(comprobante!=null) {
							listComprobante.add(comprobante);
						}
					}
					user.setComprobantes(listComprobante);
				}else {
					user.setComprobantes(null);
				}
				
				usuarioService.save(user);
			}else {
				response.put("mensaje",
						"El usuario no existe en la base de datos!");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/roles")
	public List<Roles> getAllRoles(){	
		return rolesService.getAllRoles();
	}
	
}
