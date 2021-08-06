package com.gisconsultoria.centrocfdi.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import com.gisconsultoria.centrocfdi.model.Clientes;
import com.gisconsultoria.centrocfdi.model.dto.ClientesDto;

public interface IClientesService {

	public List<Clientes> getActiveClientes();
	
	public void updateFechaInicialClienteById(Date fecha, Long id);
	
	public Clientes findById(Long id);

	public Clientes getClienteByRfc(String rfc);
	
	public void save(Clientes cliente);
	
	//public  List<Clientes> getClientes();
	
	public void delete(Long id);
	
	public List<Long> getIdClienteByUsername(String username);
	 
	public List<Clientes> getClientesByUsername(String username);
	
	 public List<Clientes> getClienteWithIn(List<Long> cliente);
}
