package com.esdnl.personnel.jobs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.ApplicantRegionalJobPoolSSBean;
public class ApplicantRegionalJobPoolSSManager {

	public static void addApplicantRegionalJobPoolSSBean(String sin,Integer jobid)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.add_app_reg_job_ss(?,?); end;");

			stat.setString(1, sin);
			stat.setInt(2, jobid);

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("ApplicantRegionalJobPoolSSBean addApplicantRegionalJobPoolSSBean(ApplicantRegionalJobPoolSSBean abean): "
					+ e);
			throw new JobOpportunityException("Can not add ApplicantRegionalJobPoolSSBean to DB.", e);
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

	public static boolean deleteApplicantRegionalJobPoolSSBean(String sin) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = true;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.del_app_reg_job_ss(?); end;");

			stat.setString(1, sin);

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("deleteApplicantRegionalJobPoolSSBean(ApplicantProfileBean abean): "
					+ e);
			throw new JobOpportunityException("Can not delete ApplicantEducationOtherSSBean to DB.", e);
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

		return check;
	}
	public static HashMap<Integer, ApplicantRegionalJobPoolSSBean> getApplicantRegionalJobPoolPreferencesMap(String sin)
	throws JobOpportunityException {

			HashMap<Integer, ApplicantRegionalJobPoolSSBean> v_opps = null;
			ApplicantRegionalJobPoolSSBean r = null;
			Connection con = null;
			CallableStatement stat = null;
			ResultSet rs = null;
			
			try {
				v_opps = new HashMap<Integer, ApplicantRegionalJobPoolSSBean>();
			
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_region_pool_ss(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setString(2, sin);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
			
				while (rs.next()) {
					r = createApplicantRegionalJobPoolSSBean(rs);
					v_opps.put(new Integer(r.getPoolJob()), r);
				}
			}
			catch (SQLException e) {
				System.err.println("HashMap<Integer, ApplicantRegionalJobPoolSSBean> getApplicantRegionalJobPoolPreferencesMap(String sin): "
						+ e);
				throw new JobOpportunityException("Can not extract ApplicantRegionalJobPoolSSBean from DB.", e);
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
	
	public static ApplicantRegionalJobPoolSSBean createApplicantRegionalJobPoolSSBean(ResultSet rs) {

		ApplicantRegionalJobPoolSSBean aBean = null;
		try {
			aBean = new ApplicantRegionalJobPoolSSBean();

			aBean.setSin(rs.getString("APPLICANT_ID"));
			aBean.setPoolJob(rs.getInt("JOB_TYPE_ID"));
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}
}