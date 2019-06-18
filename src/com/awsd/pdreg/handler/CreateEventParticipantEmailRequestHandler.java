package com.awsd.pdreg.handler;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.pdreg.Event;
import com.awsd.pdreg.EventDB;
import com.awsd.pdreg.EventException;
import com.awsd.pdreg.RegisteredPersonnelCollection;
import com.awsd.personnel.Personnel;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.security.UserPermissions;
import com.awsd.servlet.RequestHandler;

public class CreateEventParticipantEmailRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		HttpSession session = null;
		User usr = null;
		UserPermissions permissions = null;
		RegisteredPersonnelCollection users = null;
		Event evt = null;
		Personnel p = null;
		Iterator iter = null;
		int id, i = 0;
		StringBuffer to = null;

		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("usr") != null)) {
			usr = (User) session.getAttribute("usr");
			permissions = usr.getUserPermissions();
			if (!(permissions.containsKey("CALENDAR-VIEW") && (permissions.containsKey("CALENDAR-SCHEDULE") || permissions.containsKey("CALENDAR-VIEW-PARTICIPANTS")))) {
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
			}
		}
		else {
			throw new SecurityException("User login required.");
		}

		if (request.getParameter("id") != null) {
			id = Integer.parseInt((String) request.getParameter("id"));
			evt = EventDB.getEvent(id);
		}
		else {
			throw new EventException("Event ID required for registration.");
		}

		users = new RegisteredPersonnelCollection(evt, RegisteredPersonnelCollection.SORT_BY_SCHOOL);

		to = new StringBuffer();

		iter = users.iterator();
		while (iter.hasNext()) {
			p = (Personnel) iter.next();
			if (iter.hasNext())
				to.append(p.getEmailAddress() + ";");
			else
				to.append(p.getEmailAddress());
		}

		request.setAttribute("to", to.toString());
		request.setAttribute("subject", evt.getEventName());
		return "participantemail.jsp";
	}
}