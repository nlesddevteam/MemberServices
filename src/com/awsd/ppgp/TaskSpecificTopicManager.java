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

public class TaskSpecificTopicManager {

	private static HashMap<Integer, TaskSpecificTopicBean> stopics = null;

	static {
		try {
			stopics = getTaskSpecificTopicBeansMap();
		}
		catch (PPGPException e) {
			e.printStackTrace();
			stopics = new HashMap<Integer, TaskSpecificTopicBean>();
		}
	}

	public static TaskSpecificTopicBean getTaskSpecificTopicBean(int stopic_id) {

		return (TaskSpecificTopicBean) stopics.get(new Integer(stopic_id));
	}

	public static TaskSpecificTopicBean[] getTaskSpecificTopicBeans() throws PPGPException {

		Vector<TaskSpecificTopicBean> v_opps = null;
		TaskSpecificTopicBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<TaskSpecificTopicBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.pgp_sys.get_task_stopics; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createTaskSpecificTopicBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("TaskTopicAreaManager.TaskSpecificTopicBean[] getTaskSpecificTopicBeans(int cat_id, int topic_id): "
					+ e);
			throw new PPGPException("Can not extract TaskSpecificTopicBean from DB.", e);
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

		return (TaskSpecificTopicBean[]) v_opps.toArray(new TaskSpecificTopicBean[0]);
	}

	public static HashMap<Integer, TaskSpecificTopicBean> getTaskSpecificTopicBeansMap() throws PPGPException {

		HashMap<Integer, TaskSpecificTopicBean> v_opps = null;
		TaskSpecificTopicBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new HashMap<Integer, TaskSpecificTopicBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.pgp_sys.get_task_stopics; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createTaskSpecificTopicBean(rs);

				v_opps.put(new Integer(eBean.getSpecificTopicID()), eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("TaskTopicAreaManager.TaskSpecificTopicBean[] getTaskSpecificTopicBeans(int cat_id, int topic_id): "
					+ e);
			throw new PPGPException("Can not extract TaskSpecificTopicBean from DB.", e);
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

	public static TaskSpecificTopicBean[] getTaskSpecificTopicBeans(int cat_id, int topic_id) throws PPGPException {

		Vector<TaskSpecificTopicBean> v_opps = null;
		TaskSpecificTopicBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<TaskSpecificTopicBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.pgp_sys.get_task_stopics(?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cat_id);
			stat.setInt(3, topic_id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createTaskSpecificTopicBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("TaskTopicAreaManager.TaskSpecificTopicBean[] getTaskSpecificTopicBeans(int cat_id, int topic_id): "
					+ e);
			throw new PPGPException("Can not extract TaskSpecificTopicBean from DB.", e);
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

		return (TaskSpecificTopicBean[]) v_opps.toArray(new TaskSpecificTopicBean[0]);
	}

	public static TaskSpecificTopicBean[] getTaskSpecificTopicBeans(int cat_id, int grd_id, int sub_id, int topic_id)
			throws PPGPException {

		Vector<TaskSpecificTopicBean> v_opps = null;
		TaskSpecificTopicBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<TaskSpecificTopicBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.pgp_sys.get_task_stopics(?, ?, ?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cat_id);
			stat.setInt(3, grd_id);
			stat.setInt(4, sub_id);
			stat.setInt(5, topic_id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createTaskSpecificTopicBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("TaskTopicAreaManager.TaskSpecificTopicBean[] getTaskSpecificTopicBeans(int cat_id, int topic_id): "
					+ e);
			throw new PPGPException("Can not extract TaskSpecificTopicBean from DB.", e);
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

		return (TaskSpecificTopicBean[]) v_opps.toArray(new TaskSpecificTopicBean[0]);
	}

	public static TaskSpecificTopicBean createTaskSpecificTopicBean(ResultSet rs) {

		TaskSpecificTopicBean aBean = null;
		try {
			aBean = new TaskSpecificTopicBean();

			aBean.setSpecificTopicID(rs.getInt("STOPIC_ID"));
			aBean.setSpecificTopicTitle(rs.getString("STOPIC_TITLE"));
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}
}