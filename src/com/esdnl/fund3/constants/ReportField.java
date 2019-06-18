package com.esdnl.fund3.constants;

public class ReportField {
	private int value;
	private String desc;
	public static final ReportField CRITERIA_SUBJECT = new ReportField(1, "SUBJECT");
	public static final ReportField CRITERIA_TRAININGMETHOD = new ReportField(2, "TRAINING METHOD");
	public static final ReportField[] ALL = new ReportField[] {
			CRITERIA_SUBJECT, CRITERIA_TRAININGMETHOD
	};
	private ReportField(int value, String desc) {
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
		if (!(o instanceof ReportField))
			return false;
		else
			return (this.getValue() == ((ReportField) o).getValue());
	}
	public static ReportField get(int value) {
		ReportField tmp = null;
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