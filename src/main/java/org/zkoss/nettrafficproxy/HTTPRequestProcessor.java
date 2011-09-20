package org.zkoss.nettrafficproxy;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class HTTPRequestProcessor extends HTTPProcessor {

	private String resourceUrl;
	private String browserLanguage;
	private String charset;

	public HTTPRequestProcessor(InputStream in, OutputStream out,
			File parentFolder, SocketColser closer) throws IOException {
		super("Request", 1024, in, out, parentFolder, closer);
	}
	
	@Override
	protected void parseHeader(String line) {
		super.parseHeader(line);
		
		if (line.startsWith("GET"))
			resourceUrl = String.valueOf(line.split(" ")[1]);
		else if (line.startsWith("Accept-Language:"))
			browserLanguage = line.split(" ")[1].split(",")[0];
		else if (line.startsWith("Accept-Charset"))
			charset = line.split(" ")[1].split(",")[0];
		
	}
	
	@Override
	protected void writeContent(byte[] data) throws IOException {
		super.writeContent(data);
		System.out.println("charset:" +charset);
		if (charset == null)
			charset = "UTF-8";
		FileUtils.writeByteArrayToFile(new File(dataFolder, type+"Cnt"), data);
		
	}
	
}
