package com.esdnl.personnel.v2.model.sds.bean;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

public class EmployeePositionBean {

	private static SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");

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

	public enum TenureType {

		UNKNOWN(false), CASU(false), TEMP(false), TERM(false), TENP1(true), TENP2(true), TENP3(true), TENTM(true), PR1TM(
				true), PR2TM(true), PR3TM(true), PROB1(true), PROB2(true), PROB3(true), PERM(true), TENUR(true);

		private boolean permType;

		private TenureType(boolean permType) {

			this.permType = permType;
		}

		public boolean isPermType() {

			return this.permType;
		}

		public static TenureType get(String tenure) {

			TenureType tmp = UNKNOWN;

			for (TenureType tt : values()) {
				if (tt.name().equalsIgnoreCase(tenure)) {
					tmp = tt;
					break;
				}
			}

			return tmp;
		}

		public static List<TenureType> getPermList() {

			return Arrays.stream(values()).filter(t -> t.isPermType()).collect(Collectors.toList());
		}

		public static List<TenureType> getTermList() {

			return Arrays.stream(TenureType.values()).filter(t -> !t.isPermType()).collect(Collectors.toList());
		}

		public static boolean isPerm(TenureType tt) {

			return getPermList().contains(tt);
		}

		public static boolean isTerm(TenureType tt) {

			return getTermList().contains(tt);
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
	private TenureType tenure;
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
		this.tenure = TenureType.UNKNOWN;
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

	public boolean isSubstitute() {

		return StringUtils.isNotBlank(this.position) && StringUtils.contains(this.position.toLowerCase(), "substitute");
	}

	public PositionType getPositionType() {

		return positionType;
	}

	public void setPositionType(PositionType positionType) {

		this.positionType = positionType;
	}

	public boolean isLeave() {

		return EmployeePositionBean.PositionType.LEAVE.equals(getPositionType());
	}

	public boolean isReplacement() {

		return EmployeePositionBean.PositionType.REPLACEMENT.equals(getPositionType());
	}

	public boolean isRegular() {

		return EmployeePositionBean.PositionType.REPLACEMENT.equals(getPositionType());
	}

	public Date getStartDate() {

		return startDate;
	}

	public String getStartDateFormatted() {

		return this.startDate != null ? sdf.format(this.startDate) : "UNKNOWN";
	}

	public void setStartDate(Date startDate) {

		this.startDate = startDate;
	}

	public Date getEndDate() {

		return endDate;
	}

	public String getEndDateFormatted() {

		return this.endDate != null ? sdf.format(this.endDate) : "UNKNOWN";
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

	public TenureType getTenure() {

		return tenure;
	}

	public void setTenure(TenureType tenure) {

		this.tenure = tenure;
	}

	public boolean isPerm() {

		return TenureType.isPerm(this.tenure);
	}

	public boolean isTerm() {

		return TenureType.isTerm(this.tenure);
	}

	public double getFteHours() {

		return fteHours;
	}

	public void setFteHours(double fteHours) {

		this.fteHours = fteHours;
	}

	public boolean isError() {

		return (this.startDate == null) || (this.endDate == null)
				|| (this.endDate != null && this.startDate != null && this.endDate.before(this.startDate))
				|| (this.startDate != null
						&& !com.esdnl.personnel.v2.utils.StringUtils.getSchoolYear(this.startDate).equals(this.schoolYear));
	}

	@Override
	public String toString() {

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String tmp = schoolYear + ": " + location + " - " + position + " " + fteHours + (fteHours <= 1 ? " FTE " : " Hrs ")
				+ "(" + tenure.name() + ")";

		if (this.startDate != null) {
			tmp += " started " + sdf.format(this.startDate);
		}

		if (this.endDate != null) {
			tmp += " ending " + sdf.format(this.endDate);
		}

		tmp += " (" + (isLeave() ? "<b>" : "") + this.getPositionType() + (isLeave() ? "</b>" : "") + ")";

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
