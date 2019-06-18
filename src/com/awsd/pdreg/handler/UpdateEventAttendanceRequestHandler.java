package com.awsd.pdreg.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.pdreg.Event;
import com.awsd.pdreg.EventAttendee;
import com.awsd.pdreg.EventAttendeeCollection;
import com.awsd.pdreg.EventDB;
import com.awsd.pdreg.EventException;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class UpdateEventAttendanceRequestHandler extends RequestHandlerImpl {

	public UpdateEventAttendanceRequestHandler() {

		this.requiredPermissions = new String[] {
			"CALENDAR-SCHEDULE"
		};

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("hdn-event-id", "Event ID required to add attendee.")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		Event evt = null;

		super.handleRequest(request, response);

		if (validate_form()) {
			evt = EventDB.getEvent(form.getInt("hdn-event-id"));

			if (evt == null) {
				throw new EventException("Event not found.");
			}
			else {
				String[] attendees = form.getArray("attendees[]");

				Map<Integer, EventAttendee> map = EventDB.getEventAttendees(evt);
				Map<Integer, EventAttendee> attended = new HashMap<Integer, EventAttendee>();
				EventAttendee attendee = null;
				Personnel p = null;
				int pid = 0;

				if (attendees != null && attendees.length > 0) {
					for (String a : attendees) {
						pid = Integer.parseInt(a);

						attendee = map.get(pid);

						if (attendee != null) {
							attended.put(attendee.getPersonnel().getPersonnelID(), attendee);

							if (!attendee.isAttended()) {
								EventDB.updateAttendance(attendee.getPersonnel(), evt, true);
							}
						}
						else {
							p = PersonnelDB.getPersonnel(pid);

							if (p != null) {
								EventDB.addAttendee(p, evt);

								attended.put(p.getPersonnelID(), new EventAttendee(evt, p, true));
							}
						}
					}
				}

				for (EventAttendee ea : map.values()) {
					if (!attended.containsKey(ea.getPersonnel().getPersonnelID()) && ea.isAttended()) {
						EventDB.updateAttendance(ea.getPersonnel(), evt, false);
					}
				}

				request.setAttribute("EventAttendees", new EventAttendeeCollection(evt, EventAttendeeCollection.SORT_BY_SCHOOL));
			}
		}
		else {
			throw new EventException(this.validator.getErrorString());
		}

		return "eventattendance.jsp";
	}
}