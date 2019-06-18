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

public class TaskGradeManager {

	private static HashMap<Integer, TaskGradeBean> grades = null;

	static {
		try {
			grades = getTaskGradeBeansMap();
		}
		catch (PPGPException e) {
			e.printStackTrace();

			grades = new HashMap<Integer, TaskGradeBean>();
		}
	}

	public static TaskGradeBean[] getTaskGradeBeans() throws PPGPException {

		Vector<TaskGradeBean> v_opps = null;
		TaskGradeBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<TaskGradeBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.pgp_sys.get_task_grds; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createTaskGradeBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("TaskGradeManager.TaskGradeBean[] getTaskGradeBeans(): " + e);
			throw new PPGPException("Can not extract TaskGradeBean from DB.", e);
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

		return (TaskGradeBean[]) v_opps.toArray(new TaskGradeBean[0]);
	}

	public static HashMap<Integer, TaskGradeBean> getTaskGradeBeansMap() throws PPGPException {

		HashMap<Integer, TaskGradeBean> v_opps = null;
		TaskGradeBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new HashMap<Integer, TaskGradeBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.pgp_sys.get_task_grds; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createTaskGradeBean(rs);

				v_opps.put(new Integer(eBean.getGradeID()), eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("TaskGradeManager.TaskGradeBean[] getTaskGradeBeans(): " + e);
			throw new PPGPException("Can not extract TaskGradeBean from DB.", e);
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

	public static TaskGradeBean getTaskGradeBean(int grade_id) {

		return (TaskGradeBean) grades.get(new Integer(grade_id));
	}

	public static TaskGradeBean createTaskGradeBean(ResultSet rs) {

		TaskGradeBean aBean = null;
		try {
			aBean = new TaskGradeBean();

			aBean.setGradeID(rs.getInt("GRADE_ID"));
			aBean.setGradeTitle(rs.getString("GRADE_TITLE"));
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}
}