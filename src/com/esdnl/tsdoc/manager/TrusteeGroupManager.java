package com.esdnl.tsdoc.manager;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.tsdoc.bean.TSDocException;
import com.esdnl.tsdoc.bean.TrusteeGroupBean;

public class TrusteeGroupManager {

	public static TrusteeGroupBean getTrusteeGroupBean(int groupId) throws TSDocException {

		TrusteeGroupBean bean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.tsdoc_pkg.get_trustee_group(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, groupId);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				bean = TrusteeGroupManager.createTrusteeGroupBean(rs);
		}
		catch (SQLException e) {
			System.err.println("TrusteeGroupBean getTrusteeGroupBean(int groupId): " + e);
			throw new TSDocException("Can not extract TrusteeGroupBean from DB.", e);
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

		return bean;
	}

	public static TrusteeGroupBean[] getTrusteeGroupBeans() throws TSDocException {

		Vector<TrusteeGroupBean> beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector<TrusteeGroupBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.tsdoc_pkg.get_trustee_groups; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(TrusteeGroupManager.createTrusteeGroupBean(rs));
		}
		catch (SQLException e) {
			System.err.println("TrusteeGroupBean[] getTrusteeGroupBeans(): " + e);
			throw new TSDocException("Can not extract TrusteeGroupBean from DB.", e);
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

		return (TrusteeGroupBean[]) beans.toArray(new TrusteeGroupBean[0]);
	}

	public static void addTrusteeGroupBean(TrusteeGroupBean abean) throws TSDocException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.tsdoc_pkg.add_trustee_group(?); end;");

			stat.setString(1, abean.getGroupName());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addTrusteeGroupBean(TrusteeGroupBean abean): " + e);
			throw new TSDocException("Can not add TrusteeGroupBean to DB.", e);
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

	public static void deleteTrusteeGroupBean(int groupId) throws TSDocException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.tsdoc_pkg.del_trustee_group(?); end;");

			stat.setInt(1, groupId);

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void deleteTrusteeGroupBean(int groupId): " + e);
			throw new TSDocException("Can not delete TrusteeGroupBean to DB.", e);
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

	public static TrusteeGroupBean createTrusteeGroupBean(ResultSet rs) {

		TrusteeGroupBean abean = null;

		try {
			abean = new TrusteeGroupBean();

			abean.setGroupId(rs.getInt("GROUP_ID"));
			abean.setGroupName(rs.getString("GROUP_NAME"));
		}
		catch (SQLException e) {
			e.printStackTrace();
			abean = null;
		}
		catch (Exception e) {
			e.printStackTrace();
			abean = null;
		}

		return abean;
	}
}
