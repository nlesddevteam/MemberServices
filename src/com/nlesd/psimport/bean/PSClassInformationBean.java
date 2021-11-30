package com.nlesd.psimport.bean;

import java.util.TreeMap;

public class PSClassInformationBean {
	private TreeMap<String,PSHSClassBean> hClass = new TreeMap<>();
	private TreeMap<String,PSK9ClassBean> kClass = new TreeMap<>();
	private PSStudentCountsBean scBean = new PSStudentCountsBean();
	public TreeMap<String, PSHSClassBean> gethClass() {
		return hClass;
	}
	public void sethClass(TreeMap<String, PSHSClassBean> hClass) {
		this.hClass = hClass;
	}
	public TreeMap<String, PSK9ClassBean> getkClass() {
		return kClass;
	}
	public void setkClass(TreeMap<String, PSK9ClassBean> kClass) {
		this.kClass = kClass;
	}
	public PSStudentCountsBean getScBean() {
		return scBean;
	}
	public void setScBean(PSStudentCountsBean scBean) {
		this.scBean = scBean;
	}
}
