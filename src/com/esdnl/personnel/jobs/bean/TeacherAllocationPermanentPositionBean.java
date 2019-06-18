package com.esdnl.personnel.jobs.bean;

import org.apache.commons.lang.StringEscapeUtils;

import com.esdnl.personnel.v2.model.sds.bean.EmployeeBean;

public class TeacherAllocationPermanentPositionBean {

	private int positionId;
	private int allocationId;
	private EmployeeBean employee;
	private int classSize;
	private double unit;
	private String assignment;
	private String tenur;

	public TeacherAllocationPermanentPositionBean() {

		this.positionId = 0;
		this.allocationId = 0;
		this.employee = null;
		this.classSize = 0;
		this.unit = 0.0;
		this.assignment = null;
		this.tenur = null;
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

	public int getClassSize() {

		return classSize;
	}

	public void setClassSize(int classSize) {

		this.classSize = classSize;
	}

	public double getUnit() {

		return unit;
	}

	public void setUnit(double unit) {

		this.unit = unit;
	}

	public String getAssignment() {

		return assignment;
	}

	public void setAssignment(String assignment) {

		this.assignment = assignment;
	}

	public String getTenur() {

		return tenur;
	}

	public void setTenur(String tenur) {

		this.tenur = tenur;
	}

	public String toXML() {

		StringBuffer buf = new StringBuffer();

		buf.append("<TEACHER-ALLOCATION-PERMANENT-POSITION-BEAN POSITION-ID=\""
				+ this.positionId
				+ "\" ALLOCATION-ID=\""
				+ this.allocationId
				+ "\" EMP-ID=\""
				+ this.employee.getEmpId().trim()
				+ "\" EMP-NAME=\""
				+ this.employee.getFullnameReverse()
				+ "\" "
				+ (this.employee.getSeniority() != null ? "SENIORITY-1=\"" + this.employee.getSeniority().getSeniorityValue1()
						+ "\" SENIORITY-2=\"" + this.employee.getSeniority().getSeniorityValue2() + "\" SENIORITY-3=\""
						+ this.employee.getSeniority().getSeniorityValue3() + "\" " : "") + "CLASS-SIZE=\"" + this.classSize
				+ "\" ASSIGNMENT=\"" + StringEscapeUtils.escapeHtml(this.assignment) + "\" UNIT=\"" + this.unit + "\" TENUR=\""
				+ this.tenur + "\" />");

		return buf.toString();
	}

}
