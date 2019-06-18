package com.nlesd.eecd.constants;
public class TeacherAreaStatus {
	private int value;
	private String desc;
	public static final TeacherAreaStatus SUBMITTED= new TeacherAreaStatus(1, "Submitted");
	public static final TeacherAreaStatus APPROVED_SCHOOL_ADMIN = new TeacherAreaStatus(2, "Approved School Admin");
	public static final TeacherAreaStatus DECLINED_SCHOOL_ADMIN = new TeacherAreaStatus(3, "Declined School Admin");
	public static final TeacherAreaStatus SELECTED_FOR_GROUP = new TeacherAreaStatus(4, "Selected For Group");
	public static final TeacherAreaStatus DELETED = new TeacherAreaStatus(5, "Deleted By User");
	public static final TeacherAreaStatus[] ALL = new TeacherAreaStatus[] {
		SUBMITTED, APPROVED_SCHOOL_ADMIN,DECLINED_SCHOOL_ADMIN,SELECTED_FOR_GROUP,DELETED
	};
	private TeacherAreaStatus(int value, String desc) {
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
		if (!(o instanceof TeacherAreaStatus))
			return false;
		else
			return (this.getValue() == ((TeacherAreaStatus) o).getValue());
	}
	public static TeacherAreaStatus get(int value) {
		TeacherAreaStatus tmp = null;
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