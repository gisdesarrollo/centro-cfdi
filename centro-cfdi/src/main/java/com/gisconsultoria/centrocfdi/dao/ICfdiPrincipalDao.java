package com.gisconsultoria.centrocfdi.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gisconsultoria.centrocfdi.model.CfdiPrincipal;

public interface ICfdiPrincipalDao extends DataTablesRepository<CfdiPrincipal, Long> {

	@Query("select c from CfdiPrincipal c where c.tfdUuid = :uuid")
    public List<CfdiPrincipal> findFirstCfdiByUuid(@Param("uuid") String uuid);
	
	@Query(value="select c.* from m_cfdi_33 c where c.fecha between ?1 and ?2 and c.tipodecomprobante like %?3%"
			+ " and c.id_cliente like %?4%",nativeQuery = true)
	public List<CfdiPrincipal> findCfdiByFecha(String fechaInicial,
											   String fechafinal,
											   String tipoComprobante,
											   String clienteId );
	
	@Query(value ="select c.* from m_cfdi_33 c join cat_clientes cli on (c.id_cliente=cli.Id) "
			+ "where (cli.RazonSocial like %?1%  or c.tfd_uuid like %?1% or cli.Rfc like %?1% "
			+ "or c.serie like %?1% or c.folio like %?1% or c.total like %?1% or c.fecha like %?1%) "
			+ "and c.fecha between ?2 and ?3 and c.id_cliente like %?4% and c.tipodecomprobante like %?5%", nativeQuery = true)
	public List<CfdiPrincipal> findCfdiBySearch(String searchParams,String fechaIncial,String fechafinal,String clienteId
												,String tipocomprobante );
	
	@Query(value ="select c.* from m_cfdi_33 c join cat_clientes cli on (c.id_cliente=cli.Id) "
			+ "where (cli.RazonSocial like %?1%  or c.tfd_uuid like %?1% or cli.Rfc like %?1% "
			+ "or c.serie like %?1% or c.folio like %?1% or c.total like %?1% or c.fecha like %?1%) "
			+ "and c.fecha between ?2 and ?3 and c.id_cliente in(?4) and c.tipodecomprobante in(?5)", nativeQuery = true)
	public List<CfdiPrincipal> findCfdiBySearchWitnIn(String searchParams,String fechaIncial,String fechafinal,
											List<Long> cliente,List<String> comprobante );
	
	@Query(value ="select c.* from m_cfdi_33 c join cat_clientes cli on (c.id_cliente=cli.Id) "
			+ "where (cli.RazonSocial like %?1%  or c.tfd_uuid like %?1% or cli.Rfc like %?1% "
			+ "or c.serie like %?1% or c.folio like %?1% or c.total like %?1% or c.fecha like %?1%) "
			+ "and c.id_cliente in(?2) and c.tipodecomprobante in(?3)", nativeQuery = true)
	public List<CfdiPrincipal> findCfdiBySearchByClienteAndComprobante(String searchParams,List<Long> cliente,List<String> comprobante );
	
	@Query(value="select count(c.id_cfdi) from m_cfdi_33 c where c.fecha between ?1 and ?2 and c.tipodecomprobante like %?3%"
			+ " and c.id_cliente like %?4%",nativeQuery = true)
	public int countSearch(String fechaInicial,String fechafinal,String tipoComprobante,String clienteId);
	
	@Query(value="select count(c.id_cfdi) from m_cfdi_33 c where c.fecha between ?1 and ?2 and c.id_cliente in(?3) "
			+ "and c.tipodecomprobante in(?4)",nativeQuery = true)
	public int countSearchWithIn(String fechaInicial,String fechafinal,List<Long> cliente,List<String> comprobante);
	
	@Query("select c from CfdiPrincipal c where c.empresaId.id IN :cliente  and c.tipoComprobante IN :comprobante")
	public List<CfdiPrincipal> findCfdiByClienteAndComprobante(@Param("cliente")List<Long> cliente,@Param("comprobante")List<String> comprobante ); 
	
	@Query("select count(c.id)from CfdiPrincipal c where c.empresaId.id IN :cliente  and c.tipoComprobante IN :comprobante")
	public int countSearchByClienteAndComprobante(@Param("cliente")List<Long> cliente,@Param("comprobante")List<String> comprobante );
	
	@Query("select c from CfdiPrincipal c where c.empresaId.id IN ?1  and c.tipoComprobante IN ?2 and c.fecha between ?3 and ?4 ")
	public List<CfdiPrincipal> findCfdiByClienteAByComprobanteAndFecha(List<Long> cliente,List<String> comprobante,
																	   Date fechaInicial ,Date fechafinal); 
	
	
}
	
	
