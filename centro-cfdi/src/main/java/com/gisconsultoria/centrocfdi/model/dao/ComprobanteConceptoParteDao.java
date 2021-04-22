package com.gisconsultoria.centrocfdi.model.dao;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlType(name = "Parte")
@XmlAccessorType(XmlAccessType.FIELD)
public class ComprobanteConceptoParteDao {

    @XmlElement(name = "InformacionAduanera")
    private List<InformacionAduaneraDao> informacionAduanera;

    @XmlAttribute(name = "ClaveProdServ")
    private String claveProdServ;

    @XmlAttribute(name = "NoIdentificacion")
    private String noIdentificacion;

    @XmlAttribute(name = "Cantidad")
    private int cantidad;

    @XmlAttribute(name = "Unidad")
    private String unidad;

    @XmlAttribute(name = "Descripcion")
    private String descripcion;

    @XmlAttribute(name = "ValorUnitario")
    private Double valorUnitario;

    @XmlTransient
    private String ValorUnitarioSpecified;

    @XmlAttribute(name = "Importe")
    private Double importe;

    @XmlTransient
    private String ImporteSpecified;

    public ComprobanteConceptoParteDao(){
        this.informacionAduanera = new ArrayList<>();
    }

    public List<InformacionAduaneraDao> getInformacionAduanera() {
        return informacionAduanera;
    }

    public void setInformacionAduanera(List<InformacionAduaneraDao> informacionAduanera) {
        this.informacionAduanera = informacionAduanera;
    }

    public String getClaveProdServ() {
        return claveProdServ;
    }

    public void setClaveProdServ(String claveProdServ) {
        this.claveProdServ = claveProdServ;
    }

    public String getNoIdentificacion() {
        return noIdentificacion;
    }

    public void setNoIdentificacion(String noIdentificacion) {
        this.noIdentificacion = noIdentificacion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public String getValorUnitarioSpecified() {
        return ValorUnitarioSpecified;
    }

    public void setValorUnitarioSpecified(String valorUnitarioSpecified) {
        ValorUnitarioSpecified = valorUnitarioSpecified;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public String getImporteSpecified() {
        return ImporteSpecified;
    }

    public void setImporteSpecified(String importeSpecified) {
        ImporteSpecified = importeSpecified;
    }
}
