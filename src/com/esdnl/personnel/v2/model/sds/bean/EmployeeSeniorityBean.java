package com.esdnl.personnel.v2.model.sds.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
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
	private Date seniorityDate1;
	private Date seniorityDate2;

	public EmployeeSeniorityBean() {

		this.employee = null;
		this.unionCode = "";
		this.seniorityValue1 = 0.0;
		this.seniorityValue2 = 0.0;
		this.seniorityValue3 = 0.0;
		this.seniorityDate1 = null;
		this.seniorityDate2 = null;
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

	public Date getSeniorityDate1() {
		return seniorityDate1;
	}

	public void setSeniorityDate1(Date seniorityDate1) {
		this.seniorityDate1 = seniorityDate1;
	}

	public Date getSeniorityDate2() {
		return seniorityDate2;
	}

	public void setSeniorityDate2(Date seniorityDate2) {
		this.seniorityDate2 = seniorityDate2;
	}
	public String getSeniorityDate1Formatted() {
		String DATE_FORMAT = "dd/MM/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		if(this.seniorityDate1 == null) {
			return "N/A";
		}else {
			return sdf.format(this.seniorityDate1);
		}
		
	}
	public String getSeniorityDate2Formatted() {
		String DATE_FORMAT = "dd/MM/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		if(this.seniorityDate2 == null) {
			return "N/A";
		}else {
			return sdf.format(this.seniorityDate2);
		}
		
	}
	public double getShortlistValue() {
		double sen=0;
		if(this.unionCode == null) {
			sen=0;
		}else if(this.unionCode.equals(EmployeeSeniorityBean.Union.NLTA_TLA.code)) {
			sen = this.getSeniorityTotal();
		}else {
			 if(this.unionCode.equals(EmployeeSeniorityBean.Union.CUPE_1560.code) || this.unionCode.equals(EmployeeSeniorityBean.Union.CUPE_STUDENT_ASSISTANTS.code)  ||
					 this.unionCode.equals(EmployeeSeniorityBean.Union.CUPE_2033.code)  || this.unionCode.equals(EmployeeSeniorityBean.Union.CUPE_WESTERN.code)  || 
					 this.unionCode.equals(EmployeeSeniorityBean.Union.CUPE_WESTERN.code )) {
				 //senority date so we calculated the years
				 if(this.seniorityDate1 != null) {
					 Date today = new Date();
					 long diffInMillies = Math.abs(today.getTime() - this.seniorityDate1.getTime());
					 long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
					 double years = (double)diff/260d; //365.2425d;
					 sen = years;
				 }else if(this.seniorityDate2 != null) {
					 Date today = new Date();
					 long diffInMillies = Math.abs(today.getTime() - this.seniorityDate2.getTime());
					 long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
					 double years = (double)diff/260d; //365.2425d;
					 sen = years;
				 }else {
					 sen=0;
				 }
			 }else if(this.unionCode.equals(EmployeeSeniorityBean.Union.NAPE_CENTRAL.code) || this.unionCode.equals(EmployeeSeniorityBean.Union.NAPE_AVALON_WEST.code) ||
					 this.unionCode.equals(EmployeeSeniorityBean.Union.NAPE_LABRADOR.code)  || this.unionCode.equals(EmployeeSeniorityBean.Union.NAPE_VISTA.code)  || 
					 this.unionCode.equals(EmployeeSeniorityBean.Union.NAPE_WESTERN.code )) {
				 		if(this.getSeniorityTotal() > 0) {
				 			sen = this.getSeniorityTotal()/260;//260 working days a year
				 		}else {
				 			sen=0;
				 		}
				 
			 }
		}
		return sen;
	}
}
