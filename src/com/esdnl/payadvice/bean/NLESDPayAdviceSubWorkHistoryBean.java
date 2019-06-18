package com.esdnl.payadvice.bean;

import java.io.Serializable;

public class NLESDPayAdviceSubWorkHistoryBean  implements Serializable {

	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private String emplId;
	private Integer emplRcd;
	private String name;
	private String dur;
	private String trc;
	private double tlQuantity;
	private double overrideRate;
	private String location;
	private String descr;
	private String userField1;
	private String name1;
	private double othHrs;
	private double otherEarnings;
	private double compRate;
	private double compRateUsed;
	private Integer payGroup;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmplId() {
		return emplId;
	}
	public void setEmplId(String emplId) {
		this.emplId = emplId;
	}
	public Integer getEmplRcd() {
		return emplRcd;
	}
	public void setEmplRcd(Integer emplRcd) {
		this.emplRcd = emplRcd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDur() {
		return dur;
	}
	public void setDur(String dur) {
		this.dur = dur;
	}
	public String getTrc() {
		return trc;
	}
	public void setTrc(String trc) {
		this.trc = trc;
	}
	public double getTlQuantity() {
		return tlQuantity;
	}
	public void setTlQuantity(double tlQuantity) {
		this.tlQuantity = tlQuantity;
	}
	public double getOverrideRate() {
		return overrideRate;
	}
	public void setOverrideRate(double overrideRate) {
		this.overrideRate = overrideRate;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getUserField1() {
		return userField1;
	}
	public void setUserField1(String userField1) {
		this.userField1 = userField1;
	}
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public double getOthHrs() {
		return othHrs;
	}
	public void setOthHrs(double othHrs) {
		this.othHrs = othHrs;
	}
	public double getOtherEarnings() {
		return otherEarnings;
	}
	public void setOtherEarnings(double otherEarnings) {
		this.otherEarnings = otherEarnings;
	}
	public double getCompRate() {
		return compRate;
	}
	public void setCompRate(double compRate) {
		this.compRate = compRate;
	}
	public double getCompRateUsed() {
		return compRateUsed;
	}
	public void setCompRateUsed(double compRateUsed) {
		this.compRateUsed = compRateUsed;
	}
	public Integer getPayGroup() {
		return payGroup;
	}
	public void setPayGroup(Integer payGroup) {
		this.payGroup = payGroup;
	}
	

}
