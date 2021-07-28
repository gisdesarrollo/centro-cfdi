package com.gisconsultoria.centrocfdi.model.dto;

public class EstadisticaDto {
	
	private String cliente;
	private Long ingreso;
	private Long egreso;
	private Long traslado;
	private Long nomina;
	private Long pago;
	private Long total;
	
	
	
	public Long getIngreso() {
		return ingreso;
	}
	public void setIngreso(Long ingreso) {
		this.ingreso = ingreso;
	}
	public Long getEgreso() {
		return egreso;
	}
	public void setEgreso(Long egreso) {
		this.egreso = egreso;
	}
	public Long getTraslado() {
		return traslado;
	}
	public void setTraslado(Long traslado) {
		this.traslado = traslado;
	}
	public Long getNomina() {
		return nomina;
	}
	public void setNomina(Long nomina) {
		this.nomina = nomina;
	}
	public Long getPago() {
		return pago;
	}
	public void setPago(Long pago) {
		this.pago = pago;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	
	
	
	
}