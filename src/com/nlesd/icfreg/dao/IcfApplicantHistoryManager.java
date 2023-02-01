package com.nlesd.icfreg.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import com.esdnl.dao.DAOUtils;
import com.nlesd.icfreg.bean.IcfApplicantHistoryBean;
import com.nlesd.icfreg.bean.IcfRegApplicantBean;
import com.nlesd.icfreg.constants.IcfApplicantHistoryTypesConstant;
import com.nlesd.icfreg.constants.IcfRegistrationStatusConstant;

import oracle.jdbc.OracleTypes;

public class IcfApplicantHistoryManager {
	public static int addIcfRegistrationPeriodBean(IcfApplicantHistoryBean atbean) {
		Connection con = null;
		CallableStatement stat = null;
		int test=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			
			stat = con.prepareCall("begin ? :=awsd_user.icf_reg_pkg.add_new_icf_app_his(?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2,atbean.getIcfAppId());
			stat.setInt(3,atbean.getIcfAppHisType());
			stat.setString(4,atbean.getIcfAppHisBy());
			stat.setString(5,atbean.getIcfAppHisNotes());
			stat.execute();
			test = stat.getInt(1);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static int addIcfRegistrationPeriodBean(IcfApplicantHistoryBean atbean) : "
					+ e);
		}
		finally {
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return test;
	}
	public static void updateAuditTrailUpdate(String user,IcfRegApplicantBean obean,IcfRegApplicantBean nbean, IcfApplicantHistoryTypesConstant htype) {
		//check each field and build string of changes
		StringBuilder changes = new StringBuilder();
		if(!obean.getIcfAppEmail().equals(nbean.getIcfAppEmail())) {
			changes.append("Email updated from ").append(obean.getIcfAppEmail()).append(" to ").append(nbean.getIcfAppEmail()).append("\n");
		}
		if(!obean.getIcfAppFullName().equals(nbean.getIcfAppFullName())) {
			changes.append("Full Name updated from ").append(obean.getIcfAppFullName()).append(" to ").append(nbean.getIcfAppFullName()).append("\n");
		}
		if(!obean.getIcfAppGuaFullName().equals(nbean.getIcfAppGuaFullName())) {
			changes.append("Guardian Full Name updated from ").append(obean.getIcfAppGuaFullName()).append(" to ").append(nbean.getIcfAppGuaFullName()).append("\n");
		}
		if(!obean.getIcfAppContact1().equals(nbean.getIcfAppContact1())) {
			changes.append("Contact 1 updated from ").append(obean.getIcfAppContact1()).append(" to ").append(nbean.getIcfAppContact1()).append("\n");
		}
		if(obean.getIcfAppContact2() != null) {
			if(nbean.getIcfAppContact2() == null) {
				changes.append("Contact 2 updated from ").append(obean.getIcfAppContact2()).append(" to ").append("").append("\n");
			}else {
				if(!obean.getIcfAppContact2().equals(nbean.getIcfAppContact2())) {
					changes.append("Contact 2 updated from ").append(obean.getIcfAppContact2()).append(" to ").append(nbean.getIcfAppContact2()).append("\n");
				}
			}
		}else {
			if(nbean.getIcfAppContact2() != null) {
				changes.append("Contact 2 updated from ").append("").append(" to ").append(nbean.getIcfAppContact2()).append("\n");
			}
		}
		
		if(!(obean.getIcfAppSchool() == nbean.getIcfAppSchool())) {
			changes.append("School updated from").append(obean.getIcfAppSchool()).append(" to ").append(nbean.getIcfAppSchool()).append("\n");
		}
		
		//now we add the History Bean
		IcfApplicantHistoryBean hbean = new IcfApplicantHistoryBean();
		hbean.setIcfAppHisBy(user);
		hbean.setIcfAppHisNotes(changes.toString());
		hbean.setIcfAppHisType(htype.getValue());
		hbean.setIcfAppId(obean.getIcfAppId());
		
		IcfApplicantHistoryManager.addIcfRegistrationPeriodBean(hbean);
		
	}
	
	public static void updateAuditTrailStatus(String user,IcfRegApplicantBean obean,int newstatus) {
		//now we add the History Bean
				IcfApplicantHistoryBean hbean = new IcfApplicantHistoryBean();
				hbean.setIcfAppHisBy(user);
				String notes = "Status changed from " + IcfRegistrationStatusConstant.get(obean.getIcfAppStatus()).getDescription() + " to " 
				+ IcfRegistrationStatusConstant.get(newstatus).getDescription();
				
				hbean.setIcfAppHisNotes(notes);
				if(newstatus == 2) {
					hbean.setIcfAppHisType(IcfApplicantHistoryTypesConstant.APPROVED.getValue());
				}else if(newstatus == 3) {
					hbean.setIcfAppHisType(IcfApplicantHistoryTypesConstant.NOTAPPROVED.getValue());
				}else if(newstatus ==4) {
					hbean.setIcfAppHisType(IcfApplicantHistoryTypesConstant.WAITLISTED.getValue());
				}
				hbean.setIcfAppId(obean.getIcfAppId());
				
				IcfApplicantHistoryManager.addIcfRegistrationPeriodBean(hbean);
	}
	public static void updateAuditTrailResendEmail(int rid, String user,String emaila,IcfApplicantHistoryTypesConstant htype) {
		//now we add the History Bean
				IcfApplicantHistoryBean hbean = new IcfApplicantHistoryBean();
				hbean.setIcfAppHisBy(user);
				String notes =htype.getDescription() + " resent to " + emaila + " by " + user;
				hbean.setIcfAppHisNotes(notes);
				hbean.setIcfAppHisType(htype.getValue());
				hbean.setIcfAppId(rid);
				
				IcfApplicantHistoryManager.addIcfRegistrationPeriodBean(hbean);
	}
	public static void updateAuditTrailDeleteRegistant(int rid, String user,IcfApplicantHistoryTypesConstant htype) {
		//now we add the History Bean
				IcfApplicantHistoryBean hbean = new IcfApplicantHistoryBean();
				hbean.setIcfAppHisBy(user);
				String notes = "Registrant removed by " + user;
				hbean.setIcfAppHisNotes(notes);
				hbean.setIcfAppHisType(htype.getValue());
				hbean.setIcfAppId(rid);
				
				IcfApplicantHistoryManager.addIcfRegistrationPeriodBean(hbean);
	}
	public static void updateAuditTrailAddNewRegistant(int rid, String user,IcfApplicantHistoryTypesConstant htype) {
		//now we add the History Bean
				IcfApplicantHistoryBean hbean = new IcfApplicantHistoryBean();
				hbean.setIcfAppHisBy(user);
				String notes = "Registrant added by " + user;
				hbean.setIcfAppHisNotes(notes);
				hbean.setIcfAppHisType(htype.getValue());
				hbean.setIcfAppId(rid);
				
				IcfApplicantHistoryManager.addIcfRegistrationPeriodBean(hbean);
	}
}
