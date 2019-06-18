package com.awsd.pdreg;

public class EventRequirement {

	private int eventId;
	private String requirement;
	private String extrainfo;

	public EventRequirement() {

	}

	public EventRequirement(String requirement) {

		this.requirement = requirement;
	}

	public int getEventId() {

		return eventId;
	}

	public void setEventId(int eventId) {

		this.eventId = eventId;
	}

	public String getRequirement() {

		return requirement;
	}

	public void setRequirement(String requirement) {

		this.requirement = requirement;
	}

	public String getExtrainfo() {

		return extrainfo;
	}

	public void setExtrainfo(String extrainfo) {

		this.extrainfo = extrainfo;
	}

}
