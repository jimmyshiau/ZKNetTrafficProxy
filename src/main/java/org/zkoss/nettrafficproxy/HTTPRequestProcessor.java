package org.zkoss.nettrafficproxy;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HTTPRequestProcessor extends HTTPProcessor {

	public HTTPRequestProcessor(InputStream in, OutputStream out,
			File parentFolder, SocketColser closer) throws IOException {
		super("Request", in, out, parentFolder, closer);
	}

	

	
	
	
	
	
	
	
	
	
	
	
	
	
	// public HTTPRequestProcessor(int id, InputStream in, OutputStream out,
	// File parentFolder, SocketColser closer, MessageHandler msgHdl)
	// throws IOException {
	// super(id, in, out, parentFolder, closer, msgHdl);
	// // TODO Auto-generated constructor stub
	// }

	// @Override
	// protected boolean process(String content) {
	// super.process(content);
	//
	//
	// if (content.startsWith("GET"))
	// infos.put("url", String.valueOf(content.split(" ")[1]));
	// else if (content.startsWith("Accept-Language:"))
	// infos.put("accept-Language", content.split(" ")[1].split(",")[0]);
	// else if (content.startsWith("Accept-Charset"))
	// infos.put("charset", content.split(" ")[1].split(",")[0]);
	//
	//
	// return true;
	// }

}
