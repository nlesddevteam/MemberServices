package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.Covid19ReportBean;
import com.esdnl.personnel.jobs.bean.Covid19SDSStatusBean;

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
				eBean = createCovid19ReportBean(rs);
		
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
						eBean = createCovid19ReportBean(rs);
						testh.add(eBean.getEmployeeSin().trim());
						v_opps.add(eBean);
					}
				}else {
					eBean = createCovid19ReportBean(rs);
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
	public static ArrayList<Covid19ReportBean> getCovid19Exemptions() {
		ArrayList<Covid19ReportBean> v_opps = null;
		Covid19ReportBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<String> testing = new ArrayList<>();
		try {
			v_opps = new ArrayList<Covid19ReportBean>(3);
		
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_covid_exempt; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			//HashSet<String>testh = new HashSet<String>();
			while (rs.next()) {
				eBean = createCovid19ReportBean(rs);
				//testh.add(eBean.getEmployeeSin().trim());
				if(!(testing.contains(eBean.getEmployeeEmail()))) {
					v_opps.add(eBean);
					testing.add(eBean.getEmployeeEmail());
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
	public static ArrayList<Covid19ReportBean> getCovid19SpecialStatus() {
		ArrayList<Covid19ReportBean> v_opps = null;
		Covid19ReportBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<String> testing = new ArrayList<>();
		try {
			v_opps = new ArrayList<Covid19ReportBean>(3);
		
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_covid_ss; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			//HashSet<String>testh = new HashSet<String>();
			while (rs.next()) {
				eBean = createCovid19ReportBeanSpecial(rs);
				//testh.add(eBean.getEmployeeSin().trim());
				if(!(testing.contains(eBean.getEmployeeEmail()))) {
					v_opps.add(eBean);
					testing.add(eBean.getEmployeeEmail());
				}
				
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<Covid19ReportBean> getCovid19SpecialStatus() : "
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
	public static Covid19ReportBean createCovid19ReportBean(ResultSet rs) {

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
			abean.setRejectedBy(rs.getString("REJECTED_BY"));
			abean.setRejectedNotes(rs.getString("REJECTED_NOTES"));
			if(rs.getTimestamp("DATE_REJECTED") ==  null) {
				abean.setRejectedDate(null);
			}else {
				abean.setRejectedDate(new Date(rs.getTimestamp("DATE_REJECTED").getTime()));
			}
			if(rs.getInt("EXCEMPTION_DOC") == 1) {
				abean.setExemptionDoc(true);
			}else {
				abean.setExemptionDoc(false);
			}
			try {
				Covid19SDSStatusBean  stbean = new Covid19SDSStatusBean();
				stbean.setAddedBy(rs.getString("ADDED_BY"));
				stbean.setApplicantId(rs.getString("APPLICANT_ID"));
				stbean.setDateAdded(new Date(rs.getTimestamp("DATE_ADDED").getTime()));
				stbean.setSdsId(rs.getString("SDS_ID"));
				stbean.setId(rs.getInt("CSS_ID"));
				abean.setStBean(stbean);
			}catch(Exception e) {
				abean.setStBean(null);
			}
			
		}
		catch (SQLException e) {
			abean = null;
		}

		return abean;
	}
	public static Covid19ReportBean createCovid19ReportBeanSpecial(ResultSet rs) {

		Covid19ReportBean abean = null;
		try {
			abean = new Covid19ReportBean();
			
			abean.setEmployeeName(rs.getString("LASTNAME") + ", " + rs.getString("FIRSTNAME"));
			abean.setEmployeeEmail(rs.getString("EMAIL").toLowerCase());
			abean.setEmployeeLocation(rs.getString("LOCATION"));
			StringBuilder sb = new StringBuilder();
			DateFormat dt = new SimpleDateFormat("dd/MM/yyyy"); 
			sb.append("Special Status added on ");
			sb.append(dt.format(new Date(rs.getTimestamp("DATE_ADDED").getTime())));
			sb.append(" by ");
			sb.append(rs.getString("ADDED_BY"));
			abean.setSsText(sb.toString());
			
		}
		catch (SQLException e) {
			abean = null;
		}

		return abean;
	}
}
