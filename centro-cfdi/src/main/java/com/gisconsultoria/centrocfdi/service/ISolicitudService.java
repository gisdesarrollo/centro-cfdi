package com.gisconsultoria.centrocfdi.service;

import java.util.List;

import com.gisconsultoria.centrocfdi.model.Solicitudes;


public interface ISolicitudService {
	
	public void save(Solicitudes solicitud);
	
	public List<Solicitudes> getSolicitudesWithEstatusEnProceso();
	
	public void deleteById(Long id);

}
