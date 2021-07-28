package com.gisconsultoria.centrocfdi.service;

import java.util.List;

import com.gisconsultoria.centrocfdi.model.Comprobantes;

public interface IComprobanteService {
	
	public Comprobantes getComprobanteByName(String nombre);
	
	public List<String> getNameComprobanteByUsername(String username);
	
	public List<Comprobantes> getComprobanteWithIn(List<String> comprobante);

	public List<Comprobantes> getAllComprobantes();
	
}
