package com.esdnl.personnel.jobs.bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.commons.lang.StringEscapeUtils;

public class InterviewSummaryScoreBean {

	private int interviewSummaryScoreId;
	private int interviewSummaryId;
	private String interviewer;
	private double score1;
	private double score2;
	private double score3;
	private double score4;
	private double score5;
	private double score6;
	private double score7;
	private double score8;
	private double score9;
	private double score10;

	public InterviewSummaryScoreBean() {

		this.interviewSummaryScoreId = 0;
		this.interviewer = null;
		this.score1 = 0;
		this.score2 = 0;
		this.score3 = 0;
		this.score4 = 0;
		this.score5 = 0;
		this.score6 = 0;
		this.score7 = 0;
		this.score8 = 0;
		this.score9 = 0;
		this.score10 = 0;
	}

	public int getInterviewSummaryId() {

		return this.interviewSummaryId;
	}

	public void setInterviewSummaryId(int interviewSummaryId) {

		this.interviewSummaryId = interviewSummaryId;
	}

	public int getInterviewSummaryScoreId() {

		return this.interviewSummaryScoreId;
	}

	public void setInterviewSummaryScoreId(int interviewSummaryScoreId) {

		this.interviewSummaryScoreId = interviewSummaryScoreId;
	}

	public String getInterviewer() {

		return interviewer;
	}

	public void setInterviewer(String interviewer) {

		this.interviewer = interviewer;
	}

	public double getScore1() {

		return score1;
	}

	public void setScore1(double score1) {

		this.score1 = score1;
	}

	public double getScore2() {

		return score2;
	}

	public void setScore2(double score2) {

		this.score2 = score2;
	}

	public double getScore3() {

		return score3;
	}

	public void setScore3(double score3) {

		this.score3 = score3;
	}

	public double getScore4() {

		return score4;
	}

	public void setScore4(double score4) {

		this.score4 = score4;
	}

	public double getScore5() {

		return score5;
	}

	public void setScore5(double score5) {

		this.score5 = score5;
	}

	public double getScore6() {

		return score6;
	}

	public void setScore6(double score6) {

		this.score6 = score6;
	}

	public double getScore7() {

		return score7;
	}

	public void setScore7(double score7) {

		this.score7 = score7;
	}

	public double getScore8() {

		return score8;
	}

	public void setScore8(double score8) {

		this.score8 = score8;
	}

	public double getScore9() {

		return score9;
	}

	public void setScore9(double score9) {

		this.score9 = score9;
	}

	public double getScore10() {

		return score10;
	}

	public void setScore10(double score10) {

		this.score10 = score10;
	}

	public double getWeigthedTotalScore(InterviewGuideBean guide)
			throws IllegalArgumentException,
				IllegalAccessException,
				InvocationTargetException,
				SecurityException,
				NoSuchMethodException {

		ArrayList<InterviewGuideQuestionBean> qs = guide.getQuestions();
		InterviewGuideQuestionBean q = null;
		Method method = null;

		double total = 0, score = 0;

		for (int i = 0; i < guide.getQuestionCount(); i++) {
			q = qs.get(i);

			if (q.getWeight() > 0) {
				method = this.getClass().getDeclaredMethod("getScore" + (i + 1));

				score = ((Double) method.invoke(this)).doubleValue();

				total += (q.getWeight() * score);
			}
		}

		return total;
	}

	public double getOverallScore(InterviewGuideBean guide)
			throws IllegalArgumentException,
				IllegalAccessException,
				InvocationTargetException,
				SecurityException,
				NoSuchMethodException {

		ArrayList<InterviewGuideQuestionBean> qs = guide.getQuestions();
		InterviewGuideQuestionBean q = null;
		Method method = null;

		double overall = 0, score = 0;
		int q_cnt = 0;

		for (int i = 0; i < guide.getQuestionCount(); i++) {
			q = qs.get(i);

			if (q.getWeight() > 0) {
				method = this.getClass().getDeclaredMethod("getScore" + (i + 1));

				score = ((Double) method.invoke(this)).doubleValue();

				//if (score > 0) { // 0 means question was skipped!
				overall += (q.getWeight() * score);

				q_cnt++;
				//}
			}
		}

		overall = overall / q_cnt;

		return overall;
	}

	public String toXML() {

		StringBuffer sb = new StringBuffer();

		sb.append("<INTERVIEW-SUMMARY-SCORE interviewSummaryScoreId='"
				+ Integer.toString(this.getInterviewSummaryScoreId()) + "' interviewSummaryId='"
				+ Integer.toString(this.getInterviewSummaryId()) + "' interviewer='"
				+ StringEscapeUtils.escapeXml(this.getInterviewer()) + "' score1='" + Double.toString(this.getScore1())
				+ "' score2='" + Double.toString(this.getScore2()) + "' score3='" + Double.toString(this.getScore3())
				+ "' score4='" + Double.toString(this.getScore4()) + "' score5='" + Double.toString(this.getScore5())
				+ "' score6='" + Double.toString(this.getScore6()) + "' score7='" + Double.toString(this.getScore7())
				+ "' score8='" + Double.toString(this.getScore8()) + "' score9='" + Double.toString(this.getScore9())
				+ "' score10='" + Double.toString(this.getScore10()) + "' />");

		return sb.toString();
	}

}
