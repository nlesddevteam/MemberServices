package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.common.Utils;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.ApplicantSubListInfoBean;
import com.esdnl.personnel.jobs.bean.ApplicantVerificationBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;

public class ApplicantSubListInfoManager {

	public static HashMap<Integer, ApplicantSubListInfoBean> getApplicantSubListInfoBeanMap(ApplicantProfileBean pbean)
			throws JobOpportunityException {

		HashMap<Integer, ApplicantSubListInfoBean> v_opps = null;
		ApplicantSubListInfoBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new HashMap<Integer, ApplicantSubListInfoBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_appl_sub_list_info_a(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, pbean.getUID());
			stat.setString(3, Utils.getCurrentSchoolYear());

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantSubListInfoBean(rs);

				v_opps.put(new Integer(rs.getInt("LIST_ID")), eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("HashMap getApplicantSubListInfoBeanMap(ApplicantProfileBean pbean): " + e);
			throw new JobOpportunityException("Can not extract ApplicantSubListInfoBean from DB.", e);
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

	public static void resetApplicantSublistApproval(String uid, int list_id) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.reset_appl_sub_list_approval(?,?); end;");
			stat.setString(1, uid);
			stat.setInt(2, list_id);

			stat.execute();
		}
		catch (SQLException e) {
			System.err.println("HashMap getApplicantSubListInfoBeanMap(ApplicantProfileBean pbean): " + e);
			throw new JobOpportunityException("Can not extract ApplicantSubListInfoBean from DB.", e);
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

	public static ApplicantSubListInfoBean createApplicantSubListInfoBean(ResultSet rs) {

		ApplicantSubListInfoBean aBean = null;

		try {
			aBean = new ApplicantSubListInfoBean();

			aBean.setSubList(SubListManager.createSubListBean(rs));
			aBean.setStatus(rs.getString("SHORTLISTED"));

			if (rs.getDate("APPLIED_DATE") != null)
				aBean.setAppliedDate(new java.util.Date(rs.getDate("APPLIED_DATE").getTime()));

			if (rs.getDate("APPROVED_DATE") != null)
				aBean.setApprovedDate(new java.util.Date(rs.getDate("APPROVED_DATE").getTime()));

			if (rs.getDate("NOTAPPROVED_DATE") != null)
				aBean.setNotApprovedDate(new java.util.Date(rs.getDate("NOTAPPROVED_DATE").getTime()));

			if (rs.getDate("WORKING_DATE") != null)
				aBean.setWorkingDate(new java.util.Date(rs.getDate("WORKING_DATE").getTime()));
			
			//now check to see if there is audit information for this applicant/list
			try {
				if (rs.getInt("ENTRYID") > 0) {
					aBean.setAuditBean(ApplicantSubListAuditManager.createApplicantSubListAuditBean(rs));
				}else {
					aBean.setAuditBean(null);
				}
			}
			catch (SQLException e) {
				aBean.setAuditBean(null);
				System.out.println(e.getStackTrace());
			}
		}
		catch (SQLException e) {
			aBean = null;
		}

		return aBean;
	}

}
