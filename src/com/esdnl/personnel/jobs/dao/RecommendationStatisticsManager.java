package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.RecommendationStatisticsBean;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class RecommendationStatisticsManager {

	public static RecommendationStatisticsBean getRecommendationStatisticsBean() throws JobOpportunityException {

		RecommendationStatisticsBean stats = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_2_pkg.get_rec_stats; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = (ResultSet) stat.getObject(1);

			if (rs.next()) {
				stats = createRecommendationStatisticsBean(rs);
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

	public static Collection<JobOpportunityBean> getRecommendationsWithOffersQueued() throws JobOpportunityException {

		Collection<JobOpportunityBean> vacancies = new ArrayList<>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_2_pkg.get_rec_stats_offer_queued; end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				vacancies.add(JobOpportunityManager.createJobOpportunityBean(rs));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("Collection<JobOpportunityBean> getRecommendationsWithOffersQueued(): " + e);
			throw new JobOpportunityException("Can not retrieve JobOpportunityBean to DB.", e);
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

		return vacancies;
	}

	public static Collection<JobOpportunityBean> getRecommendationsWithOffersInprogress() throws JobOpportunityException {

		Collection<JobOpportunityBean> vacancies = new ArrayList<>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_2_pkg.get_rec_stats_offer_inprogress; end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				vacancies.add(JobOpportunityManager.createJobOpportunityBean(rs));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("Collection<JobOpportunityBean> getRecommendationsWithOffersInprogress(): " + e);
			throw new JobOpportunityException("Can not retrieve JobOpportunityBean to DB.", e);
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

		return vacancies;
	}

	public static Collection<JobOpportunityBean> getRecommendationsWithOffersExpired() throws JobOpportunityException {

		Collection<JobOpportunityBean> vacancies = new ArrayList<>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_2_pkg.get_rec_stats_offer_expired; end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				vacancies.add(JobOpportunityManager.createJobOpportunityBean(rs));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("Collection<JobOpportunityBean> getRecommendationsWithOffersExpired(): " + e);
			throw new JobOpportunityException("Can not retrieve JobOpportunityBean to DB.", e);
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

		return vacancies;
	}

	public static RecommendationStatisticsBean createRecommendationStatisticsBean(ResultSet rs) {

		RecommendationStatisticsBean aBean = null;

		try {
			aBean = new RecommendationStatisticsBean();

			aBean.setOffersQueued(rs.getInt("offers_queued"));
			aBean.setOffersInProgress(rs.getInt("offers_inprogress"));
			aBean.setOffersExpired(rs.getInt("offers_expired"));

		}
		catch (SQLException e) {
			aBean = null;
		}

		return aBean;
	}

}
