package com.nlesd.bcs.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BussingContractorSystemRouteBean implements Serializable {

	private static final long serialVersionUID = -844016122427000813L;
	private int id;
	private String routeName;
	private String routeNotes;
	private int routeSchool;
	private int routeContract;
	private String addedBy;
	private Date dateAdded;
	private String isDeleted;
	private String routeSchoolString;
	private BussingContractorSystemContractBean contractBean;
	private ArrayList<BussingContractorSystemRouteRunBean> routeRuns;
	private int boardOwned;
	private int vehicleType;
	private int vehicleSize;
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
	public String getRouteNotes() {
		return routeNotes;
	}
	public void setRouteNotes(String routeNotes) {
		this.routeNotes = routeNotes;
	}
	public int getRouteSchool() {
		return routeSchool;
	}
	public void setRouteSchool(int routeSchool) {
		this.routeSchool = routeSchool;
	}
	public int getRouteContract() {
		return routeContract;
	}
	public void setRouteContract(int routeContract) {
		this.routeContract = routeContract;
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
	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}
	public String getAddedBy() {
		return addedBy;
	}
	public String getRouteSchoolString() {
		return routeSchoolString;
	}
	public void setRouteSchoolString(String routeSchoolString) {
		this.routeSchoolString = routeSchoolString;
	}
	public BussingContractorSystemContractBean getContractBean() {
		return contractBean;
	}
	public void setContractBean(BussingContractorSystemContractBean contractBean) {
		this.contractBean = contractBean;
	}
	public void setBoardOwned(int boardOwned) {
		this.boardOwned = boardOwned;
	}
	public int getBoardOwned() {
		return boardOwned;
	}
	public ArrayList<BussingContractorSystemRouteRunBean> getRouteRuns() {
		return routeRuns;
	}
	public void setRouteRuns(
			ArrayList<BussingContractorSystemRouteRunBean> routeRuns) {
		this.routeRuns = routeRuns;
	}
	public String getContractorName(){
		String contractorName=null;
		
		if(!(this.contractBean == null)){
			if(!(this.contractBean.getContractHistory() == null)){
				if(this.contractBean.getContractHistory().getContractorBean().getId() >0){
					
					BussingContractorBean bcbean = this.contractBean.getContractHistory().getContractorBean();
					if(bcbean.getCompany().length() > 0){
						contractorName=bcbean.getCompany();
					}else{
						contractorName=bcbean.getFirstName() + " " + bcbean.getLastName();
					}
					
				}
			}
		}
		
		return contractorName;
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
}
