package com.gisconsultoria.centrocfdi.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "cat_pdf")
public class Pdf implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nombre_pdf")
	private String namePdf;

	@Lob
	private byte[] pdf;

	@Column(name = "fecha_creacion")
	private Date fecha;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = " id_cfdi")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private CfdiPrincipal cfdi;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNamePdf() {
		return namePdf;
	}

	public void setNamePdf(String namePdf) {
		this.namePdf = namePdf;
	}

	public byte[] getPdf() {
		return pdf;
	}

	public void setPdf(byte[] pdf) {
		this.pdf = pdf;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public CfdiPrincipal getCfdi() {
		return cfdi;
	}

	public void setCfdi(CfdiPrincipal cfdi) {
		this.cfdi = cfdi;
	}

}
