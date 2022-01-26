package com.gisconsultoria.centrocfdi.model.dao;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AddendaDao {
	
	 @XmlElement(name = "Avla")
	 private ReferenciaDao avla;

	public ReferenciaDao getAvla() {
		return avla;
	}

	public void setAvla(ReferenciaDao avla) {
		this.avla = avla;
	}

	 
}
