package com.awsd.ppgp;

import java.io.Serializable;

public class TaskDomainBean implements Serializable {

	private static final long serialVersionUID = -7993459562584834620L;

	private int domain_id;
	private String domain_title;

	public TaskDomainBean(int domain_id, String domain_title) {

		this.domain_id = domain_id;
		this.domain_title = domain_title;
	}

	public TaskDomainBean() {

		this(-1, "UNKNOWN");
	}

	public int getDomainID() {

		return this.domain_id;
	}

	public void setDomainID(int domain_id) {

		this.domain_id = domain_id;
	}

	public String getDomainTitle() {

		return this.domain_title;
	}

	public void setDomainTitle(String domain_title) {

		this.domain_title = domain_title;
	}

	public boolean equals(Object obj) {

		if (obj == null)
			return false;
		else if (!(obj instanceof TaskDomainBean))
			return false;
		else if (((TaskDomainBean) obj).getDomainID() != this.domain_id)
			return false;
		else
			return true;
	}
}
