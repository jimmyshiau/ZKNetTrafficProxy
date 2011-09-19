package org.zkoss.nettrafficproxy;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class SimpleProxyServer {
	public static void main(String[] args) {
		String host = "localhost"; // 代理的對象主機
		int remotePort = 8080; // 代理對象連接埠
		int localPort = 8001; // 本機連接埠

			

			runServer(host, remotePort, localPort);
	}

	public static void runServer(String host, int remotePort, int localPort) {
		try {
			System.out.printf("Proxy伺服器啟動...Port %d%n", localPort);
			ServerSocket proxyServerSkt = new ServerSocket(localPort);
			System.out.println("OK!");

			while (true) {
				System.out.println("傾聽客戶端.....");
				final Socket clientSkt = proxyServerSkt.accept();
				System.out.printf("%s 連線..%n", clientSkt.getInetAddress()
						.toString());

				// 客戶端的來往訊息
				final BufferedInputStream clientInputStream = new BufferedInputStream(
						clientSkt.getInputStream());
				final PrintStream clientPrintStream = new PrintStream(
						clientSkt.getOutputStream());

				// 伺服端的來往訊息
				final Socket serverSkt = new Socket(host, remotePort);
				final BufferedInputStream fromServerMsg = new BufferedInputStream(
						serverSkt.getInputStream());
				final PrintStream serverPrintStream = new PrintStream(
						serverSkt.getOutputStream());

				// 由客戶端至伺服器的訊息溝通執行緒
				Thread client = new Thread() {
					public void run() {
						int read;
						try {
							while ((read = clientInputStream.read()) != -1) {
								serverPrintStream.write(read);
								serverPrintStream.flush();
							}
						} catch (IOException e) {
						}

						// 中斷至伺服器的連線
						try {
							serverSkt.close();
							System.out.println("close\t"+serverSkt);
						} catch (IOException e) {
							System.err.println(e.toString());
						}
					}
				};

				client.start();

				
				Thread server = new Thread() {
					public void run() {
						// 主執行緒為由伺服器至客戶端的訊息
						int read;
						try {
							while ((read = fromServerMsg.read()) != -1) {
								clientPrintStream.write(read);
								clientPrintStream.flush();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
						;

						// 中斷與客戶端的連線
						try {
							clientSkt.close();
							System.out.println("close\t"+clientSkt);
						} catch (IOException e) {
							e.printStackTrace();
						}
						
					}
				};

				server.start();

				
				
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
