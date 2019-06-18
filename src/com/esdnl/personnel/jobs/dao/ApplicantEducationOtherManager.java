package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.awsd.mail.bean.AlertBean;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantEducationOtherBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.constants.TrainingMethodConstant;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class ApplicantEducationOtherManager {

	public static ApplicantEducationOtherBean addApplicantEducationOtherBean(ApplicantEducationOtherBean abean)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.add_edu_other(?,?,?,?,?,?,?,?,?,?,?,?); end;");

			stat.setString(1, abean.getSIN());
			stat.setInt(2, abean.getProfessionalTrainingLevel().getValue());
			stat.setInt(3, abean.getNumberSpecialEducationCourses());
			stat.setInt(4, abean.getNumberFrenchCourses());
			stat.setInt(5, abean.getNumberMathCourses());
			stat.setInt(6, abean.getNumberEnglishCourses());
			stat.setString(7, abean.getTeachingCertificateLevel());
			if (abean.getTeachingCertificateIssuedDate() != null) {
				stat.setDate(8, new java.sql.Date(abean.getTeachingCertificateIssuedDate().getTime()));
			}
			else {
				stat.setNull(8, OracleTypes.DATE);
			}
			stat.setInt(9, abean.getNumberMusicCourses());
			stat.setInt(10, abean.getNumberTechnologyCourses());
			stat.setInt(11, abean.getNumberScienceCourses());
			stat.setInt(12, abean.getTotalCoursesCompleted());
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("ApplicantEducationBean addApplicantEducationBean(ApplicantEducationBean abean): " + e);
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

	public static ApplicantEducationOtherBean getApplicantEducationOtherBean(String sin) throws JobOpportunityException {

		ApplicantEducationOtherBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_edu_other(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, sin);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createApplicantEducationOtherBean(rs);
			else
				eBean = null;
		}
		catch (SQLException e) {
			System.err.println("ApplicantEducationOtherBean getApplicantEducationOtherBean(String sin): " + e);
			throw new JobOpportunityException("Can not extract ApplicantEducationOtherBean from DB.", e);
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

	public static ApplicantEducationOtherBean createApplicantEducationOtherBean(ResultSet rs) {

		ApplicantEducationOtherBean aBean = null;
		try {
			aBean = new ApplicantEducationOtherBean();

			aBean.setId(rs.getInt("PK_ID"));
			aBean.setSIN(rs.getString("SIN"));
			aBean.setProfessionalTrainingLevel(TrainingMethodConstant.get(rs.getInt("TRNLVL")));
			aBean.setNumberSpecialEducationCourses(rs.getInt("SPED_CRS"));
			aBean.setNumberFrenchCourses(rs.getInt("FR_CRS"));
			aBean.setNumberMathCourses(rs.getInt("MATH_CRS"));
			aBean.setNumberEnglishCourses(rs.getInt("ENGLISH_CRS"));
			aBean.setTeachingCertificateLevel(rs.getString("CERT_LVL"));
			if (rs.getDate("CERT_DATE") != null) {
				aBean.setTeachingCertificateIssuedDate(new java.util.Date(rs.getDate("CERT_DATE").getTime()));
			}
			else {
				aBean.setTeachingCertificateIssuedDate(null);
			}
			aBean.setNumberMusicCourses(rs.getInt("MUSIC_CRS"));
			aBean.setNumberTechnologyCourses(rs.getInt("TECH_CRS"));
			aBean.setNumberScienceCourses(rs.getInt("SCIENCE_CRS"));
			aBean.setTotalCoursesCompleted(rs.getInt("TOTAL_CRS_COMPLETED"));
		}
		catch (SQLException e) {
			try {
				new AlertBean(e);
			}
			catch (Exception ee) {}
			aBean = null;
		}
		return aBean;
	}
}