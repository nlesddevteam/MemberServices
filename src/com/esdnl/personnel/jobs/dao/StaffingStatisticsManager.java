package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.StaffingStatisticsBean;

public class StaffingStatisticsManager {

	public static StaffingStatisticsBean getStaffingStatisticsBean(Date searchDate) throws JobOpportunityException {

		StaffingStatisticsBean stats = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_staffing_stats(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setDate(2, new java.sql.Date(searchDate.getTime()));
			stat.execute();
			rs = (ResultSet) stat.getObject(1);

			if (rs.next()) {
				stats = createStaffingStatisticsBean(rs);
			}

		}
		catch (SQLException e) {
			System.err.println("StaffingStatisticsBean getStaffingStatisticsBean(Date searchDate): " + e);
			throw new JobOpportunityException("Can not extract StaffingStatisticsBean from DB.", e);
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

		return stats;
	}

	public static StaffingStatisticsBean createStaffingStatisticsBean(ResultSet rs) {

		StaffingStatisticsBean aBean = null;

		try {
			aBean = new StaffingStatisticsBean();

			aBean.setJobsPosted(rs.getInt("posted_jobs"));
			aBean.setApplicationsReceived(rs.getInt("job_applications"));
			aBean.setRecommendationsMade(rs.getInt("recommendations"));
			aBean.setOffersAccepted(rs.getInt("offers_accepted"));

		}
		catch (SQLException e) {
			aBean = null;
		}

		return aBean;
	}

}
