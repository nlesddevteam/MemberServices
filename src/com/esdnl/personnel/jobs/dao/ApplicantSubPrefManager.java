package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;

public class ApplicantSubPrefManager {

	public static void addApplicantSubPrefs(ApplicantProfileBean abean, School[] schools) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			ApplicantSubPrefManager.clearApplicantSubPrefs(abean);

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.add_sub_pref(?,?); end;");

			for (int i = 0; i < schools.length; i++) {
				stat.clearParameters();

				stat.setString(1, abean.getSIN());
				stat.setInt(2, schools[i].getSchoolID());

				stat.execute();
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("ApplicantProfileBean addJobOpportunityBean(ApplicantProfileBean abean): " + e);
			throw new JobOpportunityException("Can not add ApplicantProfileBean to DB.", e);
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

	public static void clearApplicantSubPrefs(ApplicantProfileBean abean) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.delete_sub_prefs(?); end;");
			stat.setString(1, abean.getSIN());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("ApplicantProfileBean addJobOpportunityBean(ApplicantProfileBean abean): " + e);
			throw new JobOpportunityException("Can not add ApplicantProfileBean to DB.", e);
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

	public static HashMap<Integer, School> getApplicantSubPrefsMap(ApplicantProfileBean abean)
			throws JobOpportunityException {

		HashMap<Integer, School> v_opps = null;
		School s = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new HashMap<Integer, School>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_sub_prefs(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, abean.getSIN());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				s = SchoolDB.createSchoolBean(rs);
				v_opps.put(new Integer(s.getSchoolID()), s);
			}
		}
		catch (SQLException e) {
			System.err.println("ApplicantProfileManager.getApplicantProfileBeanByJob(String): " + e);
			throw new JobOpportunityException("Can not extract ApplicantProfileBean from DB.", e);
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

		return v_opps;
	}
}