package com.esdnl.mrs;

import java.io.Serializable;
import java.util.Date;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.PersonnelException;

public class RequestComment implements Serializable {

	private static final long serialVersionUID = -3338867596642095532L;

	private int comment_id;
	private int request_id;
	private int made_by_id;
	private Date made_on;
	private String comment;

	private Personnel made_by = null;

	public RequestComment(int comment_id, int request_id, int made_by_id, Date made_on, String comment) {

		this.comment_id = comment_id;
		this.request_id = request_id;
		this.made_by_id = made_by_id;
		this.made_on = made_on;
		this.comment = comment;

		made_by = null;
	}

	public RequestComment(int request_id, int made_by_id, Date made_on, String comment) {

		this(-1, request_id, made_by_id, made_on, comment);
	}

	public RequestComment(int request_id, Personnel made_by, Date made_on, String comment) {

		this.comment_id = -1;
		this.request_id = request_id;
		this.made_by_id = made_by.getPersonnelID();
		this.made_by = made_by;
		this.made_on = made_on;
		this.comment = comment;
	}

	public RequestComment(MaintenanceRequest req, Personnel made_by, String comment) {

		this.comment_id = -1;
		this.request_id = req.getRequestID();
		this.made_by_id = made_by.getPersonnelID();
		this.made_by = made_by;
		this.comment = comment;

		this.made_on = null;
	}

	public int getCommentID() {

		return this.comment_id;
	}

	public int getRequestID() {

		return this.request_id;
	}

	public Personnel getMadeBy() throws PersonnelException {

		if (made_by == null)
			made_by = PersonnelDB.getPersonnel(this.made_by_id);

		return this.made_by;
	}

	public Date getMadeOn() {

		return this.made_on;
	}

	public String getComment() {

		return this.comment;
	}
}