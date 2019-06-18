package com.esd.esdweb.policies;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleResultSet;

import com.esdnl.dao.DAOUtils;

public class DistrictPolicyDB {

	public static Vector<DistrictPolicy> getPolicies() throws SQLException {

		Vector<DistrictPolicy> policies = null;
		DistrictPolicy policy = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			policies = new Vector<DistrictPolicy>(5);

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.esd_web.get_policies; end;");
			stat.registerOutParameter(1, -10);
			stat.execute();
			for (rs = ((OracleCallableStatement) stat).getCursor(1); rs.next(); policies.add(policy))
				policy = new DistrictPolicy(rs.getString("CATEGORY_CODE"), rs.getString("CODE"), rs.getString("TITLE"), new Date(rs.getDate(
						"UPLOAD_DATE").getTime()));

		}
		catch (SQLException e) {
			System.err.println("DistrictPoliciesDB.getPolicys(): " + e);
			throw e;
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception exception1) {}
			try {
				stat.close();
			}
			catch (Exception exception2) {}
			try {
				con.close();
			}
			catch (Exception exception3) {}
		}
		return policies;
	}

	public static Vector<DistrictPolicy> getRecentlyPostedPolicies() throws SQLException {

		Vector<DistrictPolicy> policies = null;
		DistrictPolicy policy = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			policies = new Vector<DistrictPolicy>(5);
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.esd_web.get_recent_policies; end;");
			stat.registerOutParameter(1, -10);
			stat.execute();
			for (rs = ((OracleCallableStatement) stat).getCursor(1); rs.next(); policies.add(policy))
				policy = new DistrictPolicy(rs.getString("CATEGORY_CODE"), rs.getString("CODE"), rs.getString("TITLE"), new Date(rs.getDate(
						"UPLOAD_DATE").getTime()));

		}
		catch (SQLException e) {
			System.err.println("DistrictPoliciesDB.getPolicys(): " + e);
			throw e;
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception exception1) {}
			try {
				stat.close();
			}
			catch (Exception exception2) {}
			try {
				con.close();
			}
			catch (Exception exception3) {}
		}
		return policies;
	}

	public static Vector<String> getPolicyCategoryCodes() throws SQLException {

		Vector<String> categories = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			categories = new Vector<String>(5);
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.esd_web.get_policy_categories; end;");
			stat.registerOutParameter(1, -10);
			stat.execute();
			for (rs = ((OracleCallableStatement) stat).getCursor(1); rs.next(); categories.add(rs.getString("CODE") + ": "
					+ rs.getString("TITLE")))
				;
		}
		catch (SQLException e) {
			System.err.println("DistrictPoliciesDB.getPolicys(): " + e);
			throw e;
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception exception1) {}
			try {
				stat.close();
			}
			catch (Exception exception2) {}
			try {
				con.close();
			}
			catch (Exception exception3) {}
		}
		return categories;
	}

	public static Vector<PolicyRegulation> getPolicyRegulations(String cat, String code) throws SQLException {

		PolicyRegulation reg = null;
		Connection con = null;
		CallableStatement stat = null;
		OracleResultSet rs = null;
		Vector<PolicyRegulation> regs = null;
		try {
			regs = new Vector<PolicyRegulation>();
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.esd_web.get_policy_regulations(?, ?); end;");
			stat.registerOutParameter(1, -10);
			stat.setString(2, cat);
			stat.setString(3, code);
			stat.execute();
			for (rs = (OracleResultSet) ((OracleCallableStatement) stat).getCursor(1); rs.next(); regs.add(reg))
				reg = new PolicyRegulation(rs.getString("CATEGORY_CODE"), rs.getString("CODE"), rs.getString("REG_CODE"), rs.getString("TITLE"), new Date(rs.getDate(
						"UPLOAD_DATE").getTime()));

		}
		catch (SQLException e) {
			System.err.println("PolicyRegulationDB.getPolicyRegulations(Policy): " + e);
			throw e;
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception exception1) {}
			try {
				stat.close();
			}
			catch (Exception exception2) {}
			try {
				con.close();
			}
			catch (Exception exception3) {}
		}
		return regs;
	}

}
