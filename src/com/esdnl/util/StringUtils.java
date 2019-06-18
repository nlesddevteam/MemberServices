package com.esdnl.util;

public class StringUtils {

	public static boolean isEmpty(String s) {

		return ((s == null) || s.trim().equals(""));
	}

	public static boolean isEqual(String s1, String s2) {

		return ((s1 != null) && (s1.equals(s2)));
	}

	public static boolean contains(String s1, String s2) {

		return ((s1 != null) && (s2 != null) && (s1.toUpperCase().indexOf(s2.toUpperCase()) >= 0));
	}

	public static String encodeHTML(String s) {

		return s.replaceAll("\n", "<BR>").replaceAll("&", "&amp;").replaceAll("'", "&#39;").replaceAll("\"", "&quot;");
	}

	public static String encodeXML(String s) {

		return s.replaceAll("&", "&amp;");
	}

	public static String encodeHTML2(String string) {

		if (string == null)
			return "";

		StringBuffer sb = new StringBuffer(string.length());
		// true if last char was blank
		boolean lastWasBlankChar = false;
		int len = string.length();
		char c;

		for (int i = 0; i < len; i++) {
			c = string.charAt(i);
			if (c == ' ') {
				// blank gets extra work,
				// this solves the problem you get if you replace all
				// blanks with &nbsp;, if you do that you loss
				// word breaking
				if (lastWasBlankChar) {
					lastWasBlankChar = false;
					sb.append("&nbsp;");
				}
				else {
					lastWasBlankChar = true;
					sb.append(' ');
				}
			}
			else {
				lastWasBlankChar = false;
				//
				// HTML Special Chars
				if (c == '"')
					sb.append("&quot;");
				else if (c == '\'')
					sb.append("&#39;");
				else if (c == '&')
					sb.append("&amp;");
				else if (c == '<')
					sb.append("&lt;");
				else if (c == '>')
					sb.append("&gt;");
				else if (c == '\n')
					// Handle Newline
					sb.append("<br />");
				else {
					int ci = 0xffff & c;
					if (ci < 160)
						// nothing special only 7 Bit
						sb.append(c);
					else {
						// Not 7 Bit use the unicode system
						sb.append("&#");
						sb.append(new Integer(ci).toString());
						sb.append(';');
					}
				}
			}
		}
		return sb.toString();
	}
}