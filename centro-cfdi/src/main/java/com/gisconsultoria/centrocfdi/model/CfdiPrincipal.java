package com.gisconsultoria.centrocfdi.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "m_cfdi_33")
public class CfdiPrincipal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cfdi")
	private Long id;
	private String version;
	private String serie;
	private String folio;
	private Date fecha;
	private String hora;
	private String sello;
	@Column(name = "formapago")
	private String formaPago;
	@Column(name = "nocertificado")
	private String noCertificado;
	private String certificado;
	@Column(name = "condicionesdepago")
	private String condicionesPago;
	private Double subtotal;
	private Double descuento;
	private String moneda;
	@Column(name = "tipocambio")
	private Double tipoCambio;
	private Double total;
	@Column(name = "tipodecomprobante")
	private String tipoComprobante;
	@Column(name = "metodopago")
	private String metodoPago;
	@Column(name = "lugarexpedicion")
	private String lugarExpedicion;
	private String confirmacion;
	private Boolean estatus;
	@Column(name = "tfd_version")
	private String tfdVersion;
	@Column(name = "tfd_uuid")
	private String tfdUuid;
	@Column(name = "tfd_fechatimbrado")
	private String tfdFechaTimbrado;
	@Column(name = "tfd_horatimbrado")
	private String tfdHoraTimbrado;
	@Column(name = "tfd_sellocfd")
	private String tfdSelloCfd;
	@Column(name = "tfd_nocertificadosat")
	private String tfdNoCertificado;
	@Column(name = "tfd_sellosat")
	private String tfdSellSat;
	
	@Transient
	private int totalRecords;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente"/*,nullable = false*/)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	//@Column(name = "id_cliente")
	private Clientes empresaId;
	
	public CfdiPrincipal() {}

	public CfdiPrincipal(Clientes empresaId, String version, String serie, String folio, Date fecha, String hora, String sello,
			String formaPago, String noCertificado, String certificado, String condicionesPago, Double subtotal,
			Double descuento, String moneda, Double tipoCambio, Double total, String tipoComprobante, String metodoPago,
			String lugarExpedicion, String confirmacion, Boolean estatus, String tfdVersion, String tfdUuid,
			String tfdFechaTimbrado, String tfdHoraTimbrado, String tfdSelloCfd, String tfdNoCertificado,
			String tfdSellSat) {
		
		this.empresaId = empresaId;
		this.version = version;
		this.serie = serie;
		this.folio = folio;
		this.fecha = fecha;
		this.hora = hora;
		this.sello = sello;
		this.formaPago = formaPago;
		this.noCertificado = noCertificado;
		this.certificado = certificado;
		this.condicionesPago = condicionesPago;
		this.subtotal = subtotal;
		this.descuento = descuento;
		this.moneda = moneda;
		this.tipoCambio = tipoCambio;
		this.total = total;
		this.tipoComprobante = tipoComprobante;
		this.metodoPago = metodoPago;
		this.lugarExpedicion = lugarExpedicion;
		this.confirmacion = confirmacion;
		this.estatus = estatus;
		this.tfdVersion = tfdVersion;
		this.tfdUuid = tfdUuid;
		this.tfdFechaTimbrado = tfdFechaTimbrado;
		this.tfdHoraTimbrado = tfdHoraTimbrado;
		this.tfdSelloCfd = tfdSelloCfd;
		this.tfdNoCertificado = tfdNoCertificado;
		this.tfdSellSat = tfdSellSat;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Clientes getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Clientes clienteId) {
		this.empresaId = clienteId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getSello() {
		return sello;
	}

	public void setSello(String sello) {
		this.sello = sello;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public String getNoCertificado() {
		return noCertificado;
	}

	public void setNoCertificado(String noCertificado) {
		this.noCertificado = noCertificado;
	}

	public String getCertificado() {
		return certificado;
	}

	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}

	public String getCondicionesPago() {
		return condicionesPago;
	}

	public void setCondicionesPago(String condicionesPago) {
		this.condicionesPago = condicionesPago;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public Double getDescuento() {
		return descuento;
	}

	public void setDescuento(Double descuento) {
		this.descuento = descuento;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public Double getTipoCambio() {
		return tipoCambio;
	}

	public void setTipoCambio(Double tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getTipoComprobante() {
		return tipoComprobante;
	}

	public void setTipoComprobante(String tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}

	public String getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
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

	public Boolean getEstatus() {
		return estatus;
	}

	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}

	public String getTfdVersion() {
		return tfdVersion;
	}

	public void setTfdVersion(String tfdVersion) {
		this.tfdVersion = tfdVersion;
	}

	public String getTfdUuid() {
		return tfdUuid;
	}

	public void setTfdUuid(String tfdUuid) {
		this.tfdUuid = tfdUuid;
	}

	public String getTfdFechaTimbrado() {
		return tfdFechaTimbrado;
	}

	public void setTfdFechaTimbrado(String tfdFechaTimbrado) {
		this.tfdFechaTimbrado = tfdFechaTimbrado;
	}

	public String getTfdHoraTimbrado() {
		return tfdHoraTimbrado;
	}

	public void setTfdHoraTimbrado(String tfdHoraTimbrado) {
		this.tfdHoraTimbrado = tfdHoraTimbrado;
	}

	public String getTfdSelloCfd() {
		return tfdSelloCfd;
	}

	public void setTfdSelloCfd(String tfdSelloCfd) {
		this.tfdSelloCfd = tfdSelloCfd;
	}

	public String getTfdNoCertificado() {
		return tfdNoCertificado;
	}

	public void setTfdNoCertificado(String tfdNoCertificado) {
		this.tfdNoCertificado = tfdNoCertificado;
	}

	public String getTfdSellSat() {
		return tfdSellSat;
	}

	public void setTfdSellSat(String tfdSellSat) {
		this.tfdSellSat = tfdSellSat;
	}
	

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	@Override
	public String toString() {
		return "CfdiPrincipal [id=" + id + ", version=" + version + ", serie=" + serie + ", folio=" + folio + ", fecha="
				+ fecha + ", hora=" + hora + ", sello=" + sello + ", formaPago=" + formaPago + ", noCertificado="
				+ noCertificado + ", certificado=" + certificado + ", condicionesPago=" + condicionesPago
				+ ", subtotal=" + subtotal + ", descuento=" + descuento + ", moneda=" + moneda + ", tipoCambio="
				+ tipoCambio + ", total=" + total + ", tipoComprobante=" + tipoComprobante + ", metodoPago="
				+ metodoPago + ", lugarExpedicion=" + lugarExpedicion + ", confirmacion=" + confirmacion + ", estatus="
				+ estatus + ", tfdVersion=" + tfdVersion + ", tfdUuid=" + tfdUuid + ", tfdFechaTimbrado="
				+ tfdFechaTimbrado + ", tfdHoraTimbrado=" + tfdHoraTimbrado + ", tfdSelloCfd=" + tfdSelloCfd
				+ ", tfdNoCertificado=" + tfdNoCertificado + ", tfdSellSat=" + tfdSellSat + ", empresaId=" + empresaId
				+ "]";
	}

}
