package com.esdnl.personnel.jobs.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ApplicantShortlistAuditBean extends ApplicantFilterParameters {
	private String applicantName;
	private String applicantEmail;
	private int jasId;
	private String shortlistedByName;
	private Date shortlistedByDate;
	private String shortlistedReason;
	private String professionalTrainingLevel;
	private String degreeList;
	private String majorSubjectGroupList;
	private String minorSubjectGroupList;
	private String majorList;
	private String minorList;
	private String regionalPreferenceList;

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getApplicantEmail() {
		return applicantEmail;
	}

	public void setApplicantEmail(String applicantEmail) {
		this.applicantEmail = applicantEmail;
	}

	public int getJasId() {
		return jasId;
	}

	public void setJasId(int jasId) {
		this.jasId = jasId;
	}

	public String getShortlistedByName() {
		return shortlistedByName;
	}

	public void setShortlistedByName(String shortlistedByName) {
		this.shortlistedByName = shortlistedByName;
	}

	public Date getShortlistedByDate() {
		return shortlistedByDate;
	}

	public void setShortlistedByDate(Date shortlistedByDate) {
		this.shortlistedByDate = shortlistedByDate;
	}

	public String getShortlistedReason() {
		return shortlistedReason;
	}

	public void setShortlistedReason(String shortlistedReason) {
		this.shortlistedReason = shortlistedReason;
	}

	public String getProfessionalTrainingLevel() {
		if(this.professionalTrainingLevel ==  null) {
			return "None Selected";
		}else {
			return professionalTrainingLevel;
		}
	}

	public void setProfessionalTrainingLevel(String professionalTrainingLevel) {
		this.professionalTrainingLevel = professionalTrainingLevel;
	}

	public String getDegreeList() {
		if(this.degreeList ==  null) {
			return "None Selected";
		}else {
			return degreeList;
		}
	}

	public void setDegreeList(String degreeList) {
		this.degreeList = degreeList;
	}

	public String getMajorSubjectGroupList() {
		if(this.majorSubjectGroupList ==  null) {
			return "None Selected";
		}else {
			return majorSubjectGroupList;
		}
	}

	public void setMajorSubjectGroupList(String majorSubjectGroupList) {
		this.majorSubjectGroupList = majorSubjectGroupList;
	}

	public String getMinorSubjectGroupList() {
		if(this.minorSubjectGroupList ==  null) {
			return "None Selected";
		}else {
			return minorSubjectGroupList;
		}
	}

	public void setMinorSubjectGroupList(String minorSubjectGroupList) {
		this.minorSubjectGroupList = minorSubjectGroupList;
	}

	public String getMajorList() {
		if(this.majorList ==  null) {
			return "None Selected";
		}else {
			return majorList;
		}
	}

	public void setMajorList(String majorList) {
		this.majorList = majorList;
	}

	public String getMinorList() {
		if(this.minorList ==  null) {
			return "None Selected";
		}else {
			return minorList;
		}
	}

	public void setMinorList(String minorList) {
		this.minorList = minorList;
	}

	public String getRegionalPreferenceList() {
		if(this.regionalPreferenceList ==  null) {
			return "None Selected";
		}else {
			return regionalPreferenceList;
		}
		
	}

	public void setRegionalPreferenceList(String regionalPreferenceList) {
		this.regionalPreferenceList = regionalPreferenceList;
	}
	public String shortlistedByDateFormatted()
	{
		return new SimpleDateFormat("dd/MM/yyyy").format(this.shortlistedByDate);
	}
}
