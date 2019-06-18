package com.esdnl.personnel.jobs.bean;

import java.util.ArrayList;
import java.util.Date;

public class InterviewGuideBean {

	private int guideId;
	private String title;
	ArrayList<InterviewGuideQuestionBean> questions;
	private int ratingScaleTop;
	private int ratingScaleBottom;
	private Date createdDate;
	private Date modifiedDate;
	private String schoolYear;
	private boolean activeList;
	private int numberOfQuestions;
	private String guideType;
	

	public InterviewGuideBean() {

		this.guideId = 0;
		this.title = "";
		this.questions = new ArrayList<InterviewGuideQuestionBean>();
		this.ratingScaleTop = 5;
		this.ratingScaleBottom = 1;
		this.activeList=false;
		this.schoolYear="";
		this.numberOfQuestions=0;
	}

	public int getGuideId() {

		return guideId;
	}

	public void setGuideId(int guideId) {

		this.guideId = guideId;
	}

	public String getTitle() {

		return title;
	}

	public void setTitle(String title) {

		this.title = title;
	}

	public ArrayList<InterviewGuideQuestionBean> getQuestions() {

		return questions;
	}

	public void setQuestions(ArrayList<InterviewGuideQuestionBean> questions) {

		this.questions = questions;
	}

	public void addQuestion(InterviewGuideQuestionBean question) {

		if (this.questions == null) {
			this.questions = new ArrayList<InterviewGuideQuestionBean>();
		}

		this.questions.add(question);
	}

	public void removeAllQuestions() {

		this.questions.clear();
	}

	public int getQuestionCount() {

		return this.questions.size();
	}

	public int getRatingScaleTop() {

		return ratingScaleTop;
	}

	public void setRatingScaleTop(int ratingScaleTop) {

		this.ratingScaleTop = ratingScaleTop;
	}

	public int getRatingScaleBottom() {

		return ratingScaleBottom;
	}

	public void setRatingScaleBottom(int ratingScaleBottom) {

		this.ratingScaleBottom = ratingScaleBottom;
	}

	public Date getCreatedDate() {

		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {

		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {

		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {

		this.modifiedDate = modifiedDate;
	}
	public boolean getActiveList() {
		return activeList;
	}

	public void setActiveList(boolean activeList) {
		this.activeList = activeList;
	}

	public String getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}

	public int getNumberOfQuestions() {
		return numberOfQuestions;
	}

	public void setNumberOfQuestions(int numberOfQuestions) {
		this.numberOfQuestions = numberOfQuestions;
	}

	public String getGuideType() {
		return guideType;
	}

	public void setGuideType(String guideType) {
		this.guideType = guideType;
	}
}
