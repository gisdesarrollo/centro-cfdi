package com.gisconsultoria.centrocfdi.model.dao;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.gisconsultoria.centrocfdi.model.enums.ImpuestoEnum;

@XmlType(name = "Retencion")
@XmlAccessorType(XmlAccessType.FIELD)
public class RetencionDao {

    @XmlAttribute(name = "Impuesto")
    private ImpuestoEnum impuesto;

    @XmlAttribute(name = "Importe")
    private Double importe;

    public ImpuestoEnum getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(ImpuestoEnum impuesto) {
        this.impuesto = impuesto;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }
}
