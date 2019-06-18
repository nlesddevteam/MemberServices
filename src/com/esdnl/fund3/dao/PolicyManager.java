package com.esdnl.fund3.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.fund3.bean.Fund3Exception;
import com.esdnl.fund3.bean.PolicyBean;
public class PolicyManager {
	public static Integer addNewPolicy(PolicyBean cb) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.add_fund3_policy(?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setString(2,cb.getLinkText());
			stat.setString(3, cb.getFileName());
			stat.setInt(4, cb.getIsActive());
			stat.setString(5, cb.getAddedBy());
			stat.execute();
			id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("Integer addNewPolicy(PolicyBean cb) " + e);
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
		return id;
	}
	public static Vector<PolicyBean> getPolicies() throws Fund3Exception {
		Vector<PolicyBean> policies = null;
		PolicyBean policy = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			policies= new Vector<PolicyBean>(5);
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_policies; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				policy = createPolicyBean(rs);
				policies.add(policy);
			}
		}
		catch (SQLException e) {
			System.err.println("Vector<PolicyBean> getPolicies() " + e);
			throw new Fund3Exception("Can not extract policies from DB: " + e);
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
		return policies;
	}
	public static PolicyBean getPolicyById(int id) throws Fund3Exception {
		PolicyBean pb = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_fund3_policy_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				pb = createPolicyBean(rs);
			}
		}
		catch (SQLException e) {
			System.err.println("PolicyBean getPolicyById(int id)  " + e);
			throw new Fund3Exception("Can not extract Policy from DB: " + e);
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
		return pb;
	}
	public static void deletePolicy(Integer tid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.fund3_pkg.delete_fund3_policy(?); end;");
			stat.setInt(1, tid);
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void deletePolicy(Integer tid))" + e);
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
	public static void updateNewPolicy(PolicyBean cb) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.fund3_pkg.update_fund3_policy(?,?,?,?,?); end;");
			stat.setString(1,cb.getLinkText());
			stat.setString(2, cb.getFileName());
			stat.setInt(3, cb.getIsActive());
			stat.setString(4, cb.getAddedBy());
			stat.setInt(5, cb.getId());
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void updateNewPolicy(PolicyBean cb)" + e);
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
	public static Vector<PolicyBean> getActivePolicies() throws Fund3Exception {
		Vector<PolicyBean> policies = null;
		PolicyBean policy = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			policies= new Vector<PolicyBean>(5);
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_active_policies; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				policy = createPolicyBean(rs);
				policies.add(policy);
			}
		}
		catch (SQLException e) {
			System.err.println("Vector<PolicyBean> getActivePolicies() " + e);
			throw new Fund3Exception("Can not extract policies from DB: " + e);
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
		return policies;
	}
	public static void updatePolicySortOrder(Integer pid,Integer sortorder) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.fund3_pkg.update_fund3_policy_sort(?,?); end;");
			stat.setInt(1,pid);
			stat.setInt(2, sortorder);
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void updatePolicySortOrder(Integer pid,Integer sortorder)" + e);
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
	public static PolicyBean createPolicyBean(ResultSet rs) {
		PolicyBean abean = null;
		try {
			abean = new PolicyBean();
			abean.setId(rs.getInt("ID"));
			abean.setLinkText(rs.getString("LINK_TEXT"));
			abean.setFileName(rs.getString("FILE_NAME"));
			abean.setIsActive(rs.getInt("IS_ACTIVE"));
			abean.setAddedBy(rs.getString("ADDED_BY"));
			abean.setDateAdded(new java.util.Date(rs.getTimestamp("DATE_ADDED").getTime()));
		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}	
}