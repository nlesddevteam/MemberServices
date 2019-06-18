package com.awsd.travel;

import com.awsd.personnel.PersonnelDB;
import com.awsd.travel.bean.Division;
import com.awsd.travel.constant.KeyType;

public class TravelClaimSupervisorRule {

	private int ruleId;
	private String supervisorKey;
	private KeyType supervisorKeyType;
	private String employeeKey;
	private KeyType employeeKeyType;
	private Division division;

	public TravelClaimSupervisorRule() {

		ruleId = 0;

		supervisorKey = "0";
		supervisorKeyType = null;

		employeeKey = "0";
		employeeKeyType = null;
	}

	public int getRuleId() {

		return this.ruleId;
	}

	public void setRuleId(int rule_id) {

		this.ruleId = rule_id;
	}

	public String getSupervisorKey() {

		return this.supervisorKey;
	}

	public void setSupervisorKey(String supervisor_key) {

		this.supervisorKey = supervisor_key;
	}

	public KeyType getSupervisorKeyType() {

		return this.supervisorKeyType;
	}

	public void setSupervisorKeyType(KeyType supervisor_keytype) {

		this.supervisorKeyType = supervisor_keytype;
	}

	public String getSupervisor() {

		String sup = "";

		switch (this.supervisorKeyType.getValue()) {
		case 1: // ROLE
			sup = this.supervisorKey;
			break;
		case 2:// USER
			try {
				sup = PersonnelDB.getPersonnel(Integer.parseInt(this.supervisorKey)).getFullName();
			}
			catch (Exception e) {
				sup = "";
			}
		}

		return sup;
	}

	public String getEmployeeKey() {

		return this.employeeKey;
	}

	public void setEmployeeKey(String employee_key) {

		this.employeeKey = employee_key;
	}

	public KeyType getEmployeeKeyType() {

		return this.employeeKeyType;
	}

	public void setEmployeeKeyType(KeyType employee_keytype) {

		this.employeeKeyType = employee_keytype;
	}

	public String getEmployee() {

		String sup = "";

		switch (this.employeeKeyType.getValue()) {
		case 1: // ROLE
			sup = this.employeeKey;
			break;
		case 2:// USER
			try {
				sup = PersonnelDB.getPersonnel(Integer.parseInt(this.employeeKey)).getFullName();
			}
			catch (Exception e) {
				e.printStackTrace();
				sup = "";
			}
		}

		return sup;
	}

	public Division getDivision() {

		return division;
	}

	public void setDivision(Division division) {

		this.division = division;
	}

}
