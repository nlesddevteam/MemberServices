package com.awsd.pdreg.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.pdreg.Event;
import com.awsd.pdreg.EventException;
import com.awsd.pdreg.EventRequirement;
import com.esdnl.dao.DAOUtils;

public class EventRequirementManager {

	public static void addEventRequirement(Event event, EventRequirement sbean) throws EventException {

		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.pdreg_sys.add_evtreq(?,?,?); end;");
			stat.setInt(1, event.getEventID());
			stat.setString(2, sbean.getRequirement());
			stat.setString(3, sbean.getExtrainfo());

			stat.execute();

			con.commit();
		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("addEventRequirement(Event event, EventRequirement sbean): " + e);
			e.printStackTrace();

			throw new EventException("Can not add EventRequirement to DB.");
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

	}

	public static void deleteEventRequirements(Event event) throws EventException {

		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.pdreg_sys.del_evtreqs(?); end;");
			stat.setInt(1, event.getEventID());

			stat.execute();

			con.commit();
		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("deleteEventRequirements(Event event): " + e);
			e.printStackTrace();

			throw new EventException("Can not delete EventRequirements to DB.");
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

	}

	public static ArrayList<EventRequirement> getEventRequirements(Event evt) throws EventException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<EventRequirement> evts = null;

		try {
			evts = new ArrayList<EventRequirement>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.pdreg_sys.get_evtregs(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, evt.getEventID());

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				evts.add(createEventRequirement(rs));
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

	public static EventRequirement createEventRequirement(ResultSet rs) {

		EventRequirement sBean = null;

		try {
			sBean = new EventRequirement();

			sBean.setEventId(rs.getInt("EVENT_ID"));
			sBean.setRequirement(rs.getString("REQUIREMENT"));
			sBean.setExtrainfo(rs.getString("EXTRAINFO"));
		}
		catch (SQLException e) {
			e.printStackTrace();
			sBean = null;
		}

		return sBean;
	}

}
