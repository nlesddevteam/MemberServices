package com.awsd.pdreg;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Vector;

import com.esdnl.dao.DAOUtils;

public class EventTypeDB {

	/*
	 * increases event type lookup performance. Event types change very rarely.
	 */
	private static HashMap<Integer, EventType> typeMap = null;

	static {
		try {
			loadTypesMap();
		}
		catch (Exception e) {
			System.err.println("Event (static block): " + e);
		}
	}

	public static EventType getEventType(int id) throws EventTypeException {

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		EventType type = null;
		String sql;

		if (typeMap.containsKey(id)) {
			type = typeMap.get(id);
		}
		else {
			try {

				sql = "SELECT * FROM EVENTTYPE WHERE EVENTTYPE_ID=" + id;

				con = DAOUtils.getConnection();
				stat = con.createStatement();
				rs = stat.executeQuery(sql);

				if (rs.next()) {
					type = new EventType(rs.getInt("EVENTTYPE_ID"), rs.getString("EVENTTYPE_NAME"));
				}
				else {
					throw new EventTypeException("EventType does not exist.");
				}
			}
			catch (SQLException e) {
				System.err.println("EventTypeDB.getEventType(): " + e);
				throw new EventTypeException("Could not retrieve eventtype from DB");
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

		return type;
	}

	public static Vector<EventType> getEventTypes() throws EventTypeException {

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		Vector<EventType> types = null;
		String sql;

		try {
			types = new Vector<EventType>(6);
			sql = "SELECT * FROM EVENTTYPE ORDER BY EVENTTYPE_NAME";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				types.add(new EventType(rs.getInt("EVENTTYPE_ID"), rs.getString("EVENTTYPE_NAME")));
			}

		}
		catch (SQLException e) {
			System.err.println("EventTypeDB.getEventTypes(): " + e);
			throw new EventTypeException("Could not retrieve eventtype from DB");
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
		return types;
	}

	/*
	 * UTILITY METHOD TO BOOST PERFORMANCE
	 */
	private static void loadTypesMap() throws EventTypeException {

		typeMap = new HashMap<Integer, EventType>(6);

		for (EventType t : EventTypeDB.getEventTypes()) {
			typeMap.put(new Integer(t.getEventTypeID()), t);
		}
	}
}