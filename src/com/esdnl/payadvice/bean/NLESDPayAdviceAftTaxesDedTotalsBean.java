package com.esdnl.payadvice.bean;

import java.io.Serializable;

public class NLESDPayAdviceAftTaxesDedTotalsBean implements Serializable {

			private static final long serialVersionUID = -8771605504141293333L;
			private Integer id;
			private double atxdTtlCur;
			private double atxdTtlYtd;
			private String empNumber;
			private int payGroupId;
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
			public double getAtxdTtlCur() {
				return atxdTtlCur;
			}
			public void setAtxdTtlCur(double atxdTtlCur) {
				this.atxdTtlCur = atxdTtlCur;
			}
			public double getAtxdTtlYtd() {
				return atxdTtlYtd;
			}
			public void setAtxdTtlYtd(double atxdTtlYtd) {
				this.atxdTtlYtd = atxdTtlYtd;
			}
			public String getEmpNumber() {
				return empNumber;
			}
			public void setEmpNumber(String empNumber) {
				this.empNumber = empNumber;
			}
			public int getPayGroupId() {
				return payGroupId;
			}
			public void setPayGroupId(int payGroupId) {
				this.payGroupId = payGroupId;
			}
}
