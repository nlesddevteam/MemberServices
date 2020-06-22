package com.nlesd.bcs.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BussingContractorSystemContractBean implements Serializable {

	private static final long serialVersionUID = -844016122427000813L;
	private int id;
	private String contractName;
	private int contractType;
	private String contractNotes;
	private int contractRegion;
	private Date contractExpiryDate;
	private String addedBy;
	private Date dateAdded;
	private String isDeleted;
	private String contractTypeString;
	private String contractRegionString;
	private int vehicleType;
	private int vehicleSize;
	private Date contractStartDate;
	private BussingContractorSystemContractHistoryBean contractHistory;
	private int boardOwned;
	private boolean subContracted;
	private int subContractorId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public int getContractType() {
		return contractType;
	}
	public void setContractType(int contractType) {
		this.contractType = contractType;
	}
	public String getContractNotes() {
		return contractNotes;
	}
	public void setContractNotes(String contractNotes) {
		this.contractNotes = contractNotes;
	}
	public int getContractRegion() {
		return contractRegion;
	}
	public void setContractRegion(int contractRegion) {
		this.contractRegion = contractRegion;
	}
	public Date getContractExpiryDate() {
		return contractExpiryDate;
	}
	public void setContractExpiryDate(Date contractExpiryDate) {
		this.contractExpiryDate = contractExpiryDate;
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
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getDateAddedFormatted() {
		if(this.dateAdded != null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.dateAdded);
		}else{
			return "";
		}
	}
	public String getContractExpiryDateFormatted() {
		if(this.contractExpiryDate != null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.contractExpiryDate);
		}else{
			return "";
		}
	}
	public String getContractTypeString() {
		return contractTypeString;
	}
	public void setContractTypeString(String contractTypeString) {
		this.contractTypeString = contractTypeString;
	}
	public String getContractRegionString() {
		return contractRegionString;
	}
	public void setContractRegionString(String contractRegionString) {
		this.contractRegionString = contractRegionString;
	}
	public int getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(int vehicleType) {
		this.vehicleType = vehicleType;
	}
	public int getVehicleSize() {
		return vehicleSize;
	}
	public void setVehicleSize(int vehicleSize) {
		this.vehicleSize = vehicleSize;
	}
	public Date getContractStartDate() {
		return contractStartDate;
	}
	public void setContractStartDate(Date contractStartDate) {
		this.contractStartDate = contractStartDate;
	}
	public String getContractStartDateFormatted() {
		if(this.contractStartDate != null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.contractStartDate);
		}else{
			return "";
		}
	}
	public BussingContractorSystemContractHistoryBean getContractHistory() {
		return contractHistory;
	}
	public void setContractHistory(
			BussingContractorSystemContractHistoryBean contractHistory) {
		this.contractHistory = contractHistory;
	}
	public void setBoardOwned(int boardOwned) {
		this.boardOwned = boardOwned;
	}
	public int getBoardOwned() {
		return boardOwned;
	}
	public boolean isSubContracted() {
		return subContracted;
	}
	public void setSubContracted(boolean subContracted) {
		this.subContracted = subContracted;
	}
	public int getSubContractorId() {
		return subContractorId;
	}
	public void setSubContractorId(int subContractorId) {
		this.subContractorId = subContractorId;
	}
}
