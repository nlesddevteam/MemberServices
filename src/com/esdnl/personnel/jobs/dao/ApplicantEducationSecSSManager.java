package com.esdnl.personnel.jobs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantEducationSecSSBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
public class ApplicantEducationSecSSManager {
	public static ApplicantEducationSecSSBean addApplicantEducationSecSSBean(ApplicantEducationSecSSBean abean)
	throws JobOpportunityException {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.add_app_education_sec_ss(?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setString(2, abean.getSin());
			stat.setString(3, abean.getEducationLevel());
			stat.setString(4, abean.getSchoolName());
			stat.setString(5, abean.getSchoolCity());
			stat.setString(6, abean.getSchoolProvince());
			stat.setString(7, abean.getYearsCompleted());
			stat.setString(8, abean.getGraduated());
			stat.setString(9, abean.getYearGraduated());
			stat.setString(10, abean.getSchoolCountry());
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ApplicantEducationSecSSBean addApplicantEducationSecSSBean(ApplicantEducationSecSSBean abean): "
					+ e);
			throw new JobOpportunityException("Can not add ApplicantEducationSecSSBean to DB.", e);
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
	public static ApplicantEducationSecSSBean getApplicantEducationSecSSBeanBySin(String sin)
	throws JobOpportunityException {
		ApplicantEducationSecSSBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_app_education_sec_ss(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, sin);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				eBean = createApplicantEducationSecSSBean(rs);
			}
		}
		catch (SQLException e) {
			System.err.println("ApplicantEducationSecSSBean getApplicantEducationSecSSBeanBySin(String sin): "
					+ e);
			throw new JobOpportunityException("Can not extract ApplicantNLESDPermanentExperienceBean from DB.", e);
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
	public static void updateApplicantEducationSecSSBean(ApplicantEducationSecSSBean abean)
		throws JobOpportunityException {
			Connection con = null;
			CallableStatement stat = null;
			try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(true);
				stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.update_app_education_sec_ss(?,?,?,?,?,?,?,?,?,?); end;");
				stat.setString(1, abean.getSin());
				stat.setString(2, abean.getEducationLevel());
				stat.setString(3, abean.getSchoolName());
				stat.setString(4, abean.getSchoolCity());
				stat.setString(5, abean.getSchoolProvince());
				stat.setString(6, abean.getYearsCompleted());
				stat.setString(7, abean.getGraduated());
				stat.setInt(8, abean.getId());
				stat.setString(9, abean.getYearGraduated());
				stat.setString(10, abean.getSchoolCountry());
				stat.execute();
			}
			catch (SQLException e) {
				e.printStackTrace();
				try {
					con.rollback();
				}
				catch (Exception ex) {}
				System.err.println("void updateApplicantEducationSecSSBean(ApplicantEducationSecSSBean abean): "
						+ e);
				throw new JobOpportunityException("Can not add ApplicantEducationSecSSBean to DB.", e);
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
	public static ApplicantEducationSecSSBean createApplicantEducationSecSSBean(ResultSet rs) {
		ApplicantEducationSecSSBean aBean = null;
		try {
			aBean = new ApplicantEducationSecSSBean();
			aBean.setId(rs.getInt("ID"));
			aBean.setEducationLevel(rs.getString("EDUCATIONLEVEL"));
			aBean.setSin(rs.getString("SIN"));
			aBean.setSchoolName(rs.getString("SCHOOLNAME"));
			aBean.setSchoolCity(rs.getString("SCHOOLCITY"));
			aBean.setSchoolProvince(rs.getString("SCHOOLPROVINCE"));
			aBean.setYearsCompleted(rs.getString("YEARSCOMPLETED"));
			aBean.setGraduated(rs.getString("GRADUATED"));
			aBean.setYearGraduated(rs.getString("YEARGRADUATED"));
			aBean.setSchoolCountry(rs.getString("SCHOOLCOUNTRY"));
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}	
}
