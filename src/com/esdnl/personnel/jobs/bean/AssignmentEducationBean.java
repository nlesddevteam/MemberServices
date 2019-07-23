package com.esdnl.personnel.jobs.bean;

import java.io.Serializable;

import com.esdnl.personnel.jobs.dao.DegreeManager;

public class AssignmentEducationBean implements Serializable {

	private static final long serialVersionUID = -1847767379549247916L;
	private int assign_id;
	private String degree_id;

	public AssignmentEducationBean(String degree_id) {

		this(-1, degree_id);
	}

	public AssignmentEducationBean(int assign_id, String degree_id) {

		this.assign_id = assign_id;
		this.degree_id = degree_id;
	}

	public int getJobOpportunityAssignmentId() {

		return this.assign_id;
	}

	public String getDegreeId() {

		return this.degree_id;
	}

	public DegreeBean getDegree() throws JobOpportunityException {

		return DegreeManager.getDegreeBeans(degree_id);
	}
}