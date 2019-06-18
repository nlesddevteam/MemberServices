package com.nlesd.bcs.constants;

public class StatusConstant {
	private int value;
	private String desc;
	public static final StatusConstant SUBMITTED= new StatusConstant(1, "Submitted For Approval");
	public static final StatusConstant APPROVED= new StatusConstant(2, "Approved For Access");
	public static final StatusConstant REJECTED= new StatusConstant(3, "Rejected For Access");
	public static final StatusConstant SUSPENDED= new StatusConstant(4, "Suspended For Access");
	public static final StatusConstant REMOVED= new StatusConstant(5, "Removed From System");
	


	public static final StatusConstant[] ALL = new StatusConstant[] {
		SUBMITTED, APPROVED, REJECTED, SUSPENDED, REMOVED
	};

	private StatusConstant(int value, String desc) {

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

		if (!(o instanceof StatusConstant))
			return false;
		else
			return (this.getValue() == ((StatusConstant) o).getValue());
	}

	public static StatusConstant get(int value) {

		StatusConstant tmp = null;

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
