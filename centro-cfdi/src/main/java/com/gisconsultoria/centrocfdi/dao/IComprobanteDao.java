package com.gisconsultoria.centrocfdi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.gisconsultoria.centrocfdi.model.Clientes;
import com.gisconsultoria.centrocfdi.model.Comprobantes;

public interface IComprobanteDao extends CrudRepository<Comprobantes, Long>{
	
	
	@Query("select c from Comprobantes c where c.nombre=:nombre")
	public Comprobantes getComprobanteByName(@Param("nombre")String nombre);
	
	@Query(value = "select c.nombre from cat_usuarios u "
			+ "join rel_usuarios_comprobantes relUC on (u.id_usuario=relUC.id_usuario) "
			+ "join cat_comprobantes c on (relUC.id_comprobante=c.id) "
			+ "where u.username=?1",nativeQuery = true)
	public List<String> getNameComprobanteByUsername(String username);
	
	@Query("select c from Comprobantes c where c.nombre IN :comprobante")
	 public List<Comprobantes> getComprobanteWithIn(@Param("comprobante")List<String> comprobante);

	
}
