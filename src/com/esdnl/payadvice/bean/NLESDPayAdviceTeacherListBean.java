package com.esdnl.payadvice.bean;

import java.io.Serializable;

public class NLESDPayAdviceTeacherListBean implements Serializable,Comparable {

		private static final long serialVersionUID = -8771605504141293333L;
		private Integer id;
		private String payGp;
		private String payBgDt;
		private String payEndDt;
		private String busUnit;
		private String checkNum;
		private String checkDt;
		private String empName;
		private String empNumber;
		private String payrollId;
		private Integer hisCount;
		private Integer empInfoId;

		public Integer getEmpInfoId() {
			return empInfoId;
		}
		public void setEmpInfoId(Integer empInfoId) {
			this.empInfoId = empInfoId;
		}
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getPayGp() {
			return payGp;
		}
		public void setPayGp(String payGp) {
			this.payGp = payGp;
		}
		public String getPayBgDt() {
			return payBgDt;
		}
		public void setPayBgDt(String payBgDt) {
			this.payBgDt = payBgDt;
		}
		public String getPayEndDt() {
			return payEndDt;
		}
		public void setPayEndDt(String payEndDt) {
			this.payEndDt = payEndDt;
		}
		public String getBusUnit() {
			return busUnit;
		}
		public void setBusUnit(String busUnit) {
			this.busUnit = busUnit;
		}
		public String getCheckNum() {
			return checkNum;
		}
		public void setCheckNum(String checkNum) {
			this.checkNum = checkNum;
		}
		public String getCheckDt() {
			return checkDt;
		}
		public void setCheckDt(String check_Dt) {
			this.checkDt = check_Dt;
		}
		public String getEmpName() {
			return empName;
		}
		public void setEmpName(String empName) {
			this.empName = empName;
		}
		public String getPayrollId() {
			return payrollId;
		}
		public void setPayrollId(String payrollId) {
			this.payrollId = payrollId;
		}
		public String getEmpNumber() {
			return empNumber;
		}
		public void setEmpNumber(String empNumber) {
			this.empNumber = empNumber;
		}
		public int compareTo(Object bean) {
			// TODO Auto-generated method stub
			int compareid=((NLESDPayAdviceTeacherListBean)bean).getId();
			return compareid-this.id;
		}
		public Integer getHisCount() {
			return hisCount;
		}
		public void setHisCount(Integer hisCount) {
			this.hisCount = hisCount;
		}

}
