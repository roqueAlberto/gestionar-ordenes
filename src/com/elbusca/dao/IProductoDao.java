package com.elbusca.dao;

import java.util.List;

import com.elbusca.entity.Producto;

public interface IProductoDao {

	
	public List<Producto> listaProductos();
	public Producto buscar(int id);
	public void actualizar (Producto producto);
	public void agregar(Producto producto);
	public Integer precioProducto(int id);
	public void eliminar(int id);
	
}
