package com.esdnl.payadvice.bean;

import java.io.Serializable;

public class NLESDPayAdvicePayGroupBean implements Serializable {

	private static final long serialVersionUID = -8771605504141292222L;
	private Integer Id;
	private String payGp;
	private String payBgDt;
	private String payEndDt;
	private String busUnit;
	private String checkNum;
	private String checkDt;
	private String payBgDtD;
	private String payEndDtD;
	private String checkDtD;
	
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getPayGp() {
		return payGp;
	}
	public void setPayGp(String payGp) {
		this.payGp = payGp;
	}
	public String getPayBgDt() {
		return payBgDt;
	}
	public void setPayBgDt(String payBgDt) {
		this.payBgDt = payBgDt;
	}
	public String getPayEndDt() {
		return payEndDt;
	}
	public void setPayEndDt(String payEndDt) {
		this.payEndDt = payEndDt;
	}
	public String getBusUnit() {
		return busUnit;
	}
	public void setBusUnit(String busUnit) {
		this.busUnit = busUnit;
	}
	public String getCheckNum() {
		return checkNum;
	}
	public void setCheckNum(String checkNum) {
		this.checkNum = checkNum;
	}
	public String getCheckDt() {
		return checkDt;
	}
	public void setCheckDt(String checkDt) {
		this.checkDt = checkDt;
	}
	public String getPayBgDtD() {
		return payBgDtD;
	}
	public void setPayBgDtD(String payBgDtD) {
		this.payBgDtD = payBgDtD;
	}
	public String getPayEndDtD() {
		return payEndDtD;
	}
	public void setPayEndDtD(String payEndDtD) {
		this.payEndDtD = payEndDtD;
	}
	public String getCheckDtD() {
		return checkDtD;
	}
	public void setCheckDtD(String checkDtD) {
		this.checkDtD = checkDtD;
	}
	
}
