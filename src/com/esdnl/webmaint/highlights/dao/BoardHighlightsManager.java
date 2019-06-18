package com.esdnl.webmaint.highlights.dao;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.servlet.ControllerServlet;
import com.esdnl.dao.DAOUtils;
import com.esdnl.webmaint.highlights.beans.HighlightsException;

public class BoardHighlightsManager {

	private static String DATA_WAREHOUSE_JDBC_URL;
	private static String DATA_WAREHOUSE_JDBC_USERNAME;
	private static String DATA_WAREHOUSE_JDBC_PASSWORD;

	static {
		boolean loaded = false;
		try {
			Class.forName("com.awsd.servlet.ControllerServlet");

			DATA_WAREHOUSE_JDBC_URL = ControllerServlet.DATA_WAREHOUSE_JDBC_URL;
			DATA_WAREHOUSE_JDBC_USERNAME = ControllerServlet.DATA_WAREHOUSE_JDBC_USERNAME;
			DATA_WAREHOUSE_JDBC_PASSWORD = ControllerServlet.DATA_WAREHOUSE_JDBC_PASSWORD;

			loaded = true;

		}
		catch (ClassNotFoundException e) {
			loaded = false;
		}

		if (!loaded) {
			try {
				InputStream istr = BoardHighlightsManager.class.getResourceAsStream("connection.properties");
				Properties prop = new Properties();
				prop.load(istr);

				Class.forName(prop.getProperty("jdbc.driver"));

				DATA_WAREHOUSE_JDBC_URL = prop.getProperty("jdbc.url");
				DATA_WAREHOUSE_JDBC_USERNAME = prop.getProperty("jdbc.username");
				DATA_WAREHOUSE_JDBC_PASSWORD = prop.getProperty("jdbc.password");
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean addHighlights(java.util.Date meeting_date) throws Exception {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);
			stat = con.prepareCall("begin awsd_user.esd_web.insert_highlights(?); end;");
			stat.setDate(1, new Date(meeting_date.getTime()));
			stat.execute();
			stat.close();

			check = true;
		}
		catch (SQLException e) {
			check = false;
			System.err.println("BoardHighlightsManager.addHighlights(java.util.Date meeting_date): " + e);
			throw new HighlightsException("Can not add Highlights to DB: " + e);
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
		return (check);
	}

	public static Vector getHighlights() throws HighlightsException {

		Vector mins = null;
		java.util.Date dt = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			mins = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.esd_web.get_highlights; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				mins.add(new java.util.Date(rs.getDate("DOC_DATE").getTime()));
		}
		catch (SQLException e) {
			System.err.println("BoardHighlightsManager.getHighlights(): " + e);
			throw new HighlightsException("Can not extract Highlights from DB: " + e);
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
		return mins;
	}

	public static boolean deleteHighlights(java.util.Date dt) throws SQLException {

		Connection con = null;
		CallableStatement stat = null;
		File f = null;

		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);
			stat = con.prepareCall("begin awsd_user.esd_web.delete_highlights(?); end;");
			stat.setDate(1, new Date(dt.getTime()));
			stat.execute();
			stat.close();

			check = true;
		}
		catch (SQLException e) {
			check = false;
			System.err.println("BoardHighlightsManager.deleteHighlights(java.util.Date dt): " + e);
			throw e;
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
		return (check);
	}
}