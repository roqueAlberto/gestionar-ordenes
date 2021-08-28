package com.elbusca.dto;

import com.elbusca.entity.Seccion;

public class SeccionDTO {

	private int id;
	private String nombre;
	
	
	
	public SeccionDTO() {
		
	}


	public SeccionDTO(Seccion seccion) {		
		this.id = seccion.getId();
		this.nombre = seccion.getNombre();		
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
	
	
	
	
}
