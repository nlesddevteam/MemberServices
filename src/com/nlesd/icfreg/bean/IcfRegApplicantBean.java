package com.nlesd.icfreg.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.nlesd.icfreg.constants.IcfRegistrationStatusConstant;

public class IcfRegApplicantBean implements Serializable {
	private static final long serialVersionUID = -844016122427000813L;
	private int icfAppId;
	private String icfAppEmail;
	private String icfAppFullName;
	private int icfAppSchool;
	private String icfAppGuaFullName;
	private String icfAppContact1;
	private String icfAppContact2;
	private Date icfAppDateSubmitted;
	private int icfAppStatus;
	private int icfAppRegPer;
	private String icfAppSchoolName;
	public int getIcfAppId() {
		return icfAppId;
	}
	public void setIcfAppId(int icfAppId) {
		this.icfAppId = icfAppId;
	}
	public String getIcfAppEmail() {
		return icfAppEmail;
	}
	public void setIcfAppEmail(String icfAppEmail) {
		this.icfAppEmail = icfAppEmail;
	}
	public int getIcfAppSchool() {
		return icfAppSchool;
	}
	public void setIcfAppSchool(int icfAppSchool) {
		this.icfAppSchool = icfAppSchool;
	}
	public String getIcfAppContact1() {
		return icfAppContact1;
	}
	public void setIcfAppContact1(String icfAppContact1) {
		this.icfAppContact1 = icfAppContact1;
	}
	public String getIcfAppContact2() {
		return icfAppContact2;
	}
	public void setIcfAppContact2(String icfAppContact2) {
		this.icfAppContact2 = icfAppContact2;
	}
	public Date getIcfAppDateSubmitted() {
		return icfAppDateSubmitted;
	}
	public void setIcfAppDateSubmitted(Date icfAppDateSubmitted) {
		this.icfAppDateSubmitted = icfAppDateSubmitted;
	}
	public int getIcfAppStatus() {
		return icfAppStatus;
	}
	public void setIcfAppStatus(int icfAppStatus) {
		this.icfAppStatus = icfAppStatus;
	}
	public int getIcfAppRegPer() {
		return icfAppRegPer;
	}
	public String getIcfAppFullName() {
		return icfAppFullName;
	}
	public void setIcfAppFullName(String icfAppFullName) {
		this.icfAppFullName = icfAppFullName;
	}
	public String getIcfAppGuaFullName() {
		return icfAppGuaFullName;
	}
	public void setIcfAppGuaFullName(String icfAppGuaFullName) {
		this.icfAppGuaFullName = icfAppGuaFullName;
	}
	public void setIcfAppRegPer(int icfAppRegPer) {
		this.icfAppRegPer = icfAppRegPer;
	}
	public String getIcfAppSchoolName() {
		return icfAppSchoolName;
	}
	public void setIcfAppSchoolName(String icfAppSchoolName) {
		this.icfAppSchoolName = icfAppSchoolName;
	}
	public String getIcfAppDateSubmittedFormatted() {
		if(this.icfAppDateSubmitted!= null){
			return new SimpleDateFormat("MMMM dd,yyyy hh:mm:ss aa").format(this.icfAppDateSubmitted);
		}else{
			return "";
		}
	}
	public String getIcfAppDateSubmittedFormattedShortDate() {
		if(this.icfAppDateSubmitted!= null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.icfAppDateSubmitted);
		}else{
			return "";
		}
	}
	public String getIcfAppDateSubmittedFormattedShortTime() {
		if(this.icfAppDateSubmitted!= null){
			return new SimpleDateFormat("hh:mm;ss aa").format(this.icfAppDateSubmitted);
		}else{
			return "";
		}
	}
	public String getApplicantStatusString() {
		return IcfRegistrationStatusConstant.get(this.icfAppStatus).getDescription();
	}
	public String toXml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<APPID>").append(this.icfAppId).append("</APPID>");
		sb.append("<APPEMAIL>").append(this.icfAppEmail).append("</APPEMAIL>");
		sb.append("<APPFULLNAME>").append(this.icfAppFullName).append("</APPFULLNAME>");
		sb.append("<APPSCHOOL>").append(this.icfAppSchool).append("</APPSCHOOL>");
		sb.append("<APPGUAFULLNAME>").append(this.icfAppGuaFullName).append("</APPGUAFULLNAME>");
		sb.append("<APPCONTACT1>").append(this.icfAppContact1).append("</APPCONTACT1>");
		sb.append("<APPCONTACT2>").append(this.icfAppContact2).append("</APPCONTACT2>");
		sb.append("<APPDATESUBMITTED>").append(this.getIcfAppDateSubmittedFormattedShortDate()).append("</APPDATESUBMITTED>");
		sb.append("<APPDATESUBMITTEDT>").append(this.getIcfAppDateSubmittedFormattedShortTime()).append("</APPDATESUBMITTEDT>");
		sb.append("<APPSTATUS>").append(this.icfAppStatus).append("</APPSTATUS>");
		sb.append("<APPSTATUSTEXT>").append(this.getApplicantStatusString()).append("</APPSTATUSTEXT>");
		sb.append("<APPREGPER>").append(this.icfAppRegPer).append("</APPREGPER>");
		sb.append("<APPSCHOOLNAME>").append(this.getIcfAppSchoolName()).append("</APPSCHOOLNAME>");
		return sb.toString();
	}
}
