package com.gisconsultoria.centrocfdi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.gisconsultoria.centrocfdi.model.StatusWs;

public interface IStatusWsDao extends CrudRepository<StatusWs, Long>{

	@Query(value = "select s.* from status_ws s where s.id_cfdi = ?1",nativeQuery = true)
	public StatusWs findStatusByIdCfdi(Long idCfdi);
	
	@Query(value = "select * from  status_ws s where s.status='ENPROCESO'",nativeQuery = true)
	public List<StatusWs> findAllWs();
}
