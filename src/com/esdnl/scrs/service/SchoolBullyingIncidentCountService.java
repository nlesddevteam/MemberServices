package com.esdnl.scrs.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.school.SchoolDB;
import com.esdnl.dao.DAOUtils;
import com.esdnl.scrs.domain.BullyingException;
import com.esdnl.scrs.domain.SchoolBullyingIncidentCount;

public class SchoolBullyingIncidentCountService {

	public static ArrayList<SchoolBullyingIncidentCount> getSchoolBullyingIncidentCounts() throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<SchoolBullyingIncidentCount> beans = null;

		try {
			beans = new ArrayList<SchoolBullyingIncidentCount>();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_school_incident_counts; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(createSchoolBullyingIncidentCount(rs));

			return beans;
		}
		catch (SQLException e) {
			e.printStackTrace();

			System.err.println("ArrayList<SchoolBullyingIncidentCount> getBullyingIncidentBeans(): " + e);
			throw new BullyingException("Can not fetch SchoolBullyingIncidentCount to DB.", e);
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

	public static SchoolBullyingIncidentCount createSchoolBullyingIncidentCount(ResultSet rs) {

		SchoolBullyingIncidentCount abean = null;

		try {
			abean = new SchoolBullyingIncidentCount();

			abean.setSchool(SchoolDB.createSchoolBean(rs));
			abean.setOverallIncidentCount(rs.getInt("INCIDENT_COUNT"));
			abean.setWeeklyIncidentCount(rs.getInt("WEEKLY_INCIDENT_COUNT"));
			abean.setMonthlyIncidentCount(rs.getInt("MONTHLY_INCIDENT_COUNT"));

			if (rs.getInt("WEEKLY_INCIDENT_COUNT") == 0 && rs.getInt("WEEKLY_NO_INCIDENT_COUNT") == 0)
				abean.setNoIncidentsReported(false);
			else
				abean.setNoIncidentsReported(true);
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
