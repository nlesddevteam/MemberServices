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

public class TaskSubjectManager {

	private static HashMap<Integer, TaskSubjectBean> subs = null;

	static {
		try {
			subs = getTaskSubjectBeansMap();
		}
		catch (PPGPException e) {
			e.printStackTrace();

			subs = new HashMap<Integer, TaskSubjectBean>();
		}
	}

	public static TaskSubjectBean getTaskSubjectBean(int subject_id) {

		return (TaskSubjectBean) subs.get(new Integer(subject_id));
	}

	public static TaskSubjectBean[] getTaskSubjectBeans() throws PPGPException {

		Vector<TaskSubjectBean> v_opps = null;
		TaskSubjectBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<TaskSubjectBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.pgp_sys.get_task_subs; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createTaskSubjectBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("TaskSubjectManager.TaskSubjectBean[] getTaskSubjectBeans(): " + e);
			throw new PPGPException("Can not extract TaskSubjectBean from DB.", e);
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

		return (TaskSubjectBean[]) v_opps.toArray(new TaskSubjectBean[0]);
	}

	public static HashMap<Integer, TaskSubjectBean> getTaskSubjectBeansMap() throws PPGPException {

		HashMap<Integer, TaskSubjectBean> v_opps = null;
		TaskSubjectBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new HashMap<Integer, TaskSubjectBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.pgp_sys.get_task_subs; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createTaskSubjectBean(rs);

				v_opps.put(new Integer(eBean.getSubjectID()), eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("TaskSubjectManager.TaskSubjectBean[] getTaskSubjectBeans(): " + e);
			throw new PPGPException("Can not extract TaskSubjectBean from DB.", e);
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

	public static TaskSubjectBean[] getTaskSubjectBeans(int grade_id) throws PPGPException {

		Vector<TaskSubjectBean> v_opps = null;
		TaskSubjectBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<TaskSubjectBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.pgp_sys.get_task_grds_subs(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, grade_id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createTaskSubjectBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("TaskSubjectManager.TaskSubjectBean[] getTaskSubjectBeans(): " + e);
			throw new PPGPException("Can not extract TaskSubjectBean from DB.", e);
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

		return (TaskSubjectBean[]) v_opps.toArray(new TaskSubjectBean[0]);
	}

	public static TaskSubjectBean createTaskSubjectBean(ResultSet rs) {

		TaskSubjectBean aBean = null;
		try {
			aBean = new TaskSubjectBean();

			aBean.setSubjectID(rs.getInt("SUBJECT_ID"));
			aBean.setSubjectTitle(rs.getString("SUBJECT_TITLE"));
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}
}