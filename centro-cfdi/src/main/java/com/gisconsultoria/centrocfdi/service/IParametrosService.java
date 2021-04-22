package com.gisconsultoria.centrocfdi.service;

import java.util.List;

import com.gisconsultoria.centrocfdi.model.Parametros;

public interface IParametrosService {

	public List<Parametros> getParametrosByIdCliente(Long id);
}
