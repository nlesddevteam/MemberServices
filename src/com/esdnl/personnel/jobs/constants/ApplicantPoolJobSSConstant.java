package com.esdnl.personnel.jobs.constants;

public class ApplicantPoolJobSSConstant {
	private int value;
	private String desc;

	public static final ApplicantPoolJobSSConstant CUSTODIAN = new ApplicantPoolJobSSConstant(1, "Custodian");
	public static final ApplicantPoolJobSSConstant CLEANER = new ApplicantPoolJobSSConstant(2, "Cleaner");
	public static final ApplicantPoolJobSSConstant SCHOOL_SECRETARY = new ApplicantPoolJobSSConstant(3, "School Secretary");
	public static final ApplicantPoolJobSSConstant STUDENT_ASSISTANT = new ApplicantPoolJobSSConstant(4, "Student Assistant");
	public static final ApplicantPoolJobSSConstant CARPENTER = new ApplicantPoolJobSSConstant(5, "Trades- Carpenter(Journeyperson");
	public static final ApplicantPoolJobSSConstant ELECTRICIAN = new ApplicantPoolJobSSConstant(6, "Trades- Electrician(Journeyperson");	
	public static final ApplicantPoolJobSSConstant OFFICE_ADMIN = new ApplicantPoolJobSSConstant(7, "Office Administrator");
	public static final ApplicantPoolJobSSConstant OTHER = new ApplicantPoolJobSSConstant(8, "Other");

	public static final ApplicantPoolJobSSConstant[] ALL = new ApplicantPoolJobSSConstant[] {
		CUSTODIAN, CLEANER, SCHOOL_SECRETARY, STUDENT_ASSISTANT, CARPENTER, ELECTRICIAN, OFFICE_ADMIN, OTHER
	};

	private ApplicantPoolJobSSConstant(int value, String desc) {

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

		if (!(o instanceof ApplicantPoolJobSSConstant))
			return false;
		else
			return (this.getValue() == ((ApplicantPoolJobSSConstant) o).getValue());
	}

	public static ApplicantPoolJobSSConstant get(int value) {

		ApplicantPoolJobSSConstant tmp = null;

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
