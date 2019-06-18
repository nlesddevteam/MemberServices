package com.nlesd.bcs.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BussingContractorSystemContractHistoryBean implements Serializable {

	private static final long serialVersionUID = -844016122427000813L;
	private int id;
	private int contractStatus;
	private int contractId;
	private int contractorId;
	private String statusNotes;
	private String statusBy;
	private Date statusDate;
	private String statusString;
	private BussingContractorBean contractorBean;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getContractStatus() {
		return contractStatus;
	}
	public void setContractStatus(int contractStatus) {
		this.contractStatus = contractStatus;
	}
	public int getContractId() {
		return contractId;
	}
	public void setContractId(int contractId) {
		this.contractId = contractId;
	}
	public int getContractorId() {
		return contractorId;
	}
	public void setContractorId(int contractorId) {
		this.contractorId = contractorId;
	}
	public String getStatusNotes() {
		return statusNotes;
	}
	public void setStatusNotes(String statusNotes) {
		this.statusNotes = statusNotes;
	}
	public String getStatusBy() {
		return statusBy;
	}
	public void setStatusBy(String statusBy) {
		this.statusBy = statusBy;
	}
	public Date getStatusDate() {
		return statusDate;
	}
	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}
	public String getStatusDateFormatted() {
		if(this.statusDate != null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.statusDate);
		}else{
			return "";
		}
	}
	public String getStatusString() {
		return statusString;
	}
	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}
	public BussingContractorBean getContractorBean() {
		return contractorBean;
	}
	public void setContractorBean(BussingContractorBean contractorBean) {
		this.contractorBean = contractorBean;
	}	
}
