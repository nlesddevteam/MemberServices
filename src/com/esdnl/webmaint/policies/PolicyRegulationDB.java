package com.esdnl.webmaint.policies;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.OracleTypes;
import oracle.sql.BLOB;

import com.esdnl.dao.DAOUtils;

public class PolicyRegulationDB {

	public static Vector getPolicyRegulations() throws PolicyException {

		Vector regs = null;
		PolicyRegulation reg = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			regs = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.esd_web.get_policy_regulations; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				reg = new PolicyRegulation(rs.getString("CATEGORY_CODE"), rs.getString("CODE"), rs.getString("REG_CODE"), rs.getString("TITLE"), new java.util.Date(rs.getDate(
						"UPLOAD_DATE").getTime()));

				regs.add(reg);
			}
		}
		catch (SQLException e) {
			System.err.println("PolicyRegulationDB.getPolicyRegulations(): " + e);
			throw new PolicyException("Can not extract Policy Regulations from DB: " + e);
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return regs;
	}

	public static Vector getPolicyRegulations(String cat, String code) throws SQLException, PolicyException {

		PolicyRegulation reg = null;
		Connection con = null;
		CallableStatement stat = null;
		OracleResultSet rs = null;
		Vector regs = null;
		try {
			regs = new Vector();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.esd_web.get_policy_regulations(?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, cat);
			stat.setString(3, code);
			stat.execute();
			rs = (OracleResultSet) (((OracleCallableStatement) stat).getCursor(1));

			while (rs.next()) {
				reg = new PolicyRegulation(rs.getString("CATEGORY_CODE"), rs.getString("CODE"), rs.getString("REG_CODE"), rs.getString("TITLE"), new java.util.Date(rs.getDate(
						"UPLOAD_DATE").getTime()));

				regs.add(reg);
			}
		}
		catch (SQLException e) {
			System.err.println("PolicyRegulationDB.getPolicyRegulations(Policy): " + e);
			throw e;
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return regs;
	}

	public static boolean addPolicyRegulation(PolicyRegulation reg) throws Exception {

		Connection con = null;
		CallableStatement stat = null;
		PreparedStatement p_stat = null;
		OracleResultSet rs = null;
		BLOB blob = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);
			stat = con.prepareCall("begin awsd_user.esd_web.insert_policy_regulation(?, ?, ?, ?); end;");
			stat.setString(1, reg.getCategoryCode());
			stat.setString(2, reg.getPolicyCode());
			stat.setString(3, reg.getRegulationCode());
			stat.setString(4, reg.getTitle());
			stat.execute();
			stat.close();

			check = true;
		}
		catch (Exception e) {
			check = false;
			System.err.println("PolicyRegulationDB.addPolicyRegulation(PolicyRegulation reg): " + e);
			throw e;
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return (check);
	}

	public static boolean deletePolicyRegulation(String cat, String code, String reg_code) throws SQLException {

		Connection con = null;
		CallableStatement stat = null;
		File f = null;

		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);
			stat = con.prepareCall("begin awsd_user.esd_web.delete_policy_regulation(?, ?, ?); end;");
			stat.setString(1, cat);
			stat.setString(2, code);
			stat.setString(3, reg_code);
			stat.execute();
			stat.close();

			check = true;
		}
		catch (SQLException e) {
			check = false;
			System.err.println("PolicyRegulationDB.deletePolicy(String cat, String code, String reg_code): " + e);
			throw e;
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
		return (check);
	}
}