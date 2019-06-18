package com.esdnl.survey.bean;

import com.esdnl.util.StringUtils;

public class SurveySectionBean {

	private int surveyId;
	private int sectionId;
	private String heading;
	private boolean headerDisplayed;
	private int sortOrder;
	private String introduction;
	private String instructions;
	private int questionCount;

	public int getQuestionCount() {

		return questionCount;
	}

	public void setQuestionCount(int questionCount) {

		this.questionCount = questionCount;
	}

	public String getIntroduction() {

		return introduction;
	}

	public boolean hasIntroduction() {

		return !StringUtils.isEmpty(getIntroduction());
	}

	public void setIntroduction(String introduction) {

		this.introduction = introduction;
	}

	public String getInstructions() {

		return instructions;
	}

	public boolean hasInstructions() {

		return !StringUtils.isEmpty(getInstructions());
	}

	public void setInstructions(String instructions) {

		this.instructions = instructions;
	}

	public int getSurveyId() {

		return surveyId;
	}

	public void setSurveyId(int surveyId) {

		this.surveyId = surveyId;
	}

	public int getSectionId() {

		return sectionId;
	}

	public void setSectionId(int sectionId) {

		this.sectionId = sectionId;
	}

	public String getHeading() {

		return heading;
	}

	public void setHeading(String heading) {

		this.heading = heading;
	}

	public boolean isHeaderDisplayed() {

		return headerDisplayed;
	}

	public void setHeaderDisplayed(boolean headerDisplayed) {

		this.headerDisplayed = headerDisplayed;
	}

	public int getSortOrder() {

		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {

		this.sortOrder = sortOrder;
	}

	public boolean equals(Object o) {

		boolean check = false;

		if ((o != null) && (o instanceof SurveySectionBean)
				&& (((SurveySectionBean) o).getSectionId() == this.getSectionId()))
			check = true;

		return check;
	}
}
