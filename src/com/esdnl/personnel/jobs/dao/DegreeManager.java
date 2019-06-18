package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.DegreeBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;

public class DegreeManager {

	public static DegreeBean[] getDegreeBeans() throws JobOpportunityException {

		Vector v_opps = null;
		DegreeBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_degrees; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createDegreeBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("DegreeManager.getDegreeBeans(): " + e);
			throw new JobOpportunityException("Can not extract DegreeBean from DB.", e);
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

		return (DegreeBean[]) v_opps.toArray(new DegreeBean[0]);
	}

	public static DegreeBean getDegreeBeans(String deg_id) throws JobOpportunityException {

		DegreeBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_degree(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, deg_id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createDegreeBean(rs);
			else
				eBean = null;
		}
		catch (SQLException e) {
			System.err.println("DegreeManager.getDegreeBeans(): " + e);
			throw new JobOpportunityException("Can not extract DegreeBean from DB.", e);
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

	public static DegreeBean createDegreeBean(ResultSet rs) {

		DegreeBean aBean = null;
		try {
			aBean = new DegreeBean(rs.getString("DEGREE_ID"), rs.getString("TITLE"));
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}
}