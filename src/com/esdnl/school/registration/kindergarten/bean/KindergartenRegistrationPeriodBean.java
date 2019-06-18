package com.esdnl.school.registration.kindergarten.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.nlesd.school.bean.SchoolZoneBean;

public class KindergartenRegistrationPeriodBean {

	private int registrationId;
	private String schoolYear;
	private Date startDate;
	private Date endDate;
	private Date addressConfirmationDeadline;
	private boolean past;
	private int registrantCount;
	private int englishCount;
	private int frenchCount;
	private Collection<SchoolZoneBean> zones;

	public KindergartenRegistrationPeriodBean() {

		this.registrationId = 0;
		this.schoolYear = "";
		this.startDate = null;
		this.endDate = null;
		this.zones = new ArrayList<SchoolZoneBean>();
	}

	public int getRegistrationId() {

		return registrationId;
	}

	public void setRegistrationId(int registrationId) {

		this.registrationId = registrationId;
	}

	public String getSchoolYear() {

		return schoolYear;
	}

	public void setSchoolYear(String schoolYear) {

		this.schoolYear = schoolYear;
	}

	public Date getStartDate() {

		return startDate;
	}

	public void setStartDate(Date startDate) {

		this.startDate = startDate;
	}

	public Date getEndDate() {

		return endDate;
	}

	public void setEndDate(Date endDate) {

		this.endDate = endDate;
	}

	public Date getAddressConfirmationDeadline() {

		return addressConfirmationDeadline;
	}

	public void setAddressConfirmationDeadline(Date addressConfirmationDeadline) {

		this.addressConfirmationDeadline = addressConfirmationDeadline;
	}

	public boolean isPast() {

		return past;
	}

	public void setPast(boolean past) {

		this.past = past;
	}

	public int getRegistrantCount() {

		return registrantCount;
	}

	public void setRegistrantCount(int registrantCount) {

		this.registrantCount = registrantCount;
	}

	public int getEnglishCount() {

		return englishCount;
	}

	public void setEnglishCount(int englishCount) {

		this.englishCount = englishCount;
	}

	public int getFrenchCount() {

		return frenchCount;
	}

	public void setFrenchCount(int frenchCount) {

		this.frenchCount = frenchCount;
	}

	public Collection<SchoolZoneBean> getZones() {

		return zones;
	}

	public void setZones(Collection<SchoolZoneBean> zones) {

		this.zones = zones;
	}

	public void addZone(SchoolZoneBean zone) {

		if (this.zones == null) {
			this.zones = new ArrayList<SchoolZoneBean>();
		}

		this.zones.add(zone);
	}

	public boolean isAccessible(SchoolZoneBean zone) {

		return isAccessible(zone.getZoneId());
	}

	public boolean isAccessible(int zoneId) {

		boolean assessible = false;

		for (SchoolZoneBean z : this.zones) {
			if (z.getZoneId() == zoneId) {
				assessible = true;
				break;
			}
		}

		return assessible;
	}

	public boolean getIsAccessibleEasternZone() {

		return this.isAccessible(1);
	}

	public boolean getIsAccessibleCentralZone() {

		return this.isAccessible(2);
	}

	public boolean getIsAccessibleWesternZone() {

		return this.isAccessible(3);
	}

	public boolean getIsAccessibleLabradorZone() {

		return this.isAccessible(4);
	}

}
