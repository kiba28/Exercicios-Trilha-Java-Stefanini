package com.stefanini.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.stefanini.respositories.ClientRepository;

@SuppressWarnings("deprecation")
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final AuthenticationService authenticationService;
	private final TokenService tokenService;
	private final ClientRepository clientRepository;

	@Autowired
	public SecurityConfiguration(AuthenticationService authenticationService, TokenService tokenService, ClientRepository clientRepository) {
		this.authenticationService = authenticationService;
		this.tokenService = tokenService;
		this.clientRepository = clientRepository;
	}

	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	// autenticação
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authenticationService).passwordEncoder(new BCryptPasswordEncoder());
	}

	// autorização
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
				.antMatchers(HttpMethod.GET, "/company").permitAll()
				.antMatchers(HttpMethod.GET, "/product").permitAll()
				.antMatchers(HttpMethod.GET, "/company/*").permitAll()
				.antMatchers(HttpMethod.GET, "/product/*").permitAll()
				.antMatchers(HttpMethod.POST, "/auth").permitAll()
				.anyRequest().authenticated()
				.and().csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().addFilterBefore(new TokenAuthenticationFilter(tokenService, clientRepository), UsernamePasswordAuthenticationFilter.class);
	}

	// recursos estáticos
	@Override
	public void configure(WebSecurity web) throws Exception {
	}

}
