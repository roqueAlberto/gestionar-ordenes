package com.elbusca.dto;

import com.elbusca.entity.Producto;

public class ProductoDTO {

	private int id;
	private String nombre;
	private int precio;
	private SeccionDTO seccion = new SeccionDTO();
	
	public ProductoDTO(Producto producto) {	
		this.id = producto.getId();
		this.nombre = producto.getNombre();
		this.precio = producto.getPrecio();
		this.seccion.setId(producto.getSeccion().getId());
	}
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setSeccion(SeccionDTO seccion) {
		this.seccion = seccion;
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
	
	
	
	public SeccionDTO getSeccion() {
		return seccion;
	}
	

	@Override
	public String toString() {
		return "Producto [nombre=" + nombre + ", precio=" + precio + ", id= "+id+"]";
	}
	
	
}
