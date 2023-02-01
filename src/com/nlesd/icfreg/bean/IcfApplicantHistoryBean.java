package com.nlesd.icfreg.bean;

import java.io.Serializable;
import java.util.Date;

public class IcfApplicantHistoryBean  implements Serializable {
	private static final long serialVersionUID = -844016122427000813L;
	private int icfAppHisId;
	private int icfAppId;
	private int icfAppHisType;
	private Date icfAppHisDate;
	private String icfAppHisBy;
	private String icfAppHisNotes;
	public int getIcfAppHisId() {
		return icfAppHisId;
	}
	public void setIcfAppHisId(int icfAppHisId) {
		this.icfAppHisId = icfAppHisId;
	}
	public int getIcfAppId() {
		return icfAppId;
	}
	public void setIcfAppId(int icfAppId) {
		this.icfAppId = icfAppId;
	}
	public int getIcfAppHisType() {
		return icfAppHisType;
	}
	public void setIcfAppHisType(int icfAppHisType) {
		this.icfAppHisType = icfAppHisType;
	}
	public Date getIcfAppHisDate() {
		return icfAppHisDate;
	}
	public void setIcfAppHisDate(Date icfAppHisDate) {
		this.icfAppHisDate = icfAppHisDate;
	}
	public String getIcfAppHisBy() {
		return icfAppHisBy;
	}
	public void setIcfAppHisBy(String icfAppHisBy) {
		this.icfAppHisBy = icfAppHisBy;
	}
	public String getIcfAppHisNotes() {
		return icfAppHisNotes;
	}
	public void setIcfAppHisNotes(String icfAppHisNotes) {
		this.icfAppHisNotes = icfAppHisNotes;
	}
}
