package com.esdnl.personnel.jobs.bean;

import com.esdnl.util.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.esdnl.servlet.FormElementFormat;

public class ApplicantSubListInfoBean {

	private ApplicantProfileBean profile;
	private SubListBean list;
	private String status;
	private Date appliedDate;
	private Date approvedDate;
	private Date notApprovedDate;
	private Date workingDate;

	public ApplicantSubListInfoBean() {
		this.profile = null;
		this.list = null;
		this.status = null;
		this.appliedDate = null;
		this.approvedDate = null;
		this.notApprovedDate = null;
		this.workingDate = null;
	}
	
	public ApplicantProfileBean getApplicant(){
		return this.profile;
	}
	
	public void setApplicant(ApplicantProfileBean profile){
		this.profile = profile;
	}

	public SubListBean getSubList() {
		return list;
	}

	public void setSubList(SubListBean list) {
		this.list = list;
	}

	public String getStatus() {
		return status;
	}
	
	public boolean isNewApplicant(){
		return StringUtils.isEmpty(this.status);
	}
	
	public boolean isShortlisted(){
		return StringUtils.isEqual(this.status, "Y");
	}
	
	public boolean isNotApproved(){
		return StringUtils.isEqual(this.status, "N");
	}
	
	public boolean isWorking(){
		return StringUtils.isEqual(this.status, "W");
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getAppliedDate() {
		return appliedDate;
	}
	
	public String getAppliedDateFormatted(){
		String format = "";
		
		if(getAppliedDate() != null){
			format = new SimpleDateFormat(FormElementFormat.DATE_FORMAT).format(getAppliedDate());
		}
		
		return format;
	}

	public void setAppliedDate(Date appliedDate) {
		this.appliedDate = appliedDate;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}
	
	public String getApprovedDateFormatted(){
		String format = "";
		
		if(getApprovedDate() != null){
			format = new SimpleDateFormat(FormElementFormat.DATE_FORMAT).format(getApprovedDate());
		}
		
		return format;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public Date getNotApprovedDate() {
		return notApprovedDate;
	}
	
	public String getNotApprovedDateFormatted(){
		String format = "";
		
		if(getNotApprovedDate() != null){
			format = new SimpleDateFormat(FormElementFormat.DATE_FORMAT).format(getNotApprovedDate());
		}
		
		return format;
	}

	public void setNotApprovedDate(Date notApprovedDate) {
		this.notApprovedDate = notApprovedDate;
	}

	public Date getWorkingDate() {
		return workingDate;
	}
	
	public String getWorkingDateFormatted(){
		String format = "";
		
		if(getWorkingDate() != null){
			format = new SimpleDateFormat(FormElementFormat.DATE_FORMAT).format(getWorkingDate());
		}
		
		return format;
	}

	public void setWorkingDate(Date workingDate) {
		this.workingDate = workingDate;
	}

}
