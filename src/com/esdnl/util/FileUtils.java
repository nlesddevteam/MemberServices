package com.esdnl.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class FileUtils {

	public static String generateRandomFilename(String dir, String prefix, String extension) {

		return generateRandomFilename(new File(!dir.endsWith("/") ? dir + "/" : dir), prefix, extension);
	}

	public static String generateRandomFilename(File dir, String prefix, String extension) {

		String filename = null;
		File f = null;

		do {
			filename = (!StringUtils.isEmpty(prefix) ? prefix : "") + Long.toString(Calendar.getInstance().getTimeInMillis())
					+ (!StringUtils.isEmpty(extension) ? (!extension.startsWith(".") ? "." + extension : extension) : "");

			f = new File(dir, filename);
		} while (f.exists());

		return filename;
	}

	public static String extractExtension(String filename) {

		String exd = null;

		try {
			exd = filename.substring(filename.lastIndexOf("."));
		}
		catch (Exception e) {
			exd = null;
		}

		return exd;
	}

	public static String getFileContents(File f) throws IOException {

		FileInputStream fis = null;
		byte[] buf = null;
		StringBuffer contents = new StringBuffer();
		;
		int read = 0;

		try {
			if (f.exists()) {
				fis = new FileInputStream(f);

				if (f.length() > 0) {
					buf = new byte[1024];

					read = fis.read(buf);
					while (read != -1) {
						contents.append(new String(buf, 0, read));
						read = fis.read(buf);
					}
					fis.close();
				}
			}
		}
		catch (IOException e) {
			throw e;
		}

		return contents.toString();
	}

	public static String getFileContentsHTML(File f) throws IOException {

		String contents = getFileContents(f);

		if (!StringUtils.isEmpty(contents)) {
			// replace linefeeds with <BR>
			contents = contents.replaceAll("\n", "<BR>");
		}

		return contents;
	}

	public static void writeFileContents(File f, String contents) throws IOException {

		FileOutputStream fos = null;

		try {
			if (f.exists() && !StringUtils.isEmpty(contents)) {
				fos = new FileOutputStream(f);
				fos.write(contents.getBytes());
				fos.close();
			}
		}
		catch (IOException e) {
			throw e;
		}
	}
}