package org.zkoss.nettrafficproxy;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.io.FileUtils;

public class Utils {

	public static File createFolder(String dirName) throws IOException {
		File file = new File(dirName);
		if (file.exists())
			FileUtils.cleanDirectory(file);
		else
			file.mkdir();
		return file;
	}

	public static File createFolder(File parent, String dirName)
			throws IOException {
		if (!parent.exists())
			parent.mkdirs();

		File file = new File(parent, dirName);
		if (file.exists())
			FileUtils.cleanDirectory(file);
		else
			file.mkdir();
		return file;
	}

	public static BufferedReader getReader(byte[] data) {
		return new BufferedReader(new InputStreamReader(
				new ByteArrayInputStream(data)));
	}

	public static byte[] getSubBytes(byte[] source, int srcBegin) {
		int srcEnd = source.length;
		int newLen = srcEnd - srcBegin;
		byte destination[] = new byte[newLen];
		System.arraycopy(source, srcBegin, destination, 0, newLen);

		return destination;
	}

}
