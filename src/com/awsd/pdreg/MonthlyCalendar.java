package com.awsd.pdreg;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.nlesd.school.bean.SchoolZoneBean;

public class MonthlyCalendar extends HashMap<String, DailyCalendar> {

	private static final long serialVersionUID = -87190224682344818L;

	private String month;
	private SchoolZoneBean zone;

	public MonthlyCalendar(String month, SchoolZoneBean zone) throws EventException {

		this.month = month;
		this.zone = zone;

		Calendar original = Calendar.getInstance();
		try {
			original.setTime((new SimpleDateFormat("yyyyMM")).parse(month));
		}
		catch (ParseException e) {
			throw new EventException("Invalid Date format.\n" + e);
		}

		Calendar cal = (Calendar) original.clone();
		cal.set(Calendar.DATE, 1);

		if (zone != null) {
			this.putAll(EventDB.getMonthlyEvents(cal, zone));
		}
		else {
			this.putAll(EventDB.getMonthlyEvents(cal));
		}

	}

	public String getMonth() {

		return month;
	}

	public SchoolZoneBean getZone() {

		return zone;
	}

	public String toString() {

		String str = "";

		for (Map.Entry<String, DailyCalendar> entry : this.entrySet()) {
			str += ((String) entry.getKey()) + "\n";
		}

		return str;
	}
}