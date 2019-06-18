package com.awsd.pdreg;

import java.util.HashMap;
import java.util.Map;

public class CalendarLegend extends HashMap<Integer, String> {

	private static final long serialVersionUID = 5575917019079713975L;

	public CalendarLegend() throws EventException {

		this.putAll(EventDB.getCalendarLegend());
	}

	public String toString() {

		String tmp = "";

		for (Map.Entry<Integer, String> entry : this.entrySet()) {
			tmp += (entry.getKey()).intValue() + ":" + (entry.getValue()) + "\n";
		}

		return tmp;
	}
}