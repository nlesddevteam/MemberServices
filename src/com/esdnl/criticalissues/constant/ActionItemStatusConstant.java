package com.esdnl.criticalissues.constant;

public class ActionItemStatusConstant {

	private int value;
	private String desc;

	public static final ActionItemStatusConstant SUBMITTED = new ActionItemStatusConstant(1, "SUBMITTED");
	public static final ActionItemStatusConstant DUEDATE_REQUIRED = new ActionItemStatusConstant(1, "DUE DATE REQUIRED");
	public static final ActionItemStatusConstant APPROVED = new ActionItemStatusConstant(2, "APPROVED");

	public static final ActionItemStatusConstant[] ALL = new ActionItemStatusConstant[] {
			SUBMITTED, DUEDATE_REQUIRED, APPROVED
	};

	private ActionItemStatusConstant(int value, String desc) {

		this.value = value;
		this.desc = desc;
	}

	public int getValue() {

		return this.value;
	}

	public String getDescription() {

		return this.desc;
	}

	public boolean equals(Object o) {

		if (!(o instanceof ActionItemStatusConstant))
			return false;
		else if (o == null)
			return false;
		else
			return (this.getValue() == ((ActionItemStatusConstant) o).getValue());
	}

	public static ActionItemStatusConstant get(int value) {

		ActionItemStatusConstant tmp = null;

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
