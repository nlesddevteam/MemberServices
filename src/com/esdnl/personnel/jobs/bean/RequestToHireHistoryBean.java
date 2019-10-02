package com.esdnl.personnel.jobs.bean;

import java.util.Date;

import com.esdnl.personnel.jobs.constants.RequestToHireStatus;

public class RequestToHireHistoryBean {
	private int id;
	private int requestToHireId;
	private RequestToHireStatus statusId;
	private Date historyDate;
	private String notes;
	private String historyDateTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRequestToHireId() {
		return requestToHireId;
	}
	public void setRequestToHireId(int requestToHireId) {
		this.requestToHireId = requestToHireId;
	}
	public RequestToHireStatus getStatusId() {
		return statusId;
	}
	public void setStatusId(RequestToHireStatus statusId) {
		this.statusId = statusId;
	}
	public Date getHistoryDate() {
		return historyDate;
	}
	public void setHistoryDate(Date historyDate) {
		this.historyDate = historyDate;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getHistoryDateTime() {
		return historyDateTime;
	}
	public void setHistoryDateTime(String historyDateTime) {
		this.historyDateTime = historyDateTime;
	}

}
