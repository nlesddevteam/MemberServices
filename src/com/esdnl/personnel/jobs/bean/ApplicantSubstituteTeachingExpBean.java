package com.esdnl.personnel.jobs.bean;

import java.io.Serializable;
import java.util.Date;

public class ApplicantSubstituteTeachingExpBean implements Serializable {

	private static final long serialVersionUID = -3134866910703991896L;
	private int id;
	private String sin;
	private Date from;
	private Date to;
	private String board;
	private int days;

	public ApplicantSubstituteTeachingExpBean() {

		this.id = -1;
		this.sin = null;
		this.from = null;
		this.to = null;
		this.board = null;
		this.days = 0;
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

	public String getSchoolBoard() {

		return this.board;
	}

	public void setSchoolBoard(String board) {

		this.board = board;
	}

	public int getNumDays() {

		return this.days;
	}

	public void setNumDays(int days) {

		this.days = days;
	}
}