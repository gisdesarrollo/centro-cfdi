package com.gisconsultoria.centrocfdi.model.dao;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


@XmlType(name = "ConceptoCuentaPredial")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConceptoCuentaPredialDao {

    @XmlAttribute(name = "Numero")
    private Double numero;

    public Double getNumero() {
        return numero;
    }

    public void setNumero(Double numero) {
        this.numero = numero;
    }
}
