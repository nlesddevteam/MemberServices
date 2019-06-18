package com.esdnl.payadvice.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.esdnl.payadvice.dao.NLESDPayAdvicePayrollProcessErrorManager;

public class NLESDPayAdvicePayrollProcessBean implements Serializable,Comparable {

	private static final long serialVersionUID = -8771605504141292777L;
	private NLESDPayAdvicePayGroupBean payGroupBean;
	private NLESDPayAdviceImportJobBean importJobBean;
	private Integer payGroupId;
	private String stubCreationStatus;
	private Date stubCreationStarted;
	private Date stubCreationFinished;
	private String stubCreationBy;
	private String closedStatus;
	private Date closedStarted;
	private Date closedFinished;
	private String closedBy;
	private Integer processFinished;
	private String importStatus;
	private String bgDate;
	private String endDate;
	private String stubCreationStartedFormatted;
	private String stubCreationFinishedFormatted;
	private String closedStartedFormatted;
	private String closedFinishedFormatted;
	private boolean checkForErrors;
	private Integer totalPayStubs;
	private Integer totalPayStubsSent;
	private Integer totalPayStubsNotSent;
	private String manualFilename;
	
	public void setCheckForErrors()
	{
		boolean check=false;
		try {
			check= NLESDPayAdvicePayrollProcessErrorManager.checkNLESDPayAdvicePayrollProcessErrors(this.payGroupId);
		} catch (NLESDPayAdviceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.checkForErrors=check;
	}
	public boolean getCheckForErrors()
	{
		return this.checkForErrors;
	}
	
	
	public void setImportStatus()
	{
		if(!(this.importJobBean == null))
		{
			if(this.importJobBean.getJobCompleted().equals(1))
			{
				this.importStatus= "Completed";
			}else
			{
				this.importStatus=  "Not Started";
			}
		}else{
			this.importStatus= "Not Started";
		}
			
	}
	public String getBgDate()
	{
		this.bgDate=this.payGroupBean.getPayBgDt();
		return this.bgDate;
	}
	public String getEndDate()
	{
		this.endDate=this.payGroupBean.getPayEndDt();
		return this.endDate;
	}
	public String getImportStatus()
	{
		setImportStatus();
		return this.importStatus;
	}
	public NLESDPayAdvicePayGroupBean getPayGroupBean() {
		return payGroupBean;
	}
	public void setPayGroupBean(NLESDPayAdvicePayGroupBean payGroupBean) {
		this.payGroupBean = payGroupBean;
	}
	public NLESDPayAdviceImportJobBean getImportJobBean() {
		return importJobBean;
	}
	public void setImportJobBean(NLESDPayAdviceImportJobBean importJobBean) {
		this.importJobBean = importJobBean;
	}
	public Integer getPayGroupId() {
		return payGroupId;
	}
	public void setPayGroupId(Integer payGroupId) {
		this.payGroupId = payGroupId;
	}
	public String getStubCreationStatus() {
		return stubCreationStatus;
	}
	public void setStubCreationStatus(String stubCreationStatus) {
		this.stubCreationStatus = stubCreationStatus;
	}
	public Date getStubCreationStarted() {
		return stubCreationStarted;
	}
	public void setStubCreationStarted(Date stubCreationStarted) {
		this.stubCreationStarted = stubCreationStarted;
	}
	public Date getStubCreationFinished() {
		return stubCreationFinished;
	}
	public void setStubCreationFinished(Date stubCreationFinished) {
		this.stubCreationFinished = stubCreationFinished;
	}
	public String getStubCreationBy() {
		return stubCreationBy;
	}
	public void setStubCreationBy(String stubCreationBy) {
		this.stubCreationBy = stubCreationBy;
	}
	public String getClosedStatus() {
		return closedStatus;
	}
	public void setClosedStatus(String stubEmailStatus) {
		this.closedStatus = stubEmailStatus;
	}
	public Date getClosedStarted() {
		return closedStarted;
	}
	public void setClosedStarted(Date stubEmailStarted) {
		this.closedStarted = stubEmailStarted;
	}
	public Date getClosedFinished() {
		return closedFinished;
	}
	public void setClosedFinished(Date stubEmailFinished) {
		this.closedFinished = stubEmailFinished;
	}
	public String getClosedBy() {
		return closedBy;
	}
	public void setClosedBy(String stubEmailBy) {
		this.closedBy = stubEmailBy;
	}
	public Integer getProcessFinished() {
		return processFinished;
	}
	public void setProcessFinished(Integer processFinished) {
		this.processFinished = processFinished;
	}
	public int compareTo(Object bean) {
		// TODO Auto-generated method stub
		int compareid=((NLESDPayAdvicePayrollProcessBean)bean).getPayGroupId();
		return compareid-this.payGroupId;
	}
	public String getStubCreationStartedFormatted()
	{
		if(this.stubCreationStarted != null)
		{
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
			this.stubCreationStartedFormatted=dt.format(this.stubCreationStarted);
			return this.stubCreationStartedFormatted;
		}else{
			this.stubCreationStartedFormatted="";
			return this.stubCreationStartedFormatted;
		}
		
	}
	public String getStubCreationFinishedFormatted()
	{
		if(this.stubCreationFinished != null)
		{
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
			this.stubCreationFinishedFormatted=dt.format(this.stubCreationFinished);
			return this.stubCreationFinishedFormatted;
		}else{
			this.stubCreationFinishedFormatted="";
			return this.stubCreationFinishedFormatted;
		}
		
	}
	public String getClosedStartedFormatted()
	{
		if(this.closedStarted != null)
		{
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
			this.closedStartedFormatted=dt.format(this.closedStarted);
			return this.closedStartedFormatted;
		}else{
			this.closedStartedFormatted="";
			return this.closedStartedFormatted;
		}
		
	}
	public String getClosedFinishedFormatted()
	{
		if(this.closedFinished != null)
		{
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
			this.closedFinishedFormatted=dt.format(this.closedFinished);
			return this.closedFinishedFormatted;
		}else{
			this.closedFinishedFormatted="";
			return this.closedFinishedFormatted;
		}
		
	}
	public Integer getTotalPayStubs() {
		return totalPayStubs;
	}
	public void setTotalPayStubs(Integer totalPayStubs) {
		this.totalPayStubs = totalPayStubs;
	}
	public Integer getTotalPayStubsSent() {
		return totalPayStubsSent;
	}
	public void setTotalPayStubsSent(Integer totalPayStubsSent) {
		this.totalPayStubsSent = totalPayStubsSent;
	}
	public Integer getTotalPayStubsNotSent() {
		return totalPayStubsNotSent;
	}
	public void setTotalPayStubsNotSent(Integer totalPayStubsNotSent) {
		this.totalPayStubsNotSent = totalPayStubsNotSent;
	}
	public String getManualFilename() {
		return manualFilename;
	}
	public void setManualFilename(String manualFilename) {
		this.manualFilename = manualFilename;
	}
	
	
}
