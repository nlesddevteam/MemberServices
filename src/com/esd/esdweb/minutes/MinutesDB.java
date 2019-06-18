package com.esd.esdweb.minutes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;

import com.esdnl.dao.DAOUtils;

public class MinutesDB {

	public static Vector<Date> getMinutes() throws SQLException {

		Vector<Date> mins = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			mins = new Vector<Date>(5);
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.esd_web.get_minutes; end;");
			stat.registerOutParameter(1, -10);
			stat.execute();
			for (rs = ((OracleCallableStatement) stat).getCursor(1); rs.next(); mins.add(new Date(rs.getDate("MEETING_DATE").getTime())))
				;
		}
		catch (SQLException e) {
			System.err.println("BoardMeetingMinutesDB.getMinutes(): " + e);
			throw e;
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception exception1) {}
			try {
				stat.close();
			}
			catch (Exception exception2) {}
			try {
				con.close();
			}
			catch (Exception exception3) {}
		}
		return mins;
	}

}