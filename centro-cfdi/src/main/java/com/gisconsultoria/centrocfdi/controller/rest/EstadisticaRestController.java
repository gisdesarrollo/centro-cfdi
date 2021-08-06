package com.gisconsultoria.centrocfdi.controller.rest;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gisconsultoria.centrocfdi.model.Clientes;
import com.gisconsultoria.centrocfdi.model.Comprobantes;
import com.gisconsultoria.centrocfdi.model.Roles;
import com.gisconsultoria.centrocfdi.model.Usuarios;
import com.gisconsultoria.centrocfdi.model.dto.EstadisticaDto;
import com.gisconsultoria.centrocfdi.service.ICfdiPrincipalService;
import com.gisconsultoria.centrocfdi.service.IClientesService;
import com.gisconsultoria.centrocfdi.service.IComprobanteService;
import com.gisconsultoria.centrocfdi.service.IUsuariosService;

@RestController
@RequestMapping("/api")
public class EstadisticaRestController {

	private static final Log LOG = LogFactory.getLog(EstadisticaRestController.class);

	@Autowired
	private IClientesService clienteService;

	@Autowired
	private IUsuariosService usuarioService;

	@Autowired
	private ICfdiPrincipalService cfdiPrincipal;

	@Autowired
	private IComprobanteService comprobanteService;

	@GetMapping("/estadistica")
	public List<EstadisticaDto> getEstadisticaComprobante(
			@RequestParam(value = "username", required = false) String username) {

		String rolUsername = null;
		List<Clientes> clientes = null;
		List<Long> idCliente = null;
		List<EstadisticaDto> listCompTotales = new ArrayList<EstadisticaDto>();
		// Estadistica totalComp=null;
		Long CompTotales = null;
		List<Comprobantes> listComp = null;
		try {
			if (username != null && !username.contains("undefined")) {
				Usuarios usuario = usuarioService.findByUsername(username);
				for (Roles rol : usuario.getRoles()) {
					rolUsername = rol.getNombre();
				}
			}
			if (rolUsername != null && rolUsername.equals("ROLE_ADMIN")) {
				// ROL_ADMIN
				clientes = clienteService.getActiveClientes();
				for (Clientes lista : clientes) {
					lista.setCer(null);
					lista.setKey(null);
					lista.setLogo(null);
				}
			} else {
				// Role_User
				idCliente = clienteService.getIdClienteByUsername(username);
				// clientes = clienteService.getClienteWithIn(idCliente);
				EstadisticaDto eTotal = new EstadisticaDto();

				listComp = comprobanteService.getAllComprobantes();
				for (Long idC : idCliente) {

					eTotal = getCaseComprobante(idC);

					listCompTotales.add(eTotal);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Error al momento de obtener las estadisticas");
		}
		return listCompTotales;

	}

	public EstadisticaDto getCaseComprobante(Long idC) {

		List<EstadisticaDto> estadC = new ArrayList<EstadisticaDto>();
		;
		EstadisticaDto eTotal = new EstadisticaDto();
		String nameCliente = null;
		try {
			List<Comprobantes> listComp = comprobanteService.getAllComprobantes();
			for (Comprobantes comp : listComp) {

				Long CompTotales = cfdiPrincipal.getEstadisticaByIdcliente(idC, comp.getNombre());

				if (comp.getNombre().equals("I")) {
					eTotal.setIngreso(CompTotales);
				}
				if (comp.getNombre().equals("E")) {
					eTotal.setEgreso(CompTotales);
				}
				if (comp.getNombre().equals("T")) {
					eTotal.setTraslado(CompTotales);
				}
				if (comp.getNombre().equals("N")) {
					eTotal.setNomina(CompTotales);

				}
				if (comp.getNombre().equals("P")) {
					eTotal.setPago(CompTotales);
				}
			}
			Long total = eTotal.getIngreso() + eTotal.getEgreso() + eTotal.getTraslado() + eTotal.getNomina()
			+ eTotal.getPago();
			eTotal.setTotal(total);
			nameCliente = getClienteId(idC);
			eTotal.setCliente(nameCliente);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return eTotal;

	}

	public String getClienteId(Long id) {
		Clientes cliente = clienteService.findById(id);

		return cliente.getNombre();

	}

}
