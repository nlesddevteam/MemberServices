package com.esdnl.h1n1.bean;

import com.awsd.school.School;
import com.awsd.school.bean.SchoolStatsBean;
import com.esdnl.h1n1.constant.Grade;

public class SchoolGradeConsentDataBean {

	private School school;
	private Grade grade;
	private int consented;
	private int refused;
	private int vaccinated;
	private int total;
	private SchoolStatsBean stats;

	public School getSchool() {

		return school;
	}

	public void setSchool(School school) {

		this.school = school;
	}

	public Grade getGrade() {

		return grade;
	}

	public void setGrade(Grade grade) {

		this.grade = grade;
	}

	public int getConsented() {

		return consented;
	}

	public void setConsented(int consented) {

		this.consented = consented;
	}

	public int getRefused() {

		return refused;
	}

	public void setRefused(int refused) {

		this.refused = refused;
	}

	public int getVaccinated() {

		return vaccinated;
	}

	public void setVaccinated(int vaccinated) {

		this.vaccinated = vaccinated;
	}

	public int getTotal() {

		return total;
	}

	public void setTotal(int total) {

		this.total = total;
	}

	public SchoolStatsBean getStats() {

		return stats;
	}

	public void setStats(SchoolStatsBean stats) {

		this.stats = stats;
	}

	public double getGradeStudents() {

		switch (this.grade.getId()) {
		case 1:
			return stats.getGrade0Total();
		case 2:
			return stats.getGrade1Total();
		case 3:
			return stats.getGrade2Total();
		case 4:
			return stats.getGrade3Total();
		case 5:
			return stats.getGrade4Total();
		case 6:
			return stats.getGrade5Total();
		case 7:
			return stats.getGrade6Total();
		case 8:
			return stats.getGrade7Total();
		case 9:
			return stats.getGrade8Total();
		case 10:
			return stats.getGrade9Total();
		case 11:
			return stats.getGrade10Total();
		case 12:
			return stats.getGrade11Total();
		case 13:
			return stats.getGrade12Total();
		default:
			return 0.0;
		}
	}

	public double getConsentedAverage() {

		return this.consented / this.getGradeStudents() * 100.0;
	}

	public double getRefusedAverage() {

		return this.refused / this.getGradeStudents() * 100.0;
	}

	public double getVaccinatedAverage() {

		return this.vaccinated / this.getGradeStudents() * 100.0;
	}

	public double getOverallAverage() {

		return this.getTotal() / this.getGradeStudents() * 100.0;
	}

}
