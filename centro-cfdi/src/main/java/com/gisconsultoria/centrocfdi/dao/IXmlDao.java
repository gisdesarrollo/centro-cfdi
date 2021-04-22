package com.gisconsultoria.centrocfdi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.gisconsultoria.centrocfdi.model.Xml;

public interface IXmlDao extends CrudRepository<Xml, Long> {

	@Query("select f from Xml f where f.cfdi.id = :idCfdi")
	public Xml getFileByIdCfdi(@Param("idCfdi") Long idCfdi);
	
	@Query(value="select x.* from cat_clientes c "
			+ "join m_cfdi_33 f on (c.Id=f.id_cliente)"
			+ "join cat_xml x on (f.id_cfdi = x.id_cfdi)"
			+ "where c.Id = ?1",nativeQuery = true)
	public List<Xml> getAllFileXmlByCliente(Long id);
	
	@Query(value="select x.* from m_cfdi_33 c "
			+ "join cat_xml x on (c.id_cfdi=x.id_cfdi) "
			+ "where c.fecha between ?1 and ?2 and c.tipodecomprobante like %?3% "
			+ "and c.id_cliente like %?4%",nativeQuery = true)
	public List<Xml> getAllFileXmlByParameter(String fechaInicial,String fechaFinal ,
												String tipoComprobante,String clienteId);
}
