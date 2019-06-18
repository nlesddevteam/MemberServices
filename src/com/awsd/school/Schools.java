package com.awsd.school;

import java.util.ArrayList;

import com.awsd.strike.SchoolStrikeGroup;
import com.awsd.weather.SchoolSystem;

public class Schools extends ArrayList<School> {

	private static final long serialVersionUID = -8679759458702230094L;

	public Schools() throws SchoolException {

		this(false);
	}

	public Schools(boolean empty) throws SchoolException {

		super();

		if (!empty) {
			super.addAll(SchoolDB.getSchools());
		}
	}

	public Schools(SchoolSystem ss) throws SchoolException {

		super.addAll(SchoolDB.getSchools(ss));
	}

	public Schools(SchoolStrikeGroup group) throws SchoolException {

		super.addAll(SchoolDB.getSchools(group));
	}

	public Schools(SchoolFamily family) throws SchoolException {

		super.addAll(SchoolDB.getSchools(family));
	}

	public boolean add(School o) {

		return super.add(o);
	}
}