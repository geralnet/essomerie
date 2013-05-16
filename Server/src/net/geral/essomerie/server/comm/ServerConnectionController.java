package net.geral.essomerie.server.comm;

import net.geral.essomerie._shared.communication.ConnectionController;
import net.geral.essomerie._shared.communication.IMessageType;
import net.geral.essomerie._shared.communication.MessageSubSystem;

public abstract class ServerConnectionController<T extends Enum<? extends IMessageType>>
	extends ConnectionController<T> {
    protected final Connection connection;

    public ServerConnectionController(final Connection c,
	    final MessageSubSystem mss) {
	super(c, null, mss);
	connection = c;
    }
}
