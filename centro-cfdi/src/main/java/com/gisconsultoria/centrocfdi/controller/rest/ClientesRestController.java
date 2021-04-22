package com.gisconsultoria.centrocfdi.controller.rest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.exception.DataException;
import org.hibernate.transform.ToListResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gisconsultoria.centrocfdi.model.Clientes;
import com.gisconsultoria.centrocfdi.model.Comprobantes;
import com.gisconsultoria.centrocfdi.model.Roles;
import com.gisconsultoria.centrocfdi.model.Usuarios;
import com.gisconsultoria.centrocfdi.model.dto.ClientesDto;
import com.gisconsultoria.centrocfdi.service.IClientesService;
import com.gisconsultoria.centrocfdi.service.IComprobanteService;
import com.gisconsultoria.centrocfdi.service.IUsuariosService;

@RestController
@RequestMapping("/api")
public class ClientesRestController {

	private static final Log logger = LogFactory.getLog(ClientesRestController.class);

	@Autowired
	private IClientesService clienteService;
	
	 @Value("${path.archivo.archivosXml}")
	    private String pathArchivos;
	 
	 @Autowired
		private IUsuariosService usuarioService;
	 
	 @Autowired
		private IComprobanteService comprobanteService;

	@GetMapping("/cliente")
	public List<Clientes> getClientesWithUsername(@RequestParam(value = "username" ,required = false)String username) {
		 
		String rolUsername=null;
		List<Clientes> clientes=null;
		List<Long> idCliente = null;
		 if(username!= null && !username.contains("undefined")) {
			 Usuarios usuario = usuarioService.findByUsername(username);
			 for (Roles rol : usuario.getRoles()) {
				rolUsername= rol.getNombre();
			}
		}
		if(rolUsername!=null && rolUsername.equals("ROLE_ADMIN")) {
		//ROL_ADMIN
		clientes = clienteService.getActiveEmpresas();
		for (Clientes lista : clientes) {
			lista.setCer(null);
			lista.setKey(null);
			lista.setLogo(null);
			}
		}else {
			//Role_User
			 idCliente = clienteService.getIdClienteByUsername(username);
			 clientes = clienteService.getClienteWithIn(idCliente);
			 
			 List<Clientes> clie = clientes.stream().filter(p-> p.getNombre().equals("TIS")  ).collect(Collectors.toList()); 
			 logger.info(clie.size());
		}
		return clientes;
	}
	
	@GetMapping("/clientes")
	public List<Clientes> getAllClientes() {
		 
		List<Clientes> clientes=null;
		clientes = clienteService.getActiveEmpresas();
		for (Clientes lista : clientes) {
			lista.setCer(null);
			lista.setKey(null);
			lista.setLogo(null);
			}
		
		return clientes;
	}

	@Secured("ROLE_ADMIN")
	@PostMapping(value = "/cliente", consumes = { "application/json", "multipart/form-data",
			"application/octet-stream" })
	public ResponseEntity<?> createCliente(@RequestPart(value = "cliente", required = false) ClientesDto clienteDto,
			@RequestPart(value = "archivoCer", required = false) MultipartFile archivoCer,
			@RequestPart(value = "archivoKey", required = false) MultipartFile archivoKey,
			@RequestPart(value = "logo", required = false) MultipartFile logo) throws IOException {

		Map<String, Object> response = new HashMap<>();
		Clientes clienteNew = new Clientes();
		Clientes clienteActual = new Clientes();
		File fileActual=null;
		File fileRename=null;
		try {
			if(clienteDto.getNombre()== null || clienteDto.getRazonSocial()== null || clienteDto.getRfc() ==null ||
					clienteDto.getCodigoPostal()<0 || clienteDto.getPais() <0) {
				logger.error(" Campos requeridos no pueden ir vacios");
				response.put("mensaje", "Campos requeridos no pueden ir vacios");
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
			}
			if (clienteDto.getId() != null) {
				clienteActual = clienteService.findById(clienteDto.getId());
				fileActual = new File(pathArchivos, File.separator + clienteActual.getNombre());
				if(!clienteActual.getNombre().equals(clienteDto.getNombre())) {
					if(!buildDirectory(fileActual)) {
						fileRename = new File(pathArchivos, File.separator + clienteDto.getNombre());
						fileActual.renameTo(fileRename);
						fileActual=fileRename;
					}
				}
				clienteNew.setId(clienteActual.getId());
				if (archivoCer==null) {
					clienteNew.setCer(clienteActual.getCer());
					clienteNew.setNombreFileCer(clienteActual.getNombreFileCer());
				} else {
					clienteNew.setCer(archivoCer.getBytes());
					clienteNew.setNombreFileCer(archivoCer.getOriginalFilename().replace(" ", ""));
				}
				if (archivoKey== null) {
					clienteNew.setKey(clienteActual.getKey());
					clienteNew.setNombreFileKey(clienteActual.getNombreFileKey());
				} else {
					clienteNew.setKey(archivoKey.getBytes());
					clienteNew.setNombreFileKey(archivoKey.getOriginalFilename().replace(" ", ""));
				}
				if (logo==null) {
					clienteNew.setLogo(clienteActual.getLogo());
					clienteNew.setNombreLogo(clienteActual.getNombreLogo());
				} else {
					clienteNew.setLogo(logo.getBytes());
					clienteNew.setNombreLogo(logo.getOriginalFilename().replace(" ", ""));
				}
			} else {
				fileActual = new File(pathArchivos, File.separator + clienteDto.getNombre());
				if (archivoCer!=null) {
					clienteNew.setCer(archivoCer.getBytes());
					clienteNew.setNombreFileCer(archivoCer.getOriginalFilename().replace(" ", ""));
				}
				if (archivoKey!=null) {
					clienteNew.setKey(archivoKey.getBytes());
					clienteNew.setNombreFileKey(archivoKey.getOriginalFilename().replace(" ", ""));
				}
				if (logo!=null) {
					clienteNew.setLogo(logo.getBytes());
					clienteNew.setNombreLogo(logo.getOriginalFilename().replace(" ", ""));
				}
			}

			Date fechaRegistro = new Date();
			// pasamos datos
			clienteNew.setFechaInicial(fechaRegistro);
			clienteNew.setStatus(1);
			clienteNew.setNombre(clienteDto.getNombre());
			clienteNew.setRazonSocial(clienteDto.getRazonSocial());
			clienteNew.setRfc(clienteDto.getRfc());
			clienteNew.setCodigoPostal(clienteDto.getCodigoPostal());
			clienteNew.setPais(clienteDto.getPais());
			clienteNew.setEmail(clienteDto.getEmail());
			clienteNew.setPasswordKey(clienteDto.getPasswordKey());
			clienteNew.setKeyXsa(clienteDto.getKeyXsa());
			clienteNew.setServidor(clienteDto.getServidor());

			clienteService.save(clienteNew);
			//crea la carpeta del nuevo cliente
			buildDirectory(fileActual);
			 
			logger.info("cliente creado con exito!");

		} catch (DataAccessException e) {
			logger.error("Error al momento de insertar el cliente");
			response.put("mensaje", "Error al insertar el cliente");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El cliente ha sido creado con éxito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	

	@Secured("ROLE_ADMIN")
	@DeleteMapping("/cliente/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			Clientes cliente = clienteService.findById(id);
			if (cliente.getId() == null) {
				response.put("mensaje",
						"Error al eliminar el cliente con ID :".concat(id.toString().concat("no existe en la BD!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			clienteService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el cliente de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN" )
	@GetMapping("/cliente/{id}")
	public ResponseEntity<?> getUsuarios(@PathVariable Long id) {

		Clientes cliente = null;
		Map<String, Object> response = new HashMap<>();

		try {
			cliente = clienteService.findById(id);
			if (cliente == null) {
				response.put("mensaje",
						"El cliente con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			cliente.setCer(null);
			cliente.setKey(null);
			cliente.setLogo(null);
			// response.put("usuario", usuario);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Clientes>(cliente, HttpStatus.OK);
	}
	
	
	 private boolean buildDirectory(File file) {
		 	if(file.exists()) {
		 		return false;
		 	}
		 	file.mkdir();
	        return true;
	    }

}
