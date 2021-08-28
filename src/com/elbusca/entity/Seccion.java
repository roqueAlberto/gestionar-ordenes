package com.elbusca.entity;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name = "seccion")
public class Seccion {




	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_seccion")
	private int id;
	
	private String nombre;
	
	@OneToMany(mappedBy = "seccion", fetch = FetchType.LAZY)	
	private List<Producto> productos;

	public Seccion() {
		
	}

	
	
	public Seccion(int id) {
		
		this.id = id;
	}


	

	public Seccion(String nombre) {
		
		this.nombre = nombre;
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

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}



	@Override
	public String toString() {
		return "SeccionEntity [id=" + id + ", nombre=" + nombre + ", productos=" + productos + "]";
	}
	
	
	
}
