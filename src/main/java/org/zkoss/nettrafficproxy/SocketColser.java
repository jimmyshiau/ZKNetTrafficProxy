package org.zkoss.nettrafficproxy;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketColser {
	private int count = 2;
	private Socket incoming, outgoing;
	private List<Closeable> streams;

	public SocketColser(Socket incoming, Socket outgoing) {
		super();
		this.incoming = incoming;
		this.outgoing = outgoing;
		streams = new ArrayList<Closeable>(4);
	}

	public void doClose(InputStream in, OutputStream out) throws IOException {
		synchronized (this) {
			streams.add(in);
			streams.add(out);
			if (--count == 0) {
				
				for (Closeable stream : streams)
					stream.close();
					
				incoming.close();
				outgoing.close();
			}
		}
	}

}
