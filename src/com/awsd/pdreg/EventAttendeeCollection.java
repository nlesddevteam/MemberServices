package com.awsd.pdreg;

import java.util.Vector;

import com.awsd.school.SchoolException;

public class EventAttendeeCollection extends Vector<EventAttendee> {

	private static final long serialVersionUID = -4255576118510652144L;

	private Event evt;

	public static final int SORT_BY_SCHOOL = 1;

	public EventAttendeeCollection(Event evt, int sortcriteria) throws EventException, SchoolException {

		this.evt = evt;

		switch (sortcriteria) {
		case SORT_BY_SCHOOL:
			this.addAll(EventDB.getEventAttendeesSortedBySchool(evt));
			break;
		default:
			throw new EventException("INVALID SORT CRITERIA.");
		}

		if (this.evt.isSchoolPDEntry() && this.size() <= 0) {
			this.addAll(EventDB.getEventAttendeesSortedBySchool(evt, evt.getEventSchool()));
		}
	}

	public Event getEvent() {

		return evt;
	}
}