package com.gisconsultoria.centrocfdi.controller.rest;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.Column;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gisconsultoria.centrocfdi.dao.ICfdiPrincipalDao;
import com.gisconsultoria.centrocfdi.model.CfdiPrincipal;
import com.gisconsultoria.centrocfdi.model.Clientes;
import com.gisconsultoria.centrocfdi.model.Roles;
import com.gisconsultoria.centrocfdi.model.Usuarios;
import com.gisconsultoria.centrocfdi.model.dto.DataTableResponseDto;
import com.gisconsultoria.centrocfdi.model.dto.ParamsDto;
import com.gisconsultoria.centrocfdi.service.ICfdiPrincipalService;
import com.gisconsultoria.centrocfdi.service.IClientesService;
import com.gisconsultoria.centrocfdi.service.IComprobanteService;
import com.gisconsultoria.centrocfdi.service.IUsuariosService;

@RestController
@RequestMapping("/api")
public class CfdiRestController {

	protected static final Log logger = LogFactory.getLog(CfdiRestController.class);

	@Autowired
	private ICfdiPrincipalDao cfdiPrincipalDao;

	@Autowired
	private ICfdiPrincipalService cfdiPrincipalService;

	@Autowired
	private IClientesService clienteService;

	@Autowired
	private IUsuariosService usuarioService;

	@Autowired
	private IComprobanteService comprobanteService;

	@Autowired
	private ModelMapper mapper;

	@SuppressWarnings("unused")
	//@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@RequestMapping(value = "/data/cfdi", method = RequestMethod.POST)
	@ResponseBody
	public DataTablesOutput<DataTableResponseDto> getCfdiByParametros(@RequestParam(value = "inicial", required = false) String fechaInicial,
			@RequestParam(value = "final", required = false) String fechafinal,
			@RequestParam(value = "clienteId", required = false) String clienteId,
			@RequestParam(value = "tipoComp", required = false) String tipoComprobante,
			@RequestParam(value = "username", required = false) String username,
			@RequestBody DataTablesInput input) throws IOException {

		
		Page<CfdiPrincipal> cfdi = null;
		int searchTotal = 0;
		String rolUsername = null;
		List<Long> idCliente = null;
		List<String> comprobante = null;
		int cfdiSize=0;
		DataTablesOutput<DataTableResponseDto> result = new DataTablesOutput<>();
		try {
			int pageNumber = (int) (Math.floor(input.getStart() / input.getLength()) + ( input.getStart() % input.getLength()));
			Pageable pageableRequest= PageRequest.of(pageNumber,input.getLength());
			if (username != null) {
				Usuarios usuario = usuarioService.findByUsername(username);
				for (Roles rol : usuario.getRoles()) {
					rolUsername = rol.getNombre();
				}
			}
			if (rolUsername != null && rolUsername.equals("ROLE_ADMIN")) {
				//ROLE_ADMIN
				if (!fechaInicial.contains("undefined") && !fechafinal.contains("undefined")) {
					if (tipoComprobante.contains("undefined")) {
						tipoComprobante = "";
					}
					if (clienteId.contains("undefined")) {
						clienteId = "";
					}
					if (input.getSearch().getValue() != null && input.getSearch().getValue() != "") {
						cfdi = cfdiPrincipalService.findCfdiBySearch(input.getSearch().getValue(), fechaInicial, fechafinal,
								clienteId, tipoComprobante,pageableRequest);
						searchTotal = (int) cfdi.getTotalElements();
						if(!clienteId.equals("")) {
							cfdiSize = cfdiPrincipalService.countCfdiByFechaAndClienteAndComprobante(fechaInicial, fechafinal, clienteId,tipoComprobante);
						}else {
							cfdiSize = cfdiPrincipalService.countByFechaByComprobante(fechaInicial, fechafinal, tipoComprobante);
						}
						
					} else {
						if(!clienteId.equals("")) {
							cfdi = cfdiPrincipalService.findCfdiByFechaAndClienteAndComprobante(fechaInicial, fechafinal, clienteId,tipoComprobante,pageableRequest);
							cfdiSize = (int) cfdi.getTotalElements();
						}else {
							cfdi = cfdiPrincipalService.findCfdiByFechaAndComprobante(fechaInicial, fechafinal, tipoComprobante,pageableRequest);
							cfdiSize = (int) cfdi.getTotalElements();
						}
						
					}
					if (cfdi.getContent() != null && cfdi.getContent().size() > 0) {					
						 result.setData(cfdi.getContent().stream().map(this::convertToDto).collect(Collectors.toList()));
				         result.setRecordsTotal(cfdiSize);
				         if(searchTotal>0) {
								result.setRecordsFiltered(searchTotal);
							}else {
								result.setRecordsFiltered(cfdiSize);
							}
					}
					
				} else {
					DataTablesOutput<CfdiPrincipal> dtoCfdi = cfdiPrincipalDao.findAll(input);
					
					if (dtoCfdi.getData() != null && dtoCfdi.getData().size()>0) {
						 result.setData(dtoCfdi.getData().stream().map(this::convertToDto).collect(Collectors.toList()));
				         result.setRecordsFiltered(dtoCfdi.getRecordsFiltered());
				         result.setRecordsTotal(dtoCfdi.getRecordsTotal());

					}
					
				}
			} else {
				// ROLE_USER		
		
				idCliente = clienteService.getIdClienteByUsername(username);
				comprobante = comprobanteService.getNameComprobanteByUsername(username);
				
					// busqueda de cfdi con input
					if (!fechaInicial.contains("undefined") || !fechafinal.contains("undefined")) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						Date fechInicial = format.parse(fechaInicial);
						Date fechFinal = format.parse(fechafinal);
						if (tipoComprobante.contains("undefined")) {
							tipoComprobante = "";
						
						}else {
							for(int x=0;x<comprobante.size();x++) {
								if(!comprobante.get(x).equals(tipoComprobante)) {
									comprobante.remove(x);
									x--;
								}
							}
						}
						if (clienteId.contains("undefined")) {
							clienteId = "";
						}else {
							for(int x=0;x<idCliente.size();x++) {
								if(!idCliente.get(x).toString().equals(clienteId)) {
									idCliente.remove(x);
									x--;
								}
							}
						}
						//busqueda cfdi por search and input
						if (input.getSearch().getValue() != null && input.getSearch().getValue() != "") {
							cfdi = cfdiPrincipalService.findCfdiBySearchWitnIn(input.getSearch().getValue(), fechaInicial, fechafinal, idCliente, comprobante,pageableRequest);
							cfdiSize = cfdiPrincipalService.countSearchByClienteAndComprobanteWithFecha(fechaInicial,fechafinal,idCliente, comprobante);
							searchTotal = (int) cfdi.getTotalElements();
							
						}else {
							//busqueda cfdi with input
							cfdi = cfdiPrincipalService.findCfdiByClienteAByComprobanteAndFecha(idCliente, comprobante, fechaInicial, fechafinal,pageableRequest);
							cfdiSize = (int) cfdi.getTotalElements();
						}
					} else {
						// busqueda de cfdi sin input
						if(input.getSearch().getValue()!=null && input.getSearch().getValue()!="") {
							cfdi = cfdiPrincipalService.findCfdiBySearchByClienteAndComprobante(input.getSearch().getValue(), idCliente, comprobante,pageableRequest);
							searchTotal = (int) cfdi.getTotalElements();
							cfdiSize = cfdiPrincipalService.countCfdiByClienteAndComprobante(idCliente, comprobante);
						}else {
							
							cfdi =cfdiPrincipalService.findCfdiByClienteAndComprobanteWithCount(idCliente, comprobante,pageableRequest);
							cfdiSize = (int) cfdi.getTotalElements();
						}
					}
				

				if (cfdi.getContent()!=null && cfdi.getContent().size() > 0) {
					result.setData(cfdi.getContent().stream().map(this::convertToDto).collect(Collectors.toList()));
			        result.setRecordsTotal(cfdiSize);
			        if(searchTotal>0) {
						result.setRecordsFiltered(searchTotal);
					}else {
						result.setRecordsFiltered(cfdiSize);
					}
			        
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error al momento de la ejecucion: " + e);
		}
		
		return result;
		

	}

	private DataTableResponseDto convertToDto(CfdiPrincipal cfdiPrincipal) {		
		DataTableResponseDto responseDto = mapper.map(cfdiPrincipal, DataTableResponseDto.class);
		responseDto.setRazonSocial(cfdiPrincipal.getEmpresaId().getRazonSocial());
		responseDto.setRfc(cfdiPrincipal.getEmpresaId().getRfc());
		responseDto.setSerie(cfdiPrincipal.getSerie());

		return responseDto;
	}

}
