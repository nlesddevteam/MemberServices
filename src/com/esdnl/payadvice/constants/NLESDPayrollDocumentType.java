package com.esdnl.payadvice.constants;

public class NLESDPayrollDocumentType {
	private int value;
	private String desc;
	public static final NLESDPayrollDocumentType PAYROLL_DATA = new NLESDPayrollDocumentType(1, "Payroll Data");
	public static final NLESDPayrollDocumentType EMPLOYEE_MAPPING = new NLESDPayrollDocumentType(2, "Employee Mapping");
	public static final NLESDPayrollDocumentType OTHER = new NLESDPayrollDocumentType(3, "Other");
	public static final NLESDPayrollDocumentType[] ALL = new NLESDPayrollDocumentType[] {
			PAYROLL_DATA, EMPLOYEE_MAPPING, OTHER
	};
	private NLESDPayrollDocumentType(int value, String desc) {
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
		if (!(o instanceof NLESDPayrollDocumentType))
			return false;
		else
			return (this.getValue() == ((NLESDPayrollDocumentType) o).getValue());
	}
	public static NLESDPayrollDocumentType get(int value) {
	NLESDPayrollDocumentType tmp = null;
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
