package com.esdnl.webmaint.ceoweb.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Properties;
import java.util.TreeMap;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.servlet.ControllerServlet;
import com.esdnl.dao.DAOUtils;
import com.esdnl.webmaint.ceoweb.bean.CeoWebException;
import com.esdnl.webmaint.ceoweb.bean.SchoolVisitBean;

public class SchoolVisitManager {

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
				InputStream istr = SchoolVisitManager.class.getResourceAsStream("connection.properties");
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

	public static SchoolVisitBean[] getSchoolVisitBeans(boolean archived) throws CeoWebException {

		Vector v_opps = null;
		SchoolVisitBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector(5);

			con = DAOUtils.getConnection();
			if (archived)
				stat = con.prepareCall("begin ? := awsd_user.ceo_web.get_archived_school_visits; end;");
			else
				stat = con.prepareCall("begin ? := awsd_user.ceo_web.get_school_visits; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createSchoolVisitBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("SchoolVisitManager.getSchoolVisitBeans(): " + e);
			throw new CeoWebException("Can not extract SchoolVisitBean from DB.", e);
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

		return (SchoolVisitBean[]) v_opps.toArray(new SchoolVisitBean[0]);
	}

	public static SchoolVisitBean getSchoolVisitBean(int visit_id) throws CeoWebException {

		SchoolVisitBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.ceo_web.get_school_visit(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, visit_id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createSchoolVisitBean(rs);

		}
		catch (SQLException e) {
			System.err.println("SchoolVisitManager.getSchoolVisitBeans(): " + e);
			throw new CeoWebException("Can not extract SchoolVisitBean from DB.", e);
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

	public static TreeMap getSchoolVisitBeansMap(boolean archived) throws CeoWebException {

		TreeMap years = null;
		Vector v_opps = null;
		SchoolVisitBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		Calendar cal = null;

		try {
			years = new TreeMap(new Comparator() {

				public int compare(Object o1, Object o2) {

					return ((Integer) o1).compareTo((Integer) o2) * -1;
				}
			});

			cal = Calendar.getInstance();

			con = DAOUtils.getConnection();
			if (archived)
				stat = con.prepareCall("begin ? := awsd_user.ceo_web.get_archived_school_visits; end;");
			else
				stat = con.prepareCall("begin ? := awsd_user.ceo_web.get_school_visits; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createSchoolVisitBean(rs);

				cal.setTime(eBean.getVisitDate());

				if (years.containsKey(new Integer(cal.get(Calendar.YEAR)))) {
					v_opps = (Vector) years.get(new Integer(cal.get(Calendar.YEAR)));
				}
				else {
					v_opps = new Vector();
					years.put(new Integer(cal.get(Calendar.YEAR)), v_opps);
				}

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("SchoolVisitManager.getSchoolVisitBeans(): " + e);
			throw new CeoWebException("Can not extract SchoolVisitBean from DB.", e);
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

		return years;
	}

	public static void addSchoolVisitBean(SchoolVisitBean eBean) throws CeoWebException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.ceo_web.add_school_visit(?, ?, ?); end;");
			stat.setString(1, eBean.getImageFileName());
			stat.setString(2, eBean.getCaption());
			stat.setDate(3, new java.sql.Date(eBean.getVisitDate().getTime()));
			stat.execute();

		}
		catch (SQLException e) {
			System.err.println("SchoolVisitManager.getSchoolVisitBeans(): " + e);
			throw new CeoWebException("Can not ADD SchoolVisitBean to DB.", e);
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

	public static void updateSchoolVisitBean(SchoolVisitBean eBean) throws CeoWebException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.ceo_web.update_school_visit(?, ?, ?, ?); end;");
			stat.setInt(1, eBean.getVisitID());
			stat.setString(2, eBean.getImageFileName());
			stat.setString(3, eBean.getCaption());
			stat.setDate(4, new java.sql.Date(eBean.getVisitDate().getTime()));

			stat.execute();

		}
		catch (SQLException e) {
			System.err.println("SchoolVisitManager.getSchoolVisitBeans(): " + e);
			throw new CeoWebException("Can not UPDATE SchoolVisitBean to DB.", e);
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

	public static void deleteSchoolVisitBean(int visit_id) throws CeoWebException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.ceo_web.del_school_visit(?); end;");
			stat.setInt(1, visit_id);
			stat.execute();

		}
		catch (SQLException e) {
			System.err.println("SchoolVisitManager.deleteSchoolVisitBean(int visit_id): " + e);
			throw new CeoWebException("Can not DELETE SchoolVisitBean to DB.", e);
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

	public static void archiveSchoolVisitBean(int visit_id) throws CeoWebException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.ceo_web.archive_school_visit(?); end;");
			stat.setInt(1, visit_id);
			stat.execute();

		}
		catch (SQLException e) {
			System.err.println("SchoolVisitManager.deleteSchoolVisitBean(int visit_id): " + e);
			throw new CeoWebException("Can not DELETE SchoolVisitBean to DB.", e);
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

	public static SchoolVisitBean createSchoolVisitBean(ResultSet rs) {

		SchoolVisitBean aBean = null;
		try {
			aBean = new SchoolVisitBean();

			aBean.setVisitID(rs.getInt("VISIT_ID"));
			aBean.setImageFileName(rs.getString("IMG_FILE"));
			aBean.setCaption(rs.getString("IMG_CAPTION"));
			aBean.setVisitDate(new java.util.Date(rs.getDate("IMG_DATE").getTime()));
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}
}
