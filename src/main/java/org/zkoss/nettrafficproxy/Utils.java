package org.zkoss.nettrafficproxy;

import java.io.File;
import java.io.IOException;
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
	
}
