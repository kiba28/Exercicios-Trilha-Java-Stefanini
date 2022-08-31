package com.stefanini.controllers;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stefanini.dto.ProductDto;
import com.stefanini.form.ProductForm;
import com.stefanini.services.ProductService;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

	private ProductService service;

	@Autowired
	public ProductController(ProductService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<List<ProductDto>> findAll() {
		List<ProductDto> list = service.findAll();
		return ResponseEntity.ok(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDto> findById(@PathVariable Long id) {
		ProductDto obj = service.findById(id);
		return ResponseEntity.ok(obj);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<ProductDto> insert(@RequestBody @Valid ProductForm form) {
		ProductDto saved = service.insert(form);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	@PutMapping(value = "/{id}")
	@Transactional
	public ResponseEntity<ProductDto> update(@PathVariable Long id, @RequestBody @Valid ProductForm form) {
		ProductDto updated = service.update(id, form);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping(value = "/{id}")
	@Transactional
	public ResponseEntity<Void> deleteById(@PathVariable Long id) {
		service.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
