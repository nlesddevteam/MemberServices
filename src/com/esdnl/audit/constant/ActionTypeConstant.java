package com.esdnl.audit.constant;

public class ActionTypeConstant {

	private String value;

	public static final ActionTypeConstant CREATE = new ActionTypeConstant("CREATE");
	public static final ActionTypeConstant DELETE = new ActionTypeConstant("DELETE");
	public static final ActionTypeConstant UPDATE = new ActionTypeConstant("UPDATE");
	public static final ActionTypeConstant VIEW = new ActionTypeConstant("VIEW");
	public static final ActionTypeConstant ILLEGAL_ACCESS = new ActionTypeConstant("ILLEGAL ACCESS");

	public static final ActionTypeConstant[] ALL = new ActionTypeConstant[] {
			CREATE, DELETE, UPDATE, VIEW, ILLEGAL_ACCESS
	};

	private ActionTypeConstant(String value) {

		this.value = value;
	}

	public String getValue() {

		return this.value;
	}

	public static ActionTypeConstant get(String value) {

		ActionTypeConstant tmp = null;

		for (int i = 0; i < ALL.length; i++) {
			if (ALL[i].getValue().equals(value)) {
				tmp = ALL[i];
				break;
			}
		}

		return tmp;
	}

	public boolean equals(Object o) {

		return ((o != null) && (o instanceof ActionTypeConstant) && (this.value.equals(((ActionTypeConstant) o).value)));
	}

	public String toString() {

		return this.getValue();
	}
}
