package com.awsd.ppgp;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;

public class TaskTopicAreaManager {

	private static HashMap<Integer, TaskTopicAreaBean> topics = null;

	static {
		try {
			topics = getTaskTopicAreaBeansMap();
		}
		catch (PPGPException e) {
			e.printStackTrace();

			topics = new HashMap<Integer, TaskTopicAreaBean>();
		}
	}

	public static TaskTopicAreaBean getTaskTopicAreaBean(int topic_id) {

		return (TaskTopicAreaBean) topics.get(new Integer(topic_id));
	}

	public static TaskTopicAreaBean[] getTaskTopicAreaBeans(int cat_id) throws PPGPException {

		Vector<TaskTopicAreaBean> v_opps = null;
		TaskTopicAreaBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<TaskTopicAreaBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.pgp_sys.get_task_topics(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cat_id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createTaskTopicAreaBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("TaskTopicAreaManager.TaskTopicAreaBean[] getTaskTopicAreaBeans(int cat_id): " + e);
			throw new PPGPException("Can not extract TaskTopicAreaBean from DB.", e);
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

		return (TaskTopicAreaBean[]) v_opps.toArray(new TaskTopicAreaBean[0]);
	}

	public static TaskTopicAreaBean[] getTaskTopicAreaBeans() throws PPGPException {

		Vector<TaskTopicAreaBean> v_opps = null;
		TaskTopicAreaBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<TaskTopicAreaBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.pgp_sys.get_task_topics; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createTaskTopicAreaBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("TaskTopicAreaManager.TaskTopicAreaBean[] getTaskTopicAreaBeans(int cat_id): " + e);
			throw new PPGPException("Can not extract TaskTopicAreaBean from DB.", e);
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

		return (TaskTopicAreaBean[]) v_opps.toArray(new TaskTopicAreaBean[0]);
	}

	public static HashMap<Integer, TaskTopicAreaBean> getTaskTopicAreaBeansMap() throws PPGPException {

		HashMap<Integer, TaskTopicAreaBean> v_opps = null;
		TaskTopicAreaBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new HashMap<Integer, TaskTopicAreaBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.pgp_sys.get_task_topics; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createTaskTopicAreaBean(rs);

				v_opps.put(new Integer(eBean.getTopicID()), eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("TaskTopicAreaManager.TaskTopicAreaBean[] getTaskTopicAreaBeans(int cat_id): " + e);
			throw new PPGPException("Can not extract TaskTopicAreaBean from DB.", e);
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

		return v_opps;
	}

	public static TaskTopicAreaBean[] getTaskTopicAreaBeans(int cat_id, int grade_id, int subject_id)
			throws PPGPException {

		Vector<TaskTopicAreaBean> v_opps = null;
		TaskTopicAreaBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<TaskTopicAreaBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.pgp_sys.get_task_topics(?, ?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cat_id);
			stat.setInt(3, grade_id);
			stat.setInt(4, subject_id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createTaskTopicAreaBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("TaskTopicAreaManager.TaskTopicAreaBean[] getTaskTopicAreaBeans(int cat_id, int grade_id, int subject_id): "
					+ e);
			throw new PPGPException("Can not extract TaskTopicAreaBean from DB.", e);
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

		return (TaskTopicAreaBean[]) v_opps.toArray(new TaskTopicAreaBean[0]);
	}

	public static TaskTopicAreaBean createTaskTopicAreaBean(ResultSet rs) {

		TaskTopicAreaBean aBean = null;
		try {
			aBean = new TaskTopicAreaBean();

			aBean.setTopicID(rs.getInt("TOPIC_ID"));
			aBean.setTopicTitle(rs.getString("TOPIC_TITLE"));
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}
}