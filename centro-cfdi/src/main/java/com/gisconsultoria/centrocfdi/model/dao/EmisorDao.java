package com.gisconsultoria.centrocfdi.model.dao;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import com.gisconsultoria.centrocfdi.model.enums.RegimenFiscalEnum;

@XmlAccessorType(XmlAccessType.FIELD)
public class EmisorDao {

    @XmlAttribute(name = "Rfc")
    private String rfc;

    @XmlAttribute(name = "Nombre")
    private String nombre;

    @XmlAttribute(name = "RegimenFiscal")
    private RegimenFiscalEnum regimenFiscal;

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public RegimenFiscalEnum getRegimenFiscal() {
        return regimenFiscal;
    }

    public void setRegimenFiscal(RegimenFiscalEnum regimenFiscal) {
        this.regimenFiscal = regimenFiscal;
    }
}
