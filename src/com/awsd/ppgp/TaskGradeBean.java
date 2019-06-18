package com.awsd.ppgp;

import java.io.Serializable;

public class TaskGradeBean implements Serializable {

	private static final long serialVersionUID = -7993459562584834620L;

	private int grade_id;
	private String grade_title;

	public TaskGradeBean(int grade_id, String grade_title) {

		this.grade_id = grade_id;
		this.grade_title = grade_title;
	}

	public TaskGradeBean() {

		this(-1, "UNKNOWN");
	}

	public int getGradeID() {

		return this.grade_id;
	}

	public void setGradeID(int grade_id) {

		this.grade_id = grade_id;
	}

	public String getGradeTitle() {

		return this.grade_title;
	}

	public void setGradeTitle(String grade_title) {

		this.grade_title = grade_title;
	}

	public boolean equals(Object obj) {

		if (obj == null)
			return false;
		else if (!(obj instanceof TaskGradeBean))
			return false;
		else if (((TaskGradeBean) obj).getGradeID() != this.grade_id)
			return false;
		else
			return true;
	}
}