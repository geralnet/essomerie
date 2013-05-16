package net.geral.essomerie._shared;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

public class ByteWriter extends OutputStream {
	private static final int		BUFFER_SIZE	= 1024;
	private final Vector<byte[]>	buffers		= new Vector<byte[]>();
	private byte[]					buffer		= new byte[BUFFER_SIZE];
	private int						offset		= 0;
	
	private int copy(final byte[] to, int offset, final byte[] from, final int length) {
		for (int i = 0; i < length; i++) {
			to[offset++] = from[i];
		}
		return offset;
	}
	
	public byte[] getBytes() {
		final byte[] bytes = new byte[size()];
		int pos = 0;
		for (final byte[] bs : buffers) {
			pos = copy(bytes, pos, bs, BUFFER_SIZE);
		}
		pos = copy(bytes, pos, buffer, offset);
		return bytes;
	}
	
	public int size() {
		return buffers.size() * BUFFER_SIZE + offset;
	}
	
	@Override
	public String toString() {
		return "ByteWriter[" + size() + "]";
	}
	
	@Override
	public void write(final int b) throws IOException {
		if (offset >= BUFFER_SIZE) {
			buffers.add(buffer);
			buffer = new byte[BUFFER_SIZE];
			offset = 0;
		}
		buffer[offset++] = (byte)b;
	}
}
