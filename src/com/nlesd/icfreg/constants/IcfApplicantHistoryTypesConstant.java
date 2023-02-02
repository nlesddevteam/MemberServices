package com.nlesd.icfreg.constants;

import com.nlesd.bcs.constants.StatusConstant;

public class IcfApplicantHistoryTypesConstant {
	private int value;
	private String desc;
	public static final IcfApplicantHistoryTypesConstant UPDATED= new IcfApplicantHistoryTypesConstant(1, "Updated");
	public static final IcfApplicantHistoryTypesConstant APPROVED= new IcfApplicantHistoryTypesConstant(2, "Approved");
	public static final IcfApplicantHistoryTypesConstant NOTAPPROVED= new IcfApplicantHistoryTypesConstant(3, "Not Approved");
	public static final IcfApplicantHistoryTypesConstant WAITLISTED= new IcfApplicantHistoryTypesConstant(4, "Wait Listed");
	public static final IcfApplicantHistoryTypesConstant REMOVED= new IcfApplicantHistoryTypesConstant(5, "Removed");
	public static final IcfApplicantHistoryTypesConstant APPROVEDEMAIL= new IcfApplicantHistoryTypesConstant(6, "Approved Email");
	public static final IcfApplicantHistoryTypesConstant NOTAPPROVEDEMAIL= new IcfApplicantHistoryTypesConstant(7, "Not Approved Email");
	public static final IcfApplicantHistoryTypesConstant WAITLISTEDEMAIL= new IcfApplicantHistoryTypesConstant(8, "Wait Listed Email");
	public static final IcfApplicantHistoryTypesConstant SUBMISSIONCONFIRMATIONEMAIL= new IcfApplicantHistoryTypesConstant(9, "Submission Confirmation Email");
	public static final IcfApplicantHistoryTypesConstant ADDNEWREGISTRANT= new IcfApplicantHistoryTypesConstant(10, "Add New Registrant");
	static final IcfApplicantHistoryTypesConstant[] ALL = new IcfApplicantHistoryTypesConstant[] {
			UPDATED, APPROVED, NOTAPPROVED, WAITLISTED, REMOVED,APPROVEDEMAIL,NOTAPPROVEDEMAIL,WAITLISTEDEMAIL,SUBMISSIONCONFIRMATIONEMAIL,ADDNEWREGISTRANT
	};

	private IcfApplicantHistoryTypesConstant(int value, String desc) {

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

	public static IcfApplicantHistoryTypesConstant get(int value) {

		IcfApplicantHistoryTypesConstant tmp = null;

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
