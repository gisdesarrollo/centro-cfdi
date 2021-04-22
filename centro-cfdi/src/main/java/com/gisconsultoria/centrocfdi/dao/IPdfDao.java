package com.gisconsultoria.centrocfdi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.gisconsultoria.centrocfdi.model.Pdf;
import com.gisconsultoria.centrocfdi.model.Xml;

public interface IPdfDao extends CrudRepository<Pdf, Long> {

	@Query("select p from Pdf p where p.cfdi.id = :idCfdi")
	public Pdf getFileByIdCfdi(@Param("idCfdi") Long idCfdi);
	
	@Query(value="select p.* from cat_clientes c "
			+ "join m_cfdi_33 f on (c.Id=f.id_cliente)"
			+ "join cat_pdf p on (f.id_cfdi = p.id_cfdi)"
			+ "where c.Id = ?1",nativeQuery = true)
	public List<Pdf> getAllFilePdfByCliente(Long id);
	
	@Query(value="select p.* from m_cfdi_33 c "
			+ "join cat_pdf p on (c.id_cfdi=p.id_cfdi) "
			+ "where c.fecha between ?1 and ?2 and c.tipodecomprobante like %?3% "
			+ "and c.id_cliente like %?4%",nativeQuery = true)
	public List<Pdf> getAllFilePdfByParameter(String fechaInicial,String fechaFinal ,
												String tipoComprobante,String clienteId);
}
