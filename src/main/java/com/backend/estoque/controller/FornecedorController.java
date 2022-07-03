package com.backend.estoque.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.backend.estoque.model.Fornecedor;
import com.backend.estoque.repository.FornecedorRepository;

@RestController
@RequestMapping("/fornecedor")
@CrossOrigin(origins = "*" , allowedHeaders = "*")
public class FornecedorController {

	
	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	@GetMapping
	public ResponseEntity<List<Fornecedor>> getAll() {
		return ResponseEntity.ok(fornecedorRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Fornecedor> getById(@PathVariable Long id) {
		return fornecedorRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@PostMapping
	public ResponseEntity<Fornecedor> post(@Valid @RequestBody Fornecedor fornecedor ) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(fornecedorRepository.save(fornecedor));
	}
	
	@PutMapping
	public ResponseEntity<Fornecedor> put(@Valid @RequestBody Fornecedor fornecedor) {
		return fornecedorRepository.findById(fornecedor.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.CREATED)
					.body(fornecedorRepository.save(fornecedor)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		java.util.Optional<Fornecedor> categoria = fornecedorRepository.findById(id);
		
		if(categoria.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		fornecedorRepository.deleteById(id);
	}
}
