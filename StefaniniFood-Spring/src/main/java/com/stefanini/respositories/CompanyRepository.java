package com.stefanini.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stefanini.models.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
