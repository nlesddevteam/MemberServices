package com.esdnl.payadvice.bean;

import java.io.Serializable;

public class NLESDPayAdviceInformationBean implements Serializable {

	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private String bnkChDt;
	private String bnkChkNo;
	private String bnkPayAmt;
	private String chkPyeName;
	private String chkPyeAdl1;
	private String chkPyeAdl2;
	private String chkPyeLocn;
	private String ddAdvNo;
	private String ddAcctNum;
	private String ddAcctTyp;
	private String ddAcctAmt;
	private String ddNetPay;
	private String empNumber;
	private int payGroupId;
	private Integer empInfoId;
	
	public Integer getEmpInfoId() {
		return empInfoId;
	}
	public void setEmpInfoId(Integer empInfoId) {
		this.empInfoId = empInfoId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBnkChDt() {
		return bnkChDt;
	}
	public void setBnkChDt(String bnkChDt) {
		this.bnkChDt = bnkChDt;
	}
	public String getBnkChkNo() {
		return bnkChkNo;
	}
	public void setBnkChkNo(String bnkChkNo) {
		this.bnkChkNo = bnkChkNo;
	}
	public String getBnkPayAmt() {
		return bnkPayAmt;
	}
	public void setBnkPayAmt(String bnkPayAmt) {
		this.bnkPayAmt = bnkPayAmt;
	}
	public String getChkPyeName() {
		return chkPyeName;
	}
	public void setChkPyeName(String chkPyeName) {
		this.chkPyeName = chkPyeName;
	}
	public String getChkPyeAdl1() {
		return chkPyeAdl1;
	}
	public void setChkPyeAdl1(String chkPyeAdl1) {
		this.chkPyeAdl1 = chkPyeAdl1;
	}
	public String getChkPyeAdl2() {
		return chkPyeAdl2;
	}
	public void setChkPyeAdl2(String chkPyeAdl2) {
		this.chkPyeAdl2 = chkPyeAdl2;
	}
	public String getChkPyeLocn() {
		return chkPyeLocn;
	}
	public void setChkPyeLocn(String chkPyeLocn) {
		this.chkPyeLocn = chkPyeLocn;
	}
	public String getDdAdvNo() {
		return ddAdvNo;
	}
	public void setDdAdvNo(String ddAdvNo) {
		this.ddAdvNo = ddAdvNo;
	}
	public String getDdAcctNum() {
		return ddAcctNum;
	}
	public void setDdAcctNum(String ddAcctNum) {
		this.ddAcctNum = ddAcctNum;
	}
	public String getDdAcctAmt() {
		return ddAcctAmt;
	}
	public void setDdAcctAmt(String ddAcctAmt) {
		this.ddAcctAmt = ddAcctAmt;
	}
	public String getDdNetPay() {
		return ddNetPay;
	}
	public void setDdNetPay(String ddNetPay) {
		this.ddNetPay = ddNetPay;
	}
	public String getEmpNumber() {
		return empNumber;
	}
	public void setEmpNumber(String empNumber) {
		this.empNumber = empNumber;
	}
	public int getPayGroupId() {
		return payGroupId;
	}
	public void setPayGroupId(int payGroupId) {
		this.payGroupId = payGroupId;
	}
	public String getDdAcctTyp() {
		return ddAcctTyp;
	}
	public void setDdAcctTyp(String ddAcctTyp) {
		this.ddAcctTyp = ddAcctTyp;
	}
}
