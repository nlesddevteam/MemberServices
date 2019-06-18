package com.esdnl.fund3.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AuditLogBean  implements Serializable {
	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private String userName;
	private Date auditDate;
	private String logEntry;
	private Integer projectId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	public String getLogEntry() {
		return logEntry;
	}
	public void setLogEntry(String logEntry) {
		this.logEntry = logEntry;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	
	public String getAuditDateFormatted() {
		if(this.auditDate != null){
			return new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(this.auditDate);
		}else{
			return "";
		}
	}
	
}
