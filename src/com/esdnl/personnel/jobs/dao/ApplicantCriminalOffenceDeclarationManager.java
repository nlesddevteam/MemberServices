package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantCriminalOffenceDeclarationBean;
import com.esdnl.personnel.jobs.bean.ApplicantCriminalOffenceDeclarationOffenceBean;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;

public class ApplicantCriminalOffenceDeclarationManager {

	public static ApplicantCriminalOffenceDeclarationBean addApplicantCriminalOffenceDeclarationBean(	ApplicantCriminalOffenceDeclarationBean abean)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.add_applicant_cod(?,?,?,?); end;");

			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2, abean.getApplicant().getUID());
			stat.setTimestamp(3, new Timestamp(abean.getDeclarationDate().getTime()));
			stat.setString(4, abean.getPosition());
			stat.setString(5, abean.getLocation());

			stat.execute();

			int id = stat.getInt(1);

			if (id > 0) {
				abean.setDeclarationId(id);

				stat.close();

				if (abean.getOffences() != null && abean.getOffences().size() > 0) {

					stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.add_applicant_codo(?,?,?,?); end;");

					for (ApplicantCriminalOffenceDeclarationOffenceBean codo : abean.getOffences()) {
						stat.clearParameters();

						stat.setInt(1, id);
						stat.setDate(2, new java.sql.Date(codo.getOffenceDate().getTime()));
						stat.setString(3, codo.getCourtLocation());
						stat.setString(4, codo.getConviction());

						stat.execute();
					}

					stat.close();
				}

				con.commit();
			}
			else {
				con.rollback();
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("ApplicantCriminalOffenceDeclarationBean addApplicantCriminalOffenceDeclarationBean(ApplicantCriminalOffenceDeclarationBean abean): "
					+ e);
			throw new JobOpportunityException("Can not add ApplicantCriminalOffenceDeclarationBean to DB.", e);
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

	public static void deleteApplicantCriminalOffenceDeclarationBean(int id) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.del_applicant_cod(?); end;");

			stat.setInt(1, id);

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void deleteApplicantCriminalOffenceDeclarationBean(int id): " + e);
			throw new JobOpportunityException("Can not delete ApplicantCriminalOffenceDeclarationBean to DB.", e);
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

	public static ApplicantCriminalOffenceDeclarationBean getApplicantCriminalOffenceDeclarationBean(int declarationID)
			throws JobOpportunityException {

		ApplicantCriminalOffenceDeclarationBean doc = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_applicant_cod(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, declarationID);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				doc = createApplicantCriminalOffenceDeclarationBean(rs);
		}
		catch (SQLException e) {
			System.err.println("ApplicantCriminalOffenceDeclarationBean getApplicantCriminalOffenceDeclarationBean(int declarationID): "
					+ e);
			throw new JobOpportunityException("Can not extract ApplicantCriminalOffenceDeclarationBean from DB.", e);
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

		return doc;
	}

	public static Collection<ApplicantCriminalOffenceDeclarationBean> getApplicantCriminalOffenceDeclarationBeans(ApplicantProfileBean profile)
			throws JobOpportunityException {

		ArrayList<ApplicantCriminalOffenceDeclarationBean> v_opps = null;
		ApplicantCriminalOffenceDeclarationBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new ArrayList<ApplicantCriminalOffenceDeclarationBean>(3);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_applicant_cods(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, profile.getUID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantCriminalOffenceDeclarationBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("Collection<ApplicantCriminalOffenceDeclarationBean> getApplicantCriminalOffenceDeclarationBeans(ApplicantProfileBean profile): "
					+ e);
			throw new JobOpportunityException("Can not extract ApplicantCriminalOffenceDeclarationBean from DB.", e);
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

	public static Collection<ApplicantCriminalOffenceDeclarationOffenceBean> getApplicantCriminalOffenceDeclarationOffenceBeans(ApplicantCriminalOffenceDeclarationBean declaration)
			throws JobOpportunityException {

		ArrayList<ApplicantCriminalOffenceDeclarationOffenceBean> beans = null;
		ApplicantCriminalOffenceDeclarationOffenceBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new ArrayList<ApplicantCriminalOffenceDeclarationOffenceBean>(3);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_applicant_codos(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, declaration.getDeclarationId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantCriminalOffenceDeclarationOffenceBean(rs);
				eBean.setDeclaration(declaration);

				beans.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("Collection<ApplicantCriminalOffenceDeclarationOffenceBean> getApplicantCriminalOffenceDeclarationOffenceBeans(ApplicantCriminalOffenceDeclarationBean declaration): "
					+ e);
			throw new JobOpportunityException("Can not extract ApplicantCriminalOffenceDeclarationOffenceBean from DB.", e);
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

	public static ApplicantCriminalOffenceDeclarationOffenceBean createApplicantCriminalOffenceDeclarationOffenceBean(ResultSet rs) {

		ApplicantCriminalOffenceDeclarationOffenceBean abean = null;
		try {
			abean = new ApplicantCriminalOffenceDeclarationOffenceBean();

			abean.setOffenceId(rs.getInt("OFFENCE_ID"));
			if (rs.getDate("OFFENCE_DATE") != null)
				abean.setOffenceDate(new Date(rs.getDate("OFFENCE_DATE").getTime()));
			else
				abean.setOffenceDate(null);
			abean.setCourtLocation(rs.getString("COURT_LOCATION"));
			abean.setConviction(rs.getString("CONVICTION"));
		}
		catch (SQLException e) {
			abean = null;
		}

		return abean;
	}

	public static ApplicantCriminalOffenceDeclarationBean createApplicantCriminalOffenceDeclarationBean(ResultSet rs)
			throws JobOpportunityException {

		ApplicantCriminalOffenceDeclarationBean abean = null;
		try {
			abean = new ApplicantCriminalOffenceDeclarationBean();

			abean.setDeclarationId(rs.getInt("DECLARATION_ID"));
			if (rs.getTimestamp("DECLARATION_DATE") != null)
				abean.setDeclarationDate(new Date(rs.getTimestamp("DECLARATION_DATE").getTime()));
			else
				abean.setDeclarationDate(null);
			abean.setApplicant(ApplicantProfileManager.createApplicantProfileBean(rs));
			abean.setLocation(rs.getString("LOCATION_SCHOOL"));
			abean.setPosition(rs.getString("POSITION"));

			abean.setOffences(getApplicantCriminalOffenceDeclarationOffenceBeans(abean));
		}
		catch (SQLException e) {
			abean = null;
		}

		return abean;
	}
}