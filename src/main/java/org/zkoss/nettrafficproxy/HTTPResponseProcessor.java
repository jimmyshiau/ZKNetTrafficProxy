package org.zkoss.nettrafficproxy;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class HTTPResponseProcessor extends HTTPProcessor {

	private String contentType = "";
	private String charset;
	private String cntEncoding;
	
	public HTTPResponseProcessor(InputStream in, OutputStream out,
			File parentFolder, SocketColser closer) throws IOException {
		super("Response", 4096, in, out, parentFolder, closer);
	}

	@Override
	protected void parseHeader(String line) {
		super.parseHeader(line);

		if (line.startsWith("Content-Type:")) {
			String value = line.split(" ")[1];
			contentType = value.split(";")[0];
			charset = value.split("=")[1];
		} else if (line.startsWith("Content-Encoding:")) {
			cntEncoding = line.split(" ")[1];
		}
	}

	
	@Override
	protected void writeContent(byte[] data) throws IOException {
		super.writeContent(data);
		
		
		System.out.println("cntEncoding:" +cntEncoding);
		if (charset == null)
			charset = "UTF-8";
		
		
		if ("gzip".equals(cntEncoding)) {
			GZIPInputStream fin = new GZIPInputStream(new ByteArrayInputStream(data));
			
			FileUtils.copyInputStreamToFile(fin, new File(dataFolder, type+"Cnt"));
		} else
			FileUtils.writeByteArrayToFile(new File(dataFolder, type+"Cnt"), data);
		
	}


	protected boolean process(String content) {
		super.process(content);

		 if (end) {
			if (len > 0) {
				if (contentType.equals("text/plain")
						|| contentType
								.equals("application/x-www-form-urlencoded")) {

					infos.put("responseStr", content.substring(0, len));
					// return false;
				}
			}
		}

		return true;
	}

}
