package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.Covid19ReportBean;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class Covid19ReportManager {
	public static ArrayList<Covid19ReportBean> getCovid19ReportBySchool(String locationid) {
		ArrayList<Covid19ReportBean> v_opps = null;
		Covid19ReportBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		
		try {
			v_opps = new ArrayList<Covid19ReportBean>(3);
		
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_covid19_report_by_school(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, locationid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
		
			while (rs.next()) {
				eBean = createCovid19ReportBeanBean(rs);
		
				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<Covid19ReportBean> getCovid19ReportBySchool(String locationid): "
					+ e);
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		
		return v_opps;
	}
	public static ArrayList<Covid19ReportBean> getLatestCovid19VaccinationsByDays(int numdays) {
		ArrayList<Covid19ReportBean> v_opps = null;
		Covid19ReportBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		
		try {
			v_opps = new ArrayList<Covid19ReportBean>(3);
		
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_latest_covid_vax(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, numdays);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			HashSet<String>testh = new HashSet<String>();
			while (rs.next()) {
				if(testh.size() > 0) {
					if(!testh.contains(rs.getString("SIN").trim())) {
						eBean = createCovid19ReportBeanBean(rs);
						testh.add(eBean.getEmployeeSin().trim());
						v_opps.add(eBean);
					}
				}else {
					eBean = createCovid19ReportBeanBean(rs);
					testh.add(eBean.getEmployeeSin().trim());
					v_opps.add(eBean);
				}
				
		
				
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<Covid19ReportBean> getLatestCovid19VaccinationsByDays(int numdays) : "
					+ e);
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		
		return v_opps;
	}
	public static Covid19ReportBean createCovid19ReportBeanBean(ResultSet rs) {

		Covid19ReportBean abean = null;
		try {
			abean = new Covid19ReportBean();
			
			abean.setEmployeeName(rs.getString("LASTNAME") + ", " + rs.getString("FIRSTNAME"));
			abean.setEmployeeEmail(rs.getString("EMAIL"));
			abean.setEmployeeSin(rs.getString("SIN"));
			abean.setDocumentId(rs.getInt("DOCUMENT_ID"));
			abean.setFileName(rs.getString("FILENAME"));
			abean.setEmployeeLocation(rs.getString("LOCATION"));
			if(rs.getString("DOCUMENT_TYPE") == null) {
				abean.setDocumentType(null);
			}else {
				if(rs.getString("DOCUMENT_TYPE") == "20") {
					abean.setDocumentType("T");
				}else {
					abean.setDocumentType("S");
				}
			}
			if(rs.getTimestamp("CREATED_DATE") ==  null) {
				abean.setCreatedDate(null);
			}else {
				abean.setCreatedDate(new Date(rs.getTimestamp("CREATED_DATE").getTime()));
			}
			if(rs.getTimestamp("DATE_VERIFIED") ==  null) {
				abean.setVerifiedDate(null);
			}else {
				abean.setVerifiedDate(new Date(rs.getTimestamp("DATE_VERIFIED").getTime()));
			}
			abean.setVerifiedBy(rs.getString("VERIFIED_BY"));
			
			
		}
		catch (SQLException e) {
			abean = null;
		}

		return abean;
	}
}
