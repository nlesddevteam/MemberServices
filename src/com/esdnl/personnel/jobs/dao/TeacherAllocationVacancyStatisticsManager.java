package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.awsd.school.SchoolException;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.TeacherAllocationVacancyStatisticsBean;
import com.nlesd.school.bean.SchoolZoneBean;
import com.nlesd.school.service.SchoolZoneService;

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

	public static Map<SchoolZoneBean, TeacherAllocationVacancyStatisticsBean> getVacancyStatsByRegion(String schoolYear)
			throws JobOpportunityException {

		Map<SchoolZoneBean, TeacherAllocationVacancyStatisticsBean> stats = new HashMap<>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_2_pkg.get_vac_stats_by_region(?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, schoolYear);

			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				stats.put(SchoolZoneService.getSchoolZoneBean(rs.getInt("ZONE_ID")),
						createTeacherAllocationVacancyStatisticsBean(rs));
			}
		}
		catch (SQLException | SchoolException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println(
					"Map<SchoolZoneBean, TeacherAllocationVacancyStatisticsBean> getVacancyStatsByRegion(String schoolYear): "
							+ e);
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

	public static Collection<JobOpportunityBean> getVacanciesWithNoShortlist(String schoolYear, SchoolZoneBean zone)
			throws JobOpportunityException {

		Collection<JobOpportunityBean> vacancies = new ArrayList<>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_2_pkg.get_vac_no_sl(?,?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, schoolYear);
			stat.setInt(3, zone.getZoneId());

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

			System.err.println(
					"Collection<JobOpportunityBean> getVacanciesWithNoShortlist(String schoolYear, int zoneId): " + e);
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

	public static Collection<JobOpportunityBean> getVacanciesShortlistedWithNoRecommendation(	String schoolYear,
																																														SchoolZoneBean zone)
			throws JobOpportunityException {

		Collection<JobOpportunityBean> vacancies = new ArrayList<>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_2_pkg.get_vac_sl_no_rec(?,?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, schoolYear);
			stat.setInt(3, zone.getZoneId());

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

			System.err.println(
					"Collection<JobOpportunityBean> getVacanciesShortlistedWithNoRecommendation(	String schoolYear, SchoolZoneBean zone): "
							+ e);
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

	public static Collection<JobOpportunityBean> getVacanciesWithExpiredOffer(String schoolYear, SchoolZoneBean zone)
			throws JobOpportunityException {

		Collection<JobOpportunityBean> vacancies = new ArrayList<>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_2_pkg.get_vac_offer_expired(?,?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, schoolYear);
			stat.setInt(3, zone.getZoneId());

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

			System.err.println(
					"Collection<JobOpportunityBean> getVacanciesWithExpiredOffer(	String schoolYear, SchoolZoneBean zone): " + e);
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

	public static Collection<JobOpportunityBean> getVacanciesWithRejectedOffer(String schoolYear, SchoolZoneBean zone)
			throws JobOpportunityException {

		Collection<JobOpportunityBean> vacancies = new ArrayList<>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_2_pkg.get_vac_offer_rejected(?,?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, schoolYear);
			stat.setInt(3, zone.getZoneId());

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

			System.err.println(
					"Collection<JobOpportunityBean> getVacanciesWithRejectedOffer(String schoolYear, SchoolZoneBean zone): " + e);
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

	public static Collection<JobOpportunityBean> getVacanciesWithAcceptedOffer(String schoolYear, SchoolZoneBean zone)
			throws JobOpportunityException {

		Collection<JobOpportunityBean> vacancies = new ArrayList<>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_2_pkg.get_vac_offer_accepted(?,?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, schoolYear);
			stat.setInt(3, zone.getZoneId());

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

			System.err.println(
					"Collection<JobOpportunityBean> getVacanciesWithAcceptedOffer(String schoolYear, SchoolZoneBean zone): " + e);
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

	public static Collection<JobOpportunityBean> getVacanciesOffered(String schoolYear, SchoolZoneBean zone)
			throws JobOpportunityException {

		Collection<JobOpportunityBean> vacancies = new ArrayList<>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_2_pkg.get_vac_offered(?,?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, schoolYear);
			stat.setInt(3, zone.getZoneId());

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

			System.err.println(
					"Collection<JobOpportunityBean> getVacanciesOffered(String schoolYear, SchoolZoneBean zone): " + e);
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

	public static Collection<JobOpportunityBean> getVacanciesWithRecommendationAccepted(String schoolYear,
																																											SchoolZoneBean zone)
			throws JobOpportunityException {

		Collection<JobOpportunityBean> vacancies = new ArrayList<>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_2_pkg.get_vac_rec_accepted(?,?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, schoolYear);
			stat.setInt(3, zone.getZoneId());

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

			System.err.println(
					"Collection<JobOpportunityBean> getVacanciesWithRecommendationAccepted(String schoolYear, SchoolZoneBean zone): "
							+ e);
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

	public static Collection<JobOpportunityBean> getVacanciesWithRecommendationApproved(String schoolYear,
																																											SchoolZoneBean zone)
			throws JobOpportunityException {

		Collection<JobOpportunityBean> vacancies = new ArrayList<>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_2_pkg.get_vac_rec_approved(?,?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, schoolYear);
			stat.setInt(3, zone.getZoneId());

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

			System.err.println(
					"Collection<JobOpportunityBean> getVacanciesWithRecommendationApproved(String schoolYear, SchoolZoneBean zone): "
							+ e);
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

	public static Collection<JobOpportunityBean> getVacanciesWithRecommendationSubmitted(	String schoolYear,
																																												SchoolZoneBean zone)
			throws JobOpportunityException {

		Collection<JobOpportunityBean> vacancies = new ArrayList<>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_2_pkg.get_vac_rec_submitted(?,?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, schoolYear);
			stat.setInt(3, zone.getZoneId());

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

			System.err.println(
					"Collection<JobOpportunityBean> getVacanciesWithRecommendationSubmitted(String schoolYear, SchoolZoneBean zone): "
							+ e);
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

	public static Collection<JobOpportunityBean> getVacanciesWithRecommendationRejected(String schoolYear,
																																											SchoolZoneBean zone)
			throws JobOpportunityException {

		Collection<JobOpportunityBean> vacancies = new ArrayList<>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_2_pkg.get_vac_rec_rejected(?,?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, schoolYear);
			stat.setInt(3, zone.getZoneId());

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

			System.err.println(
					"Collection<JobOpportunityBean> getVacanciesWithRecommendationRejected(	String schoolYear, SchoolZoneBean zone): "
							+ e);
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

	public static TeacherAllocationVacancyStatisticsBean createTeacherAllocationVacancyStatisticsBean(ResultSet rs) {

		TeacherAllocationVacancyStatisticsBean stats = null;

		try {
			stats = new TeacherAllocationVacancyStatisticsBean();

			stats.setSchoolYear(rs.getString("school_year"));
			stats.setTotalVacancies(rs.getInt("TOTAL_VACANCIES"));
			stats.setTotalAdApproved(rs.getInt("AD_APPROVED"));
			stats.setTotalAdSubmitted(rs.getInt("AD_SUBMITTED"));
			stats.setTotalAdPosted(rs.getInt("AD_POSTED"));
			stats.setTotalCompetitionsInProgress(rs.getInt("COMP_INPROGRESS"));
			stats.setTotalFilledByCompetition(rs.getInt("FILLED_COMP"));
			stats.setTotalFilledManually(rs.getInt("FILLED_MANUAL"));
			stats.setTotalNoAdCreated(rs.getInt("NO_AD_CREATED"));
			stats.setTotalRecommendationSubmitted(rs.getInt("REC_SUBMITTED"));
			stats.setTotalRecommendationRejected(rs.getInt("REC_REJECTED"));
			stats.setTotalRecommendationApproved(rs.getInt("REC_APPROVED"));
			stats.setTotalRecommendationAccepted(rs.getInt("REC_ACCEPTED"));
			stats.setTotalRecommendationOffered(rs.getInt("REC_OFFERED"));
			stats.setTotalShortlistNoRecemmendation(rs.getInt("SHORTLIST_NO_REC"));
			stats.setTotalRecommendationOfferRejected(rs.getInt("REC_OFFER_REJECTED"));
			stats.setTotalNoShortlist(rs.getInt("NO_SHORTLIST"));
			stats.setTotalRecommendationOfferExpired(rs.getInt("REC_OFFER_EXPIRED"));
			stats.setTotalRecommendationOfferAccepted(rs.getInt("REC_OFFER_ACCEPTED"));
		}
		catch (SQLException e) {
			stats = null;
		}

		return stats;
	}

}
