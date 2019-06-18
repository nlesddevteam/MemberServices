package com.awsd.school;

import java.io.Serializable;

public class Grade implements Serializable {

	private static final long serialVersionUID = 4915253654887382363L;
	private int id;
	private String name;

	public Grade(String name) {

		this.name = name;
		this.id = -1;
	}

	public Grade(int id, String name) {

		this(name);
		this.id = id;
	}

	public int getGradeID() {

		return this.id;
	}

	public String getGradeName() {

		return this.name;
	}

	public boolean isHighSchool() {

		return (name.equals("LEVEL I") || name.equals("LEVEL II") || name.equals("LEVEL III"));
	}

	public boolean equals(Object o) {

		if ((o instanceof Grade) && (o != null) && (((Grade) o).getGradeID() == id))
			return true;
		else
			return false;
	}

	public String toString() {

		return this.name;
	}
}