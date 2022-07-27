package com.stefanini.aula08.entidade;

import com.stefanini.aula08.Carro;

public class BMW extends Carro {

	public BMW() {
		this.setMarca("BMW");
	}

	@Override
	public String acelerar() {
		return "A BMW pode fazer de 0 a " + this.getVelocidade() + " em 5s";
	}

}
