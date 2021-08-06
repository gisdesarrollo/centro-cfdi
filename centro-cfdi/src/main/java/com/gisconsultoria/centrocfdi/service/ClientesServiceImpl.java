package com.gisconsultoria.centrocfdi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gisconsultoria.centrocfdi.dao.IClientesDao;
import com.gisconsultoria.centrocfdi.model.Clientes;
import com.gisconsultoria.centrocfdi.model.dto.ClientesDto;

@Service
public class ClientesServiceImpl implements IClientesService{

	@Autowired
	private IClientesDao clienteDao;
	
	@Override
	public List<Clientes> getActiveClientes() {
		return clienteDao.getActiveClientes();
	}

	@Override
	@Transactional
	public void updateFechaInicialClienteById(Date fecha, Long id) {
		clienteDao.updateFechaInicialClienteById(fecha, id);
		
	}

	@Override
	public Clientes findById(Long id) {
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	public Clientes getClienteByRfc(String rfc) {
		return clienteDao.getClienteByRfc(rfc);
	}

	@Override
	public void save(Clientes cliente) {
		clienteDao.save(cliente);
	}

	/*@Override
	public List<Clientes> getClientes() {
		return  empresasDao.getEmpresas();
	}*/

	@Override
	public void delete(Long id) {
		clienteDao.deleteById(id);
		
	}

	@Override
	public List<Long> getIdClienteByUsername(String username) {
		return clienteDao.getIdClienteByUsername(username);
	}

	@Override
	public List<Clientes> getClientesByUsername(String username) {
		return clienteDao.getClientesByUsername(username);
	}

	@Override
	public List<Clientes> getClienteWithIn(List<Long> cliente) {
		return clienteDao.getClienteWithIn(cliente);
	}

}
