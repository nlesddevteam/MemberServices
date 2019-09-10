package com.nlesd.eecd.bean;

import java.io.Serializable;

public class EECDAreaQuestionBean implements Serializable {
	private static final long serialVersionUID = -844016122427000813L;
	private int id;
	private String questionText;
	private int questionSort;
	private int isActive;
	private int areaId;
	private String areaDescription;
	private String eligibleTeachers;
	private String teacherAnswer;
	private int personnelId;
	private int teacherAnswerId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQuestionText() {
		return questionText;
	}
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	public int getQuestionSort() {
		return questionSort;
	}
	public void setQuestionSort(int questionSort) {
		this.questionSort = questionSort;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public String getAreaDescription() {
		return areaDescription;
	}
	public void setAreaDescription(String areaDescription) {
		this.areaDescription = areaDescription;
	}
	public String getEligibleTeachers() {
		return eligibleTeachers;
	}
	public void setEligibleTeachers(String eligibleTeachers) {
		this.eligibleTeachers = eligibleTeachers;
	}
	public String getTeacherAnswer() {
		return teacherAnswer;
	}
	public void setTeacherAnswer(String teacherAnswer) {
		this.teacherAnswer = teacherAnswer;
	}
	public int getPersonnelId() {
		return personnelId;
	}
	public void setPersonnelId(int personnelId) {
		this.personnelId = personnelId;
	}
	public int getTeacherAnswerId() {
		return teacherAnswerId;
	}
	public void setTeacherAnswerId(int teacherAnswerId) {
		this.teacherAnswerId = teacherAnswerId;
	}
	
	
}
