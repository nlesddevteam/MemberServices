package com.nlesd.bcs.bean;

import java.io.Serializable;

public class BussingContractorSystemRouteRunSchoolBean  implements Serializable {

	private static final long serialVersionUID = -844016122427000813L;
	private int id;
	private int routeRunId;
	private int schoolId;
	private int schoolOrder;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRouteRunId() {
		return routeRunId;
	}
	public void setRouteRunId(int routeRunId) {
		this.routeRunId = routeRunId;
	}
	public int getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
	public int getSchoolOrder() {
		return schoolOrder;
	}
	public void setSchoolOrder(int schoolOrder) {
		this.schoolOrder = schoolOrder;
	}
}
