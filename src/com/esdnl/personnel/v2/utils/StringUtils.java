package com.esdnl.personnel.v2.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StringUtils {

	public static String getSchoolYear(Date d, String y1Format, String y2Format, String separator) {

		String school_year;

		SimpleDateFormat y1 = new SimpleDateFormat(y1Format);
		SimpleDateFormat y2 = new SimpleDateFormat(y2Format);
		Calendar cur = Calendar.getInstance();
		cur.setTime(d);

		if (cur.get(Calendar.MONTH) > Calendar.JUNE) {
			//beginning of school year
			cur.add(Calendar.YEAR, 1);
			school_year = y1.format(d) + (org.apache.commons.lang.StringUtils.isNotEmpty(separator) ? separator : "")
					+ y2.format(cur.getTime());
		}
		else if (cur.get(Calendar.MONTH) <= Calendar.JUNE) {
			//end of school year
			cur.add(Calendar.YEAR, -1);
			school_year = y1.format(cur.getTime())
					+ (org.apache.commons.lang.StringUtils.isNotEmpty(separator) ? separator : "") + y2.format(d);
		}
		else {
			school_year = "UNKNOWN";
		}

		return school_year;
	}

	public static String getSchoolYear(Date d) {

		return getSchoolYear(d, "yyyy", "yy", "-");
	}

	public static String getPreviousSchoolYear(Date d) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.YEAR, -1);

		return getSchoolYear(cal.getTime(), "yyyy", "yy", "-");
	}

	public static String getCurrentSchoolYear() {

		return getCurrentSchoolYear("yy", "yy", "");
	}

	public static String getCurrentSchoolYear(String y1Format, String y2Format, String separator) {

		return getSchoolYear(new Date(), y1Format, y2Format, separator);
	}
}