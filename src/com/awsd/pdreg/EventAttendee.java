package com.awsd.pdreg;

import com.awsd.personnel.Personnel;

public class EventAttendee {

	private Event event;
	private Personnel personnel;
	private boolean attended;

	public EventAttendee(Event event, Personnel personnel, boolean attended) {

		this.event = event;
		this.personnel = personnel;
		this.attended = attended;
	}

	public Event getEvent() {

		return event;
	}

	public void setEvent(Event event) {

		this.event = event;
	}

	public Personnel getPersonnel() {

		return personnel;
	}

	public void setPersonnel(Personnel personnel) {

		this.personnel = personnel;
	}

	public boolean isAttended() {

		return attended;
	}

	public void setAttended(boolean attended) {

		this.attended = attended;
	}

}
