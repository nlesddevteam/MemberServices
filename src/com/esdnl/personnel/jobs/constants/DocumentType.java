package com.esdnl.personnel.jobs.constants;

public class DocumentType {

	private int value;
	private String desc;

	public static final DocumentType UNIVERSITY_TRANSSCRIPT = new DocumentType(1, "University Transcript");
	public static final DocumentType TEACHING_CERTIFICATE = new DocumentType(2, "Teaching Certificate");
	public static final DocumentType CODE_OF_CONDUCT = new DocumentType(3, "Code of Conduct");
	public static final DocumentType DELF = new DocumentType(4, "French Proficiency (DELF)");
	public static final DocumentType ECE = new DocumentType(5, "Level 2 Early Childhood Education Certificate");

	public static final DocumentType[] ALL = new DocumentType[] {
			UNIVERSITY_TRANSSCRIPT, TEACHING_CERTIFICATE, CODE_OF_CONDUCT, DELF, ECE
	};

	private DocumentType(int value, String desc) {

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

		if (!(o instanceof DocumentType))
			return false;
		else
			return (this.getValue() == ((DocumentType) o).getValue());
	}

	public static DocumentType get(int value) {

		DocumentType tmp = null;

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