package com.nlesd.eecd.bean;

import java.io.Serializable;

public class EECDExportQuestionBean implements Serializable {
	private static final long serialVersionUID = -844016122427000813L;
	private int questionSort;
	private String questionText;
	private String questionAnswer;
	public int getQuestionSort() {
		return questionSort;
	}
	public void setQuestionSort(int questionSort) {
		this.questionSort = questionSort;
	}
	public String getQuestionText() {
		return questionText;
	}
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	public String getQuestionAnswer() {
		return questionAnswer;
	}
	public void setQuestionAnswer(String questionAnswer) {
		this.questionAnswer = questionAnswer;
	}
}
