package com.stefanini.aula08.executor;

import com.stefanini.aula08.Carro;
import com.stefanini.aula08.Mercedes;
import com.stefanini.aula08.entidade.BMW;

public class Executor {

	public static void main(String[] args) {
		Carro carro1 = new Carro();
		carro1.setMarca("BMW");
		carro1.setModelo("320");
		carro1.setChassi(9999);
		carro1.setPlaca("XXX9999");
		carro1.setBancos(5);
		carro1.setVelocidade(280);

		System.out.println(carro1.getMarca());
		System.out.println(carro1.getModelo());
		System.out.println(carro1.getChassi());
		System.out.println(carro1.getPlaca());
		System.out.println(carro1.getBancos());

		System.out.println(carro1.acelerar());

		Carro carro2 = new Carro("Mercedes");
		System.out.println(carro2.getMarca());

		System.out.println(soma(10, 5));
		System.out.println(soma(10, 15, 6));

		BMW bmw = new BMW();
		bmw.setVelocidade(100);
		System.out.println(bmw.acelerar());

		Mercedes mercedes = new Mercedes();
		System.out.println(mercedes.acelerar());

	}

	public static int soma(int a, int b) {
		return a + b;
	}

	public static int soma(int a, int b, int c) {
		return a + b + c;
	}

}
