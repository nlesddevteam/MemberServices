package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.InterviewSummaryBean;
import com.esdnl.personnel.jobs.bean.InterviewSummaryScoreBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class InterviewSummaryManager {

	public static InterviewSummaryBean addInterviewSummaryBean(InterviewSummaryBean abean)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.add_interview_summary(?,?,?,?,?); end;");

			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2, abean.getCandidate().getUID());
			stat.setString(3, abean.getCompetition().getCompetitionNumber());
			stat.setString(4, abean.getStrengths());
			stat.setString(5, abean.getGaps());
			stat.setInt(6, abean.getRecommendation().getValue());

			stat.execute();

			int id = stat.getInt(1);

			stat.close();

			if (id > 0) {
				abean.setInterviewSummaryId(id);

				stat = con.prepareCall(
						"begin ? := awsd_user.personnel_jobs_pkg.add_interview_summary_score(?,?,?,?,?,?,?,?,?,?,?,?); end;");

				for (InterviewSummaryScoreBean iss : abean.getInterviewSummaryScoreBeans()) {
					iss.setInterviewSummaryId(abean.getInterviewSummaryId());

					stat.clearParameters();

					stat.registerOutParameter(1, OracleTypes.NUMBER);
					stat.setInt(2, iss.getInterviewSummaryId());
					stat.setString(3, iss.getInterviewer());
					stat.setDouble(4, iss.getScore1());
					stat.setDouble(5, iss.getScore2());
					stat.setDouble(6, iss.getScore3());
					stat.setDouble(7, iss.getScore4());
					stat.setDouble(8, iss.getScore5());
					stat.setDouble(9, iss.getScore6());
					stat.setDouble(10, iss.getScore7());
					stat.setDouble(11, iss.getScore8());
					stat.setDouble(12, iss.getScore9());
					stat.setDouble(13, iss.getScore10());

					stat.execute();

					iss.setInterviewSummaryScoreId(stat.getInt(1));
				}

				con.commit();
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println(
					"InterviewSummaryBean InterviewSummaryManager.addInterviewSummaryBean(InterviewSummaryBean abean): " + e);
			throw new JobOpportunityException("Can not add InterviewSummaryBean to DB.", e);
		}
		finally {
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}

		return abean;
	}

	public static InterviewSummaryBean updateInterviewSummaryBean(InterviewSummaryBean abean)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.mod_interview_summary(?,?,?,?); end;");

			stat.setInt(1, abean.getInterviewSummaryId());
			stat.setString(2, abean.getStrengths());
			stat.setString(3, abean.getGaps());
			stat.setInt(4, abean.getRecommendation().getValue());

			stat.execute();
			stat.close();

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.del_interview_summary_scores(?); end;");

			stat.setInt(1, abean.getInterviewSummaryId());

			stat.execute();
			stat.close();

			stat = con.prepareCall(
					"begin ? := awsd_user.personnel_jobs_pkg.add_interview_summary_score(?,?,?,?,?,?,?,?,?,?,?,?); end;");

			for (InterviewSummaryScoreBean iss : abean.getInterviewSummaryScoreBeans()) {
				iss.setInterviewSummaryId(abean.getInterviewSummaryId());

				stat.clearParameters();

				stat.registerOutParameter(1, OracleTypes.NUMBER);
				stat.setInt(2, iss.getInterviewSummaryId());
				stat.setString(3, iss.getInterviewer());
				stat.setDouble(4, iss.getScore1());
				stat.setDouble(5, iss.getScore2());
				stat.setDouble(6, iss.getScore3());
				stat.setDouble(7, iss.getScore4());
				stat.setDouble(8, iss.getScore5());
				stat.setDouble(9, iss.getScore6());
				stat.setDouble(10, iss.getScore7());
				stat.setDouble(11, iss.getScore8());
				stat.setDouble(12, iss.getScore9());
				stat.setDouble(13, iss.getScore10());

				stat.execute();

				iss.setInterviewSummaryScoreId(stat.getInt(1));
			}

			con.commit();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("InterviewSummaryBean updateInterviewSummaryBean(InterviewSummaryBean abean): " + e);
			throw new JobOpportunityException("Can not update InterviewSummaryBean to DB.", e);
		}
		finally {
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}

		return abean;
	}

	public static InterviewSummaryBean getInterviewSummaryBean(int summaryId) throws JobOpportunityException {

		InterviewSummaryBean summary = null;
		InterviewSummaryScoreBean summaryScore = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_interview_summary(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, summaryId);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if ((summary == null) || (summary.getInterviewSummaryId() != rs.getInt("summary_id"))) {
					summary = createInterviewSummaryBean(rs);
				}

				summaryScore = createInterviewSummaryScoreBean(rs);
				if (summaryScore != null && summaryScore.getInterviewSummaryId() == summary.getInterviewSummaryId()) {
					summary.addInterviewSummaryScoreBean(summaryScore);
				}
			}
		}
		catch (SQLException e) {
			System.err.println("InterviewSummaryBean getInterviewSummaryBean(int summaryId): " + e);
			throw new JobOpportunityException("Can not extract InterviewSummaryBean from DB.", e);
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

		return summary;
	}

	public static InterviewSummaryBean getInterviewSummaryBean(ApplicantProfileBean profile, JobOpportunityBean job)
			throws JobOpportunityException {

		InterviewSummaryBean summary = null;
		InterviewSummaryScoreBean summaryScore = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_interview_summary(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, profile.getUID());
			stat.setString(3, job.getCompetitionNumber());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if ((summary == null) || (summary.getInterviewSummaryId() != rs.getInt("summary_id"))) {
					summary = createInterviewSummaryBean(rs);
				}

				summaryScore = createInterviewSummaryScoreBean(rs);
				if (summaryScore != null && summaryScore.getInterviewSummaryId() == summary.getInterviewSummaryId()) {
					summary.addInterviewSummaryScoreBean(summaryScore);
				}
			}
		}
		catch (SQLException e) {
			System.err.println(
					"InterviewSummaryBean getInterviewSummaryBean(ApplicantProfileBean profile, JobOpportunityBean job): " + e);
			throw new JobOpportunityException("Can not extract InterviewSummaryBean from DB.", e);
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

		return summary;

	}

	public static Collection<InterviewSummaryBean> getInterviewSummaryBeans(JobOpportunityBean job)
			throws JobOpportunityException {

		Collection<InterviewSummaryBean> summaries = null;
		InterviewSummaryBean summary = null;
		InterviewSummaryScoreBean summaryScore = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			summaries = new ArrayList<InterviewSummaryBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_interview_summaries_by_job(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, job.getCompetitionNumber());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if ((summary == null) || (summary.getInterviewSummaryId() != rs.getInt("summary_id"))) {
					summary = createInterviewSummaryBean(rs);

					summaries.add(summary);
				}

				summaryScore = createInterviewSummaryScoreBean(rs);
				if (summaryScore != null && summaryScore.getInterviewSummaryId() == summary.getInterviewSummaryId()) {
					summary.addInterviewSummaryScoreBean(summaryScore);
				}
			}
		}
		catch (SQLException e) {
			System.err.println("Collection<InterviewSummaryBean> getInterviewSummaryBean(JobOpportunityBean job): " + e);
			throw new JobOpportunityException("Can not extract InterviewSummaryBean from DB.", e);
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

		return summaries;
	}

	public static Map<String, InterviewSummaryBean> getInterviewSummaryBeansMap(JobOpportunityBean job)
			throws JobOpportunityException {

		Map<String, InterviewSummaryBean> map = new HashMap<String, InterviewSummaryBean>();

		for (InterviewSummaryBean isb : getInterviewSummaryBeans(job)) {
			map.put(isb.getCandidate().getUID(), isb);
		}

		return map;
	}

	public static Collection<InterviewSummaryBean> getInterviewSummaryBeansByShortlist(JobOpportunityBean job)
			throws JobOpportunityException {

		Collection<InterviewSummaryBean> summaries = null;
		InterviewSummaryBean summary = null;
		InterviewSummaryScoreBean summaryScore = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			summaries = new ArrayList<InterviewSummaryBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_interview_sums_shortlisted(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, job.getCompetitionNumber());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if ((summary == null) || (summary.getInterviewSummaryId() != rs.getInt("summary_id"))) {
					summary = createInterviewSummaryBean(rs);

					summaries.add(summary);
				}

				summaryScore = createInterviewSummaryScoreBean(rs);
				if (summaryScore != null && summaryScore.getInterviewSummaryId() == summary.getInterviewSummaryId()) {
					summary.addInterviewSummaryScoreBean(summaryScore);
				}
			}
		}
		catch (SQLException e) {
			System.err.println(
					"Collection<InterviewSummaryBean> getInterviewSummaryBeansByShortlist(JobOpportunityBean job): " + e);
			throw new JobOpportunityException("Can not extract InterviewSummaryBean from DB.", e);
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

		return summaries;
	}

	public static Collection<InterviewSummaryBean> getTLAInterviewSummaryBeansByShortlist(JobOpportunityBean job)
			throws JobOpportunityException {

		Collection<InterviewSummaryBean> summaries = null;
		InterviewSummaryBean summary = null;
		InterviewSummaryScoreBean summaryScore = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			summaries = new ArrayList<InterviewSummaryBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_tla_interview_sums_sl(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, job.getCompetitionNumber());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if ((summary == null) || (summary.getInterviewSummaryId() != rs.getInt("summary_id"))) {
					summary = createInterviewSummaryBean(rs);

					summaries.add(summary);
				}

				summaryScore = createInterviewSummaryScoreBean(rs);
				if (summaryScore != null && summaryScore.getInterviewSummaryId() == summary.getInterviewSummaryId()) {
					summary.addInterviewSummaryScoreBean(summaryScore);
				}
			}
		}
		catch (SQLException e) {
			System.err.println(
					"Collection<InterviewSummaryBean> getInterviewSummaryBeansByShortlist(JobOpportunityBean job): " + e);
			throw new JobOpportunityException("Can not extract InterviewSummaryBean from DB.", e);
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

		return summaries;
	}

	public static Map<String, ArrayList<InterviewSummaryBean>> getInterviewSummaryBeansMapByShortlist(JobOpportunityBean job)
			throws JobOpportunityException {

		Map<String, ArrayList<InterviewSummaryBean>> map = new HashMap<String, ArrayList<InterviewSummaryBean>>();

		for (InterviewSummaryBean isb : getInterviewSummaryBeansByShortlist(job)) {
			if (!map.containsKey(isb.getCandidate().getUID())) {
				map.put(isb.getCandidate().getUID(), new ArrayList<InterviewSummaryBean>());
			}

			((ArrayList<InterviewSummaryBean>) map.get(isb.getCandidate().getUID())).add(isb);
		}

		return map;
	}

	public static Map<String, ArrayList<InterviewSummaryBean>> getTLAInterviewSummaryBeansMapByShortlist(JobOpportunityBean job)
			throws JobOpportunityException {

		Map<String, ArrayList<InterviewSummaryBean>> map = new HashMap<String, ArrayList<InterviewSummaryBean>>();

		for (InterviewSummaryBean isb : getTLAInterviewSummaryBeansByShortlist(job)) {
			if (!map.containsKey(isb.getCandidate().getUID())) {
				map.put(isb.getCandidate().getUID(), new ArrayList<InterviewSummaryBean>());
			}

			((ArrayList<InterviewSummaryBean>) map.get(isb.getCandidate().getUID())).add(isb);
		}

		return map;
	}

	public static Collection<InterviewSummaryBean> getInterviewSummaryBeans(ApplicantProfileBean profile)
			throws JobOpportunityException {

		Collection<InterviewSummaryBean> summaries = null;
		InterviewSummaryBean summary = null;
		InterviewSummaryScoreBean summaryScore = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			summaries = new ArrayList<InterviewSummaryBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_interview_summaries_by_app(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, profile.getUID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if ((summary == null) || (summary.getInterviewSummaryId() != rs.getInt("summary_id"))) {
					summary = createInterviewSummaryBean(rs);

					summaries.add(summary);
				}

				summaryScore = createInterviewSummaryScoreBean(rs);
				if (summaryScore != null && summaryScore.getInterviewSummaryId() == summary.getInterviewSummaryId()) {
					summary.addInterviewSummaryScoreBean(summaryScore);
				}
			}
		}
		catch (SQLException e) {
			System.err.println(
					"Collection<InterviewSummaryBean> getInterviewSummaryBean(ApplicantProfileBean profile): " + e);
			throw new JobOpportunityException("Can not extract InterviewSummaryBean from DB.", e);
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

		return summaries;
	}

	public static Map<String, InterviewSummaryBean> getInterviewSummaryBeansMap(ApplicantProfileBean profile)
			throws JobOpportunityException {

		Map<String, InterviewSummaryBean> map = new HashMap<String, InterviewSummaryBean>();

		for (InterviewSummaryBean isb : getInterviewSummaryBeans(profile)) {
			map.put(isb.getCompetition().getCompetitionNumber(), isb);
		}

		return map;
	}

	public static Collection<InterviewSummaryBean> getPoolInterviewSummaryBeans(ApplicantProfileBean profile)
			throws JobOpportunityException {

		Collection<InterviewSummaryBean> summaries = null;
		InterviewSummaryBean summary = null;
		InterviewSummaryScoreBean summaryScore = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			summaries = new ArrayList<InterviewSummaryBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_pool_interview_summaries(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, profile.getUID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if ((summary == null) || (summary.getInterviewSummaryId() != rs.getInt("summary_id"))) {
					summary = createInterviewSummaryBean(rs);

					summaries.add(summary);
				}

				summaryScore = createInterviewSummaryScoreBean(rs);
				if (summaryScore != null && summaryScore.getInterviewSummaryId() == summary.getInterviewSummaryId()) {
					summary.addInterviewSummaryScoreBean(summaryScore);
				}
			}
		}
		catch (SQLException e) {
			System.err.println(
					"Collection<InterviewSummaryBean> getPoolInterviewSummaryBeans(ApplicantProfileBean profile): " + e);
			throw new JobOpportunityException("Can not extract InterviewSummaryBean from DB.", e);
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

		return summaries;
	}

	public static InterviewSummaryBean createInterviewSummaryBean(ResultSet rs) {

		InterviewSummaryBean abean = null;
		try {
			abean = new InterviewSummaryBean();

			abean.setInterviewSummaryId(rs.getInt("summary_id"));
			abean.setCandidate(ApplicantProfileManager.createApplicantProfileBean(rs));
			abean.setCompetition(JobOpportunityManager.createJobOpportunityBean(rs));
			abean.setStrengths(rs.getString("strengths"));
			abean.setGaps(rs.getString("gaps"));
			abean.setRecommendation(InterviewSummaryBean.SummaryRecommendation.get(rs.getInt("recommendation")));
			abean.setCreated(new java.util.Date(rs.getTimestamp("created").getTime()));
		}
		catch (Exception e) {
			abean = null;
		}

		return abean;
	}

	public static InterviewSummaryScoreBean createInterviewSummaryScoreBean(ResultSet rs) {

		InterviewSummaryScoreBean abean = null;
		try {
			abean = new InterviewSummaryScoreBean();

			abean.setInterviewSummaryScoreId(rs.getInt("scores_id"));
			abean.setInterviewSummaryId(rs.getInt("summary_id"));
			abean.setInterviewer(rs.getString("interviewer"));
			abean.setScore1(rs.getDouble("score_1"));
			abean.setScore2(rs.getDouble("score_2"));
			abean.setScore3(rs.getDouble("score_3"));
			abean.setScore4(rs.getDouble("score_4"));
			abean.setScore5(rs.getDouble("score_5"));
			abean.setScore6(rs.getDouble("score_6"));
			abean.setScore7(rs.getDouble("score_7"));
			abean.setScore8(rs.getDouble("score_8"));
			abean.setScore9(rs.getDouble("score_9"));
			abean.setScore10(rs.getDouble("score_10"));
		}
		catch (Exception e) {
			abean = null;
		}

		return abean;
	}
}
