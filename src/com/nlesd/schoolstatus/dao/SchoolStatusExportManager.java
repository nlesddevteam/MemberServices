package com.nlesd.schoolstatus.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.esdnl.dao.DAOUtils;
import com.nlesd.schoolstatus.bean.SchoolStatusExportBean;
import com.nlesd.schoolstatus.bean.SchoolStatusExportSettingsBean;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class SchoolStatusExportManager {
	public static ArrayList<SchoolStatusExportBean> getSchoolStatusList() {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<SchoolStatusExportBean> list = new ArrayList<SchoolStatusExportBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.SCHOOL_CLOSURE_PKG.get_school_status_export; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				SchoolStatusExportBean abean = new SchoolStatusExportBean();
				abean = createSchoolStatusExportBean(rs);
				list.add(abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<SchoolStatusExportBean> getSchoolStatusList(): "
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
	public static SchoolStatusExportSettingsBean getSchoolStatusExportSettings() {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		SchoolStatusExportSettingsBean ebean = new SchoolStatusExportSettingsBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.SCHOOL_CLOSURE_PKG.get_school_export_settings; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean.setExportStatus(rs.getInt("EXPORT_STATUS") == 1 ? true:false);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("SchoolStatusExportSettingsBean getSchoolStatusExportSettings(): "
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
	public static void addSchoolStatusExportLog(String ipaddress, String exporttype) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.SCHOOL_CLOSURE_PKG.add_school_export_log(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2,ipaddress);
			stat.setString(3,exporttype);
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("addSchoolStatusExportLog(String ipaddress, String exporttype): "
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
	}	
	public static SchoolStatusExportBean createSchoolStatusExportBean(ResultSet rs) {
		SchoolStatusExportBean abean = null;
		try {
				abean = new SchoolStatusExportBean();
				abean.setSchoolNumber(rs.getString("SCHOOL_NUMBER1") + rs.getString("SCHOOL_NUMBER2"));
				abean.setSchoolStatus(rs.getString("SCHOOL_STATUS") == null ? "School Open": rs.getString("SCHOOL_STATUS"));
				abean.setStatusNotes(rs.getString("STATUS_NOTES") == null ? "" : rs.getString("STATUS_NOTES"));
		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}	
}
