package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.personnel.Personnel;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.ReferenceBean;

public class ReferenceManager {

	public static ReferenceBean getReferenceBean(int id) throws JobOpportunityException {

		ReferenceBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_ref_chk(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createReferenceBean(rs);

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

	public static ReferenceBean getReferenceBean(ApplicantProfileBean profile, Personnel by)
			throws JobOpportunityException {

		ReferenceBean ref = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_appl_ref_chk(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, profile.getUID());
			stat.setString(3, by.getFullName());
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				ref = createReferenceBean(rs);

		}
		catch (SQLException e) {
			System.err.println("ReferenceCheckRequestManager.getReferenceBean(ApplicantProfileBean profile, Personnel by): "
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

		return ref;
	}

	public static ReferenceBean[] getReferenceBeans(ApplicantProfileBean profile) throws JobOpportunityException {

		ArrayList<ReferenceBean> refs = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			refs = new ArrayList<ReferenceBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_appl_ref_chks(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, profile.getUID());
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				refs.add(createReferenceBean(rs));

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

		return (ReferenceBean[]) refs.toArray(new ReferenceBean[0]);
	}

	public static ArrayList<ReferenceBean> getReferenceBeans(Personnel by) throws JobOpportunityException {

		ArrayList<ReferenceBean> refs = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			refs = new ArrayList<ReferenceBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_appl_ref_chks_by(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, by.getFullName());
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				refs.add(createReferenceBean(rs));

		}
		catch (SQLException e) {
			System.err.println("ReferenceCheckRequestManager.getReferenceBeans(Personnel by): " + e);
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

		return refs;
	}

	public static ReferenceBean addReferenceBean(ReferenceBean abean) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.add_ref_chk(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);

			stat.setString(2, abean.getReferenceProviderName());
			stat.setString(3, abean.getReferenceProviderPosition());

			stat.setString(4, abean.getQ1());
			stat.setString(5, abean.getQ2());
			stat.setString(6, abean.getQ3());
			stat.setString(7, abean.getQ4());
			stat.setString(8, abean.getQ5());
			stat.setString(9, abean.getQ6());
			stat.setString(10, abean.getQ7());
			stat.setString(11, abean.getQ7Comment());
			stat.setString(12, abean.getQ8());
			stat.setString(13, abean.getQ9());
			stat.setString(14, abean.getQ9Comment());
			stat.setString(15, abean.getQ10());

			stat.setString(16, abean.getScale1());
			stat.setString(17, abean.getScale2());
			stat.setString(18, abean.getScale3());
			stat.setString(19, abean.getScale4());
			stat.setString(20, abean.getScale5());
			stat.setString(21, abean.getScale6());
			stat.setString(22, abean.getScale7());
			stat.setString(23, abean.getScale8());
			stat.setString(24, abean.getScale9());
			stat.setString(25, abean.getScale10());
			stat.setString(26, abean.getApplicantId());

			stat.execute();

			int id = ((OracleCallableStatement) stat).getInt(1);

			abean.setId(id);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("ReferenceBean addReferenceBean(ReferenceBean abean): " + e);
			throw new JobOpportunityException("Can not add ReferenceBean to DB.", e);
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

	public static ReferenceBean updateReferenceBean(ReferenceBean abean) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.mod_ref_chk(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");

			stat.setInt(1, abean.getId());
			stat.setString(2, abean.getReferenceProviderName());
			stat.setString(3, abean.getReferenceProviderPosition());

			stat.setString(4, abean.getQ1());
			stat.setString(5, abean.getQ2());
			stat.setString(6, abean.getQ3());
			stat.setString(7, abean.getQ4());
			stat.setString(8, abean.getQ5());
			stat.setString(9, abean.getQ6());
			stat.setString(10, abean.getQ7());
			stat.setString(11, abean.getQ7Comment());
			stat.setString(12, abean.getQ8());
			stat.setString(13, abean.getQ9());
			stat.setString(14, abean.getQ9Comment());
			stat.setString(15, abean.getQ10());

			stat.setString(16, abean.getScale1());
			stat.setString(17, abean.getScale2());
			stat.setString(18, abean.getScale3());
			stat.setString(19, abean.getScale4());
			stat.setString(20, abean.getScale5());
			stat.setString(21, abean.getScale6());
			stat.setString(22, abean.getScale7());
			stat.setString(23, abean.getScale8());
			stat.setString(24, abean.getScale9());
			stat.setString(25, abean.getScale10());
			stat.setString(26, abean.getApplicantId());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("ReferenceBean addReferenceBean(ReferenceBean abean): " + e);
			throw new JobOpportunityException("Can not add ReferenceBean to DB.", e);
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

	public static ReferenceBean createReferenceBean(ResultSet rs) {

		ReferenceBean abean = null;

		try {
			abean = new ReferenceBean();

			abean.setId(rs.getInt("REFERENCE_ID"));

			abean.setReferenceProviderName(rs.getString("PROVIDED_BY"));
			abean.setReferenceProviderPosition(rs.getString("PROVIDER_POSITION"));
			abean.setProvidedDate(new java.util.Date(rs.getTimestamp("DATE_PROVIDED").getTime()));

			abean.setQ1(rs.getString("Q1"));
			abean.setQ10(rs.getString("Q10"));
			abean.setQ2(rs.getString("Q2"));
			abean.setQ3(rs.getString("Q3"));
			abean.setQ4(rs.getString("Q4"));
			abean.setQ5(rs.getString("Q5"));
			abean.setQ6(rs.getString("Q6"));
			abean.setQ7(rs.getString("Q7"));
			abean.setQ7Comment(rs.getString("Q7_COMMENT"));
			abean.setQ8(rs.getString("Q8"));
			abean.setQ9(rs.getString("Q9"));
			abean.setQ9Comment(rs.getString("Q9_COMMENT"));
			abean.setQ10(rs.getString("Q10"));

			abean.setScale1(rs.getString("SCALE1"));
			abean.setScale2(rs.getString("SCALE2"));
			abean.setScale3(rs.getString("SCALE3"));
			abean.setScale4(rs.getString("SCALE4"));
			abean.setScale5(rs.getString("SCALE5"));
			abean.setScale6(rs.getString("SCALE6"));
			abean.setScale7(rs.getString("SCALE7"));
			abean.setScale8(rs.getString("SCALE8"));
			abean.setScale9(rs.getString("SCALE9"));
			abean.setScale10(rs.getString("SCALE10"));

			abean.setApplicant(ApplicantProfileManager.createApplicantProfileBean(rs));
		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}

}
