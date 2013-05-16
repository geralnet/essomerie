package net.geral.essomerie._shared;

import java.io.File;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.Random;

public final class CRUtil {
	// exposed fields
	public static final String	RESOURCES_PATH	= "res/";
	public static final File	RESOURCES_FILE	= new File(RESOURCES_PATH);
	public static final Random	Random			= new Random();

	public static Calendar amd2calendar(final String amd) {
		final String[] p = amd.split("-");
		final Calendar c = Calendar.getInstance();
		c.clear();
		final int a = Integer.parseInt(p[0]);
		final int m = Integer.parseInt(p[1]);
		final int d = Integer.parseInt(p[2]);
		c.set(a, m - 1, d);
		return c;
	}

	public static String calendar2amd(final Calendar c) {
		return String.format("%04d-%02d-%02d", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1,
				c.get(Calendar.DAY_OF_MONTH));
	}

	public static String calendar2dma(final Calendar c) {
		return String.format("%02d/%02d/%04d", c.get(Calendar.DAY_OF_MONTH),
				c.get(Calendar.MONTH) + 1, c.get(Calendar.YEAR));
	}

	public static String getComputador(final String notFound) {
		try {
			return InetAddress.getLocalHost().getHostName().toUpperCase();
		}
		catch (final Exception e) {
			return notFound;
		}
	}

	public static String getDiaSemana(final int i) {
		switch (i) {
			case 0:
				return "Domingo";
			case 1:
				return "Segunda-Feira";
			case 2:
				return "Terça-Feira";
			case 3:
				return "Quarta-Feira";
			case 4:
				return "Quinta-Feira";
			case 5:
				return "Sexta-Feira";
			case 6:
				return "Sábado";
			case 7:
				return "Domingo";
			default:
				return "na";
		}
	}

	private CRUtil() {}
}
