package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.AssignmentMajorMinorBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityAssignmentBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;

public class AssignmentMajorMinorManager {

	public static AssignmentMajorMinorBean[] getAssignmentMajorMinorBeans(JobOpportunityAssignmentBean aBean)
			throws JobOpportunityException {

		Vector v_opps = null;
		AssignmentMajorMinorBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_assign_mjr_minr(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, aBean.getAssignmentId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createAssignmentMajorMinorBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("AssignmentMajorMinorManager.getAssignmentMajorMinorBeans(JobOpportunityAssignemntBean): " + e);
			throw new JobOpportunityException("Can not extract AssignmentMajorMinorBean from DB.", e);
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

		return (AssignmentMajorMinorBean[]) v_opps.toArray(new AssignmentMajorMinorBean[0]);
	}

	public static AssignmentMajorMinorBean createAssignmentMajorMinorBean(ResultSet rs) {

		AssignmentMajorMinorBean aBean = null;
		try {
			aBean = new AssignmentMajorMinorBean(rs.getInt("ASSIGN_ID"), rs.getInt("MAJOR_ID"), rs.getInt("MINOR_ID"));
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}
}