package com.esdnl.servlet;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;

public class ApplicationPropertyManager {

	public static HashMap PROPERTIES = null;

	static {
		ApplicationPropertyManager.loadApplicationProperties();
	}

	public static void loadApplicationProperties() {

		ApplicationPropertyManager.PROPERTIES = ApplicationPropertyManager.getApplicationProperties();
	}

	public static HashMap getApplicationProperties() {

		HashMap props = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			props = new HashMap(5);

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.ms_security.get_application_properties; end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				props.put(rs.getString("NAME"), rs.getString("VALUE"));
		}
		catch (SQLException e) {
			System.err.println("JobOpportunityBean[] getJobOpportunityBeans(int location_id): " + e);
			e.printStackTrace();
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

		return props;
	}
}
