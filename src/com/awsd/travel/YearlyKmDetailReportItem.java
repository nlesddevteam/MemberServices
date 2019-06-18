package com.awsd.travel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class YearlyKmDetailReportItem {

	private int personnelId;
	private String personnelFirstname;
	private String personnelLastname;
	private String personnelStreetAddress;
	private String personnelCommunity;
	private String personnelProvince;
	private String personnelPostalcode;
	private String personnelPhone1;
	private String month;
	private double totalKms;
	private double totalAmount;

	private YearlyKmDetailReportItem() {

	}

	public int getPersonnelId() {

		return personnelId;
	}

	public void setPersonnelId(int personnelId) {

		this.personnelId = personnelId;
	}

	public String getPersonnelFirstname() {

		return personnelFirstname;
	}

	public void setPersonnelFirstname(String personnelFirstname) {

		this.personnelFirstname = personnelFirstname;
	}

	public String getPersonnelLastname() {

		return personnelLastname;
	}

	public void setPersonnelLastname(String personnelLastname) {

		this.personnelLastname = personnelLastname;
	}

	public String getPersonnelStreetAddress() {

		return personnelStreetAddress;
	}

	public void setPersonnelStreetAddress(String personnelStreetAddress) {

		this.personnelStreetAddress = personnelStreetAddress;
	}

	public String getPersonnelCommunity() {

		return personnelCommunity;
	}

	public void setPersonnelCommunity(String personnelCommunity) {

		this.personnelCommunity = personnelCommunity;
	}

	public String getPersonnelProvince() {

		return personnelProvince;
	}

	public void setPersonnelProvince(String personnelProvince) {

		this.personnelProvince = personnelProvince;
	}

	public String getPersonnelPostalcode() {

		return personnelPostalcode;
	}

	public void setPersonnelPostalcode(String personnelPostalcode) {

		this.personnelPostalcode = personnelPostalcode;
	}

	public String getPersonnelPhone1() {

		return personnelPhone1;
	}

	public void setPersonnelPhone1(String personnelPhone1) {

		this.personnelPhone1 = personnelPhone1;
	}

	public String getMonth() {

		return month;
	}

	public void setMonth(String month) {

		this.month = month;
	}

	public double getTotalKms() {

		return totalKms;
	}

	public void setTotalKms(double totalKms) {

		this.totalKms = totalKms;
	}

	public double getTotalAmount() {

		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {

		this.totalAmount = totalAmount;
	}

	public static YearlyKmDetailReportItem create(ResultSet rs) throws SQLException {

		YearlyKmDetailReportItem item = new YearlyKmDetailReportItem();

		item.setMonth(rs.getString("month"));
		item.setPersonnelFirstname(rs.getString("personnel_firstname"));
		item.setPersonnelId(rs.getInt("personnel_id"));
		item.setPersonnelLastname(rs.getString("personnel_lastname"));
		item.setPersonnelPhone1(rs.getString("phone_1"));
		item.setTotalAmount(rs.getDouble("total_amount"));
		item.setTotalKms(rs.getDouble("total_kms"));
		item.setPersonnelCommunity(rs.getString("CUR_COMMUNITY"));
		item.setPersonnelPostalcode(rs.getString("CUR_POSTALCODE"));
		item.setPersonnelProvince(rs.getString("CUR_PROVINCE"));
		item.setPersonnelStreetAddress(rs.getString("CUR_STR_ADDR"));

		return item;

	}

}
