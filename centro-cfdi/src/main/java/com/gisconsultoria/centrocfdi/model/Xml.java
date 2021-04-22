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
@Table(name="cat_xml")
public class Xml implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long id;
	
	@Column(name="nombre_xml")
	private String nameXml;
	
	@Lob
	@Column(name="`xml`")
	private byte[] xml;
	
	@Column(name ="fecha_creacion")
	private Date fecha;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = " id_cfdi")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private CfdiPrincipal cfdi;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getXml() {
		return xml;
	}

	public void setXml(byte[] xml) {
		this.xml = xml;
	}

	public CfdiPrincipal getCfdi() {
		return cfdi;
	}

	public void setCfdi(CfdiPrincipal cfdi) {
		this.cfdi = cfdi;
	}

	public String getNameXml() {
		return nameXml;
	}

	public void setNameXml(String nameXml) {
		this.nameXml = nameXml;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	
}
