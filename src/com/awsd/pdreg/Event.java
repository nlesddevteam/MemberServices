package com.awsd.pdreg;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import com.awsd.common.Utils;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.PersonnelException;
import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.awsd.school.SchoolException;
import com.awsd.security.User;
import com.nlesd.school.bean.SchoolZoneBean;
import com.nlesd.school.service.SchoolZoneService;

public class Event {

	private static final long serialVersionUID = 650674613771810030L;

	private int evtID;
	private int evtTypeID;
	private String evtName;
	private String evtDesc;
	private Date evtDate;
	private Date evtEndDate;
	private int numdays;
	private String evtLocation;
	private int evtSchoolID;
	private School evtSchool;
	private int evtSchoolZoneID;
	private SchoolZoneBean evtSchoolZone;
	private String starttime;
	private String finishtime;
	private String closeout_option;
	private int max;
	private int schedulerID;
	Personnel scheduler = null;
	private int participants;

	private boolean governmentFunded;
	private ArrayList<EventRequirement> eventRequirements;
	private ArrayList<EventCategory> eventCategories;

	public Event(int evtTypeID, String evtName, String evtDesc, Date evtDate, Date evtEndDate, String evtLocation,
			int evtSchoolID, int evtSchoolZoneID, int schedulerID, String starttime, String finishtime, int max,
			String closeout_option, boolean governmentFunded) {

		this.evtTypeID = evtTypeID;
		this.evtName = evtName;
		this.evtDesc = evtDesc;
		this.evtLocation = evtLocation;
		this.evtSchoolID = evtSchoolID;
		this.evtSchoolZoneID = evtSchoolZoneID;
		this.evtDate = evtDate;
		this.evtEndDate = evtEndDate;
		this.schedulerID = schedulerID;

		this.starttime = starttime;
		this.finishtime = finishtime;
		this.max = max;
		this.closeout_option = closeout_option;

		this.participants = 0;

		this.governmentFunded = governmentFunded;

		this.calcEventDays();
	}

	public Event(int evtID, int evtTypeID, String evtName, String evtDesc, Date evtDate, Date evtEndDate,
			String evtLocation, int evtSchoolID, int evtSchoolZoneID, int schedulerID, String starttime, String finishtime,
			int max, String closeout_option, boolean governmentFunded) {

		this(evtTypeID, evtName, evtDesc, evtDate, evtEndDate, evtLocation, evtSchoolID, evtSchoolZoneID, schedulerID, starttime, finishtime, max, closeout_option, governmentFunded);

		this.evtID = evtID;
	}

	public int getEventID() {

		return evtID;
	}

	public void setEventID(int e_id) {

		this.evtID = e_id;
	}

	public EventType getEventType() throws EventException {

		EventType type;

		try {
			type = EventTypeDB.getEventType(evtTypeID);

			if (type == null) {
				throw new EventException("Could not find event type");
			}
		}
		catch (EventTypeException e) {
			throw new EventException("Could not find event type");
		}

		return type;
	}

	public void setEventType(EventType type) {

		if (type != null) {
			this.evtTypeID = type.getEventTypeID();
		}
		else {
			throw new NullPointerException("EventType cannot be null");
		}
	}

	public String getEventName() {

		return evtName;
	}

	public void setEventName(String evtName) {

		this.evtName = evtName;
	}

	public String getEventDescription() {

		return evtDesc;
	}

	public void setEventDescription(String evtDesc) {

		this.evtDesc = evtDesc;
	}

	public String getEventLocation() {

		return evtLocation;
	}

	public void setEventLocation(String evtLocation) {

		this.evtLocation = evtLocation;
	}

	public int getEventSchoolID() {

		return this.evtSchoolID;
	}

	public School getEventSchool() throws SchoolException {

		if (this.evtSchool == null && this.evtSchoolID > 0) {
			this.evtSchool = SchoolDB.getSchool(this.evtSchoolID);
		}

		return this.evtSchool;
	}

	public void setEventSchoolID(int evtSchoolID) {

		this.evtSchoolID = evtSchoolID;
	}

	public int getEventSchoolZoneID() {

		return this.evtSchoolZoneID;
	}

	public SchoolZoneBean getEventSchoolZone() throws SchoolException {

		if (this.evtSchoolZone == null && this.evtSchoolZoneID > 0) {
			this.evtSchoolZone = SchoolZoneService.getSchoolZoneBean(this.evtSchoolZoneID);
		}

		return this.evtSchoolZone;
	}

	public void setEventSchoolZoneID(int evtSchoolZoneID) {

		this.evtSchoolZoneID = evtSchoolZoneID;
	}

	public Date getEventDate() {

		return evtDate;
	}

	public void setEventDate(Date evtDate) {

		this.evtDate = evtDate;
	}

	public Date getEventEndDate() {

		return evtEndDate;
	}

	public void setEventEndDate(Date evtEndDate) {

		this.evtEndDate = evtEndDate;
	}

	public boolean isMultiDayEvent() {

		return ((this.evtEndDate != null) && this.evtEndDate.after(this.evtDate));
	}

	public boolean isActive() {

		return ((Utils.compareCurrentDate(getEventDate()) == 0) || (((getEventEndDate() != null)
				&& (Utils.compareCurrentDate(getEventDate()) == -1) && (Utils.compareCurrentDate(getEventEndDate()) >= 0))));
	}

	public boolean isPast() {

		return ((Utils.compareCurrentDate(getEventDate()) == -1 && (getEventEndDate() == null)) || ((getEventEndDate() != null) && (Utils.compareCurrentDate(getEventEndDate()) == -1)));
	}

	public boolean isFuture() {

		return (Utils.compareCurrentDate(getEventDate()) == 1);
	}

	public String getEventStartTime() {

		return starttime;
	}

	public String getEventStartTimeHour() {

		return starttime.substring(0, starttime.indexOf(":"));
	}

	public String getEventStartTimeMinute() {

		return starttime.substring(starttime.indexOf(":") + 1, starttime.indexOf(" "));
	}

	public String getEventStartTimeAMPM() {

		return starttime.substring(starttime.indexOf(" ") + 1);
	}

	public void setEventStartTime(String starttime) {

		this.starttime = starttime;
	}

	public String getEventFinishTime() {

		return finishtime;
	}

	public String getEventFinishTimeHour() {

		return finishtime.substring(0, finishtime.indexOf(":"));
	}

	public String getEventFinishTimeMinute() {

		return finishtime.substring(finishtime.indexOf(":") + 1, finishtime.indexOf(" "));
	}

	public String getEventFinishTimeAMPM() {

		return finishtime.substring(finishtime.indexOf(" ") + 1);
	}

	public void setEventFinishTime(String finishtime) {

		this.finishtime = finishtime;
	}

	public int getEventMaximumParticipants() {

		return max;
	}

	public void setEventMaximumParticipants(int max) {

		this.max = max;
	}

	public String getEventCloseoutOption() {

		return closeout_option;
	}

	public void setEventCloseoutOption(String closeout_option) {

		this.closeout_option = closeout_option;
	}

	public int getRegistrationCount() throws EventException {

		return this.participants;
	}

	public void setRegistrationCount(int participants) {

		this.participants = participants;
	}

	public boolean isFull() throws EventException {

		int cur;
		boolean check = false;

		if (max == 0) {
			check = false;
		}
		else {
			cur = this.participants;
			if (cur >= max) {
				check = true;
			}
			else {
				check = false;
			}
		}
		return check;
	}

	public Personnel getScheduler() {

		if (scheduler == null) {
			try {
				scheduler = PersonnelDB.getPersonnel(schedulerID);
			}
			catch (PersonnelException e) {
				System.err.println(e);
			}
		}

		return scheduler;
	}

	public void setScheduler(Personnel p) {

		this.scheduler = p;
	}

	public int getSchedulerID() {

		return getScheduler().getPersonnelID();
	}

	public boolean isScheduler(User u) {

		return (u != null) && (u.getPersonnel() != null) && (this.scheduler != null)
				&& (u.getPersonnel().getPersonnelID() == this.getSchedulerID());
	}

	public int getNumberEventDays() {

		return this.numdays;
	}

	public boolean isGovernmentFunded() {

		return governmentFunded;
	}

	public void setGovernmentFunded(boolean governmentFunded) {

		this.governmentFunded = governmentFunded;
	}

	public ArrayList<EventRequirement> getEventRequirements() {

		return eventRequirements;
	}

	public boolean hasEventRequirements() {

		return this.eventRequirements != null && this.eventRequirements.size() > 0;
	}

	public void setEventRequirements(ArrayList<EventRequirement> eventRequirements) {

		this.eventRequirements = eventRequirements;
	}

	public void addEventRequirement(EventRequirement req) {

		if (this.eventRequirements == null) {
			this.eventRequirements = new ArrayList<EventRequirement>();
		}

		this.eventRequirements.add(req);
	}

	public void addEventRequirements(Collection<EventRequirement> reqs) {

		if (this.eventRequirements == null) {
			this.eventRequirements = new ArrayList<EventRequirement>();
		}

		this.eventRequirements.addAll(reqs);
	}

	public ArrayList<EventCategory> getEventCategories() {

		return eventCategories;
	}

	public boolean hasEventCategories() {

		return this.eventCategories != null && this.eventCategories.size() > 0;
	}

	public void setEventCategories(ArrayList<EventCategory> eventCategories) {

		this.eventCategories = eventCategories;
	}

	public void addEventCategory(EventCategory cat) {

		if (this.eventCategories == null) {
			this.eventCategories = new ArrayList<EventCategory>();
		}

		this.eventCategories.add(cat);
	}

	public void addEventCategories(Collection<EventCategory> cats) {

		if (this.eventCategories == null) {
			this.eventCategories = new ArrayList<EventCategory>();
		}

		this.eventCategories.addAll(cats);
	}

	public boolean equals(Object obj) {

		Event evt = (Event) obj;

		return (this.getEventID() == evt.getEventID());
	}

	public boolean isDistrictCalendarEntry() throws EventException {

		return (this.getEventType().getEventTypeName().equalsIgnoreCase("DISTRICT CALENDAR ENTRY"));
	}

	public boolean isDistrictCalendarCloseOutEntry() throws EventException {

		return (this.getEventType().getEventTypeName().equalsIgnoreCase("DISTRICT CALENDAR CLOSE-OUT ENTRY"));
	}

	public boolean isCloseOutDaySession() throws EventException {

		return (this.getEventType().getEventTypeName().equalsIgnoreCase("CLOSE-OUT DAY PD SESSION"));
	}

	public boolean isPDOpportunity() throws EventException {

		return (this.getEventType().getEventTypeName().equalsIgnoreCase("PD OPPORTUNITY"));
	}

	public boolean isPrivateCalendarEntry() throws EventException {

		return (this.getEventType().getEventTypeName().equalsIgnoreCase("PRIVATE CALENDAR ENTRY"));
	}

	public boolean isHolidayCalendarEntry() throws EventException {

		return (this.getEventType().getEventTypeName().equalsIgnoreCase("DISTRICT CALENDAR HOLIDAY ENTRY"));
	}

	public boolean isReminderCalendarEntry() throws EventException {

		return (this.getEventType().getEventTypeName().equalsIgnoreCase("DISTRICT CALENDAR REMINDER ENTRY"));
	}

	public boolean isSchoolPDRequest() throws EventException {

		return (this.getEventType().getEventTypeName().equalsIgnoreCase("SCHOOL PD REQUEST"));
	}

	public boolean isSchoolPDEntry() throws EventException {

		return this.getEventType().getEventTypeName().equalsIgnoreCase("SCHOOL PD ENTRY");
	}

	public boolean isSchoolCloseoutRequest() throws EventException {

		return (this.getEventType().getEventTypeName().equalsIgnoreCase("SCHOOL CLOSE-OUT REQUEST"));
	}

	public boolean hasParticipants() throws EventException {

		return this.isCloseOutDaySession() || this.isPDOpportunity() || this.isSchoolPDEntry();
	}

	/*
	 * This method will calculate the current day in a multi-day event
	 */
	public int calcCurrentDay(Date cur) {

		Calendar sd = null;
		Calendar cd = null;
		int cnt = 0;

		if (this.isMultiDayEvent()) {
			sd = Calendar.getInstance();
			sd.setTime(this.evtDate);

			cd = Calendar.getInstance();
			cd.setTime(cur);

			while (!sd.after(cd)) {
				cnt++;
				sd.add(Calendar.DATE, 1);
			}
		}
		else {
			cnt = 1;
		}

		return cnt;
	}

	public String toString() {

		StringBuffer msg = null;
		try {
			msg = new StringBuffer("Type: " + this.getEventType().getEventTypeName());
			msg.append("\nName: " + evtName);
			msg.append("\nDescription: " + evtDesc);
			msg.append("\nLocation: " + evtLocation);
			if (this.evtSchool != null) {
				msg.append("\nSchool: " + evtSchool.getSchoolName());
			}
			if (this.evtSchoolZone != null) {
				msg.append("\nSchool Zone: " + evtSchoolZone.getZoneName());
			}
			if (!this.isDistrictCalendarEntry() && !this.isDistrictCalendarCloseOutEntry()) {
				msg.append("\nHost: " + getScheduler().getFullNameReverse());
			}
			msg.append("\nStart Date: " + (new SimpleDateFormat("EEEEEEEE, MMMMMMMM d, yyyy ")).format(evtDate));

			if (evtEndDate != null) {
				msg.append("\nEnd Date: " + (new SimpleDateFormat("EEEEEEEE, MMMMMMMM d, yyyy ")).format(evtEndDate));
			}
			if (!starttime.equals("UNKNOWN")) {
				msg.append("\nStart Time: " + starttime + "\nFinish Time: " + finishtime);
			}
		}
		catch (EventException e) {
			System.err.println("Event.toString(): " + e);
		}

		return msg.toString();
	}

	/*
	 * If event is a multiday event, this method will set the num_days instance
	 * variable.
	 */
	private void calcEventDays() {

		Calendar sd = null;
		Calendar ed = null;
		int cnt = 0;

		if (this.isMultiDayEvent()) {
			sd = Calendar.getInstance();
			sd.setTime(this.evtDate);

			ed = Calendar.getInstance();
			ed.setTime(this.evtEndDate);

			while (!sd.after(ed)) {
				cnt++;
				sd.add(Calendar.DATE, 1);
			}
			this.numdays = cnt;
		}
		else {
			numdays = 1;
		}
	}

}