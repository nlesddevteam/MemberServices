package com.awsd.pdreg.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.pdreg.Event;
import com.awsd.pdreg.EventAttendeeCollection;
import com.awsd.pdreg.EventDB;
import com.awsd.pdreg.EventException;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class ViewEventAttendanceRequestHandler extends RequestHandlerImpl {

	public ViewEventAttendanceRequestHandler() {

		this.requiredPermissions = new String[] {
			"CALENDAR-SCHEDULE"
		};

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("id", "Event ID required for attendance tracking.")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		EventAttendeeCollection attendees = null;
		Event evt = null;

		super.handleRequest(request, response);

		if (validate_form()) {
			evt = EventDB.getEvent(form.getInt("id"));

			if (evt.hasParticipants()) {
				attendees = new EventAttendeeCollection(evt, EventAttendeeCollection.SORT_BY_SCHOOL);
			}

			request.setAttribute("EventAttendees", attendees);
		}
		else {
			throw new EventException(this.validator.getErrorString());
		}

		return "eventattendance.jsp";
	}
}