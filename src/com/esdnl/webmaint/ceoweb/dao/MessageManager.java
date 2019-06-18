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
import com.esdnl.webmaint.ceoweb.bean.MessageBean;

public class MessageManager {

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
				InputStream istr = MessageManager.class.getResourceAsStream("connection.properties");
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

	public static MessageBean[] getCurrentMessageBeans(int msg_type) throws CeoWebException {

		Vector v_opps = null;
		MessageBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.ceo_web.get_cur_msgs(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, msg_type);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createMessageBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("MessageBeanManager.getMessageBeans(): " + e);
			throw new CeoWebException("Can not extract MessageBean from DB.", e);
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

		return (MessageBean[]) v_opps.toArray(new MessageBean[0]);
	}

	public static MessageBean[] getMessageBeans(int msg_type, boolean archived) throws CeoWebException {

		Vector v_opps = null;
		MessageBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector(5);

			con = DAOUtils.getConnection();
			if (archived)
				stat = con.prepareCall("begin ? := awsd_user.ceo_web.get_archived_msgs(?); end;");
			else
				stat = con.prepareCall("begin ? := awsd_user.ceo_web.get_msgs(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, msg_type);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createMessageBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("MessageBeanManager.getMessageBeans(): " + e);
			throw new CeoWebException("Can not extract MessageBean from DB.", e);
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

		return (MessageBean[]) v_opps.toArray(new MessageBean[0]);
	}

	public static MessageBean getMessageBean(int msg_id) throws CeoWebException {

		MessageBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.ceo_web.get_msg(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, msg_id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createMessageBean(rs);

		}
		catch (SQLException e) {
			System.err.println("MessageBeanManager.getMessageBeans(): " + e);
			throw new CeoWebException("Can not extract MessageBean from DB.", e);
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

	public static TreeMap getMessageBeansMap(int msg_type, boolean archived) throws CeoWebException {

		TreeMap years = null;
		Vector v_opps = null;
		MessageBean eBean = null;
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
				stat = con.prepareCall("begin ? := awsd_user.ceo_web.get_archived_msgs(?); end;");
			else
				stat = con.prepareCall("begin ? := awsd_user.ceo_web.get_msgs(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, msg_type);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createMessageBean(rs);

				cal.setTime(eBean.getMessageDate());

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
			System.err.println("MessageBeanManager.getMessageBeans(): " + e);
			throw new CeoWebException("Can not extract MessageBean from DB.", e);
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

	public static void addMessageBean(MessageBean eBean) throws CeoWebException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.ceo_web.add_msg(?, ?, ?, ?, ?); end;");
			stat.setDate(1, new java.sql.Date(eBean.getMessageDate().getTime()));
			stat.setString(2, eBean.getMessageTitle());
			stat.setString(3, eBean.getMessage());
			stat.setInt(4, eBean.getMessageType().getTypeID());
			stat.setString(5, eBean.getMessageImage());

			stat.execute();

		}
		catch (SQLException e) {
			System.err.println("MessageBeanManager.getMessageBeans(): " + e);
			throw new CeoWebException("Can not ADD MessageBean to DB.", e);
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

	public static void updateMessageBean(MessageBean eBean) throws CeoWebException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.ceo_web.update_msg(?, ?, ?, ?, ?, ?); end;");
			stat.setInt(1, eBean.getMessageID());
			stat.setDate(2, new java.sql.Date(eBean.getMessageDate().getTime()));
			stat.setString(3, eBean.getMessageTitle());
			stat.setString(4, eBean.getMessage());
			stat.setInt(5, eBean.getMessageType().getTypeID());
			stat.setString(6, eBean.getMessageImage());

			stat.execute();

		}
		catch (SQLException e) {
			System.err.println("MessageBeanManager.getMessageBeans(): " + e);
			throw new CeoWebException("Can not UPDATE MessageBean to DB.", e);
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

	public static void deleteMessageBean(int msg_id) throws CeoWebException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.ceo_web.del_msg(?); end;");
			stat.setInt(1, msg_id);
			stat.execute();

		}
		catch (SQLException e) {
			System.err.println("MessageBeanManager.deleteMessageBean(int visit_id): " + e);
			throw new CeoWebException("Can not DELETE MessageBean to DB.", e);
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

	public static void archiveMessageBean(int msg_id) throws CeoWebException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.ceo_web.archive_msg(?); end;");
			stat.setInt(1, msg_id);
			stat.execute();

		}
		catch (SQLException e) {
			System.err.println("MessageBeanManager.deleteMessageBean(int visit_id): " + e);
			throw new CeoWebException("Can not DELETE MessageBean to DB.", e);
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

	public static MessageBean createMessageBean(ResultSet rs) {

		MessageBean aBean = null;
		try {
			aBean = new MessageBean();

			aBean.setMessageID(rs.getInt("MSG_ID"));
			aBean.setMessageImage(rs.getString("MSG_IMG"));
			aBean.setMessage(rs.getString("MSG_TEXT"));
			aBean.setMessageDate(new java.util.Date(rs.getDate("MSG_DATE").getTime()));
			aBean.setMessageType(rs.getInt("MSG_TYPE"));
			aBean.setMessageTitle(rs.getString("MSG_TITLE"));
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}
}
