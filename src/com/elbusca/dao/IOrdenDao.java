package com.elbusca.dao;

import java.util.ArrayList;
import java.util.List;

import com.elbusca.entity.DetalleOrden;
import com.elbusca.entity.Entrega;
import com.elbusca.entity.EstadoPago;
import com.elbusca.entity.MetodoPago;
import com.elbusca.entity.Orden;

public interface IOrdenDao {

	
	
	public MetodoPago getFormaPago(int id);
	public EstadoPago getEstadoPago(int id);
	public Orden getOrdenRequerida(int id);
	public ArrayList<Orden> getOrdenes(String fecha);
	public void confirmarEntrega(int id);
    public void eliminarDetalleOrden(int id_detalle,int id_orden, int total);
    public void actualizarOrden(Orden orden, int id_cliente);
    public void agregar(List<DetalleOrden> ordenes,Orden orden);
    public void realizarPago(int id);
    public Entrega getEntrega(int estado);
    public void eliminarOrden(Orden orden);
    public void actualizarOrdenes(ArrayList<DetalleOrden> ordenes);
    
    

}
