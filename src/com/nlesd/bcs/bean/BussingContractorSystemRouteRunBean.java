package com.nlesd.bcs.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class BussingContractorSystemRouteRunBean implements Serializable {

	private static final long serialVersionUID = -844016122427000813L;
	private int id;
	private String routeTime;
	private int routeId;
	private String routeRun;
	private Date dateAdded;
	private String addedBy;
	private String runSchools;
	private HashMap<Integer,String> runSchoolsDD;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRouteTime() {
		return routeTime;
	}
	public void setRouteTime(String routeTime) {
		this.routeTime = routeTime;
	}
	public int getRouteId() {
		return routeId;
	}
	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}
	public String getRouteRun() {
		return routeRun;
	}
	public void setRouteRun(String routeRun) {
		this.routeRun = routeRun;
	}
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	public String getAddedBy() {
		return addedBy;
	}
	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}
	public String getRunSchools() {
		return runSchools;
	}
	public void setRunSchools(String runSchools) {
		this.runSchools = runSchools;
	}
	public HashMap<Integer, String> getRunSchoolsDD() {
		return runSchoolsDD;
	}
	public void setRunSchoolsDD(HashMap<Integer, String> runSchoolsDD) {
		this.runSchoolsDD = runSchoolsDD;
	}
}
