package com.esdnl.personnel.jobs.bean;

import java.io.Serializable;

public class ApplicantSupervisorBean implements Serializable {

	private static final long serialVersionUID = -8771605504141292563L;
	private int id;
	private String sin;
	private String name;
	private String title;
	private String address;
	private String telephone;
	private ApplicantRefRequestBean aBean;

	public ApplicantSupervisorBean() {

		this.id = -1;
		this.sin = null;
		this.name = null;
		this.title = null;
		this.address = null;
		this.telephone = null;
		this.aBean=null;
	}

	public int getId() {

		return this.id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getSIN() {

		return this.sin;
	}

	public void setSIN(String sin) {

		this.sin = sin;
	}

	public String getName() {

		return this.name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getTitle() {

		return this.title;
	}

	public void setTitle(String title) {

		this.title = title;
	}

	public String getAddress() {

		return this.address;
	}

	public void setAddress(String address) {

		this.address = address;
	}

	public String getTelephone() {

		return this.telephone;
	}

	public void setTelephone(String telephone) {

		this.telephone = telephone;
	}
	public String getSin() {
		return sin;
	}

	public void setSin(String sin) {
		this.sin = sin;
	}

	public ApplicantRefRequestBean getApplicantRefRequestBean() {
		return aBean;
	}

	public void setApplicantRefRequestBeanBean(ApplicantRefRequestBean aBean) {
		this.aBean = aBean;
	}
}