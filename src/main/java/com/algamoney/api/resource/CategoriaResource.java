package com.algamoney.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algamoney.api.model.Categoria;
import com.algamoney.api.repository.CategoriaRepository;

//classe controladora REST 
//Mapegamento da requisicao

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	
	
	//injeta classe CategoriaRepository para ter acesso aos métodos JPA 
	@Autowired //implementacao de CategoriaRepository (new)
 	private CategoriaRepository categoriaRepository;
	
	@GetMapping //mapeamento GET para URL /categorias
	public List<Categoria> listar() {
		return categoriaRepository.findAll();
	}

}
