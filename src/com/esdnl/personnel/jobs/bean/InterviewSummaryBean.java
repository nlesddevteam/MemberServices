package com.esdnl.personnel.jobs.bean;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;

import com.esdnl.personnel.jobs.constants.JobTypeConstant;
import com.esdnl.servlet.FormElementFormat;

public class InterviewSummaryBean {

	private int interviewSummaryId;
	private ApplicantProfileBean candidate;
	private JobOpportunityBean competition;
	private String strengths;
	private String gaps;
	private ArrayList<InterviewSummaryScoreBean> interviewSummaryScoreBeans;
	private InterviewSummaryBean.SummaryRecommendation recommendation;
	private Date created;

	public InterviewSummaryBean() {

		this.candidate = null;
		this.competition = null;
		this.strengths = null;
		this.gaps = null;
		this.interviewSummaryScoreBeans = new ArrayList<InterviewSummaryScoreBean>();
		this.recommendation = SummaryRecommendation.UNKNOWN;
		this.created = null;
	}

	public int getInterviewSummaryId() {

		return interviewSummaryId;
	}

	public void setInterviewSummaryId(int interviewSummaryId) {

		this.interviewSummaryId = interviewSummaryId;
	}

	public ApplicantProfileBean getCandidate() {

		return candidate;
	}

	public void setCandidate(ApplicantProfileBean candidate) {

		this.candidate = candidate;
	}

	public JobOpportunityBean getCompetition() {

		return competition;
	}

	public void setCompetition(JobOpportunityBean competition) {

		this.competition = competition;
	}

	public String getStrengths() {

		return strengths;
	}

	public void setStrengths(String strengths) {

		this.strengths = strengths;
	}

	public String getGaps() {

		return gaps;
	}

	public void setGaps(String gaps) {

		this.gaps = gaps;
	}

	public ArrayList<InterviewSummaryScoreBean> getInterviewSummaryScoreBeans() {

		return this.interviewSummaryScoreBeans;
	}

	public void setInterviewSummaryScoreBeans(ArrayList<InterviewSummaryScoreBean> interviewSummaryScoreBeans) {

		this.interviewSummaryScoreBeans = interviewSummaryScoreBeans;
	}

	public void addInterviewSummaryScoreBean(InterviewSummaryScoreBean summaryScoreBean) {

		if (this.interviewSummaryScoreBeans == null) {
			this.interviewSummaryScoreBeans = new ArrayList<InterviewSummaryScoreBean>();
		}

		this.interviewSummaryScoreBeans.add(summaryScoreBean);
	}

	public void removeAllScores() {

		if (this.interviewSummaryScoreBeans != null) {
			this.interviewSummaryScoreBeans.clear();
		}
		else {
			this.interviewSummaryScoreBeans = new ArrayList<InterviewSummaryScoreBean>();
		}
	}

	public InterviewSummaryBean.SummaryRecommendation getRecommendation() {

		return recommendation;
	}

	public void setRecommendation(InterviewSummaryBean.SummaryRecommendation recommendation) {

		this.recommendation = recommendation;
	}

	public Date getCreated() {

		return created;
	}

	public String getCreatedFormatted() {

		return (new SimpleDateFormat(FormElementFormat.DATE_FORMAT)).format(this.getCreated());
	}

	public void setCreated(Date created) {

		this.created = created;
	}

	public double getOverallScore(InterviewGuideBean guide)
			throws IllegalArgumentException,
				SecurityException,
				IllegalAccessException,
				InvocationTargetException,
				NoSuchMethodException {

		double score = 0;

		if (guide == null || this.getInterviewSummaryScoreBeans() == null
				|| this.getInterviewSummaryScoreBeans().size() <= 0) {
			return 0;
		}

		for (InterviewSummaryScoreBean s : this.getInterviewSummaryScoreBeans()) {
			score += s.getOverallScore(guide);
		}

		score = score / this.getInterviewSummaryScoreBeans().size();

		return score;
	}

	public boolean isAdministrative() throws JobOpportunityException {

		return this.competition.getJobType().equal(JobTypeConstant.ADMINISTRATIVE)
				|| (this.competition.getJobType().equal(JobTypeConstant.POOL) && (this.competition.getAdRequest() != null)
						&& this.competition.getAdRequest().isAdminPool());
	}

	public boolean isLeadership() throws JobOpportunityException {

		return this.competition.getJobType().equal(JobTypeConstant.LEADERSHIP)
				|| (this.competition.getJobType().equal(JobTypeConstant.POOL) && (this.competition.getAdRequest() != null)
						&& this.competition.getAdRequest().isLeadershipPool());
	}

	public String toXML() {

		StringBuffer sb = new StringBuffer();

		sb.append("<INTERVIEW-SUMMARY interviewSummaryId='" + Integer.toString(this.getInterviewSummaryId())
				+ "' applicantId='" + this.getCandidate().getUID() + "' competitionNumber='"
				+ this.getCompetition().getCompetitionNumber() + "' position='" + this.getCompetition().getPositionTitle()
				+ "' strengths='" + StringEscapeUtils.escapeXml(this.getStrengths()) + "' gaps='"
				+ StringEscapeUtils.escapeXml(this.getGaps()) + "' recommendation='" + this.getRecommendation().getText()
				+ "' created='" + this.getCreatedFormatted() + "'>");

		sb.append("<INTERVIEW-SUMMARY-SCORES>");
		for (InterviewSummaryScoreBean issb : this.getInterviewSummaryScoreBeans()) {
			sb.append(issb.toXML());
		}
		sb.append("</INTERVIEW-SUMMARY-SCORES>");
		sb.append("</INTERVIEW-SUMMARY>");

		return sb.toString();
	}

	public enum SummaryRecommendation {

		UNKNOWN(0, "INVALID"), PERMANENT(1, "Highly Recommended"), LONG_TERM_REPLACEMENT(2,
				"Recommended"), SHORT_TERM_REPLACEMENT(3,
						"Recommend for short-term Replacement"), NOT_RECOMMENDED(4, "Not Recommended At This Time");

		private int value;
		private String text;

		SummaryRecommendation(int value, String text) {

			this.value = value;
			this.text = text;
		}

		public int getValue() {

			return this.value;
		}

		public String getText() {

			return this.text;
		}

		public boolean equals(SummaryRecommendation sr) {

			boolean check = false;

			if (sr != null) {
				check = this.value == sr.getValue();
			}
			else {
				check = false;
			}

			return check;
		}

		public String toString() {

			return this.getText();
		}

		public static SummaryRecommendation get(int value) {

			SummaryRecommendation tmp = UNKNOWN;

			for (SummaryRecommendation sr : SummaryRecommendation.values()) {
				if (sr.getValue() == value) {
					tmp = sr;
					break;
				}
			}

			return tmp;
		}
	}
}
