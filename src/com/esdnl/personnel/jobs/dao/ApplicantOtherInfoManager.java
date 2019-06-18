package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantOtherInformationBean;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;

public class ApplicantOtherInfoManager {

	public static ApplicantOtherInformationBean addApplicantOtherInformationBean(ApplicantOtherInformationBean abean)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.add_other_info(?,?); end;");

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

			System.err.println("ApplicantOtherInformationBean addApplicantOtherInformationBean(ApplicantOtherInformationBean abean): "
					+ e);
			throw new JobOpportunityException("Can not add ApplicantOtherInformationBean to DB.", e);
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

	public static boolean deleteApplicantOtherInformationBean(ApplicantProfileBean abean) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = true;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.del_other_info(?); end;");

			stat.setString(1, abean.getSIN());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("ApplicantOtherInformationBean deleteApplicantOtherInformationBean(ApplicantProfileBean abean): "
					+ e);
			throw new JobOpportunityException("Can not delete ApplicantOtherInformationBean to DB.", e);
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

	public static ApplicantOtherInformationBean getApplicantOtherInformationBean(String sin)
			throws JobOpportunityException {

		ApplicantOtherInformationBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_other_info(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, sin);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createApplicantOtherInformationBean(rs);
			else
				eBean = null;
		}
		catch (SQLException e) {
			System.err.println("ApplicantOtherInformationBean getApplicantOtherInformationBean(String sin): " + e);
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

	public static ApplicantOtherInformationBean createApplicantOtherInformationBean(ResultSet rs) {

		ApplicantOtherInformationBean aBean = null;
		try {
			aBean = new ApplicantOtherInformationBean();

			aBean.setSIN(rs.getString("SIN"));
			aBean.setOtherInformation(rs.getString("OTHER_INFO"));
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}
}