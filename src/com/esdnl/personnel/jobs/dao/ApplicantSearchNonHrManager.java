package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantSearchNonHrBean;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class ApplicantSearchNonHrManager {
	public static ApplicantSearchNonHrBean[] searchApplicantProfile(String searchby,String searchtext) {

		Vector<ApplicantSearchNonHrBean> v_opps = null;
		ApplicantSearchNonHrBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<ApplicantSearchNonHrBean>(5);

			con = DAOUtils.getConnection();
			
			if (searchby.equals("EM")) {
				stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.search_coe_doc_email(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setString(2, searchtext.toLowerCase());
			}else if (searchby.equals("FN")) {
				stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.search_coe_doc_fname(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setString(2, searchtext.toLowerCase());
			}else  {
				stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.search_coe_doc_lname(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setString(2, searchtext.toLowerCase());
			}

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantSearchNonHrBean(rs,false);
				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println(
					"ApplicantSearchNonHrBean[] searchApplicantProfile(String searchby,String searchtext): " + e);
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

		return (ApplicantSearchNonHrBean[]) v_opps.toArray(new ApplicantSearchNonHrBean[0]);
	}
	public static ArrayList<ApplicantSearchNonHrBean> viewMissingCoeReport() {

		ArrayList<ApplicantSearchNonHrBean> v_opps = new ArrayList<>();
		ApplicantSearchNonHrBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_missing_coe; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantSearchNonHrBean(rs,true);
				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println(
					"static ApplicantSearchNonHrBean[] viewMissingCoeReport(): " + e);
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
	public static ApplicantSearchNonHrBean createApplicantSearchNonHrBean(ResultSet rs,Boolean ptype) {

		ApplicantSearchNonHrBean aBean = null;
		try {
			aBean = new ApplicantSearchNonHrBean();
			
			aBean.setSdsLastName(rs.getString("SDSLNAME"));
			aBean.setSdsFirstName(rs.getString("SDSFNAME"));
			aBean.setSdsEmail(rs.getString("SDSEMAIL"));
			aBean.setAppLastName(rs.getString("APPLNAME"));
			aBean.setAppFirstName(rs.getString("APPFNAME"));
			aBean.setAppEmail(rs.getString("APPEMAIL"));
			aBean.setAppSin(rs.getString("APPSIN"));
			//used for reporting page only
			if(ptype) {
				aBean.setProfileType(rs.getString("PROFILETYPE"));
			}else {
				aBean.setDocType(rs.getInt("DDOCTYPE"));
				aBean.setDocId(rs.getInt("MAXID"));
			}
			
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}
}

