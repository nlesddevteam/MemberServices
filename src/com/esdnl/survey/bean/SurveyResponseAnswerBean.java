package com.esdnl.survey.bean;

public class SurveyResponseAnswerBean {

	private int responseId;
	private int questionId;
	private int optionId;
	private boolean trueOrFalse;
	private String body;
	private int rating;

	public int getRating() {

		return rating;
	}

	public void setRating(int rating) {

		this.rating = rating;
	}

	public SurveyResponseAnswerBean() {

		this.responseId = 0;
		this.questionId = 0;
		this.optionId = 0;
		this.trueOrFalse = false;
		this.body = null;
		this.rating = 0;
	}

	public int getResponseId() {

		return responseId;
	}

	public void setResponseId(int responseId) {

		this.responseId = responseId;
	}

	public int getQuestionId() {

		return questionId;
	}

	public void setQuestionId(int questionId) {

		this.questionId = questionId;
	}

	public int getOptionId() {

		return optionId;
	}

	public void setOptionId(int optionId) {

		this.optionId = optionId;
	}

	public boolean isTrueOrFalse() {

		return trueOrFalse;
	}

	public void setTrueOrFalse(boolean trueOrFalse) {

		this.trueOrFalse = trueOrFalse;
	}

	public String getBody() {

		return body;
	}

	public void setBody(String body) {

		this.body = body;
	}
}
