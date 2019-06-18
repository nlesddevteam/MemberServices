package com.esdnl.criticalissues.constant;

public class ReportTypeConstant {

	private int value;
	private String desc;

	public static final ReportTypeConstant FIRE_ALARM_ANNUAL = new ReportTypeConstant(1, "Fire Alarm Annual Test and Inspection Report");
	public static final ReportTypeConstant ENVIRONMENT_HEALTH = new ReportTypeConstant(2, "Environmental Health Services Division School Inspection Report");
	public static final ReportTypeConstant GOVERNMENT_SERVICES = new ReportTypeConstant(3, "Government Services School Inspection Report");
	public static final ReportTypeConstant DAILY_SCHOOL_FIRE = new ReportTypeConstant(4, "DAILY SCHOOL FIRE INSPECTION REPORT");

	public static final ReportTypeConstant[] ALL = new ReportTypeConstant[] {
			FIRE_ALARM_ANNUAL, ENVIRONMENT_HEALTH, GOVERNMENT_SERVICES, DAILY_SCHOOL_FIRE
	};

	private ReportTypeConstant(int value, String desc) {

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

		if (!(o instanceof ReportTypeConstant))
			return false;
		else if (o == null)
			return false;
		else
			return (this.getValue() == ((ReportTypeConstant) o).getValue());
	}

	public static ReportTypeConstant get(int value) {

		ReportTypeConstant tmp = null;

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
