package com.stefanini.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.stefanini.models.Client;
import com.stefanini.respositories.ClientRepository;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private final TokenService tokenService;
	private final ClientRepository clientRepository;

	public TokenAuthenticationFilter(TokenService tokenService, ClientRepository clientRepository) {
		this.tokenService = tokenService;
		this.clientRepository = clientRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = retriveToken(request);
		boolean valid = tokenService.isTokenValid(token);
		if (valid) {
			authClient(token);
		}

		filterChain.doFilter(request, response);
	}

	private void authClient(String token) {
		Long clientId = tokenService.getClientId(token);
		Client client = clientRepository.findById(clientId).get();
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(client, null,
				client.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private String retriveToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}

		return token.substring(7, token.length());
	}

}
