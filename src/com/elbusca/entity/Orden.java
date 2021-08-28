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
@Table(name = "orden")
public class Orden {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_orden")
	private int id;	
	private String fecha;
	private int totalProductos;
	
	
	@ManyToOne
	@JoinColumn(name = "estadoPago_id")
	private EstadoPago estado;
	
	@ManyToOne
	@JoinColumn(name = "metodo_pago_id")
	private MetodoPago metodo;
	
	
	private int total;
	
	@ManyToOne
	@JoinColumn(name = "entrega_id")
	private Entrega estadoEntrega;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	@OneToMany(mappedBy = "orden",cascade = CascadeType.PERSIST)
	private Set<DetalleOrden> orden_detalle = new HashSet<>();
	

	public Orden() {
	
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public EstadoPago getEstado() {
		return estado;
	}

	public void setEstado(EstadoPago estado) {
		this.estado = estado;
	}

	public MetodoPago getMetodo() {
		return metodo;
	}

	public void setMetodo(MetodoPago metodo) {
		this.metodo = metodo;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Set<DetalleOrden> getOrden_detalle() {
		return orden_detalle;
	}

	public void setOrden_detalle(Set<DetalleOrden> orden_detalle) {
		this.orden_detalle = orden_detalle;
	}

	public void agregarDetalleOrden(DetalleOrden detalle) {
		
		orden_detalle.add(detalle);
		
		
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public int getTotalProductos() {
		return totalProductos;
	}

	public void setTotalProductos(int totalProductos) {
		this.totalProductos = totalProductos;
	}

	public Entrega getEstadoEntrega() {
		return estadoEntrega;
	}

	public void setEstadoEntrega(Entrega estadoEntrega) {
		this.estadoEntrega = estadoEntrega;
	}
	
	
	
	
	
}
