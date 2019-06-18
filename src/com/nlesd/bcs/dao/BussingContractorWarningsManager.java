package com.nlesd.bcs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.BussingContractorEmployeeBean;
import com.nlesd.bcs.bean.BussingContractorVehicleBean;
import com.nlesd.bcs.bean.BussingContractorWarningsBean;
public class BussingContractorWarningsManager {
	public static ArrayList<BussingContractorWarningsBean> getWarningsByTable(String tablename) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorWarningsBean> list = new ArrayList<BussingContractorWarningsBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_warnings_by_table(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, tablename);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorWarningsBean abean = new BussingContractorWarningsBean();
				abean = createBussingContractorWarningsBean(rs);
				list.add(abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorWarningsBean> getWarningsByTable(String tablename): "
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
	public static ArrayList<BussingContractorEmployeeBean> getEmployeeWarnings(int contractorid) {
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorWarningsBean> wlist = getWarningsByTable("BCS_CONTRACTOR_EMPLOYEE");
		ArrayList<BussingContractorEmployeeBean> employeelist = new ArrayList<BussingContractorEmployeeBean>();
		BussingContractorEmployeeBean ebean = new BussingContractorEmployeeBean();
		try {
			con = DAOUtils.getConnection();
			
			for(BussingContractorWarningsBean wb : wlist){
				String sql="";
				sql=wb.getMainPageSql().replaceFirst("vcontractorid", Integer.toString(contractorid));
				stat = con.createStatement();
				rs = stat.executeQuery(sql);
				while (rs.next()) {
					ebean = BussingContractorEmployeeManager.createBussingContractorEmployeeBean(rs);
					//set the warning type
					ebean.setWarningNotes(wb.getWarningName());
					employeelist.add(ebean);
				}
				
			}
			
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorEmployeeBean> getEmployeeWarnings(ArrayList<BussingContractorWarningsBean> wlist, int contractorid): "
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
		return employeelist;
	}
	public static ArrayList<BussingContractorVehicleBean> getVehicleWarnings(int contractorid) {
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorWarningsBean> wlist = getWarningsByTable("BCS_CONTRACTOR_VEHICLE");
		ArrayList<BussingContractorVehicleBean> employeelist = new ArrayList<BussingContractorVehicleBean>();
		BussingContractorVehicleBean ebean = new BussingContractorVehicleBean();
		try {
			con = DAOUtils.getConnection();
			
			for(BussingContractorWarningsBean wb : wlist){
				String sql="";
				sql=wb.getMainPageSql().replaceFirst("vcontractorid", Integer.toString(contractorid));
				stat = con.createStatement();
				rs = stat.executeQuery(sql);
				while (rs.next()) {
					ebean = BussingContractorVehicleManager.createBussingContractorVehicleBean(rs);
					//set the warning type
					ebean.setWarningNotes(wb.getWarningName());
					employeelist.add(ebean);
				}
				
			}
			
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorVehicleBean> getVehicleWarnings(int contractorid): "
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
		return employeelist;
	}	
	public static BussingContractorWarningsBean createBussingContractorWarningsBean(ResultSet rs) {
		BussingContractorWarningsBean abean = null;
		try {
				abean = new BussingContractorWarningsBean();
				abean.setId(rs.getInt("ID"));
				abean.setTableName(rs.getString("TABLENAME"));
				abean.setFieldName(rs.getString("FIELDNAME"));
				abean.setWarningDays(rs.getInt("WARNINGDAYS"));
				abean.setWarningNotes(rs.getString("WARNINGNOTES"));
				abean.setIsActive(rs.getString("ISACTIVE"));
				abean.setWarningName(rs.getString("WARNINGNAME"));
				abean.setWarningException(rs.getString("WARNINGEXCEPTION"));
				abean.setMainPageSql(rs.getString("MAINPAGESQL"));
				abean.setAutomatedSql(rs.getString("AUTOMATEDSQL"));		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}	
}
