package com.nlesd.bcs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.ApplicationSettingsBean;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class ApplicationSettingsManager {
	public static ApplicationSettingsBean getApplicationSettings() {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ApplicationSettingsBean ebean = new ApplicationSettingsBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_app_settings; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createApplicationSettingsBean(rs);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ApplicationSettingsBean getApplicationSettings():"
					+ e);
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
		return ebean;
	}
	public static void updateWeeklyReportStatus(int status) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.bcs_pkg.update_weekly_status(?); end;");
			stat.setInt(1, status);
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void updateWeeklyReportStatus(int status): "
					+ e);
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
	public static ApplicationSettingsBean createApplicationSettingsBean(ResultSet rs)  {
		ApplicationSettingsBean abean = new ApplicationSettingsBean();
		try {
			abean.setId(rs.getInt("ID"));
			if(rs.getInt("WEEKLY_REPORT") == 0) {
				abean.setRunWeeklyReport(false);
			}else {
				abean.setRunWeeklyReport(true);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			abean.setId(1);
			abean.setRunWeeklyReport(false);
		}
		

		return abean;
	}
}
