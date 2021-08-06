package com.gisconsultoria.centrocfdi.sheduled;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.gisconsultoria.centrocfdi.model.Clientes;
import com.gisconsultoria.centrocfdi.service.IClientesService;
import com.gisconsultoria.centrocfdi.services.GetCfdiRecibidos;
import com.gisconsultoria.centrocfdi.services.GisconsultoriaXsaService;
import com.gisconsultoria.centrocfdi.util.GisConsultoriaReadXmlFile;


//@Configuration
//@EnableScheduling
public class XsaConnector {

	private static final Logger LOG = LoggerFactory.getLogger(XsaConnector.class);
	
	@Autowired
	private IClientesService clienteService;
	
	@Autowired
	private GisconsultoriaXsaService xsaService;
	
	@Autowired
	private GisConsultoriaReadXmlFile gisConsultoriaReadXmlFile;
	
	@Autowired
	private GetCfdiRecibidos cfdiRecibidos;
	
	@Value("${path.archivo.archivosXml}")
    private File path;
 
	// @Scheduled(cron = "0 0 0 * * *", zone = "America/Mexico_City")
	 public void ProcesandoFoliofiscal() {
		 
		// cfdiRecibidos.getcfdiRecibidos();
		 List<Clientes> clientes = clienteService.getActiveClientes();
	        ZoneId zoneId = ZoneId.of("America/Mexico_City");
	        Date fechaActual = Date.from(ZonedDateTime.now(zoneId).toInstant());
	        Calendar calendar = Calendar.getInstance();
	        
	        if(clientes != null){
	            try{
	                LOG.info("DESCARGANDO ARCHIVOS DEL SERVLET");
	              for(Clientes sucursal : clientes){
	                        if(fechaActual.after(sucursal.getFechaInicial())){
	                            LocalDate fechaInicial = sucursal.getFechaInicial().toInstant()
	                                                             .atZone(zoneId)
	                                                             .toLocalDate();
	                            LocalDate fechaFinal = fechaActual.toInstant()
	                                                              .atZone(zoneId)
	                                                              .toLocalDate();

	                            long totalDias = ChronoUnit.DAYS.between(fechaFinal, fechaInicial);
	                            calendar.setTime(sucursal.getFechaInicial());
	                            for(long dias = 0; totalDias < dias; totalDias++){
	                                calendar.add(Calendar.DAY_OF_MONTH, (int)dias);
	                                Clientes readSucursal = clienteService.findById(sucursal.getId());
	                                LocalDate fechaInicial2 = readSucursal.getFechaInicial()
	                                                           .toInstant()
	                                                           .atZone(zoneId)
	                                                           .toLocalDate();

	                                if(fechaInicial.compareTo(fechaInicial2) < 0){
	                                    xsaService.ObtenerArchivos(readSucursal.getId(),
	                                            readSucursal.getFechaInicial());
	                                }else{
	                                    xsaService.ObtenerArchivos(sucursal.getId(),
	                                            sucursal.getFechaInicial());
	                                }
	                            }
	                        }
	                	}
	             for(Clientes cliente : clientes) {
	            	
	            	 LOG.info("OBTENIENDO ARCHIVOS EXTRAIDOS EN LA CARPETA " +cliente.getNombre());
	            	 File pathCliente = new  File(path.toString().concat("\\").concat(cliente.getNombre())); 
	            	 gisConsultoriaReadXmlFile.readXmlFile(pathCliente);
	            	 
	             }
	             LOG.info("EXTRACION DE ARCHIVOS A FINALIZADO");
	            }catch (Exception e) {
					LOG.error("Error al momento de la ejecución: " + e.getMessage());
				}
	        }

	 }
}
