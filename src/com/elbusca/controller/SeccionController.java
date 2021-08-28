package com.elbusca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.elbusca.dao.ISeccionDao;
import com.elbusca.dto.SeccionDTO;
import com.elbusca.entity.Seccion;

@Controller
@RequestMapping("/seccion")
public class SeccionController {

	
	@Autowired
	ISeccionDao seccion_dao;
	
	@RequestMapping("/lista")
	public ModelAndView listar() {
		
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("secciones",seccion_dao.listarSecciones());
		mv.setViewName("comedor/Secciones");
		
		return mv;
		
	}
	
	@PostMapping("/agregar")
	public String agregar(@RequestParam("nombre") String nombre) {
		
		seccion_dao.agregar(new Seccion(nombre));
		
		return "redirect:/seccion/lista";
		
	}
	
	
	
	@GetMapping("/leer")
	@ResponseBody
	public SeccionDTO leer(@RequestParam("id") int id) {
				 			
		Seccion seccion = seccion_dao.obtenerSeccion(id);	
		SeccionDTO seccionDto = new SeccionDTO(seccion);
		
		return seccionDto;
		
	}
	
	
	@PostMapping("/actualizar")
	public String actualizar(@RequestParam("nombre") String nombre, 
			                 @RequestParam("id") int id) {
	
	   Seccion seccion = seccion_dao.obtenerSeccion(id);
	   seccion.setNombre(nombre);    
	   seccion_dao.actualizar(seccion);		
	  return "redirect:/seccion/lista";
		
	}
	
	@GetMapping("/eliminar")
	public String eliminar(@RequestParam("id") int id) {
	
	   Seccion seccion = seccion_dao.obtenerSeccion(id);     
	   seccion_dao.eliminar(seccion);
			
	  return "redirect:/seccion/lista";
		
	}
	
}
