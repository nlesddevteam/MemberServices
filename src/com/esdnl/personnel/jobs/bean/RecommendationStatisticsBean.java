package com.esdnl.personnel.jobs.bean;

public class RecommendationStatisticsBean {

	private int offersQueued;
	private int offersInProgress;
	private int offersExpired;

	public RecommendationStatisticsBean() {

		this.offersQueued = 0;
		this.offersInProgress = 0;
		this.offersExpired = 0;
	}

	public int getOffersQueued() {

		return offersQueued;
	}

	public void setOffersQueued(int offersQueued) {

		this.offersQueued = offersQueued;
	}

	public int getOffersInProgress() {

		return offersInProgress;
	}

	public void setOffersInProgress(int offersInProgress) {

		this.offersInProgress = offersInProgress;
	}

	public int getOffersExpired() {

		return offersExpired;
	}

	public void setOffersExpired(int offersExpired) {

		this.offersExpired = offersExpired;
	}

}
