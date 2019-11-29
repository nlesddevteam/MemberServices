package com.awsd.pdreg.handler;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.pdreg.Event;
import com.awsd.pdreg.EventDB;
import com.awsd.pdreg.EventException;
import com.awsd.pdreg.worker.FirstClassWorkerThread;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class RegisterEventRequestHandler extends RequestHandlerImpl {

	public RegisterEventRequestHandler() {

		this.requiredPermissions = new String[] {
				"CALENDAR-VIEW"
		};

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("id", "Event ID required for registration.")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		Event evt = null;
		boolean registered = false;

		try {

			if (validate_form()) {
				evt = EventDB.getEvent(form.getInt("id"));

				if (request.getParameter("confirmed") != null) {
					// register event for user
					registered = EventDB.registerEvent(usr, evt);
					if (registered) {
						// new EventRegistrationWorkerThread(usr, evt).start();
						new FirstClassWorkerThread(usr.getPersonnel(), new Event[] {
								evt
						}, FirstClassWorkerThread.REGISTER_EVENT).start();

						request.setAttribute("msgOK", "SUCCESS! Registration successful.");
					}
					else {
						request.setAttribute("msgERR", "ERROR: Registration unsuccessful. Please Try again.");
					}
				}

				request.setAttribute("evt", evt);
				if (request.getParameter("details") != null) {
					request.setAttribute("details", request.getParameter("details"));
				}

				File agenda_dir = new File(session.getServletContext().getRealPath("/") + "/PDReg/agendas/");
				File[] agendas = agenda_dir.listFiles(new AgendaFilenameFilter(evt));

				if (agendas != null && agendas.length > 0) {
					request.setAttribute("AGENDA_FILE", agendas[0]);
					} 

			}

			path = "registerevent.jsp";
		}
		catch (NumberFormatException e) {
			
			request.setAttribute("err", new EventException("Could not parse Event ID.\n" + e));
			path = "error.jsp";
		}
		catch (EventException e) {
			request.setAttribute("err", e);
			path = "error.jsp";
		}

		return path;
	}

	public class AgendaFilenameFilter implements FilenameFilter {

		private Event evt;

		public AgendaFilenameFilter(Event evt) {

			this.evt = evt;
		}

		public boolean accept(File dir, String name) {

			boolean check = false;

			if (name.indexOf(evt.getEventID() + ".") >= 0)
				check = true;

			return check;
		}
	}
}