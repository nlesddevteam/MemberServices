package com.esdnl.webupdatesystem.policies.constants;
public class PolicyStatus {
	private int value;
	private String desc;
	public static final PolicyStatus POLICY_PUBLIC = new PolicyStatus(1, "PUBLIC");
	public static final PolicyStatus POLICY_DRAFT= new PolicyStatus(2, "DRAFT");
	public static final PolicyStatus POLICY_DISABLED = new PolicyStatus(3, "DISABLED");
	
	public static final PolicyStatus[] ALL = new PolicyStatus[] {
		POLICY_PUBLIC , POLICY_DRAFT,POLICY_DISABLED
	};
	private PolicyStatus(int value, String desc) {
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
		if (!(o instanceof PolicyStatus))
			return false;
		else
			return (this.getValue() == ((PolicyStatus) o).getValue());
	}
	public static PolicyStatus get(int value) {
		PolicyStatus tmp = null;
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