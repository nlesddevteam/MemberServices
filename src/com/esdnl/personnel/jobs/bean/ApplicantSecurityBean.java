package com.esdnl.personnel.jobs.bean;
import java.io.Serializable;
public class ApplicantSecurityBean implements Serializable {
	private static final long serialVersionUID = 7479014654525485539L;
	private String sin;
	private String security_question;
	private String security_answer;
	private int pk_id;
	public ApplicantSecurityBean() {
		this.setSin(null);
		this.setSecurity_question(null);
		this.setSecurity_answer(null);
		this.setPk_id(-1);
	}
	public void setSin(String sin) {
		this.sin = sin;
	}
	public String getSin() {
		return sin;
	}
	public void setSecurity_question(String security_question) {
		this.security_question = security_question;
	}
	public String getSecurity_question() {
		return security_question;
	}
	public void setSecurity_answer(String security_answer) {
		this.security_answer = security_answer;
	}
	public String getSecurity_answer() {
		return security_answer;
	}
	public void setPk_id(int pk_id) {
		this.pk_id = pk_id;
	}
	public int getPk_id() {
		return pk_id;
	}
}
