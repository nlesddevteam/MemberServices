package com.esdnl.criticalissues.bean;

import java.util.Date;

import com.esdnl.criticalissues.constant.ActionItemStatusConstant;
import com.esdnl.mrs.MaintenanceRequest;

public class ReportActionItemBean {

	private int itemId;
	private ReportBean report;
	private MaintenanceRequest maintenanceRequest;
	private Date dueDate;
	private ActionItemStatusConstant status;

	public ReportActionItemBean(ReportBean report, MaintenanceRequest maintenanceRequest) {

		super();
		this.itemId = 0;
		this.report = report;
		this.maintenanceRequest = maintenanceRequest;
	}

	public ReportActionItemBean() {

		this.itemId = 0;
		this.report = null;
		this.maintenanceRequest = null;
	}

	public int getItemId() {

		return itemId;
	}

	public void setItemId(int itemId) {

		this.itemId = itemId;
	}

	public ReportBean getReport() {

		return report;
	}

	public void setReport(ReportBean report) {

		this.report = report;
	}

	public MaintenanceRequest getMaintenanceRequest() {

		return maintenanceRequest;
	}

	public void setMaintenanceRequest(MaintenanceRequest mr) {

		this.maintenanceRequest = mr;
	}

}
