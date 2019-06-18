package com.esdnl.personnel.jobs.bean;

public class NLESDRecommendationTrackingFormBean implements Comparable<NLESDRecommendationTrackingFormBean>{
	private String candidateName;
	private String qualifications;
	private String experience;
	private String referenceScore;
	private String referenceRecommendation;
	private String interviewScore1;
	private String interviewScore2;
	private String interviewScore3;
	private String interviewScoreTotal;
	private int overallRanking;
	private String comments;
	private String interviewHeader;
	private String interviewRow;
	private int totalInterviews;
	private int scaleBottom;
	private int scaleTop;
	private int scaleTotal;
	private String referenceScale;
	
	
	public String getReferenceScale() {
		return referenceScale;
	}
	public void setReferenceScale(String referenceScale) {
		this.referenceScale = referenceScale;
	}
	public NLESDRecommendationTrackingFormBean()
	{
		//use for sorting
		interviewScoreTotal="0";
	}
	public String getCandidateName() {
		return candidateName;
	}
	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}
	public String getQualifications() {
		return qualifications;
	}
	public void setQualifications(String qualifications) {
		this.qualifications = qualifications;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getReferenceScore() {
		return referenceScore;
	}
	public void setReferenceScore(String referenceScore) {
		this.referenceScore = referenceScore;
	}
	public String getReferenceRecommendation() {
		return referenceRecommendation;
	}
	public void setReferenceRecommendation(String referenceRecommendation) {
		this.referenceRecommendation = referenceRecommendation;
	}
	public String getInterviewScore1() {
		return interviewScore1;
	}
	public void setInterviewScore1(String interviewScore1) {
		this.interviewScore1 = interviewScore1;
	}
	public String getInterviewScore2() {
		return interviewScore2;
	}
	public void setInterviewScore2(String interviewScore2) {
		this.interviewScore2 = interviewScore2;
	}
	public String getInterviewScore3() {
		return interviewScore3;
	}
	public void setInterviewScore3(String interviewScore3) {
		this.interviewScore3 = interviewScore3;
	}
	public String getInterviewScoreTotal() {
		return interviewScoreTotal;
	}
	public void setInterviewScoreTotal(String interviewScoreTotal) {
		this.interviewScoreTotal = interviewScoreTotal;
	}
	public int getOverallRanking() {
		return overallRanking;
	}
	public void setOverallRanking(int overallRanking) {
		this.overallRanking = overallRanking;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	  @Override
	  public int compareTo(NLESDRecommendationTrackingFormBean app) {
	   return app.getInterviewScoreTotal().compareTo(this.getInterviewScoreTotal());
	  }
	public String getInterviewHeader() {
		return interviewHeader;
	}
	public void setInterviewHeader(String interviewHeader) {
		this.interviewHeader = interviewHeader;
	}
	public String getInterviewRow() {
		return interviewRow;
	}
	public void setInterviewRow(String interviewRow) {
		this.interviewRow = interviewRow;
	}
	public int getTotalInterviews() {
		return totalInterviews;
	}
	public void setTotalInterviews(int totalInterviews) {
		this.totalInterviews = totalInterviews;
	}
	public int getScaleBottom() {
		return scaleBottom;
	}
	public void setScaleBottom(int scaleBottom) {
		this.scaleBottom = scaleBottom;
	}
	public int getScaleTop() {
		return scaleTop;
	}
	public void setScaleTop(int scaleTop) {
		this.scaleTop = scaleTop;
	}
	public int getScaleTotal() {
		return scaleTotal;
	}
	public void setScaleTotal(int scaleTotal) {
		this.scaleTotal = scaleTotal;
	}
}
