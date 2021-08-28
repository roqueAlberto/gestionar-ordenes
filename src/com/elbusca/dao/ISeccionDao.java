package com.elbusca.dao;

import java.util.List;

import com.elbusca.entity.Producto;
import com.elbusca.entity.Seccion;

public interface ISeccionDao {

	public List<Seccion> listarSecciones();
	public List<Producto> productos(int id);
	public void agregar(Seccion seccion);
	public Seccion obtenerSeccion(int id);
	public void actualizar(Seccion seccion);
	public void eliminar(Seccion seccion);

}
