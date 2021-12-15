package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.Covid19SDSStatusBean;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class Covid19SDSStatusManager {
	public static int addCovid19SDSStatus(Covid19SDSStatusBean abean) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.add_covid19_sds(?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setString(2, abean.getApplicantId());
			stat.setString(3, abean.getAddedBy());
			stat.setString(4, abean.getSdsId());
			stat.execute();
			id = ((OracleCallableStatement) stat).getInt(1);
		}
		catch (SQLException e) {
			System.err.println("static int addCovid19SDSStatus(Covid19SDSStatusBean abean) : " + e);
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
		return id;
	}
	public static Covid19SDSStatusBean getCovid19SDSStatus(String applicantid) {
		Covid19SDSStatusBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_covid_sds_status(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, applicantid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				eBean = createCovid19SDSStatusBean(rs);
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<Covid19ReportBean> getLatestCovid19VaccinationsByDays(int numdays) : "
					+ e);
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
	public static String getSDSId(String applicantid,String emaila) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		String s = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_sds_id(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, applicantid);
			stat.setString(3, emaila.toLowerCase());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				s=rs.getString("EMP_ID");
			}
		}
		catch (SQLException e) {
			System.err.println("String getSDSId(String applicantid,String emaila) : "
					+ e);
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
		
		return s;
	}		
	public static Covid19SDSStatusBean createCovid19SDSStatusBean(ResultSet rs) {

		Covid19SDSStatusBean abean = null;
		try {
			abean = new Covid19SDSStatusBean();
			abean.setId(rs.getInt("CSS_ID"));
			abean.setApplicantId(rs.getString("APPLICANT_ID"));
			abean.setAddedBy(rs.getString("ADDED_BY"));
			abean.setDateAdded(new Date(rs.getTimestamp("DATE_ADDED").getTime()));
			abean.setSdsId(rs.getString("SDS_ID"));
		}
		catch (SQLException e) {
			abean = null;
		}

		return abean;
	}
}
