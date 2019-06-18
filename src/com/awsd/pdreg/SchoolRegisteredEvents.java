package com.awsd.pdreg;

import java.util.HashMap;
import java.util.Vector;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.PersonnelException;
import com.awsd.school.School;

public class SchoolRegisteredEvents extends HashMap<Personnel, RegisteredEvents> {

	private static final long serialVersionUID = 706352798639762057L;

	private School s;
	private Vector<Personnel> teachers;

	public SchoolRegisteredEvents(School s) throws EventException, PersonnelException {

		this.s = s;
		this.teachers = PersonnelDB.getPersonnelList(s);

		for (Personnel p : this.teachers) {
			this.put(p, new RegisteredEvents(p));
		}
	}

	public School getSchool() {

		return s;
	}

	public Vector<Personnel> getSchoolTeachers() {

		return teachers;
	}
}