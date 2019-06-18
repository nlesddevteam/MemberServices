package com.nlesd.bcs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.BussingContractorSystemReportTableBean;
public class BussingContractorSystemReportTableManager {
	public static ArrayList<BussingContractorSystemReportTableBean> getReportTables() {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorSystemReportTableBean ebean = new BussingContractorSystemReportTableBean();
		ArrayList<BussingContractorSystemReportTableBean> list = new ArrayList<BussingContractorSystemReportTableBean>(); 
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_report_tables; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorSystemReportTableBean(rs);
				list.add(ebean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static ArrayList<BussingContractorRouteListBean> getContractorsRoutes(Integer cid):"
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
	public static BussingContractorSystemReportTableBean getReportTableById(int tid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorSystemReportTableBean ebean = new BussingContractorSystemReportTableBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_report_table_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,tid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorSystemReportTableBean(rs);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BussingContractorSystemReportTableBean getReportTableById(int tid):"
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
	public static BussingContractorSystemReportTableBean createBussingContractorSystemReportTableBean(ResultSet rs) {
		BussingContractorSystemReportTableBean abean = null;
		try {
				abean = new BussingContractorSystemReportTableBean();
				abean.setTableName(rs.getString("TABLENAME"));
				abean.setTableTitle(rs.getString("TABLETITLE"));
				abean.setIsActive(rs.getString("ISACTIVE"));
				abean.setId(rs.getInt("ID"));
				abean.setShortName(rs.getString("SHORTNAME"));
				abean.setJoinTables(rs.getString("JOINTABLES"));
				abean.setJoinWhere(rs.getString("JOINWHERE"));
				abean.setParentTable(rs.getInt("PARENTTABLE"));
				abean.setJoinSelect(rs.getString("JOINSELECT"));
				abean.setJoinHeader(rs.getString("JOINHEADER"));
		}catch (SQLException e) {
				abean = null;
		}
		return abean;
	}	
}
