package com.awsd.travel.bean;

public class TravelBudgetFiscalYearSummary {

	private double budget;
	private double claimed;
	private double deficit;

	public TravelBudgetFiscalYearSummary() {

		this(0, 0, 0);
	}

	public TravelBudgetFiscalYearSummary(double budget, double claimed, double deficit) {

		this.budget = budget;
		this.claimed = claimed;
		this.deficit = deficit;
	}

	public double getBudget() {

		return budget;
	}

	public void setBudget(double budget) {

		this.budget = budget;
	}

	public double getClaimed() {

		return claimed;
	}

	public void setClaimed(double claimed) {

		this.claimed = claimed;
	}

	public double getDeficit() {

		return deficit;
	}

	public void setDeficit(double deficit) {

		this.deficit = deficit;
	}

}
