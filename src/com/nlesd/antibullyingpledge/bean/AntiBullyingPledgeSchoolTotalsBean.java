package com.nlesd.antibullyingpledge.bean;

import java.io.Serializable;
import java.util.List;

import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.nlesd.antibullyingpledge.dao.AntiBullyingPledgeManager;

public class AntiBullyingPledgeSchoolTotalsBean implements Serializable{
	private static final long serialVersionUID = 7479014654525485539L;
	private int totalPledges;
	private String schoolName;
	private int schoolId;
	private String schoolPicture;
	private int totalPledgesConfirmed;
	public int getTotalPledges() {
		return totalPledges;
	}
	public void setTotalPledges(int totalPledges) {
		this.totalPledges = totalPledges;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public int getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
	public String getSchoolPicture() {
		return Integer.toString(schoolId);
		
	}
	public void setSchoolPicture(String schoolPicture) {
		this.schoolPicture = schoolPicture;
	}
	public List<AntiBullyingPledgeSchoolTotalsBean> getRandomSchoolTotals(int tnumber)
	{
		List<AntiBullyingPledgeSchoolTotalsBean> listbeans = null;
		try {
			listbeans = AntiBullyingPledgeManager.getRandomSchoolTotals(tnumber);
		} catch (JobOpportunityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listbeans;
	}
	public List<AntiBullyingPledgeSchoolTotalsBean> getAllSchoolTotals(int tnumber)
	{
		List<AntiBullyingPledgeSchoolTotalsBean> listbeans = null;
		try {
			listbeans = AntiBullyingPledgeManager.getAllSchoolTotals();
			
		} catch (JobOpportunityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listbeans;
	}
	public int getTotalPledgesConfirmed() {
		return totalPledgesConfirmed;
	}
	public void setTotalPledgesConfirmed(int totalPledgesConfirmed) {
		this.totalPledgesConfirmed = totalPledgesConfirmed;
	}	
}
