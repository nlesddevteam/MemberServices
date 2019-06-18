package com.esdnl.mrs;

import java.io.Serializable;

public class PersonnelType implements Serializable {

	private static final long serialVersionUID = -3710584025636396826L;

	public static transient final PersonnelType MAINTENANCE_CUSTODIAN = new PersonnelType(-100, "MAINTENANCE CUSTODIAN");
	public static transient final PersonnelType CUSTODIAN = new PersonnelType(-101, "CUSTODIAN");
	public static transient final PersonnelType MAINTENANCE_REPAIRER = new PersonnelType(-102, "MAINTENANCE/REPAIRER");
	public static transient final PersonnelType JANITOR = new PersonnelType(-103, "JANITOR");
	public static transient final PersonnelType CARETAKER = new PersonnelType(-104, "CARETAKER");

	public static final PersonnelType[] ALL = new PersonnelType[] {
			CARETAKER, CUSTODIAN, MAINTENANCE_CUSTODIAN, MAINTENANCE_REPAIRER, JANITOR,
	};

	private int value;
	private String desc;

	private PersonnelType(int value, String desc) {

		this.value = value;
		this.desc = desc;
	}

	public int getValue() {

		return this.value;
	}

	public String getDescription() {

		return this.desc;
	}

	public static PersonnelType get(int value) {

		PersonnelType tmp = null;

		switch (value) {
		case -100:
			tmp = MAINTENANCE_CUSTODIAN;
			break;
		case -101:
			tmp = CUSTODIAN;
			break;
		case -102:
			tmp = MAINTENANCE_REPAIRER;
			break;
		case -103:
			tmp = JANITOR;
			break;
		case -104:
			tmp = CARETAKER;
			break;
		default:
			tmp = null;
		}

		return tmp;
	}

	public String toString() {

		return this.desc;
	}
}