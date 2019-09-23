package com.nlesd.eecd.bean;

import java.util.TreeMap;

public class EECDExportItemBean {
	public String teacherName;
	public String currentSchool;
	public String currentAssignment;
	public String seniority;
	public String committees;
	public String areaDescription;
	public TreeMap<Integer,EECDExportQuestionBean> questions;
	public EECDExportItemBean() {
		questions=new TreeMap<Integer,EECDExportQuestionBean>();
	}
	public String getAreaDescription() {
		return areaDescription;
	}
	public void setAreaDescription(String areaDescription) {
		this.areaDescription = areaDescription;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getCurrentSchool() {
		return currentSchool;
	}
	public void setCurrentSchool(String currentSchool) {
		this.currentSchool = currentSchool;
	}
	public String getCurrentAssignment() {
		return currentAssignment;
	}
	public void setCurrentAssignment(String currentAssignment) {
		this.currentAssignment = currentAssignment;
	}
	public String getSeniority() {
		return seniority;
	}
	public void setSeniority(String seniority) {
		this.seniority = seniority;
	}
	public String getCommittees() {
		return committees;
	}
	public void setCommittees(String committees) {
		this.committees = committees;
	}
	public TreeMap<Integer, EECDExportQuestionBean> getQuestions() {
		return questions;
	}
	public void setQuestions(TreeMap<Integer, EECDExportQuestionBean> questions) {
		this.questions = questions;
	}

}
