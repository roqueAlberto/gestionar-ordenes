package com.elbusca.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.elbusca.entity.Producto;
import com.elbusca.entity.Seccion;

@Repository
public class SeccionDaoImpl implements ISeccionDao {

	private EntityManagerFactory factory;
	private EntityManager entity;

	public SeccionDaoImpl() {
		
		factory = Persistence.createEntityManagerFactory("persistenceElBusca");
		entity = factory.createEntityManager();
	}

	/**
	 * Esta funcion devuelve una lista con todas las secciones
	 * @return secciones - Son todas las secciones que se encuentran en la base de datos
	 */
	@Override
	public List<Seccion> listarSecciones() {
		
     	List<Seccion> secciones= entity.createQuery("FROM Seccion", Seccion.class).getResultList();
     	
		return secciones;
	}
	

	/**
	 * Este metodo obtiene todos los productos que pertenecen a la seccion seleccionada
	 * @param id - Es la primary key de la seccion
	 * @return productos - Es la lista con todos los productos de dicha seccion
	 */
	@Override
	public List<Producto> productos(int id) {

		Query query = entity.createQuery("FROM Seccion s WHERE s.id =: id_seccion",Seccion.class);
		query.setParameter("id_seccion", id);
 
        Seccion seccion = (Seccion)query.getSingleResult();	
        
        return seccion.getProductos();	
	}
	
	
	/**
	 * Este metodo agrega una nueva seccion
	 * @param seccion - Es la sección que sera agregada
	 */
	@Override
	public void agregar(Seccion seccion) {

		entity.getTransaction().begin();
		entity.persist(seccion);
		entity.getTransaction().commit();

	}

	
	/**
	 * Esta funcion devuelve la seccion seleccionada
	 * @param id - es la llave primaria de la seccion a obtener
	 * @return seccion - es la seccion encontrada
	 */
	@Override
	public Seccion obtenerSeccion(int id) {

		Seccion seccion = entity.find(Seccion.class, id);
		return seccion;
	}

	
	/**
	 * Este metodo actualiza la seccion
	 * @param seccion - seccion que sera actualizada
	 */
	@Override
	public void actualizar(Seccion seccion) {

		entity.getTransaction().begin();
		entity.merge(seccion);
		entity.getTransaction().commit();

	}

	
	/**
	 * Este metodo elimina la seccion de la base de datos
	 * @param seccion - es la seccion que sera eliminada
	 */
	@Override
	public void eliminar(Seccion seccion) {
		entity.getTransaction().begin();
		entity.remove(seccion);
		entity.getTransaction().commit();
	}

}
