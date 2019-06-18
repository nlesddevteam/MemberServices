package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.AssignmentTrainingMethodBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityAssignmentBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.constants.TrainingMethodConstant;

public class AssignmentTrainingMethodManager {

	public static AssignmentTrainingMethodBean[] getAssignmentTrainingMethodBeans(JobOpportunityAssignmentBean aBean)
			throws JobOpportunityException {

		Vector v_opps = null;
		AssignmentTrainingMethodBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_assign_trnmthd(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, aBean.getAssignmentId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createAssignmentTrainingMethodBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("AssignmentTrainingMethodManager.getAssignmentTrainingMethodBeans(JobOpportunityAssignemntBean): "
					+ e);
			throw new JobOpportunityException("Can not extract AssignmentTrainingMethodBean from DB.", e);
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

		return (AssignmentTrainingMethodBean[]) v_opps.toArray(new AssignmentTrainingMethodBean[0]);
	}

	public static AssignmentTrainingMethodBean createAssignmentTrainingMethodBean(ResultSet rs) {

		AssignmentTrainingMethodBean aBean = null;
		try {
			aBean = new AssignmentTrainingMethodBean(rs.getInt("ASSIGN_ID"), TrainingMethodConstant.get(rs.getInt("TRNMTHD_ID")));
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}
}