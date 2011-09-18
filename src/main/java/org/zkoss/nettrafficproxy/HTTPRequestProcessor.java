package org.zkoss.nettrafficproxy;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.zkoss.nettrafficproxy.api.MessageHandler;

public class HTTPRequestProcessor extends HTTPProcessor {
	
	public HTTPRequestProcessor(InputStream in, OutputStream out,
			int bufferSize, File storeFile, SocketColser closer, MessageHandler msgHdl) {
		super(in, out, bufferSize, storeFile, closer, msgHdl);
	}

	@Override
	protected boolean process(String content) {
		super.process(content);
		
		
		if (content.startsWith("GET"))
			infos.put("url", String.valueOf(content.split(" ")[1]));
		else if (content.startsWith("Accept-Language:"))
			infos.put("accept-Language", content.split(" ")[1].split(",")[0]);
		else if (content.startsWith("Accept-Charset"))
			infos.put("charset", content.split(" ")[1].split(",")[0]);
		
		
		return true;
	}

	@Override
	protected void doFinally() {
//		System.out.println("afterWrite: "+new Date());
//		System.out.println("-------------- Request ----------------------");
//		
//		System.out.println(sb.toString());
//		System.out.println(infos.get("url"));
//		System.out.println(infos.get("charset"));
//		System.out.println(infos.get("accept-Language"));
		
		
		
	}

	@Override
	protected void beforeWrite() {
//		System.out.println("beforeWrite: "+new Date());
//		FileUtils.writeByteArrayToFile(file, data)
		
		
	}

	

}
