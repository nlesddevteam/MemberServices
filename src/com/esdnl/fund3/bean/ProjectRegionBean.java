package com.esdnl.fund3.bean;

import java.io.Serializable;

public class ProjectRegionBean implements Serializable {
	private static final long serialVersionUID = -8771605504141293333L;
	private int id;
	private int projectId;
	private int regionId;
	private Double budgetAmount;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public int getRegionId() {
		return regionId;
	}
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	public Double getBudgetAmount() {
		return budgetAmount;
	}
	public void setBudgetAmount(Double budgetAmount) {
		this.budgetAmount = budgetAmount;
	}

}
