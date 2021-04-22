package com.gisconsultoria.centrocfdi.util;

import java.io.File;
import java.io.IOException;

public interface IReadXmlFile {
	
	public void readXmlFile(File dir) throws IOException;
	
	 public void decodeXmlFile(File file, String xml) throws Exception;
	 
	 public void unmarshallXmlToComprobanteXml(File file, String xml) throws Exception;

}
