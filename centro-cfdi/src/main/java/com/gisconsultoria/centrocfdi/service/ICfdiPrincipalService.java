package com.gisconsultoria.centrocfdi.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import com.gisconsultoria.centrocfdi.model.CfdiPrincipal;

public interface ICfdiPrincipalService {
	
	public List<CfdiPrincipal> findFirstCfdiByUuid(String uuid);

	public void save(CfdiPrincipal cfdiPrincipal);
	
	public List<CfdiPrincipal> findAll();
	
	public List<CfdiPrincipal> findCfdiByFecha(String fechaInicial,String fechafinal,String tipoComprobante,
			   String clienteId );
	
	public List<CfdiPrincipal> findCfdiBySearch(String searchParams,String fechaIncial,String fechafinal,String clienteId
			,String tipocomprobante );
	
	public List<CfdiPrincipal> findCfdiBySearchWitnIn(String searchParams,String fechaIncial,String fechafinal,
			List<Long> cliente,List<String> comprobante );
	
	public List<CfdiPrincipal> findCfdiBySearchByClienteAndComprobante(String searchParams,List<Long> cliente,List<String> comprobante );
	
	public int countSearch(String fechaInicial,String fechafinal,String tipoComprobante,String clienteId);
	
	public int countSearchWithIn(String fechaInicial,String fechafinal,List<Long> cliente,List<String> comprobante);
	
	public List<CfdiPrincipal> findCfdiByClienteAndComprobante(List<Long> cliente,List<String> comprobante );
	
	public List<CfdiPrincipal> findCfdiByClienteAByComprobanteAndFecha(List<Long> cliente,List<String> comprobante,
			   Date fechaInicial ,Date fechafinal); 
	
	public int countSearchByClienteAndComprobante(List<Long> cliente,List<String> comprobante );

}
