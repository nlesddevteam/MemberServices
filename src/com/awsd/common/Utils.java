package com.awsd.common;

import java.util.Calendar;
import java.util.Date;

public class Utils {

	public static int compareCurrentDate(Date d) {

		int check = 0;
		Calendar cur = Calendar.getInstance();
		Calendar cald = Calendar.getInstance();
		cald.setTime(d);

		int curd, curm, cury, ed, em, ey;

		curd = cur.get(Calendar.DATE);
		curm = cur.get(Calendar.MONTH);
		cury = cur.get(Calendar.YEAR);

		ed = cald.get(Calendar.DATE);
		em = cald.get(Calendar.MONTH);
		ey = cald.get(Calendar.YEAR);

		if (ey < cury)
			check = -1;
		else if (ey > cury)
			check = 1;
		else if ((ey == cury) && (em < curm))
			check = -1;
		else if ((ey == cury) && (em > curm))
			check = 1;
		else if ((ey == cury) && (em == curm) && (ed < curd))
			check = -1;
		else if ((ey == cury) && (em == curm) && (ed > curd))
			check = 1;
		else if ((ey == cury) && (em == curm) && (ed == curd))
			check = 0;

		return check;
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

	public static String getCurrentSchoolYear() {

		String school_year;

		Calendar cur = Calendar.getInstance();

		if (cur.get(Calendar.MONTH) > Calendar.JUNE) {
			// beginning of school year
			school_year = cur.get(Calendar.YEAR) + "-" + (cur.get(Calendar.YEAR) + 1);
		}
		else if (cur.get(Calendar.MONTH) <= Calendar.JUNE) {
			// end of school year
			school_year = (cur.get(Calendar.YEAR) - 1) + "-" + (cur.get(Calendar.YEAR));
		}
		else {
			school_year = "UNKNOWN";
		}

		return school_year;
	}

	public static String getCurrentGrowthPlanYear() {

		String school_year;

		Calendar cur = Calendar.getInstance();

		if (((cur.get(Calendar.MONTH) == Calendar.MAY) && (cur.get(Calendar.DATE) >= 15))
				|| (cur.get(Calendar.MONTH) > Calendar.MAY)) {
			// beginning of school year
			school_year = cur.get(Calendar.YEAR) + "-" + (cur.get(Calendar.YEAR) + 1);
		}
		else if (cur.get(Calendar.MONTH) <= Calendar.MAY) {
			// end of school year
			school_year = (cur.get(Calendar.YEAR) - 1) + "-" + (cur.get(Calendar.YEAR));
		}
		else {
			school_year = "UNKNOWN";
		}

		return school_year;
	}

	public static String getSchoolYear(Calendar cur) {

		String school_year;

		if (cur.get(Calendar.MONTH) > Calendar.JUNE) {
			// beginning of school year
			school_year = cur.get(Calendar.YEAR) + "-" + (cur.get(Calendar.YEAR) + 1);
		}
		else if (cur.get(Calendar.MONTH) <= Calendar.JUNE) {
			// end of school year
			school_year = (cur.get(Calendar.YEAR) - 1) + "-" + (cur.get(Calendar.YEAR));
		}
		else {
			school_year = "UNKNOWN";
		}

		return school_year;
	}

	public static int getYear(int my, String fy) {

		String yr;

		if (my > Calendar.JUNE)
			yr = fy.substring(0, fy.indexOf("-"));

		else
			yr = fy.substring(fy.indexOf("-") + 1);

		return Integer.parseInt(yr);
	}

	public static Date getDateFromFiscalYearMonth(int my, String fy) {

		Calendar cal = Calendar.getInstance();

		cal.clear();

		cal.set(Utils.getYear(my, fy), my, 1);

		return cal.getTime();
	}
}