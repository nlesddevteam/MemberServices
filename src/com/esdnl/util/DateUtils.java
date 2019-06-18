package com.esdnl.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static boolean dateInRange(Date start, Date end, Date d) {

		boolean inRange = false;

		if ((start.compareTo(d) == 0) || (end.compareTo(d) == 0))
			inRange = true;
		else if (start.before(d) && d.before(end))
			inRange = true;

		return inRange;
	}

	public static int getDaysInMonth(Date d) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(d);

		return cal.getMaximum(Calendar.DATE);
	}

	public static int getDaysInRange(Date sd, Date ed) {

		int days = 0;

		Calendar sCal = Calendar.getInstance();
		sCal.clear();
		sCal.setTime(sd);

		Calendar eCal = Calendar.getInstance();
		eCal.clear();
		eCal.setTime(ed);

		while (sCal.compareTo(eCal) <= 0) {
			days++;
			sCal.add(Calendar.DATE, 1);
		}

		return days;
	}

	public static String getMonthString(int month) {

		String month_str;

		switch (month) {
		case Calendar.JANUARY:
			month_str = "January";
			break;
		case Calendar.FEBRUARY:
			month_str = "February";
			break;
		case Calendar.MARCH:
			month_str = "March";
			break;
		case Calendar.APRIL:
			month_str = "April";
			break;
		case Calendar.MAY:
			month_str = "May";
			break;
		case Calendar.JUNE:
			month_str = "June";
			break;
		case Calendar.JULY:
			month_str = "July";
			break;
		case Calendar.AUGUST:
			month_str = "August";
			break;
		case Calendar.SEPTEMBER:
			month_str = "September";
			break;
		case Calendar.OCTOBER:
			month_str = "October";
			break;
		case Calendar.NOVEMBER:
			month_str = "November";
			break;
		case Calendar.DECEMBER:
			month_str = "December";
			break;
		default:
			month_str = "UNKNOWN";
			break;
		}

		return month_str;
	}

	public static int getMonthNumber(String strMonth) {

		int month = 0;

		if (strMonth.equalsIgnoreCase("Janurary"))
			month = Calendar.JANUARY;
		else if (strMonth.equalsIgnoreCase("February"))
			month = Calendar.FEBRUARY;
		else if (strMonth.equalsIgnoreCase("March"))
			month = Calendar.MARCH;
		else if (strMonth.equalsIgnoreCase("April"))
			month = Calendar.APRIL;
		else if (strMonth.equalsIgnoreCase("May"))
			month = Calendar.MAY;
		else if (strMonth.equalsIgnoreCase("June"))
			month = Calendar.JUNE;
		else if (strMonth.equalsIgnoreCase("July"))
			month = Calendar.JULY;
		else if (strMonth.equalsIgnoreCase("August"))
			month = Calendar.AUGUST;
		else if (strMonth.equalsIgnoreCase("September"))
			month = Calendar.SEPTEMBER;
		else if (strMonth.equalsIgnoreCase("October"))
			month = Calendar.OCTOBER;
		else if (strMonth.equalsIgnoreCase("November"))
			month = Calendar.NOVEMBER;
		else if (strMonth.equalsIgnoreCase("December"))
			month = Calendar.DECEMBER;

		return month;
	}
}
