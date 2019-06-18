package com.nlesd.bcs.bean;

import java.io.Serializable;

public class BussingContractorCompanyBean implements Serializable {

	private static final long serialVersionUID = -844016122427000813L;
	private int id;
	private int contractorId;
	private String tRegular;
	private String tAlternate;
	private String tParent;
	private String crSameAs;
	private String crFirstName;
	private String crLastName;
	private String crPhoneNumber;
	private String crEmail;
	private String toSameAs;
	private String toFirstName;
	private String toLastName;
	private String toPhoneNumber;
	private String toEmail;
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
	public String gettRegular() {
		return tRegular;
	}
	public void settRegular(String tRegular) {
		this.tRegular = tRegular;
	}
	public String gettAlternate() {
		return tAlternate;
	}
	public void settAlternate(String tAlternate) {
		this.tAlternate = tAlternate;
	}
	public String gettParent() {
		return tParent;
	}
	public void settParent(String tParent) {
		this.tParent = tParent;
	}
	public String getCrSameAs() {
		return crSameAs;
	}
	public void setCrSameAs(String crSameAs) {
		this.crSameAs = crSameAs;
	}
	public String getCrFirstName() {
		return crFirstName;
	}
	public void setCrFirstName(String crFirstName) {
		this.crFirstName = crFirstName;
	}
	public String getCrLastName() {
		return crLastName;
	}
	public void setCrLastName(String crLastName) {
		this.crLastName = crLastName;
	}
	public String getCrPhoneNumber() {
		return crPhoneNumber;
	}
	public void setCrPhoneNumber(String crPhoneNumber) {
		this.crPhoneNumber = crPhoneNumber;
	}
	public String getCrEmail() {
		return crEmail;
	}
	public void setCrEmail(String crEmail) {
		this.crEmail = crEmail;
	}
	public String getToSameAs() {
		return toSameAs;
	}
	public void setToSameAs(String toSameAs) {
		this.toSameAs = toSameAs;
	}
	public String getToFirstName() {
		return toFirstName;
	}
	public void setToFirstName(String toFirstName) {
		this.toFirstName = toFirstName;
	}
	public String getToLastName() {
		return toLastName;
	}
	public void setToLastName(String toLastName) {
		this.toLastName = toLastName;
	}
	public String getToPhoneNumber() {
		return toPhoneNumber;
	}
	public void setToPhoneNumber(String toPhoneNumber) {
		this.toPhoneNumber = toPhoneNumber;
	}
	public String getToEmail() {
		return toEmail;
	}
	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}
}
