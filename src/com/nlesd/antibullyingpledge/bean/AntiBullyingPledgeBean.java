package com.nlesd.antibullyingpledge.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.nlesd.antibullyingpledge.dao.AntiBullyingPledgeManager;

public class AntiBullyingPledgeBean implements Serializable{
	private static final long serialVersionUID = 7479014654525485539L;
	private int pk;
	private String firstName;
	private String lastName;
	private int fkSchool;
	private int gradeLevel;
	private Date dateSubmittedUser;
	private String cancellationCode;
	private String email;
	private String confirmEmail;
	private String schoolImage;
	private String schoolName;
	public static final String DATE_FORMAT_STRING = "MMMM dd, yyyy";
	private String confirmed;
	public AntiBullyingPledgeBean()
	{
		this.setFirstName("");
		this.setLastName("");
		this.setEmail("");
		this.setConfirmEmail("");
	
	}
	public List<AntiBullyingPledgeSchoolListBean> getSchoolListings()
	{
		List<AntiBullyingPledgeSchoolListBean> listbeans = null;
		try {
			listbeans = AntiBullyingPledgeManager.getSchoolListings();
		} catch (JobOpportunityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listbeans;
	}
	public int getPk() {
		return pk;
	}
	public void setPk(int pk) {
		this.pk = pk;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getFkSchool() {
		return fkSchool;
	}
	public void setFkSchool(int fkSchool) {
		this.fkSchool = fkSchool;
	}
	public int getGradeLevel() {
		return gradeLevel;
	}
	public void setGradeLevel(int gradeLevel) {
		this.gradeLevel = gradeLevel;
	}
	public Date getDateSubmittedUser() {
		return dateSubmittedUser;
	}
	public void setDateSubmittedUser(Date dateSubmittedUser) {
		this.dateSubmittedUser = dateSubmittedUser;
	}
	public String getDate_Submitted_User_Formatted() {
		if (this.getDateSubmittedUser() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat(AntiBullyingPledgeBean.DATE_FORMAT_STRING);
				return sdf.format(this.getDateSubmittedUser());
			}
			else
				return "INDEFINITE";
		}
	
	public String getCancellation_Code() {
		return cancellationCode;
	}
	public void setCancellation_Code(String cancellationCode) {
		this.cancellationCode = cancellationCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getConfirmEmail() {
		return confirmEmail;
	}
	public void setConfirmEmail(String confirmEmail) {
		this.confirmEmail = confirmEmail;
	}
	public void setSchoolImage(String schoolImage) {
		this.schoolImage = schoolImage;
	}
	public String getSchoolImage() {
		return schoolImage;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getSchoolName() {
		return this.schoolName;
	}
	public void setConfirmed(String confirmed) {
		this.confirmed = confirmed;
	}
	public String getConfirmed() {
		return confirmed;
	}
}
