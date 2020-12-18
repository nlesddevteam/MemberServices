package com.awsd.travel.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PDTravelClaimEventBean implements Serializable {
	private static final long serialVersionUID = -8771605504141293333L;
	private long eventId;
	private String eventName;
	private int attendedEvent;
	private long attendeeId;
	private long schedulerId;
	private Date eventStartDate;
	private Date eventEndDate;
	private String eventDescription;
	public long getEventId() {
		return eventId;
	}
	public void setEventId(long eventId) {
		this.eventId = eventId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public int getAttendedEvent() {
		return attendedEvent;
	}
	public void setAttendedEvent(int attendedEvent) {
		this.attendedEvent = attendedEvent;
	}
	public long getAttendeeId() {
		return attendeeId;
	}
	public void setAttendeeId(long attendeeId) {
		this.attendeeId = attendeeId;
	}
	public long getSchedulerId() {
		return schedulerId;
	}
	public void setSchedulerId(long schedulerId) {
		this.schedulerId = schedulerId;
	}
	public Date getEventStartDate() {
		return eventStartDate;
	}
	public void setEventStartDate(Date eventStartDate) {
		this.eventStartDate = eventStartDate;
	}
	public Date getEventEndDate() {
		return eventEndDate;
	}
	public void setEventEndDate(Date eventEndDate) {
		this.eventEndDate = eventEndDate;
	}
	public String toXml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<PDEVENT>");
		sb.append("<EVENTID>" + this.eventId + "</EVENTID>");
		sb.append("<EVENTNAME>" + this.eventName + "</EVENTNAME>");
		sb.append("<ATTENDEDEVENT>" + this.attendedEvent + "</ATTENDEDEVENT>");
		sb.append("<ATTENDEEID>" + this.attendeeId + "</ATTENDEEID>");
		sb.append("<SCHEDULERID>" + this.schedulerId + "</SCHEDULERID>");
		sb.append("<EVENTSTARTDATE>" + this.getEventStartDateFormatted() + "</EVENTSTARTDATE>");
		sb.append("<EVENTENDDATE>" + this.getEventEndDateFormatted() + "</EVENTENDDATE>");
		sb.append("<EVENTDESCRIPTION>" + this.eventDescription+ "</EVENTDESCRIPTION>");
		sb.append("<MESSAGE>SUCCESS</MESSAGE>");
		sb.append("</PDEVENT>");
		
		return sb.toString();
	}
	public String getEventDescription() {
		return eventDescription;
	}
	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}
	public String getEventStartDateFormatted()
	{
		if(this.eventStartDate == null) {
			return "";
		}else {
			return new SimpleDateFormat("dd/MM/yyyy").format(this.eventStartDate);
		}
		
	}
	public String getEventEndDateFormatted()
	{
		if(this.eventEndDate == null) {
			return "";
		}else {
			return new SimpleDateFormat("dd/MM/yyyy").format(this.eventEndDate);
		}
		
	}
}
