package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantSubstituteTeachingExpBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;

public class ApplicantSubExpManager {

	public static ApplicantSubstituteTeachingExpBean addApplicantSubstituteTeachingExpBean(	ApplicantSubstituteTeachingExpBean abean)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.add_sub_exp(?,?,?,?,?); end;");

			stat.setString(1, abean.getSIN());
			stat.setDate(2, new java.sql.Date(abean.getFrom().getTime()));
			stat.setDate(3, new java.sql.Date(abean.getTo().getTime()));
			stat.setString(4, abean.getSchoolBoard());
			stat.setInt(5, abean.getNumDays());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("ApplicantSubstituteTeachingExpBean addApplicantSubstituteTeachingExpBean(ApplicantSubstituteTeachingExpBean abean): "
					+ e);
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

		return abean;
	}

	public static void deleteApplicantSubExpBean(int id) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.del_sub_exp(?); end;");

			stat.setInt(1, id);

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("deleteApplicantSubExpBean(int id): " + e);
			throw new JobOpportunityException("Can not delete ApplicantSubExpBean to DB.", e);
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

	public static ApplicantSubstituteTeachingExpBean[] getApplicantSubstituteTeachingExpBeans(String sin)
			throws JobOpportunityException {

		Vector v_opps = null;
		ApplicantSubstituteTeachingExpBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_sub_exp(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, sin);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantSubstituteTeachingExpBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("ApplicantSubstituteTeachingExpBean[] getApplicantSubstituteTeachingExpBeans(String sin): "
					+ e);
			throw new JobOpportunityException("Can not extract ApplicantEsdReplacementExperienceBean from DB.", e);
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

		return (ApplicantSubstituteTeachingExpBean[]) v_opps.toArray(new ApplicantSubstituteTeachingExpBean[0]);
	}

	public static ApplicantSubstituteTeachingExpBean createApplicantSubstituteTeachingExpBean(ResultSet rs) {

		ApplicantSubstituteTeachingExpBean aBean = null;
		try {
			aBean = new ApplicantSubstituteTeachingExpBean();

			aBean.setId(rs.getInt("PK_ID"));
			aBean.setSIN(rs.getString("SIN"));
			aBean.setFrom(new java.util.Date(rs.getDate("FROM").getTime()));
			aBean.setTo(new java.util.Date(rs.getDate("TO").getTime()));
			aBean.setSchoolBoard(rs.getString("BOARD"));
			aBean.setNumDays(rs.getInt("NUM_DAYS"));
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}
}