package com.esdnl.payadvice.bean;

import java.io.Serializable;

public class NLESDPayAdviceMessageBean implements Serializable {

	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private String message;
	private Integer payGroup;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getPayGroup() {
		return payGroup;
	}
	public void setPayGroup(Integer payGroup) {
		this.payGroup = payGroup;
	}
}
