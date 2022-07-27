package com.stefanini.aula11;

public class Principal {

	public static void main(String[] args) {
		Carro bmw = new Carro();
		bmw.setMarca("BMW");
		bmw.setModelo("M3");
		bmw.setQuilometrosPorLitro(7.5);
		bmw.setVelocidadeMaxima(280);

		Carro mercedes = new Carro("Mercedes", "C180");

		BMW bmwDois = new BMW();
		Tesla tesla = new Tesla();
		tesla.isDirigeSozinho();

		Carro teslaDois = new Tesla();
		((Tesla) teslaDois).setDirigeSozinho(true);
		System.out.println(((Tesla) teslaDois).isDirigeSozinho());

		Carro[] carros = new Carro[] { bmw, bmwDois, tesla, teslaDois };

		for (Carro carro : carros) {
			if (carro instanceof Tesla) {
				System.out.println(((Tesla) carro).isDirigeSozinho());
			}
		}
		
		bmwDois.calcularTaxaAceleracao(100.50, 10);
		tesla.calcularTaxaAceleracao(100.50, 10);
		
		/*
		 * System.out.println(bmwDois.calcularTaxaAceleracao(100.5, 10.5));
		 * 
		 * System.out.println(mercedes.getMarca());
		 * System.out.println(mercedes.getModelo());
		 * 
		 * System.out.println(bmw.getMarca()); System.out.println(bmw.getModelo());
		 * System.out.println(bmw.getQuilometrosPorLitro());
		 * System.out.println(bmw.getTaxaAceleracao());
		 * System.out.println(bmw.getVelocidadeMaxima());
		 * 
		 * bmw.acelerar(); bmw.frear();
		 * 
		 * System.out.println(bmw.calcularTaxaAceleracao(bmw.getVelocidadeMaxima(), 0,
		 * 10, 0));
		 * System.out.println(bmw.calcularTaxaAceleracao(bmw.getVelocidadeMaxima(),
		 * 10));
		 */
	}

}
