package com.stefanini.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.stefanini.models.Client;
import com.stefanini.respositories.ClientRepository;

@Configuration
@Profile("tests")
public class TestConfiguration {

	@Bean
	CommandLineRunner commandLineRunner(ClientRepository repository) {
		return args -> {
			Client jose = new Client("jlribeiro2", new BCryptPasswordEncoder().encode("12345"));
			repository.save(jose);
		};
	}

}
