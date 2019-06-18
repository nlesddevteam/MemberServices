package com.esdnl.personnel.jobs.bean;


public class InterviewGuideQuestionBean {

	private int questionId;
	private int guideId;
	private String question;
	private double weight;

	public InterviewGuideQuestionBean() {

		this.questionId = 0;
		this.guideId = 0;
		this.question = "";
		this.weight = 1.0;
	}

	public int getQuestionId() {

		return questionId;
	}

	public void setQuestionId(int questionId) {

		this.questionId = questionId;
	}

	public int getGuideId() {

		return guideId;
	}

	public void setGuideId(int guideId) {

		this.guideId = guideId;
	}

	public String getQuestion() {

		return question;
	}

	public void setQuestion(String question) {

		this.question = question;
	}

	public double getWeight() {

		return weight;
	}

	public void setWeight(double weight) {

		this.weight = weight;
	}

}
