package com.gisconsultoria.centrocfdi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "status_ws")
public class StatusWs implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_status")
	private Long id;
	
	@Column(name = "`status`")
	private String status;
	
	private String serie;
	
	private String folio;
	
	private String referencia;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cfdi")
	private CfdiPrincipal Cfdi;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public CfdiPrincipal getCfdi() {
		return Cfdi;
	}

	public void setCfdi(CfdiPrincipal cfdi) {
		Cfdi = cfdi;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	
	
}
