package com.nlesd.bcs.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.nlesd.bcs.constants.DateFieldConstant;

public class BussingContractorDateHistoryBean {
	private int id;
	private int linkId;
	private int dateType;
	private Date oldValue;
	private Date newValue;
	private Date dateChanged;
	private String changedBy;
	private BussingContractorVehicleBean bcvBean;
	private BussingContractorEmployeeBean bceBean;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLinkId() {
		return linkId;
	}
	public void setLinkId(int linkId) {
		this.linkId = linkId;
	}
	public int getDateType() {
		return dateType;
	}
	public void setDateType(int dateType) {
		this.dateType = dateType;
	}
	public Date getOldValue() {
		return oldValue;
	}
	public void setOldValue(Date oldValue) {
		this.oldValue = oldValue;
	}
	public Date getNewValue() {
		return newValue;
	}
	public void setNewValue(Date newValue) {
		this.newValue = newValue;
	}
	public Date getDateChanged() {
		return dateChanged;
	}
	public void setDateChanged(Date dateChanged) {
		this.dateChanged = dateChanged;
	}
	public String getChangedBy() {
		return changedBy;
	}
	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}
	public BussingContractorVehicleBean getBcvBean() {
		return bcvBean;
	}
	public void setBcvBean(BussingContractorVehicleBean bcvBean) {
		this.bcvBean = bcvBean;
	}
	public BussingContractorEmployeeBean getBceBean() {
		return bceBean;
	}
	public void setBceBean(BussingContractorEmployeeBean bceBean) {
		this.bceBean = bceBean;
	}
	public String getDateTypeString(){
		return DateFieldConstant.get(this.dateType).getDescription();
	}
	public String getOldValueFormatted() {
		if(this.oldValue!= null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.oldValue);
		}else{
			return "";
		}
	}
	public String getNewValueFormatted() {
		if(this.newValue!= null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.newValue);
		}else{
			return "";
		}
	}
	public String getDateChangedFormatted() {
		if(this.dateChanged!= null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.dateChanged);
		}else{
			return "";
		}
	}		
}
