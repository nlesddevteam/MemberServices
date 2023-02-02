package com.nlesd.icfreg.bean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IcfRegistrationPeriodBean implements Serializable {
	private static final long serialVersionUID = -844016122427000813L;
	private int icfRegPerId;
	private String icfRegPerSchoolYear;
	private Date icfRegStartDate;
	private Date icfRegEndDate;
	private int icfRegCount;
	public int getIcfRegPerId() {
		return icfRegPerId;
	}
	public void setIcfRegPerId(int icfRegPerId) {
		this.icfRegPerId = icfRegPerId;
	}
	public String getIcfRegPerSchoolYear() {
		return icfRegPerSchoolYear;
	}
	public void setIcfRegPerSchoolYear(String icfRegPerSchoolYear) {
		this.icfRegPerSchoolYear = icfRegPerSchoolYear;
	}
	public Date getIcfRegStartDate() {
		return icfRegStartDate;
	}
	public void setIcfRegStartDate(Date icfRegStartDate) {
		this.icfRegStartDate = icfRegStartDate;
	}
	public Date getIcfRegEndDate() {
		return icfRegEndDate;
	}
	public void setIcfRegEndDate(Date icfRegEndDate) {
		this.icfRegEndDate = icfRegEndDate;
	}
	public String toXml() {
		DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
		StringBuilder sb = new StringBuilder();
		sb.append("<REGPERID>").append(this.icfRegPerId).append("</REGPERID>");
		sb.append("<REGPERSCHOOLYEAR>").append(this.icfRegPerSchoolYear).append("</REGPERSCHOOLYEAR>");
		sb.append("<REGPERSTARTDATE>").append(df.format(this.icfRegStartDate)).append("</REGPERSTARTDATE>");
		sb.append("<REGPERENDDATE>").append(df.format(this.icfRegEndDate)).append("</REGPERENDDATE>");
		sb.append("<REGPERSTARTDATET>").append(this.getStartDateFormatted()).append("</REGPERSTARTDATET>");
		sb.append("<REGPERENDDATET>").append(this.getEndDateFormatted()).append("</REGPERENDDATET>");
		sb.append("<REGPERSTATUS>").append(this.isPast()).append("</REGPERSTATUS>");
		sb.append("<REGPERCOUNT>").append(this.icfRegCount).append("</REGPERCOUNT>");
		return sb.toString();
	}
	public String isPast() {
		Date newdate = new Date();
		String status="";
		if(this.icfRegStartDate.after(newdate)) {
			status="NOT STARTED";
		}else if(this.icfRegEndDate.before(newdate)) {
			status="CLOSED";
		}else if(this.icfRegStartDate.before(newdate) && this.icfRegEndDate.after(newdate)) {
			status="OPENED";
		}
		else {
			status="";
		}
		return status;
	}
	public String getStartDateFormatted() {
		if(this.icfRegStartDate!= null){
			return new SimpleDateFormat("yyyy/MM/dd @ hh:mm aa").format(this.icfRegStartDate);
			
			
		}else{
			return "";
		}
	}
	public String getEndDateFormatted() {
		if(this.icfRegEndDate!= null){
			return new SimpleDateFormat("yyyy/MM/dd @ hh:mm aa").format(this.icfRegEndDate);
		}else{
			return "";
		}
	}
	public int getIcfRegCount() {
		return icfRegCount;
	}
	public void setIcfRegCount(int icfRegCount) {
		this.icfRegCount = icfRegCount;
	}
	
}
