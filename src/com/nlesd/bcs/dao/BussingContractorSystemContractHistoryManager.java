package com.nlesd.bcs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.BussingContractorSystemContractHistoryBean;
public class BussingContractorSystemContractHistoryManager {
	public static BussingContractorSystemContractHistoryBean addBussingContractorSystemContractHistory(BussingContractorSystemContractHistoryBean vbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.add_contract_history(?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, vbean.getContractStatus());
			stat.setInt(3, vbean.getContractId());
			stat.setInt(4, vbean.getContractorId());
			stat.setString(5, vbean.getStatusNotes());
			stat.setString(6, vbean.getStatusBy());
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
			System.err.println("BussingContractorSystemContractHistoryBean addBussingContractorSystemContract(BussingContractorSystemContractHistoryBean vbean): "
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
	public static BussingContractorSystemContractHistoryBean getBussingContractorSystemContractStatus(Integer cid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorSystemContractHistoryBean ebean = new BussingContractorSystemContractHistoryBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_contract_status(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			int x=1;
			while (rs.next()){
				if(x == 1){
					ebean = createBussingContractorSystemContractHistoryBean(rs);
					x++;
				}
				
				
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
	public static BussingContractorSystemContractHistoryBean createBussingContractorSystemContractHistoryBean(ResultSet rs) {
		BussingContractorSystemContractHistoryBean abean = null;
		try {
				abean = new BussingContractorSystemContractHistoryBean();
				abean.setId(rs.getInt("ID"));
				abean.setContractStatus(rs.getInt("CONTRACTSTATUS"));
				abean.setContractId(rs.getInt("CONTRACTID"));
				abean.setContractorId(rs.getInt("CONTACTORID"));
				abean.setStatusNotes(rs.getString("STATUSNOTES"));
				abean.setStatusBy(rs.getString("STATUSBY"));
				Timestamp ts= rs.getTimestamp("STATUSDATE");
				if(ts != null){
					abean.setStatusDate(new java.util.Date(rs.getTimestamp("STATUSDATE").getTime()));
				}
				abean.setStatusString(rs.getString("DD_TEXT"));
				abean.setContractorBean(BussingContractorManager.getBussingContractorById(abean.getContractorId()));
		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}		
}
