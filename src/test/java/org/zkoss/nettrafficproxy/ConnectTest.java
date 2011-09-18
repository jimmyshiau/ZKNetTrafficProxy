package org.zkoss.nettrafficproxy;


public class ConnectTest {
	public static void main(String[] args) {

		new ProxyServer(8001, "localhost", 8080).start();

	}
}
