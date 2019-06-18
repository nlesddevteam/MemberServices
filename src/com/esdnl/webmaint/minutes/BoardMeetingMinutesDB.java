package com.esdnl.webmaint.minutes;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;

public class BoardMeetingMinutesDB {

	public static boolean addMinutes(java.util.Date meeting_date) throws Exception {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);
			stat = con.prepareCall("begin awsd_user.esd_web.insert_minutes(?); end;");
			stat.setDate(1, new Date(meeting_date.getTime()));
			stat.execute();
			stat.close();

			check = true;
		}
		catch (SQLException e) {
			check = false;
			System.err.println("BoardMeetingMinutesDB.addMinutes(java.util.Date meeting_date): " + e);
			throw new MinutesException("Can not add MEETING MINUTES to DB: " + e);
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

	public static Vector getMinutes() throws MinutesException {

		Vector mins = null;
		java.util.Date dt = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			mins = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.esd_web.get_minutes; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				mins.add(new java.util.Date(rs.getDate("MEETING_DATE").getTime()));
		}
		catch (SQLException e) {
			System.err.println("BoardMeetingMinutesDB.getMinutes(): " + e);
			throw new MinutesException("Can not extract MEETING MINUTES from DB: " + e);
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
		return mins;
	}

	public static boolean deleteMinutes(java.util.Date dt) throws SQLException {

		Connection con = null;
		CallableStatement stat = null;
		File f = null;

		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);
			stat = con.prepareCall("begin awsd_user.esd_web.delete_minutes(?); end;");
			stat.setDate(1, new Date(dt.getTime()));
			stat.execute();
			stat.close();

			check = true;
		}
		catch (SQLException e) {
			check = false;
			System.err.println("BoardMeetingMinutesDB.deleteMinutes(java.util.Date dt): " + e);
			throw e;
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
}