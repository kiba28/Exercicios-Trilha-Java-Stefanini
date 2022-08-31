package com.stefanini.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ClientDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String username;
	private String password;

	public ClientDto(String username, String password) {
		this.username = username;
		this.password = password;
	}

}
