package com.nlesd.antibullyingpledge.bean;

import java.io.Serializable;

public class AntiBullyingPledgeGradeLevelReport implements Serializable{
	private static final long serialVersionUID = 7479014654525485539L;
	private int gradeId;
	private String gradeName;
	private int notConfirmedCount;
	private int confirmedCount;
	private int totalCount;
	public int getGradeId() {
		return gradeId;
	}
	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public int getNotConfirmedCount() {
		return notConfirmedCount;
	}
	public void setNotConfirmedCount(int notConfirmedCount) {
		this.notConfirmedCount = notConfirmedCount;
	}
	public int getConfirmedCount() {
		return confirmedCount;
	}
	public void setConfirmedCount(int confirmedCount) {
		this.confirmedCount = confirmedCount;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}


}
