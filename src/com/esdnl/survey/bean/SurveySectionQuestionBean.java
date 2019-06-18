package com.esdnl.survey.bean;

import java.util.ArrayList;

import com.esdnl.survey.constant.SurveyQuestionTypeConstant;

public class SurveySectionQuestionBean {

	private int sectionId;
	private int questionId;
	private SurveyQuestionTypeConstant questionType;
	private String questionBody;
	private int sortOrder;
	private ArrayList<SurveySectionQuestionOptionBean> options = null;
	private boolean manditory = true;
	private int bullets;
	private int bulletLength;

	public boolean isManditory() {

		return manditory;
	}

	public void setManditory(boolean manditory) {

		this.manditory = manditory;
	}

	public SurveySectionQuestionBean() {

		this.sectionId = 0;
		this.questionId = 0;
		this.questionType = null;
		this.questionBody = null;
		this.sortOrder = 0;
		this.options = null;
	}

	public int getSectionId() {

		return sectionId;
	}

	public void setSectionId(int sectionId) {

		this.sectionId = sectionId;
	}

	public int getQuestionId() {

		return questionId;
	}

	public void setQuestionId(int questionId) {

		this.questionId = questionId;
	}

	public SurveyQuestionTypeConstant getQuestionType() {

		return questionType;
	}

	public void setQuestionType(SurveyQuestionTypeConstant questionType) {

		this.questionType = questionType;
	}

	public String getQuestionBody() {

		return questionBody;
	}

	public void setQuestionBody(String questionBody) {

		this.questionBody = questionBody;
	}

	public int getSortOrder() {

		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {

		this.sortOrder = sortOrder;
	}

	public void setOptions(ArrayList<SurveySectionQuestionOptionBean> options) {

		this.options = options;
	}

	public void addMultipleChoiceOption(SurveySectionQuestionOptionBean option) {

		if (this.options == null)
			options = new ArrayList<SurveySectionQuestionOptionBean>();

		options.add(option);
	}

	public SurveySectionQuestionOptionBean[] getMultipleChoiceOptions() {

		if (this.options == null)
			return null;

		return (SurveySectionQuestionOptionBean[]) this.options.toArray(new SurveySectionQuestionOptionBean[0]);
	}

	public int getMultipleChoiceOptionCount() {

		int num = 0;

		if (options != null)
			num = options.size();

		return num;
	}

	public int getBullets() {

		return bullets;
	}

	public void setBullets(int bullets) {

		this.bullets = bullets;
	}

	public int getBulletLength() {

		return bulletLength;
	}

	public void setBulletLength(int bulletLength) {

		this.bulletLength = bulletLength;
	}

}
