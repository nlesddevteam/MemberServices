package com.awsd.pdreg.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.pdreg.Event;
import com.awsd.pdreg.EventAttendeeCollection;
import com.awsd.pdreg.EventDB;
import com.awsd.pdreg.EventException;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class AddEventAttendeeRequestHandler extends RequestHandlerImpl {

	public AddEventAttendeeRequestHandler() {

		this.requiredPermissions = new String[] {
			"CALENDAR-SCHEDULE"
		};

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("hdn-event-id", "Event ID required to add attendee."),
				new RequiredFormElement("lst-attendee-id", "Personnel ID required to add attendee."),
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		Event evt = null;
		Personnel p = null;

		super.handleRequest(request, response);

		if (validate_form()) {
			evt = EventDB.getEvent(form.getInt("hdn-event-id"));
			p = PersonnelDB.getPersonnel(form.getInt("lst-attendee-id"));

			if (evt == null) {
				throw new EventException("Event not found.");
			}
			else if (p == null) {
				throw new EventException("Personnel not found.");
			}
			else {
				EventDB.addAttendee(p, evt);

				request.setAttribute("EventAttendees", new EventAttendeeCollection(evt, EventAttendeeCollection.SORT_BY_SCHOOL));
			}
		}
		else {
			throw new EventException(this.validator.getErrorString());
		}

		return "eventattendance.jsp";
	}
}