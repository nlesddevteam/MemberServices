package com.esdnl.personnel.jobs.constants;

public class RequestToHireStatus {

	private int value;
	private String desc;

	public static final RequestToHireStatus SUBMITTED = new RequestToHireStatus(1, "Submitted");
	public static final RequestToHireStatus APPROVEDDIVISION = new RequestToHireStatus(2, "Approved Division Director");
	public static final RequestToHireStatus APPROVEDBUDGET = new RequestToHireStatus(3, "Approved Budgeting/Comptroller");
	public static final RequestToHireStatus APPROVEDASSISTANT = new RequestToHireStatus(4, "Approved Assistant Director");
	public static final RequestToHireStatus APPROVEDASSISTANTHR = new RequestToHireStatus(5, "Approved Associate Director of Education (Programs and Human Resources)");
	public static final RequestToHireStatus ADCREATED = new RequestToHireStatus(6, "AD Created");
	public static final RequestToHireStatus REJECTED = new RequestToHireStatus(7, "Rejected");
	public static final RequestToHireStatus NOTIFICATIONSENT = new RequestToHireStatus(9, "Notification Sent");
	public static final RequestToHireStatus NOTIFICATIONRESENT = new RequestToHireStatus(10, "Notification Resent");
	public static final RequestToHireStatus UPDATED = new RequestToHireStatus(11, "Updated");
	public static final RequestToHireStatus[] ALL = new RequestToHireStatus[] {
		SUBMITTED, APPROVEDDIVISION, APPROVEDBUDGET, APPROVEDASSISTANT,APPROVEDASSISTANTHR,ADCREATED,REJECTED,NOTIFICATIONSENT,NOTIFICATIONRESENT,UPDATED
	};

	private RequestToHireStatus(int value, String desc) {

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

		if (!(o instanceof RequestToHireStatus))
			return false;
		else
			return (this.getValue() == ((RequestToHireStatus) o).getValue());
	}

	public static RequestToHireStatus get(int value) {

		RequestToHireStatus tmp = null;

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
