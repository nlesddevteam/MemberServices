package com.esdnl.personnel.jobs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.OtherJobApplicantBean;
public class OtherJobApplicantManager {
	public static String applyForPosition(ApplicantProfileBean abean, Integer jobid) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;
		String result="";
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.apply_for_other_position(?,?); end;");

			stat.setString(1, abean.getSIN());
			stat.setInt(2, jobid);

			stat.execute();
			result="UPDATED";
			//now we send the confirmation email
			/**
		    try
		    {
		    	EmailBean email = new EmailBean();
		    	OtherJobOpportunityBean obean = OtherJobOpportunityManager.getOtherJobOpportunityBeanById(jobid);
		    	email.setTo(new String[]{abean.getEmail()});
		    	email.setSubject("NLESD: Application Received");
		    	email.setBody("You application has been received for competition: " + obean.getTitle()
		          + "Please do not reply to this message.\n\n" 
		          + "\n\nMember Services");
		    	email.setFrom("employment@nlesd.ca");
		    	email.send();
		    }
		    catch(Exception e){}
		    **/
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("static void applyForPosition(ApplicantProfileBean abean, Integer jobid): "
					+ e);
			throw new JobOpportunityException("Can not apply for position.", e);
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
		return result;
	}
	public static OtherJobApplicantBean checkApplicantAppliedFor(ApplicantProfileBean abean, Integer jobid) throws JobOpportunityException {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		OtherJobApplicantBean appliedFor=null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.personnel_jobs_pkg.check_other_job_applicant(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, abean.getSIN());
			stat.setInt(3, jobid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			if (rs.next())
				appliedFor=createOtherJobApplicantBean(rs);
}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("static Boolean checkApplicantAppliedFor(ApplicantProfileBean abean, Integer jobid) : "
					+ e);
			throw new JobOpportunityException("Can not check for position.", e);
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
		return appliedFor;
	}
	public static String unapplyForPosition(ApplicantProfileBean abean, Integer jobid) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;
		String result="";
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.unapply_for_other_job(?,?); end;");

			stat.setString(1, abean.getSIN());
			stat.setInt(2, jobid);

			stat.execute();
			result="UPDATED";
			
			//now we send the confirmation email
			/**
		    try
		    {
		    	EmailBean email = new EmailBean();
		    	OtherJobOpportunityBean obean = OtherJobOpportunityManager.getOtherJobOpportunityBeanById(jobid);
		    	email.setTo(new String[]{abean.getEmail()});
		    	email.setSubject("NLESD: Application Withdrawn");
		    	email.setBody("You application has been withdrawn from competition: " + obean.getTitle()
		          + "Please do not reply to this message.\n\n" 
		          + "\n\nMember Services");
		    	email.setFrom("employment@nlesd.ca");
		    	email.send();
		    }
		    catch(Exception e){}
		    **/
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("static String unapplyForPosition(ApplicantProfileBean abean, Integer jobid): "
					+ e);
			throw new JobOpportunityException("Can not unapply for position.", e);
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
		return result;
	}	
	public static OtherJobApplicantBean createOtherJobApplicantBean(ResultSet rs) {

		OtherJobApplicantBean aBean = null;
		try {
			aBean = new OtherJobApplicantBean();

			aBean.setSin(rs.getString("SIN"));
			aBean.setJobId(rs.getInt("JOB_ID"));
			aBean.setExtraInfo(rs.getString("EXTRA_INFO"));
			aBean.setShortListed(rs.getString("SHORTLISTED"));
			try {
				if (rs.getTimestamp("APPLIED_DATE") != null)
					aBean.setAppliedDate(new java.util.Date(rs.getTimestamp("APPLIED_DATE").getTime()));
			}
			catch (SQLException e) {}

			try {
				if (rs.getTimestamp("DECLINED_INTERVIEW_DATE") != null)
					aBean.setDeclinedInterviewDate(new java.util.Date(rs.getTimestamp("DECLINED_INTERVIEW_DATE").getTime()));
			}
			catch (SQLException e) {}

			
		}
		catch (SQLException e) {
			aBean = null;
			System.out.println("ERROR CREATING OtherJobApplicantBean: " + e.getMessage());
		}
		return aBean;
	}	
}
