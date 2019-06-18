package com.nlesd.bcs.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BussingContractorSystemRouteVehicleBean implements Serializable {

	private static final long serialVersionUID = -844016122427000813L;
	private int id;
	private int routeId;
	private int vehicleId;
	private Date dateAssigned;
	private String isCurrent;
	private String AssignedBy;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRouteId() {
		return routeId;
	}
	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}
	public int getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}
	public Date getDateAssigned() {
		return dateAssigned;
	}
	public void setDateAssigned(Date dateAssigned) {
		this.dateAssigned = dateAssigned;
	}
	public String getIsCurrent() {
		return isCurrent;
	}
	public void setIsCurrent(String isCurrent) {
		this.isCurrent = isCurrent;
	}
	public String getAssignedBy() {
		return AssignedBy;
	}
	public void setAssignedBy(String assignedBy) {
		AssignedBy = assignedBy;
	}
	public String getDateAssignedFormatted() {
		if(this.dateAssigned!= null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.dateAssigned);
		}else{
			return "";
		}
	}
}