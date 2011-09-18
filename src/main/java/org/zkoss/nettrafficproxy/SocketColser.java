package org.zkoss.nettrafficproxy;

import java.io.IOException;
import java.net.Socket;

public class SocketColser {
	private int count = 2;
	private Socket incoming, outgoing;

	public SocketColser(Socket incoming, Socket outgoing) {
		super();
		this.incoming = incoming;
		this.outgoing = outgoing;
	}

	public void doClose() throws IOException {
//		synchronized (this) {
			if (--count == 0) {
				if (incoming != null)
					incoming.close();
				if (outgoing != null)
					outgoing.close();
			}
//		}

	}

}
