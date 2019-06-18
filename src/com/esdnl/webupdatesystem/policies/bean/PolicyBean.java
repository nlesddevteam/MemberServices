package com.esdnl.webupdatesystem.policies.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.esdnl.webupdatesystem.policies.constants.PolicyCategory;
import com.esdnl.webupdatesystem.policies.constants.PolicyStatus;

public class PolicyBean implements Serializable 
{
	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private PolicyCategory policyCategory;
	private PolicyStatus policyStatus;
	private String policyNumber;
	private String policyTitle;
	private String policyDocumentation;
	private String policyAdminDoc;
	private String addedBy;
	private Date dateAdded;
	private ArrayList<PolicyFileBean> otherPolicyFiles;
	public PolicyBean()
	{
		otherPolicyFiles = new ArrayList<PolicyFileBean>();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public PolicyCategory getPolicyCategory() {
		return policyCategory;
	}
	public void setPolicyCategory(PolicyCategory policyCategory) {
		this.policyCategory = policyCategory;
	}
	public PolicyStatus getPolicyStatus() {
		return policyStatus;
	}
	public void setPolicyStatus(PolicyStatus policyStatus) {
		this.policyStatus = policyStatus;
	}
	public String getPolicyNumber() {
		return policyNumber;
	}
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}
	public String getPolicyTitle() {
		return policyTitle;
	}
	public void setPolicyTitle(String policyTitle) {
		this.policyTitle = policyTitle;
	}
	public String getPolicyDocumentation() {
		return policyDocumentation;
	}
	public void setPolicyDocumentation(String policyDocumentation) {
		this.policyDocumentation = policyDocumentation;
	}
	public String getPolicyAdminDoc() {
		return policyAdminDoc;
	}
	public void setPolicyAdminDoc(String policyAdminDoc) {
		this.policyAdminDoc = policyAdminDoc;
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
	public ArrayList<PolicyFileBean> getOtherPolicyFiles() {
		return otherPolicyFiles;
	}
	public void setOtherPolicyFiles(ArrayList<PolicyFileBean> otherPolicyFiles) {
		this.otherPolicyFiles = otherPolicyFiles;
	}

	
}
