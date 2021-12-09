package com.nlesd.psimport.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;
import com.esdnl.dao.DAOUtils;
import com.nlesd.psimport.bean.PSClassInformationBean;
import com.nlesd.psimport.bean.PSHSClassBean;
import com.nlesd.psimport.bean.PSK9ClassBean;
import com.nlesd.psimport.bean.PSStudentCountsBean;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class PSClassManager {
	public static PSClassInformationBean getPSClassInformtion(Integer cid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		PSClassInformationBean ebean = new PSClassInformationBean();
		PSK9ClassBean kbean = null;
		PSHSClassBean hbean = null;
		PSStudentCountsBean scbean = null;
		TreeMap<String,PSK9ClassBean> kbeans = new TreeMap<String,PSK9ClassBean>();
		TreeMap<String,PSHSClassBean> hbeans = new TreeMap<String,PSHSClassBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.ps_import_pkg.get_ps_class_stats(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				if(rs.getInt("KID") > 0) {
					//if(rs.getInt("KGRADE") < 10) {
						if(checkGrades(rs.getString("GRADES"))) {
						String test = rs.getString("KGRADE") + "-" + rs.getString("SECTION_NUMBER");
						if(!kbeans.containsKey(test)) {
							kbean = createPSK9ClassBean(rs);
							kbeans.put(kbean.getGradeSection(), kbean);
						}
					}
				}
				if(rs.getInt("HID") > 0) {
					if(!hbeans.containsKey(rs.getString("HGRADE"))) {
						hbean = createPSHSClassBean(rs);
						hbeans.put(hbean.getGradeLevel(), hbean);
					}
					
				}
			}
			ebean.setkClass(kbeans);
			ebean.sethClass(hbeans);
			//now we get the student counts for the school
			String schoolid ="";
			if(ebean.getkClass() != null && !ebean.getkClass().isEmpty()) {
				schoolid = ebean.getkClass().firstEntry().getValue().getSchoolNumber();
			}else {
				if(!ebean.gethClass().isEmpty()) {
					schoolid = ebean.gethClass().firstEntry().getValue().getSchoolNumber();
				}else {
					//test school or not a known school
					schoolid="0000000";
				}
				
			}
			
			scbean = PSStudentCountsManager.getPSStudentCounts(schoolid);
			ebean.setScBean(scbean);
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("PSClassInformationBean getPSClassInformtion(Integer cid): "
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
		return ebean;
	}
	public static PSK9ClassBean createPSK9ClassBean(ResultSet rs) {
		PSK9ClassBean kbean = null;
		try {
		if(rs.getInt("KID") > 0) {
			kbean = new PSK9ClassBean();
			kbean.setGradeLevel(rs.getString("KGRADE"));
			kbean.setId(rs.getInt("KID"));
			kbean.setNumberOfStudents(rs.getInt("NUMBER_OF_STUDENTS"));
			kbean.setSchoolNumber(rs.getString("KSNUM"));
			kbean.setSectionNumber(rs.getString("SECTION_NUMBER"));
			kbean.setGradeLevels(rs.getString("GRADES"));
		}
		}
		catch (SQLException e) {
				kbean = null;
		}
		return kbean;
	}
	public static PSHSClassBean createPSHSClassBean(ResultSet rs) {
		PSHSClassBean hbean = null;
		try {
			if(rs.getInt("HID") > 0) {
				hbean = new PSHSClassBean();
				hbean.setGradeLevel(rs.getString("HGRADE"));
				hbean.setId(rs.getInt("HID"));
				hbean.setSchoolNumber(rs.getString("HSNUM"));
				hbean.setLessThan15(rs.getInt("LT15"));
				hbean.setBetween1520(rs.getInt("LT20"));
				hbean.setBetween2025(rs.getInt("LT25"));
				hbean.setBetween2530(rs.getInt("LT30"));
				hbean.setBetween3035(rs.getInt("LT35"));
				hbean.setGreaterThan35(rs.getInt("LT40"));
			}
		}
		catch (SQLException e) {
				hbean = null;
		}
		return hbean;
	}
	private static boolean checkGrades(String grades) {
		boolean isK=false;
			if(grades.contains(",")) {
				String[] testing = grades.split(",");
				for(String s : testing) {
						if(Integer.parseInt(s) < 10) {
							isK=true;
						}
				}
			}else {
				if(Integer.parseInt(grades) < 10) {
					isK=true;	
				}
			}
		
		return isK;
	}
}
