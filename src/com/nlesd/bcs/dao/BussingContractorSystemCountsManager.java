package com.nlesd.bcs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.BussingContractorSystemCountsBean;
public class BussingContractorSystemCountsManager {
	public static BussingContractorSystemCountsBean getBussingContractorSystemCounts() {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorSystemCountsBean ebean = new BussingContractorSystemCountsBean();
		try {
			con = DAOUtils.getConnection();
			//get contractor counts first
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_con_status_counts; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				createBussingContractorSystemContractBean(rs,ebean,"C");
				
			}
			//get contractor employee counts
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_con_emp_status_counts; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				createBussingContractorSystemContractBean(rs,ebean,"CE");
				
			}
			//get contractor vehicle counts
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_con_veh_status_counts; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				createBussingContractorSystemContractBean(rs,ebean,"CV");
				
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BussingContractorSystemContractBean getBussingContractorSystemContractById(Integer cid):"
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
	public static BussingContractorSystemCountsBean getBussingContractorSystemCountsReg(int cid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorSystemCountsBean ebean = new BussingContractorSystemCountsBean();
		try {
			con = DAOUtils.getConnection();
			//get contractor employee counts
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_reg_emp_status_counts(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				createBussingContractorSystemContractBean(rs,ebean,"CE");
				
			}
			//get contractor vehicle counts
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_reg_veh_status_counts(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				createBussingContractorSystemContractBean(rs,ebean,"CV");
				
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BussingContractorSystemContractBean getBussingContractorSystemContractById(Integer cid):"
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
	public static void createBussingContractorSystemContractBean(ResultSet rs,BussingContractorSystemCountsBean ebean, String ctype) {
		try {
			
				if(ctype == "C"){
					ebean.setSubmittedContractors(rs.getInt("submitted"));
					ebean.setApprovedContractors(rs.getInt("approved"));
					ebean.setRejectedContractors(rs.getInt("rejected"));
					ebean.setSuspendedContractors(rs.getInt("suspended"));
					ebean.setRemovedContractors(rs.getInt("removed"));
					ebean.setTotalContractors(rs.getInt("total"));
				}else if(ctype == "CE"){
					ebean.setSubmittedContractorsEmployees(rs.getInt("submitted"));
					ebean.setApprovedContractorsEmployees(rs.getInt("approved"));
					ebean.setRejectedContractorsEmployees(rs.getInt("rejected"));
					ebean.setSuspendedContractorsEmployees(rs.getInt("suspended"));
					ebean.setRemovedContractorsEmployees(rs.getInt("removed"));
					ebean.setTotalContractorsEmployees(rs.getInt("total"));
					ebean.setNotSubmittedContractorsEmployees(rs.getInt("notsubmitted"));
				}else if(ctype == "CV"){
					ebean.setSubmittedContractorsVehicles(rs.getInt("submitted"));
					ebean.setApprovedContractorsVehicles(rs.getInt("approved"));
					ebean.setRejectedContractorsVehicles(rs.getInt("rejected"));
					ebean.setSuspendedContractorsVehicles(rs.getInt("suspended"));
					ebean.setRemovedContractorsVehicles(rs.getInt("removed"));
					ebean.setTotalContractorsVehicles(rs.getInt("total"));
				}		
		}catch (SQLException e) {
			System.err.println("createBussingContractorSystemContractBean(ResultSet rs,BussingContractorSystemCountsBean ebean, String ctype): "
					+ e);
		}
	}
}
