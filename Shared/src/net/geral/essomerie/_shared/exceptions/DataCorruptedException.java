package net.geral.essomerie._shared.exceptions;

//TODO delete it?
@Deprecated
public class DataCorruptedException extends Exception {
	private static final long	serialVersionUID	= 1L;

	public DataCorruptedException(final String message) {
		super(message);
	}
}
