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

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@RequestMapping(value = "/data/cfdi", method = RequestMethod.POST)
	@ResponseBody
	public void getCfdiByParametros(@RequestParam(value = "inicial", required = false) String fechaInicial,
			@RequestParam(value = "final", required = false) String fechafinal,
			@RequestParam(value = "clienteId", required = false) String clienteId,
			@RequestParam(value = "tipoComp", required = false) String tipoComprobante,
			@RequestParam(value = "username", required = false) String username,
			@RequestBody DataTablesInput input,
			HttpServletResponse response) throws IOException {

		List<CfdiPrincipal> cfdi = null;
		List<DataTableResponseDto> listDto = null;
		int totalCfdi = 0;
		int index = 1;
		String data = "";
		String json = null;
		int searchTotal = 0;
		String rolUsername = null;
		List<Long> idCliente = null;
		List<String> comprobante = null;

		try {
			if (username != null) {
				Usuarios usuario = usuarioService.findByUsername(username);
				for (Roles rol : usuario.getRoles()) {
					rolUsername = rol.getNombre();
				}
			}
			if (rolUsername != null && rolUsername.equals("ROLE_ADMIN")) {
				//ROLE_ADMIN
				DataTablesOutput<CfdiPrincipal> dtoCfdi = cfdiPrincipalDao.findAll(input);
				if (!fechaInicial.contains("undefined") || !fechafinal.contains("undefined")) {
					if (tipoComprobante.contains("undefined")) {
						tipoComprobante = "";
					}
					if (clienteId.contains("undefined")) {
						clienteId = "";
					}
					if (input.getSearch().getValue() != null && input.getSearch().getValue() != "") {
						cfdi = cfdiPrincipalDao.findCfdiBySearch(input.getSearch().getValue(), fechaInicial, fechafinal,
								clienteId, tipoComprobante);
						searchTotal = cfdiPrincipalDao.countSearch(fechaInicial, fechafinal, tipoComprobante,
								clienteId);
					} else {
						logger.info(clienteId);
						cfdi = cfdiPrincipalService.findCfdiByFecha(fechaInicial, fechafinal, tipoComprobante,
								clienteId);
						searchTotal = cfdi.size();
					}
					if (cfdi != null && cfdi.size() > 0) {
						listDto = cfdi.stream().map(this::convertToDto).collect(Collectors.toList());
						// error empieza desde aka
						int startCfdi = input.getStart();
						totalCfdi = input.getLength() + input.getStart();
						int validamostotalstart = totalCfdi;
						if (validamostotalstart > listDto.size()) {
							totalCfdi = listDto.size();
						}

						index += startCfdi;
						for (int x = input.getStart(); x < totalCfdi; x++) {

							data += "{" + "\"razonSocial\" :" + "\"" + listDto.get(x).getRazonSocial() + "\", "
									+ "\"rfc\" :" + "\"" + listDto.get(x).getRfc() + "\", " + "\"uuid\" :" + "\""
									+ listDto.get(x).getUuid() + "\", " + "\"folio\" :" + "\""
									+ listDto.get(x).getFolio() + "\", " + "\"serie\" :" + "\""
									+ listDto.get(x).getSerie() + "\", " + "\"fecha\" :" + "\""
									+ listDto.get(x).getFecha() + "\", " + "\"total\" : " + "\""
									+ listDto.get(x).getTotal() + "\", " + "\"id\" :" + "\"" + listDto.get(x).getId()
									+ "\"" + "}";

							if (index < totalCfdi) {
								data += ",";
							}

							index++;
						}

					}
					json = "{" + "\"recordsTotal\" : " + searchTotal + "," + "\"recordsFiltered\" : " + cfdi.size()
							+ "," + "\"data\" : [" + data + "]" + "}";
				} else {

					if (dtoCfdi.getData() != null && dtoCfdi.getData().size()>0) {
						listDto = dtoCfdi.getData().stream().map(this::convertToDto).collect(Collectors.toList());
						totalCfdi = listDto.size();
						logger.info(totalCfdi);
						for (DataTableResponseDto datas : listDto) {

							data += "{" + "\"razonSocial\" :" + "\"" + datas.getRazonSocial() + "\", " + "\"rfc\" :"
									+ "\"" + datas.getRfc() + "\", " + "\"uuid\" :" + "\"" + datas.getUuid() + "\", "
									+ "\"folio\" :" + "\"" + datas.getFolio() + "\", " + "\"serie\" :" + "\""
									+ datas.getSerie() + "\", " + "\"fecha\" :" + "\"" + datas.getFecha() + "\", "
									+ "\"total\" : " + "\"" + datas.getTotal() + "\", " + "\"id\" :" + "\""
									+ datas.getId() + "\"" + "}";

							if (index < totalCfdi) {
								data += ",";
							}

							// logger.info(data);
							index++;
						}

					}
					json = "{" + "\"recordsTotal\" : " + dtoCfdi.getRecordsFiltered() + "," + "\"recordsFiltered\" : "
							+ dtoCfdi.getRecordsTotal() + "," + "\"data\" : [" + data + "]" + "}";
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
								}
							}
						}
						if (clienteId.contains("undefined")) {
							clienteId = "";
						}else {
							for(int x=0;x<idCliente.size();x++) {
								if(!idCliente.get(x).toString().equals(clienteId)) {
									idCliente.remove(x);
								}
							}
						}
						//busqueda cfdi por search
						if (input.getSearch().getValue() != null && input.getSearch().getValue() != "") {
							cfdi = cfdiPrincipalService.findCfdiBySearchWitnIn(input.getSearch().getValue(), fechaInicial, fechafinal, idCliente, comprobante);
							searchTotal = cfdiPrincipalService.countSearchWithIn(fechaInicial, fechafinal, idCliente, comprobante);
						}else {
							cfdi = cfdiPrincipalService.findCfdiByClienteAByComprobanteAndFecha(idCliente, comprobante, fechInicial, fechFinal);
							searchTotal = cfdi.size();
						}
					} else {
						// busqueda de cfdi sin input
						if(input.getSearch().getValue()!=null && input.getSearch().getValue()!="") {
							cfdi = cfdiPrincipalService.findCfdiBySearchByClienteAndComprobante(input.getSearch().getValue(), idCliente, comprobante);
							searchTotal = cfdiPrincipalService.countSearchByClienteAndComprobante(idCliente, comprobante);
						}else {
							cfdi = cfdiPrincipalService.findCfdiByClienteAndComprobante(idCliente, comprobante);
							searchTotal = cfdi.size();
						}
					}

				

				if (cfdi != null && cfdi.size() > 0) {
					listDto = cfdi.stream().map(this::convertToDto).collect(Collectors.toList());

					int startCfdi = input.getStart();
					totalCfdi = input.getLength() + input.getStart();
					int validamostotalstart = totalCfdi;
					if (validamostotalstart > listDto.size()) {
						totalCfdi = listDto.size();
					}

					index += startCfdi;
					for (int x = input.getStart(); x < totalCfdi; x++) {

						data += "{" + "\"razonSocial\" :" + "\"" + listDto.get(x).getRazonSocial() + "\", "
								+ "\"rfc\" :" + "\"" + listDto.get(x).getRfc() + "\", " + "\"uuid\" :" + "\""
								+ listDto.get(x).getUuid() + "\", " + "\"folio\" :" + "\"" + listDto.get(x).getFolio()
								+ "\", " + "\"serie\" :" + "\"" + listDto.get(x).getSerie() + "\", " + "\"fecha\" :"
								+ "\"" + listDto.get(x).getFecha() + "\", " + "\"total\" : " + "\""
								+ listDto.get(x).getTotal() + "\", " + "\"id\" :" + "\"" + listDto.get(x).getId() + "\""
								+ "}";

						if (index < totalCfdi) {
							data += ",";
						}

						index++;
					}
				}
				json = "{" + "\"recordsTotal\" : " + searchTotal + "," + "\"recordsFiltered\" : " + cfdi.size()
				+ "," + "\"data\" : [" + data + "]" + "}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error al momento de la ejecucion: " + e);
		}
		response.setStatus(200);
		response.setContentType("application/x-json;charset=UTF-8");
		response.getWriter().write(json);

	}

	private DataTableResponseDto convertToDto(CfdiPrincipal cfdiPrincipal) {		
		DataTableResponseDto responseDto = mapper.map(cfdiPrincipal, DataTableResponseDto.class);
		responseDto.setRazonSocial(cfdiPrincipal.getEmpresaId().getRazonSocial());
		responseDto.setRfc(cfdiPrincipal.getEmpresaId().getRfc());
		responseDto.setSerie(cfdiPrincipal.getSerie());

		return responseDto;
	}

}
