package com.awsd.pdreg.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.pdreg.Event;
import com.awsd.pdreg.EventDB;
import com.awsd.pdreg.EventException;
import com.awsd.pdreg.RegisteredPersonnelCollection;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class ViewEventParticipantsRequestHandler extends RequestHandlerImpl {

	public ViewEventParticipantsRequestHandler() {

		this.requiredPermissions = new String[] {
				"CALENDAR-SCHEDULE", "CALENDAR-VIEW-PARTICIPANTS"
		};

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("id", "Event ID required for registration.")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		RegisteredPersonnelCollection users = null;
		Event evt = null;

		super.handleRequest(request, response);

		if (validate_form()) {
			evt = EventDB.getEvent(form.getInt("id"));

			users = new RegisteredPersonnelCollection(evt, RegisteredPersonnelCollection.SORT_BY_SCHOOL);

			request.setAttribute("RegisteredPersonnel", users);
		}
		else {
			throw new EventException(this.validator.getErrorString());
		}

		return "eventparticipants.jsp";
	}
}