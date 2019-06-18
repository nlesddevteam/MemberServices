package com.esdnl.fund3.dao;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.fund3.bean.CustomReportBean;
import com.esdnl.fund3.bean.CustomReportFieldBean;
import com.esdnl.fund3.bean.Fund3Exception;
public class CustomReportManager {
	public static List<Map<String, Object>> runCustomReport(String sql) throws Fund3Exception {
		Connection con = null;
		ResultSet resultSet = null;
		Statement stat = null;
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		try {
			con = DAOUtils.getConnection();
			stat = con.createStatement();
			resultSet = stat.executeQuery(sql);
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (resultSet.next()) {
			    Map<String, Object> columns = new LinkedHashMap<String, Object>();

			    for (int i = 1; i <= columnCount; i++) {
			        columns.put(metaData.getColumnLabel(i), resultSet.getObject(i));
			    }
			    rows.add(columns);
			}
		}
		catch (SQLException e) {
			System.err.println("Map<String, Object> runCustomReport(String sql) " + e);
			throw new Fund3Exception("Can not extract projects from DB: " + e);
		}
		finally {
			try {
				resultSet.close();
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
		return rows;
	}
	public static Integer addNewCustomReport(String reportname,String userid) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.add_fund3_custom_report(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setString(2,reportname);
			stat.setString(3, userid);
			stat.execute();
			id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("Integer addNewContact(String reportname,String userid) " + e);
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
	public static Integer addNewCustomReportField(CustomReportFieldBean crfb) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.add_fund3_custom_report_f(?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setInt(2,crfb.getReportId());
			stat.setString(3, crfb.getFieldName());
			stat.setString(4, crfb.getFieldCriteria());
			stat.setInt(5, crfb.getFieldUsed());
			stat.execute();
			id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("Integer addNewCustomReportField(CustomReportFieldBean crfb) " + e);
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
	public static void addCustomReportSql(String reportsql,Integer reportid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.fund3_pkg.add_fund3_custom_report_s(?,?); end;");
			stat.setInt(1,reportid);
			if (reportsql != null) {
				Clob clobdesc = con.createClob();
				clobdesc.setString(1, reportsql);
				((OracleCallableStatement) stat).setClob(2, clobdesc);
			}
			else
			{
				stat.setNull(2, OracleTypes.CLOB);
			}
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void addCustomReportSql(String reportsql,Integer reportid) " + e);
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
	public static ArrayList<CustomReportBean> getReportsForUser(String user) throws Fund3Exception {
		ArrayList<CustomReportBean> list = new ArrayList<CustomReportBean>();
		CustomReportBean creport = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_fund3_custom_reports(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2,user);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				creport = createCustomReportBean(rs);
				list.add(creport);
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<CustomReportBean> getReportsForUser(String user) " + e);
			throw new Fund3Exception("Can not extract custom report from DB: " + e);
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
		return list;
	}

	public static CustomReportBean createCustomReportBean(ResultSet rs) {
		CustomReportBean abean = null;
		try {
			abean = new CustomReportBean();
			abean.setId(rs.getInt("ID"));
			abean.setReportTitle(rs.getString("REPORT_TITLE"));
			Timestamp ts= rs.getTimestamp("DATE_CREATED");
			if(ts != null){
				abean.setDateCreated(new java.util.Date(rs.getTimestamp("DATE_CREATED").getTime()));
			}
			ts=rs.getTimestamp("DATE_LAST_USED");
			if(ts != null){
				abean.setDateLastUsed(new java.util.Date(rs.getTimestamp("DATE_LAST_USED").getTime()));
			}
			abean.setReportType(rs.getString("REPORT_TYPE"));
			abean.setUserId(rs.getString("USER_ID"));		}
		catch (SQLException e) {
			System.err.println("ProjectManager createProjectBean(ResultSet rs)" + e);
			abean = null;
		}
		return abean;
	}
	public static String getReportsSql(Integer report) throws Fund3Exception {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		String reportsql="";
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_fund3_custom_report_sql(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, report);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
			    Clob clob = rs.getClob("REPORT_SQL");
			    reportsql=clob.getSubString(1, (int) clob.length());
			}
		}
		catch (SQLException e) {
			System.err.println("String getReportsSql(Integer report) " + e);
			throw new Fund3Exception("Can not extract custom report from DB: " + e);
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
		return reportsql;
	}
	public static void updateReportDateLastUsed(Integer report) throws Fund3Exception {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.fund3_pkg.update_fund3_report_last(?); end;");
			stat.setInt(1, report);
			stat.execute();
		}
		catch (SQLException e) {
			System.err.println("void updateReportDateLastUsed(Integer report) " + e);
			throw new Fund3Exception("Can not extract custom report from DB: " + e);
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
	public static CustomReportBean getCustomReportById(Integer reportid) throws Fund3Exception {
		CustomReportBean creport = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_fund3_custom_report_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,reportid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				creport = createCustomReportBean(rs);
			}
		}
		catch (SQLException e) {
			System.err.println("CustomReportBean getCustomReportById(Integer reportid) " + e);
			throw new Fund3Exception("Can not extract custom report from DB: " + e);
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
		return creport;
	}
	public static void deleteCustomReport(Integer report) throws Fund3Exception {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.fund3_pkg.remove_fund3_report(?); end;");
			stat.setInt(1, report);
			stat.execute();
		}
		catch (SQLException e) {
			System.err.println("deleteCustomReport(Integer report) " + e);
			throw new Fund3Exception("Can not extract custom report from DB: " + e);
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
	public static void updateCustomReport(String reportname,Integer reportid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.fund3_pkg.update_fund3_report_title(?,?); end;");
			stat.setString(1,reportname);
			stat.setInt(2, reportid);
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("updateCustomReport(String reportname,Integer reportid) " + e);
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
	public static void updateCustomReportSQL(String reportsql,Integer reportid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.fund3_pkg.update_fund3_report_sql(?,?); end;");
			if (reportsql != null) {
				Clob clobdesc = con.createClob();
				clobdesc.setString(1, reportsql);
				((OracleCallableStatement) stat).setClob(1, clobdesc);
			}
			else
			{
				stat.setNull(1, OracleTypes.CLOB);
			}
			stat.setInt(2, reportid);
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void updateCustomReportSQL(String reportsql,Integer reportid) " + e);
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
	public static void updateCustomReportFields(CustomReportFieldBean crfb) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.fund3_pkg.update_fund3_report_fields(?,?,?,?); end;");
			stat.setString(1,crfb.getFieldName());
			stat.setString(2,crfb.getFieldCriteria());
			stat.setInt(3,crfb.getFieldUsed());
			stat.setInt(4,crfb.getReportId() );
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void updateCustomReportFields(CustomReportFieldBean crfb) " + e);
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
}