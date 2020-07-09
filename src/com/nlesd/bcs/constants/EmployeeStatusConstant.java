package com.nlesd.bcs.constants;

public class EmployeeStatusConstant {
	private int value;
	private String desc;
	public static final EmployeeStatusConstant NOTREVIEWED= new EmployeeStatusConstant(1, "Not Yet Submitted");
	public static final EmployeeStatusConstant APPROVED= new EmployeeStatusConstant(2, "Approved");
	public static final EmployeeStatusConstant NOTAPPROVED= new EmployeeStatusConstant(3, "Not Approved");
	public static final EmployeeStatusConstant SUSPENDED= new EmployeeStatusConstant(4, "Suspended");
	public static final EmployeeStatusConstant REMOVED= new EmployeeStatusConstant(5, "Removed From System");
	public static final EmployeeStatusConstant SUBMITTEDFORREVIEW =new EmployeeStatusConstant(6, "Submitted For Approval");
	public static final EmployeeStatusConstant[] ALL = new EmployeeStatusConstant[] {
		NOTREVIEWED, APPROVED, NOTAPPROVED,SUSPENDED,REMOVED,SUBMITTEDFORREVIEW
	};

	private EmployeeStatusConstant(int value, String desc) {

		this.value = value;
		this.desc = desc;
	}

	public int getValue() {

		return this.value;
	}

	public String getDescription() {

		return this.desc;
	}

	public boolean equal(Object o) {

		if (!(o instanceof EmployeeStatusConstant))
			return false;
		else
			return (this.getValue() == ((EmployeeStatusConstant) o).getValue());
	}

	public static EmployeeStatusConstant get(int value) {

		EmployeeStatusConstant tmp = null;

		for (int i = 0; i < ALL.length; i++) {
			if (ALL[i].getValue() == value) {
				tmp = ALL[i];
				break;
			}
		}

		return tmp;
	}

	public String toString() {

		return this.getDescription();
	}

} 
