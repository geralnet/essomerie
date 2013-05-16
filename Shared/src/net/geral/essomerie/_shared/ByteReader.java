package net.geral.essomerie._shared;

import java.io.IOException;
import java.io.InputStream;

public class ByteReader extends InputStream {
	private final byte[]	bytes;
	private int				offset;
	
	public ByteReader(final byte[] data) {
		bytes = data;
		offset = 0;
	}
	
	@Override
	public int read() throws IOException {
		if (offset >= bytes.length) return -1;
		return bytes[offset++];
	}
}
