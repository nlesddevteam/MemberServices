package com.esdnl.fund3.bean;

import java.io.Serializable;

public class ProjectEmployeeResponsibleBean implements Serializable {
	private static final long serialVersionUID = -8771605504141293333L;
	private int id;
	private int projectId;
	private int regionId;
	private String employeeEmail;
	private String employeeName;
	private String employeePhone;
	private String regionText;
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
	public String getEmployeeEmail() {
		return employeeEmail;
	}
	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeePhone() {
		return employeePhone;
	}
	public void setEmployeePhone(String employeePhone) {
		this.employeePhone = employeePhone;
	}
	public String getRegionText() {
		return regionText;
	}
	public void setRegionText(String regionText) {
		this.regionText = regionText;
	}


}
