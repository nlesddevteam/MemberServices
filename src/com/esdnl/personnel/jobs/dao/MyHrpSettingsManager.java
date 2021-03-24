package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.MyHrpSettingsBean;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class MyHrpSettingsManager {
	public static MyHrpSettingsBean getMyHrpSettings() {

		MyHrpSettingsBean jBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_mhrp_settings; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			if (rs.next()) {
				jBean = createMyHrpSettingsBean(rs);
			}
		}
		catch (SQLException e) {
			System.err.println("static MyHrpSettingsBean getMyHrpSettings() " + e);
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

		return jBean;
	}
	public static void updateMyHrpSettings(MyHrpSettingsBean jbean) {

		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.update_hrp_settings(?); end;");
			if(jbean.ppBlockSchools) {
				stat.setInt(1, 1);
			}else {
				stat.setInt(1, 0);
			}
			
			stat.execute();
		}
		catch (SQLException e) {
			System.err.println("updateMyHrpSettings(MyHrpSettingsBean jbean) " + e);
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
	public static MyHrpSettingsBean createMyHrpSettingsBean(ResultSet rs) {
		MyHrpSettingsBean abean = null;
		try {
			abean = new MyHrpSettingsBean();
			abean.setPpBlockSchools(rs.getInt("PP_BLOCK_SCHOOLS") == 0 ? false:true);
		}catch (SQLException e) {
			e.printStackTrace();
			abean = null;
		}
		catch (Exception e) {
			e.printStackTrace();
			abean = null;
		}
		return abean;
	}		
}
