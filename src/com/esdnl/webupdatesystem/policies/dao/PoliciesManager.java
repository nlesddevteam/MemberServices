package com.esdnl.webupdatesystem.policies.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.webupdatesystem.blogs.bean.BlogsException;
import com.esdnl.webupdatesystem.policies.bean.PoliciesException;
import com.esdnl.webupdatesystem.policies.bean.PolicyBean;
import com.esdnl.webupdatesystem.policies.constants.PolicyCategory;
import com.esdnl.webupdatesystem.policies.constants.PolicyStatus;

public class PoliciesManager {
	public static int addPolicy(PolicyBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.add_new_policy(?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setInt(2, ebean.getPolicyCategory().getValue());
			stat.setInt(3, ebean.getPolicyStatus().getValue());
			stat.setString(4, ebean.getPolicyNumber());
			stat.setString(5, ebean.getPolicyTitle());
			stat.setString(6, ebean.getPolicyDocumentation());
			stat.setString(7, ebean.getPolicyAdminDoc());
			stat.setString(8, ebean.getAddedBy());
			stat.execute();
			id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("int addPolicy(PolicyBean ebean) " + e);
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
	public static Vector<PolicyBean> getPolicies() throws PoliciesException {
		Vector<PolicyBean> mms = null;
		PolicyBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			mms = new Vector<PolicyBean>(5);
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_policies; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createPolicyBean(rs);
				mms.add(mm);
			}
		}
		catch (SQLException e) {
			System.err.println("static Vector getPolicies() throws PoliciesException " + e);
			throw new PoliciesException("Can not extract Policies from DB: " + e);
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
		return mms;
	}	
	public static PolicyBean getPolicyById(int id) throws BlogsException {
		PolicyBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_policy_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createPolicyBean(rs);
			}
		}
		catch (SQLException e) {
			System.err.println("static PolicyBean getBlogById(int id) throws BlogsException " + e);
			throw new BlogsException("Can not extract Policy from DB: " + e);
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
		return mm;
	}
	public static void updatePolicy(PolicyBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.web_update_system_pkg.update_policy(?,?,?,?,?,?,?,?); end;");
			stat.setInt(1, ebean.getPolicyCategory().getValue());
			stat.setInt(2, ebean.getPolicyStatus().getValue());
			stat.setString(3, ebean.getPolicyNumber());
			stat.setString(4, ebean.getPolicyTitle());
			stat.setString(5, ebean.getPolicyDocumentation());
			stat.setString(6, ebean.getPolicyAdminDoc());
			stat.setString(7, ebean.getAddedBy());
			stat.setInt(8, ebean.getId());
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void int updatePolicy(PolicyBean ebean) " + e);
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
	public static void deletePolicy(Integer tid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.web_update_system_pkg.delete_policy(?); end;");
			stat.setInt(1, tid);
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("deletePolicy(Integer tid) " + e);
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
			abean.setPolicyCategory(PolicyCategory.get(rs.getInt("POLICY_CATEGORY")));
			abean.setPolicyStatus(PolicyStatus.get(rs.getInt("POLICY_STATUS")));
			abean.setPolicyNumber(rs.getString("POLICY_NUMBER"));
			abean.setPolicyTitle(rs.getString("POLICY_TITLE"));
			abean.setPolicyDocumentation(rs.getString("POLICY_DOCUMENTATION"));
			abean.setPolicyAdminDoc(rs.getString("POLICY_ADMIN_DOC"));
			abean.setAddedBy(rs.getString("ADDED_BY"));
			abean.setDateAdded(new java.util.Date(rs.getTimestamp("DATE_ADDED").getTime()));
			abean.setOtherPolicyFiles(PolicyFileManager.getPoliciesFiles(abean.getId()));
		}
		catch (SQLException e) {
			abean = null;
		} catch (PoliciesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return abean;
	}	
}
