package com.esdnl.webupdatesystem.tenders.constants;
public class TenderStatus {
	private int value;
	private String desc;
	public static final TenderStatus TENDER_OPEN = new TenderStatus(1, "OPEN");
	public static final TenderStatus TENDER_CLOSED= new TenderStatus(2, "CLOSED");
	public static final TenderStatus TENDER_CANCELLED = new TenderStatus(3, "CANCELLED");
	public static final TenderStatus TENDER_ON_HOLD = new TenderStatus(4, "ON HOLD");
	public static final TenderStatus TENDER_AWARDED= new TenderStatus(5, "AWARDED");
	public static final TenderStatus TENDER_AMMENDED = new TenderStatus(6, "AMMENDED");
	public static final TenderStatus TENDER_ARCHIVED = new TenderStatus(7, "ARCHIVED");
	public static final TenderStatus TENDER_DISABLED = new TenderStatus(8, "DISABLED");
	public static final TenderStatus[] ALL = new TenderStatus[] {
		TENDER_OPEN, TENDER_CLOSED,TENDER_CANCELLED,TENDER_ON_HOLD,TENDER_AWARDED,TENDER_AMMENDED,TENDER_ARCHIVED,TENDER_DISABLED
	};
	private TenderStatus(int value, String desc) {
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
			return (this.getValue() == ((TenderStatus) o).getValue());
	}
	public static TenderStatus get(int value) {
		TenderStatus tmp = null;
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
