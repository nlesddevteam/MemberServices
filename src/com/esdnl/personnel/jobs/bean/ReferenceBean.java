package com.esdnl.personnel.jobs.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReferenceBean {

	private int id;
	private ApplicantProfileBean profile;

	private String provided_by;
	private String provided_by_position;
	private Date date_provided;

	private String Q1;
	private String Q2;
	private String Q3;
	private String Q4;
	private String Q5;
	private String Q6;
	private String Q7;
	private String Q7_Comment;
	private String Q8;
	private String Q9;
	private String Q9_Comment;
	private String Q10;

	private String scale1;
	private String scale2;
	private String scale3;
	private String scale4;
	private String scale5;
	private String scale6;
	private String scale7;
	private String scale8;
	private String scale9;
	private String scale10;

	public int getId() {

		return this.id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public boolean isNew() {

		return this.id <= 0;
	}

	public String getApplicantId() {

		return profile.getUID();
	}

	public ApplicantProfileBean getApplicant() {

		return profile;
	}

	public void setApplicant(ApplicantProfileBean applicant) {

		this.profile = applicant;
	}

	public String getReferenceProviderName() {

		return this.provided_by;
	}

	public void setReferenceProviderName(String provided_by) {

		this.provided_by = provided_by;
	}

	public String getReferenceProviderPosition() {

		return this.provided_by_position;
	}

	public void setReferenceProviderPosition(String provided_by_position) {

		this.provided_by_position = provided_by_position;
	}

	public Date getProvidedDate() {

		return this.date_provided;
	}

	public void setProvidedDate(Date date_provided) {

		this.date_provided = date_provided;
	}

	public String getProvidedDateFormatted() {

		return new SimpleDateFormat("dd/MM/yyyy").format(this.date_provided);
	}

	public String getQ1() {

		return this.Q1;
	}

	public void setQ1(String Q1) {

		this.Q1 = Q1;
	}

	public String getQ2() {

		return this.Q2;
	}

	public void setQ2(String Q2) {

		this.Q2 = Q2;
	}

	public String getQ3() {

		return this.Q3;
	}

	public void setQ3(String Q3) {

		this.Q3 = Q3;
	}

	public String getQ4() {

		return this.Q4;
	}

	public void setQ4(String Q4) {

		this.Q4 = Q4;
	}

	public String getQ5() {

		return this.Q5;
	}

	public void setQ5(String Q5) {

		this.Q5 = Q5;
	}

	public String getQ6() {

		return this.Q6;
	}

	public void setQ6(String Q6) {

		this.Q6 = Q6;
	}

	public String getQ7() {

		return this.Q7;
	}

	public void setQ7(String Q7) {

		this.Q7 = Q7;
	}

	public String getQ7Comment() {

		return this.Q7_Comment;
	}

	public void setQ7Comment(String Q7_Comment) {

		this.Q7_Comment = Q7_Comment;
	}

	public String getQ8() {

		return this.Q8;
	}

	public void setQ8(String Q8) {

		this.Q8 = Q8;
	}

	public String getQ9() {

		return this.Q9;
	}

	public void setQ9(String Q9) {

		this.Q9 = Q9;
	}

	public String getQ9Comment() {

		return this.Q9_Comment;
	}

	public void setQ9Comment(String Q9_Comment) {

		this.Q9_Comment = Q9_Comment;
	}

	public String getQ10() {

		return this.Q10;
	}

	public void setQ10(String Q10) {

		this.Q10 = Q10;
	}

	public String getScale1() {

		return this.scale1;
	}

	public void setScale1(String scale1) {

		this.scale1 = scale1;
	}

	public String getScale2() {

		return this.scale2;
	}

	public void setScale2(String scale2) {

		this.scale2 = scale2;
	}

	public String getScale3() {

		return this.scale3;
	}

	public void setScale3(String scale3) {

		this.scale3 = scale3;
	}

	public String getScale4() {

		return this.scale4;
	}

	public void setScale4(String scale4) {

		this.scale4 = scale4;
	}

	public String getScale5() {

		return this.scale5;
	}

	public void setScale5(String scale5) {

		this.scale5 = scale5;
	}

	public String getScale6() {

		return this.scale6;
	}

	public void setScale6(String scale6) {

		this.scale6 = scale6;
	}

	public String getScale7() {

		return this.scale7;
	}

	public void setScale7(String scale7) {

		this.scale7 = scale7;
	}

	public String getScale8() {

		return this.scale8;
	}

	public void setScale8(String scale8) {

		this.scale8 = scale8;
	}

	public String getScale9() {

		return this.scale9;
	}

	public void setScale9(String scale9) {

		this.scale9 = scale9;
	}

	public String getScale10() {

		return this.scale10;
	}

	public void setScale10(String scale10) {

		this.scale10 = scale10;
	}

	public String toXML() {

		StringBuffer sb = new StringBuffer();
		sb.append("<REFERENCE>");

		sb.append("<REFERENCE-ID>" + this.id + "</REFERENCE-ID>");
		sb.append("<REFERENCE-DATE>" + this.getProvidedDateFormatted() + "</REFERENCE-DATE>");
		sb.append("<CANDIDATE-ID>" + this.profile.getUID() + "</CANDIDATE-ID>");
		sb.append("<PROVIDED-BY>" + this.provided_by + "</PROVIDED-BY>");
		sb.append("<PROVIDED-BY-POSITION>" + this.provided_by_position + "</PROVIDED-BY-POSITION>");

		sb.append("</REFERENCE>");

		return sb.toString();
	}
}
