package com.esdnl.school.bean;

import org.apache.commons.lang.StringUtils;

public class StudentRecordBean {

	private String studentId;
	private String firstName;
	private String lastName;
	private String middleName;
	private GENDER gender;

	public enum GENDER {
		MALE(1, "Male"), FEMALE(2, "Female");

		private int id;
		private String name;

		GENDER(int id, String name) {

			this.id = id;
			this.name = name;
		}

		public int getId() {

			return this.id;
		}

		public String getName() {

			return this.name;
		}

		public static GENDER get(int id) {

			for (GENDER g : GENDER.values()) {
				if (g.id == id)
					return g;
			}

			return null;
		}
	}

	public StudentRecordBean() {

		super();
	}

	public String getStudentId() {

		return studentId;
	}

	public void setStudentId(String studentId) {

		this.studentId = studentId;
	}

	public String getFirstName() {

		return firstName;
	}

	public void setFirstName(String firstName) {

		this.firstName = firstName;
	}

	public String getLastName() {

		return lastName;
	}

	public void setLastName(String lastName) {

		this.lastName = lastName;
	}

	public String getMiddleName() {

		return middleName;
	}

	public void setMiddleName(String middleName) {

		this.middleName = middleName;
	}

	public GENDER getGender() {

		return gender;
	}

	public void setGender(GENDER gender) {

		this.gender = gender;
	}

	@Override
	public String toString() {

		return getLastName() + ", " + getFirstName() + (StringUtils.isNotEmpty(getMiddleName()) ? getMiddleName() : "")
				+ " (" + getStudentId() + ")";
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecordBean other = (StudentRecordBean) obj;
		if (studentId == null) {
			if (other.studentId != null)
				return false;
		}
		else if (!studentId.equals(other.studentId))
			return false;
		return true;
	}
}
