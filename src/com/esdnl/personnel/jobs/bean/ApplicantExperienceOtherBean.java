package com.esdnl.personnel.jobs.bean;

import java.io.Serializable;
import java.util.Date;

public class ApplicantExperienceOtherBean implements Serializable {

	private static final long serialVersionUID = 4153008950874910756L;
	private int id;
	private String sin;
	private Date from;
	private Date to;
	private String school_board;
	private String grds_subs;

	public ApplicantExperienceOtherBean() {

		this.id = -1;
		this.sin = null;
		this.from = null;
		this.to = null;
		this.school_board = null;
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

	public String getSchoolAndBoard() {

		return this.school_board;
	}

	public void setSchoolAndBoard(String school_board) {

		this.school_board = school_board;
	}

	public String getGradesSubjects() {

		return this.grds_subs;
	}

	public void setGradesSubjects(String grds_subs) {

		this.grds_subs = grds_subs;
	}
}