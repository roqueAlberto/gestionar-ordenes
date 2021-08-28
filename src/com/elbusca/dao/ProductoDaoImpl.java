package com.elbusca.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import com.elbusca.entity.Producto;


@Repository
public class ProductoDaoImpl implements IProductoDao {

	private EntityManagerFactory factory;
	private EntityManager entity;

	public ProductoDaoImpl() {
		factory = Persistence.createEntityManagerFactory("persistenceElBusca");
		entity = factory.createEntityManager();
	}

		
	/**
	 * Esta funcion devuelve una lista con todos los productos
	 * @return list - es la lista con todos los productos
	 */
	@Override
	public List<Producto> listaProductos() {

		List<Producto> productos = entity.createQuery("FROM Producto", Producto.class)
				.getResultList();

		return productos;
	}

	
	/**
	 * Este metodo devuelve el producto a traves de su primary key
	 * @param id- Es un tipo de indice que servira para buscar el producto dentro de la base de datos
	 * @return producto - Producto encontrado
	 */
	@Override
	public Producto buscar(int id) {
		Producto producto = entity.find(Producto.class, id);
		return producto;
	}
	

	/**
	 * Este metodo actualiza el producto
	 * @param producto - Producto que será actualizado
	 */
	@Override
	public void actualizar(Producto producto) {

		entity.getTransaction().begin();
		entity.merge(producto);
		entity.getTransaction().commit();
	}

		
	/**
	 * El objetivo de este metodo es agregar un nuevo producto
	 * @param producto - Es el nuevo producto que sera agregado
	 */
	@Override
	public void agregar(Producto producto) {

		entity.getTransaction().begin();
		entity.persist(producto);
		entity.getTransaction().commit();
	}

	
	/**
	 * La funcion de este metodo es devolver el precio de un producto
	 * @param id - Es la llave primaria del producto que sera mapeado
	 * @return precio - precio del producto seleccionado
	 */
	@Override
	public Integer precioProducto(int id) {

		Query query = entity.createQuery("SELECT precio FROM Producto p " + "WHERE p.id =: id_producto");
		query.setParameter("id_producto", id);
		Integer precio = (Integer) query.getSingleResult();

		return precio;
	}

	
	/**
	 * Este metodo elimina el producto atraves de su primary key
	 * @param id - Es la llave primaria del producto que se eliminara
	 */
	@Override
	public void eliminar(int id) {

		Producto producto = entity.find(Producto.class, id);

		entity.getTransaction().begin();
		entity.remove(producto);
		entity.getTransaction().commit();
	}

	
}
