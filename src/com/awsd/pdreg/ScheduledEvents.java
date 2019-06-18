package com.awsd.pdreg;

import java.util.Vector;

import com.awsd.personnel.Personnel;

public class ScheduledEvents extends Vector<Event> {

	private static final long serialVersionUID = 6938233158575163893L;

	private Personnel p;
	private int category;

	public ScheduledEvents(int category) throws EventException {

		this.p = null;
		this.category = category;

		switch (category) {
		case -1:
			this.addAll(EventDB.getDistrictCalendarScheduledEvents());
			break;
		case -2:
			this.addAll(EventDB.getDistrictCalendarCloseoutScheduledEvents());
		}
	}

	public ScheduledEvents(Personnel p) throws EventException {

		this.p = p;
		this.category = 0;

		this.addAll(EventDB.getPersonnelScheduledEvents(p));
	}

	public Personnel getPersonnel() {

		return p;
	}

	public int getDistrictEventCategory() {

		return this.category;
	}
}