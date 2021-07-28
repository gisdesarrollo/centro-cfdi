package com.gisconsultoria.centrocfdi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public Page<CfdiPrincipal> findCfdiBySearch(String searchParams,String fechaIncial,String fechafinal,String clienteId
			,String tipocomprobante,Pageable pageable ){
		return cfdiPrincipalDao.findCfdiBySearch(searchParams, fechaIncial, fechafinal, clienteId, tipocomprobante,pageable);
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
	public Page<CfdiPrincipal> findCfdiByClienteAByComprobanteAndFecha(List<Long> cliente, List<String> comprobante,
			String fechaInicial, String fechafinal,Pageable pageable) {
		return cfdiPrincipalDao.findCfdiByClienteAByComprobanteAndFecha(cliente, comprobante, fechaInicial, fechafinal,pageable);
	}

	@Override
	public Page<CfdiPrincipal> findCfdiBySearchWitnIn(String searchParams, String fechaIncial, String fechafinal,
			List<Long> cliente, List<String> comprobante,Pageable pageable) {
		return cfdiPrincipalDao.findCfdiBySearchWitnIn(searchParams, fechaIncial, fechafinal, cliente, comprobante,pageable);
	}

	/*@Override
	public int countSearchWithIn(String searchParams,String fechaInicial, String fechafinal, List<Long> cliente, List<String> comprobante) {
		return cfdiPrincipalDao.countSearchWithIn(searchParams,fechaInicial, fechafinal, cliente, comprobante);
	}*/

	@Override
	public Page<CfdiPrincipal> findCfdiBySearchByClienteAndComprobante(String searchParams, List<Long> cliente,
			List<String> comprobante,Pageable pageable) {
		return cfdiPrincipalDao.findCfdiBySearchByClienteAndComprobante(searchParams, cliente, comprobante,pageable);
	}

	/*@Override
	public int countSearchByClienteAndComprobante(String searchParams,List<Long> cliente, List<String> comprobante) {
		return cfdiPrincipalDao.countSearchByClienteAndComprobante(searchParams,cliente, comprobante);
	}*/

	

	@Override
	public Page<CfdiPrincipal> findCfdiByFechaAndComprobante(String fechaInicial, String fechafinal,
			String tipoComprobante,Pageable pageable) {
		return cfdiPrincipalDao.findCfdiByFechaAndComprobante(fechaInicial, fechafinal, tipoComprobante,pageable);
	}

	@Override
	public Page<CfdiPrincipal> findCfdiByFechaAndClienteAndComprobante(String fechaInicial, String fechafinal, String clienteId,String comprobante,Pageable pageable) {
		return cfdiPrincipalDao.findCfdiByFechaAndClienteAndComprobante(fechaInicial, fechafinal, clienteId,comprobante,pageable);
	}

	@Override
	public Page<CfdiPrincipal> findCfdiByClienteAndComprobanteWithCount(List<Long> cliente, List<String> comprobante,Pageable pageable) {
		return cfdiPrincipalDao.findCfdiByClienteAndComprobanteWithCount(cliente, comprobante,pageable);
	}

	@Override
	public int countSearchByClienteAndComprobanteWithFecha(String fechaInicial, String fechaFinal, List<Long> cliente,
			List<String> comprobante) {
		return cfdiPrincipalDao.countSearchByClienteAndComprobanteWithFecha(fechaInicial, fechaFinal, cliente, comprobante);
	}

	@Override
	public int countCfdiByClienteAndComprobante(List<Long> cliente, List<String> comprobante) {
		return cfdiPrincipalDao.countCfdiByClienteAndComprobante(cliente, comprobante);
	}

	@Override
	public int countByFechaByComprobante(String fechaInicial, String fechafinal, String tipoComprobante) {
		return cfdiPrincipalDao.countByFechaByComprobante(fechaInicial, fechafinal, tipoComprobante);
	}

	/*@Override
	public int countSearchCfdiTotal(String searchParams, String fechaIncial, String fechafinal, String clienteId,
			String tipocomprobante) {
		return cfdiPrincipalDao.countSearchCfdiTotal(searchParams, fechaIncial, fechafinal, clienteId, tipocomprobante);
	}*/

	@Override
	public int countCfdiByFechaAndClienteAndComprobante(String fechaInicial, String fechafinal, String clienteId,String comprobante) {
		return cfdiPrincipalDao.countCfdiByFechaAndClienteAndComprobante(fechaInicial, fechafinal, clienteId,comprobante);
	}

	@Override
	public Long getEstadisticaByIdcliente(Long idCliente,String tipoComp ) {
		return cfdiPrincipalDao.getEstadisticaByIdcliente(idCliente,tipoComp);
	}


	

	

}
