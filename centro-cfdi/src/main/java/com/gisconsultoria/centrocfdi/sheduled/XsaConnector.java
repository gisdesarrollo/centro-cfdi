package com.gisconsultoria.centrocfdi.sheduled;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.gisconsultoria.centrocfdi.model.CfdiPrincipal;
import com.gisconsultoria.centrocfdi.model.Clientes;
import com.gisconsultoria.centrocfdi.service.ICfdiPrincipalService;
import com.gisconsultoria.centrocfdi.service.IClientesService;
import com.gisconsultoria.centrocfdi.services.GisconsultoriaXsaService;
import com.gisconsultoria.centrocfdi.util.GisConsultoriaReadXmlFile;
import com.gisconsultoria.centrocfdi.util.IConsumirSoap;

@Configuration
@EnableScheduling
public class XsaConnector {

	protected final Logger LOG = Logger.getLogger(XsaConnector.class);

	@Autowired
	private IClientesService clienteService;

	@Autowired
	private GisconsultoriaXsaService xsaService;

	@Autowired
	private GisConsultoriaReadXmlFile gisConsultoriaReadXmlFile;

	@Autowired
	private ICfdiPrincipalService cfdiPrincipalService;

	@Autowired
	private IConsumirSoap consumirSoap;

	@Value("${path.archivo.archivosXml}")
	private File path;

	private String rfcClienteWs = "DAL050601L35";

	 //@Scheduled(cron = "05 11 09 * * *", zone = "America/Mexico_City")
	//@Scheduled(fixedDelay = 600000) // se ejecuta cada 10 minutos
	@Scheduled(fixedDelay = 21600000) // se ejecuta cada 6 horas
	public void ProcesandoFoliofiscal() {

		List<Clientes> clientes = clienteService.getActiveClientes();
		ZoneId zoneId = ZoneId.of("America/Mexico_City");
		Date fechaActual = Date.from(ZonedDateTime.now(zoneId).toInstant());
		Calendar calendar = Calendar.getInstance();

		if (clientes != null) {
			try {
				LOG.info("DESCARGANDO ARCHIVOS");
				for (Clientes sucursal : clientes) {
					if (sucursal.getNombre().equals("AGE") || sucursal.getNombre().equals("ASE")) {
						if (fechaActual.after(sucursal.getFechaInicial())) {

							/*
							 * LocalDate fechaInicial = sucursal.getFechaInicial().toInstant()
							 * .atZone(zoneId) .toLocalDate(); LocalDate fechaFinal =
							 * fechaActual.toInstant() .atZone(zoneId) .toLocalDate();
							 */

							// long totalDias = ChronoUnit.DAYS.between(fechaFinal, fechaInicial);
							// calendar.setTime(sucursal.getFechaInicial());
							/* for(long dias = 0; totalDias < dias; totalDias++){ */
							// calendar.add(Calendar.DAY_OF_MONTH, (int)dias);
							/*
							 * Clientes readSucursal = clienteService.findById(sucursal.getId()); LocalDate
							 * fechaInicial2 = readSucursal.getFechaInicial() .toInstant() .atZone(zoneId)
							 * .toLocalDate();
							 */

							/*
							 * if(fechaInicial.compareTo(fechaInicial2) < 0){
							 * xsaService.ObtenerArchivos(readSucursal.getId(),
							 * readSucursal.getFechaInicial()); }else{
							 */
								//este metodo se activa
							xsaService.ObtenerArchivos(sucursal.getId(), sucursal.getFechaInicial(), fechaActual);
							/* } */
							/* } */
						}
					}
				}
				for (Clientes cliente : clientes) {

					// LOG.info("OBTENIENDO ARCHIVOS EXTRAIDOS EN LA CARPETA "
					// +cliente.getNombre());
					File pathCliente = new File(path.toString() + File.separator + cliente.getNombre());
					if (pathCliente.exists()) {
						LOG.info("OBTENIENDO ARCHIVOS EXTRAIDOS EN LA CARPETA " + cliente.getNombre());
						gisConsultoriaReadXmlFile.readXmlFile(pathCliente);
					}
				}
				LOG.info("EXTRACION DE ARCHIVOS A FINALIZADO");
				for (Clientes cliente : clientes) {
					// Clientes clientDAL =clienteService.getClienteByRfc(rfcClienteWs);
					// List<CfdiPrincipal> cfdiPrincipal=
					// cfdiPrincipalService.findCfdiByCliente(clientDAL.getId());
					if (cliente.getNombre().equals("AGE") || cliente.getNombre().equals("ASE")) {
						List<CfdiPrincipal> cfdiPrincipal = cfdiPrincipalService
								.findAllCfdiNoStatusByCliente(cliente.getId());
						for (CfdiPrincipal cfdi : cfdiPrincipal) {
							LOG.info("INICIANDO ENVIO DE DATOS A WEB SERVICE CHILENOS");
							consumirSoap.connectionSoapWs(cfdi);

						}
					}
				}
				LOG.info("FINALIZADO...");
			} catch (Exception e) {
				LOG.error("Error al momento de la ejecución: " + e.getMessage());
			}
		}

	}
}
