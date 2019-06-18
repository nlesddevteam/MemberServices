package com.esdnl.h1n1.manager;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.personnel.Personnel;
import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.awsd.school.dao.SchoolStatsManager;
import com.esdnl.dao.DAOUtils;
import com.esdnl.h1n1.bean.ConsentDataSummaryBean;
import com.esdnl.h1n1.bean.DailySchoolDifferenceBean;
import com.esdnl.h1n1.bean.DailySchoolInfoBean;
import com.esdnl.h1n1.bean.DailySchoolReportBean;
import com.esdnl.h1n1.bean.DailySummaryBean;
import com.esdnl.h1n1.bean.H1N1Exception;
import com.esdnl.h1n1.bean.MonthlySchoolSummaryBean;
import com.esdnl.h1n1.bean.SchoolConsentDataSummaryBean;
import com.esdnl.h1n1.bean.SchoolGradeConsentDataBean;
import com.esdnl.h1n1.constant.Grade;

public class DailyReportManager {

	public static DailySchoolReportBean addDailySchoolReportBean(DailySchoolReportBean abean) throws H1N1Exception {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.h1n1_pkg.add_daily_report(?,?,?,?,?,?,?); end;");

			stat.registerOutParameter(1, OracleTypes.NUMBER);

			stat.setInt(2, abean.getReportedById());
			stat.setInt(3, abean.getSchoolId());
			stat.setDate(4, new java.sql.Date(abean.getDateAdded().getTime()));
			stat.setDouble(5, abean.getTeacherTotal());
			stat.setDouble(6, abean.getSupportStaffTotal());
			stat.setDouble(7, abean.getStudentTotal());
			stat.setString(8, abean.getAdditionalComments());

			stat.execute();

			abean.setReportId(((OracleCallableStatement) stat).getInt(1));

		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("ApplicantOtherInformationBean addApplicantOtherInformationBean(ApplicantOtherInformationBean abean): "
					+ e);
			throw new H1N1Exception("Can not add ApplicantOtherInformationBean to DB.", e);
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

	public static DailySchoolReportBean[] getDailySchoolReportBeans(School s) throws H1N1Exception {

		Vector reports = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			reports = new Vector(31);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.h1n1_pkg.get_monthly_daily_reports(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, s.getSchoolID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				reports.add(createDailySchoolReportBean(rs));
		}
		catch (SQLException e) {
			System.err.println("DailySchoolReportBean[] getDailySchoolReportBeans(School s): " + e);
			throw new H1N1Exception("Can not extract DailySchoolReportBean from DB.", e);
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

		return (DailySchoolReportBean[]) reports.toArray(new DailySchoolReportBean[0]);
	}

	public static DailySchoolReportBean[] getDailySchoolReportBeans(School s, java.util.Date date) throws H1N1Exception {

		Vector reports = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			reports = new Vector(31);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.h1n1_pkg.get_monthly_daily_reports(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, s.getSchoolID());
			stat.setDate(3, new java.sql.Date(date.getTime()));
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				reports.add(createDailySchoolReportBean(rs));
		}
		catch (SQLException e) {
			System.err.println("DailySchoolReportBean[] getDailySchoolReportBeans(School s, java.util.Date date): " + e);
			throw new H1N1Exception("Can not extract DailySchoolReportBean from DB.", e);
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

		return (DailySchoolReportBean[]) reports.toArray(new DailySchoolReportBean[0]);
	}

	public static MonthlySchoolSummaryBean getMonthlySchoolSummaryBean(School s) throws H1N1Exception {

		MonthlySchoolSummaryBean summary = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.h1n1_pkg.get_monthly_summary(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, s.getSchoolID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				summary = createMonthlySchoolSummaryBean(rs);
		}
		catch (SQLException e) {
			System.err.println("MonthlySchoolSummaryBean getMonthlySchoolSummaryBean(School s): " + e);
			throw new H1N1Exception("Can not extract MonthlySchoolSummaryBean from DB.", e);
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

		return summary;
	}

	public static MonthlySchoolSummaryBean getMonthlySchoolSummaryBean(School s, java.util.Date date)
			throws H1N1Exception {

		MonthlySchoolSummaryBean summary = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.h1n1_pkg.get_monthly_summary(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, s.getSchoolID());
			stat.setDate(3, new java.sql.Date(date.getTime()));
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				summary = createMonthlySchoolSummaryBean(rs);
		}
		catch (SQLException e) {
			System.err.println("MonthlySchoolSummaryBean getMonthlySchoolSummaryBean(School s, java.util.Date date): " + e);
			throw new H1N1Exception("Can not extract MonthlySchoolSummaryBean from DB.", e);
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

		return summary;
	}

	public static DailySchoolDifferenceBean getDailySchoolDifferenceBean(School s, java.util.Date date)
			throws H1N1Exception {

		DailySchoolDifferenceBean difference = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		double td = 0;
		double ssd = 0;
		double sd = 0;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.h1n1_pkg.get_daily_summary_difference(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, s.getSchoolID());
			stat.setDate(3, new java.sql.Date(date.getTime()));
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				td = rs.getDouble("TEACHERS_TOTAL");
				ssd = rs.getDouble("SUPPORT_STAFF_TOTAL");
				sd = rs.getDouble("STUDENTS_TOTAL");

				if (rs.next()) {
					td -= rs.getDouble("TEACHERS_TOTAL");
					ssd -= rs.getDouble("SUPPORT_STAFF_TOTAL");
					sd -= rs.getDouble("STUDENTS_TOTAL");
				}
			}

			difference = new DailySchoolDifferenceBean();
			difference.setStudentDifference(sd);
			difference.setSupportStaffDifference(ssd);
			difference.setTeacherDifference(td);

		}
		catch (SQLException e) {
			System.err.println("DailySchoolDifferenceBean getDailySchoolDifferenceBean(School s, java.util.Date date): " + e);
			throw new H1N1Exception("Can not extract MonthlySchoolSummaryBean from DB.", e);
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

		return difference;
	}

	public static DailySchoolInfoBean[] getDailySchoolInfoBeans(java.util.Date viewDate) throws H1N1Exception {

		Vector summarys = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			summarys = new Vector(31);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.h1n1_pkg.get_daily_summmary(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setDate(2, new java.sql.Date(viewDate.getTime()));

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				summarys.add(DailyReportManager.createDailySchoolInfoBean(rs));
		}
		catch (SQLException e) {
			System.err.println("DailySchoolSummaryBean[] getDailySchoolSummaryBeans(): " + e);
			throw new H1N1Exception("Can not extract DailySchoolSummaryBean from DB.", e);
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

		return (DailySchoolInfoBean[]) summarys.toArray(new DailySchoolInfoBean[0]);
	}

	public static DailySummaryBean getDailySummaryBean(java.util.Date viewDate) throws H1N1Exception {

		DailySummaryBean summary = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.h1n1_pkg.get_daily_summmary_totals(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setDate(2, new java.sql.Date(viewDate.getTime()));

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				summary = DailyReportManager.createDailySummaryBean(rs);
		}
		catch (SQLException e) {
			System.err.println("DailySummaryBean getSummaryBeans(): " + e);
			throw new H1N1Exception("Can not extract DailySummaryBean from DB.", e);
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

		return summary;
	}

	public static DailySummaryBean[] getMonthlyTrendData(java.util.Date startDate, java.util.Date endDate)
			throws H1N1Exception {

		ArrayList<DailySummaryBean> beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			beans = new ArrayList<DailySummaryBean>(31);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.h1n1_pkg.get_monthly_trend_data(?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setDate(2, new java.sql.Date(startDate.getTime()));
			stat.setDate(3, new java.sql.Date(endDate.getTime()));

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(DailyReportManager.createDailySummaryBean(rs));
		}
		catch (SQLException e) {
			System.err.println("DailySummaryBean[] getMonthlyTrendData(java.util.Date viewDate): " + e);
			throw new H1N1Exception("Can not extract DailySummaryBean from DB.", e);
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

		return (DailySummaryBean[]) beans.toArray(new DailySummaryBean[0]);
	}

	public static void addSchoolGradeConsentDataBean(Personnel who, SchoolGradeConsentDataBean abean)
			throws H1N1Exception {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.h1n1_pkg.add_grade_consent_data(?,?,?,?,?,?); end;");

			stat.setInt(1, who.getPersonnelID());
			stat.setInt(2, abean.getSchool().getSchoolID());
			stat.setInt(3, abean.getGrade().getId());
			stat.setInt(4, abean.getConsented());
			stat.setInt(5, abean.getRefused());
			stat.setInt(6, abean.getVaccinated());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addSchoolGradeConsentDataBean(Personnel who, SchoolGradeConsentDataBean abean): " + e);
			throw new H1N1Exception("Can not add SchoolGradeConsentDataBean to DB.", e);
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

	public static SchoolGradeConsentDataBean[] getConsentData(School school) throws H1N1Exception {

		ArrayList<SchoolGradeConsentDataBean> beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			beans = new ArrayList<SchoolGradeConsentDataBean>(31);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.h1n1_pkg.get_consent_data_by_school(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, school.getSchoolID());

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(DailyReportManager.createSchoolGradeConsentDataBean(rs));
		}
		catch (SQLException e) {
			System.err.println("SchoolGradeConsentDataBean[] getConsentData(School school): " + e);
			throw new H1N1Exception("Can not extract SchoolGradeConsentDataBean from DB.", e);
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

		return (SchoolGradeConsentDataBean[]) beans.toArray(new SchoolGradeConsentDataBean[0]);
	}

	public static SchoolConsentDataSummaryBean[] getSchoolConsentDataSummary() throws H1N1Exception {

		ArrayList<SchoolConsentDataSummaryBean> beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			beans = new ArrayList<SchoolConsentDataSummaryBean>(122);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.h1n1_pkg.get_school_consent_summary; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(DailyReportManager.createSchoolConsentSummaryBean(rs));
		}
		catch (SQLException e) {
			System.err.println("SchoolGradeConsentDataBean[] getConsentData(School school): " + e);
			throw new H1N1Exception("Can not extract SchoolGradeConsentDataBean from DB.", e);
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

		return (SchoolConsentDataSummaryBean[]) beans.toArray(new SchoolConsentDataSummaryBean[0]);
	}

	public static SchoolConsentDataSummaryBean getSchoolConsentDataSummary(School school) throws H1N1Exception {

		SchoolConsentDataSummaryBean bean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.h1n1_pkg.get_school_consent_summary(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, school.getSchoolID());

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				bean = DailyReportManager.createSchoolConsentSummaryBean(rs);
		}
		catch (SQLException e) {
			System.err.println("SchoolGradeConsentDataBean[] getConsentData(School school): " + e);
			throw new H1N1Exception("Can not extract SchoolGradeConsentDataBean from DB.", e);
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

		return bean;
	}

	public static ConsentDataSummaryBean getConsentDataSummary() throws H1N1Exception {

		ConsentDataSummaryBean bean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.h1n1_pkg.get_consent_summary; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				bean = DailyReportManager.createConsentSummaryBean(rs);
		}
		catch (SQLException e) {
			System.err.println("ConsentDataSummaryBean getConsentDataSummary(): " + e);
			throw new H1N1Exception("Can not extract ConsentDataSummaryBean from DB.", e);
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

		return bean;
	}

	public static DailySchoolReportBean createDailySchoolReportBean(ResultSet rs) {

		DailySchoolReportBean aBean = null;
		try {
			aBean = new DailySchoolReportBean();

			aBean.setAdditionalComments(rs.getString("ADDITIONAL_COMMENTS"));
			if (rs.getDate("DATE_ADDED") != null)
				aBean.setDateAdded(new java.util.Date(rs.getDate("DATE_ADDED").getTime()));
			aBean.setReportedById(rs.getInt("REPORTED_BY"));
			aBean.setReportId(rs.getInt("REPORT_ID"));
			aBean.setSchoolId(rs.getInt("SCHOOL_ID"));
			aBean.setStudentTotal(rs.getDouble("STUDENTS_TOTAL"));
			aBean.setSupportStaffTotal(rs.getDouble("SUPPORT_STAFF_TOTAL"));
			aBean.setTeacherTotal(rs.getDouble("TEACHERS_TOTAL"));
			try {
				// These totals will not be available for daily summary view, and
				// therefore not in the recordset.
				aBean.setTotal(rs.getDouble("TOTAL"));
				aBean.setTeacherAverage(rs.getDouble("TEACHERS_AVERAGE"));
				aBean.setSupportStaffAverage(rs.getDouble("SUPPORT_STAFF_AVERAGE"));
				aBean.setStudentAverage(rs.getDouble("STUDENTS_AVERAGE"));
				aBean.setOverallAverage(rs.getDouble("OVERALL_AVERAGE"));
			}
			catch (SQLException e) {}
		}
		catch (SQLException e) {
			e.printStackTrace();
			aBean = null;
		}
		return aBean;
	}

	public static MonthlySchoolSummaryBean createMonthlySchoolSummaryBean(ResultSet rs) {

		MonthlySchoolSummaryBean aBean = null;
		try {
			aBean = new MonthlySchoolSummaryBean();

			aBean.setSchool(SchoolDB.createSchoolBean(rs));
			aBean.setStudentSummary(rs.getDouble("STUDENT_SUMMARY"));
			aBean.setSummaryDate(new java.util.Date(rs.getDate("SUMMARY_DATE").getTime()));
			aBean.setSupportStaffSummary(rs.getDouble("SUPPORT_STAFF_SUMMARY"));
			aBean.setTeacherSummary(rs.getDouble("TEACHER_SUMMARY"));
			aBean.setStudentAverage(rs.getDouble("STUDENTS_AVERAGE"));
			aBean.setSupportStaffAverage(rs.getDouble("SUPPORT_STAFF_AVERAGE"));
			aBean.setTeacherAverage(rs.getDouble("TEACHERS_AVERAGE"));
			aBean.setRecordCount(rs.getInt("RECORD_COUNT"));
			aBean.setTotalAverage(rs.getDouble("TOTAL_AVERAGE"));
		}
		catch (SQLException e) {
			e.printStackTrace();
			aBean = null;
		}
		return aBean;
	}

	public static DailySchoolInfoBean createDailySchoolInfoBean(ResultSet rs) {

		DailySchoolInfoBean sbean = new DailySchoolInfoBean();

		try {
			sbean.setSummaryDate(new java.util.Date(rs.getDate("SUMMARY_DATE").getTime()));
			sbean.setDailyReport(DailyReportManager.createDailySchoolReportBean(rs));
			sbean.setSchool(SchoolDB.createSchoolBean(rs));
			sbean.setStats(SchoolStatsManager.createSchoolStatsBean(rs));
			try {
				sbean.getDifference();
			}
			catch (H1N1Exception e) {}
		}
		catch (SQLException e) {
			sbean = null;
		}

		return sbean;
	}

	public static DailySummaryBean createDailySummaryBean(ResultSet rs) {

		DailySummaryBean aBean = null;
		try {
			aBean = new DailySummaryBean();

			aBean.setStudentSummary(rs.getDouble("STUDENT_SUMMARY"));
			aBean.setSummaryDate(new java.util.Date(rs.getDate("SUMMARY_DATE").getTime()));
			aBean.setSupportStaffSummary(rs.getDouble("SUPPORT_STAFF_SUMMARY"));
			aBean.setTeacherSummary(rs.getDouble("TEACHER_SUMMARY"));
		}
		catch (SQLException e) {
			e.printStackTrace();
			aBean = null;
		}
		return aBean;
	}

	public static SchoolGradeConsentDataBean createSchoolGradeConsentDataBean(ResultSet rs) {

		SchoolGradeConsentDataBean aBean = null;
		try {
			aBean = new SchoolGradeConsentDataBean();

			aBean.setSchool(SchoolDB.createSchoolBean(rs));
			aBean.setGrade(Grade.get(rs.getInt("GRADE_ID")));
			aBean.setRefused(rs.getInt("REFUSED"));
			aBean.setConsented(rs.getInt("CONSENTED"));
			aBean.setVaccinated(rs.getInt("VACCINATED"));
			aBean.setTotal(rs.getInt("TOTAL"));
			aBean.setStats(SchoolStatsManager.createSchoolStatsBean(rs));
		}
		catch (SQLException e) {
			e.printStackTrace();
			aBean = null;
		}
		return aBean;
	}

	public static SchoolConsentDataSummaryBean createSchoolConsentSummaryBean(ResultSet rs) {

		SchoolConsentDataSummaryBean aBean = null;

		try {
			aBean = new SchoolConsentDataSummaryBean();

			aBean.setConsented(rs.getDouble("CONSENTED"));
			aBean.setPercent_consented(rs.getDouble("PERCENT_CONSENTED"));
			aBean.setPercent_refused(rs.getDouble("PERCENT_REFUSED"));
			aBean.setPercent_vaccinated(rs.getDouble("PERCENT_VACCINATED"));
			aBean.setRefused(rs.getDouble("REFUSED"));
			aBean.setVaccinated(rs.getDouble("VACCINATED"));
			aBean.setSchool(SchoolDB.createSchoolBean(rs));
			aBean.setTotal(rs.getDouble("TOTAL"));
			aBean.setPercent_overall(rs.getDouble("PERCENT_OVERALL"));
			aBean.setStats(SchoolStatsManager.createSchoolStatsBean(rs));
		}
		catch (SQLException e) {
			e.printStackTrace();
			aBean = null;
		}
		return aBean;
	}

	public static ConsentDataSummaryBean createConsentSummaryBean(ResultSet rs) {

		ConsentDataSummaryBean aBean = null;

		try {
			aBean = new ConsentDataSummaryBean();

			aBean.setConsented(rs.getDouble("CONSENTED"));
			aBean.setRefused(rs.getDouble("REFUSED"));
			aBean.setVaccinated(rs.getDouble("VACCINATED"));
			aBean.setTotal(rs.getDouble("TOTAL"));
		}
		catch (SQLException e) {
			e.printStackTrace();
			aBean = null;
		}
		return aBean;
	}
}
