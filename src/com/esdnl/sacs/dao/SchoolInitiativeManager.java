package com.esdnl.sacs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.esdnl.dao.DAOUtils;
import com.esdnl.sacs.model.bean.SACSException;
import com.esdnl.sacs.model.bean.SchoolInitiativeBean;

public class SchoolInitiativeManager {

	public static void addSchoolInitiativeBean(SchoolInitiativeBean abean) throws SACSException {

		Connection con = null;
		CallableStatement stat = null;
		int id = 0;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.sacs_pkg.add_school_initiative(?,?,?); end;");

			stat.setInt(1, abean.getSchool().getSchoolID());
			stat.setInt(2, abean.getInitiative().getId());
			stat.setString(3, abean.getDescription());

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

	public static HashMap getSchoolInitiativeBeans(School s) throws SACSException {

		HashMap v_opps = null;
		SchoolInitiativeBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new HashMap();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.sacs_pkg.get_school_initiatives(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, s.getSchoolID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createSchoolInitiativeBean(rs);

				v_opps.put(eBean.getInitiative(), eBean);
			}

		}
		catch (SQLException e) {
			System.err.println("HashMap getSchoolInitiativeBeans(School s): " + e);
			throw new SACSException("Can not extract SchoolInitiativeBeans from DB.", e);
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

		return v_opps;
	}

	public static SchoolInitiativeBean getSchoolInitiativeBean(int school_id, int initiative_id) throws SACSException {

		SchoolInitiativeBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.sacs_pkg.get_school_initiative(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, school_id);
			stat.setInt(3, initiative_id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createSchoolInitiativeBean(rs);

		}
		catch (SQLException e) {
			System.err.println("HashMap getSchoolInitiativeBeans(School s): " + e);
			throw new SACSException("Can not extract SchoolInitiativeBeans from DB.", e);
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

	public static SchoolInitiativeBean createSchoolInitiativeBean(ResultSet rs) {

		SchoolInitiativeBean abean = null;

		try {
			abean = new SchoolInitiativeBean();

			abean.setSchool(SchoolDB.createSchoolBean(rs));
			abean.setInitiative(InitiativeManager.createInitiativeBean(rs));
			abean.setDescription(rs.getString("DESCRIPTION"));
		}
		catch (SQLException e) {
			e.printStackTrace();
			abean = null;
		}

		return abean;
	}
}