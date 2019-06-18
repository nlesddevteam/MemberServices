package com.awsd.weather;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import com.awsd.school.School;
import com.esdnl.dao.DAOUtils;
import com.nlesd.schoolstatus.bean.SchoolStatusGlobalConfigBean;
import com.nlesd.schoolstatus.service.SchoolStatusGlobalConfigService;

public class ClosureStatusDB {

	private static Map<Integer, ClosureStatus> statuses = null;

	static {
		try {
			statuses = getClosureStatusesMap();
		}
		catch (Exception e) {
			statuses = null;
		}
	}

	public static Vector<ClosureStatus> getClosureStatuses() throws ClosureStatusException {

		Vector<ClosureStatus> statuses = null;
		ClosureStatus status = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			statuses = new Vector<ClosureStatus>(10);

			sql = "SELECT * FROM SCHOOL_SYSTEM_WEATHER_STATUS ORDER BY STATUS_DESC";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				status = new ClosureStatus(rs.getInt("status_id"), rs.getString("status_desc"), rs.getBoolean("deleted"));

				statuses.add(status);
			}
		}
		catch (SQLException e) {
			System.err.println("ClosureStatusDB.getClosureStatuses(): " + e);
			throw new ClosureStatusException("Can not extract closure statuses from DB: " + e);
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
		return statuses;
	}

	public static HashMap<Integer, ClosureStatus> getClosureStatusesMap() throws ClosureStatusException {

		HashMap<Integer, ClosureStatus> statuses = null;

		try {
			statuses = new HashMap<Integer, ClosureStatus>(10);

			for (ClosureStatus status : getClosureStatuses()) {

				statuses.put(status.getClosureStatusID(), status);
			}
		}
		catch (ClosureStatusException e) {
			System.err.println("ClosureStatusDB.getClosureStatusesMap(): " + e);
			throw e;
		}

		return statuses;
	}

	public static Vector<ClosureStatus> getClosureStatuses(final boolean active) throws ClosureStatusException {

		Vector<ClosureStatus> cs = getClosureStatuses();

		CollectionUtils.filter(cs, new Predicate() {

			@Override
			public boolean evaluate(Object o) {

				return (!((ClosureStatus) o).isDeleted() == active);
			}
		});

		return cs;
	}

	public static ClosureStatus getClosureStatus(int status_id) throws ClosureStatusException {

		ClosureStatus status = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		if (statuses != null && statuses.containsKey(new Integer(status_id))) {
			status = (ClosureStatus) ((ClosureStatus) statuses.get(new Integer(status_id))).clone();
		}
		else {
			try {
				sql = "SELECT * FROM SCHOOL_SYSTEM_WEATHER_STATUS WHERE STATUS_ID=" + status_id;

				con = DAOUtils.getConnection();
				stat = con.createStatement();
				rs = stat.executeQuery(sql);

				if (rs.next()) {
					status = new ClosureStatus(rs.getInt("status_id"), rs.getString("status_desc"), rs.getBoolean("deleted"));

					if (statuses == null) {
						statuses = new HashMap<Integer, ClosureStatus>();
					}

					statuses.put(new Integer(status.getClosureStatusID()), status);
				}
				else {
					throw new ClosureStatusException(status_id, "Can not extract closure statuses from DB ");
				}
			}
			catch (SQLException e) {
				System.err.println("ClosureStatusDB.getClosureStatuses(): " + e);
				throw new ClosureStatusException("Can not extract closure statuses from DB: " + e);
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
		return status;
	}

	public static ClosureStatus getClosureStatus(School s) throws ClosureStatusException {

		ClosureStatus status = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT * FROM SCHOOL_WEATHER_CLOSURES WHERE SCHOOL_ID=" + s.getSchoolID()
					+ " AND CLOSURE_DATE=TO_CHAR(SYSDATE, 'DD-MON-YY') ORDER BY CLOSURE_ID DESC";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			if (rs.next()) {
				status = getClosureStatus(rs.getInt("status_id"));

				status.setSchoolClosureNote(rs.getString("comment"));
				status.setWeatherRelated(rs.getBoolean("weather_related"));
				status.setRationale(rs.getString("rationale"));
			}
			else {
				if (statuses != null) {
					SchoolStatusGlobalConfigBean config;
					try {
						config = SchoolStatusGlobalConfigService.getSchoolStatusGlobalConfigBean();
					}
					catch (SchoolSystemException e) {
						throw new ClosureStatusException("Can not extract school status global config statuses from DB: ");
					}
					if (config.isSummer())
						status = (ClosureStatus) statuses.get(new Integer(22)); // closed for summer
					else
						status = (ClosureStatus) statuses.get(new Integer(9)); // open
				}
				else
					throw new ClosureStatusException("Can not extract closure statuses from DB: ");
			}
		}
		catch (SQLException e) {
			System.err.println("ClosureStatusDB.getClosureStatuses(): " + e);
			throw new ClosureStatusException("Can not extract closure statuses from DB: " + e);
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

		return status;
	}

	public static boolean updateClosureStatus(ClosureStatus s) throws ClosureStatusException {

		Connection con = null;
		Statement stat = null;
		String sql;
		int check = 0;

		try {
			sql = "UPDATE SCHOOL_SYSTEM_WEATHER_STATUS SET STATUS_NAME='" + s.getClosureStatusDescription() + "'";

			sql += " WHERE STATUS_ID=" + s.getClosureStatusID();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.createStatement();
			check = stat.executeUpdate(sql);
		}
		catch (SQLException e) {
			System.err.println("ClosureStatusDB.updateClosureStatus(): " + e);
			throw new ClosureStatusException("Could not update closure status in DB: " + e);
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

	public static int addClosureStatus(String desc) throws ClosureStatusException {

		Connection con = null;
		PreparedStatement stat = null;
		Statement stat2 = null;
		ResultSet rs = null;
		String sql;
		int check = 0, id = -1;

		try {
			sql = "INSERT INTO SCHOOL_SYSTEM_WEATHER_STATUS VALUES(WEATHER_STATUS_SEQ.nextval, ?, 0)";

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareStatement(sql);
			stat.clearParameters();
			stat.setString(1, desc);

			check = stat.executeUpdate();
			con.commit();
			stat.close();

			if (check == 1) {
				stat2 = con.createStatement();
				rs = stat2.executeQuery("SELECT WEATHER_STATUS_SEQ.CURRVAL pid FROM DUAL");
				if (rs.next()) {
					id = rs.getInt("pid");
				}
				else {
					id = -1;
				}
				rs.close();
				stat2.close();
			}

			con.close();
		}
		catch (SQLException e) {
			System.err.println("ClosureStatusDB.addClosureStatus(): " + e);
			throw new ClosureStatusException("Could not add ClosureStatus to DB: " + e);
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
				stat2.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return (id);
	}

	public static boolean deleteClosureStatus(int sid) throws ClosureStatusException {

		Connection con = null;
		Statement stat = null;
		String sql;
		int check = 0;

		try {
			sql = "UPDATE SCHOOL_SYSTEM_WEATHER_STATUS SET DELETED = 1 WHERE STATUS_ID=" + sid;

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.createStatement();
			check = stat.executeUpdate(sql);

			con.commit();

		}
		catch (SQLException e) {
			System.err.println("ClosureStatusDB.deleteClosureStatus(): " + e);
			throw new ClosureStatusException("Could not delete ClosureStatus from DB: " + e);
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

	public static ClosureStatus createClosureStatusBean(ResultSet rs) throws ClosureStatusException {

		ClosureStatus abean = null;
		try {
			abean = ClosureStatusDB.getClosureStatus(rs.getInt("status_id"));
			abean.setSchoolClosureNote(rs.getString("COMMENT"));
			abean.setWeatherRelated(rs.getBoolean("weather_related"));
			abean.setRationale(rs.getString("rationale"));
		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}
}