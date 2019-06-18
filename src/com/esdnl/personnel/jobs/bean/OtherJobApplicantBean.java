package com.esdnl.personnel.jobs.bean;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
public class OtherJobApplicantBean implements Serializable {

	private static final long serialVersionUID = 5304511837525367873L;
	private String sin;
	private Integer jobId;
	private String extraInfo;
	private String shortListed;
	private Date appliedDate;
	private Date declinedInterviewDate;
	public String getSin() {
		return sin;
	}
	public void setSin(String sin) {
		this.sin = sin;
	}
	public Integer getJobId() {
		return jobId;
	}
	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}
	public String getExtraInfo() {
		return extraInfo;
	}
	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}
	public String getShortListed() {
		return shortListed;
	}
	public void setShortListed(String shortListed) {
		this.shortListed = shortListed;
	}
	public Date getAppliedDate() {
		return appliedDate;
	}
	public void setAppliedDate(Date appliedDate) {
		this.appliedDate = appliedDate;
	}
	public Date getDeclinedInterviewDate() {
		return declinedInterviewDate;
	}
	public void setDeclinedInterviewDate(Date declinedInterviewDate) {
		this.declinedInterviewDate = declinedInterviewDate;
	}
	public String getAppliedDateFormatted() {
		String formatted="";
		if(!(this.appliedDate == null)){
			formatted = new SimpleDateFormat("dd/MM/yyyy").format(this.appliedDate);
		}
		return formatted;
	}
	public String getDeclinedInterviewDateFormatted() {
		String formatted="";
		if(!(this.declinedInterviewDate == null)){
			formatted = new SimpleDateFormat("dd/MM/yyyy").format(this.declinedInterviewDate);
		}
		return formatted;
	}
}
