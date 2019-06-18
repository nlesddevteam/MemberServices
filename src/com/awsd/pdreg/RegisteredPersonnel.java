package com.awsd.pdreg;

import java.util.HashMap;

import com.awsd.personnel.Personnel;

public class RegisteredPersonnel extends HashMap<Integer, Personnel> {

	private static final long serialVersionUID = 4727960332464870149L;

	private Event evt;

	public RegisteredPersonnel(Event evt) throws EventException {

		this.evt = evt;
		this.putAll(EventDB.getEventRegisteredPersonnel(evt));
	}

	public Event getEvent() {

		return evt;
	}
}