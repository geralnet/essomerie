package net.geral.essomerie.client._gui.sysadmin;

public class ScreenLogCache {
	public static final int	MAXIMO	= 50;

	private final byte[][]	imagens	= new byte[MAXIMO][];
	private final int[]		ids		= new int[MAXIMO];
	private int				next	= 0;

	public ScreenLogCache() {
		for (int i = 0; i < MAXIMO; i++) {
			imagens[i] = null;
			ids[i] = 0;
		}
	}

	public synchronized void add(final int id, final byte[] imagem) {
		ids[next] = id;
		imagens[next] = imagem;
		next++;
		if (next == MAXIMO) {
			next = 0;
		}
	}

	public synchronized byte[] get(final int id) {
		for (int i = 0; i < MAXIMO; i++) {
			if (ids[i] == id) { return imagens[i]; }
		}
		return null;
	}
}
