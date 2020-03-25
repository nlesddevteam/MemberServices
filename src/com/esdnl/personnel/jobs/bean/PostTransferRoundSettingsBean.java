package com.esdnl.personnel.jobs.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PostTransferRoundSettingsBean {
	//public static final String DATE_FORMAT = "dd/MM/yyyy";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	private int ptrStatus;
	private Date ptrStartDate;
	private Date ptrEndDate;
	private int ptrPositionLimit;
	public int getPtrStatus() {
		return ptrStatus;
	}
	public void setPtrStatus(int ptrStatus) {
		this.ptrStatus = ptrStatus;
	}
	public Date getPtrStartDate() {
		return ptrStartDate;
	}
	public void setPtrStartDate(Date ptrStartDate) {
		this.ptrStartDate = ptrStartDate;
	}
	public Date getPtrEndDate() {
		return ptrEndDate;
	}
	public void setPtrEndDate(Date ptrEndDate) {
		this.ptrEndDate = ptrEndDate;
	}
	public int getPtrPositionLimit() {
		return ptrPositionLimit;
	}
	public void setPtrPositionLimit(int ptrPositionLimit) {
		this.ptrPositionLimit = ptrPositionLimit;
	}
	public String getPtrStartDateFormatted() {

		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

		return sdf.format(ptrStartDate);
	}
	public String getPtrEndDateFormatted() {

		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

		return sdf.format(ptrEndDate);
	}
	public boolean IsActive() {
		if(this.getPtrStatus() == 1) {
			//now check the dates to make sure current date in range
			Date today = new Date();
			if(today.after(this.ptrStartDate) && today.before(this.ptrEndDate)) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
}
