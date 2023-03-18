package com.nlesd.msauditlog.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.esdnl.dao.DAOUtils;
import com.nlesd.msauditlog.bean.MsAuditLogBean;


public class MsAuditLogManager {
	public static void addMsAuditLogBean(MsAuditLogBean vbean) {
			Connection con = null;
			CallableStatement stat = null;
			try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(true);
				stat = con.prepareCall("begin awsd_user.ms_audit_log_pkg.add_ms_audit_log(?,?,?,?,?); end;");
				stat.setString(1, vbean.getMalAppName());
				stat.setString(2, vbean.getMalAction());
				stat.setString(3, vbean.getMalNotes());
				stat.setInt(4, vbean.getMalBy());
				stat.setInt(5, vbean.getMalObjectKey());
				stat.execute();
				
			}
			catch (SQLException e) {
				e.printStackTrace();
				try {
					con.rollback();
				}
				catch (Exception ex) {}
				System.err.println("static int addMsAuditLogBean(MsAuditLogBean vbean): "
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
	public static MsAuditLogBean createMsAuditLogBean(ResultSet rs) {
		MsAuditLogBean abean = null;
		try {
				abean = new MsAuditLogBean();
				abean.setMalAuditId(rs.getInt("MAL_ID"));
				abean.setMalAppName(rs.getString("MAL_APP"));
				abean.setMalAction(rs.getString("MAL_ACTION"));
				abean.setMalNotes(rs.getString("MAL_NOTES"));
				abean.setMalBy(rs.getInt("MAL_BY"));
				if(!(rs.getTimestamp("MAL_DATE") ==  null)){
					abean.setMalDate(new java.util.Date(rs.getTimestamp("MAL_DATE").getTime()));
				}
				abean.setMalObjectKey(rs.getInt("MAL_OBJECT_KEY"));
				
			}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}	
}
