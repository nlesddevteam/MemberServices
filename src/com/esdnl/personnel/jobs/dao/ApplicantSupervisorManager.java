package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantSupervisorBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;

public class ApplicantSupervisorManager {

	public static ApplicantSupervisorBean addApplicantSupervisorBean(ApplicantSupervisorBean abean)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.add_appl_supervisor(?,?,?,?,?); end;");

			stat.setString(1, abean.getSIN());
			stat.setString(2, abean.getName());
			stat.setString(3, abean.getTitle());
			stat.setString(4, abean.getAddress());
			stat.setString(5, abean.getTelephone());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("ApplicantSupervisorBean addApplicantSupervisorBean(ApplicantSupervisorBean abean): " + e);
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

	public static void deleteApplicantSupervisorBean(int id) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.del_supervisor(?); end;");

			stat.setInt(1, id);

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void deleteApplicantSupervisorBean(int id): " + e);
			throw new JobOpportunityException("Can not delete ApplicantSupervisorBean to DB.", e);
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

	public static ApplicantSupervisorBean[] getApplicantSupervisorBeans(String sin) throws JobOpportunityException {

		Vector v_opps = null;
		ApplicantSupervisorBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_appl_supervisors(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, sin);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantSupervisorBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("ApplicantSupervisorBean[] getApplicantSupervisorBeans(String sin): " + e);
			throw new JobOpportunityException("Can not extract ApplicantEsdReplacementExperienceBean from DB.", e);
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

		return (ApplicantSupervisorBean[]) v_opps.toArray(new ApplicantSupervisorBean[0]);
	}

	public static ApplicantSupervisorBean createApplicantSupervisorBean(ResultSet rs) {

		ApplicantSupervisorBean aBean = null;
		try {
			aBean = new ApplicantSupervisorBean();

			aBean.setId(rs.getInt("PK_ID"));
			aBean.setSIN(rs.getString("SIN"));
			aBean.setName(rs.getString("FULLNAME"));
			aBean.setTitle(rs.getString("TITLE"));
			aBean.setAddress(rs.getString("ADDRESS"));
			aBean.setTelephone(rs.getString("TELEPHONE"));
			//now we get the other info for new refs
			aBean.setApplicantRefRequestBeanBean(ApplicantRefRequestManager.getApplicantRefRequestBeanByRef(aBean.getId()));
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}
}