package com.esdnl.webupdatesystem.tenders.bean;

import java.util.Date;

import com.esdnl.webupdatesystem.tenders.constants.TenderRenewal;

public class TenderExceptionBean {
	private int wteId;
	private int tenderId;
	private String vendorName;
	private String eDescription;
	private String eAddress;
	private String eLocation;
	private String ePrice;
	private String poNumber;
	private String eTerms;
	private String renewalother;
	private String eClause;
	private String addedBy;
	private Date dateAdded;
	private TenderRenewal tenderRenewal;
	public int getWteId() {
		return wteId;
	}
	public void setWteId(int wteId) {
		this.wteId = wteId;
	}
	public int getTenderId() {
		return tenderId;
	}
	public void setTenderId(int tenderId) {
		this.tenderId = tenderId;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String geteDescription() {
		return eDescription;
	}
	public void seteDescription(String eDescription) {
		this.eDescription = eDescription;
	}
	public String geteAddress() {
		return eAddress;
	}
	public void seteAddress(String eAddress) {
		this.eAddress = eAddress;
	}
	public String geteLocation() {
		return eLocation;
	}
	public void seteLocation(String eLocation) {
		this.eLocation = eLocation;
	}
	public String getePrice() {
		return ePrice;
	}
	public void setePrice(String ePrice) {
		this.ePrice = ePrice;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public String geteTerms() {
		return eTerms;
	}
	public void seteTerms(String eTerms) {
		this.eTerms = eTerms;
	}
	public String getRenewalother() {
		return renewalother;
	}
	public void setRenewalother(String renewalother) {
		this.renewalother = renewalother;
	}
	public String geteClause() {
		return eClause;
	}
	public void seteClause(String eClause) {
		this.eClause = eClause;
	}
	public String getAddedBy() {
		return addedBy;
	}
	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	public TenderRenewal getTenderRenewal() {
		return tenderRenewal;
	}
	public void setTenderRenewal(TenderRenewal tenderRenewal) {
		this.tenderRenewal = tenderRenewal;
	}
}
