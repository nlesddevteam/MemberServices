package com.nlesd.bcs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.BussingContractorSystemRegionalBean;
import oracle.jdbc.OracleTypes;

public class BussingContractorSystemRegionalManager {
	public static void addBussingContractorSystemRegionalBean(BussingContractorSystemRegionalBean atbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.add_regional_info(?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2,atbean.getRegionCode());
			stat.setInt(3,atbean.getDepotCode());
			stat.setInt(4,atbean.getrId());
			stat.setString(5,atbean.getrType());
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void addBussingContractorSystemRegionalBean(BussingContractorSystemRegionalBean atbean): "
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
	public static void updateBussingContractorSystemRegionalBean(BussingContractorSystemRegionalBean atbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.bcs_pkg.update_regional_info(?,?,?,?,?); end;");
			stat.setInt(1,atbean.getId());
			stat.setInt(2,atbean.getRegionCode());
			stat.setInt(3,atbean.getDepotCode());
			stat.setInt(4,atbean.getrId());
			stat.setString(5,atbean.getrType());
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void updateBussingContractorSystemRegionalBean(BussingContractorSystemRegionalBean atbean): "
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
	public static BussingContractorSystemRegionalBean createBussingContractorSystemRegionalBean(ResultSet rs) {
		BussingContractorSystemRegionalBean abean = null;
		//try to create the bean, return null in case query has not been updated with fields
		try {
				abean = new BussingContractorSystemRegionalBean();
				abean.setId(rs.getInt("REGID"));
				abean.setRegionCode(rs.getInt("REGIONCODE"));
				abean.setDepotCode(rs.getInt("DEPOTCODE"));
				abean.setrId(rs.getInt("RTYPEID"));
				abean.setrType(rs.getString("RTYPE"));
				abean.setRegionName(rs.getString("REGLONG"));
				abean.setDepotName(rs.getString("DEPLONG"));
		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}
}
