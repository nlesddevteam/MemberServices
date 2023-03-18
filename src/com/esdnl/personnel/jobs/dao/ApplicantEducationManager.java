package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantEducationBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;

public class ApplicantEducationManager {

	public static ApplicantEducationBean addApplicantEducationBean(ApplicantEducationBean abean)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.add_applicant_edu(?,?,?,?,?,?,?,?,?,?,?); end;");

			stat.setString(1, abean.getSIN());
			stat.setDate(2, new java.sql.Date(abean.getFrom().getTime()));
			stat.setDate(3, new java.sql.Date(abean.getTo().getTime()));
			stat.setString(4, abean.getProgramFacultyName());
			stat.setInt(5, abean.getMajor());
			stat.setInt(6, abean.getNumberMajorCourses());
			stat.setInt(7, abean.getMinor());
			stat.setInt(8, abean.getNumberMinorCourses());
			stat.setString(9, abean.getDegreeConferred());
			stat.setString(10, abean.getInstitutionName());
			stat.setInt(11,abean.getMajor_other());
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

	public static void deleteApplicantEducationBean(int id) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.del_applicant_edu(?); end;");

			stat.setInt(1, id);

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void deleteApplicantEducationBean(int): " + e);
			throw new JobOpportunityException("Can not delete ApplicantProfileBean to DB.", e);
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

	public static ApplicantEducationBean[] getApplicantEducationBeans(String sin) throws JobOpportunityException {

		Vector v_opps = null;
		ApplicantEducationBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_applicant_edu(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, sin);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantEducationBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("ApplicantEducationBean[] getApplicantEducationBeans(String sin): " + e);
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

		return (ApplicantEducationBean[]) v_opps.toArray(new ApplicantEducationBean[0]);
	}
	public static void deleteApplicantEducationNLESDBean(int id) {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.delete_edu_nlesd(?); end;");

			stat.setInt(1, id);

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("static void deleteApplicantEducationNLESDBean(int id): " + e);
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
	public static ApplicantEducationBean createApplicantEducationBean(ResultSet rs) {

		ApplicantEducationBean aBean = null;
		try {
			aBean = new ApplicantEducationBean();

			aBean.setId(rs.getInt("PK_ID"));
			aBean.setSIN(rs.getString("SIN"));
			aBean.setFrom(new java.util.Date(rs.getDate("FROM").getTime()));
			aBean.setTo(new java.util.Date(rs.getDate("TO").getTime()));
			aBean.setInstitutionName(rs.getString("INSTITUTION"));
			aBean.setProgramFacultyName(rs.getString("PROGRAM_FACULTY"));
			aBean.setMajor(rs.getInt("MAJOR_ID"));
			aBean.setNumberMajorCourses(rs.getInt("NUM_MAJOR_COURSES"));
			aBean.setMinor(rs.getInt("MINOR_ID"));
			aBean.setNumberMinorCourses(rs.getInt("NUM_MINOR_COURSES"));
			aBean.setDegreeConferred(rs.getString("DEGREE_ID"));
			aBean.setMajor_other(rs.getInt("MAJOR_ID_O"));
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}
}