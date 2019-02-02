package com.algamoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algamoney.api.event.RecursoCriadoEvent;
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
	
	@Autowired
	private ApplicationEventPublisher publisher; //publicador de evento listener 
	
	@GetMapping //mapeamento GET para URL /categorias
	public List<Categoria> listar() {
		return categoriaRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) { //@RequestBody pega o Json e tranforma em objeto Categoria
		Categoria categoriaSalva = categoriaRepository.save(categoria);		
		
		publisher.publishEvent(new RecursoCriadoEvent(this,response, categoriaSalva.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
		
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity <Categoria> buscarPeloCodigo(@PathVariable Long codigo) {
		Categoria categoria = categoriaRepository.findOne(codigo);
		return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build(); //devolvendo not found caso não encontre a categoria
	}

}
