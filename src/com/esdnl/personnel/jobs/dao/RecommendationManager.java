package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.awsd.personnel.Personnel;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.TeacherRecommendationBean;
import com.esdnl.personnel.jobs.constants.EmploymentConstant;
import com.esdnl.personnel.jobs.constants.PositionTypeConstant;
import com.esdnl.personnel.jobs.constants.RTHPositionTypeConstant;
import com.esdnl.personnel.jobs.constants.RecommendationStatus;
import com.esdnl.personnel.jobs.dao.worker.ExpiredJobOfferNotificationWorker;

public class RecommendationManager {

	public static final long WAIT_PERIOD = 3600000; // 10 mins

	static {
		try {
			(new Timer()).schedule(new ExpiredJobOfferNotificationWorker(), 600000, WAIT_PERIOD);
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	public static TeacherRecommendationBean getTeacherRecommendationBean(int id) throws JobOpportunityException {

		TeacherRecommendationBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_tchr_rec(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createTeacherRecommendationBean(rs);

		}
		catch (SQLException e) {
			System.err.println("TeacherRecommendationBean getTeacherRecommendationBean(int id): " + e);
			throw new JobOpportunityException("Can not extract ReferenceCheckRequestBean from DB.", e);
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

		return eBean;
	}
	public static ArrayList<String> getRecommendationsEmails(String compnum) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<String> rlist = new ArrayList<String>();
		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_rec_emails(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, compnum);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				rlist.add(rs.getString("EMAIL"));
			}
				

		}
		catch (SQLException e) {
			System.err.println("ArrayList<String> getRecommendationsEmails(String compnum): " + e);
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

		return rlist;
	}
	public static void deleteTeacherRecommendationBean(int id) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.del_tchr_rec(?); end;");
			stat.setInt(1, id);
			stat.execute();
		}
		catch (SQLException e) {
			System.err.println("TeacherRecommendationBean getTeacherRecommendationBean(int id): " + e);
			throw new JobOpportunityException("Can not extract ReferenceCheckRequestBean from DB.", e);
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

	public static TeacherRecommendationBean[] getTeacherRecommendationBean(String comp_num)
			throws JobOpportunityException {

		ArrayList<TeacherRecommendationBean> recs = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			/*
			recs = new  TreeSet<TeacherRecommendationBean>(new Comparator<TeacherRecommendationBean>(){;
				public int compare(TeacherRecommendationBean o1, TeacherRecommendationBean o2){
					return o1.getRecommendedDate().compareTo(o2.getRecommendedDate()) * -1;
				}
			});
			*/
			recs = new ArrayList<TeacherRecommendationBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_tchr_rec(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, comp_num);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				recs.add(createTeacherRecommendationBean(rs));

		}
		catch (SQLException e) {
			System.err.println("TeacherRecommendationBean getTeacherRecommendationBean(int id): " + e);
			throw new JobOpportunityException("Can not extract ReferenceCheckRequestBean from DB.", e);
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

		return (TeacherRecommendationBean[]) recs.toArray(new TeacherRecommendationBean[0]);
	}

	public static TeacherRecommendationBean[] getTeacherRecommendationBean(ApplicantProfileBean profile, int search_window)
			throws JobOpportunityException {

		ArrayList<TeacherRecommendationBean> recs = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			// search window of 6 months.
			Calendar cur = Calendar.getInstance();
			cur.add(Calendar.MONTH, search_window);

			/*
			recs = new  TreeSet<TeacherRecommendationBean>(new Comparator<TeacherRecommendationBean>(){;
				public int compare(TeacherRecommendationBean o1, TeacherRecommendationBean o2){
					return o1.getRecommendedDate().compareTo(o2.getRecommendedDate()) * -1;
				}
			});
			*/

			recs = new ArrayList<TeacherRecommendationBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_appl_tchr_recs(?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, profile.getUID());
			stat.setDate(3, new java.sql.Date(cur.getTime().getTime()));
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				recs.add(createTeacherRecommendationBean(rs));

		}
		catch (SQLException e) {
			System.err.println("TeacherRecommendationBean getTeacherRecommendationBean(int id): " + e);
			throw new JobOpportunityException("Can not extract ReferenceCheckRequestBean from DB.", e);
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

		return (TeacherRecommendationBean[]) recs.toArray(new TeacherRecommendationBean[0]);
	}

	public static TeacherRecommendationBean[] getTeacherRecommendationBean(ApplicantProfileBean profile,
																																					java.util.Date start_date)
			throws JobOpportunityException {

		ArrayList<TeacherRecommendationBean> recs = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			// search window of 6 months.
			//Calendar cur = Calendar.getInstance();
			//cur.add(Calendar.MONTH, search_window);

			/*
			recs = new  TreeSet<TeacherRecommendationBean>(new Comparator<TeacherRecommendationBean>(){;
				public int compare(TeacherRecommendationBean o1, TeacherRecommendationBean o2){
					
					return o1.getRecommendedDate().compareTo(o2.getRecommendedDate()) * -1;
				}
			});
			*/

			recs = new ArrayList<TeacherRecommendationBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_appl_tchr_recs(?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, profile.getUID());
			stat.setDate(3, new java.sql.Date(start_date.getTime()));
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				recs.add(createTeacherRecommendationBean(rs));

		}
		catch (SQLException e) {
			System.err.println("TeacherRecommendationBean getTeacherRecommendationBean(int id): " + e);
			throw new JobOpportunityException("Can not extract ReferenceCheckRequestBean from DB.", e);
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

		return (TeacherRecommendationBean[]) recs.toArray(new TeacherRecommendationBean[0]);
	}

	public static TeacherRecommendationBean addTeacherRecommendationBean(TeacherRecommendationBean abean)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.add_tchr_rec_2(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);

			stat.setString(2, abean.getCompetitionNumber());
			stat.setString(3, abean.getCandidateId());
			stat.setInt(4, abean.getCurrentStatus().getValue());
			if(abean.getPositionType() != null){
				stat.setInt(5, abean.getPositionType().getValue());
			}else{
				stat.setInt(5, abean.getRth_position_type().getValue());
			}
			
			stat.setString(6, abean.getPositionTypeOther());
			stat.setInt(7, abean.getEmploymentStatus().getValue());
			stat.setString(8, abean.getSpecialConditions());
			stat.setString(9, abean.getSpecialConditionsComment());
			stat.setString(10, abean.getOtherComments());
			stat.setString(11, abean.getCandidate2());
			stat.setString(12, abean.getCandidate3());
			stat.setInt(13, abean.getRecommendedBy());
			stat.setString(14, abean.getInterviewPanel());
			stat.setString(15, abean.getReferencesSatisfactory());

			stat.setNull(16, OracleTypes.VARCHAR);
			stat.setNull(17, OracleTypes.DATE);

			stat.setInt(18, abean.getReferenceId());
			stat.setInt(19, abean.getInterviewSummaryId());
			stat.setString(20, abean.getCandidateComments());
			stat.setString(21, abean.getCandidateComments2());
			stat.setString(22, abean.getCandidateComments3());

			stat.execute();

			int id = ((OracleCallableStatement) stat).getInt(1);

			abean.setRecommendationId(id);

			stat.close();
			
			if (abean.getGSU() != null) {
				// add all gsu
				stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.add_rec_gsu(?,?,?,?); end;");

				for (int i = 0; i < abean.getGSU().length; i++) {
					stat.clearParameters();

					stat.setInt(1, abean.getRecommendationId());
					stat.setInt(2, abean.getGSU()[i].getGrade().getGradeID());
					if (abean.getGSU()[i].getSubject() != null)
						stat.setInt(3, abean.getGSU()[i].getSubject().getSubjectID());
					else
						stat.setNull(3, OracleTypes.NUMBER);
					stat.setDouble(4, abean.getGSU()[i].getUnitPercentage());

					stat.execute();
				}
			}
			 
			con.commit();

		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("ReferenceBean addReferenceBean(ReferenceBean abean): " + e);
			throw new JobOpportunityException("Can not add ReferenceBean to DB.", e);
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

	public static void updateTeacherRecommendationStatus(TeacherRecommendationBean rec, Personnel p,
																												RecommendationStatus new_status) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.update_tchr_rec_status(?,?,?); end;");

			stat.setInt(1, rec.getRecommendationId());
			if (p != null)
				stat.setInt(2, p.getPersonnelID());
			else
				stat.setNull(2, OracleTypes.NUMBER);
			stat.setInt(3, new_status.getValue());

			stat.execute();

		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void updateTeacherRecommendationStatus(TeacherRecommendationBean rec, Personnel p, RecommendationStatus new_status): "
					+ e);
			throw new JobOpportunityException("Can not add ReferenceBean to DB.", e);
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

	public static void updateTeacherRecommendation(TeacherRecommendationBean rec) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.update_tchr_rec(?,?); end;");

			stat.setInt(1, rec.getRecommendationId());
			stat.setBoolean(2, rec.isLetterOfOfferRequire());

			stat.execute();

		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void updateTeacherRecommendation(TeacherRecommendationBean rec) throws JobOpportunityException: "
					+ e);
			throw new JobOpportunityException("Can not update TeacherRecommendationBean to DB.", e);
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

	public static TeacherRecommendationBean createTeacherRecommendationBean(ResultSet rs) {

		TeacherRecommendationBean abean = null;

		try {
			abean = new TeacherRecommendationBean();

			abean.setApprovedBy(rs.getInt("APPROVED_BY"));
			if (rs.getDate("APPROVED_DATE") != null)
				abean.setApprovedDate(new java.util.Date(rs.getDate("APPROVED_DATE").getTime()));
			abean.setAcceptedBy(rs.getInt("ACCEPTED_BY"));
			if (rs.getDate("ACCEPTED_DATE") != null)
				abean.setAcceptedDate(new java.util.Date(rs.getDate("ACCEPTED_DATE").getTime()));
			abean.setCandidate2(rs.getString("CANDIDATE_2"));
			abean.setCandidate3(rs.getString("CANDIDATE_3"));
			abean.setCandidateId(rs.getString("CANDIDATE_ID"));
			abean.setCompetitionNumber(rs.getString("COMP_NUM"));
			abean.setCurrentStatus(RecommendationStatus.get(rs.getInt("REC_STATUS")));
			abean.setEmploymentStatus(EmploymentConstant.get(rs.getInt("EMP_STATUS")));
			abean.setInterviewPanel(rs.getString("INTERVIEW_PANEL"));
			abean.setOtherComments(rs.getString("OTHER_COMMENT"));
			JobOpportunityBean jbean =JobOpportunityManager.getJobOpportunityBean(rs.getString("COMP_NUM"));
			if(jbean.getIsSupport().equals("Y")){
				abean.setRth_position_type(RTHPositionTypeConstant.get(rs.getInt("POSITION_TYPE")));
			}else{
				abean.setPositionType(PositionTypeConstant.get(rs.getInt("POSITION_TYPE")));
			}
			
			abean.setPositionTypeOther(rs.getString("POSITION_TYPE_OTHER"));
			abean.setProcessedBy(rs.getInt("PROCESSED_BY"));
			if (rs.getDate("PROCESSED_DATE") != null)
				abean.setProcessedDate(new java.util.Date(rs.getDate("PROCESSED_DATE").getTime()));
			abean.setRecommendationId(rs.getInt("RECOMMENDATION_ID"));
			abean.setRecommendBy(rs.getInt("RECOMMENDED_BY"));
			if (rs.getDate("RECOMMENDED_DATE") != null)
				abean.setRecommendedDate(new java.util.Date(rs.getDate("RECOMMENDED_DATE").getTime()));
			abean.setRejectedBy(rs.getInt("REJECTED_BY"));
			if (rs.getDate("REJECTED_DATE") != null)
				abean.setRejectedDate(new java.util.Date(rs.getDate("REJECTED_DATE").getTime()));
			abean.setReferencesSatisfactory(rs.getString("REFERENCES_SATISFACTORY"));
			abean.setSpecialConditions(rs.getString("SPECIAL_CONDITIONS"));
			abean.setSpecialConditionsComment(rs.getString("SPECIAL_CONDITIONS_COMMENT"));

			abean.setOfferMadeBy(rs.getInt("OFFER_MADE_BY"));
			if (rs.getTimestamp("OFFER_DATE") != null)
				abean.setOfferMadeDate(new java.util.Date(rs.getTimestamp("OFFER_DATE").getTime()));
			if (rs.getDate("OFFER_ACCEPTED_DATE") != null)
				abean.setOfferAcceptedDate(new java.util.Date(rs.getDate("OFFER_ACCEPTED_DATE").getTime()));
			if (rs.getDate("OFFER_REJECTED_DATE") != null)
				abean.setOfferRejectedDate(new java.util.Date(rs.getDate("OFFER_REJECTED_DATE").getTime()));
			
			abean.setReferenceId(rs.getInt("REFERENCE_ID"));
			abean.setInterviewSummaryId(rs.getInt("INTERVIEW_SUMMARY_ID"));
			abean.setCandidateComments(rs.getString("CANDIDATECOMMENTS"));
			abean.setCandidateComments2(rs.getString("CANDIDATECOMMENTS2"));
			abean.setCandidateComments3(rs.getString("CANDIDATECOMMENTS3"));

			try {
				abean.setGSU(ReccommentationGSUManager.getGradeSubjectPercentUnitBeans(abean));
			}
			catch (JobOpportunityException e) {
				abean.setGSU(null);
			}
		}
		catch (SQLException e) {
			abean = null;
		} catch (JobOpportunityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return abean;
	}

}
