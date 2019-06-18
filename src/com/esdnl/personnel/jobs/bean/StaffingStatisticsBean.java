package com.esdnl.personnel.jobs.bean;

import java.io.Serializable;

import net.sf.json.JSONObject;

public class StaffingStatisticsBean implements Serializable {

	private static final long serialVersionUID = -467523371689513022L;

	private int jobsPosted;
	private int applicationsReceived;
	private int recommendationsMade;
	private int offersAccepted;

	public int getJobsPosted() {

		return jobsPosted;
	}

	public void setJobsPosted(int jobsPosted) {

		this.jobsPosted = jobsPosted;
	}

	public int getApplicationsReceived() {

		return applicationsReceived;
	}

	public void setApplicationsReceived(int applicationsReceived) {

		this.applicationsReceived = applicationsReceived;
	}

	public int getRecommendationsMade() {

		return recommendationsMade;
	}

	public void setRecommendationsMade(int recommendationsMade) {

		this.recommendationsMade = recommendationsMade;
	}

	public int getOffersAccepted() {

		return offersAccepted;
	}

	public void setOffersAccepted(int offersAccepted) {

		this.offersAccepted = offersAccepted;
	}

	public static long getSerialversionuid() {

		return serialVersionUID;
	}

	public String toJSON() {

		JSONObject jsonObj = new net.sf.json.JSONObject();

		jsonObj.put("jobsPosted", getJobsPosted());
		jsonObj.put("applicationsReceived", getApplicationsReceived());
		jsonObj.put("recommendationsMade", getRecommendationsMade());
		jsonObj.put("offersAccepted", getOffersAccepted());

		return jsonObj.toString();
	}

}
