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
	private Object contentType;

	public HTTPRequestProcessor(InputStream in, OutputStream out,
			File parentFolder, SocketHandler closer) throws IOException {
		super("Request", 1024, in, out, parentFolder, closer);
	}
	
	@Override
	protected void parseHeader(String line) {
		super.parseHeader(line);
		
		if (line.startsWith("GET")) {
			resourceUrl = String.valueOf(line.split(" ")[1]);
			String fileName = resourceUrl.substring(resourceUrl.lastIndexOf("/")+1);
			if (fileName != null)
				sckHdler.setResourceName(fileName);
		} else if (line.startsWith("Accept-Language:"))
			browserLanguage = line.split(" ")[1].split(",")[0];
		else if (line.startsWith("Accept-Charset"))
			charset = line.split(" ")[1].split(",")[0];
		else if (line.startsWith("Content-Type:")) {
			String value = line.split(" ")[1];
			contentType = value.split(";")[0];
			String[] v = value.split("=");
			if (v.length > 2)
				charset = value.split("=")[1];
		} 
	}
	
	@Override
	protected void writeContent(int index, byte[] data) throws IOException {
		super.writeContent(index, data);
		if (charset == null)
			charset = "UTF-8";
		
		String fileName = type+index+"Cnt";
		if ("application/x-www-form-urlencoded".equals(contentType))
			fileName = type+index+"AuCmd";
		FileUtils.writeByteArrayToFile(new File(dataFolder, fileName), data);
		
	}
	
}
