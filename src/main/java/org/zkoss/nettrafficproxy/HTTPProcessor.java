package org.zkoss.nettrafficproxy;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.zkoss.nettrafficproxy.api.MessageHandler;

public abstract class HTTPProcessor extends Thread {

	private InputStream in;
	private OutputStream out;
	private byte[] buffer;
	private SocketColser closer;
	private MessageHandler msgHdl;
	
	protected Map<String, String> infos = new HashMap<String, String>();
	protected StringBuffer headSb = new StringBuffer();
	protected StringBuffer bodySb = new StringBuffer();
	protected int len = 0;
	protected boolean end = false;
	private OutputStream fOut;

	public HTTPProcessor(InputStream in, OutputStream out, int bufferSize,
			File storeFile, SocketColser closer, MessageHandler msgHdl) {
		super();
		this.in = in;
		this.out = out;
		buffer = new byte[bufferSize];
		this.closer = closer;
		this.msgHdl = msgHdl;

		try {
			fOut = FileUtils.openOutputStream(storeFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		int bytesRead;
		try {
			beforeWrite();
			while ((bytesRead = in.read(buffer)) != -1) {
//				if (!process(new String(buffer)))
//					break;
				
				process(new String(buffer));
				process(buffer, 0, bytesRead);
				
				out.write(buffer, 0, bytesRead);
				out.flush();
			}
			
		}catch (SocketException e) {
			// ignore socket closed
		} catch (IOException e) {
			msgHdl.printStackTrace(e);
		} finally {
			doFinally();
		}
	}

	

	protected abstract void beforeWrite();
	protected void doFinally() {
		try {
			out.close();
			in.close();
			fOut.close();
			closer.doClose();
		} catch (IOException e) {
			msgHdl.printStackTrace(e);
		}
	}

	private void process(byte[] b, int off, int len) throws IOException {
		fOut.write(b, off, len);
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

	

}
