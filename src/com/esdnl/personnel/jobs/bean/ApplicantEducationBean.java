package com.esdnl.personnel.jobs.bean;

import java.io.Serializable;
import java.util.Date;

public class ApplicantEducationBean implements Serializable {

	private static final long serialVersionUID = -660787786507967304L;
	private int id;
	private String sin;
	private Date from;
	private Date to;
	private String institution;
	private String program;
	private int major;
	private int major_crs;
	private int minor;
	private int minor_crs;
	private String degree;

	public ApplicantEducationBean() {

		this.id = -1;
		this.sin = null;
		this.from = null;
		this.to = null;
		this.institution = null;
		this.program = null;
		this.major = 0;
		this.major_crs = 0;
		this.minor = 0;
		this.minor_crs = 0;
		this.degree = null;
	}

	public int getId() {

		return this.id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getSIN() {

		return this.sin;
	}

	public void setSIN(String sin) {

		this.sin = sin;
	}

	public Date getFrom() {

		return this.from;
	}

	public void setFrom(Date from) {

		this.from = from;
	}

	public Date getTo() {

		return this.to;
	}

	public void setTo(Date to) {

		this.to = to;
	}

	public String getInstitutionName() {

		return this.institution;
	}

	public void setInstitutionName(String institution) {

		this.institution = institution;
	}

	public String getProgramFacultyName() {

		return this.program;
	}

	public void setProgramFacultyName(String program) {

		this.program = program;
	}

	public int getMajor() {

		return this.major;
	}

	public void setMajor(int major) {

		this.major = major;
	}

	public int getNumberMajorCourses() {

		return this.major_crs;
	}

	public void setNumberMajorCourses(int major_crs) {

		this.major_crs = major_crs;
	}

	public int getMinor() {

		return this.minor;
	}

	public void setMinor(int minor) {

		this.minor = minor;
	}

	public int getNumberMinorCourses() {

		return this.minor_crs;
	}

	public void setNumberMinorCourses(int minor_crs) {

		this.minor_crs = minor_crs;
	}

	public String getDegreeConferred() {

		return this.degree;
	}

	public void setDegreeConferred(String degree) {

		this.degree = degree;
	}
}