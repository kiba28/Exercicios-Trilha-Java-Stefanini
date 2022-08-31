package com.stefanini.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stefanini.exceptions.DefaultException;
import com.stefanini.respositories.ClientRepository;

@Service
public class AuthenticationService implements UserDetailsService {

	private final ClientRepository repository;

	@Autowired
	public AuthenticationService(ClientRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findByUsername(username).orElseThrow(
				() -> new DefaultException(403, "FORBIDDEN", "Invalid data, verify your username or password."));
	}

}
