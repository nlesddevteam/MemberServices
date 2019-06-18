package com.awsd.personnel;

import java.util.ArrayList;

public class PersonnelCategories extends ArrayList<PersonnelCategory> {

	private static final long serialVersionUID = -8938131068032674710L;

	public PersonnelCategories() throws PersonnelCategoryException {

		this.addAll(PersonnelCategoryDB.getPersonnelCategories());
	}
}