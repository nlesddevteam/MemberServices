package com.esdnl.personnel.jobs.constants;

public class CriteriaType {

	private int value;
	private String desc;

	public static final CriteriaType CRITERIA_SUBJECT = new CriteriaType(1, "SUBJECT");
	public static final CriteriaType CRITERIA_TRAININGMETHOD = new CriteriaType(2, "TRAINING METHOD");

	public static final CriteriaType[] ALL = new CriteriaType[] {
			CRITERIA_SUBJECT, CRITERIA_TRAININGMETHOD
	};

	private CriteriaType(int value, String desc) {

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

		if (!(o instanceof CriteriaType))
			return false;
		else
			return (this.getValue() == ((CriteriaType) o).getValue());
	}

	public static CriteriaType get(int value) {

		CriteriaType tmp = null;

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
