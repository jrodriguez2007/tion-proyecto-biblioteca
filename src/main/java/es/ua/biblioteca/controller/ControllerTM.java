package es.ua.biblioteca.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import es.ua.biblioteca.service.IBookService;

@Controller
public class ControllerTM {
	
	@Autowired
    private IBookService bookService;

	
	@RequestMapping("/")
	public String hola(Model modelo) {

		modelo.addAttribute("mensaje", "Ejemplo biblioteca en Spring Boot");
		return "index";
	}

}
