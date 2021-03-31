package com.esdnl.personnel.jobs.bean;

import org.apache.commons.lang.StringEscapeUtils;

import com.esdnl.personnel.v2.model.sds.bean.EmployeeBean;

public class TeacherAllocationRedundantPositionBean {

	private int positionId;
	private int allocationId;
	private EmployeeBean employee;
	private String rationale;
	private double unit;

	public TeacherAllocationRedundantPositionBean() {

		this.positionId = 0;
		this.allocationId = 0;
		this.employee = null;
		this.rationale = null;
		this.unit = 0.0;
	}

	public int getPositionId() {

		return positionId;
	}

	public void setPositionId(int positionId) {

		this.positionId = positionId;
	}

	public int getAllocationId() {

		return allocationId;
	}

	public void setAllocationId(int allocationId) {

		this.allocationId = allocationId;
	}

	public EmployeeBean getEmployee() {

		return employee;
	}

	public void setEmployee(EmployeeBean employee) {

		this.employee = employee;
	}

	public String getRationale() {

		return rationale;
	}

	public void setRationale(String rationale) {

		this.rationale = rationale;
	}

	public double getUnit() {

		return unit;
	}

	public void setUnit(double unit) {

		this.unit = unit;
	}

	public String toXML() {

		StringBuffer buf = new StringBuffer();

		buf.append("<TEACHER-ALLOCATION-REDUNDANT-POSITION-BEAN POSITION-ID=\"" + this.positionId + "\" ALLOCATION-ID=\""
				+ this.allocationId + "\" EMP-ID=\"" + this.employee.getEmpId().trim() + "\" EMP-NAME=\""
				+ this.employee.getFullnameReverse() + "\" RATIONALE=\"" + StringEscapeUtils.escapeHtml(this.rationale)
				+ "\" UNIT=\"" + this.unit + "\" />");

		return buf.toString();
	}

}
