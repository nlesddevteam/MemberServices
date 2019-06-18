package com.esdnl.survey.bean;

import java.util.ArrayList;
import java.util.Date;

public class SurveyResponseBean {

	private int surveyId;
	private int responseId;
	private String ipAddress;
	private Date responseDate;

	private ArrayList<SurveyResponseAnswerBean> answers;

	public int getSurveyId() {

		return surveyId;
	}

	public void setSurveyId(int surveyId) {

		this.surveyId = surveyId;
	}

	public int getResponseId() {

		return responseId;
	}

	public void setResponseId(int responseId) {

		this.responseId = responseId;
	}

	public String getIpAddress() {

		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {

		this.ipAddress = ipAddress;
	}

	public Date getResponseDate() {

		return responseDate;
	}

	public void setResponseDate(Date responseDate) {

		this.responseDate = responseDate;
	}

	public void addAnswer(SurveyResponseAnswerBean answer) {

		if (this.answers == null)
			this.answers = new ArrayList<SurveyResponseAnswerBean>();

		this.answers.add(answer);
	}

	public SurveyResponseAnswerBean[] getAnswers() {

		if (this.answers == null)
			return null;

		return (SurveyResponseAnswerBean[]) this.answers.toArray(new SurveyResponseAnswerBean[0]);
	}

	public void setAnswers(ArrayList<SurveyResponseAnswerBean> answers) {

		this.answers = answers;
	}

}
