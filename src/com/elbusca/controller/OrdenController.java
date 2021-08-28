package com.elbusca.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.elbusca.dao.OrdenDaoImpl;
import com.elbusca.dao.ProductoDaoImpl;
import com.elbusca.dao.SeccionDaoImpl;
import com.elbusca.dto.ProductoDTO;
import com.elbusca.entity.Cliente;
import com.elbusca.entity.DetalleOrden;
import com.elbusca.entity.Orden;
import com.elbusca.entity.Producto;

@Controller
@RequestMapping("orden")
public class OrdenController {

	Date date = new Date();
	SimpleDateFormat formato = new SimpleDateFormat("YYYY-MM-dd");
	String fecha = formato.format(date);

	@Autowired
	ProductoDaoImpl producto_dao;

	@Autowired
	SeccionDaoImpl seccion_dao;

	@Autowired
	OrdenDaoImpl orden_dao;

	List<Orden> lista_ordenes = new ArrayList<>();

	@RequestMapping("/agregar")
	public String orden(Model modelo) {
		modelo.addAttribute("fecha", fecha);
		modelo.addAttribute("secciones", seccion_dao.listarSecciones());
		modelo.addAttribute("productos", producto_dao.listaProductos());
		
    	return "/comedor/AgregarOrden";
	}
	
	
	@RequestMapping(value = "/nuevaOrden")
	public String probar(@RequestParam("nombreCliente") String nombre, @RequestParam("telefonoCliente") String telefono,
			@RequestParam("producto") int [] productos, @RequestParam("cantidad") int [] cantidades,
			@RequestParam("importeOculto") int [] importes, @RequestParam("estadoDePago") int estadoPago,
			@RequestParam("totalOculto") int total, @RequestParam("metodoPago") int metodo,Model modelo) {
		
		// traer todos las entidades productos elegidos por el cliente
		
			
		// agregar los datos a la orden
		Orden orden = new Orden();
		orden.setFecha(fecha);
		orden.setEstado(orden_dao.getEstadoPago(estadoPago));
		orden.setMetodo(orden_dao.getFormaPago(metodo));
		orden.setTotal(total);
		orden.setCliente(new Cliente(nombre,telefono));
		orden.setTotalProductos(this.totalProductos(cantidades));
		orden.setEstadoEntrega(orden_dao.getEntrega(1));
			
		List<Producto> productoElegidos = new ArrayList<>();
		Producto p;
		
		for (int i = 0; i < productos.length; i++) {		
			p = producto_dao.buscar(productos[i]);
			productoElegidos.add(p);		
		}
		
		DetalleOrden detalleOrden;
		List<DetalleOrden> ordenes = new ArrayList<>();
		
		for(int i = 0; i < productoElegidos.size(); i++) {		
			detalleOrden = new DetalleOrden();
			detalleOrden.setProducto(productoElegidos.get(i));
			detalleOrden.setOrden(orden);
			detalleOrden.setCantidad(cantidades[i]);
			detalleOrden.setImporte(importes[i]);
		    ordenes.add(detalleOrden);	
		}
		
		orden_dao.agregar(ordenes, orden);
		return "redirect:/orden/agregar";
	}
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "/listaProductos")
	public List<ProductoDTO> listar(@RequestParam("id") int id) {

		List<ProductoDTO> lista = new ArrayList<>();

		for (Producto producto : seccion_dao.productos(id))
			lista.add(new ProductoDTO(producto));
		
		return lista;
	}

	
	
	@ResponseBody
	@RequestMapping(value = "/precio")
	public Integer precio(@RequestParam("id") int id) {
		
		return producto_dao.precioProducto(id);
	}

	

	@RequestMapping("/gestionar")
	public String gestionar(Model modelo) {
	
		modelo.addAttribute("ordenes", orden_dao.getOrdenes(fecha));

		return "/comedor/GestionarOrden";
	}

	
	@RequestMapping("/editar")
	public String editarOrden(@RequestParam("identificador") int id, Model modelo) {

		modelo.addAttribute("secciones", seccion_dao.listarSecciones());
		modelo.addAttribute("productos", producto_dao.listaProductos());
		modelo.addAttribute("orden", orden_dao.getOrdenRequerida(id));

		return "/comedor/EditarOrden";
	}
	
	@RequestMapping("/confirmarEntrega")
	public String confirmarEntrega(@RequestParam("id") int id_orden) {

		orden_dao.confirmarEntrega(id_orden);

		return "redirect:/orden/gestionar";
	}
	
	
	@RequestMapping("/realizarPago")
	public String realizarPago(@RequestParam("id") int id_orden, Model modelo) {
		
		orden_dao.realizarPago(id_orden);
			
		return "redirect:/orden/gestionar";
	}
	

	@RequestMapping("/actualizar")
	public String actualizarOrden(@RequestParam("idCliente") int id_cliente, @RequestParam("producto") int [] productos,
			@RequestParam("cantidad") int [] cantidades, @RequestParam("importeOculto") int[] importes,
			@RequestParam("estadoDePago") int estadoPago, @RequestParam("totalOculto") int total,
			@RequestParam("metodoPago") int metodo, @RequestParam("id_orden")int id,
			@RequestParam("ordenes_id") int [] idOrdenes) {
	
		// Actualizar orden
		Orden orden = orden_dao.getOrdenRequerida(id);
		orden.setFecha(fecha);
		orden.setEstado(orden_dao.getEstadoPago(estadoPago));
		orden.setMetodo(orden_dao.getFormaPago(metodo));
		orden.setTotal(total);
		orden.setTotalProductos(this.totalProductos(cantidades));
		orden.setEstadoEntrega(orden_dao.getEntrega(1));
		
		orden_dao.actualizarOrden(orden,id_cliente);
			
		// Agregando las entidades productos a la lista
		List<Producto> productoElegidos = new ArrayList();
		Producto p;
		
		for (int i = 0; i < productos.length; i++) {		
			p = producto_dao.buscar(productos[i]);
			productoElegidos.add(p);		
		}
				
		// Obteniendo las entidades mapeadas: detalle_orden, que pertenecen a la orden principal
	     Set<DetalleOrden> listaOrdenes = orden_dao.getOrdenRequerida(id).getOrden_detalle();    
	     Iterator<DetalleOrden> iterador = listaOrdenes.iterator();
     
	     ArrayList<DetalleOrden> orders = new ArrayList<>();
	     
	     //Agregando cada objeto 'detalle_orden' a la lista principal	     
	     while(iterador.hasNext()) 	    	 
	    	 orders.add(iterador.next());
	    	 	     	    
	    for (int i = 0; i < orders.size(); i++) {		
	    	orders.get(i).setProducto(productoElegidos.get(i));
	    	orders.get(i).setCantidad(cantidades[i]);
	    	orders.get(i).setImporte(importes[i]);
		}
	     
	    orden_dao.actualizarOrdenes(orders);   
	    
		return "redirect:/orden/gestionar";
	}
	
	
	@RequestMapping("/eliminar")
	public String eliminarOrden(@RequestParam("id") int id_orden) {
		
		Orden orden = orden_dao.getOrdenRequerida(id_orden);
		orden_dao.eliminarOrden(orden);
	
		return "redirect:/orden/gestionar";
	}
	
	
	public int totalProductos(int [] cantidades) {		
		int total = 0;	
		for(int cantidad : cantidades) 	
			total+= cantidad;
		
		return total;
	}

}
