package com.esdnl.personnel.jobs.bean;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.esdnl.util.StringUtils;
public class NLESDReferenceListBean {
	private int id;
	private String firstName;
	private String surName;
	private String maidenName;
	private String middleName;
	private String referenceType;
	private Date referenceDate;
	private String editUrl;
	private String viewUrl;
	private String applicantId;
	private String providedBy;
	private String providerPosition;
	public String getProvidedBy() {
		return providedBy;
	}
	public void setProvidedBy(String providedBy) {
		this.providedBy = providedBy;
	}
	public String getProviderPosition() {
		return providerPosition;
	}
	public void setProviderPosition(String providerPosition) {
		this.providerPosition = providerPosition;
	}
	public String getApplicantId() {
		return applicantId;
	}
	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}
	public String getEditUrl() {
		return editUrl;
	}
	public void setEditUrl(String editUrl) {
		this.editUrl = editUrl;
	}
	public String getViewUrl() {
		return viewUrl;
	}
	public void setViewUrl(String viewUrl) {
		this.viewUrl = viewUrl;
	}
	public int getId() {
		return id;
	}
	public String getMaidenName() {
		return maidenName;
	}
	public void setMaidenName(String maidenName) {
		this.maidenName = maidenName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSurName() {
		return surName;
	}
	public void setSurName(String surName) {
		this.surName = surName;
	}
	public String getReferenceType() {
		return referenceType;
	}
	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}
	public Date getReferenceDate() {
		return referenceDate;
	}
	public void setReferenceDate(Date referenceDate) {
		this.referenceDate = referenceDate;
	}
	public String getProvidedDateFormatted() {

		return new SimpleDateFormat("dd/MM/yyyy").format(this.referenceDate);
	}
	public String getFullName() {

		return this.surName + (!StringUtils.isEmpty(this.maidenName) ? "(nee " + this.maidenName + ")" : "") + ", "
				+ this.firstName + (!StringUtils.isEmpty(this.middleName) ? " " + this.middleName : "");
	}

	public String getFullNameReverse() {

		return this.firstName + (!StringUtils.isEmpty(this.middleName) ? " " + this.middleName : "") + " " + this.surName;
	}
	
	public String toXML() {

		StringBuffer sb = new StringBuffer();
		sb.append("<REFERENCE>");

		sb.append("<REFERENCE-ID>" + this.id + "</REFERENCE-ID>");
		sb.append("<REFERENCE-DATE>" + this.getProvidedDateFormatted() + "</REFERENCE-DATE>");
		sb.append("<CANDIDATE-ID>" + this.applicantId + "</CANDIDATE-ID>");
		sb.append("<PROVIDED-BY>" + this.providedBy + "</PROVIDED-BY>");
		sb.append("<PROVIDED-BY-POSITION>" + this.providerPosition + "</PROVIDED-BY-POSITION>");
		sb.append("<REFERENCE-TYPE>" + this.referenceType + "</REFERENCE-TYPE>");
		sb.append("<VIEW-URL>" + this.viewUrl + "</VIEW-URL>");
		sb.append("</REFERENCE>");

		return sb.toString();
	}
}
