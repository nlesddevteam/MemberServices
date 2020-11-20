package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.PersonnelException;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantSubListAuditBean;
import com.esdnl.personnel.jobs.constants.SublistAuditTypeCostant;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class ApplicantSubListAuditManager {
	public static ApplicantSubListAuditBean addApplicantSubListAuditBean(ApplicantSubListAuditBean abean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.add_sublist_audit(?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setInt(2, abean.getSubListId());
			stat.setString(3, abean.getApplicantId());
			stat.setInt(4, abean.getEntryType().getValue());
			if(abean.getEntryBy() != null) {
				stat.setInt(5, abean.getEntryBy().getPersonnelID());
			}else {
				stat.setInt(5, 0);
			}
			
			stat.setString(6, abean.getEntryNotes());
			stat.execute();
			abean.setEntryId(stat.getInt(1));
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
				System.err.println("ApplicantSubListAuditBean addApplicantSubListAuditBean(ApplicantSubListAuditBean abean): " + e);
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
	public static ApplicantSubListAuditBean[] getSublistAuditTrail(int slid) {

		Vector<ApplicantSubListAuditBean> v_opps = null;
		ApplicantSubListAuditBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<ApplicantSubListAuditBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_sublist_audit_details(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,slid );
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantSubListAuditBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("ApplicantSubListAuditBean[] getSublistAuditTrail(int slid)): " + e);
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

		return (ApplicantSubListAuditBean[]) v_opps.toArray(new ApplicantSubListAuditBean[0]);
	}
	public static ApplicantSubListAuditBean[] getSublistApplicantAuditTrail(int slid,String appid) {

		Vector<ApplicantSubListAuditBean> v_opps = null;
		ApplicantSubListAuditBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<ApplicantSubListAuditBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_sl_app_audit_details(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2,appid );
			stat.setInt(3,slid );
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantSubListAuditBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("ApplicantSubListAuditBean[] getSublistApplicantAuditTrail(int slid,int appid)): " + e);
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

		return (ApplicantSubListAuditBean[]) v_opps.toArray(new ApplicantSubListAuditBean[0]);
	}
	public static ApplicantSubListAuditBean createApplicantSubListAuditBean(ResultSet rs) {

		ApplicantSubListAuditBean aBean = null;
		try {
			aBean = new ApplicantSubListAuditBean();

			aBean.setEntryId(rs.getInt("ENTRYID"));
			aBean.setSubListId(rs.getInt("SUBLISTID"));
			aBean.setApplicantId(rs.getString("APPLICANTID"));
			aBean.setEntryType(SublistAuditTypeCostant.get(rs.getInt("ENTRYTYPE")));
			if (rs.getDate("ENTRYDATE") != null)
				//aBean.setEntryDate(new java.util.Date(rs.getDate("ENTRYDATE").getTime()));
				aBean.setEntryDate(new java.util.Date(rs.getDate("ENTRYDATE").getTime()));
			if(rs.getInt("ENTRYBY") == 0) {
				aBean.setEntryBy(null);
			}else {
				aBean.setEntryBy(PersonnelDB.getPersonnel(rs.getInt("ENTRYBY")));
			}
			aBean.setEntryNotes(rs.getString("ENTRYNOTES"));
			aBean.setEntryDateString(rs.getString("ENTRYDATESTRING"));
		}
		catch (SQLException | PersonnelException e) {
			aBean = null;
		}
		return aBean;
	}
}
