package com.awsd.pdreg.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.mail.bean.AlertBean;
import com.awsd.mail.bean.EmailException;
import com.awsd.pdreg.Event;
import com.awsd.pdreg.EventCategory;
import com.awsd.pdreg.EventDB;
import com.awsd.pdreg.EventException;
import com.esdnl.dao.DAOUtils;

public class EventCategoryManager {

	private static final Map<Integer, EventCategory> catMap;

	static {
		catMap = new HashMap<Integer, EventCategory>();

		try {
			for (EventCategory cat : getEventCategorys()) {
				catMap.put(cat.getCategoryId(), cat);
			}
		}
		catch (EventException e) {
			try {
				new AlertBean(e);
			}
			catch (EmailException e1) {}
		}
	}

	public static EventCategory getEventCategory(int id) throws EventException {

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		EventCategory cat = null;
		String sql;

		if (catMap.containsKey(id)) {
			cat = catMap.get(id);
		}
		else {
			try {

				sql = "SELECT * FROM EVENTCATEGORY WHERE CATEGORY_ID=" + id;

				con = DAOUtils.getConnection();
				stat = con.createStatement();
				rs = stat.executeQuery(sql);

				if (rs.next()) {
					cat = createEventCategory(rs);

					catMap.put(cat.getCategoryId(), cat);
				}
				else {
					throw new EventException("EventCategory does not exist.");
				}
			}
			catch (SQLException e) {
				System.err.println("EventCategory getEventCategory(int id): " + e);
				throw new EventException("Could not retrieve eventcategory from DB");
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
		}

		return cat;
	}

	public static void addEventCategory(Event event, EventCategory sbean) throws EventException {

		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.pdreg_sys.add_evtcat(?,?); end;");
			stat.setInt(1, event.getEventID());
			stat.setInt(2, sbean.getCategoryId());

			stat.execute();

			con.commit();
		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("addEventCategory(Event event, EventCategory sbean): " + e);
			e.printStackTrace();

			throw new EventException("Can not add EventCategory to DB.");
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

	public static void deleteEventCategories(Event event) throws EventException {

		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.pdreg_sys.del_evtcats(?); end;");
			stat.setInt(1, event.getEventID());

			stat.execute();

			con.commit();
		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("deleteEventCategories: " + e);
			e.printStackTrace();

			throw new EventException("Can not delete EventCategory to DB.");
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

	public static ArrayList<EventCategory> getEventCategorys() throws EventException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<EventCategory> evts = null;

		try {
			evts = new ArrayList<EventCategory>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.pdreg_sys.get_evtcats; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				evts.add(createEventCategory(rs));
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<EventCategory> getEventCategorys(): " + e);
			throw new EventException("Can not get EventCategory from DB: " + e);
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

	public static ArrayList<EventCategory> getEventCategories(Event evt) throws EventException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<EventCategory> evts = null;

		try {
			evts = new ArrayList<EventCategory>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.pdreg_sys.get_evtcats(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, evt.getEventID());

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				evts.add(createEventCategory(rs));
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<EventCategory> getEventCategorys(Event evt): " + e);
			throw new EventException("Can not get EventCategory from DB: " + e);
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

	public static EventCategory createEventCategory(ResultSet rs) {

		EventCategory sBean = null;

		try {
			sBean = new EventCategory(rs.getInt("CATEGORY_ID"), rs.getString("CATEGORY_NAME"));
		}
		catch (SQLException e) {
			e.printStackTrace();
			sBean = null;
		}

		return sBean;
	}

	public static void main(String args[]) {

		try {
			Event e = EventDB.getEvent(16177);
			EventCategory c = EventCategoryManager.getEventCategory(233);

			EventCategoryManager.addEventCategory(e, c);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
