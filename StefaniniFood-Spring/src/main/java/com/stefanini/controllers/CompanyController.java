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

import com.stefanini.dto.CompanyDto;
import com.stefanini.form.CompanyForm;
import com.stefanini.services.CompanyService;

@RestController
@RequestMapping(value = "/company")
public class CompanyController {

	private CompanyService service;
	
	@Autowired
	public CompanyController(CompanyService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<List<CompanyDto>> findAll() {
		List<CompanyDto> list = service.findAll();
		return ResponseEntity.ok(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<CompanyDto> findById(@PathVariable Long id) {
		CompanyDto obj = service.findById(id);
		return ResponseEntity.ok(obj);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<CompanyDto> insert(@RequestBody @Valid CompanyForm form) {
		CompanyDto saved = service.insert(form);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	@PutMapping(value = "/{id}")
	@Transactional
	public ResponseEntity<CompanyDto> update(@PathVariable Long id, @RequestBody @Valid CompanyForm form) {
		CompanyDto updated = service.update(id, form);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping(value = "/{id}")
	@Transactional
	public ResponseEntity<Void> deleteById(@PathVariable Long id) {
		service.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
