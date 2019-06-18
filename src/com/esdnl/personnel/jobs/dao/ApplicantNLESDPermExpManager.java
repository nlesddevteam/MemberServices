package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantNLESDPermanentExperienceBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;

public class ApplicantNLESDPermExpManager {
	public static ApplicantNLESDPermanentExperienceBean addApplicantNLESDPermanentExperienceBean(	ApplicantNLESDPermanentExperienceBean abean)
	throws JobOpportunityException {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.add_nlesd_perm_exp(?,?,?,?,?); end;");
			stat.setString(1, abean.getSIN());
			stat.setDate(2, new java.sql.Date(abean.getFrom().getTime()));
			stat.setDate(3, new java.sql.Date(abean.getTo().getTime()));
			stat.setInt(4, abean.getSchoolId());
			stat.setString(5, abean.getGradesSubjects());
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ApplicantNLESDPermeanentExperienceBean addApplicantNLESDPermanentExperienceBean(ApplicantNLESDPermanentExperienceBean abean): "
					+ e);
			throw new JobOpportunityException("Can not add ApplicantProfileBean to DB.", e);
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

	public static void deleteApplicantNLESDPermanentExperienceBean(int id) throws JobOpportunityException {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.del_nlesd_perm_exp(?); end;");
			stat.setInt(1, id);
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void deleteApplicantNLESDPernamentExperienceBean(int id): " + e);
			throw new JobOpportunityException("Can not delete ApplicantNLESDPermanentExperienceBean to DB.", e);
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

	public static ApplicantNLESDPermanentExperienceBean[] getApplicantNLESDPermanentExperienceBeans(String sin)
	throws JobOpportunityException {
		Vector v_opps = null;
		ApplicantNLESDPermanentExperienceBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			v_opps = new Vector(5);
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_nlesd_perm_exp(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, sin);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				eBean = createApplicantNLESDPermanentExperienceBean(rs);
				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("ApplicantNLESDPermanentExperienceBean addApplicantNLESDPermanentExperienceBean(ApplicantNLESDPermanentExperienceBean abean): "
					+ e);
			throw new JobOpportunityException("Can not extract ApplicantNLESDPermanentExperienceBean from DB.", e);
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
		return (ApplicantNLESDPermanentExperienceBean[]) v_opps.toArray(new ApplicantNLESDPermanentExperienceBean[0]);
	}

	public static ApplicantNLESDPermanentExperienceBean createApplicantNLESDPermanentExperienceBean(ResultSet rs) {
		ApplicantNLESDPermanentExperienceBean aBean = null;
		try {
			aBean = new ApplicantNLESDPermanentExperienceBean();
			aBean.setId(rs.getInt("PK_ID"));
			aBean.setSIN(rs.getString("SIN"));
			aBean.setFrom(new java.util.Date(rs.getDate("FROM").getTime()));
			aBean.setTo(new java.util.Date(rs.getDate("TO").getTime()));
			aBean.setSchoolId(rs.getInt("SCHOOL_ID"));
			aBean.setGradesSubjects(rs.getString("GRDS_SUBS"));
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}

}
