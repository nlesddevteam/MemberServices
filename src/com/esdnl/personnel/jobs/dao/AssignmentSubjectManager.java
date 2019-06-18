package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.AssignmentSubjectBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityAssignmentBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;

public class AssignmentSubjectManager {

	public static AssignmentSubjectBean[] getAssignmentSubjectBeans(JobOpportunityAssignmentBean aBean)
			throws JobOpportunityException {

		Vector v_opps = null;
		AssignmentSubjectBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_assign_subject(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(1, aBean.getAssignmentId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createAssignmentSubjectBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("AssignmentSubjectManager.getAssignmentSubjectBeans(JobOpportunityAssignemntBean): " + e);
			throw new JobOpportunityException("Can not extract AssignmentSubjectBean from DB.", e);
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

		return (AssignmentSubjectBean[]) v_opps.toArray(new AssignmentSubjectBean[0]);
	}

	public static AssignmentSubjectBean createAssignmentSubjectBean(ResultSet rs) {

		AssignmentSubjectBean aBean = null;
		try {
			aBean = new AssignmentSubjectBean(rs.getInt("ASSIGN_ID"), rs.getInt("SUBJECT_ID"));
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}
}