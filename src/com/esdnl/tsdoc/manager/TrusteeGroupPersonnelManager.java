package com.esdnl.tsdoc.manager;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.esdnl.dao.DAOUtils;
import com.esdnl.tsdoc.bean.TSDocException;
import com.esdnl.tsdoc.bean.TrusteeGroupBean;

public class TrusteeGroupPersonnelManager {

	public static void addTrusteeGroupPersonnelBean(TrusteeGroupBean group, Personnel personnel) throws TSDocException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.tsdoc_pkg.add_group_personnel(?,?); end;");

			stat.setInt(1, group.getGroupId());
			stat.setInt(2, personnel.getPersonnelID());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addTrusteeGroupPersonnelBean(TrusteeGroupBean group, Personnel personnel): " + e);
			throw new TSDocException("Can not add TrusteeGroupPersonnelBean to DB.", e);
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

	public static void deleteTrusteeGroupPersonnelBean(TrusteeGroupBean group, Personnel personnel) throws TSDocException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.tsdoc_pkg.del_group_personnel(?,?); end;");

			stat.setInt(1, group.getGroupId());
			stat.setInt(2, personnel.getPersonnelID());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void deleteTrusteeGroupPersonnelBean(TrusteeGroupBean group, Personnel personnel): " + e);
			throw new TSDocException("Can not delete TrusteeGroupPersonnelBean to DB.", e);
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

	public static Personnel[] getTrusteeGroupMembershipBean(TrusteeGroupBean group) throws TSDocException {

		Vector<Personnel> beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector<Personnel>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.tsdoc_pkg.get_group_membership(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, group.getGroupId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(PersonnelDB.createPersonnelBean(rs));
		}
		catch (SQLException e) {
			System.err.println("Personnel[] getTrusteeGroupMembershipBean(TrusteeGroupBean group): " + e);
			throw new TSDocException("Can not extract Personnel from DB.", e);
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

		return (Personnel[]) beans.toArray(new Personnel[0]);
	}

}
