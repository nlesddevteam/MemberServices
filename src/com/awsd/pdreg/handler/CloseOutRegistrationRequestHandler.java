package com.awsd.pdreg.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.pdreg.CloseoutEvents;
import com.awsd.pdreg.Event;
import com.awsd.pdreg.EventDB;
import com.awsd.pdreg.EventException;
import com.awsd.pdreg.worker.FirstClassWorkerThread;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;

public class CloseOutRegistrationRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		CloseoutEvents events = null;
		Event evt = null;
		// Event tmp = null;
		// Iterator iter = null;
		String path = "";
		String options[] = null;
		int id;
		HttpSession session = null;
		User usr = null;
		boolean registered = false;

		try {
			session = request.getSession(false);
			if ((session != null) && (session.getAttribute("usr") != null)) {
				usr = (User) session.getAttribute("usr");
				if (!(usr.getUserPermissions().containsKey("CALENDAR-VIEW"))) {
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
				path = "error.jsp";
				throw new EventException("Event ID required for registration.");
			}
			options = new String[] {
					"A", "B", "C"
			};
			events = new CloseoutEvents(evt, options);
			for (int i = 0; i < options.length; i++) {
				for (Event tmp : events.getEvents(options[i])) {
					if (request.getParameter(tmp.getEventCloseoutOption() + "_" + tmp.getEventID()) != null) {
						// register event for user
						registered = EventDB.registerEvent(usr, tmp);
						if (registered) {
							new FirstClassWorkerThread(usr.getPersonnel(), new Event[] {
								tmp
							}, FirstClassWorkerThread.REGISTER_EVENT).start();

							request.setAttribute("msg", "Registration successful.");
						}
						else {
							request.setAttribute("msg", "Registration unsuccessful. Please Try again.");
						}
					}
				}
			}

			request.setAttribute("events", events);

			path = "closeout.jsp";
		}
		catch (NumberFormatException e) {
			path = "error.jsp";
			request.setAttribute("err", new EventException("Could not parse Event ID.\n" + e));
		}
		catch (EventException e) {
			path = "error.jsp";
			request.setAttribute("err", e);
		}

		return path;
	}
}