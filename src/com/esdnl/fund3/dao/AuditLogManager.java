package com.esdnl.fund3.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.fund3.bean.AuditLogBean;
import com.esdnl.fund3.bean.Fund3Exception;
public class AuditLogManager {
	public static Integer addNewAuditLog(AuditLogBean al) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.add_fund3_audit_log(?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setString(2,al.getUserName());
			stat.setString(3, al.getLogEntry());
			stat.setInt(4,al.getProjectId());
			stat.execute();
			id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("Integer addNewAuditLog(AuditLogBean al) " + e);
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
		return id;
	}
	public static ArrayList<AuditLogBean> getAuditLogByProject(int id) throws Fund3Exception {
		ArrayList<AuditLogBean> auditlogs = null;
		AuditLogBean auditlog= null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			auditlogs= new ArrayList<AuditLogBean>(5);
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_audit_log_by_pid(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				auditlog = createAuditLogBean(rs);
				auditlogs.add(auditlog);
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<AuditLogBean> getAuditLogByProject(int id) " + e);
			throw new Fund3Exception("Can not extract AuditLogs from DB: " + e);
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
		return auditlogs;
	}	
	public static AuditLogBean createAuditLogBean(ResultSet rs) {
		AuditLogBean abean = null;
		try {
			abean = new AuditLogBean();
			abean.setId(rs.getInt("ID"));
			abean.setAuditDate(new java.util.Date(rs.getTimestamp("AUDITDATE").getTime()));
			abean.setUserName(rs.getString("USERNAME"));
			abean.setLogEntry(rs.getString("LOGENTRY"));
			abean.setProjectId(rs.getInt("PROJECTID"));
		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}
}