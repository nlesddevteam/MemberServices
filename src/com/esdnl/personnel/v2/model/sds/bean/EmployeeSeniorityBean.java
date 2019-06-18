package com.esdnl.personnel.v2.model.sds.bean;

/**
 * 
 * @author chris
 *
 * partial representation of the SDS table PRSENMAS
 */
public class EmployeeSeniorityBean {

	private EmployeeBean employee;
	private String unionCode;
	private double seniorityValue1;
	private double seniorityValue2;
	private double seniorityValue3;

	public EmployeeSeniorityBean() {

		this.employee = null;
		this.unionCode = "";
		this.seniorityValue1 = 0.0;
		this.seniorityValue2 = 0.0;
		this.seniorityValue3 = 0.0;
	}

	public EmployeeBean getEmployee() {

		return employee;
	}

	public void setEmployee(EmployeeBean employee) {

		this.employee = employee;
	}

	public String getUnionCode() {

		return unionCode;
	}

	public void setUnionCode(String unionCode) {

		this.unionCode = unionCode;
	}

	public double getSeniorityValue1() {

		return seniorityValue1;
	}

	public void setSeniorityValue1(double seniorityValue1) {

		this.seniorityValue1 = seniorityValue1;
	}

	public double getSeniorityValue2() {

		return seniorityValue2;
	}

	public void setSeniorityValue2(double seniorityValue2) {

		this.seniorityValue2 = seniorityValue2;
	}

	public double getSeniorityValue3() {

		return seniorityValue3;
	}

	public void setSeniorityValue3(double seniorityValue3) {

		this.seniorityValue3 = seniorityValue3;
	}
	public double getSeniorityTotal(){
		return this.seniorityValue1 + this.seniorityValue2 + this.seniorityValue3;
	}
}
