package org.zkoss.nettrafficproxy;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketHandler {
	private int count = 2;
	private String dirName;
	private String resourceName;
	private Socket incoming, outgoing;
	private List<Closeable> streams;

	public SocketHandler(String dirName, Socket incoming, Socket outgoing) {
		super();
		this.dirName = dirName;
		this.incoming = incoming;
		this.outgoing = outgoing;
		streams = new ArrayList<Closeable>(4);
	}

	public void doClose(InputStream in, OutputStream out, MessageHandler msgHdl)
			throws IOException {
		synchronized (this) {

			streams.add(in);
			streams.add(out);
			if (--count == 0) {

				for (Closeable stream : streams)
					stream.close();

				incoming.close();
				outgoing.close();
				msgHdl.log("Request " + dirName + " recording finished.");
			}
		}
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
}
