package com.stefanini.respositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stefanini.models.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
	
	Optional<Client> findByUsername(String username);
}
