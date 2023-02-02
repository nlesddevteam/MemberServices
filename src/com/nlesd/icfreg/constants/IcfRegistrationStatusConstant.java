package com.nlesd.icfreg.constants;

import com.nlesd.bcs.constants.StatusConstant;

public class IcfRegistrationStatusConstant {
	private int value;
	private String desc;
	public static final IcfRegistrationStatusConstant SUBMITTED= new IcfRegistrationStatusConstant(1, "Submitted");
	public static final IcfRegistrationStatusConstant APPROVED= new IcfRegistrationStatusConstant(2, "Approved");
	public static final IcfRegistrationStatusConstant NOTAPPROVED= new IcfRegistrationStatusConstant(3, "Not Approved");
	public static final IcfRegistrationStatusConstant WAITLISTED= new IcfRegistrationStatusConstant(4, "Wait Listed");
	public static final IcfRegistrationStatusConstant REMOVED= new IcfRegistrationStatusConstant(5, "Removed");
	static final IcfRegistrationStatusConstant[] ALL = new IcfRegistrationStatusConstant[] {
		SUBMITTED, APPROVED, NOTAPPROVED, WAITLISTED, REMOVED
	};

	private IcfRegistrationStatusConstant(int value, String desc) {

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

	public static IcfRegistrationStatusConstant get(int value) {

		IcfRegistrationStatusConstant tmp = null;

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
