package com.awsd.school.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.awsd.school.SchoolException;
import com.awsd.school.bean.SchoolStatsBean;
import com.awsd.school.bean.SchoolStatsSummaryBean;
import com.esdnl.dao.DAOUtils;

public class SchoolStatsManager {

	public static SchoolStatsBean addSchoolStatsBean(SchoolStatsBean abean) throws SchoolException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.schools_pkg.add_school_stats(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");

			stat.setInt(1, abean.getSchool().getSchoolID());
			stat.setDouble(2, abean.getNumberTeachers());
			stat.setDouble(3, abean.getNumberSupportStaff());
			stat.setDouble(4, abean.getNumberStudents());
			stat.setDouble(5, abean.getGrade0Total());
			stat.setDouble(6, abean.getGrade1Total());
			stat.setDouble(7, abean.getGrade2Total());
			stat.setDouble(8, abean.getGrade3Total());
			stat.setDouble(9, abean.getGrade4Total());
			stat.setDouble(10, abean.getGrade5Total());
			stat.setDouble(11, abean.getGrade6Total());
			stat.setDouble(12, abean.getGrade7Total());
			stat.setDouble(13, abean.getGrade8Total());
			stat.setDouble(14, abean.getGrade9Total());
			stat.setDouble(15, abean.getGrade10Total());
			stat.setDouble(16, abean.getGrade11Total());
			stat.setDouble(17, abean.getGrade12Total());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("SchoolStatsBean addSchoolStatsBean(SchoolStatsBean abean): " + e);
			throw new SchoolException("Can not add SchoolStatsBean to DB.");
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

	public static SchoolStatsBean getSchoolStatsBean(School s) throws SchoolException {

		SchoolStatsBean stats = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_school_stats(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, s.getSchoolID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				stats = createSchoolStatsBean(rs);
		}
		catch (SQLException e) {
			System.err.println("SchoolStatsBean getSchoolStatsBeans(School s): " + e);
			throw new SchoolException("Can not extract SchoolStatsBean from DB.");
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

		return stats;
	}

	public static SchoolStatsSummaryBean getSchoolStatsSummaryBean() throws SchoolException {

		SchoolStatsSummaryBean stats = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_school_stats_summary; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				stats = createSchoolStatsSummaryBean(rs);
		}
		catch (SQLException e) {
			System.err.println("SchoolStatsSummaryBean getSchoolStatsSummaryBean(): " + e);
			throw new SchoolException("Can not extract SchoolStatsSummaryBean from DB.");
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

		return stats;
	}

	public static SchoolStatsBean createSchoolStatsBean(ResultSet rs) {

		SchoolStatsBean aBean = null;
		try {
			aBean = new SchoolStatsBean();

			aBean.setSchool(SchoolDB.createSchoolBean(rs));
			aBean.setNumberStudents(rs.getDouble("STUDENTS"));
			aBean.setNumberSupportStaff(rs.getDouble("SUPPORT_STAFF"));
			aBean.setNumberTeachers(rs.getDouble("TEACHERS"));
			aBean.setGrade0Total(rs.getDouble("GRADE_0"));
			aBean.setGrade1Total(rs.getDouble("GRADE_1"));
			aBean.setGrade2Total(rs.getDouble("GRADE_2"));
			aBean.setGrade3Total(rs.getDouble("GRADE_3"));
			aBean.setGrade4Total(rs.getDouble("GRADE_4"));
			aBean.setGrade5Total(rs.getDouble("GRADE_5"));
			aBean.setGrade6Total(rs.getDouble("GRADE_6"));
			aBean.setGrade7Total(rs.getDouble("GRADE_7"));
			aBean.setGrade8Total(rs.getDouble("GRADE_8"));
			aBean.setGrade9Total(rs.getDouble("GRADE_9"));
			aBean.setGrade10Total(rs.getDouble("GRADE_10"));
			aBean.setGrade11Total(rs.getDouble("GRADE_11"));
			aBean.setGrade12Total(rs.getDouble("GRADE_12"));
		}
		catch (SQLException e) {
			e.printStackTrace();
			aBean = null;
		}
		return aBean;
	}

	public static SchoolStatsSummaryBean createSchoolStatsSummaryBean(ResultSet rs) {

		SchoolStatsSummaryBean aBean = null;
		try {
			aBean = new SchoolStatsSummaryBean();

			aBean.setReportCount(rs.getInt("NUM_SCHOOLS"));
			aBean.setTotalStudents(rs.getDouble("TOTAL_STUDENTS"));
			aBean.setTotalSupportStaff(rs.getDouble("TOTAL_SUPPORT_STAFF"));
			aBean.setTotalTeachers(rs.getDouble("TOTAL_TEACHERS"));
		}
		catch (SQLException e) {
			e.printStackTrace();
			aBean = null;
		}
		return aBean;
	}
}
