package com.nlesd.bcs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.BussingContractorReportingHistoryBean;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class BussingContractorReportingHistoryManager {
	public static void addReportingHistoyBean(BussingContractorReportingHistoryBean atbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.add_reporting_history(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2,atbean.getReportTitle());
			stat.setString(3, atbean.getRanBy());
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void addReportingHistoyBean(BussingContractorReportingHistoryBean atbean): "
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
	public static ArrayList<BussingContractorReportingHistoryBean> getReportingHistory() {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorReportingHistoryBean> list = new ArrayList<BussingContractorReportingHistoryBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_reporting_history; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorReportingHistoryBean abean = new BussingContractorReportingHistoryBean();
				abean = createBussingContractorReportingHistoryBean(rs);
				list.add(abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorReportingHistoryBean> getReportingHistory(): "
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
	public static BussingContractorReportingHistoryBean createBussingContractorReportingHistoryBean(ResultSet rs) {
		BussingContractorReportingHistoryBean abean = null;
		try {
				abean = new BussingContractorReportingHistoryBean();
				abean.setRhId(rs.getInt("RH_ID"));
				abean.setReportTitle(rs.getString("REPORT_TITLE"));
				abean.setRanBy(rs.getString("RAN_BY"));
				abean.setLastRunDate(new java.util.Date(rs.getTimestamp("LAST_RUN_DATE").getTime()));
		}catch (SQLException e) {
				abean = null;
		}
		return abean;
	}
}
