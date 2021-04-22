package com.gisconsultoria.centrocfdi.model.dto;

import java.util.Date;

public class DataTableResponseDto {
	
	private Long id;
	
	private String razonSocial;
	
	private String rfc;
	
	private String uuid;
	
	private String fecha;
	
	private String folio;
	
	private Double total;
	
	private String fechaIicial;
	
	private String fechafinal;
	
	private String serie;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getFechaIicial() {
		return fechaIicial;
	}

	public void setFechaIicial(String fechaIicial) {
		this.fechaIicial = fechaIicial;
	}

	public String getFechafinal() {
		return fechafinal;
	}

	public void setFechafinal(String fechafinal) {
		this.fechafinal = fechafinal;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	
}
