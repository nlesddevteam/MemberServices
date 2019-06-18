package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.NLESDReferenceListBean;

public class NLESDReferenceListManager {

	public static ArrayList<NLESDReferenceListBean> getReferenceBeans(String providedBy) throws JobOpportunityException {

		ArrayList<NLESDReferenceListBean> refs = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			refs = new ArrayList<NLESDReferenceListBean>(5);
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_nlesd_ref_chk_by(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, providedBy);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next())
				refs.add(createNLESDReferenceListBean((rs)));
		}
		catch (SQLException e) {
			System.err.println("NLESDReferenceListManager.getReferenceBeans(Personnel by): " + e);
			throw new JobOpportunityException("Can not extract NLESDReferenceListManager from DB.", e);
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
		return refs;
	}
	public static NLESDReferenceListBean createNLESDReferenceListBean(ResultSet rs) {

		NLESDReferenceListBean abean = null;
		try {
			abean = new NLESDReferenceListBean();
			abean.setId(rs.getInt("REFERENCE_ID"));
			abean.setFirstName(rs.getString("FIRSTNAME"));
			abean.setSurName(rs.getString("SURNAME"));
			abean.setMaidenName(rs.getString("MAIDENNAME"));
			abean.setMiddleName(rs.getString("MIDDLENAME"));
			abean.setReferenceType(rs.getString("RTYPE"));
			abean.setApplicantId(rs.getString("SIN"));
			abean.setProvidedBy(rs.getString("PROVIDED_BY"));
			abean.setProviderPosition(rs.getString("PROVIDER_POSITION"));
			abean.setReferenceDate(new java.util.Date(rs.getTimestamp("DATE_PROVIDED").getTime()));
			if (abean.getReferenceType().equals("ADMIN")) {
				abean.setEditUrl("editNLESDAdminReference.html?id=" + abean.getId());
				abean.setViewUrl("viewNLESDAdminReference.html?id=" + abean.getId());
			}
			else if (abean.getReferenceType().equals("GUIDE")) {
				abean.setEditUrl("editNLESDGuideReference.html?id=" + abean.getId());
				abean.setViewUrl("viewNLESDGuideReference.html?id=" + abean.getId());
			}
			else if (abean.getReferenceType().equals("TEACHER")) {
				abean.setEditUrl("editNLESDTeacherReference.html?id=" + abean.getId());
				abean.setViewUrl("viewNLESDTeacherReference.html?id=" + abean.getId());
			}else if (abean.getReferenceType().equals("EXTERNAL")) {
				abean.setEditUrl("");
				abean.setViewUrl("viewNLESDExternalReference.html?id=" + abean.getId());
			}else if (abean.getReferenceType().equals("MANAGEMENT")) {
				abean.setEditUrl("");
				abean.setViewUrl("viewNLESDManageReference.html?id=" + abean.getId());
			}else if (abean.getReferenceType().equals("SUPPORT")) {
				abean.setEditUrl("");
				abean.setViewUrl("viewNLESDSupportReference.html?id=" + abean.getId());
			}
			else {
				abean.setEditUrl("editApplicantReference.html?id=" + abean.getId());
				abean.setViewUrl("viewApplicantReference.html?id=" + abean.getId());
			}
		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}

	public static NLESDReferenceListBean[] getReferenceBeansByApplicant(String id) throws JobOpportunityException {

		ArrayList<NLESDReferenceListBean> refs = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			refs = new ArrayList<NLESDReferenceListBean>(5);
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_nlesd_ref_chk_applicant(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next())
				refs.add(createNLESDReferenceListBean((rs)));
		}
		catch (SQLException e) {
			System.err.println("NLESDReferenceListManager.getReferenceBeans(Personnel by): " + e);
			throw new JobOpportunityException("Can not extract NLESDReferenceListManager from DB.", e);
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
		return (NLESDReferenceListBean[]) refs.toArray(new NLESDReferenceListBean[0]);
	}

	public static NLESDReferenceListBean getReferenceBeansByApplicantRec(int rid, String sid)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		NLESDReferenceListBean ref = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_nlesd_ref_chk_for_rec(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, rid);
			stat.setString(3, sid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next())
				ref = createNLESDReferenceListBean((rs));
		}
		catch (SQLException e) {
			System.err.println("NLESDReferenceListManager.getReferenceBeans(Personnel by): " + e);
			throw new JobOpportunityException("Can not extract NLESDReferenceListManager from DB.", e);
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
		return (ref);
	}
	public static NLESDReferenceListBean[] getOtherReferenceBeansByApplicant(String id) throws JobOpportunityException {

		ArrayList<NLESDReferenceListBean> refs = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			refs = new ArrayList<NLESDReferenceListBean>(5);
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_other_refs_app(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next())
				refs.add(createNLESDReferenceListBean((rs)));
		}
		catch (SQLException e) {
			System.err.println("NLESDReferenceListBean[] getOtherReferenceBeansByApplicant(String id): " + e);
			throw new JobOpportunityException("Can not extract NLESDReferenceListManager from DB.", e);
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
		return (NLESDReferenceListBean[]) refs.toArray(new NLESDReferenceListBean[0]);
	}
}
