package com.gisconsultoria.centrocfdi.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ori_cfdirecibidos")
public class CfdiRecibidos implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;
	
	@Column(name = "Folio")
    private String folio;
	
	@Column(name = "Uuid")
	private String uuid;
	
	@Column(name = "EstatusF")
	private String estatusF;
	
	@Column(name = "EstatusC")
	private String estatusC;
	
	@Column(name = "RFCEmisor")
    private String rfcEmisor;
	
	@Column(name = "FechaEmision")
	private Date fechaEmision;
	
	@Column(name = "FechaCreacion")
	private Date fechaCreacion;
	
	@Column(name = "Subtotal")
	private String subtotal;
	
	@Column(name = "Total")
	private Double total;
	
	@Column(name = "RFCReceptor")
	private String rfcReceptor;
	
	@Column(name = "RazonSocial")
	private String razonSocial;
	
	@Column(name = "Moneda")
    private String moneda;
	
	@Column(name = "Division")
    private String division;
	
	@Column(name = "TotalImpTrasl")
    private String totalImpTrasl;
	
	@Column(name = "TipoComprobante")
    private String tipoComprobante;
	
	@Column(name = "NoCertificado")
    private String certificado;
	
	@Column(name = "FormaPago")
    private String formaPago;
	
	@Column(name = "Version")
    private String version;
	
	@Column(name = "MetodoPago")
    private String metodoPago;
	
	@Column(name = "TotalImpRet")
    private String totalImpRet;
	
	@Column(name = "Regimenfiscal")
    private String regimenfiscal;
	
	@Column(name = "Expedicion")
    private String expedicion;
	
	@Column(name = "ITIVA")
    private String ITIva;
	
	@Column(name = "ITIEPS")
    private String ITEps;
	
	@Column(name = "TIVATraslado")
    private String TIvaTraslado;
	
	@Column(name = "IRIVA")
    private String IRIva;
	
	@Column(name = "IRISR")
    private String IRIsr;
	
	@Column(name = "TipoCambio")
	private String tipoCambio;
	
	@Column(name = "Warning")
	private String warning;
	
	@Column(name = "TipoRelacion")
	private String tipoRelacion;
	
	@Column(name = "ListaNegra")
	private String listaNegra;
	
	@Column(name = "CFDIRel")
	private String CFDIRel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getEstatusF() {
		return estatusF;
	}

	public void setEstatusF(String estatusF) {
		this.estatusF = estatusF;
	}

	public String getEstatusC() {
		return estatusC;
	}

	public void setEstatusC(String estatusC) {
		this.estatusC = estatusC;
	}

	public String getRfcEmisor() {
		return rfcEmisor;
	}

	public void setRfcEmisor(String rfcEmisor) {
		this.rfcEmisor = rfcEmisor;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getRfcReceptor() {
		return rfcReceptor;
	}

	public void setRfcReceptor(String rfcReceptor) {
		this.rfcReceptor = rfcReceptor;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getTotalImpTrasl() {
		return totalImpTrasl;
	}

	public void setTotalImpTrasl(String totalImpTrasl) {
		this.totalImpTrasl = totalImpTrasl;
	}

	public String getTipoComprobante() {
		return tipoComprobante;
	}

	public void setTipoComprobante(String tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}

	public String getCertificado() {
		return certificado;
	}

	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}

	public String getTotalImpRet() {
		return totalImpRet;
	}

	public void setTotalImpRet(String totalImpRet) {
		this.totalImpRet = totalImpRet;
	}

	public String getRegimenfiscal() {
		return regimenfiscal;
	}

	public void setRegimenfiscal(String regimenfiscal) {
		this.regimenfiscal = regimenfiscal;
	}

	public String getExpedicion() {
		return expedicion;
	}

	public void setExpedicion(String expedicion) {
		this.expedicion = expedicion;
	}

	public String getITIva() {
		return ITIva;
	}

	public void setITIva(String iTIva) {
		ITIva = iTIva;
	}

	public String getITEps() {
		return ITEps;
	}

	public void setITEps(String iTEps) {
		ITEps = iTEps;
	}

	public String getTIvaTraslado() {
		return TIvaTraslado;
	}

	public void setTIvaTraslado(String tIvaTraslado) {
		TIvaTraslado = tIvaTraslado;
	}

	public String getIRIva() {
		return IRIva;
	}

	public void setIRIva(String iRIva) {
		IRIva = iRIva;
	}

	public String getIRIsr() {
		return IRIsr;
	}

	public void setIRIsr(String iRIsr) {
		IRIsr = iRIsr;
	}

	public String getTipoCambio() {
		return tipoCambio;
	}

	public void setTipoCambio(String tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	public String getWarning() {
		return warning;
	}

	public void setWarning(String warning) {
		this.warning = warning;
	}

	public String getTipoRelacion() {
		return tipoRelacion;
	}

	public void setTipoRelacion(String tipoRelacion) {
		this.tipoRelacion = tipoRelacion;
	}

	public String getListaNegra() {
		return listaNegra;
	}

	public void setListaNegra(String listaNegra) {
		this.listaNegra = listaNegra;
	}

	public String getCFDIRel() {
		return CFDIRel;
	}

	public void setCFDIRel(String cFDIRel) {
		CFDIRel = cFDIRel;
	}

	
}
