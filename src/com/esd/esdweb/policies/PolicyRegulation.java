package com.esd.esdweb.policies;

import java.util.Date;

public class PolicyRegulation {

	private String cat_code;
	private String pol_code;
	private String reg_code;
	private String title;
	private Date upload_date;

	public PolicyRegulation(String cat_code, String pol_code, String reg_code, String title) {

		this.cat_code = cat_code;
		this.pol_code = pol_code;
		this.reg_code = reg_code;
		this.title = title;
		upload_date = null;
	}

	public PolicyRegulation(String cat_code, String pol_code, String reg_code, String title, Date upload_date) {

		this.cat_code = cat_code;
		this.pol_code = pol_code;
		this.reg_code = reg_code;
		this.title = title;
		this.upload_date = upload_date;
	}

	public String getCategoryCode() {

		return cat_code;
	}

	public String getPolicyCode() {

		return pol_code;
	}

	public String getRegulationCode() {

		return reg_code;
	}

	public String getTitle() {

		return title;
	}

	public Date getUploadDate() {

		return upload_date;
	}

	public String toString() {

		return cat_code + "-" + pol_code + "-" + reg_code + ": " + title;
	}

}
