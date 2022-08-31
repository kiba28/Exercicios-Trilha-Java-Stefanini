package com.stefanini.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stefanini.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
