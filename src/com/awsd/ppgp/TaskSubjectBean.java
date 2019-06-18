package com.awsd.ppgp;

import java.io.Serializable;

public class TaskSubjectBean implements Serializable {

	private static final long serialVersionUID = -4746157305895405876L;

	private int subject_id;
	private String subject_title;

	public TaskSubjectBean(int subject_id, String subject_title) {

		this.subject_id = subject_id;
		this.subject_title = subject_title;
	}

	public TaskSubjectBean() {

		this(-1, "UNKNOWN");
	}

	public int getSubjectID() {

		return this.subject_id;
	}

	public void setSubjectID(int subject_id) {

		this.subject_id = subject_id;
	}

	public String getSubjectTitle() {

		return this.subject_title;
	}

	public void setSubjectTitle(String subject_title) {

		this.subject_title = subject_title;
	}

	public boolean equals(Object obj) {

		if (obj == null)
			return false;
		else if (!(obj instanceof TaskSubjectBean))
			return false;
		else if (((TaskSubjectBean) obj).getSubjectID() != this.subject_id)
			return false;
		else
			return true;
	}

}