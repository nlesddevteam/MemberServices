package com.esdnl.h1n1.bean;

import com.awsd.school.School;
import com.awsd.school.bean.SchoolStatsBean;

public class SchoolConsentDataSummaryBean {

	private School school;
	private SchoolStatsBean stats;
	private double consented;
	private double percent_consented;
	private double refused;
	private double percent_refused;
	private double vaccinated;
	private double percent_vaccinated;
	private double total;
	private double percent_overall;

	public School getSchool() {

		return school;
	}

	public void setSchool(School school) {

		this.school = school;
	}

	public double getConsented() {

		return consented;
	}

	public void setConsented(double consented) {

		this.consented = consented;
	}

	public double getPercent_consented() {

		return percent_consented;
	}

	public void setPercent_consented(double percent_consented) {

		this.percent_consented = percent_consented;
	}

	public double getRefused() {

		return refused;
	}

	public void setRefused(double refused) {

		this.refused = refused;
	}

	public double getPercent_refused() {

		return percent_refused;
	}

	public void setPercent_refused(double percent_refused) {

		this.percent_refused = percent_refused;
	}

	public double getVaccinated() {

		return vaccinated;
	}

	public void setVaccinated(double vaccinated) {

		this.vaccinated = vaccinated;
	}

	public double getPercent_vaccinated() {

		return percent_vaccinated;
	}

	public void setPercent_vaccinated(double percent_vaccinated) {

		this.percent_vaccinated = percent_vaccinated;
	}

	public double getTotal() {

		return total;
	}

	public void setTotal(double total) {

		this.total = total;
	}

	public SchoolStatsBean getStats() {

		return stats;
	}

	public void setStats(SchoolStatsBean stats) {

		this.stats = stats;
	}

	public double getPercent_overall() {

		return percent_overall;
	}

	public void setPercent_overall(double percent_overall) {

		this.percent_overall = percent_overall;
	}

}
