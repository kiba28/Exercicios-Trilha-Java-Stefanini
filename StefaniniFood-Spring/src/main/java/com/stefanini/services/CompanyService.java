package com.stefanini.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stefanini.dto.CompanyDto;
import com.stefanini.exceptions.DefaultException;
import com.stefanini.form.CompanyForm;
import com.stefanini.models.Company;
import com.stefanini.respositories.CompanyRepository;
import com.stefanini.respositories.ProductRepository;

@Service
public class CompanyService {

	private final CompanyRepository repository;
	private final ProductRepository productRepository;
	private final ModelMapper mapper;

	@Autowired
	public CompanyService(CompanyRepository repository, ModelMapper mapper, ProductRepository productRepository) {
		this.repository = repository;
		this.productRepository = productRepository;
		this.mapper = mapper;
	}

	public List<CompanyDto> findAll() {
		List<Company> companies = repository.findAll();
		return companies.stream().map(company -> mapper.map(company, CompanyDto.class)).toList();
	}

	public CompanyDto findById(Long id) {
		Company company = repository.findById(id)
				.orElseThrow(() -> new DefaultException(404, "NOT_FOUND", "Company with id = " + id + " not found"));
		return mapper.map(company, CompanyDto.class);
	}

	public CompanyDto insert(CompanyForm form) {
		if (form.getProducts().isEmpty()) {
			Company company = mapper.map(form, Company.class);
			Company saved = repository.save(company);
			return mapper.map(saved, CompanyDto.class);
		}
		Company company = mapper.map(form, Company.class);
		Company saved = repository.save(company);
		saved.getProducts().forEach(product -> {
			product.setCompany(saved);
			productRepository.save(product);
		});
		return mapper.map(saved, CompanyDto.class);
	}

	public CompanyDto update(Long id, CompanyForm form) {
		Company company = repository.findById(id)
				.orElseThrow(() -> new DefaultException(404, "NOT_FOUND", "Company with id = " + id + " not found"));
		company = form.update(company);
		Company savedDto = repository.save(company);
		return mapper.map(savedDto, CompanyDto.class);
	}

	public void deleteById(Long id) {
		Company company = repository.findById(id)
				.orElseThrow(() -> new DefaultException(404, "NOT_FOUND", "Company with id = " + id + " not found"));
		repository.delete(company);
	}

}
