package com.esdnl.personnel.jobs.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;

public class ApplicantCriminalOffenceDeclarationOffenceBean {

	private ApplicantCriminalOffenceDeclarationBean declaration;
	private int offenceId;
	private Date offenceDate;
	private String courtLocation;
	private String conviction;

	public ApplicantCriminalOffenceDeclarationOffenceBean() {

		this.offenceId = 0;
		this.offenceDate = null;
		this.courtLocation = null;
		this.conviction = null;
	}

	public ApplicantCriminalOffenceDeclarationBean getDeclaration() {

		return declaration;
	}

	public void setDeclaration(ApplicantCriminalOffenceDeclarationBean declaration) {

		this.declaration = declaration;
	}

	public int getOffenceId() {

		return offenceId;
	}

	public void setOffenceId(int offenceId) {

		this.offenceId = offenceId;
	}

	public Date getOffenceDate() {

		return offenceDate;
	}

	public String getOffenceDateFormatted() {

		return (new SimpleDateFormat("MM/dd/yyyy")).format(this.getOffenceDate());
	}

	public void setOffenceDate(Date offenceDate) {

		this.offenceDate = offenceDate;
	}

	public String getCourtLocation() {

		return courtLocation;
	}

	public void setCourtLocation(String courtLocation) {

		this.courtLocation = courtLocation;
	}

	public String getConviction() {

		return conviction;
	}

	public void setConviction(String conviction) {

		this.conviction = conviction;
	}

	public String toXML() {

		String xml = "<ApplicantCriminalOffenceDeclarationOffenceBean offenceId='" + this.offenceId + "' offenceDate='"
				+ this.getOffenceDateFormatted() + "' courtLocation='" + StringEscapeUtils.escapeXml(this.getCourtLocation())
				+ "' conviction='" + StringEscapeUtils.escapeXml(this.getConviction()) + "' />";

		return xml;
	}

}
