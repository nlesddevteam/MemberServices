package com.awsd.pdreg;

import java.util.HashMap;

import com.awsd.personnel.Personnel;

public class RegisteredEvents extends HashMap<Integer, Event> {

	private static final long serialVersionUID = 7006894114271086552L;
	private Personnel p;

	public RegisteredEvents(Personnel p) throws EventException {

		this.p = p;
		this.putAll(EventDB.getUserRegisteredEvents(p));
	}

	public Personnel getPersonnel() {

		return p;
	}
}