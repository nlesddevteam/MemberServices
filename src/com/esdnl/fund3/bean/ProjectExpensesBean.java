package com.esdnl.fund3.bean;

import java.io.Serializable;

public class ProjectExpensesBean implements Serializable {
	private static final long serialVersionUID = -8771605504141293333L;
	private int id;
	private int projectId;
	private String expenseDetails;
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
	public String getExpenseDetails() {
		return expenseDetails;
	}
	public void setExpenseDetails(String expenseDetails) {
		this.expenseDetails = expenseDetails;
	}
	

}
