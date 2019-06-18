package com.esdnl.survey.constant;

public class SurveyQuestionTypeConstant {

	private int value;
	private String desc;

	public static final SurveyQuestionTypeConstant MULTIPLE_CHOICE_SINGLE_ANSWER = new SurveyQuestionTypeConstant(1, "MULTIPLE CHOICE - SINGLE ANSWER");
	public static final SurveyQuestionTypeConstant MULTIPLE_CHOICE_MULTIPLE_ANSWER = new SurveyQuestionTypeConstant(2, "MULTIPLE CHOICE - MULTIPLE ANSWER");
	public static final SurveyQuestionTypeConstant TRUE_OR_FALSE = new SurveyQuestionTypeConstant(3, "TRUE OR FALSE");
	public static final SurveyQuestionTypeConstant SHORT_ANSWER = new SurveyQuestionTypeConstant(4, "SHORT ANSWER");
	public static final SurveyQuestionTypeConstant LONG_ANSWER = new SurveyQuestionTypeConstant(5, "LONG ANSWER");
	public static final SurveyQuestionTypeConstant MULTIPLE_RATINGS = new SurveyQuestionTypeConstant(6, "MULTIPLE RATINGS");
	public static final SurveyQuestionTypeConstant MULTIPLE_BULLETS = new SurveyQuestionTypeConstant(7, "MULTIPLE BULLET POINTS");

	public static final SurveyQuestionTypeConstant[] ALL = new SurveyQuestionTypeConstant[] {
			MULTIPLE_CHOICE_SINGLE_ANSWER, MULTIPLE_CHOICE_MULTIPLE_ANSWER, MULTIPLE_RATINGS, TRUE_OR_FALSE, SHORT_ANSWER,
			LONG_ANSWER, MULTIPLE_BULLETS
	};

	private SurveyQuestionTypeConstant(int value, String desc) {

		this.value = value;
		this.desc = desc;
	}

	public int getValue() {

		return this.value;
	}

	public String getDescription() {

		return this.desc;
	}

	public boolean equals(Object o) {

		if (!(o instanceof SurveyQuestionTypeConstant))
			return false;
		else if (o == null)
			return false;
		else
			return (this.getValue() == ((SurveyQuestionTypeConstant) o).getValue());
	}

	public static SurveyQuestionTypeConstant get(int value) {

		SurveyQuestionTypeConstant tmp = null;

		for (int i = 0; i < ALL.length; i++) {
			if (ALL[i].getValue() == value) {
				tmp = ALL[i];
				break;
			}
		}

		return tmp;
	}

	public String toString() {

		return this.getDescription();
	}
}
