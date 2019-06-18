package com.esdnl.survey.bean;

public class SurveySectionQuestionOptionBean {

	private int questionId;
	private int optionId;
	private String optionBody;
	private int sortOrder;
	private boolean other;

	public int getQuestionId() {

		return questionId;
	}

	public void setQuestionId(int questionId) {

		this.questionId = questionId;
	}

	public int getOptionId() {

		return optionId;
	}

	public void setOptionId(int answerId) {

		this.optionId = answerId;
	}

	public String getOptionBody() {

		return optionBody;
	}

	public void setOptionBody(String anwserBody) {

		this.optionBody = anwserBody;
	}

	public int getSortOrder() {

		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {

		this.sortOrder = sortOrder;
	}

	public boolean isOther() {

		return other;
	}

	public void setOther(boolean other) {

		this.other = other;
	}

}
