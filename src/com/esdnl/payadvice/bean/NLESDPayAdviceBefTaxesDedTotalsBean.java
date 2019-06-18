package com.esdnl.payadvice.bean;

import java.io.Serializable;

public class NLESDPayAdviceBefTaxesDedTotalsBean  implements Serializable {

	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private double btxdTtlCur;
	private double btxdTtlYtd;
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
	public double getBtxdTtlCur() {
		return btxdTtlCur;
	}
	public void setBtxdTtlCur(double btxdTtlCur) {
		this.btxdTtlCur = btxdTtlCur;
	}
	public double getBtxdTtlYtd() {
		return btxdTtlYtd;
	}
	public void setBtxdTtlYtd(double btxdTtlYtd) {
		this.btxdTtlYtd = btxdTtlYtd;
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

}
