package com.awsd.school;

import java.io.Serializable;

public class Subject implements Serializable {

	private static final long serialVersionUID = -8716426516326918478L;
	private int sid;
	private String name;

	public Subject(String name) {

		this.name = name;
		sid = -1;
	}

	public Subject(int sid, String name) {

		this(name);
		this.sid = sid;
	}

	public int getSubjectID() {

		return sid;
	}

	public String getSubjectName() {

		return name;
	}

	public boolean equals(Object o) {

		if ((o instanceof Subject) && (o != null) && (((Subject) o).getSubjectID() == sid))
			return true;
		else
			return false;
	}

	public String toString() {

		return this.name;
	}

	public String toXML() {

		return "<SubjectBean subject-id='" + this.sid + "' subject-name='" + this.name + "' />";
	}
}