package com.gisconsultoria.centrocfdi.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="cat_usuarios")
public class Usuarios  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Long id;
	
	@Column(unique = true, length = 30)
	private String username; 
	
	@Column(length = 40)
	private String nombre;
	
	@Column(length = 40)
	private String apellidos;
	
	@Column(length = 60)
	private String password;
	
	@Column(length = 60)
	private String email;
	
	private Boolean estatus;
	
	@Transient
	private Long[] clienteId;
	
	@Transient
	private String[] comprobante;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "rel_usuarios_roles", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_role")
	,uniqueConstraints = {@UniqueConstraint(columnNames = {"id_usuario", "id_role"})})
	private List<Roles> roles;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "rel_usuarios_clientes", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_cliente"))
	private List<Clientes> clientes;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "rel_usuarios_comprobantes", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_comprobante"))
	private List<Comprobantes> comprobantes;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEstatus() {
		return estatus;
	}

	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}

	public List<Roles> getRoles() {
		return roles;
	}

	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Long[] getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long[] clienteId) {
		this.clienteId = clienteId;
	}

	public String[] getComprobante() {
		return comprobante;
	}

	public void setComprobante(String[] tipoComprobante) {
		this.comprobante = tipoComprobante;
	}

	public List<Clientes> getClientes() {
		return clientes;
	}

	public void setClientes(List<Clientes> clientes) {
		this.clientes = clientes;
	}

	public List<Comprobantes> getComprobantes() {
		return comprobantes;
	}

	public void setComprobantes(List<Comprobantes> comprobantes) {
		this.comprobantes = comprobantes;
	}
	
}
