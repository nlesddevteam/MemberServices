package com.awsd.pdreg.handler;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.awsd.pdreg.Event;
import com.awsd.pdreg.EventDB;
import com.awsd.pdreg.EventException;
import com.awsd.pdreg.EventRequirement;
import com.awsd.pdreg.EventType;
import com.awsd.pdreg.EventTypeDB;
import com.awsd.pdreg.dao.EventCategoryManager;
import com.awsd.pdreg.worker.FirstClassWorkerThread;
import com.esdnl.servlet.RequestHandlerImpl;

public class ScheduleEventRequestHandler extends RequestHandlerImpl {

	public ScheduleEventRequestHandler() {

		this.requiredPermissions = new String[] {
				"CALENDAR-SCHEDULE", "CALENDAR-SCHEDULE-ALL"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String name, desc, location, dStr, dEndStr, limited, evttime, starttime, finishtime, option = "";
		java.util.Date d = null, endd = null;
		Calendar cur, evt;
		int curYear, curDay, curMonth, evtYear, evtDay, evtMonth, evtSchoolID, evtSchoolZoneID;
		int e_id = 0;
		Event event = null;
		EventType type = null;
		int max = 0, evtTypeID;

		super.handleRequest(request, response);

		try {

			if (!form.exists("passthrough")) {

				if (form.isMultipart()) {
					if (!form.exists("evttype")) {
						throw new EventException("Event TYPE Not Provided.");
					}
					else {
						evtTypeID = form.getInt("evttype");
					}

					name = form.get("EventName");
					if (StringUtils.isEmpty(name)) {
						throw new EventException("Event Name Not Provided.");
					}

					desc = form.get("EventDesc");
					if (StringUtils.isEmpty(desc)) {
						throw new EventException("Event Description Not Provided");
					}

					location = form.get("EventLocation");
					if (StringUtils.isEmpty(location)) {
						throw new EventException("Event Location Not Provided");
					}

					if (StringUtils.isEmpty(form.get("EventZoneID"))) {
						throw new EventException("School Zone Not Provided.");
					}
					else {
						evtSchoolZoneID = form.getInt("EventZoneID");
					}

					if (StringUtils.isNotEmpty(form.get("EventSchoolID"))) {
						evtSchoolID = form.getInt("EventSchoolID");
					}
					else {
						evtSchoolID = 0;
					}

					dStr = form.get("EventDate");
					if (StringUtils.isEmpty(dStr)) {
						throw new EventException("Event Date Not Provided.");
					}
					else {
						d = form.getDate("EventDate");

						cur = Calendar.getInstance();
						curYear = cur.get(Calendar.YEAR);
						curDay = cur.get(Calendar.DAY_OF_MONTH);
						curMonth = cur.get(Calendar.MONTH);

						evt = Calendar.getInstance();
						evt.setTime(d);
						evtYear = evt.get(Calendar.YEAR);
						evtDay = evt.get(Calendar.DAY_OF_MONTH);
						evtMonth = evt.get(Calendar.MONTH);

						if ((evtYear < curYear) || ((evtYear == curYear) && (evtMonth < curMonth))
								|| ((evtYear == curYear) && (evtMonth == curMonth) && (evtDay <= curDay))) {
							throw new EventException(d, "Event Date[" + sdf.format(d) + "] has already passed.");
						}
					}

					dEndStr = form.get("EventEndDate");
					if (StringUtils.isNotEmpty(dEndStr)) {
						endd = form.getDate("EventEndDate");

						evt.setTime(endd);
						evtYear = evt.get(Calendar.YEAR);
						evtDay = evt.get(Calendar.DAY_OF_MONTH);
						evtMonth = evt.get(Calendar.MONTH);

						if ((evtYear < curYear) || ((evtYear == curYear) && (evtMonth < curMonth))
								|| ((evtYear == curYear) && (evtMonth == curMonth) && (evtDay <= curDay))) {
							throw new EventException(endd, "Event Date[" + sdf.format(endd) + "] has already passed.");
						}

						if (d.after(endd)) {
							throw new EventException("Start Data occurs after End Date");
						}
					}
					else {
						endd = null;
					}

					type = EventTypeDB.getEventType(evtTypeID);

					if (type.getEventTypeName().equalsIgnoreCase("CLOSE-OUT DAY PD SESSION")) {
						option = form.get("closeoutoption");
						if (StringUtils.isEmpty(option)) {
							throw new EventException("Close-out Option required.");
						}
					}

					if (type.getEventTypeName().equalsIgnoreCase("PD OPPORTUNITY")
							|| type.getEventTypeName().equalsIgnoreCase("CLOSE-OUT DAY PD SESSION")
							|| type.getEventTypeName().equalsIgnoreCase("SCHOOL PD REQUEST")) {
						limited = form.get("limited");
						if (StringUtils.isNotEmpty(limited)) {
							if (StringUtils.isNotEmpty(form.get("max"))) {
								max = form.getInt("max");
							}
							else {
								throw new EventException("Max Participants required.");
							}
						}

						if (type.getEventTypeName().equalsIgnoreCase("SCHOOL PD REQUEST")) {
							if (!form.uploadFileExists("agendafile")) {
								throw new EventException("Agenda file is required.");
							}
						}
					}
					else {
						max = 0;
					}

					evttime = form.get("evttime");
					if (StringUtils.isNotEmpty(evttime)) {
						try {
							starttime = form.get("shour") + ":" + form.get("sminute") + " " + form.get("sAMPM");
						}
						catch (NullPointerException e) {
							throw new EventException("Start time required");
						}

						try {
							finishtime = form.get("fhour") + ":" + form.get("fminute") + " " + form.get("fAMPM");
						}
						catch (NullPointerException e) {
							throw new EventException("Finish time required");
						}
					}
					else {
						starttime = "UNKNOWN";
						finishtime = "UNKNOWN";
					}

					event = new Event(evtTypeID, name, desc, d, endd, location, evtSchoolID, evtSchoolZoneID, usr.getPersonnel().getPersonnelID(), starttime, finishtime, max, option, form.exists("chk-is-government-funded"));

					String[] evtreqs = form.getArray("evtreqs");
					EventRequirement evtreq = null;

					if (evtreqs != null && evtreqs.length > 0) {
						for (String req : evtreqs) {
							evtreq = new EventRequirement(req);

							if (req.equalsIgnoreCase("Computer Bank"))
								evtreq.setExtrainfo(form.get("computerbank"));
							else if (req.equalsIgnoreCase("Software Installation"))
								evtreq.setExtrainfo(form.get("softwarerequired"));
							else if (req.equalsIgnoreCase("Special Requirements"))
								evtreq.setExtrainfo(form.get("otherreqs"));

							event.addEventRequirement(evtreq);
						}
					}

					int[] evtcats = form.getIntArray("evtcats");

					if (evtcats != null && evtcats.length > 0) {
						for (int cat : evtcats) {
							event.addEventCategory(EventCategoryManager.getEventCategory(cat));
						}
					}

					e_id = EventDB.addEvent(event);

					if (e_id > 0) {

						if (!event.isDistrictCalendarEntry() && !event.isHolidayCalendarEntry() && !event.isReminderCalendarEntry()) {
							System.err.println("SCHEDULING: " + event);
							event.setEventID(e_id);

							if (!type.getEventTypeName().equalsIgnoreCase("SCHOOL PD REQUEST")) {

								if (form.uploadFileExists("agendafile")) {
									try {
										save_file("agendafile", "/PDReg/agendas/", Integer.toString(e_id));
									}
									catch (Exception e) {
										EventDB.removeEvent(event);

										throw new EventException(e.getMessage());
									}
								}

								new FirstClassWorkerThread(usr.getPersonnel(), new Event[] {
									event
								}, FirstClassWorkerThread.SCHEDULE_EVENT).start();
							}
							else {
								String filename = "";
								try {
									filename = save_file("agendafile", "/PDReg/agendas/", Integer.toString(e_id));
								}
								catch (Exception e) {
									EventDB.removeEvent(event);

									throw new EventException(e.getMessage());
								}

								File f_tmp = new File(session.getServletContext().getRealPath("/") + "/PDReg/agendas/" + filename);

								new FirstClassWorkerThread(usr.getPersonnel(), new Event[] {
									event
								}, FirstClassWorkerThread.REQUEST_SCHOOL_PD_EVENT, new File[] {
									f_tmp
								}).start();
							}
						}

						if (type.getEventTypeName().equalsIgnoreCase("SCHOOL PD REQUEST")) {
							request.setAttribute("msgOK", "PD request sent to "
									+ usr.getPersonnel().getSchool().getSchoolFamily().getProgramSpecialist().getFullNameReverse()
									+ " for approval.");
						}
						else {
							request.setAttribute("msgOK", "SUCCESS: Event Scheduled Successfully.");
						}
					}
					else {
						request.setAttribute("msgERR", "ERROR: Event Scheduling Unsuccessfully.");
					}
				}
			}
		}
		catch (EventException e) {
			request.setAttribute("msgERR", e.getMessage());
		}

		return "scheduleevent.jsp";
	}
}