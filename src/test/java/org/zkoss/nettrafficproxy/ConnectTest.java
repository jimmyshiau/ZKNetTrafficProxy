package org.zkoss.nettrafficproxy;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class ConnectTest {
	public static void main(String[] args) {

		new ProxyServer(8001, "localhost", 8080).start();

//		File f = new File("httpData","Response0");
//		
//		try {
//			String s = IOUtils.toString(FileUtils.openInputStream(f));
//			
//			
//			System.out.println(StringUtils.substringBetween(s, "Content-Length: ",
//					System.getProperty("line.separator")));
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		NumberFormat formatter = new DecimalFormat("0000");
//		
//		for (int i = 0; i < 100; i++) {
//			System.out.println(formatter.format(i));
//		}
		
		
	}
}
