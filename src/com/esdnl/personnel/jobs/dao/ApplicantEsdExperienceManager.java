package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantEsdExperienceBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;

public class ApplicantEsdExperienceManager {

	public static ApplicantEsdExperienceBean addApplicantEsdExperienceBean(ApplicantEsdExperienceBean abean)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.add_esd_exp_general(?,?,?,?,?); end;");

			stat.setString(1, abean.getSIN());
			stat.setInt(2, abean.getPermanentContractSchool());
			stat.setString(3, abean.getPermanentContractPosition());
			stat.setInt(4, abean.getContractSchool());
			if (abean.getContractEndDate() != null)
				stat.setDate(5, new java.sql.Date(abean.getContractEndDate().getTime()));
			else
				stat.setDate(5, null);

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("ApplicantEsdExperienceBean addApplicantEsdExperienceBean(ApplicantEsdExperienceBean abean): "
					+ e);
			throw new JobOpportunityException("Can not add ApplicantEsdExperienceBean to DB.", e);
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

	public static ApplicantEsdExperienceBean getApplicantEsdExperienceBean(String sin) throws JobOpportunityException {

		ApplicantEsdExperienceBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_esd_exp(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, sin);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createApplicantEsdExperienceBean(rs);
			else
				eBean = null;
		}
		catch (SQLException e) {
			System.err.println("ApplicantEsdExperienceBean getApplicantEsdExperienceBean(String sin): " + e);
			throw new JobOpportunityException("Can not extract ApplicantEsdExperienceBean from DB.", e);
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

	public static ApplicantEsdExperienceBean createApplicantEsdExperienceBean(ResultSet rs) {

		ApplicantEsdExperienceBean aBean = null;
		try {
			aBean = new ApplicantEsdExperienceBean();

			aBean.setId(rs.getInt("PK_ID"));
			aBean.setSIN(rs.getString("SIN"));
			aBean.setPermanentContractPosition(rs.getString("PERM_POSITION"));
			aBean.setPermanentContractSchool(rs.getInt("PERM_SCHOOL"));
			aBean.setReplacementTime(rs.getInt("REPL_TIME"));
			aBean.setPermanentLTime(rs.getInt("PERM_TIME"));
			aBean.setSubstituteTime(rs.getInt("SUB_TIME"));
			aBean.setContractSchool(rs.getInt("CONTRACT_SCHOOL"));
			if (rs.getDate("CONTRACT_ENDDATE") != null)
				aBean.setContractEndDate(new java.util.Date(rs.getDate("CONTRACT_ENDDATE").getTime()));

			//System.err.println("REPL_TIME (RS) = " + rs.getInt("REPL_TIME"));
			//System.err.println("REPL_TIME (BEAN) = " + aBean.getReplacementTime());
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}
}