package com.awsd.school.bean;

import java.io.Serializable;

import com.nlesd.school.bean.SchoolZoneBean;

public class RegionBean implements Comparable<RegionBean>, Serializable {

	private static final long serialVersionUID = -3769518533352943275L;

	private int id;
	private String name;
	private SchoolZoneBean zone;

	public RegionBean() {

		this.id = -1;
		this.name = "UNKNOWN";
	}

	public RegionBean(int id, String name) {

		this.id = id;
		this.name = name;
	}

	public void setId(int id) {

		this.id = id;
	}

	public int getId() {

		return this.id;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getName() {

		return name.toLowerCase();
	}

	public SchoolZoneBean getZone() {

		return zone;
	}

	public void setZone(SchoolZoneBean zone) {

		this.zone = zone;
	}

	public String toString() {

		return this.name;
	}

	public String toXML() {

		StringBuffer xml = new StringBuffer();

		xml.append("<REGION regionid='" + this.getId() + "' regionname='" + this.getName() + "' />");

		return xml.toString();
	}

	public boolean equals(Object o) {

		boolean check = true;
		if (o == null)
			check = false;
		else if (!(o instanceof RegionBean))
			check = false;
		else if (((RegionBean) o).getId() != this.id)
			check = false;

		return check;
	}

	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	public int compareTo(RegionBean o) {

		if (o == null)
			return 0;
		else
			return this.getName().compareTo(o.getName());
	}
}