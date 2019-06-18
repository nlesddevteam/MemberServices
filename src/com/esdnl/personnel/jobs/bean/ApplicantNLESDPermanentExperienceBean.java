package com.esdnl.personnel.jobs.bean;

import java.io.Serializable;
import java.util.Date;

public class ApplicantNLESDPermanentExperienceBean implements Serializable {

	private static final long serialVersionUID = 3174644811487313617L;

	private int id;
	private String sin;
	private Date from;
	private Date to;
	private int school_id;
	private String grds_subs;

	public ApplicantNLESDPermanentExperienceBean() {

		this.id = -1;
		this.sin = null;
		this.from = null;
		this.to = null;
		this.school_id = -1;
		this.grds_subs = null;
	}

	public int getId() {

		return this.id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getSIN() {

		return this.sin;
	}

	public void setSIN(String sin) {

		this.sin = sin;
	}

	public Date getFrom() {

		return this.from;
	}

	public void setFrom(Date from) {

		this.from = from;
	}

	public Date getTo() {

		return this.to;
	}

	public void setTo(Date to) {

		this.to = to;
	}

	public int getSchoolId() {

		return this.school_id;
	}

	public void setSchoolId(int school_id) {

		this.school_id = school_id;
	}

	public String getGradesSubjects() {

		return this.grds_subs;
	}

	public void setGradesSubjects(String grds_subs) {

		this.grds_subs = grds_subs;
	}
}
