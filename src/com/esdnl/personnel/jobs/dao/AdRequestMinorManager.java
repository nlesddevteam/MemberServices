package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.school.Subject;
import com.awsd.school.SubjectDB;
import com.awsd.school.SubjectException;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.AdRequestBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;

public class AdRequestMinorManager {

	public static Subject[] getAdRequestMinors(AdRequestBean req) throws JobOpportunityException {

		Vector beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_ad_request.get_ad_request_minors(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, req.getId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(SubjectDB.getSubject(rs.getInt("SUBJECT_ID")));
		}
		catch (SubjectException e) {
			System.err.println("Subject[] getAdRequestMajors(AdRequestBean req): " + e);
			throw new JobOpportunityException("Can not extract SubjectBeans from DB.", e);
		}
		catch (SQLException e) {
			System.err.println("Subject[] getAdRequestMajors(AdRequestBean req): " + e);
			throw new JobOpportunityException("Can not extract SubjectBeans from DB.", e);
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

		return (Subject[]) beans.toArray(new Subject[0]);
	}

	public static void addAdRequestMinors(AdRequestBean req) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;
		Subject[] minors = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.personnel_ad_request.add_request_minor(?,?); end;");

			minors = req.getMinors();
			for (int i = 0; i < minors.length; i++) {
				stat.clearParameters();

				stat.setInt(1, req.getId());
				stat.setInt(2, minors[i].getSubjectID());

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

			System.err.println("void addAdRequestMinors(AdRequestBean req): " + e);
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
	public static void deleteAdRequestMinorBeans(int requestid) throws JobOpportunityException {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);
			stat = con.prepareCall("begin awsd_user.personnel_ad_request.delete_ad_request_minors(?); end;");
			stat.setInt(1, requestid);
			stat.execute();
			}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void void deleteAdRequestinorBeans(int requestid): " + e);
			throw new JobOpportunityException("Can not delete AdRequestBean Minors", e);
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
