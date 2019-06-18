package com.esdnl.h1n1.bean;

import java.util.Date;

import com.awsd.school.School;
import com.awsd.school.bean.SchoolStatsBean;
import com.esdnl.h1n1.manager.DailyReportManager;

public class DailySchoolInfoBean {

	private Date summaryDate;
	private School school;
	private SchoolStatsBean stats;
	private DailySchoolReportBean dailyReport;
	private DailySchoolDifferenceBean difference;

	private static final double ALERT_THRESHOLD = 10;

	public Date getSummaryDate() {

		return summaryDate;
	}

	public void setSummaryDate(Date summaryDate) {

		this.summaryDate = summaryDate;
	}

	public School getSchool() {

		return school;
	}

	public void setSchool(School school) {

		this.school = school;
	}

	public SchoolStatsBean getStats() {

		return stats;
	}

	public void setStats(SchoolStatsBean stats) {

		this.stats = stats;
	}

	public DailySchoolReportBean getDailyReport() {

		return dailyReport;
	}

	public void setDailyReport(DailySchoolReportBean dailyReport) {

		this.dailyReport = dailyReport;
	}

	public double getTeacherPercentage() {

		return getDailyReport().getTeacherTotal() / getStats().getNumberTeachers() * 100.0;
	}

	public boolean isTeacherAlert() {

		return (getTeacherPercentage() > ALERT_THRESHOLD);
	}

	public double getSupportStaffPercentage() {

		return getDailyReport().getSupportStaffTotal() / getStats().getNumberSupportStaff() * 100.0;
	}

	public boolean isSupportStaffAlert() {

		return (getSupportStaffPercentage() > ALERT_THRESHOLD);
	}

	public double getStudentPercentage() {

		return getDailyReport().getStudentTotal() / getStats().getNumberStudents() * 100.0;
	}

	public boolean isStudentAlert() {

		return (getStudentPercentage() > ALERT_THRESHOLD);
	}

	public double getOverallPercentage() {

		return getDailyReport().getTotal() / getStats().getTotal() * 100.0;
	}

	public boolean isAlert() {

		return (getOverallPercentage() > ALERT_THRESHOLD);
	}

	public DailySchoolDifferenceBean getDifference() throws H1N1Exception {

		if (this.difference == null)
			difference = DailyReportManager.getDailySchoolDifferenceBean(this.school, this.summaryDate);

		return this.difference;
	}

}
