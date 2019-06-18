package com.nlesd.antibullyingpledge.constants;
public class SearchConstant {
	private int value;
	private String desc;
	public static final SearchConstant PLEASE_SELECT= new SearchConstant(-1, "Please Select");
	public static final SearchConstant CANCELLATION_CODE= new SearchConstant(1, "Cancellation Code like");
	public static final SearchConstant DATE_SUBMITTED= new SearchConstant(2, "Date Submitted equal to");
	public static final SearchConstant EMAIL= new SearchConstant(3, "Email like");
	public static final SearchConstant FIRST_NAME= new SearchConstant(4, "First Name like");
	public static final SearchConstant GRADE= new SearchConstant(5, "Grade equal to");
	public static final SearchConstant LAST_NAME= new SearchConstant(6, "Last Name like");
	public static final SearchConstant PLEDGED_CONFIRMED= new SearchConstant(7, "Pledge Confirmed");
	public static final SearchConstant SCHOOL= new SearchConstant(8, "School equal to");
	


	public static final SearchConstant[] ALL = new SearchConstant[] {
		PLEASE_SELECT, CANCELLATION_CODE, DATE_SUBMITTED, EMAIL, FIRST_NAME, GRADE, LAST_NAME, PLEDGED_CONFIRMED,SCHOOL
	};

	private SearchConstant(int value, String desc) {

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

		if (!(o instanceof SearchConstant))
			return false;
		else
			return (this.getValue() == ((SearchConstant) o).getValue());
	}

	public static SearchConstant get(int value) {

		SearchConstant tmp = null;

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
