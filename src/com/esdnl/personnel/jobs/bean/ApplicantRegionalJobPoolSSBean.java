package com.esdnl.personnel.jobs.bean;

import java.io.Serializable;
public class ApplicantRegionalJobPoolSSBean  implements Serializable {

	private static final long serialVersionUID = 7479014654525485539L;
	private String sin;
	private int poolJob;
	public String getSin() {
		return sin;
	}
	public void setSin(String sin) {
		this.sin = sin;
	}
	public int getPoolJob() {
		return poolJob;
	}
	public void setPoolJob(int poolJob) {
		this.poolJob = poolJob;
	}
}