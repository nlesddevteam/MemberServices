package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.TeacherAllocationVacancyStatisticsBean;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class TeacherAllocationVacancyStatisticsManager {

	public static TeacherAllocationVacancyStatisticsBean getVacancyStats(String schoolYear)
			throws JobOpportunityException {

		TeacherAllocationVacancyStatisticsBean stats = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_2_pkg.get_vacancies_stats(?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, schoolYear);

			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				stats = createTeacherAllocationVacancyStatisticsBean(rs);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("TeacherAllocationVacancyStatisticsBean getVacancyStats(String schoolYear): " + e);
			throw new JobOpportunityException("Can not add TeacherAllocationVacancyStatisticsBean to DB.", e);
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

	public static TeacherAllocationVacancyStatisticsBean createTeacherAllocationVacancyStatisticsBean(ResultSet rs) {

		TeacherAllocationVacancyStatisticsBean stats = null;

		try {
			stats = new TeacherAllocationVacancyStatisticsBean();

			stats.setSchoolYear(rs.getString("school_year"));
			stats.setTotalVacancies(rs.getInt("TOTAL_VACANCIES"));
			stats.setTotalAdApproved(rs.getInt("AD_APPROVED"));
			stats.setTotalAdSubmitted(rs.getInt("AD_SUBMITTED"));
			stats.setTotalAdPosted(rs.getInt("AD_POSTED"));
			stats.setTotalFilledByCompetition(rs.getInt("FILLED_COMP"));
			stats.setTotalFilledManually(rs.getInt("FILLED_MANUAL"));
			stats.setTotalNoAdCreated(rs.getInt("NO_AD_CREATED"));
			stats.setTotalRecommendationSubmitted(rs.getInt("REC_SUBMITTED"));
			stats.setTotalRecommendationApproved(rs.getInt("REC_APPROVED"));
			stats.setTotalRecommendationAccepted(rs.getInt("REC_ACCEPTED"));
			stats.setTotalRecommendationOffered(rs.getInt("REC_OFFERED"));
		}
		catch (SQLException e) {
			stats = null;
		}

		return stats;
	}

}
