package com.esdnl.personnel.jobs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.awsd.school.bean.RegionBean;
import com.awsd.school.dao.RegionManager;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;

public class ApplicantRegionalPreferenceManager {

	public static void addApplicantRegionalPreferences(ApplicantProfileBean abean, int[] regions)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			ApplicantRegionalPreferenceManager.clearApplicantRegionalPreferences(abean);

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.add_region_pref(?,?); end;");

			for (int region : regions) {
				stat.clearParameters();

				stat.setString(1, abean.getSIN());
				stat.setInt(2, region);

				stat.execute();
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addApplicantRegionalPreferences(ApplicantProfileBean abean, RegionBean[] regions) " + e);
			throw new JobOpportunityException("Can not add ApplicantRegionalPreferences to DB.", e);
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

	public static void clearApplicantRegionalPreferences(ApplicantProfileBean abean) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.delete_region_prefs(?); end;");
			stat.setString(1, abean.getSIN());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void clearApplicantRegionalPreferences(ApplicantProfileBean abean): " + e);
			throw new JobOpportunityException("Can not add ApplicantRegionalPreferences to DB.", e);
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

	public static HashMap<Integer, RegionBean> getApplicantRegionalPreferencesMap(ApplicantProfileBean abean)
			throws JobOpportunityException {

		HashMap<Integer, RegionBean> v_opps = null;
		RegionBean r = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new HashMap<Integer, RegionBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_region_prefs(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, abean.getSIN());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				r = RegionManager.createRegionBean(rs);
				v_opps.put(new Integer(r.getId()), r);
			}
		}
		catch (SQLException e) {
			System.err.println("HashMap<Integer, RegionBean> getApplicantRegionalPreferencesMap(ApplicantProfileBean abean): "
					+ e);
			throw new JobOpportunityException("Can not extract ApplicantRegionalPreferences from DB.", e);
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