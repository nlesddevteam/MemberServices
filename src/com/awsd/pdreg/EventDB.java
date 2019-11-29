package com.awsd.pdreg;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.pdreg.dao.EventCategoryManager;
import com.awsd.pdreg.dao.EventRequirementManager;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.PersonnelException;
import com.awsd.school.School;
import com.awsd.security.User;
import com.esdnl.dao.DAOUtils;
import com.nlesd.school.bean.SchoolZoneBean;

public class EventDB {

	public static Event getEvent(int id) throws EventException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		Event evt = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.pdreg_sys.get_event(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				evt = EventDB.createEvent(rs, true);
			}
			else {
				throw new EventException("Event does not exist.");
			}
		}
		catch (SQLException e) {
			System.err.println("EventDB.getEvent(int): " + e);
			throw new EventException("Could not retrieve event from DB");
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return evt;
	}

	/*
	 * Prepared Statement version..
	 */
	public static int addEvent(Event evt) throws EventException {

		Connection con = null;
		PreparedStatement stat = null;
		Statement stat2 = null;
		SimpleDateFormat sdf = null;
		String sql;
		int check = 0;

		try {
			sdf = new SimpleDateFormat("dd-MMM-yy");

			sql = "INSERT INTO EVENT VALUES(EVENT_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			con = DAOUtils.getConnection();
			con.setAutoCommit(false);
			stat = con.prepareStatement(sql);

			stat.setString(1, evt.getEventName());
			stat.setString(2, evt.getEventDescription());
			stat.setString(3, sdf.format(evt.getEventDate()));
			stat.setString(4, evt.getEventLocation());
			stat.setInt(5, evt.getSchedulerID());
			stat.setString(6, evt.getEventStartTime());
			stat.setString(7, evt.getEventFinishTime());
			stat.setInt(8, evt.getEventMaximumParticipants());
			stat.setInt(9, evt.getEventType().getEventTypeID());
			stat.setString(10, ((evt.getEventEndDate() != null) ? sdf.format(evt.getEventEndDate()) : null));
			stat.setString(11, evt.getEventCloseoutOption());
			stat.setInt(12, evt.getEventSchoolID());
			stat.setInt(13, evt.getEventSchoolZoneID());
			stat.setBoolean(14, evt.isGovernmentFunded());

			check = stat.executeUpdate();
			stat.close();
			if (check > 0) {
				stat2 = con.createStatement();
				ResultSet rs = stat2.executeQuery("SELECT EVENT_SEQ.CURRVAL e_id FROM DUAL");
				if (rs.next()) {
					check = rs.getInt("e_id");

					evt.setEventID(check);

					if (evt.hasEventCategories()) {
						for (EventCategory cat : evt.getEventCategories()) {
							EventCategoryManager.addEventCategory(evt, cat);
						}
					}

					if (evt.hasEventRequirements()) {
						for (EventRequirement req : evt.getEventRequirements()) {
							EventRequirementManager.addEventRequirement(evt, req);
						}
					}

				}
				else {
					check = -1;
				}
				stat2.close();
			}
			con.commit();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("EventDB.addEvent(): " + e);
			try {
				con.rollback();
			}
			catch (SQLException ex) {}
			throw new EventException(evt.getEventDate(), "Could not added event to DB");
		}
		finally {
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return (check);
	}

	/*
	 * Prepared statement version...
	 */
	public static boolean updateEvent(Event evt) throws EventException {

		Connection con = null;
		PreparedStatement stat = null;
		SimpleDateFormat sdf = null;
		String sql;
		int check = 0;

		try {
			sdf = new SimpleDateFormat("dd-MMM-yy");

			sql = "UPDATE EVENT SET EVENT_NAME=?, EVENT_DESC=?, EVENT_DATE=?, EVENT_ENDDATE=?, "
					+ "EVENT_LOCATION=?, EVENT_SCHOOL_ID=?, EVENT_ZONE_ID=?, SCHEDULER_ID=?, "
					+ "EVENT_STARTTIME=?, EVENT_FINISHTIME=?, EVENT_MAX=?, EVENT_CLOSEOUT_OPTION=?, "
					+ "EVENTTYPE_ID=?, GOV_FUNDED=? WHERE EVENT_ID=?";

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareStatement(sql);

			stat.setString(1, evt.getEventName());
			stat.setString(2, evt.getEventDescription());
			stat.setString(3, sdf.format(evt.getEventDate()));
			stat.setString(4, ((evt.getEventEndDate() != null) ? sdf.format(evt.getEventEndDate()) : null));
			stat.setString(5, evt.getEventLocation());
			stat.setInt(6, evt.getEventSchoolID());
			stat.setInt(7, evt.getEventSchoolZoneID());
			stat.setInt(8, evt.getSchedulerID());
			stat.setString(9, evt.getEventStartTime());
			stat.setString(10, evt.getEventFinishTime());
			stat.setInt(11, evt.getEventMaximumParticipants());
			stat.setString(12, evt.getEventCloseoutOption());
			stat.setInt(13, evt.getEventType().getEventTypeID());
			stat.setBoolean(14, evt.isGovernmentFunded());
			stat.setInt(15, evt.getEventID());

			check = stat.executeUpdate();

			if (check > 0) {

				EventRequirementManager.deleteEventRequirements(evt);
				if (evt.hasEventRequirements()) {
					for (EventRequirement req : evt.getEventRequirements()) {
						EventRequirementManager.addEventRequirement(evt, req);
					}
				}

				EventCategoryManager.deleteEventCategories(evt);
				if (evt.hasEventCategories()) {
					for (EventCategory cat : evt.getEventCategories()) {
						EventCategoryManager.addEventCategory(evt, cat);
					}
				}

			}
		}
		catch (SQLException e) {
			System.err.println("EventDB.updateEvent(): " + e);
			throw new EventException(evt.getEventDate(), "Could not update event in DB");
		}
		finally {
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return (check == 1);
	}

	public static boolean removeEvent(Event evt) throws EventException {

		Connection con = null;
		Statement stat = null;
		String sql;
		int check = 0;

		try {
			sql = "DELETE FROM EVENT WHERE EVENT_ID=" + evt.getEventID();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.createStatement();
			check = stat.executeUpdate(sql);
		}
		catch (SQLException e) {
			System.err.println("EventDB.removeEvent(): " + e);
			throw new EventException(evt.getEventDate(), "Could not remove event from DB.");
		}
		finally {
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return (check == 1);
	}

	public static Vector<Event> getDailyEvents(String dStr) throws EventException {

		Vector<Event> evts = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		SimpleDateFormat sdf = null;
		String sql;
		Date d = null;

		try {
			evts = new Vector<Event>(5);

			d = (new SimpleDateFormat("yyyyMMdd")).parse(dStr);

			sdf = new SimpleDateFormat("dd-MMM-yy");

			sql = "SELECT EVENT.EVENT_ID, EVENTTYPE_ID, EVENT_NAME, EVENT_DESC, EVENT_DATE, EVENT_ENDDATE, "
					+ "EVENT_LOCATION, EVENT_SCHOOL_ID, EVENT_ZONE_ID, SCHEDULER_ID, nvl(EVENT_STARTTIME, 'UNKNOWN') EVENT_STARTTIME, "
					+ "nvl(EVENT_FINISHTIME, 'UNKNOWN') EVENT_FINISHTIME, "
					+ "nvl(EVENT_MAX, 0) EVENT_MAX, nvl(EVENT_CLOSEOUT_OPTION,'ZZ') EVENT_CLOSEOUT_OPTION, GOV_FUNDED,nvl(ecount.pcount,0) PARTICIPANT_CNT "
					+ "FROM EVENT left outer join (select count(*) pcount,event_id from EVENTPERSONNEL group by EVENT_ID) ecount on EVENT.EVENT_ID=ecount.event_id "
					+ "WHERE EVENT_DATE='" + sdf.format(d) + "' OR ((EVENT_DATE <= '" + sdf.format(d) + "') AND ('"
					+ sdf.format(d) + "' <= EVENT_ENDDATE ))" + " ORDER BY EVENT_ID";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				evts.add(EventDB.createEvent(rs, false));
			}
		}
		catch (SQLException e) {
			System.err.println("EventDB.getDailyEvents(): " + e);
			throw new EventException("Can not extract daily events from DB");
		}
		catch (ParseException e) {
			System.err.println("EventDB.getDailyEvents(): " + e);
			throw new EventException(d, "invalid date format.");
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return evts;
	}

	public static Vector<Event> getDailyEvents(String dStr, SchoolZoneBean zone) throws EventException {

		Vector<Event> evts = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		SimpleDateFormat sdf = null;
		String sql;
		Date d = null;

		try {
			evts = new Vector<Event>(5);

			d = (new SimpleDateFormat("yyyyMMdd")).parse(dStr);

			sdf = new SimpleDateFormat("dd-MMM-yy");

			sql = "SELECT EVENT.EVENT_ID, EVENTTYPE_ID, EVENT_NAME, EVENT_DESC, EVENT_DATE, EVENT_ENDDATE, "
					+ "EVENT_LOCATION, EVENT_SCHOOL_ID, EVENT_ZONE_ID, SCHEDULER_ID, nvl(EVENT_STARTTIME, 'UNKNOWN') EVENT_STARTTIME, "
					+ "nvl(EVENT_FINISHTIME, 'UNKNOWN') EVENT_FINISHTIME, "
					+ "nvl(EVENT_MAX, 0) EVENT_MAX, nvl(EVENT_CLOSEOUT_OPTION,'ZZ') EVENT_CLOSEOUT_OPTION, GOV_FUNDED,nvl(ecount.pcount,0) PARTICIPANT_CNT "
					+ "FROM EVENT left outer join (select count(*) pcount,event_id from EVENTPERSONNEL group by EVENT_ID) ecount on EVENT.EVENT_ID=ecount.event_id "
					+ "WHERE (EVENT_DATE='" + sdf.format(d) + "' OR ((EVENT_DATE <= '" + sdf.format(d) + "') AND ('"
					+ sdf.format(d) + "' <= EVENT_ENDDATE ))) AND EVENT_ZONE_ID=" + zone.getZoneId() + " ORDER BY EVENT_ID";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				evts.add(EventDB.createEvent(rs, false));
			}
		}
		catch (SQLException e) {
			System.err.println("EventDB.getDailyEvents(): " + e);
			throw new EventException("Can not extract daily events from DB");
		}
		catch (ParseException e) {
			System.err.println("EventDB.getDailyEvents(): " + e);
			throw new EventException(d, "invalid date format.");
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return evts;
	}

	public static boolean registerEvent(User usr, Event evt) throws EventException {

		Connection con = null;
		Statement stat = null;
		String sql;
		int check = 0;

		try {
			sql = "INSERT INTO EVENTPERSONNEL VALUES(" + evt.getEventID() + ", " + usr.getPersonnel().getPersonnelID()
					+ ", 0)";

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.createStatement();
			check = stat.executeUpdate(sql);
		}
		catch (SQLException e) {
			System.err.println("EventDB.RegisterEvent(): " + e);
			throw new EventException(evt.getEventDate(), "Could not register event.");
		}
		finally {
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return (check == 1);
	}

	public static boolean addAttendee(Personnel p, Event evt) throws EventException {

		Connection con = null;
		Statement stat = null;
		String sql;
		int check = 0;

		try {
			sql = "INSERT INTO EVENTPERSONNEL VALUES(" + evt.getEventID() + ", " + p.getPersonnelID() + ", 1)";

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.createStatement();
			check = stat.executeUpdate(sql);
		}
		catch (SQLException e) {
			System.err.println("EventDB.addAttendee(): " + e);
			throw new EventException(evt.getEventDate(), "Could not add attendee event.");
		}
		finally {
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return (check == 1);
	}

	public static boolean updateAttendance(Personnel p, Event evt, boolean attended) throws EventException {

		Connection con = null;
		PreparedStatement stat = null;
		String sql;
		int check = 0;

		try {
			sql = "UPDATE EVENTPERSONNEL SET ATTENDED = ? WHERE EVENT_ID = ? AND PERSONNEL_ID = ?";

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareStatement(sql);

			stat.setBoolean(1, attended);
			stat.setInt(2, evt.getEventID());
			stat.setInt(3, p.getPersonnelID());

			check = stat.executeUpdate();
		}
		catch (SQLException e) {
			System.err.println("updateAttendance(Personnel p, Event evt, boolean attended: " + e);
			throw new EventException(evt.getEventDate(), "Could not update attendee event.");
		}
		finally {
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return (check == 1);
	}

	public static Map<Integer, Event> getUserRegisteredEvents(Personnel p) throws EventException {

		Map<Integer, Event> evts = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			evts = new HashMap<Integer, Event>(10);

			sql = "SELECT EVENT.EVENT_ID, EVENTTYPE_ID, EVENT_NAME, EVENT_DESC, EVENT_DATE, EVENT_ENDDATE, "
					+ "EVENT_LOCATION, EVENT_SCHOOL_ID, EVENT_ZONE_ID, SCHEDULER_ID, nvl(EVENT_STARTTIME, 'UNKNOWN') EVENT_STARTTIME, "
					+ "nvl(EVENT_FINISHTIME, 'UNKNOWN') EVENT_FINISHTIME, "
					+ "nvl(EVENT_MAX, 0) EVENT_MAX, nvl(EVENT_CLOSEOUT_OPTION, 'ZZ') EVENT_CLOSEOUT_OPTION, GOV_FUNDED FROM EVENT, EVENTPERSONNEL "
					+ "WHERE EVENT.EVENT_ID = EVENTPERSONNEL.EVENT_ID " + "AND EVENT.EVENT_DATE >= SYSDATE "
					+ "AND EVENTPERSONNEL.PERSONNEL_ID = " + p.getPersonnelID();

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				evts.put(new Integer(rs.getInt("EVENT_ID")), EventDB.createEvent(rs, false));
			}
		}
		catch (SQLException e) {
			System.err.println("EventDB.getUserRegisteredEvents(): " + e);
			throw new EventException("Can not extract registered events from DB");
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return evts;
	}

	public static Vector<Event> getPersonnelScheduledEvents(Personnel p) throws EventException {

		Vector<Event> evts = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			evts = new Vector<Event>(10, 10);

			sql = "SELECT EVENT.EVENT_ID, EVENTTYPE_ID, EVENT_NAME, EVENT_DESC, EVENT_DATE, EVENT_ENDDATE, "
					+ "EVENT_LOCATION, EVENT_SCHOOL_ID, EVENT_ZONE_ID, SCHEDULER_ID, nvl(EVENT_STARTTIME, 'UNKNOWN') EVENT_STARTTIME, "
					+ "nvl(EVENT_FINISHTIME, 'UNKNOWN') EVENT_FINISHTIME, "
					+ "nvl(EVENT_MAX, 0) EVENT_MAX, nvl(EVENT_CLOSEOUT_OPTION, 'ZZ') EVENT_CLOSEOUT_OPTION, GOV_FUNDED FROM EVENT "
					+ "WHERE EVENT.SCHEDULER_ID = " + p.getPersonnelID() + " ORDER BY EVENT_DATE";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				evts.add(EventDB.createEvent(rs, false));
			}
		}
		catch (SQLException e) {
			System.err.println("EventDB.getUserScheduleEvents(): " + e);
			throw new EventException("Can not extract registered events from DB");
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return evts;
	}

	public static Vector<Event> getDistrictCalendarScheduledEvents() throws EventException {

		Vector<Event> evts = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			evts = new Vector<Event>(10, 10);

			sql = "SELECT EVENT.EVENT_ID, EVENTTYPE_ID, EVENT_NAME, EVENT_DESC, EVENT_DATE, EVENT_ENDDATE, "
					+ "EVENT_LOCATION, EVENT_SCHOOL_ID, EVENT_ZONE_ID, SCHEDULER_ID, nvl(EVENT_STARTTIME, 'UNKNOWN') EVENT_STARTTIME, "
					+ "nvl(EVENT_FINISHTIME, 'UNKNOWN') EVENT_FINISHTIME, "
					+ "nvl(EVENT_MAX, 0) EVENT_MAX, nvl(EVENT_CLOSEOUT_OPTION, 'ZZ') EVENT_CLOSEOUT_OPTION, GOV_FUNDED FROM EVENT "
					+ "WHERE EVENTTYPE_ID IN (2, 3, 23, 24) ORDER BY EVENT_DATE";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				evts.add(EventDB.createEvent(rs, false));
			}
		}
		catch (SQLException e) {
			System.err.println("EventDB.getDistrictCalendarScheduleEvents(): " + e);
			throw new EventException("Can not extract registered events from DB");
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return evts;
	}

	public static Vector<Event> getDistrictCalendarCloseoutScheduledEvents() throws EventException {

		Vector<Event> evts = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			evts = new Vector<Event>(10, 10);

			sql = "SELECT EVENT.EVENT_ID, EVENTTYPE_ID, EVENT_NAME, EVENT_DESC, EVENT_DATE, EVENT_ENDDATE, "
					+ "EVENT_LOCATION, EVENT_SCHOOL_ID, EVENT_ZONE_ID, SCHEDULER_ID, nvl(EVENT_STARTTIME, 'UNKNOWN') EVENT_STARTTIME, "
					+ "nvl(EVENT_FINISHTIME, 'UNKNOWN') EVENT_FINISHTIME, "
					+ "nvl(EVENT_MAX, 0) EVENT_MAX, nvl(EVENT_CLOSEOUT_OPTION, 'ZZ') EVENT_CLOSEOUT_OPTION, GOV_FUNDED FROM EVENT "
					+ "WHERE (EVENTTYPE_ID = 3) ORDER BY EVENT_DATE";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				evts.add(EventDB.createEvent(rs, false));
			}
		}
		catch (SQLException e) {
			System.err.println("EventDB.getDistrictCalendarScheduleEvents(): " + e);
			throw new EventException("Can not extract registered events from DB");
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return evts;
	}

	public static Map<Integer, Personnel> getEventRegisteredPersonnel(Event evt) throws EventException {

		Map<Integer, Personnel> people = null;
		Personnel p = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			people = new HashMap<Integer, Personnel>(10);

			sql = "SELECT * FROM PERSONNEL, EVENTPERSONNEL WHERE PERSONNEL.PERSONNEL_ID = EVENTPERSONNEL.PERSONNEL_ID "
					+ "AND EVENTPERSONNEL.EVENT_ID = " + evt.getEventID();

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				try {
					p = new Personnel(rs.getInt("PERSONNEL_ID"), rs.getString("PERSONNEL_USERNAME"), rs.getString("PERSONNEL_PASSWORD"), rs.getString("PERSONNEL_FIRSTNAME"), rs.getString("PERSONNEL_LASTNAME"), rs.getString("PERSONNEL_EMAIL"), rs.getInt("PERSONNEL_CATEGORYID"), rs.getInt("PERSONNEL_SUPERVISOR_ID"));
				}
				catch (PersonnelException e) {
					throw new EventException("Could not find personnel");
				}

				people.put(new Integer(rs.getInt("PERSONNEL_ID")), p);
			}
		}
		catch (SQLException e) {
			System.err.println("EventDB.getUserRegisteredEvents(): " + e);
			throw new EventException("Can not extract registered events from DB");
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return people;
	}

	public static Vector<Personnel> getEventRegisteredPersonnelSortedBySchool(Event evt) throws EventException {

		Vector<Personnel> people = null;
		Personnel p = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			people = new Vector<Personnel>(20, 20);

			sql = "SELECT * FROM PERSONNEL, EVENTPERSONNEL WHERE PERSONNEL.PERSONNEL_ID = EVENTPERSONNEL.PERSONNEL_ID "
					+ "AND EVENTPERSONNEL.EVENT_ID = " + evt.getEventID()
					+ " ORDER BY SCHOOL_ID, PERSONNEL_LASTNAME, PERSONNEL_FIRSTNAME";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				try {
					p = new Personnel(rs.getInt("PERSONNEL_ID"), rs.getString("PERSONNEL_USERNAME"), rs.getString("PERSONNEL_PASSWORD"), rs.getString("PERSONNEL_FIRSTNAME"), rs.getString("PERSONNEL_LASTNAME"), rs.getString("PERSONNEL_EMAIL"), rs.getInt("PERSONNEL_CATEGORYID"), rs.getInt("PERSONNEL_SUPERVISOR_ID"), rs.getInt("SCHOOL_ID"));

					people.add(p);
				}
				catch (PersonnelException e) {
					throw new EventException("Could not find personnel");
				}
			}
		}
		catch (SQLException e) {
			System.err.println("EventDB.getUserRegisteredEvents(): " + e);
			throw new EventException("Can not extract registered events from DB");
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return people;
	}

	public static Vector<EventAttendee> getEventAttendeesSortedBySchool(Event evt) throws EventException {

		Vector<EventAttendee> attendees = null;
		Personnel p = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			attendees = new Vector<EventAttendee>(20, 10);

			sql = "SELECT * FROM PERSONNEL, EVENTPERSONNEL WHERE PERSONNEL.PERSONNEL_ID = EVENTPERSONNEL.PERSONNEL_ID "
					+ "AND EVENTPERSONNEL.EVENT_ID = " + evt.getEventID()
					+ " ORDER BY SCHOOL_ID, PERSONNEL_LASTNAME, PERSONNEL_FIRSTNAME";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				try {
					p = new Personnel(rs.getInt("PERSONNEL_ID"), rs.getString("PERSONNEL_USERNAME"), rs.getString("PERSONNEL_PASSWORD"), rs.getString("PERSONNEL_FIRSTNAME"), rs.getString("PERSONNEL_LASTNAME"), rs.getString("PERSONNEL_EMAIL"), rs.getInt("PERSONNEL_CATEGORYID"), rs.getInt("PERSONNEL_SUPERVISOR_ID"), rs.getInt("SCHOOL_ID"));

					attendees.add(new EventAttendee(evt, p, rs.getBoolean("attended")));
				}
				catch (PersonnelException e) {
					throw new EventException("Could not find personnel");
				}
			}
		}
		catch (SQLException e) {
			System.err.println("EventDB.getEventAttendeesSortedBySchool(): " + e);
			throw new EventException("Can not extract registered personnel from DB");
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return attendees;
	}

	public static Vector<EventAttendee> getEventAttendeesSortedBySchool(Event evt, School s) throws EventException {

		Vector<EventAttendee> attendees = null;
		Personnel p = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			attendees = new Vector<EventAttendee>(20, 10);

			sql = "SELECT * FROM PERSONNEL WHERE SCHOOL_ID = " + s.getSchoolID()
					+ " ORDER BY PERSONNEL_LASTNAME, PERSONNEL_FIRSTNAME";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				try {
					p = new Personnel(rs.getInt("PERSONNEL_ID"), rs.getString("PERSONNEL_USERNAME"), rs.getString("PERSONNEL_PASSWORD"), rs.getString("PERSONNEL_FIRSTNAME"), rs.getString("PERSONNEL_LASTNAME"), rs.getString("PERSONNEL_EMAIL"), rs.getInt("PERSONNEL_CATEGORYID"), rs.getInt("PERSONNEL_SUPERVISOR_ID"), rs.getInt("SCHOOL_ID"));

					attendees.add(new EventAttendee(evt, p, false));
				}
				catch (PersonnelException e) {
					throw new EventException("Could not find personnel");
				}
			}
		}
		catch (SQLException e) {
			System.err.println("EventDB.getEventAttendeesSortedBySchool(): " + e);
			throw new EventException("Can not extract registered personnel from DB");
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return attendees;
	}

	public static Map<Integer, EventAttendee> getEventAttendees(Event evt) throws EventException {

		Map<Integer, EventAttendee> attendees = null;
		Personnel p = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			attendees = new HashMap<Integer, EventAttendee>();

			sql = "SELECT * FROM PERSONNEL, EVENTPERSONNEL WHERE PERSONNEL.PERSONNEL_ID = EVENTPERSONNEL.PERSONNEL_ID "
					+ "AND EVENTPERSONNEL.EVENT_ID = " + evt.getEventID()
					+ " ORDER BY SCHOOL_ID, PERSONNEL_LASTNAME, PERSONNEL_FIRSTNAME";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				try {
					p = new Personnel(rs.getInt("PERSONNEL_ID"), rs.getString("PERSONNEL_USERNAME"), rs.getString("PERSONNEL_PASSWORD"), rs.getString("PERSONNEL_FIRSTNAME"), rs.getString("PERSONNEL_LASTNAME"), rs.getString("PERSONNEL_EMAIL"), rs.getInt("PERSONNEL_CATEGORYID"), rs.getInt("PERSONNEL_SUPERVISOR_ID"), rs.getInt("SCHOOL_ID"));

					attendees.put(p.getPersonnelID(), new EventAttendee(evt, p, rs.getBoolean("attended")));
				}
				catch (PersonnelException e) {
					throw new EventException("Could not find personnel");
				}
			}
		}
		catch (SQLException e) {
			System.err.println("EventDB.getEventAttendeesSortedBySchool(): " + e);
			throw new EventException("Can not extract registered personnel from DB");
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return attendees;
	}

	public static Map<Integer, String> getCalendarLegend() throws EventException {

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		Map<Integer, String> legend = null;
		String sql;

		try {
			legend = new HashMap<Integer, String>(15);

			sql = "SELECT * FROM LEGEND";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				legend.put(new Integer(rs.getInt("PERSONNEL_ID")), rs.getString("COLOR"));
			}
		}
		catch (SQLException e) {
			System.err.println("EventDB.getCalendarLegend(): " + e);
			e.printStackTrace(System.err);
			throw new EventException("Could not retrieve calendar legend from DB");
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return legend;
	}

	public static boolean deregisterEvent(Event evt, Personnel p) throws EventException {

		Connection con = null;
		Statement stat = null;
		String sql;
		int check = 0;

		try {
			sql = "DELETE FROM EVENTPERSONNEL WHERE EVENT_ID=" + evt.getEventID() + " AND PERSONNEL_ID=" + p.getPersonnelID();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.createStatement();
			check = stat.executeUpdate(sql);
		}
		catch (SQLException e) {
			System.err.println("EventDB.deregisterEvent(): " + e);
			throw new EventException(evt.getEventDate(), "Could not remove registration from DB.");
		}
		finally {
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return (check == 1);
	}

	public static int participantCount(Event evt) throws EventException {

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;
		int cnt = 0;

		try {
			sql = "SELECT COUNT(PERSONNEL_ID) CNTPART FROM EVENTPERSONNEL WHERE EVENT_ID=" + evt.getEventID();

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			if (rs.next()) {
				cnt = rs.getInt("CNTPART");
			}
			else {
				cnt = 0;
			}
		}
		catch (SQLException e) {
			System.err.println("EventDB.participantCount(): " + e);
			throw new EventException(evt.getEventDate(), "Could not count participants.");
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return cnt;
	}

	public static Vector<Event> getCloseOutEvents(Event closeout, String option) throws EventException {

		Vector<Event> evts = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			evts = new Vector<Event>(10, 10);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.pdreg_sys.get_closeout_events(?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.setDate(2, new java.sql.Date(closeout.getEventDate().getTime()));
			if (closeout.getEventEndDate() != null)
				stat.setDate(3, new java.sql.Date(closeout.getEventEndDate().getTime()));
			else
				stat.setNull(3, OracleTypes.DATE);
			stat.setString(4, option);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				evts.add(EventDB.createEvent(rs, false));
			}
		}
		catch (SQLException e) {
			System.err.println("EventDB.getCloseoutEvents(): " + e);
			throw new EventException("Can not extract registered events from DB");
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return evts;
	}

	public static Event getCloseOutEvent(Event evt) throws EventException {

		Event closeout = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;
		SimpleDateFormat sdf = null;

		try {
			sdf = new SimpleDateFormat("dd-MMM-yy");

			sql = "SELECT EVENT.EVENT_ID, EVENTTYPE_ID, EVENT_NAME, EVENT_DESC, EVENT_DATE, EVENT_ENDDATE, "
					+ "EVENT_LOCATION, EVENT_SCHOOL_ID, EVENT_ZONE_ID, SCHEDULER_ID, nvl(EVENT_STARTTIME, 'UNKNOWN') EVENT_STARTTIME, "
					+ "nvl(EVENT_FINISHTIME, 'UNKNOWN') EVENT_FINISHTIME, "
					+ "nvl(EVENT_MAX, 0) EVENT_MAX, nvl(EVENT_CLOSEOUT_OPTION, 'ZZ') EVENT_CLOSEOUT_OPTION, GOV_FUNDED FROM EVENT "
					+ "WHERE EVENTTYPE_ID=3";

			if (evt.getEventEndDate() != null) {
				sql += " AND (EVENT_DATE <= '" + sdf.format(evt.getEventDate()) + "')" + " AND (EVENT_ENDDATE >= '"
						+ sdf.format(evt.getEventEndDate()) + "')";
			}
			else {
				sql += " AND (EVENT_DATE <= '" + sdf.format(evt.getEventDate()) + "')" + " AND (EVENT_ENDDATE >= '"
						+ sdf.format(evt.getEventDate()) + "')";
			}

			sql += " ORDER BY EVENT_DATE";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			if (rs.next()) {
				closeout = EventDB.createEvent(rs, false);
			}
			else {
				closeout = null;
			}

		}
		catch (SQLException e) {
			System.err.println("EventDB.getUserScheduleEvents(): " + e);
			throw new EventException("Can not extract registered events from DB");
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return closeout;
	}

	/*******************************************
	 * USES STORED PROCEDURES
	 * *****************************************/

	public static HashMap<String, DailyCalendar> getMonthlyEvents(Calendar original) throws EventException {

		Event evt = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		SimpleDateFormat sdf = null;
		HashMap<String, DailyCalendar> days = null;
		Calendar cal = null;
		DailyCalendar day = null;

		try {
			sdf = new SimpleDateFormat("yyyyMMdd");

			days = new HashMap<String, DailyCalendar>(31);

			cal = (Calendar) original.clone();

			while (cal.get(Calendar.MONTH) == original.get(Calendar.MONTH)) {
				days.put(sdf.format(cal.getTime()), new DailyCalendar(sdf.format(cal.getTime()), null, false));
				cal.add(Calendar.DATE, 1);
			}

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.pdreg_sys.get_monthly_events(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setDate(2, new java.sql.Date(original.getTime().getTime()));
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				evt = createEvent(rs, false);

				if (evt.getEventDate().before(original.getTime()))
					cal = (Calendar) original.clone();
				else
					cal.setTime(evt.getEventDate());

				while (!cal.getTime().after((evt.getEventEndDate() != null) ? evt.getEventEndDate() : evt.getEventDate())) {
					day = (DailyCalendar) days.get(sdf.format(cal.getTime()));
					if (day == null) {
						day = new DailyCalendar(sdf.format(cal.getTime()), null, false);
						days.put(sdf.format(cal.getTime()), day);
					}
					day.add(evt);
					cal.add(Calendar.DATE, 1);
				}
			}
		}
		catch (SQLException e) {
			System.err.println("EventDB.getMonthlyEvents(): " + e);
			throw new EventException("Can not extract daily events from DB");
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return days;
	}

	public static HashMap<String, DailyCalendar> getMonthlyEvents(Calendar original, SchoolZoneBean zone)
			throws EventException {

		Event evt = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		SimpleDateFormat sdf = null;
		HashMap<String, DailyCalendar> days = null;
		Calendar cal = null;
		DailyCalendar day = null;

		try {
			sdf = new SimpleDateFormat("yyyyMMdd");

			days = new HashMap<String, DailyCalendar>(31);

			cal = (Calendar) original.clone();

			while (cal.get(Calendar.MONTH) == original.get(Calendar.MONTH)) {
				days.put(sdf.format(cal.getTime()), new DailyCalendar(sdf.format(cal.getTime()), zone, false));
				cal.add(Calendar.DATE, 1);
			}

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.pdreg_sys.get_monthly_events(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setDate(2, new java.sql.Date(original.getTime().getTime()));
			stat.setInt(3, zone.getZoneId());
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				evt = createEvent(rs, false);

				if (evt.getEventDate().before(original.getTime()))
					cal = (Calendar) original.clone();
				else
					cal.setTime(evt.getEventDate());

				while (!cal.getTime().after((evt.getEventEndDate() != null) ? evt.getEventEndDate() : evt.getEventDate())) {
					day = (DailyCalendar) days.get(sdf.format(cal.getTime()));
					if (day == null) {
						day = new DailyCalendar(sdf.format(cal.getTime()), zone, false);
						days.put(sdf.format(cal.getTime()), day);
					}
					day.add(evt);
					cal.add(Calendar.DATE, 1);
				}
			}
		}
		catch (SQLException e) {
			System.err.println("EventDB.getMonthlyEvents(): " + e);
			throw new EventException("Can not extract daily events from DB");
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return days;
	}

	public static boolean setEventType(int event_id, int type_id) throws EventException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.pdreg_sys.set_event_type(?, ?); end;");
			stat.setInt(1, event_id);
			stat.setInt(2, type_id);
			stat.execute();
			check = true;
		}
		catch (SQLException e) {
			System.err.println("EventDB.setEventType(): " + e);
			throw new EventException("Can not update event type to DB: " + e);
		}
		finally {
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return (check);
	}

	public static Vector<Event> getPDEventsNotClaimed(Personnel p) throws EventException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		Vector<Event> evts = null;

		try {
			evts = new Vector<Event>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.pdreg_sys.past_reg_school_pd_evts(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, p.getPersonnelID());

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				evts.add(EventDB.createEvent(rs, false));
			}
		}
		catch (SQLException e) {
			System.err.println("EventDB.getPastRegisteredSchoolPDEvents(Personnel): " + e);
			throw new EventException("Can not get events DB: " + e);
		}
		finally {
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return (evts);
	}

	public static Event createEvent(ResultSet rs, boolean loadExtraInfo) {

		Event abean = null;

		try {
			abean = new Event(rs.getInt("EVENT_ID"), rs.getInt("EVENTTYPE_ID"), rs.getString("EVENT_NAME"), rs.getString("EVENT_DESC"), rs.getDate("EVENT_DATE"), rs.getDate("EVENT_ENDDATE"), rs.getString("EVENT_LOCATION"), rs.getInt("EVENT_SCHOOL_ID"), rs.getInt("EVENT_ZONE_ID"), rs.getInt("SCHEDULER_ID"), rs.getString("EVENT_STARTTIME"), rs.getString("EVENT_FINISHTIME"), rs.getInt("EVENT_MAX"), rs.getString("EVENT_CLOSEOUT_OPTION"), rs.getBoolean("GOV_FUNDED"));

			//column may not be available.
			try {
				abean.setRegistrationCount(rs.getInt("PARTICIPANT_CNT"));
			}
			catch (SQLException e) {}

			try {
				if (rs.getInt("PERSONNEL_ID") > 0) {
					abean.setScheduler(PersonnelDB.createPersonnelBean(rs));
				}
			}
			catch (SQLException e) {
				//scheduler details not available.
			}

			if (loadExtraInfo) {
				abean.addEventRequirements(EventRequirementManager.getEventRequirements(abean));
				abean.addEventCategories(EventCategoryManager.getEventCategories(abean));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			abean = null;
		}
		catch (Exception e) {
			e.printStackTrace();
			abean = null;
		}

		return abean;
	}
}