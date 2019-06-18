package com.awsd.weather;

import java.io.Serializable;

public class ClosureStatus implements Cloneable, Serializable {

	private static final long serialVersionUID = -2531929786877946834L;

	private int sid;
	private String desc;
	private String note;
	private boolean deleted;
	private boolean weatherRelated;
	private String rationale;

	public ClosureStatus(int status, String desc) {

		this(status, desc, null, false, false, null);
	}

	public ClosureStatus(int status, String desc, boolean deleted) {

		this(status, desc, null, deleted, false, null);
	}

	public ClosureStatus(int status, String desc, String note) {

		this(status, desc, note, false, false, null);
	}

	public ClosureStatus(int status, String desc, String note, boolean weatherRelated, String rationale) {

		this(status, desc, note, false, weatherRelated, rationale);
	}

	public ClosureStatus(int status, String desc, String note, boolean deleted, boolean weatherRelated, String rationale) {

		this.sid = status;
		this.desc = desc;
		this.note = note;
		this.deleted = deleted;
		this.weatherRelated = weatherRelated;
		this.rationale = rationale;
	}

	public int getClosureStatusID() {

		return sid;
	}

	public String getClosureStatusDescription() {

		return desc;
	}

	public String getSchoolClosureNote() {

		return this.note;
	}

	public void setSchoolClosureNote(String note) {

		this.note = note;
	}

	public boolean isDeleted() {

		return deleted;
	}

	public void setDeleted(boolean deleted) {

		this.deleted = deleted;
	}

	public boolean isWeatherRelated() {

		return this.weatherRelated;
	}

	public void setWeatherRelated(boolean weatherRelated) {

		this.weatherRelated = weatherRelated;
	}

	public String getRationale() {

		return this.rationale;
	}

	public void setRationale(String rationale) {

		this.rationale = rationale;
	}

	public String toString() {

		return (sid + " - " + desc);
	}

	public Object clone() {

		return new ClosureStatus(this.sid, this.desc, this.note, this.deleted, this.weatherRelated, this.rationale);
	}

}