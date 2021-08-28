package com.elbusca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.elbusca.dao.ProductoDaoImpl;
import com.elbusca.dao.SeccionDaoImpl;
import com.elbusca.dto.ProductoDTO;
import com.elbusca.entity.Producto;
import com.elbusca.entity.Seccion;

@Controller
@RequestMapping("producto")
public class ProductoController {

	@Autowired
	ProductoDaoImpl producto_dao;

	@Autowired
	SeccionDaoImpl seccion_dao;
	
	
	@RequestMapping("/lista")
	public String menu(Model modelo) {

		modelo.addAttribute("productos", producto_dao.listaProductos());
		modelo.addAttribute("secciones", seccion_dao.listarSecciones());

		return "/comedor/Productos";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/datosProducto")
	public ProductoDTO productoRequerido(@RequestParam("id") int id) {

		Producto producto = producto_dao.buscar(id);
		ProductoDTO productoDTO = new ProductoDTO(producto);

		return productoDTO;
	}
	
	

	@RequestMapping(value = "/actualizar")
	public String actualizar(@RequestParam("editNombre") String nombre, @RequestParam("editPrecio") int precio,
			@RequestParam("editSeccion") int id_seccion, @RequestParam("editId") int id_producto) {

		Producto producto = producto_dao.buscar(id_producto);
		producto.setNombre(nombre);
		producto.setPrecio(precio);
		producto.setSeccion(seccion_dao.obtenerSeccion(id_seccion));
		producto_dao.actualizar(producto);
		return "redirect:/producto/lista";
	}
	
	
	
	
	@RequestMapping(value = "/eliminar")
	public String eliminarProducto(@RequestParam("id") int id_producto) {

		producto_dao.eliminar(id_producto);

		return "redirect:/producto/lista";
	}
	
	
	@PostMapping(value = "/agregar")
	public String agregar(@RequestParam("addNombre") String nombre, @RequestParam("addPrecio") int precio,
			@RequestParam("addSeccion") int id_seccion) {

		Seccion seccion = seccion_dao.obtenerSeccion(id_seccion);
		Producto producto = new Producto(nombre, precio, seccion);
	    producto_dao.agregar(producto);
		
		return "redirect:/producto/lista";
	}
	
	
	


}
