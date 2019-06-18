package com.nlesd.bcs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.BussingContractorSystemCustomReportBean;
public class BussingContractorSystemCustomReportManager {
	public static BussingContractorSystemCustomReportBean addBussingContractorCustomReport(BussingContractorSystemCustomReportBean vbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.add_custom_report(?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2, vbean.getReportUser());
			stat.setString(3, vbean.getReportName());
			stat.setString(4, vbean.getReportTables());
			stat.setString(5, vbean.getReportTableFields());
			stat.setString(6, vbean.getReportSql());
			stat.execute();
			Integer sid= ((OracleCallableStatement) stat).getInt(1);
			vbean.setId(sid);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static BussingContractorSystemCustomReportBean addBussingContractorCustomReport(BussingContractorSystemCustomReportBean vbean): "
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
		return vbean;
	}
	public static ArrayList<BussingContractorSystemCustomReportBean> getCustomReportsByUser(String user) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorSystemCustomReportBean> list = new ArrayList<BussingContractorSystemCustomReportBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get__user_custom_reports(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2,user);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorSystemCustomReportBean bean = createBussingContractorSystemCustomReportBean(rs);
				list.add(bean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorSystemCustomReportBean> getCustomReportsByUser(String user): "
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
	public static BussingContractorSystemCustomReportBean getCustomReportsById(int id) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorSystemCustomReportBean bean = new BussingContractorSystemCustomReportBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_custom_report_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				bean = createBussingContractorSystemCustomReportBean(rs);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static BussingContractorSystemCustomReportBean getCustomReportsById(int id): "
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
		return bean;
	}
	public static BussingContractorSystemCustomReportBean updateBussingContractorCustomReport(BussingContractorSystemCustomReportBean vbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.bcs_pkg.update_custom_report(?,?,?,?); end;");
			stat.setString(1, vbean.getReportName());
			stat.setString(2, vbean.getReportTableFields());
			stat.setString(3, vbean.getReportSql());
			stat.setInt(4, vbean.getId());
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BussingContractorSystemCustomReportBean updateBussingContractorCustomReport(BussingContractorSystemCustomReportBean vbean) "
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
		return vbean;
	}
	public static void deleteBussingContractorCustomReportBean(int id) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.bcs_pkg.delete_report(?); end;");
			stat.setInt(1, id);
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static void deleteBussingContractorCustomReportBean(int id): "
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
	public static BussingContractorSystemCustomReportBean createBussingContractorSystemCustomReportBean(ResultSet rs) {
		BussingContractorSystemCustomReportBean abean = null;
		try {
				abean = new BussingContractorSystemCustomReportBean();
				abean.setId(rs.getInt("ID"));
				abean.setReportUser(rs.getString("REPORTUSER"));
				abean.setReportName(rs.getString("REPORTNAME"));
				abean.setReportTables(rs.getString("REPORTTABLES"));
				abean.setReportTableFields(rs.getString("REPORTTABLEFIELDS"));
				abean.setReportSql(rs.getString("REPORTSQL"));
		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}	
}
