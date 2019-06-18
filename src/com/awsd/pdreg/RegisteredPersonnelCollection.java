package com.awsd.pdreg;

import java.util.Vector;

import com.awsd.personnel.Personnel;

public class RegisteredPersonnelCollection extends Vector<Personnel> {

	private static final long serialVersionUID = -4255576118510652144L;

	private Event evt;

	public static final int SORT_BY_SCHOOL = 1;

	public RegisteredPersonnelCollection(Event evt, int sortcriteria) throws EventException {

		this.evt = evt;

		switch (sortcriteria) {
		case SORT_BY_SCHOOL:
			this.addAll(EventDB.getEventRegisteredPersonnelSortedBySchool(evt));
			break;
		default:
			throw new EventException("INVALID SORT CRITERIA.");
		}
	}

	public Event getEvent() {

		return evt;
	}
}