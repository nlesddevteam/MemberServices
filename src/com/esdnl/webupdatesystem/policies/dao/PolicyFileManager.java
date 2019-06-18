package com.esdnl.webupdatesystem.policies.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.webupdatesystem.policies.bean.PoliciesException;
import com.esdnl.webupdatesystem.policies.bean.PolicyFileBean;
public class PolicyFileManager {
	public static int addPolicyFile(PolicyFileBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.add_new_policies_file(?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setString(2, ebean.getPfTitle());
			stat.setString(3, ebean.getPfDoc());
			stat.setString(4, ebean.getAddedBy());
			stat.setInt(5, ebean.getPolicyId());
			stat.execute();
			id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("int addPolicyFile(PolicyFileBean ebean)" + e);
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
	public static ArrayList<PolicyFileBean> getPoliciesFiles(Integer policyid) throws PoliciesException {
		ArrayList<PolicyFileBean> mms = null;
		PolicyFileBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			mms = new ArrayList<PolicyFileBean>();
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_policies_files(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, policyid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createPolicyFileBean(rs);
				mms.add(mm);
			}
		}
		catch (SQLException e) {
			System.err.println("Vector getPoliciesFiles() throws PoliciesException " + e);
			throw new PoliciesException("Can not extract Policies Files from DB: " + e);
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
	public static void deletePolicyFile(Integer tid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.web_update_system_pkg.delete_other_policy_file(?); end;");
			stat.setInt(1, tid);
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("deletePolicyFile(Integer tid) " + e);
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
	public static PolicyFileBean createPolicyFileBean(ResultSet rs) {
		PolicyFileBean abean = null;
		try {
			abean = new PolicyFileBean();
			abean.setId(rs.getInt("ID"));
			abean.setPfTitle(rs.getString("PF_TITLE"));
			abean.setPfDoc(rs.getString("PF_DOC"));
			abean.setAddedBy(rs.getString("ADDED_BY"));
			abean.setDateAdded(new java.util.Date(rs.getTimestamp("DATE_ADDED").getTime()));
			abean.setPolicyId(rs.getInt("POLICY_ID"));
		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}
}
