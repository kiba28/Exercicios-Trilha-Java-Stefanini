package com.stefanini.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.stefanini.models.Company;
import com.stefanini.models.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Name is required.")
	private String name;
	private List<Product> products = new ArrayList<>();
	
	public Company update(Company company) {
		company.setName(name);
		return company;
	}

}
