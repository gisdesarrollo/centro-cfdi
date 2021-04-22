package com.gisconsultoria.centrocfdi.model.dto;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class ClientesDto {

	private Long id;

    private String nombre;
    
    private int status;
    
    private String email;
    
    private String nombreLogo;
    
    //private File logo;
    
    private String razonSocial;
    
    private String rfc;
    
    private int codigoPostal;
    
    private int pais;
    
    private String nombreFileCer;
    
   // private File cer;
    
    private String nombreFileKey;
    
    //private File key;
    
    private String passwordKey;
    
    
    private String servidor;

    
    private String keyXsa;

    
    private Date fechaInicial;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getNombreLogo() {
		return nombreLogo;
	}


	public void setNombreLogo(String nombreLogo) {
		this.nombreLogo = nombreLogo;
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


	public int getCodigoPostal() {
		return codigoPostal;
	}


	public void setCodigoPostal(int codigoPostal) {
		this.codigoPostal = codigoPostal;
	}


	public int getPais() {
		return pais;
	}


	public void setPais(int pais) {
		this.pais = pais;
	}


	public String getNombreFileCer() {
		return nombreFileCer;
	}


	public void setNombreFileCer(String nombreFileCer) {
		this.nombreFileCer = nombreFileCer;
	}


	public String getNombreFileKey() {
		return nombreFileKey;
	}


	public void setNombreFileKey(String nombreFileKey) {
		this.nombreFileKey = nombreFileKey;
	}


	public String getPasswordKey() {
		return passwordKey;
	}


	public void setPasswordKey(String passwordKey) {
		this.passwordKey = passwordKey;
	}


	public String getServidor() {
		return servidor;
	}


	public void setServidor(String servidor) {
		this.servidor = servidor;
	}


	public String getKeyXsa() {
		return keyXsa;
	}


	public void setKeyXsa(String keyXsa) {
		this.keyXsa = keyXsa;
	}


	public Date getFechaInicial() {
		return fechaInicial;
	}


	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

}
