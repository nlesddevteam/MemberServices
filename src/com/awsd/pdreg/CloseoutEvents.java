package com.awsd.pdreg;

import java.util.HashMap;
import java.util.Vector;

public class CloseoutEvents {

	private HashMap<String, Vector<Event>> events = null;
	private Event closeout = null;
	private String options[] = null;

	public CloseoutEvents(Event closeout, String options[]) throws EventException {

		events = new HashMap<String, Vector<Event>>(options.length);
		this.closeout = closeout;
		this.options = options;

		for (int i = 0; i < options.length; i++) {
			events.put(options[i], EventDB.getCloseOutEvents(closeout, options[i]));
		}
	}

	public Vector<Event> getEvents(String option) {

		return ((Vector<Event>) events.get(option));
	}

	public Event getCloseOutEvent() {

		return closeout;
	}

	public String[] getOptions() {

		return options;
	}
}