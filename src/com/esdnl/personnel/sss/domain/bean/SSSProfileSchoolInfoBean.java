package com.esdnl.personnel.sss.domain.bean;

import java.util.Date;

import com.awsd.personnel.Personnel;
import com.awsd.school.School;
import com.esdnl.util.StringUtils;

public class SSSProfileSchoolInfoBean {

	private int profileId;
	private School school;
	private int periodLength;
	private int periodsPerDay;
	private int daysInCycle;
	private String projectedSchoolYear;
	private Personnel enteredBy;
	private Date enteredOn;

	private boolean dirty;

	public SSSProfileSchoolInfoBean() {

		school = null;
		enteredBy = null;
		enteredOn = null;

		periodLength = 0;
		periodsPerDay = 0;
		daysInCycle = 0;

		projectedSchoolYear = School.getNextSchoolYear();

		dirty = false;
	}

	public int getProfileId() {

		return profileId;
	}

	public void setProfileId(int profileId) {

		this.profileId = profileId;
	}

	public School getSchool() {

		return school;
	}

	public void setSchool(School school) {

		if (school != null && !school.equals(this.school)) {
			this.school = school;

			this.dirty = true;
		}
	}

	public int getPeriodLength() {

		return periodLength;
	}

	public void setPeriodLength(int periodLength) {

		if (this.periodLength != periodLength) {
			this.periodLength = periodLength;

			this.dirty = true;
		}
	}

	public int getPeriodsPerDay() {

		return periodsPerDay;
	}

	public void setPeriodsPerDay(int periodsPerDay) {

		if (this.periodsPerDay != periodsPerDay) {
			this.periodsPerDay = periodsPerDay;

			this.dirty = true;
		}
	}

	public int getDaysInCycle() {

		return daysInCycle;
	}

	public void setDaysInCycle(int daysInCycle) {

		if (this.daysInCycle != daysInCycle) {
			this.daysInCycle = daysInCycle;

			this.dirty = true;
		}
	}

	public String getProjectedSchoolYear() {

		return projectedSchoolYear;
	}

	public void setProjectedSchoolYear(String projectedSchoolYear) {

		if (projectedSchoolYear != null && !projectedSchoolYear.equalsIgnoreCase(this.projectedSchoolYear)) {
			this.projectedSchoolYear = projectedSchoolYear;

			this.dirty = true;
		}
	}

	public Personnel getEnteredBy() {

		return enteredBy;
	}

	public void setEnteredBy(Personnel enteredBy) {

		if (enteredBy != null && !enteredBy.equals(this.enteredBy)) {
			this.enteredBy = enteredBy;

			this.dirty = true;
		}
	}

	public Date getEnteredOn() {

		return enteredOn;
	}

	public void setEnteredOn(Date enteredOn) {

		if (enteredOn != null && !enteredOn.equals(this.enteredOn)) {
			this.enteredOn = enteredOn;

			this.dirty = true;
		}
	}

	public String getCurrentSchoolYear() {

		String sy = "";

		if (StringUtils.isEmpty(this.projectedSchoolYear))
			sy = School.getCurrentSchoolYear();
		else {
			String[] years = this.projectedSchoolYear.split("-");

			sy = Integer.toString(Integer.parseInt(years[0]) - 1) + '-' + years[0];
		}

		return sy;
	}

	public boolean isDirty() {

		return dirty;
	}

	public void setDirty(boolean dirty) {

		this.dirty = dirty;
	}

}
