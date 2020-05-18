package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.PersonnelException;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.ReferenceCheckRequestBean;

public class ReferenceCheckRequestManager {

	public static Collection<ReferenceCheckRequestBean> getReferenceCheckRequestBeans(JobOpportunityBean aBean,
																																										ApplicantProfileBean profile)
			throws JobOpportunityException {

		Vector<ReferenceCheckRequestBean> v_opps = null;
		ReferenceCheckRequestBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<ReferenceCheckRequestBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_ref_chk_reqs(?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, aBean.getCompetitionNumber());
			stat.setString(3, profile.getUID());
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createReferenceCheckRequestBean(rs,false);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("Collection<ReferenceCheckRequestBean> getReferenceCheckRequestBeans(JobOpportunityBean aBean,ApplicantProfileBean profile): "
					+ e);
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

		return v_opps;
	}

	public static ReferenceCheckRequestBean getReferenceCheckRequestBean(int id) throws JobOpportunityException {

		ReferenceCheckRequestBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_ref_chk_req(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createReferenceCheckRequestBean(rs,true);

		}
		catch (SQLException e) {
			System.err.println("ReferenceCheckRequestManager.getReferenceCheckRequestBeans(int): " + e);
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

	public static ReferenceCheckRequestBean addReferenceCheckRequestBean(ReferenceCheckRequestBean abean)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.add_ref_chk_req(?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);

			stat.setString(2, abean.getCompetitionNumber());
			stat.setString(3, abean.getCandidateId());
			stat.setString(4, abean.getReferrerEmail());
			stat.setInt(5, abean.getCheckRequester().getPersonnelID());
			stat.setString(6, abean.getReferenceType());

			stat.execute();

			int id = ((OracleCallableStatement) stat).getInt(1);

			abean.setRequestId(id);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("ReferenceCheckRequestBean addReferenceCheckRequestBean(ReferenceCheckRequestBean abean): "
					+ e);
			throw new JobOpportunityException("Can not add ReferenceCheckRequestBean to DB.", e);
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

	public static void updateReferenceCheckRequestBean(ReferenceCheckRequestBean abean) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.mod_ref_chk_req(?,?,?,?,?,?); end;");

			stat.setInt(1, abean.getRequestId());
			stat.setString(2, abean.getCompetitionNumber());
			stat.setString(3, abean.getCandidateId());
			stat.setString(4, abean.getReferrerEmail());
			stat.setInt(5, abean.getReferenceId());
			stat.setInt(6, abean.getCheckRequester().getPersonnelID());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void updateReferenceCheckRequestBean(ReferenceCheckRequestBean abean): " + e);
			throw new JobOpportunityException("Can not update ReferenceCheckRequestBean to DB.", e);
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

	public static void deleteReferenceCheckRequestBean(ReferenceCheckRequestBean abean) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.del_ref_chk_req(?); end;");

			stat.setInt(1, abean.getRequestId());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void deleteReferenceCheckRequestBean(ReferenceCheckRequestBean abean): " + e);
			throw new JobOpportunityException("Can not delete ReferenceCheckRequestBean to DB.", e);
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

	public static ReferenceCheckRequestBean createReferenceCheckRequestBean(ResultSet rs, boolean extra) {

		ReferenceCheckRequestBean abean = null;

		try {
			abean = new ReferenceCheckRequestBean();

			abean.setCandidateId(rs.getString("CANDIDATE_ID"));
			abean.setCompetitionNumber(rs.getString("COMP_NUM"));
			abean.setReferenceId(rs.getInt("REFERENCE_ID"));
			abean.setReferredEmail(rs.getString("REFERRER_EMAIL"));
			abean.setRequestId(rs.getInt("REQUEST_ID"));
			abean.setRequestDate(new java.util.Date(rs.getDate("REQUEST_DATE").getTime()));
			try {
				abean.setCheckRequester(PersonnelDB.getPersonnel(rs.getInt("REQUESTER_ID")));
			}
			catch (PersonnelException e) {}
			if(extra) {
				StringBuilder sb = new StringBuilder();
				sb.append(rs.getString("SURNAME"));
				if(rs.getString("MAIDENNAME")!= null) {
					sb.append("(nee " + rs.getString("MAIDENNAME") + ")");
				}
				sb.append(", " + rs.getString("FIRSTNAME"));
				if(rs.getString("MIDDLENAME")!= null) {
					sb.append(" " + rs.getString("MIDDLENAME"));
				}
				abean.setApplicantName(sb.toString());
			}
		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}

}
