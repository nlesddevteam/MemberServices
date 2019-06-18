package com.nlesd.bcs.bean;

import java.io.Serializable;

public class BussingContractorRouteListBean implements Serializable {

	private static final long serialVersionUID = -844016122427000813L;
	private String routeName;
	private String schoolName;
	private String firstName;
	private String lastName;
	private String vPlateNumber;
	private int id;
	private int contractid;
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getvPlateNumber() {
		return vPlateNumber;
	}
	public void setvPlateNumber(String vPlateNumber) {
		this.vPlateNumber = vPlateNumber;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getContractId() {
		return contractid;
	}
	public void setContractId(int contractid) {
		this.contractid = contractid;
	}
	
}
