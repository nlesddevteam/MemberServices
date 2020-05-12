package com.esdnl.personnel.v2.model.sds.bean;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.esdnl.personnel.v2.model.availability.bean.EmployeeAvailabilityBean;
import com.esdnl.personnel.v2.model.recognition.bean.IEntity;
import com.esdnl.personnel.v2.model.sds.constant.PositionConstant;
import com.esdnl.util.StringUtils;

public class EmployeeBean implements IEntity {

	private String empId;
	private String SIN;
	private String lastName;
	private String firstName;
	private String previousName;
	private String title;
	private String gender;
	private String address1;
	private String address2;
	private String city;
	private String postalCode;
	private String province;
	private String phone;
	private String alternatePhone;
	private String email;
	private String status;
	private Date seniorityDate;
	private Date boardStartDate;
	private String schoolYear;
	private PositionConstant position;
	private String tenur;
	private int tenur_code;
	private String location_prefs;
	private double FTE;
	private String positionDescription;

	private EmployeeAvailabilityBean current_availability;

	private Map<EmployeeSeniorityBean.Union, EmployeeSeniorityBean> seniority;

	public EmployeeBean() {

		this.email = null;
		this.SIN = null;
		this.lastName = null;
		this.firstName = null;
		this.previousName = null;
		this.title = null;
		this.gender = null;
		this.address1 = null;
		this.address2 = null;
		this.city = null;
		this.postalCode = null;
		this.province = null;
		this.phone = null;
		this.alternatePhone = null;
		this.email = null;
		this.status = null;
		this.seniorityDate = null;
		this.boardStartDate = null;
		this.schoolYear = null;
		this.position = null;
		this.tenur = null;
		this.tenur_code = 999;
		this.location_prefs = null;
		this.current_availability = null;
		this.seniority = new HashMap<>();
		this.FTE = 0;
		this.positionDescription = null;
	}

	public String getEmpId() {

		return empId;
	}

	public void setEmpId(String newEmpId) {

		empId = newEmpId;
	}

	public String getSIN() {

		return SIN;
	}

	public void setSIN(String newSIN) {

		SIN = newSIN;
	}

	public String getLastName() {

		return lastName;
	}

	public void setLastName(String newLastName) {

		lastName = newLastName;
	}

	public String getFirstName() {

		return firstName;
	}

	public void setFirstName(String newFirstName) {

		firstName = newFirstName;
	}

	public String getPreviousName() {

		return previousName;
	}

	public void setPreviousName(String newPreviousName) {

		previousName = newPreviousName;
	}

	public String getTitle() {

		return title;
	}

	public void setTitle(String newTitle) {

		title = newTitle;
	}

	public String getGender() {

		return gender;
	}

	public void setGender(String newGender) {

		gender = newGender;
	}

	public String getAddress1() {

		return address1;
	}

	public void setAddress1(String newAddress1) {

		address1 = newAddress1;
	}

	public String getAddress2() {

		return address2;
	}

	public void setAddress2(String newAddress2) {

		address2 = newAddress2;
	}

	public String getCity() {

		return city;
	}

	public void setCity(String newCity) {

		city = newCity;
	}

	public String getPostalCode() {

		return postalCode;
	}

	public void setPostalCode(String newPostalCode) {

		postalCode = newPostalCode;
	}

	public String getProvince() {

		return province;
	}

	public void setProvince(String newProvince) {

		province = newProvince;
	}

	public String getPhone() {

		return phone;
	}

	public void setPhone(String newPhone) {

		phone = newPhone;
	}

	public String getAlternatePhone() {

		return alternatePhone;
	}

	public void setAlternatePhone(String newAlternatePhone) {

		alternatePhone = newAlternatePhone;
	}

	public String getEmail() {

		return email;
	}

	public void setEmail(String newEmail) {

		email = newEmail;
	}

	public String getStatus() {

		return status;
	}

	public void setStatus(String newStatus) {

		status = newStatus;
	}

	public Date getSeniorityDate() {

		return seniorityDate;
	}

	public void setSeniorityDate(Date newSeniorityDate) {

		seniorityDate = newSeniorityDate;
	}

	public Date getBoardStartDate() {

		return boardStartDate;
	}

	public void setBoardStartDate(Date newBoardStartDate) {

		boardStartDate = newBoardStartDate;
	}

	public String getSchoolYear() {

		return schoolYear;
	}

	public void setSchoolYear(String newSchoolYear) {

		schoolYear = newSchoolYear;
	}

	public PositionConstant getPosition() {

		return position;
	}

	public void setPosition(PositionConstant newPosition) {

		position = newPosition;
	}

	public String getTenur() {

		return tenur;
	}

	public void setTenur(String tenur) {

		this.tenur = tenur;
	}

	public int getTenurCode() {

		return tenur_code;
	}

	public void setTenurCode(int newTenurCode) {

		this.tenur_code = newTenurCode;
	}

	public String getLocationPreferences() {

		return this.location_prefs;
	}

	public void setLocationPreferences(String location_prefs) {

		this.location_prefs = location_prefs;
	}

	public String getFullnameReverse() {

		return this.lastName + ((this.previousName != null) ? " (" + this.previousName + ")" : "") + ", " + this.firstName;
	}

	public EmployeeAvailabilityBean getCurrentAvailability() {

		return this.current_availability;
	}

	public void setCurrentAvailability(EmployeeAvailabilityBean current_availability) {

		this.current_availability = current_availability;
	}

	public EmployeeSeniorityBean getSeniority(EmployeeSeniorityBean.Union union) {

		return seniority.get(union);
	}

	public EmployeeSeniorityBean getSeniority() {

		if (seniority.size() < 1)
			return null;

		return this.seniority.values().stream().filter(
				s -> !s.getUnion().equals(EmployeeSeniorityBean.Union.NLTA)).findFirst().orElse(null);
	}

	public void addSeniority(EmployeeSeniorityBean seniority) {

		if (seniority != null) {
			this.seniority.put(seniority.getUnion(), seniority);
		}
	}

	public double getFTE() {

		return FTE;
	}

	public void setFTE(double fTE) {

		FTE = fTE;
	}

	public String getPositionDescription() {

		return positionDescription;
	}

	public void setPositionDescription(String positionDescription) {

		this.positionDescription = positionDescription;
	}

	public String toXML() {

		DecimalFormat df = new DecimalFormat("0.00");
		StringBuffer buf = new StringBuffer();

		buf.append("<EMPLOYEE-BEAN employee-id=\"" + this.empId.trim() + "\" first-name=\""
				+ StringUtils.encodeHTML2(this.firstName) + "\" last-name=\"" + StringUtils.encodeHTML2(this.lastName) + "\""
				+ ((this.previousName != null) ? (" previous-name=\"" + StringUtils.encodeHTML2(this.previousName) + "\"") : "")
				+ " fte=\"" + df.format(this.FTE) + "\" tenure=\"" + this.tenur + "\" position-description=\""
				+ StringUtils.encodeHTML2(this.getPositionDescription()) + "\" />");

		return buf.toString();
	}

}