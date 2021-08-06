package com.gisconsultoria.centrocfdi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gisconsultoria.centrocfdi.dao.ISolicitudDao;
import com.gisconsultoria.centrocfdi.model.Solicitudes;

@Service
public class SolicitudesServiceImpl implements ISolicitudService {

	@Autowired
	private ISolicitudDao solicitudDao;
	
	@Override
	public void save(Solicitudes solicitud) {
		solicitudDao.save(solicitud);
	}

	@Override
	public List<Solicitudes> getSolicitudesWithEstatusEnProceso() {
		return solicitudDao.getSolicitudesWithEstatusEnProceso();
	}

	@Override
	public void deleteById(Long id) {
		solicitudDao.deleteById(id);
		
	}

	

}
