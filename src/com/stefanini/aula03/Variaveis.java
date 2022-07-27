package com.stefanini.aula03;

public class Variaveis {

	public static void main(String[] args) {
		byte numByte = 127; // -128 a 127 (8 bits)
		short numShort = 32767; // -32768 a 32767 (16 bits)
		int numInt = 2147483647; // -2.147.483.648 a 2.147.483.647 (32 bits)
		long numLong = 2147483647; // -9.223.372.036.854.770.000 a 9.223.372.036.854.770.000 (64 bits)

		float numFloat = 10.000f; // -1,4024E-37 a 3.40282347E+38 (32 bits)
		double numDouble = 100000000000000.000; // -4,94E-307 a 1.79769313486231570E+308 (64 bits)

		char varChar = 'F'; // 0 a 65535 (16 bits)

		boolean varBoolean = true; // true ou false (1 bit)

		String texto = "Bem vindos Devs!";
		Integer numeroConta = 4000;
		Long numeroLongo = 1213123L;

		// Ele deve começar com uma letra minúscula. Exemplo: nome
		// Não deve começar com caracteres especiais como & (e comercial), $ (dólar), _
		// (sublinhado). Exemplo: _nome
		// Se o nome for composto por mais de uma palavra, inicie-se a primeira palavra
		// com a letra minúscula seguida por uma letra maiúscula. Exemplo: nomeCompleto
		// Evite usar variáveis de um caractere. Exemplo: a

		String nomeCompleto = "Davide";
	}

}
