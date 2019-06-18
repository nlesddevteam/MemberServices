package com.esdnl.personnel.sss.domain.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.awsd.personnel.Personnel;
import com.awsd.school.School;
import com.esdnl.util.StringUtils;

public class StudentProgrammingInfoBean {

	private String studentId;
	private String studentName;

	private School school;
	private String schoolYear;

	private String exceptionality;
	private School.GRADE grade;
	private String pervasiveCategory;
	private String stream;
	private boolean issp;
	private boolean iep;
	private int p1Courses;
	private int p2Courses;
	private int p3Courses;
	private int p4CC;
	private int p4NCC;
	private int p4PP;
	private int p4NCP;
	private boolean p5;

	private Personnel modifiedBy;
	private Date modifiedDate;

	//refers to previous year
	private boolean studentAssistantSupport;

	//refers to next year
	private School transitionSchool;
	private boolean leaving;
	private boolean graduating;
	private boolean moving;

	boolean dirty;

	public StudentProgrammingInfoBean() {

		super();

		dirty = false;
	}

	public String getStudentId() {

		return studentId;
	}

	public void setStudentId(String studentId) {

		if (!StringUtils.isEmpty(studentId) && !studentId.equalsIgnoreCase(this.studentId)) {
			this.studentId = studentId;
			dirty = true;
		}
	}

	public String getStudentName() {

		return studentName;
	}

	public void setStudentName(String studentName) {

		if (!StringUtils.isEmpty(studentName) && !studentName.equalsIgnoreCase(this.studentName)) {
			this.studentName = studentName;
			dirty = true;
		}
	}

	public String getSchoolYear() {

		return schoolYear;
	}

	public void setSchoolYear(String schoolYear) {

		if (!StringUtils.isEmpty(schoolYear) && !schoolYear.equalsIgnoreCase(this.schoolYear)) {
			this.schoolYear = schoolYear;
			dirty = true;
		}
	}

	public String getExceptionality() {

		return exceptionality;
	}

	public void setExceptionality(String exceptionality) {

		if (!StringUtils.isEmpty(exceptionality) && !exceptionality.equalsIgnoreCase(this.exceptionality)) {
			this.exceptionality = exceptionality;
			dirty = true;
		}
	}

	public School.GRADE getGrade() {

		return grade;
	}

	public void setGrade(School.GRADE grade) {

		if (grade != null && !grade.equals(this.grade)) {
			this.grade = grade;
			dirty = true;
		}
	}

	public String getPervasiveCategory() {

		return pervasiveCategory;
	}

	public void setPervasiveCategory(String pervasiveCategory) {

		if (!StringUtils.isEmpty(pervasiveCategory) && !pervasiveCategory.equalsIgnoreCase(this.pervasiveCategory)) {
			this.pervasiveCategory = pervasiveCategory;
			dirty = true;
		}
	}

	public String getStream() {

		return stream;
	}

	public void setStream(String stream) {

		if (!StringUtils.isEmpty(stream) && !stream.equalsIgnoreCase(this.stream)) {
			this.stream = stream;
			dirty = true;
		}
	}

	public boolean isIssp() {

		return issp;
	}

	public void setIssp(boolean issp) {

		if (issp != this.issp) {
			this.issp = issp;
			dirty = true;
		}
	}

	public boolean isIep() {

		return iep;
	}

	public void setIep(boolean iep) {

		if (iep != this.iep) {
			this.iep = iep;
			dirty = true;
		}
	}

	public int getP1Courses() {

		return p1Courses;
	}

	public void setP1Courses(int p1Courses) {

		if (p1Courses != this.p1Courses) {
			this.p1Courses = p1Courses;
			dirty = true;
		}
	}

	public int getP2Courses() {

		return p2Courses;
	}

	public void setP2Courses(int p2Courses) {

		if (p2Courses != this.p2Courses) {
			this.p2Courses = p2Courses;
			dirty = true;
		}
	}

	public int getP3Courses() {

		return p3Courses;
	}

	public void setP3Courses(int p3Courses) {

		if (p3Courses != this.p3Courses) {
			this.p3Courses = p3Courses;
			dirty = true;
		}
	}

	public int getP4CC() {

		return p4CC;
	}

	public void setP4CC(int p4cc) {

		if (p4cc != this.p4CC) {
			p4CC = p4cc;
			dirty = true;
		}
	}

	public int getP4NCC() {

		return p4NCC;
	}

	public void setP4NCC(int p4ncc) {

		if (p4ncc != this.p4NCC) {
			p4NCC = p4ncc;
			dirty = true;
		}
	}

	public int getP4PP() {

		return p4PP;
	}

	public void setP4PP(int p4pp) {

		if (p4pp != this.p4PP) {
			p4PP = p4pp;
			dirty = true;
		}
	}

	public int getP4NCP() {

		return p4NCP;
	}

	public void setP4NCP(int p4ncp) {

		if (p4ncp != this.p4NCP) {
			p4NCP = p4ncp;
			dirty = true;
		}
	}

	public boolean isP5() {

		return p5;
	}

	public void setP5(boolean p5) {

		if (p5 != this.p5) {
			this.p5 = p5;
			dirty = true;
		}
	}

	public boolean isStudentAssistantSupport() {

		return studentAssistantSupport;
	}

	public void setStudentAssistantSupport(boolean studentAssistantSupport) {

		if (studentAssistantSupport != this.studentAssistantSupport) {
			this.studentAssistantSupport = studentAssistantSupport;
			dirty = true;
		}
	}

	public School getSchool() {

		return school;
	}

	public void setSchool(School school) {

		if (school != null && !school.equals(this.school)) {
			this.school = school;
			dirty = true;
		}
	}

	public Personnel getModifiedBy() {

		return modifiedBy;
	}

	public void setModifiedBy(Personnel modifiedBy) {

		if (modifiedBy != null && !modifiedBy.equals(this.modifiedBy)) {
			this.modifiedBy = modifiedBy;
			dirty = true;
		}
	}

	public Date getModifiedDate() {

		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {

		if (modifiedDate != null && !modifiedDate.equals(this.modifiedDate)) {
			this.modifiedDate = modifiedDate;
			dirty = true;
		}
	}

	public School getTransitionSchool() {

		return transitionSchool;
	}

	public void setTransitionSchool(School transitionSchool) {

		if (transitionSchool != null && !transitionSchool.equals(this.transitionSchool)) {
			this.transitionSchool = transitionSchool;
			dirty = true;
		}
	}

	public boolean isLeaving() {

		return leaving;
	}

	public void setLeaving(boolean leaving) {

		if (leaving != this.leaving) {
			this.leaving = leaving;
			dirty = true;
		}
	}

	public boolean isGraduating() {

		return graduating;
	}

	public void setGraduating(boolean graduating) {

		if (graduating != this.graduating) {
			this.graduating = graduating;
			dirty = true;
		}
	}

	public boolean isMoving() {

		return moving;
	}

	public void setMoving(boolean moving) {

		if (moving != this.moving) {
			this.moving = moving;
			dirty = true;
		}
	}

	public boolean isStaying() {

		return (this.transitionSchool == null && !this.graduating && !this.leaving && !this.moving);
	}

	public boolean isDirty() {

		return dirty;
	}

	public void setDirty(boolean dirty) {

		this.dirty = dirty;
	}

	public String toXML() {

		StringBuffer xml = new StringBuffer();

		xml.append("<StudentProgrammingInfoBean>");

		xml.append("<StudentId>" + this.studentId + "</StudentId>");

		xml.append("<StudentName>" + this.studentName + "</StudentName>");

		if (this.school != null)
			xml.append(this.school.toXML());

		xml.append("<SchoolYear>" + this.schoolYear + "</SchoolYear>");

		xml.append("<Exceptionality>" + this.exceptionality + "</Exceptionality>");

		if (this.grade != null)
			xml.append("<Grade>" + this.grade.getValue() + "</Grade>");

		xml.append("<PervasiveCategory>" + this.pervasiveCategory + "</PervasiveCategory>");

		xml.append("<Stream>" + this.stream + "</Stream>");

		xml.append("<ISSP>" + this.issp + "</ISSP>");

		xml.append("<IEP>" + this.iep + "</IEP>");

		xml.append("<P1>" + this.p1Courses + "</P1>");

		xml.append("<P2>" + this.p2Courses + "</P2>");

		xml.append("<P3>" + this.p3Courses + "</P3>");

		xml.append("<P4CC>" + this.p4CC + "</P4CC>");

		xml.append("<P4NCC>" + this.p4NCC + "</P4NCC>");

		xml.append("<P4PP>" + this.p4PP + "</P4PP>");

		xml.append("<P4NCP>" + this.p4NCP + "</P4NCP>");

		xml.append("<P5>" + this.p5 + "</P5>");

		xml.append("<StudentAssistantSupportApproved>" + this.studentAssistantSupport
				+ "</StudentAssistantSupportApproved>");

		xml.append("<Staying>" + this.isStaying() + "</Staying>");

		if (this.transitionSchool != null)
			xml.append("<TransitionSchool>" + this.transitionSchool.toXML() + "</TransitionSchool>");

		xml.append("<Graduating>" + this.isGraduating() + "</Graduating>");

		xml.append("<Leaving>" + this.isLeaving() + "</Leaving>");

		xml.append("<Moving>" + this.isMoving() + "</Moving>");

		if (this.modifiedBy != null)
			xml.append("<ModifiedBy>" + this.modifiedBy.toXML() + "</ModifiedBy>");

		if (this.modifiedDate != null)
			xml.append("<ModifiedDate>" + (new SimpleDateFormat("dd/MM/yyyy")).format(this.modifiedDate) + "</ModifiedDate>");

		xml.append("</StudentProgrammingInfoBean>");

		return xml.toString();
	}

}
