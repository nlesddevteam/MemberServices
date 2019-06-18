package com.esdnl.personnel.jobs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantEducationOtherSSBean;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
public class ApplicantEducationOtherSSManager {

	public static ApplicantEducationOtherSSBean addApplicantEducationOtherSSBean(ApplicantEducationOtherSSBean abean)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.add_app_edu_other_ss(?,?); end;");

			stat.setString(1, abean.getSIN());
			stat.setString(2, abean.getOtherInformation());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("ApplicantEducationOtherSSBean addApplicantEducationOtherSSBean(ApplicantEducationOtherSSBean abean)): "
					+ e);
			throw new JobOpportunityException("Can not add ApplicantEducationOtherSSBean to DB.", e);
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

		return abean;
	}

	public static boolean deleteApplicantEducationOtherSSBean(ApplicantProfileBean abean) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = true;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.del_app_edu_other_ss(?); end;");

			stat.setString(1, abean.getSIN());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("deleteApplicantEducationOtherSSBean(ApplicantProfileBean abean): "
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

	public static ApplicantEducationOtherSSBean getApplicantEducationOtherSSBean(String sin)
			throws JobOpportunityException {

		ApplicantEducationOtherSSBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_app_edu_other_ss(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, sin);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createApplicantEducationOtherSSBean(rs);
			else
				eBean = null;
		}
		catch (SQLException e) {
			System.err.println("ApplicantEducationOtherSSBean getApplicantEducationOtherSSBean(String sin): " + e);
			throw new JobOpportunityException("Can not extract ApplicantOtherInformationBean from DB.", e);
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

		return eBean;
	}

	public static ApplicantEducationOtherSSBean createApplicantEducationOtherSSBean(ResultSet rs) {

		ApplicantEducationOtherSSBean aBean = null;
		try {
			aBean = new ApplicantEducationOtherSSBean();

			aBean.setSIN(rs.getString("SIN"));
			aBean.setOtherInformation(rs.getString("OTHER_INFO"));
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}
}