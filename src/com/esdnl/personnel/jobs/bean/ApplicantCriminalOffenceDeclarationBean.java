package com.esdnl.personnel.jobs.bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;

public class ApplicantCriminalOffenceDeclarationBean {

	private int declarationId;
	private ApplicantProfileBean applicant;
	private Date declarationDate;
	private String position;
	private String location;
	private Collection<ApplicantCriminalOffenceDeclarationOffenceBean> offences;

	public ApplicantCriminalOffenceDeclarationBean() {

		this.declarationId = 0;
		this.declarationDate = new Date();
		this.applicant = null;
		this.position = null;
		this.location = null;
		this.offences = null;
	}

	public int getDeclarationId() {

		return declarationId;
	}

	public void setDeclarationId(int declarationId) {

		this.declarationId = declarationId;
	}

	public Date getDeclarationDate() {

		return declarationDate;
	}

	public String getDeclarationDateFormatted() {

		return (new SimpleDateFormat("MM/dd/yyyy")).format(this.getDeclarationDate());
	}

	public void setDeclarationDate(Date declarationDate) {

		this.declarationDate = declarationDate;
	}

	public ApplicantProfileBean getApplicant() {

		return applicant;
	}

	public void setApplicant(ApplicantProfileBean applicant) {

		this.applicant = applicant;
	}

	public String getPosition() {

		return position;
	}

	public void setPosition(String position) {

		this.position = position;
	}

	public String getLocation() {

		return location;
	}

	public void setLocation(String location) {

		this.location = location;
	}

	public Collection<ApplicantCriminalOffenceDeclarationOffenceBean> getOffences() {

		return offences;
	}

	public void setOffences(Collection<ApplicantCriminalOffenceDeclarationOffenceBean> offences) {

		this.offences = offences;
	}

	public void addOffence(ApplicantCriminalOffenceDeclarationOffenceBean offence) {

		if (this.offences == null)
			this.offences = new ArrayList<ApplicantCriminalOffenceDeclarationOffenceBean>();

		this.offences.add(offence);
	}

	public String toXML() {

		StringBuffer xml = new StringBuffer("<ApplicantCriminalOffenceDeclarationBean declarationId='" + this.declarationId
				+ "' declarationDate='" + this.getDeclarationDateFormatted() + "' position='"
				+ StringEscapeUtils.escapeXml(this.position) + "' location='" + StringEscapeUtils.escapeXml(this.position)
				+ "'>");

		xml.append(this.getApplicant().generateXML());

		if (offences != null && offences.size() > 0) {
			xml.append("<ApplicantCriminalOffenceDeclarationOffenceBeans>");
			for (ApplicantCriminalOffenceDeclarationOffenceBean offence : offences) {
				xml.append(offence.toXML());
			}
			xml.append("</ApplicantCriminalOffenceDeclarationOffenceBeans>");
		}

		xml.append("</ApplicantCriminalOffenceDeclarationBean>");
		return xml.toString();
	}

}
