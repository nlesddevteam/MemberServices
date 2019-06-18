package com.esdnl.personnel.jobs.constants;

import java.io.Serializable;

public class SubstituteListConstant implements Serializable {

	private static final long serialVersionUID = 556280722415675494L;

	private int value;
	private String desc;

	public static final SubstituteListConstant TEACHER = new SubstituteListConstant(1, "Substitute Teacher List");
	public static final SubstituteListConstant TLA = new SubstituteListConstant(2, "Substitute TLA List");

	public static final SubstituteListConstant[] ALL = new SubstituteListConstant[] {
			TEACHER, TLA
	};

	private SubstituteListConstant(int value, String desc) {

		this.value = value;
		this.desc = desc;
	}

	public int getValue() {

		return this.value;
	}

	public String getDescription() {

		return this.desc;
	}

	public static SubstituteListConstant get(int value) {

		switch (value) {
		case 1:
			return SubstituteListConstant.TEACHER;
		case 2:
			return SubstituteListConstant.TLA;
		default:
			return null;
		}
	}

	public boolean equal(Object o) {

		if (!(o instanceof SubstituteListConstant))
			return false;
		else
			return (this.getValue() == ((SubstituteListConstant) o).getValue());
	}

	public String toString() {

		return this.getDescription();
	}
}