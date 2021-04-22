package com.gisconsultoria.centrocfdi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gisconsultoria.centrocfdi.dao.ICfdiPrincipalDao;
import com.gisconsultoria.centrocfdi.model.CfdiPrincipal;

@Service
public class CfdiPrincipalServiceImpl implements ICfdiPrincipalService {

	@Autowired
	private ICfdiPrincipalDao cfdiPrincipalDao;
	
	@Override
	public List<CfdiPrincipal> findFirstCfdiByUuid(String uuid) {
		return cfdiPrincipalDao.findFirstCfdiByUuid(uuid);
	}

	@Override
	public void save(CfdiPrincipal cfdiPrincipal) {
		cfdiPrincipalDao.save(cfdiPrincipal);
		
	}

	@Override
	public List<CfdiPrincipal> findAll() {
		return (List<CfdiPrincipal>) cfdiPrincipalDao.findAll();
	}


	@Override
	public List<CfdiPrincipal> findCfdiByFecha(String fechaInicial, String fechafinal, String tipoComprobante,
			String clienteId) {
		return cfdiPrincipalDao.findCfdiByFecha(fechaInicial, fechafinal, tipoComprobante, clienteId);
	}

	@Override
	public List<CfdiPrincipal> findCfdiBySearch(String searchParams,String fechaIncial,String fechafinal,String clienteId
			,String tipocomprobante ){
		return cfdiPrincipalDao.findCfdiBySearch(searchParams, fechaIncial, fechafinal, clienteId, tipocomprobante);
	}

	@Override
	public int countSearch(String fechaInicial,String fechafinal,String tipoComprobante,String clienteId) {
		return cfdiPrincipalDao.countSearch(fechaInicial, fechafinal, tipoComprobante, clienteId);
	}

	@Override
	public List<CfdiPrincipal> findCfdiByClienteAndComprobante(List<Long> cliente, List<String> comprobante) {
		return cfdiPrincipalDao.findCfdiByClienteAndComprobante(cliente, comprobante);
	}

	@Override
	public List<CfdiPrincipal> findCfdiByClienteAByComprobanteAndFecha(List<Long> cliente, List<String> comprobante,
			Date fechaInicial, Date fechafinal) {
		return cfdiPrincipalDao.findCfdiByClienteAByComprobanteAndFecha(cliente, comprobante, fechaInicial, fechafinal);
	}

	@Override
	public List<CfdiPrincipal> findCfdiBySearchWitnIn(String searchParams, String fechaIncial, String fechafinal,
			List<Long> cliente, List<String> comprobante) {
		return cfdiPrincipalDao.findCfdiBySearchWitnIn(searchParams, fechaIncial, fechafinal, cliente, comprobante);
	}

	@Override
	public int countSearchWithIn(String fechaInicial, String fechafinal, List<Long> cliente, List<String> comprobante) {
		return cfdiPrincipalDao.countSearchWithIn(fechaInicial, fechafinal, cliente, comprobante);
	}

	@Override
	public List<CfdiPrincipal> findCfdiBySearchByClienteAndComprobante(String searchParams, List<Long> cliente,
			List<String> comprobante) {
		return cfdiPrincipalDao.findCfdiBySearchByClienteAndComprobante(searchParams, cliente, comprobante);
	}

	@Override
	public int countSearchByClienteAndComprobante(List<Long> cliente, List<String> comprobante) {
		return cfdiPrincipalDao.countSearchByClienteAndComprobante(cliente, comprobante);
	}


	

	

}
