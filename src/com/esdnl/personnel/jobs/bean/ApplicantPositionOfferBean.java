package com.esdnl.personnel.jobs.bean;

public class ApplicantPositionOfferBean {
	
	private JobOpportunityBean job = null;
	private TeacherRecommendationBean rec = null;
	
	public ApplicantPositionOfferBean(JobOpportunityBean job, TeacherRecommendationBean rec)
	{
		this.job = job;
		this.rec = rec;
	}
	
	public JobOpportunityBean getJob()
	{
		return this.job;
	}
	
	public TeacherRecommendationBean getRecommendation()
	{
		return this.rec;
	}
}
