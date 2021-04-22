package com.gisconsultoria.centrocfdi.util;

import java.io.File;
import java.text.ParseException;

import com.gisconsultoria.centrocfdi.model.dto.ComprobanteXmlDto;
import com.gisconsultoria.centrocfdi.model.dto.TimbreFiscalDto;

public interface ILogicaFacade {

	public boolean checarUuidRepetidoBD(TimbreFiscalDto timbrefiscal, File file, String xml) throws ParseException;

	public boolean checarRfcReceptor(ComprobanteXmlDto comprobante) throws Exception;

	public boolean guardarComprobanteBd(ComprobanteXmlDto comprobante, File file, String xml, TimbreFiscalDto timbreFiscal) throws Exception;

}
