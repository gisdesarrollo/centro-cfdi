package com.gisconsultoria.centrocfdi.service;

import java.util.List;

import com.gisconsultoria.centrocfdi.model.StatusWs;

public interface IStatusWsService {
	
	public void save(StatusWs statusWs);
	
	public StatusWs findStatusByIdCfdi(Long idCfdi);
	
	public List<StatusWs> findAllWs();

}
