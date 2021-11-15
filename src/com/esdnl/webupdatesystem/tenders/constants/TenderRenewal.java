package com.esdnl.webupdatesystem.tenders.constants;

public class TenderRenewal {
	private int value;
	private String desc;
	public static final TenderRenewal NOT_APPLICABLE = new TenderRenewal(1, "Not Applicable");
	public static final TenderRenewal WEEKLY= new TenderRenewal(2, "Weekly");
	public static final TenderRenewal MONTHLY = new TenderRenewal(3, "Monthly");
	public static final TenderRenewal ANNUAL = new TenderRenewal(4, "Annual");
	public static final TenderRenewal OTHER= new TenderRenewal(5, "Other");
	
	public static final TenderRenewal[] ALL = new TenderRenewal[] {
			NOT_APPLICABLE, WEEKLY,MONTHLY,ANNUAL,OTHER
	};
	private TenderRenewal(int value, String desc) {
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
		if (!(o instanceof TenderStatus))
			return false;
		else
			return (this.getValue() == ((TenderRenewal) o).getValue());
	}
	public static TenderRenewal get(int value) {
		TenderRenewal tmp = null;
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