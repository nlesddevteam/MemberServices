package com.esdnl.survey.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.esdnl.servlet.FormElementFormat;
import com.esdnl.util.StringUtils;

public class SurveyBean {

	private int surveyId;
	private String name;
	private String password;
	private Date startDate;
	private Date endDate;
	private String introduction;
	private String Instructions;
	private String thankYouMessage;
	private int responseCount;
	private boolean internal;

	public int getSurveyId() {

		return surveyId;
	}

	public void setSurveyId(int surveyId) {

		this.surveyId = surveyId;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getPassword() {

		return password;
	}

	public boolean hasPassword() {

		return !StringUtils.isEmpty(this.password);
	}

	public void setPassword(String password) {

		this.password = password;
	}

	public Date getStartDate() {

		return startDate;
	}

	public String getStartDateFormatted() {

		return new SimpleDateFormat(FormElementFormat.DATE_FORMAT).format(getStartDate());
	}

	public void setStartDate(Date startDate) {

		this.startDate = startDate;
	}

	public Date getEndDate() {

		return endDate;
	}

	public String getEndDateFormatted() {

		return new SimpleDateFormat(FormElementFormat.DATE_FORMAT).format(getEndDate());
	}

	public void setEndDate(Date endDate) {

		this.endDate = endDate;
	}

	public String getIntroduction() {

		return introduction;
	}

	public boolean hasIntroduction() {

		return !StringUtils.isEmpty(this.introduction);
	}

	public void setIntroduction(String introduction) {

		this.introduction = introduction;
	}

	public String getInstructions() {

		return Instructions;
	}

	public boolean hasInstructions() {

		return !StringUtils.isEmpty(this.Instructions);
	}

	public void setInstructions(String instructions) {

		Instructions = instructions;
	}

	public String getThankYouMessage() {

		return thankYouMessage;
	}

	public void setThankYouMessage(String thankYouMessage) {

		this.thankYouMessage = thankYouMessage;
	}

	public int getResponseCount() {

		return responseCount;
	}

	public void setResponseCount(int responseCount) {

		this.responseCount = responseCount;
	}

	public boolean isInternal() {

		return internal;
	}

	public void setInternal(boolean internal) {

		this.internal = internal;
	}

}
