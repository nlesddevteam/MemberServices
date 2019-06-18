package com.nlesd.bcs.constants;

public class EntryTableConstant {
	private int value;
	private String desc;
	public static final EntryTableConstant CONTRACTORS= new EntryTableConstant(1, "BCS_CONTRACTORS");
	public static final EntryTableConstant CONTRACTORSECURITY= new EntryTableConstant(2, "BCS_CONTRACTOR_SECURITY");
	public static final EntryTableConstant CONTRACTORSECURITYARC= new EntryTableConstant(3, "BCS_CONTRACTOR_SECURITY_ARC");
	public static final EntryTableConstant CONTRACTORCOMPANY= new EntryTableConstant(4, "BCS_CONTRACTOR_COMPANY");
	public static final EntryTableConstant CONTRACTORVEHICLE= new EntryTableConstant(5, "BCS_CONTRACTOR_VEHICLE");
	public static final EntryTableConstant CONTRACTORVEHICLEDOC= new EntryTableConstant(6, "BCS_CONTRACTOR_VEHICLE_DOCUMENTS");
	public static final EntryTableConstant CONTRACTOREMPLOYEE= new EntryTableConstant(7, "BCS_CONTRACTOR_EMPLOYEE");
	public static final EntryTableConstant CONTRACTORDOC= new EntryTableConstant(8, "BCS_CONTRACTOR_DOCUMENTS");
	public static final EntryTableConstant SYSTEMDOC= new EntryTableConstant(9, "BCS_SYSTEM_DOCUMENTS");
	public static final EntryTableConstant CONTRACT= new EntryTableConstant(10, "BCS_CONTRACT");
	public static final EntryTableConstant ROUTE= new EntryTableConstant(11, "BCS_ROUTE");
	public static final EntryTableConstant CONTRACTHISTORY= new EntryTableConstant(12, "BCS_CONTRACT_HISTORY");
	public static final EntryTableConstant ROUTEDRIVER= new EntryTableConstant(13, "BCS_ROUTE_DRIVERS");
	public static final EntryTableConstant ROUTEVEHICLE= new EntryTableConstant(14, "BCS_ROUTE_VEHICLES");
	public static final EntryTableConstant EMPLOYEETRAINING= new EntryTableConstant(15, "BCS_EMPLOYEE_TRAINING");
	public static final EntryTableConstant LETTERONFILE= new EntryTableConstant(16, "BCS_LETTER_ON_FILE");
	public static final EntryTableConstant[] ALL = new EntryTableConstant[] {
		CONTRACTORS, CONTRACTORSECURITY,CONTRACTORSECURITYARC,CONTRACTORCOMPANY,CONTRACTORVEHICLE,CONTRACTORVEHICLEDOC,CONTRACTOREMPLOYEE,CONTRACTORDOC,SYSTEMDOC
		,CONTRACT,ROUTE,CONTRACTHISTORY,ROUTEDRIVER,ROUTEVEHICLE,EMPLOYEETRAINING,LETTERONFILE
	};

	private EntryTableConstant(int value, String desc) {

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

		if (!(o instanceof EntryTableConstant))
			return false;
		else
			return (this.getValue() == ((EntryTableConstant) o).getValue());
	}

	public static EntryTableConstant get(int value) {

		EntryTableConstant tmp = null;

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