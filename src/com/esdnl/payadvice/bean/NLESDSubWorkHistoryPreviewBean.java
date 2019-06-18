package com.esdnl.payadvice.bean;

import java.io.Serializable;

public class NLESDSubWorkHistoryPreviewBean implements Serializable {

	private static final long serialVersionUID = -8771605504141292777L;
	private String company;
	private String deptId;
	private Integer workHistoryRecordsCount;
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public Integer getWorkHistoryRecordsCount() {
		return workHistoryRecordsCount;
	}
	public void setWorkHistoryRecordsCount(Integer workHistoryRecordsCount) {
		this.workHistoryRecordsCount = workHistoryRecordsCount;
	}
}
