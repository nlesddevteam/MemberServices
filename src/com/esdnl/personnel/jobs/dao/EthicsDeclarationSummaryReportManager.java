package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.EthicsDeclarationSummaryReportBean;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class EthicsDeclarationSummaryReportManager {
	public static EthicsDeclarationSummaryReportBean getSummaryReport() {
		EthicsDeclarationSummaryReportBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_ethics_dec_summary; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				eBean = createEthicsDeclarationSummaryReportBean(rs);
			}
		}
		catch (SQLException e) {
			System.err.println("static EthicsDeclarationSummaryReportBean getSummaryReport() : "
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
	public static EthicsDeclarationSummaryReportBean createEthicsDeclarationSummaryReportBean (ResultSet rs) {

		EthicsDeclarationSummaryReportBean abean = null;
		try {
			abean = new EthicsDeclarationSummaryReportBean();
			
			abean.setTotalUploaded(rs.getInt("totalcount"));
			abean.setTotalNLESD(rs.getInt("nlesdcount"));
			abean.setTotalLatest(rs.getInt("latestcount"));
			
		}
		catch (SQLException e) {
			abean = null;
		}

		return abean;
	}
}
