package com.esdnl.personnel.jobs.bean;
import java.io.Serializable;
import java.util.Date;
public class ApplicantEmploymentSSBean implements Serializable {

	private static final long serialVersionUID = 5304511837525367873L;
	private int id;
	private String sin;
	private String company;
	private String address;
	private String jobTitle;
	private String phoneNumber;
	private String supervisor;
	private String startingSalary;
	private String endingSalary;
	private String duties;
	private Date fromDate;
	private Date toDate;
	private String reasonForLeaving;
	private String contact;
	private int reason;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSin() {
		return sin;
	}
	public void setSin(String sin) {
		this.sin = sin;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getSupervisor() {
		return supervisor;
	}
	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}
	public String getStartingSalary() {
		return startingSalary;
	}
	public void setStartingSalary(String startingSalary) {
		this.startingSalary = startingSalary;
	}
	public String getEndingSalary() {
		return endingSalary;
	}
	public void setEndingSalary(String endingSalary) {
		this.endingSalary = endingSalary;
	}
	public String getDuties() {
		return duties;
	}
	public void setDuties(String duties) {
		this.duties = duties;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getReasonForLeaving() {
		return reasonForLeaving;
	}
	public void setReasonForLeaving(String reasonForLeaving) {
		this.reasonForLeaving = reasonForLeaving;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public int getReason() {
		return reason;
	}
	public void setReason(int reason) {
		this.reason = reason;
	}
}
