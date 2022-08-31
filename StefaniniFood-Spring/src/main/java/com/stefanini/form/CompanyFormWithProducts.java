package com.stefanini.form;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.stefanini.models.Company;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyFormWithProducts implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Name is required.")
	private String name;

	public Company update(Company company) {
		company.setName(name);
		return company;
	}

}
