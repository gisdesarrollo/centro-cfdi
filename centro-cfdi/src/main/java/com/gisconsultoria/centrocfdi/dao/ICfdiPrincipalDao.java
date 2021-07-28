package com.gisconsultoria.centrocfdi.dao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gisconsultoria.centrocfdi.model.CfdiPrincipal;

public interface ICfdiPrincipalDao extends DataTablesRepository<CfdiPrincipal, Long> {

	@Query("select c from CfdiPrincipal c where c.tfdUuid = :uuid")
    public List<CfdiPrincipal> findFirstCfdiByUuid(@Param("uuid") String uuid);
	
	
	@Query(value="select c.* from m_cfdi_33 c where c.fecha between ?1 and ?2 and c.id_cliente in(?3) and c.tipodecomprobante like %?4%",
			countQuery = "select count(*) from m_cfdi_33 c where c.fecha between ?1 and ?2 and c.id_cliente in(?3) and c.tipodecomprobante like %?4%",nativeQuery = true)
	public Page<CfdiPrincipal> findCfdiByFechaAndClienteAndComprobante(String fechaInicial,
											   String fechafinal,
											   String clienteId,
											   String comprobante,Pageable pageable);
	
	@Query(value="select count(*) from m_cfdi_33 c where c.fecha between ?1 and ?2 and c.id_cliente in(?3) and c.tipodecomprobante like %?4% ",nativeQuery = true)
	public int countCfdiByFechaAndClienteAndComprobante(String fechaInicial,
											   String fechafinal,
											   String clienteId,
											   String comprobante);
	
	

	@Query(value="select c.* from m_cfdi_33 c where c.fecha between ?1 and ?2 and c.tipodecomprobante like %?3% ",
			countQuery ="select count(*) from m_cfdi_33 c where c.fecha between ?1 and ?2 and c.tipodecomprobante like %?3%" ,nativeQuery = true)
	public Page<CfdiPrincipal> findCfdiByFechaAndComprobante(String fechaInicial,
											   String fechafinal,
											   String tipoComprobante,Pageable pageable);
	
	@Query(value="select count(*) from m_cfdi_33 c where c.fecha between ?1 and ?2 and c.tipodecomprobante like %?3% ",nativeQuery = true)
	public int countByFechaByComprobante(String fechaInicial,
											   String fechafinal,
											   String tipoComprobante);
	
	@Query(value ="select c.* from m_cfdi_33 c join cat_clientes cli on (c.id_cliente=cli.Id) "
			+ "where (cli.RazonSocial like %?1%  or c.tfd_uuid like %?1% or cli.Rfc like %?1% "
			+ "or c.serie like %?1% or c.folio like %?1% or c.total like %?1% or c.fecha like %?1%) "
			+ "and c.fecha between ?2 and ?3 and c.id_cliente like %?4% and c.tipodecomprobante like %?5%",
			countQuery ="select count(*) from m_cfdi_33 c join cat_clientes cli on (c.id_cliente=cli.Id) "
					+ "where (cli.RazonSocial like %?1%  or c.tfd_uuid like %?1% or cli.Rfc like %?1% "
					+ "or c.serie like %?1% or c.folio like %?1% or c.total like %?1% or c.fecha like %?1%) "
					+ "and c.fecha between ?2 and ?3 and c.id_cliente like %?4% and c.tipodecomprobante like %?5%" ,nativeQuery = true)
	public Page<CfdiPrincipal> findCfdiBySearch(String searchParams,String fechaIncial,String fechafinal,String clienteId
												,String tipocomprobante,Pageable pageable);
	
	/*@Query(value ="select count(*) from m_cfdi_33 c join cat_clientes cli on (c.id_cliente=cli.Id) "
			+ "where (cli.RazonSocial like %?1%  or c.tfd_uuid like %?1% or cli.Rfc like %?1% "
			+ "or c.serie like %?1% or c.folio like %?1% or c.total like %?1% or c.fecha like %?1%) "
			+ "and c.fecha between ?2 and ?3 and c.id_cliente like %?4% and c.tipodecomprobante like %?5%", nativeQuery = true)
	public int countSearchCfdiTotal(String searchParams,String fechaIncial,String fechafinal,String clienteId
												,String tipocomprobante );*/
	
	@Query(value="select count(c.id_cfdi) from m_cfdi_33 c where c.fecha between ?1 and ?2 and c.tipodecomprobante like %?3%"
			+ " and c.id_cliente like %?4%",nativeQuery = true)
	public int countSearch(String fechaInicial,String fechafinal,String tipoComprobante,String clienteId);
	
	@Query(value ="select c.* from m_cfdi_33 c join cat_clientes cli on (c.id_cliente=cli.Id) "
			+ "where (cli.RazonSocial like %?1%  or c.tfd_uuid like %?1% or cli.Rfc like %?1% "
			+ "or c.serie like %?1% or c.folio like %?1% or c.total like %?1% or c.fecha like %?1%) "
			+ "and c.fecha between ?2 and ?3 and c.id_cliente in(?4) and c.tipodecomprobante in(?5)",
			countQuery = "select count(*) from m_cfdi_33 c join cat_clientes cli on (c.id_cliente=cli.Id) "
					+ "where (cli.RazonSocial like %?1%  or c.tfd_uuid like %?1% or cli.Rfc like %?1% "
					+ "or c.serie like %?1% or c.folio like %?1% or c.total like %?1% or c.fecha like %?1%) "
					+ "and c.fecha between ?2 and ?3 and c.id_cliente in(?4) and c.tipodecomprobante in(?5)" ,nativeQuery = true)
	public Page<CfdiPrincipal> findCfdiBySearchWitnIn(String searchParams,String fechaIncial,String fechafinal,
											List<Long> cliente,List<String> comprobante,Pageable pageable);
	
	@Query(value ="select c.* from m_cfdi_33 c join cat_clientes cli on (c.id_cliente=cli.Id) "
			+ "where (cli.RazonSocial like %?1%  or c.tfd_uuid like %?1% or cli.Rfc like %?1% "
			+ "or c.serie like %?1% or c.folio like %?1% or c.total like %?1% or c.fecha like %?1%) "
			+ "and c.id_cliente in(?2) and c.tipodecomprobante in(?3)",
			countQuery = "select count(*) from m_cfdi_33 c join cat_clientes cli on (c.id_cliente=cli.Id) "
					+ "where (cli.RazonSocial like %?1%  or c.tfd_uuid like %?1% or cli.Rfc like %?1% "
					+ "or c.serie like %?1% or c.folio like %?1% or c.total like %?1% or c.fecha like %?1%) "
					+ "and c.id_cliente in(?2) and c.tipodecomprobante in(?3)",nativeQuery = true)
	public Page<CfdiPrincipal> findCfdiBySearchByClienteAndComprobante(String searchParams,List<Long> cliente,List<String> comprobante,Pageable pageable);
	
	
	
	/*@Query(value ="select count(*) from m_cfdi_33 c join cat_clientes cli on (c.id_cliente=cli.Id) "
			+ "where (cli.RazonSocial like %?1%  or c.tfd_uuid like %?1% or cli.Rfc like %?1% "
			+ "or c.serie like %?1% or c.folio like %?1% or c.total like %?1% or c.fecha like %?1%) "
			+ "and c.fecha between ?2 and ?3 and c.id_cliente in(?4) and c.tipodecomprobante in(?5)", nativeQuery = true)
	public int countSearchWithIn(String searchParams, String fechaInicial,String fechafinal,List<Long> cliente,List<String> comprobante);
	*/
	
	@Query("select c from CfdiPrincipal c where c.empresaId.id IN :cliente  and c.tipoComprobante IN :comprobante")
	public List<CfdiPrincipal> findCfdiByClienteAndComprobante(@Param("cliente")List<Long> cliente,@Param("comprobante")List<String> comprobante ); 
	
	@Query(value="select c.* from m_cfdi_33 c where c.id_cliente in(?1)  and c.tipodecomprobante in(?2)",
			countQuery = "select count(*) from  m_cfdi_33 c where c.id_cliente in(?1)  and c.tipodecomprobante in(?2)",nativeQuery = true)
	public Page<CfdiPrincipal> findCfdiByClienteAndComprobanteWithCount(List<Long> cliente,List<String> comprobante,Pageable pageable); 
	
	@Query(value="select count(*) from  m_cfdi_33 c where c.fecha between ?1 and ?2 and c.id_cliente in(?3)  and c.tipodecomprobante in(?4)",nativeQuery = true)
	public int countSearchByClienteAndComprobanteWithFecha(String fechaInicial,String fechaFinal,List<Long> cliente,List<String> comprobante);
	
	/*@Query(value ="select count(*) from m_cfdi_33 c join cat_clientes cli on (c.id_cliente=cli.Id) "
			+ "where (cli.RazonSocial like %?1%  or c.tfd_uuid like %?1% or cli.Rfc like %?1% "
			+ "or c.serie like %?1% or c.folio like %?1% or c.total like %?1% or c.fecha like %?1%) "
			+ "and c.id_cliente in(?2) and c.tipodecomprobante in(?3)", nativeQuery = true)
	public int countSearchByClienteAndComprobante(String searchParams,List<Long> cliente,List<String> comprobante);
	*/
	@Query(value="select count(*) from  m_cfdi_33 c where c.id_cliente in(?1)  and c.tipodecomprobante in(?2)",nativeQuery = true)
	public int countCfdiByClienteAndComprobante(List<Long> cliente,List<String> comprobante);
	
	@Query(value="select c.* from  m_cfdi_33 c where c.id_cliente IN(?1)  and c.tipodecomprobante IN(?2) and c.fecha between ?3 and ?4 ",
			countQuery = "select count(*) from  m_cfdi_33 c where c.id_cliente IN(?1)  and c.tipodecomprobante IN(?2) and c.fecha between ?3 and ?4",nativeQuery = true)
	public Page<CfdiPrincipal> findCfdiByClienteAByComprobanteAndFecha(List<Long> cliente,List<String> comprobante,
																	   String fechaInicial ,String fechafinal,Pageable pageable); 
	
	@Query( value="select count(c.tipodecomprobante) "
			+ "from centrocfdi.m_cfdi_33 c where c.id_cliente=?1 "
			+ "and c.tipodecomprobante in(?2)", nativeQuery = true)
	
	public Long getEstadisticaByIdcliente(Long idCliente,String tipoComp);
	
	/*consulta para contar por tipoComprobante*/
	/*select 
(select  count(*) from centrocfdi.m_cfdi_33 where id_cliente=23 and  tipodecomprobante in('I'))as Ingresos,
(select  count(*) from centrocfdi.m_cfdi_33 where id_cliente=23 and  tipodecomprobante in('E'))as Egresos,
(select  count(*) from centrocfdi.m_cfdi_33 where id_cliente=23 and  tipodecomprobante in('T'))as Traslado    
from centrocfdi.m_cfdi_33 c where c.id_cliente=23 
group by c.id_cliente ;*/
	
}
	
	
