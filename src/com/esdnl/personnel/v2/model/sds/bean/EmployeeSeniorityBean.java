package com.esdnl.personnel.v2.model.sds.bean;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author chris
 *
 * partial representation of the SDS table PRSENMAS
 */
public class EmployeeSeniorityBean {

	public enum Union {

		CUPE_1560("01", "CUPE 1560 (Avalon East)"), NLTA("02", "NLTA"), CUPE_STUDENT_ASSISTANTS("03",
				"NAPE (Student Assistants)"), CUPE_2033("04", "CUPE 2033 (Burin)"), NAPE_VISTA("05",
						"NAPE (Vista)"), NAPE_AVALON_WEST("06", "NAPE (Avalon West)"), NAPE_CENTRAL("07",
								"NAPE - Central"), NAPE_WESTERN("08", "NAPE (Western)"), NAPE_LABRADOR("09",
										"NAPE (Labrador)"), CUPE_WESTERN("10", "CUPE (Western)"), CUPE_NORTHERN("11",
												"CUPE (Northern)"), NLTA_TLA("12",
														"NLTA (TLA Teach/Learn Assistant)"), UNKNOWN("-999", "INVALID UNION CODE");

		private String code;
		private String name;

		private Union(String code, String name) {

			this.code = code;
			this.name = name;
		}

		public String getUnionCode() {

			return this.code;
		}

		public String getUnionName() {

			return this.name;
		}

		public boolean equals(Union union) {

			return union != null && StringUtils.endsWithIgnoreCase(union.code, this.code);
		}

		public String toString() {

			return this.name;
		}

		public static Union get(String code) {

			Union tmp = UNKNOWN;

			if (StringUtils.isNotBlank(code)) {
				for (Union u : Union.values()) {
					if (StringUtils.endsWithIgnoreCase(code, u.getUnionCode())) {
						tmp = u;
						break;
					}
				}
			}

			return tmp;
		}
	}

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

	public Union getUnion() {

		return Union.get(this.getUnionCode());
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

	public double getSeniorityTotal() {

		return this.seniorityValue1 + this.seniorityValue2 + this.seniorityValue3;
	}
}
