package net.geral.essomerie._shared;

import java.io.Serializable;

public class CPF implements Serializable {
	public class CPFException extends Exception {
		private static final long	serialVersionUID	= 1L;
	}

	private static int calculaVerificador(final int digitos) {
		final int d1 = calculaVerificador1(digitos);
		final int d2 = calculaVerificador2(d1, digitos);
		return (d1 * 10) + d2;
	}

	private static int calculaVerificador1(int digitos) {
		int soma = 0;
		int fator = 2;
		while (digitos > 0) {
			final int d = digitos % 10;
			digitos /= 10;
			soma += d * fator;
			fator++;
		}
		soma %= 11;
		if (soma <= 1) return 0;
		return 11 - soma;
	}

	private static int calculaVerificador2(final int d1, final int digitos) {
		long numeros = ((long)digitos * 10) + d1;
		int soma = 0;
		int fator = 2;
		while (numeros > 0) {
			final int d = (int)(numeros % 10);
			numeros /= 10;
			soma += d * fator;
			fator++;
		}
		soma %= 11;
		if (soma <= 1) return 0;
		return 11 - soma;
	}

	private static boolean checkCPF(final long numero) {
		if (numero == 0) return true; // nulo
		if (numero <= 2) return false; // pelo menos um numero e dois verificadores
		if (String.valueOf(numero).length() > 11) return false;

		final int digitos = (int)(numero / 100);
		final int verificador = (int)(numero % 100);
		final int verificadorCalculado = calculaVerificador(digitos);

		return (verificador == verificadorCalculado);
	}

	public static CPF createOrException(final long n) throws CPFException {
		return new CPF(n);
	}

	public static CPF createOrZero(final long n) {
		try {
			return new CPF(((n > 0) && checkCPF(n)) ? n : 0L);
		}
		catch (final CPFException e) {
			throw new RuntimeException(e);
		}
	}

	public static CPF createOrZero(String s) {
		s = s.replace("[\\D]", "");
		try {
			return createOrZero(Long.parseLong(s));
		}
		catch (final NumberFormatException e) {
			return createOrZero(0);
		}
	}

	public static boolean validate(final CPF cpf) {
		return checkCPF(cpf.numero);
	}

	private final long	numero;

	private CPF(final long _numero) throws CPFException {
		if (!checkCPF(_numero)) throw new CPFException();
		numero = _numero;
	}

	public long getNumero() {
		return numero;
	}

	public boolean isZero() {
		return (numero == 0);
	}

	@Override
	public String toString() {
		if (numero == 0) return "";
		long cpf = numero;
		final int ver = (int)(cpf % 100);
		cpf /= 100;
		final int p3 = (int)(cpf % 1000);
		cpf /= 1000;
		final int p2 = (int)(cpf % 1000);
		cpf /= 1000;
		final int p1 = (int)cpf;
		return String.format("%03d.%03d.%03d-%02d", p1, p2, p3, ver);
	}
}
