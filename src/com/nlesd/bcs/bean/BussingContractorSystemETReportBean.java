package com.nlesd.bcs.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.nlesd.bcs.constants.EmployeeStatusConstant;

public class BussingContractorSystemETReportBean {
	private int id;
	private double continuousService;
	private EmployeeStatusConstant employeeStatus;
	private String employeeFirstName;
	private String employeeLastName;
	private String employeePosition;
	private String contractorFirstName;
	private String contractorLastName;
	private String companyName;
	private Date trainingDate;
	private Date expiryDate;
	private String providedBy;
	private String location;
	private String trainingType;
	private String trainingLength;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getContinuousService() {
		return continuousService;
	}
	public void setContinuousService(double continuousService) {
		this.continuousService = continuousService;
	}
	public EmployeeStatusConstant getEmployeeStatus() {
		return employeeStatus;
	}
	public void setEmployeeStatus(EmployeeStatusConstant employeeStatus) {
		this.employeeStatus = employeeStatus;
	}
	public String getEmployeeFirstName() {
		return employeeFirstName;
	}
	public void setEmployeeFirstName(String employeeFirstName) {
		this.employeeFirstName = employeeFirstName;
	}
	public String getEmployeeLastName() {
		return employeeLastName;
	}
	public void setEmployeeLastName(String employeeLastName) {
		this.employeeLastName = employeeLastName;
	}
	public String getEmployeePosition() {
		return employeePosition;
	}
	public void setEmployeePosition(String employeePosition) {
		this.employeePosition = employeePosition;
	}
	public String getContractorFirstName() {
		return contractorFirstName;
	}
	public void setContractorFirstName(String contractorFirstName) {
		this.contractorFirstName = contractorFirstName;
	}
	public String getContractorLastName() {
		return contractorLastName;
	}
	public void setContractorLastName(String contractorLastName) {
		this.contractorLastName = contractorLastName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Date getTrainingDate() {
		return trainingDate;
	}
	public void setTrainingDate(Date trainingDate) {
		this.trainingDate = trainingDate;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getProvidedBy() {
		return providedBy;
	}
	public void setProvidedBy(String providedBy) {
		this.providedBy = providedBy;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getTrainingType() {
		return trainingType;
	}
	public void setTrainingType(String trainingType) {
		this.trainingType = trainingType;
	}
	public String getTrainingLength() {
		return trainingLength;
	}
	public void setTrainingLength(String trainingLength) {
		this.trainingLength = trainingLength;
	}
	public String getTrainingDateFormatted() {
		if(this.trainingDate != null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.trainingDate);
		}else{
			return "";
		}
	}
	public String getExpiryDateFormatted() {
		if(this.expiryDate != null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.expiryDate);
		}else{
			return "";
		}
	}
}
