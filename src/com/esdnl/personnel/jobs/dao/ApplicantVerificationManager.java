package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantVerificationBean;
import oracle.jdbc.OracleTypes;

public class ApplicantVerificationManager {
	public static ApplicantVerificationBean addApplicantVerificationBean(ApplicantVerificationBean abean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.applicant_verification(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setString(2, abean.getApplicantId());
			stat.setLong(3, abean.getVerifiedBy());
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ApplicantVerificationBean addApplicantEmploymentSS(ApplicantVerificationBean abean): " + e);
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
	public static ApplicantVerificationBean createApplicantVerificationBean(ResultSet rs) {
		ApplicantVerificationBean aBean = null;
		try {
			aBean = new ApplicantVerificationBean();
			aBean.setAvid(rs.getInt("AV_ID"));
			aBean.setApplicantId(rs.getString("APPLICANT_ID"));
			aBean.setVerifiedBy(rs.getLong("VERFIED_BY"));
			if(!(rs.getDate("DATE_VERIFIED")==null)){
				aBean.setDateVerified(new java.util.Date(rs.getDate("DATE_VERIFICE").getTime()));
			}else{
				aBean.setDateVerified(null);
			}
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}
}
