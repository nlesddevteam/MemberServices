package com.awsd.ppgp;

import java.io.Serializable;

public class TaskDomainStrengthBean implements Serializable {

	private static final long serialVersionUID = -7993459562584834620L;

	private int domain_id;
	private String strength_title;
	private int strength_id;

	public TaskDomainStrengthBean(int domain_id, String strength_title,int strength_id) {

		this.domain_id = domain_id;
		this.strength_title = strength_title;
		this.strength_id = strength_id;
	}

	public TaskDomainStrengthBean() {

		this(-1, "UNKNOWN",-1);
	}

	public int getDomainID() {

		return this.domain_id;
	}

	public void setDomainID(int domain_id) {

		this.domain_id = domain_id;
	}

	public String getStrengthTitle() {

		return this.strength_title;
	}

	public void setStrengthTitle(String strength_title) {

		this.strength_title = strength_title;
	}
	public int getStrengthID() {

		return this.strength_id;
	}

	public void setStrengthID(int strength_id) {

		this.strength_id = strength_id;
	}
	public boolean equals(Object obj) {

		if (obj == null)
			return false;
		else if (!(obj instanceof TaskDomainStrengthBean))
			return false;
		else if (((TaskDomainStrengthBean) obj).getStrengthID() != this.strength_id)
			return false;
		else
			return true;
	}
}
