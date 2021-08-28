package com.elbusca.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import javax.persistence.Persistence;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.elbusca.entity.Cliente;
import com.elbusca.entity.DetalleOrden;
import com.elbusca.entity.Entrega;
import com.elbusca.entity.EstadoPago;
import com.elbusca.entity.MetodoPago;
import com.elbusca.entity.Orden;

@Repository
public class OrdenDaoImpl implements IOrdenDao {

	private EntityManagerFactory factory;
	private EntityManager entity;

	public OrdenDaoImpl() {
		factory = Persistence.createEntityManagerFactory("persistenceElBusca");
		entity = factory.createEntityManager();
	}

	/**
	 * Este metodo devuelve la forma de pago seleccionada
	 * @param id - es la llave primaria del metodo de pago a obtener
	 * @return metodo pago - entidad encontrada
	 */
	@Override
	public MetodoPago getFormaPago(int id) {
		MetodoPago metodo = entity.find(MetodoPago.class, id);
		return metodo;
	}

	
	/**
	 * Este metodo devuelve el estado de la orden, si fue pagada o no
	 * @param id - llave primaria del estado de pago
	 * @return estado pago- entidad encontrada
	 */
	@Override
	public EstadoPago getEstadoPago(int id) {
		EstadoPago estado = entity.find(EstadoPago.class, id);
		return estado;
	}

		
	/**
	 * Este metodo guarda las ordenes de comidas del cliente
	 * @param listaOrdenes - Es la lista de detalle_orden.
	 * @param orden - Es la orden principal
	 */
	@Override
	public void agregar(List<DetalleOrden> listaOrdenes, Orden orden) {

		for (int i = 0; i < listaOrdenes.size(); i++)
			orden.agregarDetalleOrden(listaOrdenes.get(i));

		entity.getTransaction().begin();
		entity.persist(orden);
		entity.getTransaction().commit();
	}
	

	/**
	 * Tiene como objetivo traer informacion de la orden solicitada
	 * @param id - es el identificador de la orden que se requiere
	 * @return orden - orden encontrada
	 */
	@Override
	public Orden getOrdenRequerida(int id) {
		Orden orden = entity.find(Orden.class, id);
		return orden;
	}

	
	/**
	 * Este metodo muestra todas las ordenes que están sin entregar en la fecha actual
	 * @param fecha - fecha actual
	 * @return ordenes - contiene las ordenes que corresponden a la fecha actual
	 */
	@Override
	public ArrayList<Orden> getOrdenes(String fecha) {

		Query query = entity
				.createQuery("FROM Orden o WHERE o.fecha = : orden_fecha AND o.estadoEntrega = : sinEntregar");

		query.setParameter("orden_fecha", fecha);
		query.setParameter("sinEntregar", this.getEntrega(1));
		ArrayList<Orden> ordenes = (ArrayList<Orden>) query.getResultList();

		return ordenes;
	}

	
	
	/**
	 * Este metodo actualiza el estado de la orden elegida, a un estado de
	 * 'Entregado'
	 * @param id - es la llave primaria y sirve para buscar la orden
	 */
	@Override
	public void confirmarEntrega(int id) {
		Orden orden = this.getOrdenRequerida(id);
		orden.setEstadoEntrega(this.getEntrega(2));

		entity.getTransaction().begin();
		entity.merge(orden);
		entity.getTransaction().commit();
	}

	
	/**
	 * Este metodo actualiza la orden principal
	 * @param orden - Orden que sera actualizada
	 * @param id_cliente - Sirve para buscar el cliente que pidio la orden
	 */
	@Override
	public void actualizarOrden(Orden orden, int id_cliente) {
		Cliente cliente = entity.find(Cliente.class, id_cliente);
		orden.setCliente(cliente);

		entity.getTransaction().begin();
		entity.merge(orden);
		entity.getTransaction().commit();
	}


	/**
	 * Este metodo elimina una de las ordenes del cliente.
	 * @param id_detalle - Sirve para buscar el detalleOrden
	 * @param id_orden - Es la llave primaria de la orden principal
	 * @param total - Es el total de la orden
	 */
	@Override
	public void eliminarDetalleOrden(int id_detalle, int id_orden, int total) {
		int totalUpdate = 0;

		try {
			entity.getTransaction().begin();
     		// 1) obtener el importe de la orden elegida
			int importe = (int) entity.createQuery("SELECT d.importe FROM DetalleOrden d WHERE id=:id_detalle")
					.setParameter("id_detalle", id_detalle).getSingleResult();

			// 2) actualizar el total de la orden principal
			totalUpdate = total - importe;
			entity.createQuery("UPDATE Orden o SET o.total =: totalUpdate WHERE id=: id_orden")
					.setParameter("totalUpdate", totalUpdate).setParameter("id_orden", id_orden).executeUpdate();

			// 3) eliminar la orden elegida
			entity.createQuery("DELETE DetalleOrden d WHERE d.id =: id_detalle").setParameter("id_detalle", id_detalle)
					.executeUpdate();
			entity.getTransaction().commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Este metodo confirma que la orden fue pagada.
	 * @param id - Es la llave primaria de la orden que fue pagada
	 */
	@Override
	public void realizarPago(int id) {

		Orden orden = this.getOrdenRequerida(id);
		orden.setEstado(this.getEstadoPago(1));

		entity.getTransaction().begin();
		entity.merge(orden);
		entity.getTransaction().commit();
	}

	
	/**
	 * Este metodo devuelve el estado de la orden, si fue entregado o no.
	 * @param estado - Es el id del estado de entrega
	 * @param entraga - es el estado
	 */
	@Override
	public Entrega getEntrega(int estado) {
		Entrega entrega = entity.find(Entrega.class, estado);
		return entrega;
	}

	
	/**
	 * Este metodo tiene como objetivo eliminar la orden de la base de datos.
	 * @param orden - es la orden que sera eliminado
	 */
	@Override
	public void eliminarOrden(Orden orden) {
		entity.getTransaction().begin();
		entity.remove(orden);
		entity.getTransaction().commit();
	}

	
	/**
	 * Este metodo actualiza las ordenes del cliente
	 * @param ordenes- son las ordenes que seran actualizadas
	 */
	@Override
	public void actualizarOrdenes(ArrayList<DetalleOrden> ordenes) {
		for (int i = 0; i < ordenes.size(); i++) {
			entity.getTransaction().begin();
			entity.merge(ordenes.get(i));
			entity.getTransaction().commit();
		}
	}

}
