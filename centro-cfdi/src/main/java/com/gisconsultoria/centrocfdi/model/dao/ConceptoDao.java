package com.gisconsultoria.centrocfdi.model.dao;

import javax.xml.bind.Element;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class ConceptoDao {

    @XmlElement(name = "Impuestos")
    private List<ImpuestoDao> impuestos;

    @XmlElement(name = "InformacionAduanera")
    private List<InformacionAduaneraDao> informacionAduanera;

    @XmlElement(name = "CuentaPredial")
    private ConceptoCuentaPredialDao cuentaPredial;

    @XmlAnyElement
    private List<Element> complementoConcepto;

    @XmlElement(name = "Parte")
    private List<ComprobanteConceptoParteDao> comprobanteConceptoParte;

    @XmlAttribute(name = "ClaveProdServ")
    private String claveProdServ;

    @XmlAttribute(name = "NoIdentificacion")
    private String noIdentificacion;

    @XmlAttribute(name = "Cantidad")
    private int cantidad;

    @XmlAttribute(name = "ClaveUnidad")
    private String claveUnidad;

    @XmlAttribute(name = "Unidad")
    private String unidad;

    @XmlAttribute(name = "Descripcion")
    private String descripcion;

    @XmlAttribute(name = "ValorUnitario")
    private Double valorUnitario;

    @XmlAttribute(name = "Importe")
    private Double importe;

    @XmlAttribute(name = "Descuento")
    private Double descuento;

    @XmlTransient
    private boolean DescuentoSpecified;

    public ConceptoDao(){
        this.complementoConcepto = new ArrayList<>();
        this.impuestos = new ArrayList<>();
        this.informacionAduanera = new ArrayList<>();
        this.comprobanteConceptoParte = new ArrayList<>();
    }

    public List<ImpuestoDao> getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(List<ImpuestoDao> impuestos) {
        this.impuestos = impuestos;
    }

    public List<InformacionAduaneraDao> getInformacionAduanera() {
        return informacionAduanera;
    }

    public void setInformacionAduanera(List<InformacionAduaneraDao> informacionAduanera) {
        this.informacionAduanera = informacionAduanera;
    }

    public ConceptoCuentaPredialDao getCuentaPredial() {
        return cuentaPredial;
    }

    public void setCuentaPredial(ConceptoCuentaPredialDao cuentaPredial) {
        this.cuentaPredial = cuentaPredial;
    }

    public List<Element> getComplementoConcepto() {
        return complementoConcepto;
    }

    public void setComplementoConcepto(List<Element> complementoConcepto) {
        this.complementoConcepto = complementoConcepto;
    }

    public List<ComprobanteConceptoParteDao> getComprobanteConceptoParte() {
        return comprobanteConceptoParte;
    }

    public void setComprobanteConceptoParte(List<ComprobanteConceptoParteDao> comprobanteConceptoParte) {
        this.comprobanteConceptoParte = comprobanteConceptoParte;
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

    public String getClaveUnidad() {
        return claveUnidad;
    }

    public void setClaveUnidad(String claveUnidad) {
        this.claveUnidad = claveUnidad;
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

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public boolean isDescuentoSpecified() {
        return DescuentoSpecified;
    }

    public void setDescuentoSpecified(boolean descuentoSpecified) {
        DescuentoSpecified = descuentoSpecified;
    }
}
