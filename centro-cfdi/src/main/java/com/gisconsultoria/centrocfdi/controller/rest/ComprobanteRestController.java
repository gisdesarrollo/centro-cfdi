package com.gisconsultoria.centrocfdi.controller.rest;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gisconsultoria.centrocfdi.model.Comprobantes;
import com.gisconsultoria.centrocfdi.model.Roles;
import com.gisconsultoria.centrocfdi.model.Usuarios;
import com.gisconsultoria.centrocfdi.service.IComprobanteService;
import com.gisconsultoria.centrocfdi.service.IUsuariosService;

@RestController
@RequestMapping("/api")
public class ComprobanteRestController {
	
	private static final Log logger = LogFactory.getLog(ComprobanteRestController.class);

	@Autowired
	private IComprobanteService comprobanteService;
	
	@Autowired
	private IUsuariosService usuarioService;
	
	@RequestMapping(value="/comprobantes",method = RequestMethod.GET)
	public List<Comprobantes> getAllComprobantes(@RequestParam(value = "username" ,required = false)String username) {
		 
		String rolUsername=null;
		List<Comprobantes> comprobantes=null;
		List<String> tiposComprobantes = null;
		 if(username!= null) {
			 Usuarios usuario = usuarioService.findByUsername(username);
			 for (Roles rol : usuario.getRoles()) {
				rolUsername= rol.getNombre();
			}
		}
		if(rolUsername!=null && rolUsername.equals("ROLE_ADMIN")) {
		//ROL-ADMIN
		comprobantes = comprobanteService.getAllComprobantes();
		
		}else {
			//ROLE_USER
			 tiposComprobantes = comprobanteService.getNameComprobanteByUsername(username);
			 comprobantes = comprobanteService.getComprobanteWithIn(tiposComprobantes);
			 
		}
		return comprobantes;
	}
}
