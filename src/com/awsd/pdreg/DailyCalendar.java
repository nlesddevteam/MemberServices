package com.awsd.pdreg;

import java.util.Vector;

import com.nlesd.school.bean.SchoolZoneBean;

public class DailyCalendar extends Vector<Event> {

	private static final long serialVersionUID = 9144508139147894112L;

	private String day;
	private SchoolZoneBean zone;

	public DailyCalendar(String day, SchoolZoneBean zone, boolean filled) throws EventException {

		this.day = day;
		this.zone = zone;

		if (filled) {
			if (this.zone != null) {
				this.addAll(EventDB.getDailyEvents(this.day, this.zone));
			}
			else {
				this.addAll(EventDB.getDailyEvents(this.day));
			}
		}
	}

	public String getDate() {

		return this.day;
	}

	public SchoolZoneBean getZone() {

		return zone;
	}

}