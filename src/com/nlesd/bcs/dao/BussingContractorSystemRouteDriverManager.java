package com.nlesd.bcs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.BussingContractorSystemRouteDriverBean;
public class BussingContractorSystemRouteDriverManager {
	public static BussingContractorSystemRouteDriverBean addBussingContractorSystemRouteDriverBean(BussingContractorSystemRouteDriverBean bpbbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.add_route_driver(?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, bpbbean.getRouteId());
			stat.setInt(3, bpbbean.getDriverId());
			stat.setString(4, bpbbean.getAssignedBy());
			stat.execute();
			Integer sid= ((OracleCallableStatement) stat).getInt(1);
			bpbbean.setId(sid);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static BussingContractorSystemRouteDriverBean addBussingContractorSystemRouteDriverBean(BussingContractorSystemRouteDriverBean bpbbean): "
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
		return bpbbean;
	}
	public static BussingContractorSystemRouteDriverBean createBussingContractorSystemRouteDriverBean(ResultSet rs) {
		BussingContractorSystemRouteDriverBean abean = null;
		try {
				abean = new BussingContractorSystemRouteDriverBean();
				abean.setId(rs.getInt("ID"));
				abean.setRouteId(rs.getInt("ROUTEID"));
				abean.setDriverId(rs.getInt("DRIVERID"));
				Timestamp ts= rs.getTimestamp("DATEASSIGNED");
				if(ts != null){
					abean.setDateAssigned(new java.util.Date(rs.getTimestamp("DATEASSIGNED").getTime()));
				}
				abean.setIsCurrent(rs.getString("ISCURRENT"));
				abean.setAssignedBy(rs.getString("ASSIGNEDBY"));
				
		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}	
}
