package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.JobOpportunityAssignmentBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class JobOpportunityAssignmentManager {

	public static Vector<JobOpportunityAssignmentBean> getJobOpportunityAssignmentBeanCollection(JobOpportunityBean jBean)
			throws JobOpportunityException {

		Vector<JobOpportunityAssignmentBean> v_opps = null;
		JobOpportunityAssignmentBean aBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<JobOpportunityAssignmentBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_job_opp_assigns(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, jBean.getCompetitionNumber());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				aBean = createJobOpportunityAssignmentBean(rs);

				v_opps.add(aBean);
			}
		}
		catch (SQLException e) {
			System.err.println("JobOpportunityAssignmentManager.getJobOpportunityAssignmentBeans(JobOpportunityBean): " + e);
			throw new JobOpportunityException("Can not extract JobOpportunityAssignmentBean from DB.", e);
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

	public static JobOpportunityAssignmentBean[] getJobOpportunityAssignmentBeans(JobOpportunityBean jBean)
			throws JobOpportunityException {

		return (JobOpportunityAssignmentBean[]) getJobOpportunityAssignmentBeanCollection(jBean).toArray(
				new JobOpportunityAssignmentBean[0]);
	}

	public static JobOpportunityAssignmentBean createJobOpportunityAssignmentBean(ResultSet rs) {

		return createJobOpportunityAssignmentBean(rs, true);
	}

	public static JobOpportunityAssignmentBean createJobOpportunityAssignmentBean(ResultSet rs, boolean loadMetaData) {

		JobOpportunityAssignmentBean aBean = null;
		try {
			aBean = new JobOpportunityAssignmentBean(rs.getInt("ASSIGN_ID"), rs.getString("COMP_NUM"), rs.getInt(
					"LOCATION"), rs.getDouble("UNITS"));

			try {
				if (loadMetaData) {
					aBean.addRequiredEducation(AssignmentEducationManager.getAssignmentEducationBeanCollection(aBean));
					aBean.addRequiredMajor(AssignmentMajorMinorManager.getAssignmentMajorMinorBeanCollection(aBean));
					aBean.addRequiredTrainingMethod(
							AssignmentTrainingMethodManager.getAssignmentTrainingMethodBeanCollection(aBean));
				}
			}
			catch (JobOpportunityException e) {
				e.printStackTrace();
			}
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}
}
