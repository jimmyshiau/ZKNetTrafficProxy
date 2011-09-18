package org.zkoss.nettrafficproxy;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.io.FileUtils;
import org.zkoss.nettrafficproxy.api.MessageHandler;

public class ProxyServer {
	
	private boolean stop = true;
	private int proxyPort;
	private String host;
	private int port;
	private ServerSocket ss;
	private MessageHandler msgHdl;
	private String rootDir = "httpData";
	private int id = 0;
	
	public ProxyServer(int proxyPort, String host, int port) {
		super();
		this.proxyPort = proxyPort;
		this.host = host;
		this.port = port;
		msgHdl = new SimpleMessageHandler();
	}
	
	public ProxyServer(int proxyPort, String host, int port, MessageHandler msgHdl) {
		super();
		this.proxyPort = proxyPort;
		this.host = host;
		this.port = port;
		this.msgHdl = msgHdl;
	}

	public void start() {
		try {
			stop = false;
			
			initRootfolder();
			
			ss = new ServerSocket(proxyPort);
			msgHdl.log("Proxy strat ....");
			Socket webServer = null, browser = null;
			while (!stop) {
				browser = ss.accept();
				webServer = new Socket(host, port);
				SocketColser closer = new SocketColser(browser, webServer);
				
				new HTTPRequestProcessor(browser.getInputStream(),
						webServer.getOutputStream(), 1024, new File(rootDir, "Request"+id), closer, msgHdl).start();
				new HTTPResponseProcessor(webServer.getInputStream(),
						browser.getOutputStream(), 4096, new File(rootDir, "Response"+id), closer, msgHdl).start();
				id++;
			}

		} catch (IOException e) {
			msgHdl.printStackTrace(e);
		}
	}

	private void initRootfolder() throws IOException {
		
		File root = new File(rootDir);
		if (root.exists())
			FileUtils.cleanDirectory(root);
		else
			new File(rootDir).mkdir();
	}

	public void stop() {
		stop = true;
		try {
			if (ss != null)
				ss.close();
		} catch (IOException e) {
			msgHdl.printStackTrace(e);
		}
	}

	
}
