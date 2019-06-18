package com.esdnl.tsdoc.bean;

import java.util.Date;

import com.awsd.personnel.Personnel;

public class TrusteeDocumentAuditBean {

	private int auditId;
	private Date auditDate;
	private int documentId;
	private Personnel personnel;
	private AuditActionBean action;
	private String actionComment;

	public TrusteeDocumentAuditBean(int auditId, Date auditDate, int documentId, Personnel personnel,
			AuditActionBean action, String actionComment) {

		super();
		this.auditId = auditId;
		this.auditDate = auditDate;
		this.documentId = documentId;
		this.personnel = personnel;
		this.action = action;
		this.actionComment = actionComment;
	}

	public TrusteeDocumentAuditBean() {

		this(0, null, 0, null, null, null);
	}

	public int getAuditId() {

		return auditId;
	}

	public void setAuditId(int auditId) {

		this.auditId = auditId;
	}

	public Date getAuditDate() {

		return auditDate;
	}

	public void setAuditDate(Date auditDate) {

		this.auditDate = auditDate;
	}

	public int getDocumentId() {

		return documentId;
	}

	public void setDocumentId(int documentId) {

		this.documentId = documentId;
	}

	public void setDocument(TrusteeDocumentBean document) {

		this.documentId = document.getDocumentId();
	}

	public Personnel getPersonnel() {

		return personnel;
	}

	public void setPersonnel(Personnel personnel) {

		this.personnel = personnel;
	}

	public AuditActionBean getAction() {

		return action;
	}

	public void setAction(AuditActionBean action) {

		this.action = action;
	}

	public String getActionComment() {

		return actionComment;
	}

	public void setActionComment(String actionComment) {

		this.actionComment = actionComment;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + auditId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrusteeDocumentAuditBean other = (TrusteeDocumentAuditBean) obj;
		if (auditId != other.auditId)
			return false;
		return true;
	}

}
