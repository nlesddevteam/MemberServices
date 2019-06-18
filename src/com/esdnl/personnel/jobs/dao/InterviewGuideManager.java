package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.InterviewGuideBean;
import com.esdnl.personnel.jobs.bean.InterviewGuideQuestionBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;

public class InterviewGuideManager {

	public static InterviewGuideBean addInterviewGuideBean(InterviewGuideBean abean) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.add_interview_guide(?,?,?,?,?,?); end;");

			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2, abean.getTitle());
			stat.setInt(3, abean.getRatingScaleBottom());
			stat.setInt(4, abean.getRatingScaleTop());
			stat.setString(5,abean.getSchoolYear());
			stat.setBoolean(6, abean.getActiveList());
			stat.setString(7, abean.getGuideType());

			stat.execute();

			int id = stat.getInt(1);

			stat.close();

			if (id > 0) {
				abean.setGuideId(id);

				stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.add_interview_guide_question(?,?,?); end;");

				for (InterviewGuideQuestionBean q : abean.getQuestions()) {
					q.setGuideId(abean.getGuideId());

					stat.clearParameters();

					stat.registerOutParameter(1, OracleTypes.NUMBER);
					stat.setInt(2, q.getGuideId());
					stat.setString(3, q.getQuestion());
					stat.setDouble(4, q.getWeight());

					stat.execute();

					q.setQuestionId(stat.getInt(1));
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

			System.err.println("InterviewGuideBean addInterviewGuideBean(InterviewGuideBean abean): " + e);
			throw new JobOpportunityException("Can not add InterviewGuideBean to DB.", e);
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

	public static InterviewGuideBean updateInterviewGuideBean(InterviewGuideBean abean) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.mod_interview_guide(?,?,?,?,?,?,?); end;");

			stat.setInt(1, abean.getGuideId());
			stat.setString(2, abean.getTitle());
			stat.setInt(3, abean.getRatingScaleBottom());
			stat.setInt(4, abean.getRatingScaleTop());
			stat.setString(5,abean.getSchoolYear());
			stat.setBoolean(6, abean.getActiveList());
			stat.setString(7,abean.getGuideType());
			stat.execute();
			stat.close();

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.del_interview_guide_questions(?); end;");

			stat.setInt(1, abean.getGuideId());

			stat.execute();
			stat.close();

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.add_interview_guide_question(?,?,?); end;");

			for (InterviewGuideQuestionBean q : abean.getQuestions()) {
				q.setGuideId(abean.getGuideId());

				stat.clearParameters();

				stat.registerOutParameter(1, OracleTypes.NUMBER);
				stat.setInt(2, q.getGuideId());
				stat.setString(3, q.getQuestion());
				stat.setDouble(4, q.getWeight());

				stat.execute();

				q.setQuestionId(stat.getInt(1));
			}

			con.commit();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("InterviewGuideBean addInterviewGuideBean(InterviewGuideBean abean): " + e);
			throw new JobOpportunityException("Can not add InterviewGuideBean to DB.", e);
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

	public static void deleteInterviewGuideBean(int guideId) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.del_interview_guide(?); end;");
			stat.setInt(1, guideId);
			stat.execute();
		}
		catch (SQLException e) {
			System.err.println("void deleteInterviewGuideBean(int guideId): " + e);
			throw new JobOpportunityException("Can not delete InterviewGuideBean from DB.", e);
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
	}

	public static Collection<InterviewGuideBean> getInterviewGuideBeans() throws JobOpportunityException {

		Collection<InterviewGuideBean> guides = null;
		InterviewGuideBean guide = null;
		InterviewGuideQuestionBean question = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			guides = new ArrayList<InterviewGuideBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_interview_guides; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if ((guide == null) || (guide.getGuideId() != rs.getInt("guideId"))) {
					guide = createInterviewGuideBean(rs);

					guides.add(guide);
				}

				question = createInterviewGuideQuestionBean(rs);
				if (question != null && question.getGuideId() == guide.getGuideId()) {
					guide.addQuestion(question);
				}
			}
		}
		catch (SQLException e) {
			System.err.println("Collection<InterviewGuideBean> getInterviewGuideBeans(): " + e);
			throw new JobOpportunityException("Can not extract InterviewGuideBean from DB.", e);
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

		return guides;
	}

	public static InterviewGuideBean getInterviewGuideBean(int guideId) throws JobOpportunityException {

		InterviewGuideBean guide = null;
		InterviewGuideQuestionBean question = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_interview_guide(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, guideId);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if ((guide == null) || (guide.getGuideId() != rs.getInt("guideId"))) {
					guide = createInterviewGuideBean(rs);
				}

				question = createInterviewGuideQuestionBean(rs);
				if (question != null && question.getGuideId() == guide.getGuideId()) {
					guide.addQuestion(question);
				}
			}
		}
		catch (SQLException e) {
			System.err.println("InterviewGuideBean getInterviewGuideBean(int guideId): " + e);
			throw new JobOpportunityException("Can not extract InterviewGuideBean from DB.", e);
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

		return guide;
	}

	public static InterviewGuideBean getInterviewGuideBean(JobOpportunityBean job) throws JobOpportunityException {

		InterviewGuideBean guide = null;
		InterviewGuideQuestionBean question = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_job_interview_guide(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, job.getCompetitionNumber());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if ((guide == null) || (guide.getGuideId() != rs.getInt("guideId"))) {
					guide = createInterviewGuideBean(rs);
				}

				question = createInterviewGuideQuestionBean(rs);
				if (question != null && question.getGuideId() == guide.getGuideId()) {
					guide.addQuestion(question);
				}
			}
		}
		catch (SQLException e) {
			System.err.println("InterviewGuideBean getInterviewGuideBean(JobOpportunityBean job): " + e);
			throw new JobOpportunityException("Can not extract InterviewGuideBean from DB.", e);
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

		return guide;
	}

	public static void setJobInterviewGuideBean(JobOpportunityBean job, InterviewGuideBean guide)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.set_job_interview_guide(?,?); end;");
			stat.setString(1, job.getCompetitionNumber());
			stat.setInt(2, guide.getGuideId());

			stat.execute();
		}
		catch (SQLException e) {
			System.err.println("void setJobInterviewGuideBean(JobOpportunityBean job, InterviewGuideBean guide): " + e);
			throw new JobOpportunityException("Can not extract InterviewGuideBean from DB.", e);
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
	}

	public static void unsetJobInterviewGuideBean(JobOpportunityBean job) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.unset_job_interview_guide(?); end;");
			stat.setString(1, job.getCompetitionNumber());

			stat.execute();
		}
		catch (SQLException e) {
			System.err.println("void unsetJobInterviewGuideBean(JobOpportunityBean job): " + e);
			throw new JobOpportunityException("Can not extract InterviewGuideBean from DB.", e);
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
	}

	public static InterviewGuideBean createInterviewGuideBean(ResultSet rs) {

		InterviewGuideBean abean = null;
		try {
			abean = new InterviewGuideBean();

			abean.setGuideId(rs.getInt("guideId"));
			abean.setTitle(rs.getString("title"));
			abean.setRatingScaleBottom(rs.getInt("scalebottom"));
			abean.setRatingScaleTop(rs.getInt("scaletop"));
			abean.setCreatedDate(new java.util.Date(rs.getTimestamp("created").getTime()));
			abean.setModifiedDate(new java.util.Date(rs.getTimestamp("modified").getTime()));
			abean.setSchoolYear(rs.getString("schoolyear"));
			abean.setActiveList(rs.getBoolean("active"));
			abean.setGuideType(rs.getString("GUIDE_TYPE"));
		}
		catch (Exception e) {
			abean = null;
		}

		return abean;
	}

	public static InterviewGuideQuestionBean createInterviewGuideQuestionBean(ResultSet rs) {

		InterviewGuideQuestionBean abean = null;
		try {
			abean = new InterviewGuideQuestionBean();

			abean.setQuestionId(rs.getInt("questionId"));
			abean.setGuideId(rs.getInt("guideId"));
			abean.setQuestion(rs.getString("content"));
			abean.setWeight(rs.getDouble("weight"));
		}
		catch (Exception e) {
			abean = null;
		}

		return abean;
	}
	
	public static Collection<InterviewGuideBean> getActiveInterviewGuideBeans() throws JobOpportunityException {

		Collection<InterviewGuideBean> guides = null;
		InterviewGuideBean guide = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			guides = new ArrayList<InterviewGuideBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_active_interview_guides; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if ((guide == null) || (guide.getGuideId() != rs.getInt("guideId"))) {
					guide = createInterviewGuideBean(rs);
					guide.setNumberOfQuestions(rs.getInt("qcount"));
					guides.add(guide);
				}
				
			}
		}
		catch (SQLException e) {
			System.err.println("Collection<InterviewGuideBean> getActiveInterviewGuideBeans(): " + e);
			throw new JobOpportunityException("Can not extract InterviewGuideBean from DB.", e);
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

		return guides;
	}
	public static Collection<InterviewGuideBean> getActiveInterviewGuideBeansByType(String stype) throws JobOpportunityException {

		Collection<InterviewGuideBean> guides = null;
		InterviewGuideBean guide = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			guides = new ArrayList<InterviewGuideBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_active_interview_guide_t(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, stype);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if ((guide == null) || (guide.getGuideId() != rs.getInt("guideId"))) {
					guide = createInterviewGuideBean(rs);
					guide.setNumberOfQuestions(rs.getInt("qcount"));
					guides.add(guide);
				}
				
			}
		}
		catch (SQLException e) {
			System.err.println("Collection<InterviewGuideBean> getActiveInterviewGuideBeansByType(String stype): " + e);
			throw new JobOpportunityException("Can not extract InterviewGuideBean from DB.", e);
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

		return guides;
	}
	
	public static Collection<InterviewGuideBean> getInactiveInterviewGuideBeans() throws JobOpportunityException {

		Collection<InterviewGuideBean> guides = null;
		InterviewGuideBean guide = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			guides = new ArrayList<InterviewGuideBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_inactive_interview_guides; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if ((guide == null) || (guide.getGuideId() != rs.getInt("guideId"))) {
					guide = createInterviewGuideBean(rs);
					guide.setNumberOfQuestions(rs.getInt("qcount"));
					guides.add(guide);
				}
				
			}
		}
		catch (SQLException e) {
			System.err.println("Collection<InterviewGuideBean> getInactiveInterviewGuideBeans(): " + e);
			throw new JobOpportunityException("Can not extract InterviewGuideBean from DB.", e);
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

		return guides;
	}
	public static Collection<InterviewGuideBean> getInactiveInterviewGuideBeansByType(String gtype) throws JobOpportunityException {

		Collection<InterviewGuideBean> guides = null;
		InterviewGuideBean guide = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			guides = new ArrayList<InterviewGuideBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_inactive_interview_guide_t(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2,gtype);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if ((guide == null) || (guide.getGuideId() != rs.getInt("guideId"))) {
					guide = createInterviewGuideBean(rs);
					guide.setNumberOfQuestions(rs.getInt("qcount"));
					guides.add(guide);
				}
				
			}
		}
		catch (SQLException e) {
			System.err.println("Collection<InterviewGuideBean> getInactiveInterviewGuideBeans(): " + e);
			throw new JobOpportunityException("Can not extract InterviewGuideBean from DB.", e);
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

		return guides;
	}



}
