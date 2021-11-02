package com.esdnl.personnel.jobs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantSecurityBean;
import com.esdnl.personnel.jobs.bean.ApplicantSecurityException;
public class ApplicantSecurityManager {
	public static ApplicantSecurityBean addApplicantSecurityBean(ApplicantSecurityBean abean)
	throws ApplicantSecurityException {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.add_security_question(?,?,?); end;");
			stat.setString(1, abean.getSin());
			stat.setString(2, abean.getSecurity_question());
			stat.setString(3, abean.getSecurity_answer());
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("ApplicantSecurityBean addApplicantSecurityBean(ApplicantSecurityBean abean): "
					+ e);
			throw new ApplicantSecurityException("Can not add ApplicantSecurityBean to DB.", e);
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
	public static ApplicantSecurityBean getApplicantSecurityBean(String sin)
	throws ApplicantSecurityException {
		ApplicantSecurityBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_security_question(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, sin);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			if (rs.next())
				eBean = createApplicantSecurityBean(rs);
			else
				eBean = null;
		}
		catch (SQLException e) {
			System.err.println("ApplicantSecurityBean getApplicantSecurityBean(String sin): " + e);
			throw new ApplicantSecurityException("Can not extract ApplicantSecurityBean from DB.", e);
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
	public static ApplicantSecurityBean getApplicantSecurityBeanByEmail(String email)
	throws ApplicantSecurityException {
		ApplicantSecurityBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_security_info_by_email2(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, email);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			if (rs.next())
				eBean = createApplicantSecurityBean(rs);
			else
				eBean = null;
		}
		catch (SQLException e) {
			System.err.println("ApplicantSecurityBean getApplicantSecurityBeanByEmail(String sin): " + e);
			throw new ApplicantSecurityException("Can not extract ApplicantSecurityBean from DB.", e);
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
	public static ApplicantSecurityBean createApplicantSecurityBean(ResultSet rs) {
		ApplicantSecurityBean aBean = null;
		try {
			aBean = new ApplicantSecurityBean();
			aBean.setSin(rs.getString("SIN"));
			aBean.setPk_id(rs.getInt("PK_ID"));
			aBean.setSecurity_question(rs.getString("SECURITY_QUESTION"));
			aBean.setSecurity_answer(rs.getString("SECURITY_ANSWER"));
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}
	public static Boolean updatePasswordForApplicant(String email,String password)
	throws ApplicantSecurityException {
		Connection con = null;
		CallableStatement stat = null;
		Boolean iserror=false;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.update_password_for_applicant(?,?); end;");
			stat.setString(1,email);
			stat.setString(2, password);
			stat.execute();
			iserror=true;
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("ApplicantSecurityBean updatePasswordForApplicant(String email,String password): "
					+ e);
			throw new ApplicantSecurityException("Can not update applicant password to DB.", e);
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
		return iserror;
	}
}
