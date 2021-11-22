package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.Covid19CountsReportBean;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class Covid19CountsReportManager {
	public static ArrayList<Covid19CountsReportBean> getCovid19CountsReport() {
		ArrayList<Covid19CountsReportBean> v_opps = null;
		Covid19CountsReportBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		
		try {
			v_opps = new ArrayList<Covid19CountsReportBean>(3);
		
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_covid_location_counts; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
		
			while (rs.next()) {
				eBean = createCovid19CountsReportBeanBean(rs);
		
				v_opps.add(eBean);
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
		
		return v_opps;
	}
	public static Covid19CountsReportBean createCovid19CountsReportBeanBean(ResultSet rs) {

		Covid19CountsReportBean abean = null;
		try {
			abean = new Covid19CountsReportBean();
			
			abean.setLocation(rs.getString("LOCATION"));
			abean.setLocationCount(rs.getInt("LOCCOUNT"));
			abean.setDocumentCount(rs.getInt("DCOUNTS"));
			abean.setNotVerifiedCount(rs.getInt("NVCOUNT"));
			abean.setVerifiedCount(rs.getInt("VCOUNT"));
			abean.setRejectCount(rs.getInt("RCOUNT"));
		}
		catch (SQLException e) {
			abean = null;
		}

		return abean;
	}
}
