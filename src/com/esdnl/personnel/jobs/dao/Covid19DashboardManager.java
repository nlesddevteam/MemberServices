package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.Covid19DashboardBean;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class Covid19DashboardManager {
	public static Covid19DashboardBean getCovid19DashboardReport() {
		
		Covid19DashboardBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		
		try {
			//first we get the nlesd details
			
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_covid_dash_nlesd; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			eBean = new Covid19DashboardBean();
			while (rs.next()) {
				eBean.setNlesdTotalEmployees(rs.getInt("totale"));
				eBean.setNlesdDocumentsUploaded(rs.getInt("docup"));
				eBean.setNlesdDocumentsVerified(rs.getInt("dvcount"));
				eBean.setNlesdDocumentsNotVerified(rs.getInt("dnvcount"));
				eBean.setNlesdDocumentsRejected(rs.getInt("rejcount"));
				eBean.setNlesdDocumentsExemptions(rs.getInt("excount"));
			}
			stat.close();
			rs.close();
			//now we get the non nlesd totals
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_covid_dash_non_nlesd; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
		
			while (rs.next()) {
				eBean.setTotalEmployees(rs.getInt("totale"));
				eBean.setDocumentsUploaded(rs.getInt("docup"));
				eBean.setDocumentsVerified(rs.getInt("dvcount"));
				eBean.setDocumentsNotVerified(rs.getInt("dnvcount"));
				eBean.setDocumentsRejected(rs.getInt("rejcount"));
				eBean.setDocumentsExemptions(rs.getInt("excount"));
			}
			stat.close();
			rs.close();
			// now we get total employees
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_covid_dash_emp_count; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			
			while (rs.next()) {
				eBean.setActiveEmployees(rs.getInt("totale"));
				
			}
			// now we nlesd special status
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_sds_ss_counts; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				eBean.setNlesdSpecialStatus(rs.getInt("sscount"));
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<Covid19CountsReportBean> getCovid19CountsReport(): "
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
}
