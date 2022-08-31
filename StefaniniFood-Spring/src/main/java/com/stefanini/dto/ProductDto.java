package com.stefanini.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.stefanini.models.Company;

import lombok.Data;

@Data
public class ProductDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private BigDecimal price;
	private String description;
	private Company company;

}
