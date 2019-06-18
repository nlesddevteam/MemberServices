package com.awsd.travel.bean;

import com.awsd.personnel.Personnel;
import com.awsd.travel.TravelClaimException;

public class TravelBudget {

	private int budgetId;
	private Personnel personnel;
	private String fiscalYear;
	private double amount;
	private double amountClaimed;
	private double amountPreclaimed;
	private Division division;

	public TravelBudget() {

	}

	public TravelBudget(Personnel personnel, String fiscalYear, double amount) {

		this.personnel = personnel;
		this.fiscalYear = fiscalYear;
		this.amount = amount;
	}

	public int getBudgetId() {

		return budgetId;
	}

	public void setBudgetId(int budgetId) {

		this.budgetId = budgetId;
	}

	public Personnel getPersonnel() {

		return personnel;
	}

	public void setPersonnel(Personnel personnel) {

		this.personnel = personnel;
	}

	public String getFiscalYear() {

		return fiscalYear;
	}

	public void setFiscalYear(String fiscalYear) {

		this.fiscalYear = fiscalYear;
	}

	public double getAmount() {

		return amount;
	}

	public void setAmount(double amount) {

		this.amount = amount;
	}

	public double getAmountClaimed() {

		return amountClaimed;
	}

	public void setAmountClaimed(double amountClaimed) {

		this.amountClaimed = amountClaimed;
	}

	public double getAmountPreclaimed() {

		return amountPreclaimed;
	}

	public void setAmountPreclaimed(double amountPreclaimed) {

		this.amountPreclaimed = amountPreclaimed;
	}

	public double getDeficit() throws TravelClaimException {

		return this.amount - this.amountClaimed;
	}

	public Division getDivision() {

		return division;
	}

	public void setDivision(Division division) {

		this.division = division;
	}

}
