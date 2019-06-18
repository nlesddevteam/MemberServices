package com.nlesd.bcs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.BussingContractorSecurityBean;
public class BussingContractorSecurityManager {
	public static BussingContractorSecurityBean getBussingContractorSecurityById(Integer cid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorSecurityBean ebean = new BussingContractorSecurityBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_contractor_sec_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorSecurityBean(rs);
				
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BussingContractorSecurityBean getBussingContractorSecurityById(Integer cid)): "
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
	public static void updateBussingContractorSecurity(BussingContractorSecurityBean bpbbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.bcs_pkg.update_contractor_security(?,?,?,?); end;");
			stat.setInt(1, bpbbean.getId());
			stat.setString(2, bpbbean.getPassword());
			stat.setString(3, bpbbean.getSecurityQuestion());
			stat.setString(4, bpbbean.getSqAnswer());
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("updateBussingContractorSecurity(BussingContractorSecurityBean bpbbean): "
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
	public static BussingContractorSecurityBean getBussingContractorSecurityByEmail(String email) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorSecurityBean ebean = new BussingContractorSecurityBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_contractor_sec_by_em(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, email);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorSecurityBean(rs);
				
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BussingContractorSecurityBean getBussingContractorSecurityByEmail(String email): "
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
	public static BussingContractorSecurityBean getBussingContractorSecurityBySid(Integer cid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorSecurityBean ebean = new BussingContractorSecurityBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_contractor_sec_by_sid(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorSecurityBean(rs);
				
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BussingContractorSecurityBean getBussingContractorSecurityBySid(Integer cid)): "
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
	public static BussingContractorSecurityBean createBussingContractorSecurityBean(ResultSet rs) {
		BussingContractorSecurityBean abean = null;
		try {
				abean = new BussingContractorSecurityBean();
				abean.setId(rs.getInt("ID"));
				abean.setContractorId(rs.getInt("CONTRACTOR_ID"));
				abean.setEmail(rs.getString("EMAIL"));
				abean.setPassword(rs.getString("PASSWORD"));
				abean.setSecurityQuestion(rs.getString("SECURITY_QUESTION"));
				abean.setSqAnswer(rs.getString("SQ_ANSWER"));
				abean.setLastUpdated(new java.util.Date(rs.getTimestamp("LAST_UPDATED").getTime()));
		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}	
}
