package com.esdnl.h1n1.constant;

import java.util.ArrayList;

public class Grade {

	public static final Grade KINDERGARDEN = new Grade(1, "Kindergraden", "kindergarden");
	public static final Grade GRADE_1 = new Grade(2, "Grade 1", "grade_1");
	public static final Grade GRADE_2 = new Grade(3, "Grade 2", "grade_2");
	public static final Grade GRADE_3 = new Grade(4, "Grade 3", "grade_3");
	public static final Grade GRADE_4 = new Grade(5, "Grade 4", "grade_4");
	public static final Grade GRADE_5 = new Grade(6, "Grade 5", "grade_5");
	public static final Grade GRADE_6 = new Grade(7, "Grade 6", "grade_6");
	public static final Grade GRADE_7 = new Grade(8, "Grade 7", "grade_7");
	public static final Grade GRADE_8 = new Grade(9, "Grade 8", "grade_8");
	public static final Grade GRADE_9 = new Grade(10, "Grade 9", "grade_9");
	public static final Grade GRADE_10 = new Grade(11, "Level I", "grade_10");
	public static final Grade GRADE_11 = new Grade(12, "Level II", "grade_11");
	public static final Grade GRADE_12 = new Grade(13, "Level III", "grade_12");

	public static final Grade[] ALL = new Grade[] {
			KINDERGARDEN, GRADE_1, GRADE_2, GRADE_3, GRADE_4, GRADE_5, GRADE_6, GRADE_7, GRADE_8, GRADE_9, GRADE_10,
			GRADE_11, GRADE_12
	};

	private int id;
	private String name;
	private String token;

	private Grade(int id, String name, String token) {

		this.id = id;
		this.name = name;
		this.token = token;
	}

	public int getId() {

		return id;
	}

	public String getName() {

		return name;
	}

	public String getToken() {

		return token;
	}

	public void setToken(String token) {

		this.token = token;
	}

	public static Grade get(int id) {

		Grade tmp = null;

		for (int i = 0; i < ALL.length; i++) {
			if (ALL[i].getId() == id) {
				tmp = ALL[i];
				break;
			}
		}

		return tmp;
	}

	public static ArrayList<Grade> getList() {

		ArrayList<Grade> grades = new ArrayList<Grade>();

		for (int i = 0; i < ALL.length; i++) {
			grades.add(ALL[i]);
		}

		return grades;
	}
}
