package com.awsd.travel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class YearlyClaimsDetailReportItem {

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
	private double totalKmsAmount;
	private double totalMeals;
	private double totalLodging;
	private double totalOther;
	private double totalClaim;

	private YearlyClaimsDetailReportItem() {

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

	public double getTotalKmsAmount() {

		return totalKmsAmount;
	}

	public void setTotalKmsAmount(double totalKmsAmount) {

		this.totalKmsAmount = totalKmsAmount;
	}

	public double getTotalMeals() {

		return totalMeals;
	}

	public void setTotalMeals(double totalMeals) {

		this.totalMeals = totalMeals;
	}

	public double getTotalLodging() {

		return totalLodging;
	}

	public void setTotalLodging(double totalLodging) {

		this.totalLodging = totalLodging;
	}

	public double getTotalOther() {

		return totalOther;
	}

	public void setTotalOther(double totalOther) {

		this.totalOther = totalOther;
	}

	public static YearlyClaimsDetailReportItem create(ResultSet rs) throws SQLException {

		YearlyClaimsDetailReportItem item = new YearlyClaimsDetailReportItem();

		item.setMonth(rs.getString("month"));
		item.setPersonnelFirstname(rs.getString("personnel_firstname"));
		item.setPersonnelId(rs.getInt("personnel_id"));
		item.setPersonnelLastname(rs.getString("personnel_lastname"));
		item.setPersonnelPhone1(rs.getString("phone_1"));
		item.setTotalKmsAmount(rs.getDouble("total_kms_amount"));
		item.setTotalKms(rs.getDouble("total_kms"));
		item.setPersonnelCommunity(rs.getString("CUR_COMMUNITY"));
		item.setPersonnelPostalcode(rs.getString("CUR_POSTALCODE"));
		item.setPersonnelProvince(rs.getString("CUR_PROVINCE"));
		item.setPersonnelStreetAddress(rs.getString("CUR_STR_ADDR"));
		item.setTotalLodging(rs.getDouble("total_lodging"));
		item.setTotalMeals(rs.getDouble("total_meals"));
		item.setTotalOther(rs.getDouble("total_other"));

		return item;

	}

	public double getTotalClaim() {
		return totalClaim;
	}

	public void setTotalClaim(double totalClaim) {
		this.totalClaim = totalClaim;
	}
	public static YearlyClaimsDetailReportItem createTotal(ResultSet rs) throws SQLException {

		YearlyClaimsDetailReportItem item = new YearlyClaimsDetailReportItem();

		item.setMonth("NA");
		item.setPersonnelFirstname(rs.getString("personnel_firstname"));
		item.setPersonnelId(rs.getInt("personnel_id"));
		item.setPersonnelLastname(rs.getString("personnel_lastname"));
		item.setPersonnelPhone1(rs.getString("phone_1"));
		item.setTotalKmsAmount(rs.getDouble("totalkmamt"));
		item.setTotalKms(rs.getDouble("totalkm"));
		item.setPersonnelCommunity(rs.getString("CUR_COMMUNITY"));
		item.setPersonnelPostalcode(rs.getString("CUR_POSTALCODE"));
		item.setPersonnelProvince(rs.getString("CUR_PROVINCE"));
		item.setPersonnelStreetAddress(rs.getString("CUR_STR_ADDR"));
		item.setTotalLodging(rs.getDouble("totallod"));
		item.setTotalMeals(rs.getDouble("totalmeals"));
		item.setTotalOther(rs.getDouble("totalother"));
		item.setTotalClaim(rs.getDouble("gtotal"));

		return item;

	}	

}
