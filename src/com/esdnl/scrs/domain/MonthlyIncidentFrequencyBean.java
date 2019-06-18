package com.esdnl.scrs.domain;

import java.util.Date;

public class MonthlyIncidentFrequencyBean {

	private Date month;
	private int count;

	public MonthlyIncidentFrequencyBean(Date month, int count) {

		super();
		this.month = month;
		this.count = count;
	}

	public Date getMonth() {

		return month;
	}

	public int getCount() {

		return count;
	}

}
