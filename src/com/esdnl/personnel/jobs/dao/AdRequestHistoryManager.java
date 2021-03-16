package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.PersonnelException;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.AdRequestBean;
import com.esdnl.personnel.jobs.bean.AdRequestHistoryBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.constants.RequestStatus;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class AdRequestHistoryManager {

	public static HashMap<RequestStatus, AdRequestHistoryBean> getAdRequestHistory(AdRequestBean req)
			throws JobOpportunityException {

		HashMap<RequestStatus, AdRequestHistoryBean> beans = null;
		AdRequestHistoryBean bean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new HashMap<RequestStatus, AdRequestHistoryBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_ad_request.get_ad_request_history(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, req.getId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				bean = createAdRequestHistoryBean(rs);
				beans.put(bean.getStatus(), bean);
			}
		}
		catch (SQLException e) {
			System.err.println("HashMap getAdRequestHistory(AdRequestBean req): " + e);
			throw new JobOpportunityException("Can not extract AdRequestHistoryBean from DB.", e);
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

		return beans;
	}

	public static AdRequestHistoryBean createAdRequestHistoryBean(ResultSet rs) {

		AdRequestHistoryBean abean = null;

		try {
			abean = new AdRequestHistoryBean();

			abean.setId(rs.getInt("HISTORY_ID"));
			abean.setRequestId(rs.getInt("REQUEST_ID"));
			abean.setRequestStatus(RequestStatus.get(rs.getInt("STATUS_ID")));
			abean.setHistoryDate(new java.util.Date(rs.getDate("HISTORY_DATE").getTime()));

			abean.setPersonnel(PersonnelDB.createPersonnelBean(rs));
			if (abean.getPersonnel() == null) {
				abean.setPersonnel(PersonnelDB.getPersonnel(rs.getInt("PERSONNEL_ID")));
			}

			abean.setComments(rs.getString("COMMENT"));

		}
		catch (PersonnelException e) {
			e.printStackTrace();
			abean = null;
		}
		catch (SQLException e) {
			e.printStackTrace();
			abean = null;
		}
		catch (Exception e) {
			e.printStackTrace();
			abean = null;
		}

		return abean;
	}
}