package com.nlesd.bcs.bean;

public class BussingContractorSystemRegionalBean {
	private int id;
	private int regionCode;
	private int depotCode;
	private String regionName;
	private String depotName;
	private int rId;
	private String rType; 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(int regionCode) {
		this.regionCode = regionCode;
	}
	public int getDepotCode() {
		return depotCode;
	}
	public void setDepotCode(int depotCode) {
		this.depotCode = depotCode;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getDepotName() {
		return depotName;
	}
	public void setDepotName(String depotName) {
		this.depotName = depotName;
	}
	public int getrId() {
		return rId;
	}
	public void setrId(int rId) {
		this.rId = rId;
	}
	public String getrType() {
		return rType;
	}
	public void setrType(String rType) {
		this.rType = rType;
	}
}
