package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.AdRequestBean;
import com.esdnl.personnel.jobs.bean.DegreeBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;

public class AdRequestDegreeManager {

	public static DegreeBean[] getAdRequestDegrees(AdRequestBean req) throws JobOpportunityException {

		Vector beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_ad_request.get_ad_request_degrees(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, req.getId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(DegreeManager.getDegreeBeans(rs.getString("DEGREE_ID")));
		}
		catch (SQLException e) {
			System.err.println("DegreeBean[] getAdRequestDegrees(AdRequestBean req): " + e);
			throw new JobOpportunityException("Can not extract DegreeBeans from DB.", e);
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

		return (DegreeBean[]) beans.toArray(new DegreeBean[0]);
	}

	public static void addAdRequestDegreeBeans(AdRequestBean req) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;
		DegreeBean[] degrees = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.personnel_ad_request.add_request_degree(?,?); end;");

			degrees = req.getDegrees();
			for (int i = 0; i < degrees.length; i++) {
				stat.clearParameters();

				stat.setInt(1, req.getId());
				stat.setString(2, degrees[i].getAbbreviation());

				stat.execute();
			}

			con.commit();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addAdRequestDegreeBeans(AdRequestBean req): " + e);
			throw new JobOpportunityException("Can not add AdRequestBean to DB.", e);
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
}