package com.esdnl.fund3.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.awsd.mail.bean.EmailBean;
import com.esdnl.dao.DAOUtils;
import com.esdnl.fund3.bean.Fund3Exception;
import com.esdnl.fund3.bean.ProjectBean;
import com.esdnl.velocity.VelocityUtils;
public class ProjectReportManager {
	public static final String DOCUMENT_BASEPATH = "WEB-INF/uploads/fund3/";
	private String serverpath;
	public static void getMonthlyReports() throws Fund3Exception {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_fund3_get_monthly_reports; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				createReportAndEmail(rs);
			}
		}
		catch (Exception e) {
			System.err.println("void getMonthlyReports() " + e);
			throw new Fund3Exception("Can not extract monthly report from DB: " + e);
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
		
	}
	public static void getOneTimeReports() throws Fund3Exception {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_fund3_get_one_time_reports; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				createReportAndEmail(rs);
			}
		}
		catch (Exception e) {
			System.err.println("void getOneTimeReports() " + e);
			throw new Fund3Exception("Can not extract monthly report from DB: " + e);
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
		
	}
	public static void getAnnualReports() throws Fund3Exception {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_fund3_get_annual_reports; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				createReportAndEmail(rs);
			}
		}
		catch (Exception e) {
			System.err.println("void getAnnualReports() " + e);
			throw new Fund3Exception("Can not extract monthly report from DB: " + e);
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
		
	}
	public static void getSemiAnnualReports() throws Fund3Exception {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_fund3_get_s_annual_reports; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				createReportAndEmail(rs);
			}
		}
		catch (Exception e) {
			System.err.println("void getSemiAnnualReports() " + e);
			throw new Fund3Exception("Can not extract monthly report from DB: " + e);
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
		
	}
	public static void getQuarterlyReports() throws Fund3Exception {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_fund3_get_quart_reports; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				createReportAndEmail(rs);
			}
		}
		catch (Exception e) {
			System.err.println("void getQuarterlyReports() " + e);
			throw new Fund3Exception("Can not extract monthly report from DB: " + e);
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
		
	}	
	public static void createReportAndEmail(ResultSet rs) {
		try {
				//retrieve email
				String email=rs.getString("REPORT_EMAIL");
				Integer projectId=rs.getInt("PROJECT_ID");
				ProjectBean pb = ProjectManager.getProjectById(projectId);
				//fund3_project_report.vm
				//email exists, send stub
				EmailBean ebean = new EmailBean();
				HashMap<String, Object> model = new HashMap<String, Object>();
				// set values to be used in template
				model.put("project",pb.getProjectName() + "(" + pb.getProjectNumber() + ")" );
				ebean.setSubject("Reminder: Generate FUND 3 Project Report For: " + pb.getProjectName() + "(" + pb.getProjectNumber() + ")");
				ebean.setTo(email);
				ebean.setFrom("fund3@nlesd.ca");
				ebean.setBody(VelocityUtils.mergeTemplateIntoString("fund3/fund3_project_report.vm", model));
				ebean.send();
		}
		catch (Exception e) {
		
		}

		
	}
	public String getServerpath() {

		return serverpath;
	}

	public void setServerpath(String serverpath) {

		this.serverpath = serverpath;
	}	
}
