package com.esdnl.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DAOUtils {

	private static String DATA_WAREHOUSE_JDBC_URL;
	private static String DATA_WAREHOUSE_JDBC_USERNAME;
	private static String DATA_WAREHOUSE_JDBC_PASSWORD;

	static {

		try {
			InputStream istr = DAOUtils.class.getResourceAsStream("/com/connection.properties");
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

	public static Connection getConnection() throws SQLException {

		Connection conn = null;

		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/avalon");
			conn = ds.getConnection();
		}
		catch (NamingException e) {
			System.err.println("MemberServices: " + e.getMessage());

			conn = DriverManager.getConnection(DATA_WAREHOUSE_JDBC_URL, DATA_WAREHOUSE_JDBC_USERNAME,
					DATA_WAREHOUSE_JDBC_PASSWORD);
		}

		return conn;

	}
}
