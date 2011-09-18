package org.zkoss.nettrafficproxy;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HTTPResponseProcessor extends HTTPProcessor {

	public HTTPResponseProcessor(InputStream in, OutputStream out,
			File parentFolder, SocketColser closer) throws IOException {
		super("Response", in, out, parentFolder, closer);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// public HTTPResponseProcessor(int id, InputStream in, OutputStream out,
	// File parentFolder, SocketColser closer, MessageHandler msgHdl)
	// throws IOException {
	// super(id, in, out, parentFolder, closer, msgHdl);
	// // TODO Auto-generated constructor stub
	// }

	// private String contentType = "";

	// protected boolean process(String content) {
	// super.process(content);
	//
	// if (content.startsWith("Content-Type:")) {
	// String value = content.split(" ")[1];
	// contentType = value.split(";")[0];
	// infos.put("charset", value.split("=")[1]);
	// } else if (end) {
	// if (len > 0) {
	// if (contentType.equals("text/plain") ||
	// contentType.equals("application/x-www-form-urlencoded")) {
	//
	// infos.put("responseStr", content.substring(0, len));
	// // return false;
	// }
	// }
	// }

	// return true;
	// }

}
