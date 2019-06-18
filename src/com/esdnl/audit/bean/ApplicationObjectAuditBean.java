package com.esdnl.audit.bean;

import java.util.Calendar;
import java.util.Date;

import com.awsd.mail.bean.AlertBean;
import com.awsd.mail.bean.EmailException;
import com.esdnl.audit.constant.ActionTypeConstant;
import com.esdnl.audit.constant.ApplicationConstant;
import com.esdnl.audit.dao.ApplicationObjectAuditManager;

public class ApplicationObjectAuditBean {

	private int auditId;
	private ApplicationConstant application;
	private String objectType;
	private String objectId;
	private Date when;
	private String who;
	private ActionTypeConstant actionType;
	private String action;
	private String oldData;
	private String newData;

	public ApplicationObjectAuditBean() {

		this(0, null, null, null, Calendar.getInstance().getTime(), null, null, null, null);
	}

	public ApplicationObjectAuditBean(int auditId, ApplicationConstant application, String objectType, String objectId,
			Date when, String who, String action, String oldData, String newData) {

		this.auditId = auditId;
		this.application = application;
		this.objectType = objectType;
		this.objectId = objectId;
		this.when = when;
		this.who = who;
		this.action = action;
		this.oldData = oldData;
		this.newData = newData;
	}

	public int getAuditId() {

		return auditId;
	}

	public void setAuditId(int auditId) {

		this.auditId = auditId;
	}

	public ApplicationConstant getApplication() {

		return application;
	}

	public void setApplication(ApplicationConstant application) {

		this.application = application;
	}

	public String getObjectType() {

		return objectType;
	}

	public void setObjectType(String objectType) {

		this.objectType = objectType;
	}

	public String getObjectId() {

		return objectId;
	}

	public void setObjectId(String objectId) {

		this.objectId = objectId;
	}

	public Date getWhen() {

		return when;
	}

	public void setWhen(Date when) {

		this.when = when;
	}

	public String getWho() {

		return who;
	}

	public void setWho(String who) {

		this.who = who;
	}

	public String getAction() {

		return action;
	}

	public void setAction(String action) {

		this.action = action;
	}

	public String getOldData() {

		return oldData;
	}

	public void setOldData(String oldData) {

		this.oldData = oldData;
	}

	public String getNewData() {

		return newData;
	}

	public void setNewData(String newData) {

		this.newData = newData;
	}

	public void saveBean() {

		try {
			ApplicationObjectAuditManager.addApplicationObjectAudit(this);
		}
		catch (AuditException e) {
			System.err.println("AUDIT ERROR: " + e.getMessage());

			try {
				new AlertBean(e);
			}
			catch (EmailException ex) {}
		}
	}

	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((application == null) ? 0 : application.hashCode());
		result = prime * result + auditId;
		result = prime * result + objectId.hashCode();
		result = prime * result + ((objectType == null) ? 0 : objectType.hashCode());
		result = prime * result + ((when == null) ? 0 : when.hashCode());
		result = prime * result + ((who == null) ? 0 : who.hashCode());
		return result;
	}

	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ApplicationObjectAuditBean other = (ApplicationObjectAuditBean) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		}
		else if (!action.equals(other.action))
			return false;
		if (application == null) {
			if (other.application != null)
				return false;
		}
		else if (!application.equals(other.application))
			return false;
		if (auditId != other.auditId)
			return false;
		if (!objectId.equals(other.objectId))
			return false;
		if (objectType == null) {
			if (other.objectType != null)
				return false;
		}
		else if (!objectType.equals(other.objectType))
			return false;
		if (when == null) {
			if (other.when != null)
				return false;
		}
		else if (!when.equals(other.when))
			return false;
		if (who == null) {
			if (other.who != null)
				return false;
		}
		else if (!who.equals(other.who))
			return false;
		return true;
	}

	public ActionTypeConstant getActionType() {

		return actionType;
	}

	public void setActionType(ActionTypeConstant actionType) {

		this.actionType = actionType;
	}
}
