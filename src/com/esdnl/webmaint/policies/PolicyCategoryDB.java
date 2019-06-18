package com.esdnl.webmaint.policies;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;

public class PolicyCategoryDB {

	public static Vector getPolicyCategories() throws PolicyException {

		Vector categories = null;
		PolicyCategory pol_cat = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			categories = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.esd_web.get_policy_categories; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				pol_cat = new PolicyCategory(rs.getString("CODE"), rs.getString("TITLE"));

				categories.add(pol_cat);
			}
		}
		catch (SQLException e) {
			System.err.println("PolicyDB.getPolicyCategories(): " + e);
			throw new PolicyException("Can not extract Policy Categories from DB: " + e);
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
		return categories;
	}

	public static boolean addPolicyCategory(PolicyCategory pol_cat) throws SQLException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.esd_web.insert_policy_category(?, ?); end;");
			stat.setString(1, pol_cat.getCode());
			stat.setString(2, pol_cat.getTitle());
			stat.execute();
			check = true;
		}
		catch (SQLException e) {
			check = false;
			System.err.println("addPolicyCategory(PolicyCategory pol_cat): " + e);
			//throw  new PolicyException("Can not add Policy Category notes to DB: " + e);
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

	public static boolean updatePolicyCategory(PolicyCategory pol_cat) throws SQLException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.esd_web.update_policy_category(?, ?); end;");
			stat.setString(1, pol_cat.getCode());
			stat.setString(2, pol_cat.getTitle());
			stat.execute();
			check = true;
		}
		catch (SQLException e) {
			check = false;
			System.err.println("updatePolicyCategory(PolicyCategory pol_cat): " + e);
			//throw  new PolicyException("Can not add Policy Category notes to DB: " + e);
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

	public static boolean deletePolicyCategory(String code) throws SQLException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.esd_web.delete_policy_category(?); end;");
			stat.setString(1, code);
			stat.execute();
			check = true;
		}
		catch (SQLException e) {
			check = false;
			System.err.println("updatePolicyCategory(PolicyCategory pol_cat): " + e);
			//throw  new PolicyException("Can not add Policy Category notes to DB: " + e);
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