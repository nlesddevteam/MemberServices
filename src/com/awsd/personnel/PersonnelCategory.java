package com.awsd.personnel;

public class PersonnelCategory {

	private int cat_id;
	private String cat_name;
	private String cat_desc;

	public PersonnelCategory(int cat_id, String cat_name, String cat_desc) {

		this.cat_id = cat_id;
		this.cat_name = cat_name;
		this.cat_desc = cat_desc;
	}

	public int getPersonnelCategoryID() {

		return cat_id;
	}

	public String getPersonnelCategoryName() {

		return cat_name;
	}

	public String getPersonnelCategoryDescription() {

		return cat_desc;
	}

	public boolean equals(Object cat) {

		if (cat instanceof PersonnelCategory && (this.cat_id == ((PersonnelCategory) cat).getPersonnelCategoryID())) {
			return true;
		}
		else
			return false;
	}
}