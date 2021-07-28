package com.gisconsultoria.centrocfdi.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.gisconsultoria.centrocfdi.model.CfdiPrincipal;

public interface ICfdiPrincipalService {
	
	public List<CfdiPrincipal> findFirstCfdiByUuid(String uuid);

	public void save(CfdiPrincipal cfdiPrincipal);
	
	public List<CfdiPrincipal> findAll();
	
	//public List<CfdiPrincipal> findCfdiByFecha(String fechaInicial,String fechafinal);
	
	/*public List<CfdiPrincipal> findCfdiByFechaAndComprobante(String fechaInicial,
			   String fechafinal,
			   String tipoComprobante);
	*/
	public Page<CfdiPrincipal> findCfdiByFechaAndClienteAndComprobante(String fechaInicial,
			   String fechafinal,
			   String clienteId,String comprobante,Pageable pageable);
	
	public int countCfdiByFechaAndClienteAndComprobante(String fechaInicial,
			   String fechafinal,
			   String clienteId,String comprobante);
	
	public Page<CfdiPrincipal> findCfdiByFechaAndComprobante(String fechaInicial,
			   String fechafinal,
			   String tipoComprobante ,Pageable pageable);
	
	public int countByFechaByComprobante(String fechaInicial,
			   String fechafinal,
			   String tipoComprobante);
	
	public Page<CfdiPrincipal> findCfdiBySearch(String searchParams,String fechaIncial,String fechafinal,String clienteId
			,String tipocomprobante,Pageable pageable );
	
	public Page<CfdiPrincipal> findCfdiBySearchWitnIn(String searchParams,String fechaIncial,String fechafinal,
			List<Long> cliente,List<String> comprobante,Pageable pageable );
	
	public Page<CfdiPrincipal> findCfdiBySearchByClienteAndComprobante(String searchParams,List<Long> cliente,List<String> comprobante,Pageable pageable);
	
	/*public int countSearchCfdiTotal(String searchParams,String fechaIncial,String fechafinal,String clienteId
			,String tipocomprobante );
	*/
	public int countSearch(String fechaInicial,String fechafinal,String tipoComprobante,String clienteId);
	
	//public int countSearchWithIn(String searchParams,String fechaInicial,String fechafinal,List<Long> cliente,List<String> comprobante);
	
	public List<CfdiPrincipal> findCfdiByClienteAndComprobante(List<Long> cliente,List<String> comprobante );
	
	public Page<CfdiPrincipal> findCfdiByClienteAByComprobanteAndFecha(List<Long> cliente,List<String> comprobante,
			   String fechaInicial ,String fechafinal,Pageable pageable); 
	
	//public int countSearchByClienteAndComprobante(String searchParams,List<Long> cliente,List<String> comprobante );
	
	public int countCfdiByClienteAndComprobante(List<Long> cliente,List<String> comprobante);
	
	public Page<CfdiPrincipal> findCfdiByClienteAndComprobanteWithCount(List<Long> cliente,List<String> comprobante,Pageable pageable);
	
	public int countSearchByClienteAndComprobanteWithFecha(String fechaInicial,String fechaFinal,List<Long> cliente,List<String> comprobante);
	
	public Long getEstadisticaByIdcliente(Long idCliente, String tipoComp);
}
