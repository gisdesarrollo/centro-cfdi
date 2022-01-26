package com.gisconsultoria.centrocfdi.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "m_receptor_33")
public class Receptor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_receptor")
	private Long idReceptor;
	
	@Column(name = "rfc")
	private String rfc;
	
	private String nombre;
	
	@Column(name="residenciafiscal")
	private String residenciaFiscal;
	
	@Column(name="numregidtrib")
	private String numRegiDTrib;

	@Column(name="usocfdi")
	private String usoCfdi;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cfdi")
	private CfdiPrincipal idCfdi;


	public Long getIdReceptor() {
		return idReceptor;
	}


	public void setIdReceptor(Long idReceptor) {
		this.idReceptor = idReceptor;
	}


	public String getRfc() {
		return rfc;
	}


	public void setRfc(String rfc) {
		this.rfc = rfc;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getResidenciaFiscal() {
		return residenciaFiscal;
	}


	public void setResidenciaFiscal(String residenciaFiscal) {
		this.residenciaFiscal = residenciaFiscal;
	}


	public String getNumRegiDTrib() {
		return numRegiDTrib;
	}


	public void setNumRegiDTrib(String numRegiDTrib) {
		this.numRegiDTrib = numRegiDTrib;
	}


	public String getUsoCfdi() {
		return usoCfdi;
	}


	public void setUsoCfdi(String usoCfdi) {
		this.usoCfdi = usoCfdi;
	}


	public CfdiPrincipal getIdCfdi() {
		return idCfdi;
	}


	public void setIdCfdi(CfdiPrincipal idCfdi) {
		this.idCfdi = idCfdi;
	}
	
	
}
