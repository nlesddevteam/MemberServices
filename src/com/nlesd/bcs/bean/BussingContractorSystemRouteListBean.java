package com.nlesd.bcs.bean;

import java.io.Serializable;

public class BussingContractorSystemRouteListBean implements Serializable {
	//class used to show route list and search routes results
	private static final long serialVersionUID = -844016122427000813L;
	private int id;
	private String routeName;
	private String company;
	private String firstName;
	private String lastName;
	private String routeRun;
	private String routeTime;
	private String routeSchools;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
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
	public String getRouteRun() {
		return routeRun;
	}
	public void setRouteRun(String routeRun) {
		this.routeRun = routeRun;
	}
	public String getRouteTime() {
		return routeTime;
	}
	public void setRouteTime(String routeTime) {
		this.routeTime = routeTime;
	}
	public String getRouteSchools() {
		return routeSchools;
	}
	public void setRouteSchools(String routeSchools) {
		this.routeSchools = routeSchools;
	}
	public String getCompanyName() {
		if(this.company ==  null) {
			if(this.lastName == null && this.firstName == null) {
				return "";
			}else {
				return this.lastName + ", " + this.firstName;
			}
		}else {
			return this.company;
		}
	}

}
