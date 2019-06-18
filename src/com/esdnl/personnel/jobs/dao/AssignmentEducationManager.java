package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.AssignmentEducationBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityAssignmentBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;

public class AssignmentEducationManager {

	public static Vector getAssignmentEducationBeanCollection(JobOpportunityAssignmentBean aBean)
			throws JobOpportunityException {

		Vector v_opps = null;
		AssignmentEducationBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_assign_edu(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, aBean.getAssignmentId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createAssignmentEducationBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("AssignmentEducationManager.getAssignmentEducationBeans(JobOpportunityAssignemntBean): " + e);
			throw new JobOpportunityException("Can not extract AssignmentEducationBean from DB.", e);
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

	public static AssignmentEducationBean[] getAssignmentEducationBeans(JobOpportunityAssignmentBean aBean)
			throws JobOpportunityException {

		Vector v_opps = null;
		AssignmentEducationBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_assign_edu(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, aBean.getAssignmentId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createAssignmentEducationBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("AssignmentEducationManager.getAssignmentEducationBeans(JobOpportunityAssignemntBean): " + e);
			throw new JobOpportunityException("Can not extract AssignmentEducationBean from DB.", e);
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

		return (AssignmentEducationBean[]) v_opps.toArray(new AssignmentEducationBean[0]);
	}

	public static AssignmentEducationBean createAssignmentEducationBean(ResultSet rs) {

		AssignmentEducationBean aBean = null;
		try {
			aBean = new AssignmentEducationBean(rs.getInt("ASSIGN_ID"), rs.getString("DEGREE_ID"));
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}
}