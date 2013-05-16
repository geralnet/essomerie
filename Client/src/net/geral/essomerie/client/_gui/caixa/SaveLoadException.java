package net.geral.essomerie.client._gui.caixa;

public class SaveLoadException extends Exception {
	private static final long	serialVersionUID	= 1L;

	public SaveLoadException(final Exception e) {
		super(e.getClass() + "[" + e.getMessage() + "]", e);
	}

	public SaveLoadException(final String string) {
		super(string);
	}
}
