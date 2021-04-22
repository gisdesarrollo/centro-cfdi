package com.gisconsultoria.centrocfdi.model.dao;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.gisconsultoria.centrocfdi.model.enums.ImpuestoEnum;
import com.gisconsultoria.centrocfdi.model.enums.TipoFactorEnum;


@XmlType(name = "Traslado")
@XmlAccessorType(XmlAccessType.FIELD)
public class TrasladoDao {

    @XmlAttribute(name = "Impuesto")
    private ImpuestoEnum impuesto;

    @XmlAttribute(name = "TipoFactor")
    private TipoFactorEnum tipoFactor;

    @XmlAttribute(name = "TasaOCuota")
    private Double tasaOCuota;

    @XmlAttribute(name = "Importe")
    private Double importe;

    public ImpuestoEnum getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(ImpuestoEnum impuesto) {
        this.impuesto = impuesto;
    }

    public TipoFactorEnum getTipoFactor() {
        return tipoFactor;
    }

    public void setTipoFactor(TipoFactorEnum tipoFactor) {
        this.tipoFactor = tipoFactor;
    }

    public Double getTasaOCuota() {
        return tasaOCuota;
    }

    public void setTasaOCuota(Double tasaOCuota) {
        this.tasaOCuota = tasaOCuota;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }
}
