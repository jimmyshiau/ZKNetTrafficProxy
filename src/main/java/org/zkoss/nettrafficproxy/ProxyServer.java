package org.zkoss.nettrafficproxy;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.util.Observable;


public class ProxyServer extends Observable {
	
	private boolean stop = true;
	private int proxyPort;
	private String host;
	private int port;
	private ServerSocket ss;
	private MessageHandler msgHdl;
	private File dataFolder;
	private String rootDir = "httpData";
	private int id = 0;
	
	public ProxyServer(int proxyPort, String host, int port) {
		super();
		this.proxyPort = proxyPort;
		this.host = host;
		this.port = port;
		msgHdl = new MessageHandler();
		MessageHandler.setInstance(msgHdl);
	}
	
	public ProxyServer(int proxyPort, String host, int port, MessageHandler msgHdl) {
		super();
		this.proxyPort = proxyPort;
		this.host = host;
		this.port = port;
		this.msgHdl = msgHdl;
		MessageHandler.setInstance(msgHdl);
	}

	public void start() {
		if (!stop)
			return;
		
		try {
			stop = false;
			
			initDataFolder();
			
			ss = new ServerSocket(proxyPort);
			msgHdl.log("Proxy strat .... port: " + proxyPort);
			
			
			new Thread(new Runnable() {
				
				public void run() {
					try {
						Socket webServer = null, browser = null;
						while (!stop) {
								browser = ss.accept();
							
//							webServer = new Socket(host, port);
//							new ProxySession(id, browser, webServer, 
//									msgHdl, dataFolder).start();
							
								
							String dirName = new DecimalFormat("0000").format(id++);
							File dir = Utils.createFolder(dataFolder, dirName);
							
								
							webServer = new Socket(host, port);
							SocketHandler sckHdler = new SocketHandler(dirName, browser, webServer);
							
							HTTPRequestProcessor reqProc = new HTTPRequestProcessor(
									browser.getInputStream(), 
									webServer.getOutputStream(), 
									dir, sckHdler);
							addObserver(reqProc);
							reqProc.start();
							
							HTTPResponseProcessor respProc = new HTTPResponseProcessor(
									webServer.getInputStream(),
									browser.getOutputStream(), 
									dir, sckHdler);
							addObserver(respProc);
							respProc.start();
						}
					} catch (SocketException e) {
						// ignore socket closed
					} catch (IOException e) {
						msgHdl.printStackTrace(e);
					} catch (Exception e) {
						msgHdl.printStackTrace(e);
					}
				}
			}).start();

		} catch (IOException e) {
			msgHdl.printStackTrace(e);
		}
	}

	public void initDataFolder() throws IOException {
		dataFolder = Utils.createFolder(rootDir);
	}

	public void stop() {
		if (stop)
			return;
		setChanged();
		notifyObservers();
		stop = true;
		try {
//			initDataFolder();
			if (ss != null)
				ss.close();
		} catch (IOException e) {
			msgHdl.printStackTrace(e);
		}
		msgHdl.log("Proxy stoped");
	}

	
}
