package com.esdnl.personnel.jobs.bean;

import com.awsd.personnel.Personnel;
import com.awsd.school.Subject;
import com.esdnl.personnel.jobs.constants.CriteriaType;
import com.esdnl.personnel.jobs.constants.TrainingMethodConstant;

public class SEOStaffingAssignmentBean {

	private int staffingId;
	private Personnel personnel;
	private CriteriaType criteriaType;
	private int criteriaId;
	private Subject subject = null;

	public int getStaffingId() {

		return staffingId;
	}

	public void setStaffingId(int staffingId) {

		this.staffingId = staffingId;
	}

	public Personnel getPersonnel() {

		return personnel;
	}

	public void setPersonnel(Personnel personnel) {

		this.personnel = personnel;
	}

	public CriteriaType getCriteriaType() {

		return criteriaType;
	}

	public void setCriteriaType(CriteriaType criteriaType) {

		this.criteriaType = criteriaType;
	}

	public int getCriteriaId() {

		return criteriaId;
	}

	public void setCriteriaId(int criteriaId) {

		this.criteriaId = criteriaId;
	}

	public Subject getSubject() {

		return subject;
	}

	public void setSubject(Subject subject) {

		this.subject = subject;
	}

	public TrainingMethodConstant getTrainingMethod() {

		return (getCriteriaType().equal(CriteriaType.CRITERIA_TRAININGMETHOD) ? TrainingMethodConstant.get(getCriteriaId())
				: null);
	}

	public String getCriteria() {

		if (getCriteriaType().equal(CriteriaType.CRITERIA_SUBJECT))
			return getSubject().getSubjectName();
		else if (getCriteriaType().equal(CriteriaType.CRITERIA_TRAININGMETHOD))
			return getTrainingMethod().getDescription();
		else
			return "INVALID CRITERIA";
	}

}
