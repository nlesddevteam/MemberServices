package com.esdnl.personnel.jobs.constants;

public class DocumentTypeSS {

	private int value;
	private String desc;
	public static final DocumentTypeSS DRIVERS_ABSTRACT = new DocumentTypeSS(101, "Drivers Abstract");
	public static final DocumentTypeSS OHS_TRAINING = new DocumentTypeSS(102, "OHS Training");
	public static final DocumentTypeSS CODE_OF_CONDUCT = new DocumentTypeSS(103, "Code of Conduct");
	public static final DocumentTypeSS FIRST_AID = new DocumentTypeSS(104, "First Aid");
	public static final DocumentTypeSS WHMIS = new DocumentTypeSS(105, "WHMIS");
	public static final DocumentTypeSS DRIVERS_LICENSE = new DocumentTypeSS(106, "Drivers License");
	public static final DocumentTypeSS UNIVERSITY_TRANSSCRIPT = new DocumentTypeSS(107, "University Transcript");
	public static final DocumentTypeSS DEGREES = new DocumentTypeSS(108, "Degrees");
	public static final DocumentTypeSS CERTIFICATES = new DocumentTypeSS(109, "Certificates");
	public static final DocumentTypeSS VULNERABLE_SECTOR_CHECK = new DocumentTypeSS(110, "Vulnerable Sector Check");
	public static final DocumentTypeSS REENTRYPL = new DocumentTypeSS(111, "Re-entry PL and Training");
	public static final DocumentTypeSS LETTER = new DocumentTypeSS(99, "District Letter");
	public static final DocumentTypeSS COVID19_VAX = new DocumentTypeSS(120, "COVID-19 Vaccination");
	public static final DocumentTypeSS[] ALL = new DocumentTypeSS[] {
			DRIVERS_ABSTRACT, OHS_TRAINING, CODE_OF_CONDUCT, FIRST_AID, WHMIS, DRIVERS_LICENSE, UNIVERSITY_TRANSSCRIPT,
			DEGREES, CERTIFICATES, VULNERABLE_SECTOR_CHECK, REENTRYPL, LETTER,COVID19_VAX
	};

	private DocumentTypeSS(int value, String desc) {

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

		if (!(o instanceof DocumentTypeSS))
			return false;
		else
			return (this.getValue() == ((DocumentTypeSS) o).getValue());
	}

	public static DocumentTypeSS get(int value) {

		DocumentTypeSS tmp = null;

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