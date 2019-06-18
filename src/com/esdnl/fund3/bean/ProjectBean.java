package com.esdnl.fund3.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ProjectBean implements Serializable {
	private static final long serialVersionUID = -8771605504141293333L;
	private int projectId;
	private String projectName;
	private String projectNumber;
	private Date dateFundingApproved;
	private Date projectStartDate;
	private Date projectEndDate;
	private int projectCategory;
	private int positionResponsible;
	private String employeeName;
	private String employeeEmail;
	private String employeePhone;
	private String projectDescription;
	private String specialRequirements;
	private int reportRequested;
	private Date firstReportDate;
	private int reportFrequency;
	private String reportEmail;
	private String addedBy;
	private Date dateAdded;
	private int projectStartDateTBD;
	private int projectEndDateTBD;
	private String positionResponsibleText;
	private String projectRegionsList;
	private int projectYear;
	private ArrayList<ProjectSchoolBean> projectSchool;
	private ArrayList<ProjectFundingBean> projectFunding;
	private ArrayList<ProjectExpensesBean> projectExpense;
	private ArrayList<ProjectDocumentsBean> projectDocuments;
	private ArrayList<ProjectEmployeeResponsibleBean> projectEmpRes;
	private int isActive;
	private String statusNotes;
	private String availabilityNotes;
	private int projectStatus;
	private String statusBy;
	private Date statusDate;
	private double currentBudget;
	private double currentBalance;
	public String getStatusBy() {
		return statusBy;
	}
	public void setStatusBy(String statusBy) {
		this.statusBy = statusBy;
	}
	public Date getStatusDate() {
		return statusDate;
	}
	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}
	public String getAvailabilityNotes() {
		return availabilityNotes;
	}
	public void setAvailabilityNotes(String availabilityNotes) {
		this.availabilityNotes = availabilityNotes;
	}
	public int getProjectStatus() {
		return projectStatus;
	}
	public void setProjectStatus(int projectStatus) {
		this.projectStatus = projectStatus;
	}
	private ArrayList<AuditLogBean> auditLog;
	public ArrayList<AuditLogBean> getAuditLog() {
		return auditLog;
	}
	public void setAuditLog(ArrayList<AuditLogBean> auditLog) {
		this.auditLog = auditLog;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Date getDateFundingApproved() {
		return dateFundingApproved;
	}
	public void setDateFundingApproved(Date dateFundingApproved) {
		this.dateFundingApproved = dateFundingApproved;
	}
	public Date getProjectStartDate() {
		return projectStartDate;
	}
	public void setProjectStartDate(Date projectStartDate) {
		this.projectStartDate = projectStartDate;
	}
	public Date getProjectEndDate() {
		return projectEndDate;
	}
	public void setProjectEndDate(Date projectEndDate) {
		this.projectEndDate = projectEndDate;
	}
	public int getProjectCategory() {
		return projectCategory;
	}
	public void setProjectCategory(int projectCategory) {
		this.projectCategory = projectCategory;
	}
	public int getPositionResponsible() {
		return positionResponsible;
	}
	public void setPositionResponsible(int positionResponsible) {
		this.positionResponsible = positionResponsible;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeEmail() {
		return employeeEmail;
	}
	public void setEmployeeEmail(String employeeEmai) {
		this.employeeEmail = employeeEmai;
	}
	public String getEmployeePhone() {
		return employeePhone;
	}
	public void setEmployeePhone(String employeePhone) {
		this.employeePhone = employeePhone;
	}
	public String getProjectDescription() {
		return projectDescription;
	}
	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}
	public String getSpecialRequirements() {
		return specialRequirements;
	}
	public void setSpecialRequirements(String specialRequirements) {
		this.specialRequirements = specialRequirements;
	}
	public int getReportRequested() {
		return reportRequested;
	}
	public void setReportRequested(int reportRequested) {
		this.reportRequested = reportRequested;
	}
	public Date getFirstReportDate() {
		return firstReportDate;
	}
	public void setFirstReportDate(Date firstReportDate) {
		this.firstReportDate = firstReportDate;
	}
	public int getReportFrequency() {
		return reportFrequency;
	}
	public void setReportFrequency(int reportFrequency) {
		this.reportFrequency = reportFrequency;
	}
	public String getReportEmail() {
		return reportEmail;
	}
	public void setReportEmail(String reportEmail) {
		this.reportEmail = reportEmail;
	}
	public String getAddedBy() {
		return addedBy;
	}
	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	public String getProjectNumber() {
		return projectNumber;
	}
	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}
	public int getProjectStartDateTBD() {
		return projectStartDateTBD;
	}
	public void setProjectStartDateTBD(int projectStartDateTBD) {
		this.projectStartDateTBD = projectStartDateTBD;
	}
	public int getProjectEndDateTBD() {
		return projectEndDateTBD;
	}
	public void setProjectEndDateTBD(int projectEndDateTBD) {
		this.projectEndDateTBD = projectEndDateTBD;
	}
	public String getPositionResponsibleText() {
		return positionResponsibleText;
	}
	public void setPositionResponsibleText(String positionResponsibleText) {
		this.positionResponsibleText = positionResponsibleText;
	}
	public String getProjectRegionsList() {
		return projectRegionsList;
	}
	public void setProjectRegionsList(String projectRegionsList) {
		this.projectRegionsList = projectRegionsList;
	}
	public int getProjectYear() {
		return projectYear;
	}
	public void setProjectYear(int projectYear) {
		this.projectYear = projectYear;
	}
	public ArrayList<ProjectSchoolBean> getProjectSchool() {
		return projectSchool;
	}
	public void setProjectSchool(ArrayList<ProjectSchoolBean> projectSchool) {
		this.projectSchool = projectSchool;
	}
	public ArrayList<ProjectFundingBean> getProjectFunding() {
		return projectFunding;
	}
	public void setProjectFunding(ArrayList<ProjectFundingBean> projectFunding) {
		this.projectFunding = projectFunding;
	}
	public String getDateFundingApprovedFormatted() {
		if(this.dateFundingApproved != null){
			return new SimpleDateFormat("dd/MM/yyyy").format(this.dateFundingApproved);
		}else{
			return "";
		}
	}
	public String getProjectStartDateFormatted() {
		if(this.projectStartDate != null){
			return new SimpleDateFormat("dd/MM/yyyy").format(this.projectStartDate);
		}else{
			return "";
		}
	}
	public String getProjectEndDateFormatted() {
		if(this.projectEndDate != null){
			return new SimpleDateFormat("dd/MM/yyyy").format(this.projectEndDate);
		}else{
			return "";
		}
	}
	public String getFirstReportDateFormatted() {
		if(this.firstReportDate != null){
			return new SimpleDateFormat("dd/MM/yyyy").format(this.firstReportDate);
		}else{
			return "";
		}
	}
	public String getDateAddedFormatted() {
		if(this.dateAdded != null){
			return new SimpleDateFormat("dd/MM/yyyy").format(this.dateAdded);
		}else{
			return "";
		}
	}
	public ArrayList<ProjectExpensesBean> getProjectExpense() {
		return projectExpense;
	}
	public void setProjectExpense(ArrayList<ProjectExpensesBean> projectExpense) {
		this.projectExpense = projectExpense;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public String getStatusNotes() {
		return statusNotes;
	}
	public void setStatusNotes(String statusNotes) {
		this.statusNotes = statusNotes;
	}
	public ArrayList<ProjectDocumentsBean> getProjectDocuments() {
		return projectDocuments;
	}
	public void setProjectDocuments(ArrayList<ProjectDocumentsBean> projectDocuments) {
		this.projectDocuments = projectDocuments;
	}
	public String getStatusDateFormatted() {
		if(this.statusDate != null){
			return new SimpleDateFormat("dd/MM/yyyy").format(this.statusDate);
		}else{
			return "";
		}
	}
	public String getAwaitingApprovalBy(){
		String approveby="";
		String[] rlist = this.projectRegionsList.split(",");
		if(rlist.length > 1){
			approveby="Associate Director";
		}else{
			approveby=rlist[0];
		}
		return approveby;
	}
	public ArrayList<ProjectEmployeeResponsibleBean> getProjectEmpRes() {
		return projectEmpRes;
	}
	public void setProjectEmpRes(
			ArrayList<ProjectEmployeeResponsibleBean> projectEmpRes) {
		this.projectEmpRes = projectEmpRes;
	}
	public double getCurrentBudget() {
		return currentBudget;
	}
	public void setCurrentBudget(double currentBudget) {
		this.currentBudget = currentBudget;
	}
	public double getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}

}
