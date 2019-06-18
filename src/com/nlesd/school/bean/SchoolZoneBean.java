package com.nlesd.school.bean;

import java.io.Serializable;
import java.util.Collection;

import com.awsd.school.School;

public class SchoolZoneBean implements Serializable, Comparable<SchoolZoneBean> {

	private static final long serialVersionUID = -844016122427000813L;

	private int zoneId;
	private String zoneName;
	private String address1;
	private String address2;
	private String townCity;
	private String province;
	private String postalCode;
	private String telephone;
	private String fax;

	private Collection<School> schools;

	public SchoolZoneBean() {

		this(0, "INVALID ZONE");
	}

	public SchoolZoneBean(int zoneId, String zoneName) {

		this.zoneId = zoneId;
		this.zoneName = zoneName;

		this.schools = null;
	}

	public int getZoneId() {

		return zoneId;
	}

	public void setZoneId(int zoneId) {

		this.zoneId = zoneId;
	}

	public String getZoneName() {

		return zoneName.toLowerCase();
	}

	public void setZoneName(String zoneName) {

		this.zoneName = zoneName;
	}

	public String getAddress1() {

		return address1;
	}

	public void setAddress1(String address1) {

		this.address1 = address1;
	}

	public String getAddress2() {

		return address2;
	}

	public void setAddress2(String address2) {

		this.address2 = address2;
	}

	public String getTownCity() {

		return townCity;
	}

	public void setTownCity(String townCity) {

		this.townCity = townCity;
	}

	public String getProvince() {

		return province;
	}

	public void setProvince(String province) {

		this.province = province;
	}

	public String getPostalCode() {

		return postalCode;
	}

	public void setPostalCode(String postalCode) {

		this.postalCode = postalCode;
	}

	public String getTelephone() {

		return telephone;
	}

	public void setTelephone(String telephone) {

		this.telephone = telephone;
	}

	public String getFax() {

		return fax;
	}

	public void setFax(String fax) {

		this.fax = fax;
	}

	public Collection<School> getSchools() {

		return schools;
	}

	public void setSchools(Collection<School> schools) {

		this.schools = schools;
	}

	public String toString() {

		return this.getZoneName();
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof SchoolZoneBean) {
			return this.zoneId == ((SchoolZoneBean) obj).getZoneId();
		}
		else {
			return false;
		}
	}

	public int compareTo(SchoolZoneBean o) {

		if (o == null)
			return 0;
		else
			return this.getZoneName().compareTo(o.getZoneName());
	}

	@Override
	public int hashCode() {

		final int prime = 31;

		int result = 1;

		result = prime * result + zoneId;

		return result;
	}

	public String toXML() {

		return "<SCHOOL-ZONE zone-id='" + this.zoneId + "' zone-name='" + this.zoneName + "' />";
	}

}
