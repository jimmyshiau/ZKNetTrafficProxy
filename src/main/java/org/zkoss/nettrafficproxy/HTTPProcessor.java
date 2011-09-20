package org.zkoss.nettrafficproxy;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

public class HTTPProcessor extends Thread implements Observer {

	private int index = 0;
	
	protected File dataFolder;
	protected String type;
	private int bufferSize;
	
	
	private InputStream in;
	private OutputStream out;
	protected SocketHandler sckHdler;
	private MessageHandler msgHdl;
	
	protected Map<String, String> infos = new HashMap<String, String>();
	protected StringBuffer headSb = new StringBuffer();
	protected StringBuffer bodySb = new StringBuffer();
	protected int len = 0;
	protected boolean end = false;
	
	private boolean stop = false;

	public HTTPProcessor(String type, int bufferSize, InputStream in, OutputStream out,
			File dataFolder, SocketHandler sckHdler)
			throws IOException {
		super();
		this.type = type;
		this.bufferSize = bufferSize;
		this.in = in;
		this.out = out;
		this.sckHdler = sckHdler;
		this.dataFolder = dataFolder;
		this.msgHdl = MessageHandler.getInstance();
//		buffer = new byte[4096];
	}

	public void run() {
		try {
			byte[] data = writeByte();
			storeByte(data);
			
		} catch (SocketException e) {
			// ignore socket closed
		} catch (IOException e) {
			msgHdl.printStackTrace(e);
		} finally {
			doFinally();
		}
	}

	protected void writeContent(int index, byte[] data) throws IOException {
	}
	
	private void storeByte(byte[] data) throws NumberFormatException, IOException {
		len = 0;
		String header = getHeader(data);
		writeHeader(index, header);
		if (len > 0) {
			System.out.println(len);
			int totalLen = data.length;
			int headerLen = header.getBytes().length;
			writeContent(index, Utils.getSubBytes(data, headerLen, len));
			
			int remain = totalLen - headerLen - len;
			
			if (remain > 0) {
				index++;
				storeByte(Utils.getSubBytes(data, headerLen+len, remain));
			}
		}
	}

	private void writeHeader(int index, String header) throws IOException {
		FileUtils.writeStringToFile(
				new File(dataFolder, type + index), header, "UTF-8");

	}

	private byte[] writeByte() throws IOException {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		int bytesRead;
		byte[] buffer = new byte[bufferSize];
		while ((bytesRead = in.read(buffer)) > 0) {
			out.write(buffer, 0, bytesRead);
			byteOut.write(buffer, 0, bytesRead);
		}
		return byteOut.toByteArray();
	}
	
	private String getHeader(byte[] data) throws NumberFormatException, IOException {
		len = 0;
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = Utils.getReader(data);
		
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line).append(System.getProperty("line.separator"));
			
			if (line.length() == 0) {
				break;
			} else parseHeader(line);
		}
		
		return sb.toString();
	}

	
	protected void parseHeader(String line) {
		if (line.startsWith("Content-Length:"))
			len = Integer.parseInt(line.split(" ")[1]);
		
	}

	private FileOutputStream parseHeader(byte[] buffer, int index) throws NumberFormatException, IOException {
		len = 0;
		StringBuilder sb = new StringBuilder();
		
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(new ByteArrayInputStream(buffer)));
		
		String line;
		while ((line = reader.readLine()) != null) {
			if (line.length() == 0) {
				return null;
			} else if (line.startsWith("Content-Length:"))
				len = Integer.parseInt(line.split(" ")[1]);
			
			System.out.println(line);
			
			sb.append(line).append(System.getProperty("line.separator"));
		}
		
		
		
		FileOutputStream outFile = FileUtils.openOutputStream(new File(dataFolder, type + index));
		
		
//		outFile.write(reader.read());
		
		return outFile;
		
//		List<String> lines = IOUtils.readLines(new BufferedReader(new InputStreamReader(
//				new ByteArrayInputStream(buffer))));
//		System.out.println("------------->"+header+"<-----------");
		
//		System.out.println("len: "+getContentLength(buffer));
		
		// TODO Auto-generated method stub
		
	}
	
	private int getContentLength(String head) {

		String s = StringUtils.substringBetween(head, "Content-Length: ",
				System.getProperty("line.separator"));
		
		if (!StringUtils.isEmpty(s))
			return Integer.parseInt(s);

		return 0;
	}

	protected void doFinally() {
		try {
			sckHdler.doClose(in, out, msgHdl);
		} catch (IOException e) {
			msgHdl.printStackTrace(e);
		}
	}

	private void process(byte[] b, int off, int len) throws IOException {
		
		
		
//		new File(dataFolder, "request");
		
		System.out.println("-------->"+new String(b));
		
	}
	
	
	protected boolean process(String content) {
//		System.out.println(content);
		
		if (content.startsWith("Content-Length:"))
			len = Integer.parseInt(content.split(" ")[1]);
		else if (content.equals("")) {
			end = true;
			return true;
		}
		
		if (end)
			bodySb.append(content);
		else 
			headSb.append(content);
		return true;
	}

	public void update(Observable o, Object arg) {
		stop = true;
		doFinally();
		o.deleteObserver(this);
	}

}
