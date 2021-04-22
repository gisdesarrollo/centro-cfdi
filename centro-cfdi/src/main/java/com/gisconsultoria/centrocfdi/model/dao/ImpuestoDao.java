package com.gisconsultoria.centrocfdi.model.dao;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImpuestoDao {

    @XmlElement(name = "Retenciones")
    private List<RetencionDao> retenciones;

    @XmlElement(name = "Traslados")
    private List<TrasladoDao> traslados;

    @XmlAttribute(name = "TotalImpuestosRetenidos")
    private Double totalImpuestosRetenidos;

    @XmlTransient
    private boolean TotalImpuestosRetenidosSpecified;

    @XmlAttribute(name = "TotalImpuestosTrasladados")
    private Double totalImpuestosTrasladados;

    @XmlTransient
    private boolean TotalImpuestosTrasladados;

    public ImpuestoDao() {
        this.retenciones = new ArrayList<>();
        this.traslados = new ArrayList<>();
    }

    public List<RetencionDao> getRetenciones() {
        return retenciones;
    }

    public void setRetenciones(List<RetencionDao> retenciones) {
        this.retenciones = retenciones;
    }

    public List<TrasladoDao> getTraslados() {
        return traslados;
    }

    public void setTraslados(List<TrasladoDao> traslados) {
        this.traslados = traslados;
    }

    public Double getTotalImpuestosRetenidos() {
        return totalImpuestosRetenidos;
    }

    public void setTotalImpuestosRetenidos(Double totalImpuestosRetenidos) {
        this.totalImpuestosRetenidos = totalImpuestosRetenidos;
    }

    public boolean isTotalImpuestosRetenidosSpecified() {
        return TotalImpuestosRetenidosSpecified;
    }

    public void setTotalImpuestosRetenidosSpecified(boolean totalImpuestosRetenidosSpecified) {
        TotalImpuestosRetenidosSpecified = totalImpuestosRetenidosSpecified;
    }

    public Double getTotalImpuestosTrasladados() {
        return totalImpuestosTrasladados;
    }

    public void setTotalImpuestosTrasladados(Double totalImpuestosTrasladados) {
        this.totalImpuestosTrasladados = totalImpuestosTrasladados;
    }

    public boolean isTotalImpuestosTrasladados() {
        return TotalImpuestosTrasladados;
    }

    public void setTotalImpuestosTrasladados(boolean totalImpuestosTrasladados) {
        TotalImpuestosTrasladados = totalImpuestosTrasladados;
    }
}
