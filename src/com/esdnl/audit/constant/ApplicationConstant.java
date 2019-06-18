package com.esdnl.audit.constant;

public class ApplicationConstant {

	public static final ApplicationConstant MRS = new ApplicationConstant(1, "MRS - Maintenance Request System");
	public static final ApplicationConstant H1N1 = new ApplicationConstant(2, "H1N1 District Advisory System");
	public static final ApplicationConstant PERSONNEL = new ApplicationConstant(3, "HR - Personnel Pkg");

	public static final ApplicationConstant[] ALL = new ApplicationConstant[] {
			MRS, H1N1, PERSONNEL
	};

	private int value;
	private String description;

	private ApplicationConstant(int value, String description) {

		this.value = value;
		this.description = description;
	}

	public int getValue() {

		return value;
	}

	public void setValue(int value) {

		this.value = value;
	}

	public String getDescription() {

		return description;
	}

	public void setDescription(String description) {

		this.description = description;
	}

	public static ApplicationConstant getH1N1() {

		return H1N1;
	}

	public static ApplicationConstant getMRS() {

		return MRS;
	}

	public static ApplicationConstant getHR() {

		return PERSONNEL;
	}

	public static ApplicationConstant get(int value) {

		ApplicationConstant tmp = null;

		for (int i = 0; i < ALL.length; i++) {
			if (ALL[i].getValue() == value) {
				tmp = ALL[i];
				break;
			}
		}

		return tmp;
	}

	public String toString() {

		return this.description;
	}

}
