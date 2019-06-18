package com.awsd.pdreg.handler;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.awsd.pdreg.Event;
import com.awsd.pdreg.EventDB;
import com.awsd.pdreg.EventException;
import com.awsd.pdreg.EventRequirement;
import com.awsd.pdreg.EventType;
import com.awsd.pdreg.EventTypeDB;
import com.awsd.pdreg.dao.EventCategoryManager;
import com.awsd.pdreg.worker.FirstClassWorkerThread;
import com.awsd.personnel.PersonnelDB;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.security.UserPermissions;
import com.awsd.servlet.RequestHandler;

public class ModifyEventRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		Event evt = null;
		EventType type = null;
		int id = -1;

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String name, desc, location, dStr, dEndStr, limited, evttime, starttime, finishtime, option = "";
		java.util.Date d = null, endd = null;
		Calendar cur, evtcal, orgcal;
		int curYear, curDay, curMonth;
		int evtYear, evtDay, evtMonth;
		int orgYear, orgDay, orgMonth;
		int evtTypeID, evtSchoolID, evtSchoolZoneID;
		HttpSession session = null;
		User usr = null;
		UserPermissions permissions = null;
		boolean notify = false;

		int max = 0;

		try {
			session = request.getSession(false);
			if ((session != null) && (session.getAttribute("usr") != null)) {
				usr = (User) session.getAttribute("usr");
				permissions = usr.getUserPermissions();
				if (!(permissions.containsKey("CALENDAR-VIEW") && permissions.containsKey("CALENDAR-SCHEDULE"))) {
					throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
				}
			}
			else {
				throw new SecurityException("User login required.");
			}

			if (request.getParameter("id") != null) {
				id = Integer.parseInt((String) request.getParameter("id"));
			}
			else {
				throw new EventException("Event ID required for modification.");
			}

			request.setAttribute("modified", new Boolean(false));

			if (usr.isAdmin()) {
				request.setAttribute("SCHEDULERS", Arrays.asList(PersonnelDB.getPersonnelByPermission("CALENDAR-SCHEDULE")));
			}

			if (request.getParameter("confirmed") != null) {
				if (request.getParameter("evttype") == null) {
					throw new EventException("Event TYPE Not Provided.");
				}
				else {
					evtTypeID = Integer.parseInt(request.getParameter("evttype"));
				}

				name = (String) request.getParameter("EventName");
				if (name == null) {
					throw new EventException("Event Name Not Provided.");
				}

				desc = (String) request.getParameter("EventDesc");
				if (desc == null) {
					throw new EventException("Event Description Not Provided");
				}

				location = (String) request.getParameter("EventLocation");
				if (location == null) {
					throw new EventException("Event Location Not Provided");
				}

				if (StringUtils.isEmpty(request.getParameter("EventZoneID"))) {
					throw new EventException("School Zone Not Provided.");
				}
				else {
					evtSchoolZoneID = Integer.parseInt(request.getParameter("EventZoneID"));
				}

				if (StringUtils.isNotEmpty(request.getParameter("EventSchoolID"))) {
					evtSchoolID = Integer.parseInt(request.getParameter("EventSchoolID"));
				}
				else {
					evtSchoolID = 0;
				}

				dStr = (String) request.getParameter("EventDate");
				if (dStr == null) {
					throw new EventException("Event Date Not Provided.");
				}
				else {
					evt = EventDB.getEvent(id);

					try {
						d = sdf.parse(dStr);

						cur = Calendar.getInstance();
						curYear = cur.get(Calendar.YEAR);
						curDay = cur.get(Calendar.DAY_OF_MONTH);
						curMonth = cur.get(Calendar.MONTH);

						evtcal = Calendar.getInstance();
						evtcal.setTime(d);
						evtYear = evtcal.get(Calendar.YEAR);
						evtDay = evtcal.get(Calendar.DAY_OF_MONTH);
						evtMonth = evtcal.get(Calendar.MONTH);

						if (((evtYear < curYear) || ((evtYear == curYear) && (evtMonth < curMonth)) || ((evtYear == curYear)
								&& (evtMonth == curMonth) && (evtDay <= curDay)))
								&& !usr.isAdmin()) {
							throw new EventException(d, "Event Date[" + sdf.format(d) + "] has already passed.");
						}

						orgcal = Calendar.getInstance();
						orgcal.setTime(evt.getEventDate());
						orgYear = orgcal.get(Calendar.YEAR);
						orgDay = orgcal.get(Calendar.DAY_OF_MONTH);
						orgMonth = orgcal.get(Calendar.MONTH);

						if ((evtYear != orgYear) || (evtMonth != orgMonth) || (evtDay != orgDay)
								|| (!evt.getEventLocation().equalsIgnoreCase(location))) {
							notify = true;
						}
					}
					catch (ParseException e) {
						throw new EventException("Invalid Event Date Format.\n" + e.getMessage());
					}
				}

				dEndStr = (String) request.getParameter("EventEndDate");
				if ((dEndStr != null) && !dEndStr.equals("")) {
					try {
						endd = sdf.parse(dEndStr);

						evtcal.setTime(endd);
						evtYear = evtcal.get(Calendar.YEAR);
						evtDay = evtcal.get(Calendar.DAY_OF_MONTH);
						evtMonth = evtcal.get(Calendar.MONTH);

						if (((evtYear < curYear) || ((evtYear == curYear) && (evtMonth < curMonth)) || ((evtYear == curYear)
								&& (evtMonth == curMonth) && (evtDay <= curDay)))
								&& !usr.isAdmin()) {
							throw new EventException(endd, "Event Date[" + sdf.format(endd) + "] has already passed.");
						}

						if (d.after(endd)) {
							throw new EventException("Start Data occurs after End Date");
						}

						if ((evtYear != orgYear) || (evtMonth != orgMonth) || (evtDay != orgDay)
								|| (!evt.getEventLocation().equalsIgnoreCase(location))) {
							notify = true;
						}
					}
					catch (ParseException e) {
						throw new EventException("Invalid Event Date Format.\n" + e.getMessage());
					}
				}
				else {
					endd = null;
					if (((dEndStr == null) || dEndStr.equals("")) && (evt.getEventEndDate() != null)) {
						notify = true;
					}
				}

				type = EventTypeDB.getEventType(evtTypeID);

				if (type.getEventTypeName().equalsIgnoreCase("CLOSE-OUT DAY PD SESSION")) {
					option = request.getParameter("closeoutoption");
					if (option == null) {
						throw new EventException("Close-out Option required.");
					}
				}

				if (type.getEventTypeName().equalsIgnoreCase("PD OPPORTUNITY")
						|| type.getEventTypeName().equalsIgnoreCase("CLOSE-OUT DAY PD SESSION")) {
					limited = request.getParameter("limited");
					if (limited != null) {
						if (request.getParameter("max") != null) {
							max = Integer.parseInt(request.getParameter("max"));
						}
						else {
							throw new EventException("Max Participants required.");
						}
					}
					else {
						max = 0;
					}
				}
				else {
					max = 0;
				}

				evttime = request.getParameter("evttime");
				if (evttime != null) {
					try {
						starttime = request.getParameter("shour") + ":" + request.getParameter("sminute") + " "
								+ request.getParameter("sAMPM");
					}
					catch (NullPointerException e) {
						throw new EventException("Start time required");
					}

					try {
						finishtime = request.getParameter("fhour") + ":" + request.getParameter("fminute") + " "
								+ request.getParameter("fAMPM");
					}
					catch (NullPointerException e) {
						throw new EventException("Finish time required");
					}
				}
				else {
					starttime = "UNKNOWN";
					finishtime = "UNKNOWN";
				}

				// System.err.println("OPTION: " + option);

				int schedulerID = usr.isAdmin() ? Integer.parseInt(request.getParameter("ddl-scheduler"))
						: evt.getSchedulerID();

				Event event = new Event(id, type.getEventTypeID(), name, desc, d, endd, location, evtSchoolID, evtSchoolZoneID, schedulerID, starttime, finishtime, max, option, StringUtils.isNotEmpty(request.getParameter("chk-is-government-funded")));

				EventRequirement evtreq = null;
				if (request.getParameterValues("evtreqs") != null) {
					for (String req : request.getParameterValues("evtreqs")) {
						evtreq = new EventRequirement(req);

						if (req.equalsIgnoreCase("Computer Bank"))
							evtreq.setExtrainfo(request.getParameter("computerbank"));
						else if (req.equalsIgnoreCase("Software Installation"))
							evtreq.setExtrainfo(request.getParameter("softwarerequired"));
						else if (req.equalsIgnoreCase("Special Requirements"))
							evtreq.setExtrainfo(request.getParameter("otherreqs"));

						event.addEventRequirement(evtreq);
					}
				}

				String[] evtcats = request.getParameterValues("evtcats");

				if (evtcats != null && evtcats.length > 0) {
					for (String cat : evtcats) {
						event.addEventCategory(EventCategoryManager.getEventCategory(Integer.parseInt(cat)));
					}
				}

				boolean flag = EventDB.updateEvent(event);

				if (flag) {
					request.setAttribute("msg", "Event modified successfully.");
					request.setAttribute("modified", new Boolean(true));
					// notify all registered personnel that event date has changed.
					if (notify) {
						// new ModifyEventWorkerThread(evt, EventDB.getEvent(id), new
						// RegisteredPersonnel(evt)).start();
						new FirstClassWorkerThread(usr.getPersonnel(), new Event[] {
								evt, EventDB.getEvent(id)
						}, FirstClassWorkerThread.MODIFY_EVENT).start();
					}
				}
				else {
					request.setAttribute("msg", "Event modification unsuccessfully.");
				}
			}

			evt = EventDB.getEvent(id);
			request.setAttribute("evt", evt);

			// path = "modifyevent.jsp";
		}
		catch (NumberFormatException e) {
			throw new EventException("Could not parse Event ID.\n" + e);
		}
		catch (EventException e) {
			request.setAttribute("msg", e.getMessage());
			evt = EventDB.getEvent(id);
			request.setAttribute("evt", evt);
		}
		// return path;
		return "modifyevent.jsp";
	}
}