package com.awsd.pdreg.handler;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.pdreg.Event;
import com.awsd.pdreg.EventDB;
import com.awsd.pdreg.EventException;
import com.awsd.pdreg.EventType;
import com.awsd.pdreg.worker.FirstClassWorkerThread;
import com.awsd.security.SecurityException;
import com.awsd.security.UserPermissions;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.util.StringUtils;

public class SchoolPDRequestAdminRequestHandler extends RequestHandlerImpl {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		UserPermissions permissions = null;
		Event evt = null;
		String userid = null;
		String op = null;
		String msg = null;
		String msgOK = null;
		String path = "information.jsp";

		userid = request.getParameter("u");

		if (StringUtils.isEmpty(userid) || !usr.getUsername().equalsIgnoreCase(userid)) {
			throw new SecurityException("Illegal Access [" + usr.getUsername() + "]");
		}
		else {
			try {
				permissions = usr.getUserPermissions();

				if (!(permissions.containsKey("CALENDAR-VIEW") && permissions.containsKey("CALENDAR-SCHOOL-PD-ADMIN"))) {
					throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
				}

				try {
					op = request.getParameter("op");
					if (op != null) {
						evt = EventDB.getEvent(Integer.parseInt(request.getParameter("id")));

						if (evt != null) {
							if (evt.isSchoolCloseoutRequest() || evt.isSchoolPDRequest()) {
								if (evt.getEventDate().after(Calendar.getInstance().getTime())) {
									if (op.equals("approve")) {
										switch (evt.getEventType().getEventTypeID()) {
										case EventType.SCHOOL_PD_REQUEST:
											EventDB.setEventType(evt.getEventID(), EventType.SCHOOL_PD_ENTRY);

											break;
										case EventType.SCHOOL_CLOSEOUT_REQUEST:
											EventDB.setEventType(evt.getEventID(), EventType.DISTRICT_CALENDAR_CLOSEOUT_ENTRY);
											break;
										}
										msgOK = "SUCCESS: Request Approved.";
										request.setAttribute("msgOK", msgOK);
										(new FirstClassWorkerThread(evt.getScheduler(), new Event[] {
											evt
										}, FirstClassWorkerThread.REQUEST_APPROVED)).start();
									}
									else if (op.equals("decline")) {
										if (!StringUtils.isEmpty(request.getParameter("CONFIRMED"))) {
											EventDB.removeEvent(evt);
											msg = "Request Declined.";
											(new FirstClassWorkerThread(evt.getScheduler(), new Event[] {
												evt
											}, FirstClassWorkerThread.REQUEST_DECLINED, "Comments:<br>" + request.getParameter("comments"))).start();
										}
										else {
											msg = "Are you sure you want to decline this request?";
											path = "information.jsp?u=" + userid + "&op=decline" + "&id=" + evt.getEventID()
													+ "&action=schoolPDRequestAdmin.html";
										}
									}
								}
								else {
									msg = "Sorry. Request date has passed and request has been deleted.";
									EventDB.removeEvent(evt);
								}
							}
							else {
								msg = "Sorry, Request has previously been filled.";
							}
						}
						else {
							msg = "ERROR: Event is no longer available.";
						}
					}
				}
				catch (EventException e) {
					msg = "ERROR: Could not complete requested operation.";
					e.printStackTrace(System.err);
				}
			}
			catch (SecurityException e) {
				msg = "ERROR: You do not have the necessary permissions to access this page. Verify that you have correctly entered your username and password.";
				e.printStackTrace(System.err);
			}
		}

		request.setAttribute("msg", msg);

		return path;
	}
}