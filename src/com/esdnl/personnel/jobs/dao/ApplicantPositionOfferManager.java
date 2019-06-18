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
import com.esdnl.personnel.jobs.bean.ApplicantPositionOfferBean;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;

public class ApplicantPositionOfferManager {

	public static ApplicantPositionOfferBean[] getApplicantPositionOfferBeans(ApplicantProfileBean profile)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<ApplicantPositionOfferBean> offers = null;

		try {

			offers = new ArrayList<ApplicantPositionOfferBean>();

			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			// get the opportunity info
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_appl_job_offer(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, profile.getSIN());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next())
				offers.add(new ApplicantPositionOfferBean(JobOpportunityManager.createJobOpportunityBean(rs), RecommendationManager.createTeacherRecommendationBean(rs)));
		}
		catch (SQLException e) {
			System.err.println("JobOpportunityManager.getOfferedPosition(ApplicantProfileBean profile): " + e);
			throw new JobOpportunityException("Can not retrieve JobOpportunityBean from DB.", e);
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

		return (ApplicantPositionOfferBean[]) offers.toArray(new ApplicantPositionOfferBean[0]);
	}

	public static ApplicantPositionOfferBean[] getExpiredPositionOfferBeans() throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<ApplicantPositionOfferBean> offers = null;

		try {

			offers = new ArrayList<ApplicantPositionOfferBean>();

			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			// get the opportunity info
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_expired_job_offers; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next())
				offers.add(new ApplicantPositionOfferBean(JobOpportunityManager.createJobOpportunityBean(rs), RecommendationManager.createTeacherRecommendationBean(rs)));
		}
		catch (SQLException e) {
			System.err.println("ApplicantPositionOfferBean[] ApplicantPositionOfferManager.getExpiredPositionOfferBeans(): "
					+ e);
			throw new JobOpportunityException("Can not retrieve ApplicantPositionOfferBean from DB.", e);
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

		return (ApplicantPositionOfferBean[]) offers.toArray(new ApplicantPositionOfferBean[0]);
	}

	public static Collection<ApplicantPositionOfferBean> getApplicantEmploymentLetters(ApplicantProfileBean profile)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<ApplicantPositionOfferBean> letters = null;

		try {

			letters = new ArrayList<ApplicantPositionOfferBean>();

			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			// get the opportunity info
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_appl_emp_letters(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, profile.getSIN());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next())
				letters.add(new ApplicantPositionOfferBean(JobOpportunityManager.createJobOpportunityBean(rs), RecommendationManager.createTeacherRecommendationBean(rs)));
		}
		catch (SQLException e) {
			System.err.println("Collection<ApplicantPositionOfferBean> getApplicantEmploymentLetters(ApplicantProfileBean profile): "
					+ e);
			throw new JobOpportunityException("Can not retrieve ApplicantPositionOfferBean from DB.", e);
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

		return letters;
	}

}
