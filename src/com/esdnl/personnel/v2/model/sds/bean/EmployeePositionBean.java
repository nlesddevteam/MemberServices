package com.esdnl.personnel.v2.model.sds.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class EmployeePositionBean {

	public enum PositionType {

		UNKNOWN, LEAVE, REGULAR, REPLACEMENT;

		public static PositionType get(String pt) {

			PositionType tmp = UNKNOWN;

			for (PositionType tpt : values()) {
				if (StringUtils.equalsIgnoreCase(tpt.name(), pt)) {
					tmp = tpt;
					break;
				}
			}

			return tmp;
		}
	}

	private EmployeeBean employee;
	private String schoolYear;
	private String name;
	private String empId;
	private String position;
	private PositionType positionType;
	private Date startDate;
	private Date endDate;
	private String sin;
	private String location;
	private String tenure;
	private double fteHours;

	public EmployeePositionBean() {

		this.employee = null;
		this.schoolYear = "";
		this.name = "";
		this.empId = "";
		this.position = "";
		this.positionType = PositionType.UNKNOWN;
		this.startDate = null;
		this.endDate = null;
		this.sin = "";
		this.location = "";
		this.tenure = "";
		this.fteHours = 0.0;
	}

	public EmployeeBean getEmployee() {

		return employee;
	}

	public void setEmployee(EmployeeBean employee) {

		this.employee = employee;
	}

	public String getSchoolYear() {

		return schoolYear;
	}

	public void setSchoolYear(String schoolYear) {

		this.schoolYear = schoolYear;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getEmpId() {

		return empId;
	}

	public void setEmpId(String empId) {

		this.empId = empId;
	}

	public String getPosition() {

		return position;
	}

	public void setPosition(String position) {

		this.position = position;
	}

	public PositionType getPositionType() {

		return positionType;
	}

	public void setPositionType(PositionType positionType) {

		this.positionType = positionType;
	}

	public Date getStartDate() {

		return startDate;
	}

	public void setStartDate(Date startDate) {

		this.startDate = startDate;
	}

	public Date getEndDate() {

		return endDate;
	}

	public void setEndDate(Date endDate) {

		this.endDate = endDate;
	}

	public String getSin() {

		return sin;
	}

	public void setSin(String sin) {

		this.sin = sin;
	}

	public String getLocation() {

		return location;
	}

	public void setLocation(String location) {

		this.location = location;
	}

	public String getTenure() {

		return tenure;
	}

	public void setTenure(String tenure) {

		this.tenure = tenure;
	}

	public double getFteHours() {

		return fteHours;
	}

	public void setFteHours(double fteHours) {

		this.fteHours = fteHours;
	}

	@Override
	public String toString() {

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String tmp = schoolYear + ": " + location + " - " + position + " " + fteHours + (fteHours <= 1 ? " FTE " : " Hrs ")
				+ "(" + tenure + ")";

		if (this.startDate != null) {
			tmp += " started " + sdf.format(this.startDate);
		}

		if (this.endDate != null) {
			tmp += " ending " + sdf.format(this.endDate);
		}

		tmp += " (" + (this.positionType.equals(PositionType.LEAVE) ? "<b>" : "") + this.getPositionType()
				+ (this.positionType.equals(PositionType.LEAVE) ? "</b>" : "") + ")";

		return tmp;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((empId == null) ? 0 : empId.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		long temp;
		temp = Double.doubleToLongBits(fteHours);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((positionType == null) ? 0 : positionType.hashCode());
		result = prime * result + ((schoolYear == null) ? 0 : schoolYear.hashCode());
		result = prime * result + ((sin == null) ? 0 : sin.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((tenure == null) ? 0 : tenure.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeePositionBean other = (EmployeePositionBean) obj;
		if (empId == null) {
			if (other.empId != null)
				return false;
		}
		else if (!empId.equals(other.empId))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		}
		else if (!endDate.equals(other.endDate))
			return false;
		if (Double.doubleToLongBits(fteHours) != Double.doubleToLongBits(other.fteHours))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		}
		else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		}
		else if (!position.equals(other.position))
			return false;
		if (positionType != other.positionType)
			return false;
		if (schoolYear == null) {
			if (other.schoolYear != null)
				return false;
		}
		else if (!schoolYear.equals(other.schoolYear))
			return false;
		if (sin == null) {
			if (other.sin != null)
				return false;
		}
		else if (!sin.equals(other.sin))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		}
		else if (!startDate.equals(other.startDate))
			return false;
		if (tenure == null) {
			if (other.tenure != null)
				return false;
		}
		else if (!tenure.equals(other.tenure))
			return false;
		return true;
	}

}
