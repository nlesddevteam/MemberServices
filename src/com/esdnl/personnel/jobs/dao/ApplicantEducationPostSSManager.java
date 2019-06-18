package com.esdnl.personnel.jobs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;
import java.util.Vector;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantEducationPostSSBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
public class ApplicantEducationPostSSManager {

	public static ApplicantEducationPostSSBean addApplicantEducationPostSSBean(ApplicantEducationPostSSBean abean)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.add_applicant_edu_post_ss(?,?,?,?,?,?,?,?,?); end;");

			stat.setString(1, abean.getSin());
			stat.setDate(2, new java.sql.Date(abean.getFrom().getTime()));
			stat.setDate(3, new java.sql.Date(abean.getTo().getTime()));
			stat.setString(4, abean.getProgram());
			stat.setInt(5, abean.getMajor());
			stat.setInt(6, abean.getMinor());
			stat.setString(7, abean.getDegree());
			stat.setString(8, abean.getInstitution());
			stat.setInt(9,abean.getCtype());
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("ApplicantEducationPostSSBean addApplicantEducationPostSSBean(ApplicantEducationPostSSBean abean): " + e);
			throw new JobOpportunityException("Can not add ApplicantEducationPostSSBean to DB.", e);
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

	public static void deleteApplicantEducationPostSSBean(int id) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.del_applicant_edu_post_ss(?); end;");

			stat.setInt(1, id);

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void deleteApplicantEducationPostSSBean(int id): " + e);
			throw new JobOpportunityException("Can not delete ApplicantEducationPostSSBean to DB.", e);
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

	public static ApplicantEducationPostSSBean[] getApplicantEducationPostSSBeans(String sin) throws JobOpportunityException {

		Vector v_opps = null;
		ApplicantEducationPostSSBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_applicant_edu_post_sec(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, sin);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantEducationPostSSBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("ApplicantEducationPostSSBean[] getApplicantEducationPostSSBeans(String sin): " + e);
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

		return (ApplicantEducationPostSSBean[]) v_opps.toArray(new ApplicantEducationPostSSBean[0]);
	}
	public static TreeMap<Integer,String> getDiplomaCertValues(int ctype) throws JobOpportunityException {

		TreeMap<Integer,String> tmap = new TreeMap<Integer,String>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_dropdown_values_ss(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, ctype);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				tmap.put(rs.getInt("ID"),rs.getString("TITLE"));
			}
		}
		catch (SQLException e) {
			System.err.println("HashMap<Integer,String> getDiplomaCertValues(int ctype): " + e);
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

		return tmap;
	}
	public static String getDiplomaCertTitleById(int ctype) throws JobOpportunityException {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		String title ="";
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_dropdown_value_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, ctype);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				title = rs.getString("TITLE");
			}
		}
		catch (SQLException e) {
			System.err.println("HashMap<Integer,String> getDiplomaCertValues(int ctype): " + e);
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

		return title;
	}
	public static ApplicantEducationPostSSBean createApplicantEducationPostSSBean(ResultSet rs) {

		ApplicantEducationPostSSBean aBean = null;
		try {
			aBean = new ApplicantEducationPostSSBean();

			aBean.setId(rs.getInt("PK_ID"));
			aBean.setSin(rs.getString("SIN"));
			aBean.setFrom(new java.util.Date(rs.getDate("FROMDATE").getTime()));
			aBean.setTo(new java.util.Date(rs.getDate("TODATE").getTime()));
			aBean.setInstitution(rs.getString("INSTITUTION"));
			aBean.setProgram(rs.getString("PROGRAM_FACULTY"));
			aBean.setMajor(rs.getInt("MAJOR_ID"));
			aBean.setMinor(rs.getInt("MINOR_ID"));
			aBean.setDegree(rs.getString("DEGREE_ID"));
			aBean.setCtype(rs.getInt("CTYPE"));
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}
}
