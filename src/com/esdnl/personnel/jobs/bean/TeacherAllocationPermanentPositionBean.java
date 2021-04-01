package com.esdnl.personnel.jobs.bean;

import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

import com.esdnl.personnel.jobs.dao.TeacherAllocationManager;
import com.esdnl.personnel.v2.model.sds.bean.EmployeeBean;
import com.esdnl.personnel.v2.model.sds.bean.EmployeePositionBean;
import com.esdnl.personnel.v2.model.sds.bean.EmployeeSeniorityBean;

public class TeacherAllocationPermanentPositionBean {

	private int positionId;
	private int allocationId;
	private TeacherAllocationBean allocation;
	private EmployeeBean employee;
	private int classSize;
	private double unit;
	private String assignment;
	private String tenur;
	private String applicantLink;

	public TeacherAllocationPermanentPositionBean() {

		this.positionId = 0;
		this.allocationId = 0;
		this.allocation = null;
		this.employee = null;
		this.classSize = 0;
		this.unit = 0.0;
		this.assignment = null;
		this.tenur = null;
		this.applicantLink = null;
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

	public TeacherAllocationBean getAllocation() {

		if (this.allocation == null) {
			try {
				this.allocation = TeacherAllocationManager.getTeacherAllocationBean(this.allocationId, false);
			}
			catch (JobOpportunityException e) {
				// DO NOTHING
			}
		}

		return allocation;
	}

	public void setAllocation(TeacherAllocationBean allocation) {

		this.allocation = allocation;
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

	public boolean isTCH() {

		List<EmployeePositionBean> positions = this.employee.getPositions(this.getAllocation());

		return (positions != null && positions.size() > 0) ? !positions.get(0).getPositionCode().isTlaPosition() : false;
	}

	public boolean isTLA() {

		List<EmployeePositionBean> positions = this.employee.getPositions(this.getAllocation());

		return (positions != null && positions.size() > 0) ? positions.get(0).getPositionCode().isTlaPosition() : false;
	}

	public String toXML() {

		StringBuffer buf = new StringBuffer();

		buf.append("<TEACHER-ALLOCATION-PERMANENT-POSITION-BEAN POSITION-ID=\"" + this.positionId + "\" ALLOCATION-ID=\""
				+ this.allocationId + "\" EMP-ID=\"" + this.employee.getEmpId().trim() + "\" EMP-NAME=\""
				+ this.employee.getFullnameReverse() + "\" ");

		List<EmployeePositionBean> positions = this.employee.getPositions(this.getAllocation());

		if (positions != null && positions.size() > 0) {
			EmployeeSeniorityBean seniority = positions.get(0).getPositionCode().isTlaPosition()
					? this.employee.getSeniority(EmployeeSeniorityBean.Union.NLTA_TLA)
					: this.employee.getSeniority(EmployeeSeniorityBean.Union.NLTA);
			if (seniority != null) {
				buf.append("SENIORITY-1=\"" + seniority.getSeniorityValue1() + "\" SENIORITY-2=\""
						+ seniority.getSeniorityValue2() + "\" SENIORITY-3=\"" + seniority.getSeniorityValue3() + "\" ");
			}

			buf.append("POSITION-TYPE=\"" + (positions.get(0).getPositionCode().isTlaPosition() ? "TLA" : "TCH") + "\" ");
		}
		else {
			buf.append("POSITION-TYPE=\"UNKNOWN\" ");
		}

		buf.append("CLASS-SIZE=\"" + this.classSize + "\" ASSIGNMENT=\"" + StringEscapeUtils.escapeHtml(this.assignment)
				+ "\" UNIT=\"" + this.unit + "\" TENUR=\"" + this.tenur + "\"" + " PROFILE-LINK=\"" + this.applicantLink + "\""
				+ "  />");

		return buf.toString();
	}

	public String getApplicantLink() {

		return applicantLink;
	}

	public void setApplicantLink(String applicantLink) {

		this.applicantLink = applicantLink;
	}

}
