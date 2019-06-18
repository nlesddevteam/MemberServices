package com.esdnl.personnel.v2.model.availability.bean;

import java.util.Date;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.PersonnelException;
import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.awsd.school.SchoolException;
import com.esdnl.personnel.v2.model.availability.constant.ReasonCodeConstant;

public class EmployeeAvailabilityBean {

	private String SIN;
	private Date startDate;
	private Date endDate;
	private int bookedBy;
	private int bookedWhere;
	private int notAvailableBy;
	private int reasonCode;

	public String getSIN() {

		return SIN;
	}

	public void setSIN(String newSIN) {

		SIN = newSIN;
	}

	public Date getStartDate() {

		return startDate;
	}

	public void setStartDate(Date newStartDate) {

		startDate = newStartDate;
	}

	public Date getEndDate() {

		return endDate;
	}

	public void setEndDate(Date newEndDate) {

		endDate = newEndDate;
	}

	public int getBookedById() {

		return bookedBy;
	}

	public Personnel getBookedBy() {

		Personnel tmp = null;
		if (bookedBy > 0) {
			try {
				tmp = PersonnelDB.getPersonnel(bookedBy);
			}
			catch (PersonnelException e) {
				tmp = null;
			}
		}

		return tmp;
	}

	public void setBookedBy(int newBookedBy) {

		bookedBy = newBookedBy;
	}

	public int getBookedWhereId() {

		return this.bookedWhere;
	}

	public School getBookedWhere() {

		School tmp = null;
		if (bookedWhere > 0) {
			try {
				tmp = SchoolDB.getSchool(bookedWhere);
			}
			catch (SchoolException e) {
				tmp = null;
			}
		}

		return tmp;
	}

	public void setBookedWhere(int newBookedWhere) {

		bookedWhere = newBookedWhere;
	}

	public int getNotAvailableById() {

		return this.notAvailableBy;
	}

	public Personnel getNotAvailableBy() {

		Personnel tmp = null;
		if (notAvailableBy > 0) {
			try {
				tmp = PersonnelDB.getPersonnel(notAvailableBy);
			}
			catch (PersonnelException e) {
				tmp = null;
			}
		}

		return tmp;
	}

	public void setNotAvailableBy(int newNotAvailableBy) {

		notAvailableBy = newNotAvailableBy;
	}

	public String getReason() {

		ReasonCodeConstant reason = ReasonCodeConstant.get(getReasonCode());
		return ((reason != null) ? reason.getDescription() : "NOT RECORDED!");
	}

	public void setReason(String newReason) {

	}

	public int getReasonCode() {

		return this.reasonCode;
	}

	public void setReasonCode(int reasonCode) {

		this.reasonCode = reasonCode;
	}

	public boolean isBooked() {

		return (this.getBookedById() > 0);
	}

	public boolean isNotAvailable() {

		return (this.getNotAvailableById() > 0);
	}

	public boolean isAvailable() {

		return (!isBooked() && !isNotAvailable());
	}

	public boolean isFutureBooking(Date viewDate) {

		return (this.isBooked() && (this.getStartDate().compareTo(viewDate) > 0));
	}
}