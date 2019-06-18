package com.esdnl.webupdatesystem.policies.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PolicyFileBean implements Serializable 
{
	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private String pfTitle;
	private String pfDoc;
	private String addedBy;
	private Date dateAdded;
	private Integer policyId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPfTitle() {
		return pfTitle;
	}
	public void setPfTitle(String pfTitle) {
		this.pfTitle = pfTitle;
	}
	public String getPfDoc() {
		return pfDoc;
	}
	public void setPfDoc(String pfDoc) {
		this.pfDoc = pfDoc;
	}
	public String getAddedBy() {
		return addedBy;
	}
	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	public String getDateAddedFormatted() {

		return new SimpleDateFormat("dd/MM/yyyy").format(this.dateAdded);
	}
	public Integer getPolicyId() {
		return policyId;
	}
	public void setPolicyId(Integer policyId) {
		this.policyId = policyId;
	}
}