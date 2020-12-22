package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantJobAppliedBean;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class ApplicantJobAppliedManager {

	public static ArrayList<ApplicantJobAppliedBean> getApplicantJobsApplied(String applicantid) {

		ArrayList<ApplicantJobAppliedBean> list = new ArrayList<ApplicantJobAppliedBean>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_all_jobs_by_applicant(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, applicantid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				ApplicantJobAppliedBean eBean = createApplicantJobAppliedBean(rs);
				list.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("ApplicantJobAppliedManager.getApplicantJobsApplied(String applicantid): " + e);
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

		return list;
	}

	public static ApplicantJobAppliedBean createApplicantJobAppliedBean(ResultSet rs) {

		ApplicantJobAppliedBean aBean = new ApplicantJobAppliedBean();

		try {
			aBean.setAppliedDate(rs.getDate("APPLIED_DATE"));
			aBean.setCompNum(rs.getString("COMP_NUM"));
			aBean.setPosTitle(rs.getString("POS_TITLE"));
			aBean.setSchoolName(rs.getString("SCHOOL_NAME"));
			aBean.setShortlisted(rs.getBoolean("SHORTLISTED"));
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}
}
