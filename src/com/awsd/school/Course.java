package com.awsd.school;

import java.io.Serializable;

public class Course implements Serializable {

	private static final long serialVersionUID = -186258065584674047L;

	private int sid;
	private String name;

	public Course(String name) {

		this.name = name;
		sid = -1;
	}

	public Course(int sid, String name) {

		this(name);
		this.sid = sid;
	}

	public int getCourseID() {

		return sid;
	}

	public String getCourseName() {

		return name;
	}
}