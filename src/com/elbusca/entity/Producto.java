package com.elbusca.entity;



import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "producto")
public class Producto  {

	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_producto")
	private int id;
	
	
	private String nombre;
	
	
	private int precio;
	
	@ManyToOne
	@JoinColumn(name = "seccion_id")
	Seccion seccion;
	
	@OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
	private Set<DetalleOrden> orden_detalle = new HashSet<>();
	

	public Producto() {
		
	}

	
	public Producto(String nombre, int precio, Seccion seccion) {		
		this.nombre = nombre;
		this.precio = precio;
		this.seccion = seccion;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public Seccion getSeccion() {
		return seccion;
	}

	public void setSeccion(Seccion seccion) {
		this.seccion = seccion;
	}


	public Set<DetalleOrden> getOrden_detalle() {
		return orden_detalle;
	}


	public void setOrden_detalle(Set<DetalleOrden> orden_detalle) {
		this.orden_detalle = orden_detalle;
	}


	@Override
	public String toString() {
		return "ProductoEntity [id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", seccion=" + seccion
				+ ", orden_detalle=" + orden_detalle + "]";
	}
	
	
	
}
