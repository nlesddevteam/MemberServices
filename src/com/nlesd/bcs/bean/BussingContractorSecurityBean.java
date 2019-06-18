package com.nlesd.bcs.bean;

import java.io.Serializable;
import java.util.Date;

public class BussingContractorSecurityBean implements Serializable {

	private static final long serialVersionUID = -844016122427000813L;
	private int id;
	private int contractorId;
	private String email;
	private String password;
	private String securityQuestion;
	private String sqAnswer;
	private Date lastUpdated;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getContractorId() {
		return contractorId;
	}
	public void setContractorId(int contractorId) {
		this.contractorId = contractorId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSecurityQuestion() {
		return securityQuestion;
	}
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}
	public String getSqAnswer() {
		return sqAnswer;
	}
	public void setSqAnswer(String sqAnswer) {
		this.sqAnswer = sqAnswer;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
}
