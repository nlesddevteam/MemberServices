package com.nlesd.bcs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.RouteRunDriverVehicleBean;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class RouteRunDriverVehicleManager {
	public static ArrayList<RouteRunDriverVehicleBean> getRouteRunDriverVehicleData() {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		RouteRunDriverVehicleBean ebean = new RouteRunDriverVehicleBean();
		ArrayList<RouteRunDriverVehicleBean> list = new ArrayList<RouteRunDriverVehicleBean>(); 
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_routes_runs_details; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createRouteRunDriverVehicleBeanBean(rs);
				list.add(ebean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<RouteRunDriverVehicleBean> getRouteRunDriverVehicleData():"
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
	public static RouteRunDriverVehicleBean createRouteRunDriverVehicleBeanBean(ResultSet rs) {
		RouteRunDriverVehicleBean abean = null;
		try {
				abean = new RouteRunDriverVehicleBean();
				abean.setContractorFirstName(rs.getString("CFIRSTNAME"));
				abean.setContractorLastName(rs.getString("CLASTNAME"));
				abean.setCompanyName(rs.getString("COMPANY"));
				abean.setContractName(rs.getString("CONTRACTNAME"));
				abean.setRouteName(rs.getString("ROUTENAME"));
				abean.setRouteRun(rs.getString("ROUTERUN"));
				abean.setRouteTime(rs.getString("ROUTETIME"));
				abean.setDriverFirstName(rs.getString("FIRSTNAME"));
				abean.setDriverLastName(rs.getString("LASTNAME"));
				abean.setPlateNumber(rs.getString("VPLATENUMBER"));
				abean.setSerialNumber(rs.getString("VSERIALNUMBER"));
		}catch (SQLException e) {
				abean = null;
		}
		return abean;
	}
}
