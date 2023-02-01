package com.nlesd.icfreg.bean;

import java.io.Serializable;

public class IcfSchoolBean  implements Serializable {
	private static final long serialVersionUID = -844016122427000813L;
	private int icfSchId;
	private int icfSchCap;
	private int icfRegPerId;
	private String icfSchSchool;
	private int icfSchSchoolId;
	private int icfSchCount;
	public int getIcfSchId() {
		return icfSchId;
	}
	public void setIcfSchId(int icfSchId) {
		this.icfSchId = icfSchId;
	}
	public int getIcfSchCap() {
		return icfSchCap;
	}
	public void setIcfSchCap(int icfSchCap) {
		this.icfSchCap = icfSchCap;
	}
	public int getIcfRegPerId() {
		return icfRegPerId;
	}
	public void setIcfRegPerId(int icfRegPerId) {
		this.icfRegPerId = icfRegPerId;
	}
	public String getIcfSchSchool() {
		return icfSchSchool;
	}
	public void setIcfSchSchool(String icfSchSchool) {
		this.icfSchSchool = icfSchSchool;
	}
	public int getIcfSchSchoolId() {
		return icfSchSchoolId;
	}
	public void setIcfSchSchoolId(int icfSchSchoolId) {
		this.icfSchSchoolId = icfSchSchoolId;
	}
	public String toXml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<SCHID>").append(this.icfSchId).append("</SCHID>");
		sb.append("<SCHCAP>").append(this.icfSchCap).append("</SCHCAP>");
		sb.append("<REGPER>").append(this.icfRegPerId).append("</REGPER>");
		sb.append("<SCHSCHOOL>").append(this.icfSchSchool).append("</SCHSCHOOL>");
		sb.append("<SCHSCHOOLID>").append(this.icfSchSchoolId).append("</SCHSCHOOLID>");
		sb.append("<SCHCOUNT>").append(this.icfSchCount).append("</SCHCOUNT>");
		return sb.toString();
	}
	public int getIcfSchCount() {
		return icfSchCount;
	}
	public void setIcfSchCount(int icfSchCount) {
		this.icfSchCount = icfSchCount;
	}

}
