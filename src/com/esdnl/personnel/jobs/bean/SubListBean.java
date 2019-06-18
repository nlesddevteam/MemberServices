package com.esdnl.personnel.jobs.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import com.awsd.common.Utils;
import com.awsd.school.Grade;
import com.awsd.school.Subject;
import com.awsd.school.bean.RegionBean;
import com.esdnl.personnel.jobs.constants.SubstituteListConstant;
import com.esdnl.personnel.jobs.dao.SubListGradeManager;
import com.esdnl.personnel.jobs.dao.SubListSubjectManager;

public class SubListBean implements Serializable {

	private static final long serialVersionUID = 3404745152098015701L;

	private int id;
	private String title;
	private SubstituteListConstant type;
	private RegionBean region;
	private String school_year;
	private Vector<Subject> subjects;
	private Vector<Grade> grades;
	private Date expiry_date;
	private boolean active;

	public SubListBean() {

		this.id = -1;
		this.title = null;
		this.type = null;
		this.region = null;
		this.school_year = null;
		this.subjects = null;
		this.grades = null;
		this.active = false;
	}

	public void setId(int id) {

		this.id = id;
	}

	public int getId() {

		return this.id;
	}

	public void setTitle(String title) {

		this.title = title;
	}

	public String getTitle() {

		return this.title;
	}

	public void setType(SubstituteListConstant type) {

		this.type = type;
	}

	public SubstituteListConstant getType() {

		return this.type;
	}

	public void setRegion(RegionBean region) {

		this.region = region;
	}

	public RegionBean getRegion() {

		return this.region;
	}

	public void setSchoolYear(String school_year) {

		this.school_year = school_year;
	}

	public String getSchoolYear() {

		return this.school_year;
	}

	public void addSubjectArea(Subject s) {

		if (subjects == null)
			subjects = new Vector<Subject>();

		this.subjects.add(s);
	}

	public void addSubjectAreaCollection(Collection<Subject> c) {

		if (subjects == null)
			subjects = new Vector<Subject>();

		this.subjects.addAll(c);
	}

	@SuppressWarnings("unchecked")
	public Subject[] getSubjectAreas() {

		try {
			if (subjects == null)
				subjects = SubListSubjectManager.getSubListSubjectBeansCollection(this);
		}
		catch (JobOpportunityException e) {
			return null;
		}

		return (Subject[]) subjects.toArray(new Subject[0]);
	}

	public void addGradeLevel(Grade grd) {

		if (grades == null)
			grades = new Vector<Grade>();

		this.grades.add(grd);
	}

	public void addGradeLevelCollection(Collection<Grade> c) {

		if (grades == null)
			grades = new Vector<Grade>();

		this.grades.addAll(c);
	}

	@SuppressWarnings("unchecked")
	public Grade[] getGrades() {

		try {
			if (grades == null)
				grades = SubListGradeManager.getSubListGradeBeansCollection(this);
		}
		catch (JobOpportunityException e) {
			return null;
		}

		return (Grade[]) grades.toArray(new Grade[0]);
	}

	public Date getExpiryDate() {

		return this.expiry_date;
	}

	public void setExpiryDate(Date expiry_date) {

		this.expiry_date = expiry_date;

		if ((this.expiry_date != null) && (id < 1)) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(this.expiry_date);
			this.school_year = Utils.getSchoolYear(cal);
		}
	}

	public boolean isActive() {

		return this.active;
	}

	public void setActive(boolean active) {

		this.active = active;
	}

	public String toString() {

		return this.title;
	}

	public boolean equals(Object o) {

		boolean check = true;

		if (!(o instanceof SubListBean))
			check = false;
		else if (((SubListBean) o).getId() != this.id)
			check = false;

		return check;
	}
}