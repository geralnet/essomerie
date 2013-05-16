package net.geral.essomerie.client._gui.caixa;

import java.util.Calendar;

import net.geral.essomerie._shared.CRUtil;

public class CaixaDetalhes {
	public static final char	SEPARADOR	= '\t';

	public static CaixaDetalhes load(final String s) {
		final String[] p = s.split(String.valueOf(SEPARADOR));
		final String computador = p[0];
		final Calendar data = CRUtil.amd2calendar(p[1]);
		final boolean dia = "Dia".equalsIgnoreCase(p[2]);
		return new CaixaDetalhes(data, dia, computador);
	}

	private Calendar	data;
	private boolean		dia;
	private String		computador;

	public CaixaDetalhes() {
		data = Calendar.getInstance();
		dia = true;
		computador = CRUtil.getComputador("NA");
	}

	public CaixaDetalhes(final CaixaDetalhes detalhes) {
		dia = detalhes.dia;
		data = (Calendar)detalhes.data.clone();
		computador = detalhes.computador;
	}

	public CaixaDetalhes(final Calendar data, final boolean dia, final String computador) {
		this.data = data;
		this.dia = dia;
		this.computador = computador;
	}

	public String getComputador() {
		return computador;
	}

	public Calendar getData() {
		return (Calendar)data.clone();
	}

	public boolean getDia() {
		return dia;
	}

	public String getDiaNoite() {
		return (dia ? "Dia" : "Noite");
	}

	public void set(final CaixaDetalhes cd) {
		computador = cd.computador;
		data = cd.data;
		dia = cd.dia;
	}

	@Override
	public String toString() {
		final String s = String.valueOf(SEPARADOR);
		return computador.replaceAll(s, "") + SEPARADOR + CRUtil.calendar2amd(data) + SEPARADOR + getDiaNoite();
	}
}
