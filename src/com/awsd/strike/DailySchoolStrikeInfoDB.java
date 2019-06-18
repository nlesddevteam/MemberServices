package com.awsd.strike;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.awsd.school.School;
import com.esdnl.dao.DAOUtils;

public class DailySchoolStrikeInfoDB {

	public static DailySchoolStrikeInfo getDailySchoolStrikeInfo(School school) throws StrikeException {

		DailySchoolStrikeInfo info = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT * FROM STRIKE_DAILY_SCHOOL_INFO WHERE INFO_DATE=TRUNC(SYSDATE) AND SCHOOL_ID="
					+ school.getSchoolID();

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			if (rs.next()) {
				info = new DailySchoolStrikeInfo(rs.getInt("INFO_ID"), school, rs.getDate("INFO_DATE"), rs.getString("INFO_LAST_UPDATED"), rs.getInt("PICKETERS"), rs.getString("PKET_LINE_INCIDENTS"), rs.getString("STUDENT_ATTD"), rs.getString("ESSENTIAL_WORKERS"), rs.getString("ESSENTIAL_WORKERS_ISSUES"), rs.getString("TRANSPORTATION_ISSUES"), rs.getString("IRREGULAR_OCCURRENCES"), rs.getString("BLDG_SFTY_SANITATION_ISSUES"), rs.getString("STUD_SUPP_SERVICES_ISSUES"));
			}
			else {
				info = null;
			}
			rs.close();
			stat.close();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("DailySchoolStrikeInfoDB.getDailySchoolStrikeInfo(School): " + e);
			throw new StrikeException("Can not extract daily school strike info from DB: " + e);
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
		return info;
	}

	public static Vector getDailySchoolStrikeInfoHistory(School school) throws StrikeException {

		Vector history = null;
		DailySchoolStrikeInfo info = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		StringBuffer sql = null;

		try {
			history = new Vector(15); //initially three weeks of history

			sql = new StringBuffer("SELECT * FROM STRIKE_DAILY_SCHOOL_INFO WHERE SCHOOL_ID=" + school.getSchoolID());
			sql.append(" ORDER BY TRUNC(INFO_DATE) DESC");

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql.toString());

			while (rs.next()) {
				info = new DailySchoolStrikeInfo(rs.getInt("INFO_ID"), school, rs.getDate("INFO_DATE"), rs.getString("INFO_LAST_UPDATED"), rs.getInt("PICKETERS"), rs.getString("PKET_LINE_INCIDENTS"), rs.getString("STUDENT_ATTD"), rs.getString("ESSENTIAL_WORKERS"), rs.getString("ESSENTIAL_WORKERS_ISSUES"), rs.getString("TRANSPORTATION_ISSUES"), rs.getString("IRREGULAR_OCCURRENCES"), rs.getString("BLDG_SFTY_SANITATION_ISSUES"), rs.getString("STUD_SUPP_SERVICES_ISSUES"));
				history.add(info);
			}

			rs.close();
			stat.close();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("DailySchoolStrikeInfoDB.getDailySchoolStrikeInfoHistory(School): " + e);
			throw new StrikeException("Can not extract daily school strike info history from DB: " + e);
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
		return history;
	}

	public static boolean addDailySchoolStrikeInfo(DailySchoolStrikeInfo info) throws StrikeException {

		Connection con = null;
		PreparedStatement stat = null;

		String sql;
		int check = 0;

		try {
			sql = "INSERT INTO STRIKE_DAILY_SCHOOL_INFO VALUES(STRIKE_DAILY_SCHOOL_INFO_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?, ?, TRUNC(SYSDATE), TO_CHAR(SYSDATE, 'HH12:MI:SS AM'), ?, ?)";

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareStatement(sql);
			stat.clearParameters();
			stat.setInt(1, info.getSchoolID());
			stat.setInt(2, info.getNumberPicketers());

			if (info.getPicketLineIncidences() != null)
				stat.setString(3, info.getPicketLineIncidences());
			else
				stat.setString(3, null);

			if (info.getStudentAttendance() != null)
				stat.setString(4, info.getStudentAttendance());
			else
				stat.setString(4, null);

			if (info.getEssentialWorkersNames() != null)
				stat.setString(5, info.getEssentialWorkersNames());
			else
				stat.setString(5, null);

			if (info.getEssentialWorkersIssues() != null)
				stat.setString(6, info.getEssentialWorkersIssues());
			else
				stat.setString(6, null);

			if (info.getTransportationIssues() != null)
				stat.setString(7, info.getTransportationIssues());
			else
				stat.setString(7, null);

			if (info.getIrregularOccurrences() != null)
				stat.setString(8, info.getIrregularOccurrences());
			else
				stat.setString(8, null);

			if (info.getBuildingSaftySanitationIssues() != null)
				stat.setString(9, info.getBuildingSaftySanitationIssues());
			else
				stat.setString(9, null);

			if (info.getStudentSupportServicesIssues() != null)
				stat.setString(10, info.getStudentSupportServicesIssues());
			else
				stat.setString(10, null);

			check = stat.executeUpdate();
			con.commit();
			stat.close();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("SchoolStrikeGroupDB.addDailySchoolStrikeInfo(): " + e);
			throw new StrikeException("Could not add daily school strike info to DB: " + e);
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

	public static boolean updateDailySchoolStrikeInfo(DailySchoolStrikeInfo info) throws StrikeException {

		Connection con = null;
		PreparedStatement stat = null;

		StringBuffer sql = null;
		int check = 0;

		try {
			sql = new StringBuffer("UPDATE STRIKE_DAILY_SCHOOL_INFO SET ");
			sql.append("PICKETERS=?, ");
			sql.append("PKET_LINE_INCIDENTS=?, ");
			sql.append("STUDENT_ATTD=?, ");
			sql.append("ESSENTIAL_WORKERS=?, ");
			sql.append("ESSENTIAL_WORKERS_ISSUES=?, ");
			sql.append("TRANSPORTATION_ISSUES=?, ");
			sql.append("IRREGULAR_OCCURRENCES=?, ");
			sql.append("INFO_LAST_UPDATED=TO_CHAR(SYSDATE, 'HH12:MI:SS AM'), ");
			sql.append("BLDG_SFTY_SANITATION_ISSUES=?, ");
			sql.append("STUD_SUPP_SERVICES_ISSUES=? ");
			sql.append("WHERE INFO_DATE=TRUNC(SYSDATE) AND SCHOOL_ID=" + info.getSchoolID());

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareStatement(sql.toString());
			stat.clearParameters();
			stat.setInt(1, info.getNumberPicketers());

			if (info.getPicketLineIncidences() != null)
				stat.setString(2, info.getPicketLineIncidences());
			else
				stat.setString(3, null);

			if (info.getStudentAttendance() != null)
				stat.setString(3, info.getStudentAttendance());
			else
				stat.setString(3, null);

			if (info.getEssentialWorkersNames() != null)
				stat.setString(4, info.getEssentialWorkersNames());
			else
				stat.setString(4, null);

			if (info.getEssentialWorkersIssues() != null)
				stat.setString(5, info.getEssentialWorkersIssues());
			else
				stat.setString(5, null);

			if (info.getTransportationIssues() != null)
				stat.setString(6, info.getTransportationIssues());
			else
				stat.setString(6, null);

			if (info.getIrregularOccurrences() != null)
				stat.setString(7, info.getIrregularOccurrences());
			else
				stat.setString(7, null);

			if (info.getBuildingSaftySanitationIssues() != null)
				stat.setString(8, info.getBuildingSaftySanitationIssues());
			else
				stat.setString(8, null);

			if (info.getStudentSupportServicesIssues() != null)
				stat.setString(9, info.getStudentSupportServicesIssues());
			else
				stat.setString(9, null);

			check = stat.executeUpdate();
			con.commit();
			stat.close();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("DailySchoolStrikeInfoDB.updateDailySchoolStrikeInfo(): " + e);
			throw new StrikeException("Could not update daily school strike info to DB: " + e);
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
}