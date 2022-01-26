package com.gisconsultoria.centrocfdi.services;

import java.io.InputStream;

public interface IDescargaCfdi {
	
	public InputStream getCfdiZip(String fechaInicial, String fechaFinal,String rfc,int pageZise,int pageNumber);
	
	public int getTotalCfdiGenerados(String servidor,String keyXsa,String fechaInicial,String fechaFinal);

}
