package com.esdnl.personnel.jobs.bean;

import java.io.Serializable;

public class AssignmentMajorMinorBean implements Serializable {

	private static final long serialVersionUID = -3583973216551733955L;
	private int assign_id;
	private int major_id;
	private int minor_id;

	public AssignmentMajorMinorBean(int assign_id, int major_id, int minor_id) {

		this.assign_id = assign_id;
		this.major_id = major_id;
		this.minor_id = minor_id;
	}

	public AssignmentMajorMinorBean(int major_id, int minor_id) {

		this(-1, major_id, minor_id);
	}

	public int getJobOpportunityAssignmentId() {

		return this.assign_id;
	}

	public void setJobOpportunityAssignmentId(int assign_id) {

		this.assign_id = assign_id;
	}

	public int getMinorId() {

		return this.minor_id;
	}

	public void setMinorId(int minor_id) {

		this.minor_id = minor_id;
	}

	public int getMajorId() {

		return this.major_id;
	}

	public void setMajorId(int major_id) {

		this.major_id = major_id;
	}

}