package com.nlesd.eecd.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.esdnl.dao.DAOUtils;
import com.nlesd.eecd.bean.EECDExportItemBean;
import com.nlesd.eecd.bean.EECDShortlistBean;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class EECDShortlistManager {
	public static int addAreaShortList(String schoolyear,int areaid) {
		Connection con = null;
		CallableStatement stat = null;
		Integer sid=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.eecd_pkg.add_shortlist(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2, schoolyear);
			stat.setInt(3, areaid);
			stat.execute();
			sid= ((OracleCallableStatement) stat).getInt(1);
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("int addAreaShortList(String schoolyear,int areaid): "
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
		return sid;
	}
	public static EECDShortlistBean getShortlistByAreaId(int aid,String schoolyear) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		EECDShortlistBean abean = new EECDShortlistBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.eecd_pkg.get_shortlist_by_aid(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, aid);
			stat.setString(3, schoolyear);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				abean = createShortlistBean(rs);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("EECDAreaBean getEECDAreaById(int pid): "
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
		return abean;
	}
	public static ArrayList<EECDExportItemBean> getExportListByArea(int aid,String syear,String ssyear) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<EECDExportItemBean> list = new ArrayList<EECDExportItemBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.eecd_pkg.get_shortlist_export(?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, aid);
			stat.setString(3, syear);
			stat.setString(4, ssyear);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				EECDExportItemBean abean = new EECDExportItemBean();
				abean = createEECDExportItemBean(rs);
				list.add(abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<EECDExportItemBean> getExportListByArea(int aid,String syear,String ssyear): "
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
		return list;
	}
	public static ArrayList<EECDExportItemBean> getExportListByAreaMulti(String aid,String syear,String ssyear) {
		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		ArrayList<EECDExportItemBean> list = new ArrayList<EECDExportItemBean>();
		try {
			con = DAOUtils.getConnection();
			StringBuilder sb= new StringBuilder();
			sb.append("select ta.PERSONNEL_ID,ta.AREA_ID,ta.SCHOOL_YEAR,ea.AREA_DESCRIPTION,coms.elements\r\n" + 
					"		,coms.fname,coms.lname,sds.LOCATION,sds.POSITION,sds.\"Seniority_Numeric\" \r\n" + 
					"		from EECD_TEACHER_AREA ta\r\n" + 
					"		inner join EECD_SHORTLIST es \r\n" + 
					"		on ta.IS_SELECTED=es.ID\r\n" + 
					"		inner join EECD_AREA ea on ta.AREA_ID=ea.ID\r\n" + 
					"		left outer join\r\n" + 
					"		(\r\n" + 
					"			SELECT personnel_firstname as fname,personnel_lastname as lname,personnel_id, 		wm_concat(nvalue) as \r\n" + 
					"		elements from\r\n" + 
					"		(\r\n" + 
					"      		select pp.personnel_firstname,personnel_lastname,ta.*,es.*,ea.*,ea.AREA_DESCRIPTION || ' (' || \r\n" + 
					"		es.SCHOOL_YEAR || ')' as nvalue from EECD_TEACHER_AREA ta\r\n" + 
					"			inner join EECD_SHORTLIST es \r\n" + 
					"			on ta.IS_SELECTED=es.ID\r\n" + 
					"      			inner join EECD_AREA ea on ta.AREA_ID=ea.ID\r\n" + 
					"      			inner join PERSONNEL pp on ta.PERSONNEL_ID=pp.PERSONNEL_ID\r\n" + 
					" 			where es.SHORTLIST_COMPLETED=1\r\n" + 
					"		)\r\n" + 
					"		GROUP BY personnel_id,personnel_firstname,personnel_lastname\r\n" + 
					"\r\n" + 
					"		) coms on ta.PERSONNEL_ID=coms.PERSONNEL_ID\r\n" + 
					"		left outer join\r\n" + 
					"		(\r\n" + 
					"		select * from (\r\n" + 
					"		select p.PERSONNEL_ID,p.PERSONNEL_FIRSTNAME,p.PERSONNEL_LASTNAME,sm.\"Seniority_Numeric\",\r\n" + 
					"		sa.LOCATION,sa.POSITION,sa.END_DATE,sa.FTE_HRS,\r\n" + 
					"		max(sa.END_DATE) over (partition by P.PERSONNEL_ID) max_my_date,\r\n" + 
					"		max(sa.FTE_HRS) over (partition by P.PERSONNEL_ID) max_my_fte\r\n" + 
					"		from PERSONNEL p \r\n" + 
					"		left outer join SDS_PREMPMAS sp on upper(trim(p.PERSONNEL_EMAIL))=upper(trim(sp.EMAIL))\r\n" + 
					"		left outer join SDS_PRSENMAS sm on trim(sp.EMP_ID)=trim(sm.\"Employee\")\r\n" + 
					"		left outer join SDS_AAPOSMAS sa on trim(sp.EMP_ID) =  trim(sa.EMP_ID)\r\n" + 
					"		where sa.SCHOOL_YR='");
			sb.append(ssyear);
			sb.append("' order by sa.END_DATE,sa.FTE_HRS desc)");
			sb.append(" where END_DATE=max_my_date and FTE_HRS=max_my_fte) sds on ta.PERSONNEL_ID=sds.PERSONNEL_ID ");
			sb.append(" where es.SHORTLIST_COMPLETED=1 and ta.AREA_ID in(" + aid + ")");
			sb.append(" and ta.SCHOOL_YEAR='" + syear + "'");
			sb.append(" order by AREA_DESCRIPTION,lname, fname");
			con = DAOUtils.getConnection();
			stat = con.prepareStatement(sb.toString());
			rs = stat.executeQuery();
			while (rs.next()){
				EECDExportItemBean abean = new EECDExportItemBean();
				abean = createEECDExportItemBean(rs);
				list.add(abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<EECDExportItemBean> getExportListByArea(int aid,String syear,String ssyear): "
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
		return list;
	}	
	public static EECDShortlistBean createShortlistBean(ResultSet rs) {
		EECDShortlistBean abean = null;
		try {
				abean = new EECDShortlistBean();
				abean.setId(rs.getInt("ID"));
				if(rs.getInt("SHORTLIST_COMPLETED") == 1){
					abean.setShortlistCompleted(true);
				}else{
					abean.setShortlistCompleted(false);
				}
				if(!(rs.getTimestamp("DATE_COMPLETED") ==  null)){
					abean.setDateCompleted(new java.util.Date(rs.getTimestamp("DATE_COMPLETED").getTime()));
				}
				abean.setCompletedBy(rs.getString("COMPLETED_BY"));
				abean.setSchoolYear(rs.getString("SCHOOL_YEAR"));
				abean.setAreaId(rs.getInt("AREA_ID"));
				
			}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}
	public static EECDExportItemBean createEECDExportItemBean(ResultSet rs) {
		EECDExportItemBean abean = null;
		try {
				abean = new EECDExportItemBean();
				abean.setTeacherName(rs.getString("FNAME") + " " + rs.getString("LNAME"));
				abean.setCurrentSchool(rs.getString("LOCATION"));
				abean.setCurrentAssignment(rs.getString("POSITION"));
				abean.setSeniority(rs.getString("SENIORITY_NUMERIC"));
				abean.setCommittees(rs.getString("ELEMENTS"));
				abean.setAreaDescription(rs.getString("AREA_DESCRIPTION"));
			}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}
}
