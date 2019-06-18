package com.nlesd.bcs.constants;

public class DropdownTypeConstant {
	private int value;
	private String desc;
	public static final DropdownTypeConstant VMAKE= new DropdownTypeConstant(1, "Vehicle Make");
	public static final DropdownTypeConstant VMODEL= new DropdownTypeConstant(2, "Vehicle Model");
	public static final DropdownTypeConstant VTYPE= new DropdownTypeConstant(3, "Vehicle Type");
	public static final DropdownTypeConstant VSIZE= new DropdownTypeConstant(4, "Vehicle Size");
	public static final DropdownTypeConstant VDOCUMENT= new DropdownTypeConstant(5, "Vehicle Document");
	public static final DropdownTypeConstant EPOSITION= new DropdownTypeConstant(6, "Employee Position");
	public static final DropdownTypeConstant LCLASS= new DropdownTypeConstant(7, "Licence Class");
	public static final DropdownTypeConstant CDOCUMENT= new DropdownTypeConstant(8, "Contractor Document");

	public static final DropdownTypeConstant[] ALL = new DropdownTypeConstant[] {
		VMAKE, VMODEL, VTYPE, VSIZE,VDOCUMENT,EPOSITION,LCLASS,CDOCUMENT
	};

	private DropdownTypeConstant(int value, String desc) {

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

		if (!(o instanceof DropdownTypeConstant))
			return false;
		else
			return (this.getValue() == ((DropdownTypeConstant) o).getValue());
	}

	public static DropdownTypeConstant get(int value) {

		DropdownTypeConstant tmp = null;

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