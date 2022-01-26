package com.gisconsultoria.centrocfdi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gisconsultoria.centrocfdi.dao.IStatusWsDao;
import com.gisconsultoria.centrocfdi.model.StatusWs;

@Service
public class StatusWsServiceImpl implements IStatusWsService{

	@Autowired
	private IStatusWsDao statusWsDao;
	
	@Override
	public void save(StatusWs statusWs) {
		statusWsDao.save(statusWs);
		
	}

	@Override
	public StatusWs findStatusByIdCfdi(Long idCfdi) {
		return statusWsDao.findStatusByIdCfdi(idCfdi);
	}

	@Override
	public List<StatusWs> findAllWs() {
		return (List<StatusWs>) statusWsDao.findAll();
	}

}
