package com.esdnl.criticalissues.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.criticalissues.bean.CIException;
import com.esdnl.criticalissues.bean.ReportActionItemBean;
import com.esdnl.dao.DAOUtils;

public class ReportActionItemManager {

	public static ReportActionItemBean addReportActionItemBean(ReportActionItemBean sbean) throws CIException {

		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin ? := awsd_user.critical_issues_pkg.add_report_action_item(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);

			stat.setInt(2, sbean.getReport().getReportId());
			stat.setInt(3, sbean.getMaintenanceRequest().getRequestID());

			stat.execute();

			sbean.setItemId(((OracleCallableStatement) stat).getInt(1));

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

}
