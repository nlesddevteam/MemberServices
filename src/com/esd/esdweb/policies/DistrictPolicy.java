package com.esd.esdweb.policies;

import java.util.Date;

public class DistrictPolicy {

	private String cat_code;
	private String code;
	private String title;
	private Date upload_date;

	public DistrictPolicy(String cat_code, String code, String title, Date upload_date) {

		this.cat_code = cat_code;
		this.code = code;
		this.title = title;
		this.upload_date = upload_date;
	}

	public String getCategoryCode() {

		return cat_code;
	}

	public String getCode() {

		return code;
	}

	public String getTitle() {

		return title;
	}

	public Date getUploadDate() {

		return upload_date;
	}

	public String toString() {

		return cat_code + "-" + code + ": " + title;
	}
}
