package com.esdnl.personnel.jobs.constants;

public class EmploymentConstant {

	private int value;
	private String desc;

	public static final EmploymentConstant TERM = new EmploymentConstant(1, "TERM");
	public static final EmploymentConstant INCREASE = new EmploymentConstant(2, "INCREASE");
	public static final EmploymentConstant PROBATION_1 = new EmploymentConstant(3, "PROBATION 1");
	public static final EmploymentConstant PROBATION_2 = new EmploymentConstant(4, "PROBATION 2");
	public static final EmploymentConstant PERM_TRANSFER = new EmploymentConstant(5, "PERM. TRANSFER");
	public static final EmploymentConstant PERM_TRANSFER_INCREASE = new EmploymentConstant(6, "PERM. TRANSFER WITH INCREASE");
	public static final EmploymentConstant ONE_YEAR_TRANSFER = new EmploymentConstant(7, "ONE YEAR TRANSFER");

	public static final EmploymentConstant[] ALL = new EmploymentConstant[] {
			TERM, INCREASE, PROBATION_1, PROBATION_2, PERM_TRANSFER, PERM_TRANSFER_INCREASE, ONE_YEAR_TRANSFER
	};

	private EmploymentConstant(int value, String desc) {

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

		if (!(o instanceof EmploymentConstant))
			return false;
		else
			return (this.getValue() == ((EmploymentConstant) o).getValue());
	}

	public static EmploymentConstant get(int value) {

		EmploymentConstant tmp = null;

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