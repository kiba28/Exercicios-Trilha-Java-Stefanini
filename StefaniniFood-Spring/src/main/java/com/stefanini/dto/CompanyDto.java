package com.stefanini.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.stefanini.models.Company;
import com.stefanini.models.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private List<Product> products = new ArrayList<>();

	public Company update(Company company) {
		company.setName(name);
		return company;
	}

}
