package org.zkoss.nettrafficproxy;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.StringUtils;

public class ProxySession extends Thread implements Observer {
	
	private String type;
	private Socket browser;
	private Socket webServer;
	private MessageHandler msgHdl;
	
	private InputStream browserIn, serverIn;
	private OutputStream browserOut, serverOut;
	
	private File dataFolder;
	
	
	public ProxySession(String type, Socket browser, Socket webServer,
			MessageHandler msgHdl, File parentFolder) throws Exception {
		super();
		this.type = type;
		this.browser = browser;
		this.webServer = webServer;
		this.msgHdl = msgHdl;
//		this.dataFolder = Utils.createFolder(parentFolder, 
//				new DecimalFormat("0000").format(id));
		
//		browserIn = new DataInputStream(browser.getInputStream());
//		browserOut = new DataOutputStream(browser.getOutputStream());
//		
//		serverIn = new DataInputStream(webServer.getInputStream());
//		serverOut = new DataOutputStream(webServer.getOutputStream());
		
		
		
		browserIn = browser.getInputStream();
		browserOut = browser.getOutputStream();
		
		serverIn = webServer.getInputStream();
		serverOut = webServer.getOutputStream();
		
		
	}


	public void run() {
		try {
//			while (true) {
				try {
					request();
					response();
				} catch (Exception e) {
					msgHdl.printStackTrace(e);
//					break;
				}
//			}
			if (!browser.isClosed())
				browser.close();
			if (!webServer.isClosed())
				webServer.close();
		} catch (Exception e) {
			msgHdl.printStackTrace(e);
		}
	}
	private void request() throws Exception {
		// read request form client
		
		
		byte[] byteData = copyByte(browserIn, serverOut, 1024).toByteArray();
		
		
		
		
		
		
		
		
		
		
		
		
//		String head = readHead(browserIn);
//		System.out.println("=========request:head============\r\n" + head);
//		byte[] content = readContent(head, browserIn);
//		
//		// send request to server
//		serverOut.write(head.getBytes("UTF-8"));
//		if (content != null) {
//			serverOut.write(content);
//		}
		
		
		
		
		
//		System.out.println(browserIn.read());
		
		
//		FileUtils.writeStringToFile(
//				new File(dataFolder, "request"), head, "UTF-8");
		
	}
	
	private void response() throws Exception {
		System.out.println("response");
		byte[] byteData = copyByte(serverIn, browserOut, 1024).toByteArray();
		
		
		
		// read response form server
//		String head = readHead(serverIn);
//		System.out.println("=========response:head===========\r\n" + head);
//		byte[] content = readContent(head, serverIn);
//		
//		// send response to client
//		browserOut.write(head.getBytes("UTF-8"));
//		
//		System.out.println("content: "+content);
//		if (content != null) {
//			browserOut.write(content);
//			
//			
//			
//			System.out.println(new String(content, "UTF-8"));
//		}
		
		
		
		
		
//		FileUtils.writeStringToFile(
//				new File(dataFolder, "response"), head, "UTF-8");
	}

	
	
	private ByteArrayOutputStream copyByte(InputStream in, OutputStream out, int len) {
		ByteArrayOutputStream bao = null;
		
		try {
			bao = new ByteArrayOutputStream();
			
//			byte[] buffer = new byte[len];
//			int numberRead;
//			while ((numberRead = in.read(buffer, 0, len)) > 0) {
//				System.out.println(numberRead);
//				out.write(buffer, 0, numberRead);
//				bao.write(buffer);
//			}
			System.out.println("write");
			out.write(in.read());
			System.out.println("done");
			
//			byte[] buffer = new byte[len];
//			int numberRead = in.read(buffer, 0, len);
//			while (numberRead > 0) {
//				System.out.println(numberRead);
//				out.write(buffer, 0, numberRead);
////				bao.write(buffer);
//				numberRead = in.read(buffer, 0, len);
//			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bao;
	}
	
	
	
	
	
	
	
	
	
//	private String readHead(InputStream in) {
//		StringBuilder sb = new StringBuilder();
//
//		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//		String line;
//		try {
//			while ((line = reader.readLine()) != null) {
//				System.out.println(line);
//				if (line.length() == 0)
//					break;
//				sb.append(line).append(System.getProperty("line.separator"));
//			}
//		} catch (IOException e) {
//			msgHdl.printStackTrace(e);
//		}
//		return sb.toString();
//	}
	
//	String readHead(InputStream in) {
//		StringBuilder sb = new StringBuilder();
//		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//		try {
//			// 讀取到第一個空白行為止，這是標頭訊息。
//			String line;
//			while ((line = reader.readLine()) != null) {
//				sb.append(line).append(System.getProperty("line.separator"));
//				if (line.length() == 0) {
//System.out.println("br");					
//					break;
//				}
//			}
//		} catch (Exception e) {
//			msgHdl.printStackTrace(e);
//		}
//		return sb.toString();
//	}
	
//	String readHead(DataInputStream in) {
//		StringBuilder sb = new StringBuilder();
//		try {
//			// 讀取到第一個空白行為止，這是標頭訊息。
//			String line;
//			while ((line = in.readLine()) != null) {
//				sb.append(line).append(System.getProperty("line.separator"));
//				if (line.length() == 0) {
//					break;
//				}
//			}
//		} catch (Exception e) {
//			msgHdl.printStackTrace(e);
//		}
//		return sb.toString();
//	}
	
//	String readHead(DataInputStream in) {
//		StringBuilder sb = new StringBuilder();
//		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//		
//		String head = "";
//		try {
//			// 讀取到第一個空白行為止，這是標頭訊息。
//			while (true) {
//				String line = reader.readLine();
//				if (line == null)
//					break;
//				head += line + System.getProperty("line.separator");
//				if (line.length() == 0)
//					break;
//			}
//		} catch (Exception e) {
//		}
//		return head;
//	}
	
	private byte[] readContent(String head, InputStream in) {
		int cntLen = getContentLength(head);
		System.out.println("len: "+cntLen);
		System.out.println("-------->"+head);
		if (cntLen > 0) {
			try {
				byte[] content = new byte[cntLen];
				System.out.println("r: "+content.length);
				in.read(content);
				System.out.println("r: "+content.length);
				return content;
			} catch (IOException e) {
				msgHdl.printStackTrace(e);
			}
		}
		
		return null;
	}
	
//	private byte[] readContent(String head, InputStream in) {
//		int cntLen = getContentLength(head);
//
//		
//		System.out.println("len: "+cntLen);
//		if (cntLen > 0) {
//			
//			
//			try {
//				ByteArrayOutputStream out = new ByteArrayOutputStream();
//				
//				int len = Math.min(4096, cntLen);
//				byte[] buffer = new byte[len];
//				
//				if (cntLen < 1024)
//					len = cntLen;
//				
//				int numberRead;
//				while ((numberRead = in.read(buffer, 0, len)) > 0) {
//					out.write(buffer, 0, numberRead);
//				}
//				System.out.println(out.toByteArray().length);
//				return out.toByteArray();
//			} catch (IOException e) {
//				msgHdl.printStackTrace(e);
//			}
//		}
//		
//		return null;
//	}
	
	private int getContentLength(String head) {

		String s = StringUtils.substringBetween(head, "Content-Length: ",
				System.getProperty("line.separator"));
//				"\n");
		
		if (!StringUtils.isEmpty(s))
			return Integer.parseInt(s);

		return 0;
	}
	
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
	
}
