package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.AssignmentSkillBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityAssignmentBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;

public class AssignmentSkillManager {

	public static AssignmentSkillBean[] getAssignmentSkillBeans(JobOpportunityAssignmentBean aBean)
			throws JobOpportunityException {

		Vector v_opps = null;
		AssignmentSkillBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_assign_skills(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(1, aBean.getAssignmentId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createAssignmentSkillBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("AssignmentSkillManager.getAssignmentSkillBeans(JobOpportunityAssignemntBean): " + e);
			throw new JobOpportunityException("Can not extract AssignmentSkillBean from DB.", e);
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

		return (AssignmentSkillBean[]) v_opps.toArray(new AssignmentSkillBean[0]);
	}

	public static AssignmentSkillBean createAssignmentSkillBean(ResultSet rs) {

		AssignmentSkillBean aBean = null;
		try {
			aBean = new AssignmentSkillBean(rs.getInt("ASSIGN_ID"), rs.getInt("SKILLS_ID"));
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}
}