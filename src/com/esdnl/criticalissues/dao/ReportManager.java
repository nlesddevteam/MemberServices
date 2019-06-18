package com.esdnl.criticalissues.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.school.SchoolDB;
import com.esdnl.criticalissues.bean.CIException;
import com.esdnl.criticalissues.bean.ReportBean;
import com.esdnl.criticalissues.constant.ReportTypeConstant;
import com.esdnl.dao.DAOUtils;
import com.esdnl.survey.bean.SurveyException;

public class ReportManager {

	public static ReportBean addReportBean(ReportBean sbean) throws CIException {

		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin ? := awsd_user.critical_issues_pkg.add_report(?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);

			if (sbean.getReportDate() != null)
				stat.setDate(2, new java.sql.Date(sbean.getReportDate().getTime()));
			else
				stat.setNull(2, OracleTypes.DATE);

			stat.setInt(3, sbean.getSchool().getSchoolID());
			stat.setInt(4, sbean.getReportType().getValue());
			stat.setString(5, sbean.getFilename());

			stat.execute();

			sbean.setReportId(((OracleCallableStatement) stat).getInt(1));

			con.commit();
		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("ReportBean ReportManager.addReportBean(ReportBean sbean): " + e);
			e.printStackTrace();

			throw new CIException("Can not add ReportBean to DB.", e);
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

		return sbean;
	}

	public static void deleteReportBean(ReportBean sbean) throws CIException {

		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.critical_issues_pkg.delete_report(?); end;");

			stat.setInt(1, sbean.getReportId());

			stat.execute();

			con.commit();
		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void ReportManager.deleteReportBean(ReportBean sbean): " + e);
			e.printStackTrace();

			throw new CIException("Can not delete ReportBean to DB.", e);
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

	public static ReportBean getReportBean(int id) throws SurveyException {

		ReportBean report = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.critical_issues_pkg.get_report(?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				report = createReportBean(rs);
		}
		catch (SQLException e) {
			System.err.println("ReportBean ReportManager.getReportBean(int id): " + e);
			throw new SurveyException("Can not extract ReportBean from DB.", e);
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

		return report;
	}

	public static ReportBean[] getReportBeans(ReportTypeConstant type) throws SurveyException {

		Vector v_opps = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector(5);

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.critical_issues_pkg.get_reports_by_type(?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, type.getValue());

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				v_opps.add(createReportBean(rs));

		}
		catch (SQLException e) {
			System.err.println("ReportBean[] ReportManager.getReportBean(ReportConstantType type): " + e);
			throw new SurveyException("Can not extract ReportBean from DB.", e);
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

		return (ReportBean[]) v_opps.toArray(new ReportBean[0]);
	}

	public static ReportBean createReportBean(ResultSet rs) {

		ReportBean sBean = null;

		try {
			sBean = new ReportBean();

			sBean.setReportId(rs.getInt("REPORT_ID"));

			if (rs.getDate("REPORT_DATE") != null)
				sBean.setReportDate(new java.util.Date(rs.getDate("REPORT_DATE").getTime()));

			if (rs.getDate("DATE_ADDED") != null)
				sBean.setDateAdded(new java.util.Date(rs.getDate("DATE_ADDED").getTime()));

			sBean.setReportType(ReportTypeConstant.get(rs.getInt("REPORT_TYPE")));

			sBean.setSchool(SchoolDB.createSchoolBean(rs));
			sBean.setFilename(rs.getString("REPORT_FILENAME"));

			sBean.setOutstandingItems(rs.getInt("OUTSTANDING_ITEMS"));
		}
		catch (SQLException e) {
			e.printStackTrace();
			sBean = null;
		}

		return sBean;
	}

}
