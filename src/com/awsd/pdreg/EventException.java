package com.awsd.pdreg;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;

public class EventException extends ServletException {

	private static final long serialVersionUID = 1922134837151593923L;

	private Date evtDate;

	public EventException(String reason) {

		super(reason);
		this.evtDate = (Calendar.getInstance()).getTime();
	}

	public EventException(Date evtDate, String reason) {

		super(reason);
		this.evtDate = (Date) evtDate.clone();
	}

	public String getEventDate() {

		return evtDate.toString();
	}
}