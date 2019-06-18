package com.nlesd.schoolstatus.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;

import com.awsd.mail.bean.AlertBean;
import com.awsd.mail.bean.EmailException;
import com.awsd.weather.SchoolSystemException;
import com.esdnl.dao.DAOUtils;
import com.nlesd.schoolstatus.bean.SchoolStatusGlobalConfigBean;

public class SchoolStatusGlobalConfigService {

	private static SchoolStatusGlobalConfigBean GLOBAL_CONFIG_CACHE;

	static {
		try {
			SchoolStatusGlobalConfigService.GLOBAL_CONFIG_CACHE = getSchoolStatusGlobalConfigBean();
		}
		catch (SchoolSystemException e) {
			SchoolStatusGlobalConfigService.GLOBAL_CONFIG_CACHE = null;
		}
	}

	public static SchoolStatusGlobalConfigBean getSchoolStatusGlobalConfigBean() throws SchoolSystemException {

		SchoolStatusGlobalConfigBean config = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			if (SchoolStatusGlobalConfigService.GLOBAL_CONFIG_CACHE != null) {
				config = SchoolStatusGlobalConfigService.GLOBAL_CONFIG_CACHE;
			}
			else {
				con = DAOUtils.getConnection();

				stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_global_config; end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.execute();
				rs = (ResultSet) stat.getObject(1);

				if (rs.next()) {
					config = createSchoolStatusGlobalConfigBean(rs);

					SchoolStatusGlobalConfigService.GLOBAL_CONFIG_CACHE = config;
				}
			}
		}
		catch (SQLException e) {
			System.err.println("SchoolStatusGlobalConfigBean getSchoolStatusGlobalConfigBean(): " + e);
			throw new SchoolSystemException("Can not extract school status global config from DB: " + e);
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
		return config;
	}

	public static SchoolStatusGlobalConfigBean createSchoolStatusGlobalConfigBean(ResultSet rs) {

		SchoolStatusGlobalConfigBean config = null;

		try {
			config = new SchoolStatusGlobalConfigBean();

			config.setSummer(rs.getBoolean("summer_flag"));
		}
		catch (SQLException e) {
			try {
				new AlertBean(e);
			}
			catch (EmailException e1) {}

			config = null;
		}

		return config;
	}
}
