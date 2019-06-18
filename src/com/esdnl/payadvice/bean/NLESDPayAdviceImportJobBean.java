package com.esdnl.payadvice.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NLESDPayAdviceImportJobBean implements Serializable {

	private static final long serialVersionUID = -8771605504141293333L;
	private Integer Id;
	private String submittedBy;
	private Date startTime;
	private Integer payrollFile;
	private String payrollStatus;
	private Integer mappingFile;
	private String mappingStatus;
	private Integer historyFile;
	private String historyStatus;
	private Date endTime;
	private Integer jobCompleted;
	private Integer jobErrors;
	private String jobErrorText;
	private Integer payGroup;
	private String payrollFileName;
	private String mappingFileName;
	private String historyFileName;
	private String startTimeFormatted;
	private String endTimeFormatted;

	public NLESDPayAdviceImportJobBean() {

	}

	public NLESDPayAdviceImportJobBean(Integer payrollfile, Integer mappingfile, Integer paygroup, String username,
			Integer historyfile) {

		this.submittedBy = username;
		Date starttime = new Date();
		this.startTime = starttime;
		if (payrollfile > 0) {
			this.payrollFile = payrollfile;
			this.payrollStatus = "Waiting To Be Processed";
		}
		else {
			this.payrollFile = -1;
			this.payrollStatus = "No Payroll File";
		}
		if (mappingfile > 0) {
			this.mappingFile = mappingfile;
			this.mappingStatus = "Waiting To Be Processed";
		}
		else {
			this.mappingFile = -1;
			this.mappingStatus = "No Mapping File";
		}
		if (historyfile > 0) {
			this.historyFile = historyfile;
			this.historyStatus = "Waiting To Be Processed";
		}
		else {
			this.historyFile = -1;
			this.historyStatus = "No History File";
		}
		this.payGroup = paygroup;
	}

	public Integer getId() {

		return Id;
	}

	public void setId(Integer id) {

		Id = id;
	}

	public String getSubmittedBy() {

		return submittedBy;
	}

	public void setSubmittedBy(String submittedBy) {

		this.submittedBy = submittedBy;
	}

	public Date getStartTime() {

		return startTime;
	}

	public void setStartTime(Date startTime) {

		this.startTime = startTime;
	}

	public Integer getPayrollFile() {

		return payrollFile;
	}

	public void setPayrollFile(Integer payrollFile) {

		this.payrollFile = payrollFile;
	}

	public String getPayrollStatus() {

		return payrollStatus;
	}

	public void setPayrollStatus(String payrollStatus) {

		this.payrollStatus = payrollStatus;
	}

	public Integer getMappingFile() {

		return mappingFile;
	}

	public void setMappingFile(Integer mappingFile) {

		this.mappingFile = mappingFile;
	}

	public String getMappingStatus() {

		return mappingStatus;
	}

	public void setMappingStatus(String mappingStatus) {

		this.mappingStatus = mappingStatus;
	}

	public Integer getHistoryFile() {

		return historyFile;
	}

	public void setHistoryFile(Integer historyFile) {

		this.historyFile = historyFile;
	}

	public String getHistoryStatus() {

		return historyStatus;
	}

	public void setHistoryStatus(String historyStatus) {

		this.historyStatus = historyStatus;
	}

	public Date getEndTime() {

		return endTime;
	}

	public void setEndTime(Date endTime) {

		this.endTime = endTime;
	}

	public Integer getJobCompleted() {

		return jobCompleted;
	}

	public void setJobCompleted(Integer jobCompleted) {

		this.jobCompleted = jobCompleted;
	}

	public Integer getJobErrors() {

		return jobErrors;
	}

	public void setJobErrors(Integer jobErrors) {

		this.jobErrors = jobErrors;
	}

	public String getJobErrorText() {

		return jobErrorText;
	}

	public void setJobErrorText(String jobErrorText) {

		this.jobErrorText = jobErrorText;
	}

	public Integer getPayGroup() {

		return payGroup;
	}

	public void setPayGroup(Integer payGroup) {

		this.payGroup = payGroup;
	}

	public String getPayrollFileName() {

		return payrollFileName;
	}

	public void setPayrollFileName(String payrollFileName) {

		this.payrollFileName = payrollFileName;
	}

	public String getMappingFileName() {

		return mappingFileName;
	}

	public void setMappingFileName(String mappingFileName) {

		this.mappingFileName = mappingFileName;
	}

	public String getHistoryFileName() {

		return historyFileName;
	}

	public void setHistoryFileName(String historyFileName) {

		this.historyFileName = historyFileName;
	}

	public String getStartTimeFormatted() {

		if (this.startTime != null) {
			SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
			this.startTimeFormatted = dt.format(this.startTime);
			return this.startTimeFormatted;
		}
		else {
			this.startTimeFormatted = "";
			return this.startTimeFormatted;
		}

	}

	public String getEndTimeFormatted() {

		if (this.endTime != null) {
			SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
			this.endTimeFormatted = dt.format(this.endTime);
			return this.endTimeFormatted;
		}
		else {
			this.endTimeFormatted = "";
			return this.endTimeFormatted;
		}

	}
}
