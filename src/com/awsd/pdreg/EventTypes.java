package com.awsd.pdreg;

import java.util.Vector;

public class EventTypes extends Vector<EventType> {

	private static final long serialVersionUID = 6193294326612761197L;

	public EventTypes() throws EventTypeException {

		this.addAll(EventTypeDB.getEventTypes());
	}
}