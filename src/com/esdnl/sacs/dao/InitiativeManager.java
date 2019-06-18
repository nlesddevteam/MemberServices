package com.esdnl.sacs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.sacs.model.bean.InitiativeBean;
import com.esdnl.sacs.model.bean.SACSException;

public class InitiativeManager {

	public static void addInitiativeBean(InitiativeBean abean) throws SACSException {

		Connection con = null;
		CallableStatement stat = null;
		int id = 0;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.sacs_pkg.add_initiative(?,?); end;");

			stat.setString(1, abean.getName());
			stat.setString(2, abean.getDescription());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addInitiativeBean(InitiativeBean abean): " + e);
			throw new SACSException("Can not add initiative to DB.", e);
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

	public static InitiativeBean[] getInitiativeBeans() throws SACSException {

		Vector v_opps = null;
		InitiativeBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.sacs_pkg.get_initiatives; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createInitiativeBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("InitiativeBean[] getInitiativeBeanBeans(): " + e);
			throw new SACSException("Can not extract InitiativeBean from DB.", e);
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

		return (InitiativeBean[]) v_opps.toArray(new InitiativeBean[0]);
	}

	public static InitiativeBean getInitiativeBean(int id) throws SACSException {

		Vector v_opps = null;
		InitiativeBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.sacs_pkg.get_initiative(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createInitiativeBean(rs);

		}
		catch (SQLException e) {
			System.err.println("InitiativeBean[] getInitiativeBeanBeans(): " + e);
			throw new SACSException("Can not extract InitiativeBean from DB.", e);
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

		return eBean;
	}

	public static InitiativeBean createInitiativeBean(ResultSet rs) {

		InitiativeBean abean = null;

		try {
			abean = new InitiativeBean();

			abean.setId(rs.getInt("ID"));
			abean.setName(rs.getString("NAME"));
			abean.setDescription(rs.getString("DESCRIPTION"));

		}
		catch (SQLException e) {
			e.printStackTrace();
			abean = null;
		}

		return abean;
	}
}