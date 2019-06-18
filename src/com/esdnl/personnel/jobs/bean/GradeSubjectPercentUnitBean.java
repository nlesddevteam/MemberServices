package com.esdnl.personnel.jobs.bean;

import java.io.Serializable;

import com.awsd.school.Grade;
import com.awsd.school.Subject;

public class GradeSubjectPercentUnitBean implements Serializable {

	private static final long serialVersionUID = 4806482450112068928L;

	private int recommendation_id;
	private String comp_num;
	private Grade grade;
	private Subject subject;
	private double percent_unit;

	public GradeSubjectPercentUnitBean() {

		this(null, null, null, 0);
	}

	public GradeSubjectPercentUnitBean(String comp_num, Grade grade, Subject subject, double percent_unit) {

		this.recommendation_id = 0;
		this.comp_num = comp_num;
		this.grade = grade;
		this.subject = subject;
		this.percent_unit = percent_unit;
	}

	public GradeSubjectPercentUnitBean(int recommendation_id, Grade grade, Subject subject, double percent_unit) {

		this.comp_num = null;
		this.recommendation_id = recommendation_id;
		this.grade = grade;
		this.subject = subject;
		this.percent_unit = percent_unit;
	}

	public int getRecommendationId() {

		return this.recommendation_id;
	}

	public void setRecommendationId(int recommendation_id) {

		this.recommendation_id = recommendation_id;
	}

	public String getCompetitionNumber() {

		return this.comp_num;
	}

	public void setCompetitionNumber(String comp_num) {

		this.comp_num = comp_num;
	}

	public Grade getGrade() {

		return this.grade;
	}

	public void setGrade(Grade grade) {

		this.grade = grade;
	}

	public Subject getSubject() {

		return this.subject;
	}

	public void setSubject(Subject subject) {

		this.subject = subject;
	}

	public double getUnitPercentage() {

		return this.percent_unit;
	}

	public void setUnitPercentage(double percent_unit) {

		this.percent_unit = percent_unit;
	}

	public String toString() {

		return this.grade.getGradeName() + ((this.subject != null) ? " - " + this.subject.getSubjectName() : "") + " - "
				+ this.percent_unit + "%";
	}

	public String toXML() {

		StringBuffer sb = new StringBuffer();

		sb.append("<GSU-BEAN>");

		sb.append("<RECOMMENDATION-ID>" + this.recommendation_id + "</RECOMMENDATION-ID>");

		sb.append("<GRADE>" + this.grade + "</GRADE>");

		if (this.subject != null)
			sb.append("<SUBJECT>" + this.subject + "</SUBJECT>");
		else
			sb.append("<SUBJECT>NA</SUBJECT>");

		sb.append("<PERCENT-UNIT>" + this.percent_unit + "</PERCENT-UNIT>");

		sb.append("</GSU-BEAN>");

		return sb.toString();
	}

	public boolean equals(Object o) {

		if ((o instanceof GradeSubjectPercentUnitBean)
				&& (recommendation_id == ((GradeSubjectPercentUnitBean) o).getRecommendationId())
				&& comp_num.equals(((GradeSubjectPercentUnitBean) o).getCompetitionNumber())
				&& grade.equals(((GradeSubjectPercentUnitBean) o).getGrade())
				&& subject.equals(((GradeSubjectPercentUnitBean) o).getSubject())
				&& (percent_unit == ((GradeSubjectPercentUnitBean) o).getUnitPercentage()))
			return true;
		else
			return false;
	}

}
