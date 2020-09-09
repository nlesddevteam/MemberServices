package com.esdnl.personnel.jobs.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.RecommendationManager;
import com.esdnl.util.StringUtils;

public class ApplicantProfileBean implements Comparable<ApplicantProfileBean>, Cloneable, Serializable {

	private static final long serialVersionUID = -187442525853871304L;

	public static final long STALE_PERIOD_DAYS = 180;

	private String email;
	private String password;
	private String surname;
	private String firstname;
	private String middlename;
	private String maidenname;
	private String sin; // now used as a uid, does NOT have to be an actual SIN,
	// could be a timestamp.
	private String address1;
	private String address2;
	private String province;
	private String country;
	private String postalcode;
	private String homephone;
	private String workphone;
	private String cellphone;
	private String sin2; // this will be the applicants sin number, which is
	// retrieved on hiring.
	private Date dob;

	// used when applicant is returns as part of job applicants list or sublist
	// applicants list
	private Date applied_date;

	private Date modifiedDate;

	//used by filter
	private double senority;
	private ApplicantEsdExperienceBean esdExp;
	//used to store minors and majors for sublist
	private String majorsList;
	private String minorsList;
	private String profileType;
	private boolean profileVerified;
	private ApplicantVerificationBean verificationBean;
	private TeacherRecommendationBean mostRecentAcceptedRecommendation;
	private boolean isDeleted;

	public ApplicantProfileBean() {

		email = null;
		password = null;
		surname = null;
		firstname = null;
		middlename = null;
		maidenname = null;
		sin = null;
		address1 = null;
		address2 = null;
		province = null;
		country = null;
		postalcode = null;
		homephone = null;
		workphone = null;
		cellphone = null;
		sin2 = null;
		dob = null;

		applied_date = null;
		modifiedDate = null;

		senority = 0;
		profileType = "T";
		profileVerified = false;
		verificationBean = null;
		mostRecentAcceptedRecommendation = null;
		isDeleted=false;
	}

	public ApplicantProfileBean(ApplicantProfileBean copy) {

		email = copy.email;
		password = copy.password;
		surname = copy.surname;
		firstname = copy.firstname;
		middlename = copy.middlename;
		maidenname = copy.maidenname;
		sin = copy.sin;
		address1 = copy.address1;
		address2 = copy.address2;
		province = copy.province;
		country = copy.country;
		postalcode = copy.postalcode;
		homephone = copy.homephone;
		workphone = copy.workphone;
		cellphone = copy.cellphone;
		sin2 = copy.sin2;

		if (copy.dob != null)
			dob = (Date) copy.dob.clone();

		if (copy.applied_date != null)
			applied_date = (Date) copy.applied_date.clone();
		else
			applied_date = null;

		if (copy.modifiedDate != null)
			modifiedDate = (Date) copy.modifiedDate.clone();
		else
			modifiedDate = null;

		this.senority = copy.senority;
		profileType = copy.profileType;
		profileVerified = copy.profileVerified;
		verificationBean = copy.verificationBean;
		mostRecentAcceptedRecommendation = copy.mostRecentAcceptedRecommendation;
		isDeleted=copy.isDeleted;
	}

	public String getEmail() {

		return this.email;
	}

	public void setEmail(String email) {

		this.email = email;
	}

	public String getPassword() {

		return this.password;
	}

	public void setPassword(String password) {

		this.password = password;
	}

	public String getSurname() {

		return this.surname;
	}

	public void setSurname(String surname) {

		this.surname = surname;
	}

	public String getFirstname() {

		return this.firstname;
	}

	public void setFirstname(String firstname) {

		this.firstname = firstname;
	}

	public String getMiddlename() {

		return this.middlename;
	}

	public void setMiddlename(String middlename) {

		this.middlename = middlename;
	}

	public String getMaidenname() {

		return this.maidenname;
	}

	public void setMaidenname(String maidenname) {

		this.maidenname = maidenname;
	}

	public String getFullName() {

		return this.surname + (!StringUtils.isEmpty(this.maidenname) ? "(nee " + this.maidenname + ")" : "") + ", "
				+ this.firstname + (!StringUtils.isEmpty(this.middlename) ? " " + this.middlename : "");
	}

	public String getFullNameReverse() {

		return this.firstname + (!StringUtils.isEmpty(this.middlename) ? " " + this.middlename : "") + " " + this.surname;
	}

	public String getSIN() {

		return this.sin;
	}

	public void setSIN(String sin) {

		this.sin = sin;
	}

	public String getUID() {

		return this.sin;
	}

	public String getSIN2() {

		return this.sin2;
	}

	public String getSIN2Unformatted() {

		if (!org.apache.commons.lang.StringUtils.isEmpty(this.sin2)) {
			return this.sin2.replaceAll("-", "").replaceAll(" ", "");
		}
		else
			return "";
	}

	public void setSIN2(String sin2) {

		this.sin2 = sin2;
	}

	public String getComparableUID() {

		return this.getFullName() + ", " + this.getUID();
	}

	public String getAddress1() {

		return this.address1;
	}

	public void setAddress1(String address1) {

		this.address1 = address1;
	}

	public String getAddress2() {

		return this.address2;
	}

	public void setAddress2(String address2) {

		this.address2 = address2;
	}

	public String getProvince() {

		return this.province;
	}

	public void setProvince(String province) {

		this.province = province;
	}

	public String getCountry() {

		return this.country;
	}

	public void setCountry(String country) {

		this.country = country;
	}

	public String getPostalcode() {

		return this.postalcode;
	}

	public void setPostalcode(String postalcode) {

		this.postalcode = postalcode;
	}

	public String getHomephone() {

		return this.homephone;
	}

	public void setHomephone(String homephone) {

		this.homephone = homephone;
	}

	public String getWorkphone() {

		return this.workphone;
	}

	public void setWorkphone(String workphone) {

		this.workphone = workphone;
	}

	public String getCellphone() {

		return this.cellphone;
	}

	public void setCellphone(String cellphone) {

		this.cellphone = cellphone;
	}

	public Date getDOB() {

		return this.dob;
	}

	public String getDOBFormatted() {

		return new SimpleDateFormat("dd/MM/yyyy").format(this.dob);
	}

	public void setDOB(Date dob) {

		this.dob = dob;
	}

	public Date getAppliedDate() {

		return this.applied_date;
	}

	public String getAppliedDateFormatted() {

		return new SimpleDateFormat("dd/MM/yyyy").format(this.applied_date);
	}

	public void setAppliedDate(Date applied_date) {

		this.applied_date = applied_date;
	}

	public Date getModifiedDate() {

		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {

		this.modifiedDate = modifiedDate;
	}

	public double getSenority() {

		return senority;
	}

	public void setSenority(double senority) {

		this.senority = senority;
	}

	public ApplicantEsdExperienceBean getEsdExp() {

		return esdExp;
	}

	public void setEsdExp(ApplicantEsdExperienceBean esdExp) {

		this.esdExp = esdExp;
	}

	public boolean isStale() {

		boolean stale = (this.getModifiedDate() != null
				? (((Calendar.getInstance().getTimeInMillis() - this.getModifiedDate().getTime())
						/ (24 * 60 * 60 * 1000)) >= ApplicantProfileBean.STALE_PERIOD_DAYS)
				: true);

		return stale;
	}

	public void modified() throws JobOpportunityException {

		ApplicantProfileManager.applicantProfileModified(this);
	}

	public TeacherRecommendationBean[] getRecentlyAcceptedPositions(int search_window) throws JobOpportunityException {

		return RecommendationManager.getTeacherRecommendationBean(this, search_window);
	}

	public TeacherRecommendationBean[] getRecentlyAcceptedPositions(Date start_date) throws JobOpportunityException {

		return RecommendationManager.getTeacherRecommendationBean(this, start_date);
	}

	public String getAddress() {

		StringBuffer sb = new StringBuffer();

		sb.append(this.address1 + "<br />");
		if (!StringUtils.isEmpty(this.address2))
			sb.append(this.address2 + "<br />");
		sb.append(this.province + ", " + this.country + "<br />");
		sb.append(this.postalcode);

		return sb.toString();
	}

	public String generateXML() {

		StringBuffer sb = new StringBuffer();

		sb.append("<APPLICANT-PROFILE>");

		sb.append("<SIN>" + this.sin + "</SIN>");
		if (!StringUtils.isEmpty(this.sin2))
			sb.append("<SIN2>" + this.sin2 + "</SIN2>");
		sb.append("<SURNAME>" + this.surname + "</SURNAME>");
		sb.append("<FIRST-NAME>" + this.firstname + "</FIRST-NAME>");
		if (!StringUtils.isEmpty(this.maidenname))
			sb.append("<MAIDEN-NAME>" + this.maidenname + "</MAIDEN-NAME>");
		if (!StringUtils.isEmpty(this.middlename))
			sb.append("<MIDDLE-NAME>" + this.middlename + "</MIDDLE-NAME>");
		sb.append("<ADDRESS1>" + this.address1 + "</ADDRESS1>");
		if (!StringUtils.isEmpty(this.address2))
			sb.append("<ADDRESS2>" + this.address2 + "</ADDRESS2>");
		sb.append("<PROVINCE>" + this.province + "</PROVINCE>");
		sb.append("<COUNTRY>" + this.country + "</COUNTRY>");
		sb.append("<POSTAL-CODE>" + this.postalcode + "</POSTAL-CODE>");
		if (!StringUtils.isEmpty(this.homephone))
			sb.append("<HOME-PHONE>" + this.homephone + "</HOME-PHONE>");
		if (!StringUtils.isEmpty(this.workphone))
			sb.append("<WORK-PHONE>" + this.workphone + "</WORK-PHONE>");
		if (!StringUtils.isEmpty(this.cellphone))
			sb.append("<CELL-PHONE>" + this.cellphone + "</CELL-PHONE>");
		if (!StringUtils.isEmpty(this.email))
			sb.append("<EMAIL>" + this.email + "</EMAIL>");
		if (this.dob != null)
			sb.append("<DOB>" + new SimpleDateFormat("dd/MM/yyyy").format(this.dob) + "</DOB>");
		if (!StringUtils.isEmpty(this.profileType))
			sb.append("<PROFILE-TYPE>" + this.profileType + "</PROFILE-TYPE>");
		sb.append("</APPLICANT-PROFILE>");

		return sb.toString();
	}

	public int compareTo(ApplicantProfileBean o) {

		return (this.getComparableUID().compareToIgnoreCase(o.getComparableUID()));
	}

	public ApplicantProfileBean clone() {

		return new ApplicantProfileBean(this);
	}

	public String getMajorsList() {

		return majorsList;
	}

	public void setMajorsList(String majorsList) {

		this.majorsList = majorsList;
	}

	public String getMinorsList() {

		return minorsList;
	}

	public void setMinorsList(String minorsList) {

		this.minorsList = minorsList;
	}

	public String getProfileType() {

		return profileType;
	}

	public void setProfileType(String profileType) {

		this.profileType = profileType;
	}

	public boolean isProfileVerified() {

		return profileVerified;
	}

	public void setProfileVerified(boolean profileVerified) {

		this.profileVerified = profileVerified;
	}

	public ApplicantVerificationBean getVerificationBean() {

		return verificationBean;
	}

	public void setVerificationBean(ApplicantVerificationBean verificationBean) {

		this.verificationBean = verificationBean;
	}

	public TeacherRecommendationBean getMostRecentAcceptedRecommendation() {

		return this.mostRecentAcceptedRecommendation;
	}

	public void setMostRecentAcceptedRecommendation(TeacherRecommendationBean mostRecentAcceptedRecommendation) {

		this.mostRecentAcceptedRecommendation = mostRecentAcceptedRecommendation;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}