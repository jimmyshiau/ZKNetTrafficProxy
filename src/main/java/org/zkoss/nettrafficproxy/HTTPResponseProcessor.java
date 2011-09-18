package org.zkoss.nettrafficproxy;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.zkoss.nettrafficproxy.api.MessageHandler;

public class HTTPResponseProcessor extends HTTPProcessor {

	
	private String contentType = "";
	
	public HTTPResponseProcessor(InputStream in, OutputStream out,
			int bufferSize, File storeFile, SocketColser closer, MessageHandler msgHdl) {
		super(in, out, bufferSize, storeFile, closer, msgHdl);
	}

	@Override
	protected boolean process(String content) {
		super.process(content);
		
		if (content.startsWith("Content-Type:")) {
			String value = content.split(" ")[1];
			contentType = value.split(";")[0];
			infos.put("charset", value.split("=")[1]);
		} else if (end) {
			if (len > 0) {
				if (contentType.equals("text/plain") ||
						contentType.equals("application/x-www-form-urlencoded")) {
					
					infos.put("responseStr", content.substring(0, len));
//					return false;
				}
			}
		}
		
		
		return true;
	}

	@Override
	protected void doFinally() {
//		System.out.println("afterWrite: "+new Date());
//		System.out.println("-------------- Response ----------------------");
		
//		System.out.println(sb.toString());
//		System.out.println(contentType);
//		System.out.println(infos.get("charset"));
//		System.out.println(infos.get("responseStr"));
	}

	@Override
	protected void beforeWrite() {
//		System.out.println("beforeWrite: "+new Date());
		
	}

}
