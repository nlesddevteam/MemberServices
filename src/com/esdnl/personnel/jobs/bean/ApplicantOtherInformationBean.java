package com.esdnl.personnel.jobs.bean;

import java.io.Serializable;

public class ApplicantOtherInformationBean implements Serializable {

	private static final long serialVersionUID = 7479014654525485539L;
	private String sin;
	private String other_info;

	public ApplicantOtherInformationBean() {

		this.sin = null;
		this.other_info = null;
	}

	public String getSIN() {

		return this.sin;
	}

	public void setSIN(String sin) {

		this.sin = sin;
	}

	public String getOtherInformation() {

		return this.other_info;
	}

	public void setOtherInformation(String other_info) {

		this.other_info = other_info;
	}

	public String toString() {

		return this.getOtherInformation().replaceAll(new String(new char[] {
			((char) 10)
		}), "<br>");
	}
}