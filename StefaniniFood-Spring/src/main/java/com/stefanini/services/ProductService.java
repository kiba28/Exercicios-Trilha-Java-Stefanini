package com.stefanini.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stefanini.dto.ProductDto;
import com.stefanini.exceptions.DefaultException;
import com.stefanini.form.ProductForm;
import com.stefanini.models.Company;
import com.stefanini.models.Product;
import com.stefanini.respositories.CompanyRepository;
import com.stefanini.respositories.ProductRepository;

@Service
public class ProductService {

	private final ProductRepository repository;
	private final CompanyRepository companyRepository;
	private final ModelMapper mapper;

	@Autowired
	public ProductService(ProductRepository repository, CompanyRepository companyRepository, ModelMapper mapper) {
		this.repository = repository;
		this.companyRepository = companyRepository;
		this.mapper = mapper;
	}

	public List<ProductDto> findAll() {
		List<Product> products = repository.findAll();
		return products.stream().map(product -> mapper.map(product, ProductDto.class)).toList();
	}

	public ProductDto findById(Long id) {
		Product product = repository.findById(id)
				.orElseThrow(() -> new DefaultException(404, "NOT_FOUND", "Product with id = " + id + " not found"));
		return mapper.map(product, ProductDto.class);
	}

	public ProductDto insert(ProductForm form) {
		Company company = companyRepository.findById(form.getCompanyCode()).orElseThrow(() -> new DefaultException(404,
				"NOT_FOUND", "Company with id = " + form.getCompanyCode() + " not found"));
		Product product = mapper.map(form, Product.class);
		product.setCompany(company);
		Product saved = repository.save(product);
		return mapper.map(saved, ProductDto.class);
	}

	public ProductDto update(Long id, ProductForm form) {
		Product product = repository.findById(id)
				.orElseThrow(() -> new DefaultException(404, "NOT_FOUND", "Product with id = " + id + " not found"));
		product = form.update(product);
		repository.save(product);
		return mapper.map(product, ProductDto.class);
	}

	public void deleteById(Long id) {
		Product product = repository.findById(id)
				.orElseThrow(() -> new DefaultException(404, "NOT_FOUND", "Product with id = " + id + " not found"));
		repository.delete(product);
	}

}
