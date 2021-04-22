package com.gisconsultoria.centrocfdi.model.dto;

import javax.xml.bind.Element;
import javax.xml.bind.annotation.*;

import com.gisconsultoria.centrocfdi.model.dao.ComprobanteCfdiRelacionado;
import com.gisconsultoria.centrocfdi.model.dao.ConceptoDao;
import com.gisconsultoria.centrocfdi.model.dao.EmisorDao;
import com.gisconsultoria.centrocfdi.model.dao.ImpuestoDao;
import com.gisconsultoria.centrocfdi.model.dao.ReceptorDao;
import com.gisconsultoria.centrocfdi.model.enums.MetodoPagoEnum;
import com.gisconsultoria.centrocfdi.model.enums.MonedaEnum;
import com.gisconsultoria.centrocfdi.model.enums.TipoComprobanteEnum;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Luis Enrique Morales Soriano
 */
@XmlRootElement(name = "Comprobante", namespace = "http://www.sat.gob.mx/cfd/3")
@XmlType(name = "Comprobante", namespace = "http://www.sat.gob.mx/cfd/3")
@XmlAccessorType(XmlAccessType.FIELD)
public class ComprobanteXmlDto {

    @XmlAttribute(name = "schemaLocation", namespace = "http://www.w3.org/2001/XMLSchema-instance")
    private String xsiSchemaLocation;

    @XmlTransient
    private boolean metodoPagoSpecified;

    @XmlAttribute(name = "MetodoPago")
    private MetodoPagoEnum metodoPago;

    @XmlAttribute(name = "TipoDeComprobante")
    private TipoComprobanteEnum tipoComprobante;

    @XmlAttribute(name = "Total")
    private Double total;

    @XmlTransient()
    private boolean TipoCambioSpecified;

    @XmlAttribute(name = "TipoCambio")
    private Double tipoCambio;

    @XmlAttribute(name = "Moneda")
    private MonedaEnum moneda;

    @XmlTransient
    private boolean DescuentoSpecified;

    @XmlAttribute(name = "Descuento")
    private Double descuento;

    @XmlAttribute(name = "SubTotal")
    private Double subTotal;

    @XmlAttribute(name = "CondicionesDePago")
    private String condicionesPago;

    @XmlAttribute(name = "Certificado")
    private String certificado;

    @XmlAttribute(name = "NoCertificado")
    private String noCertificado;

    @XmlTransient
    private boolean FormaPagoSpecified;

    @XmlAttribute(name = "FormaPago")
    private String formaPago;

    @XmlAttribute(name = "Sello")
    private String sello;

    @XmlAttribute(name = "Fecha")
    private Date fecha;

    @XmlAttribute(name = "Folio")
    private String folio;

    @XmlAttribute(name = "Serie")
    private String Serie;

    @XmlAttribute(name = "Version")
    private Double version;

    @XmlAnyElement(lax = true)
    private List<Object> complemento;

    @XmlElement(name = "Impuestos", namespace = "http://www.sat.gob.mx/cfd/3")
    private List<ImpuestoDao> impuestos;

    @XmlElement(name = "Conceptos", namespace = "http://www.sat.gob.mx/cfd/3")
    private List<ConceptoDao> conceptos;

    @XmlElement(name = "Receptor", namespace = "http://www.sat.gob.mx/cfd/3")
    private ReceptorDao receptor;

    @XmlElement(name = "Emisor", namespace = "http://www.sat.gob.mx/cfd/3")
    private EmisorDao emisor;

    @XmlElement(name = "CfdiRelacionados", namespace = "http://www.sat.gob.mx/cfd/3")
    private List<ComprobanteCfdiRelacionado> cfdiRelacionados;

    @XmlAttribute(name = "LugarExpedicion")
    private String lugarExpedicion;

    @XmlAttribute(name = "Confirmacion")
    private String confirmacion;

    public ComprobanteXmlDto() {
        this.complemento = new ArrayList<>();
        this.impuestos = new ArrayList<>();
        this.conceptos = new ArrayList<>();
        this.cfdiRelacionados = new ArrayList<>();
    }

    public String getXsiSchemaLocation() {
        return xsiSchemaLocation;
    }

    public void setXsiSchemaLocation(String xsiSchemaLocation) {
        this.xsiSchemaLocation = xsiSchemaLocation;
    }

    public boolean isMetodoPagoSpecified() {
        return metodoPagoSpecified;
    }

    public void setMetodoPagoSpecified(boolean metodoPagoSpecified) {
        this.metodoPagoSpecified = metodoPagoSpecified;
    }

    public MetodoPagoEnum getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPagoEnum metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Double getTotal() {
        return total;
    }

    public TipoComprobanteEnum getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(TipoComprobanteEnum tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public boolean isTipoCambioSpecified() {
        return TipoCambioSpecified;
    }

    public void setTipoCambioSpecified(boolean tipoCambioSpecified) {
        TipoCambioSpecified = tipoCambioSpecified;
    }

    public Double getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(Double tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public MonedaEnum getMoneda() {
        return moneda;
    }

    public void setMoneda(MonedaEnum moneda) {
        this.moneda = moneda;
    }

    public boolean isDescuentoSpecified() {
        return DescuentoSpecified;
    }

    public void setDescuentoSpecified(boolean descuentoSpecified) {
        DescuentoSpecified = descuentoSpecified;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public String getCondicionesPago() {
        return condicionesPago;
    }

    public void setCondicionesPago(String condicionesPago) {
        this.condicionesPago = condicionesPago;
    }

    public String getCertificado() {
        return certificado;
    }

    public void setCertificado(String certificado) {
        this.certificado = certificado;
    }

    public String getNoCertificado() {
        return noCertificado;
    }

    public void setNoCertificado(String noCertificado) {
        this.noCertificado = noCertificado;
    }

    public boolean isFormaPagoSpecified() {
        return FormaPagoSpecified;
    }

    public void setFormaPagoSpecified(boolean formaPagoSpecified) {
        FormaPagoSpecified = formaPagoSpecified;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getSello() {
        return sello;
    }

    public void setSello(String sello) {
        this.sello = sello;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getSerie() {
        return Serie;
    }

    public void setSerie(String serie) {
        Serie = serie;
    }

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

    public List<Object> getComplemento() {
        return complemento;
    }

    public void setComplemento(List<Object> complemento) {
        this.complemento = complemento;
    }

    public List<ImpuestoDao> getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(List<ImpuestoDao> impuestos) {
        this.impuestos = impuestos;
    }

    public List<ConceptoDao> getConceptos() {
        return conceptos;
    }

    public void setConceptos(List<ConceptoDao> conceptos) {
        this.conceptos = conceptos;
    }

    public ReceptorDao getReceptor() {
        return receptor;
    }

    public void setReceptor(ReceptorDao receptor) {
        this.receptor = receptor;
    }

    public EmisorDao getEmisor() {
        return emisor;
    }

    public void setEmisor(EmisorDao emisor) {
        this.emisor = emisor;
    }

    public List<ComprobanteCfdiRelacionado> getCfdiRelacionados() {
        return cfdiRelacionados;
    }

    public void setCfdiRelacionados(List<ComprobanteCfdiRelacionado> cfdiRelacionados) {
        this.cfdiRelacionados = cfdiRelacionados;
    }

    public String getLugarExpedicion() {
        return lugarExpedicion;
    }

    public void setLugarExpedicion(String lugarExpedicion) {
        this.lugarExpedicion = lugarExpedicion;
    }

    public String getConfirmacion() {
        return confirmacion;
    }

    public void setConfirmacion(String confirmacion) {
        this.confirmacion = confirmacion;
    }
}
