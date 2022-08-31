package com.stefanini.form;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.stefanini.models.Product;

import lombok.Data;

@Data
public class ProductForm implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Name is required.")
	private String name;
	@NotNull(message = "Price is required.")
	private BigDecimal price;
	private String description;
	private Long companyCode;
	
	public Product update(Product product) {
		product.setName(name);
		product.setDescription(description);
		product.setPrice(price);
		return product;
	}
}
