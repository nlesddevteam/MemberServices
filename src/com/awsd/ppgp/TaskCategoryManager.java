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

public class TaskCategoryManager {

	private static HashMap<Integer, TaskCategoryBean> categories = null;

	static {
		try {
			categories = getTaskCategoryBeansMap();
		}
		catch (PPGPException e) {
			e.printStackTrace();

			categories = new HashMap<Integer, TaskCategoryBean>();
		}
	}

	public static TaskCategoryBean[] getTaskCategoryBeans() throws PPGPException {

		Vector<TaskCategoryBean> v_opps = null;
		TaskCategoryBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<TaskCategoryBean>(5);

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.pgp_sys.get_task_cats; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createTaskCategoryBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("TaskCategoryManager.TaskCategoryBean[] getTaskCategoryBeans(): " + e);
			throw new PPGPException("Can not extract TaskCategoryBean from DB.", e);
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

		return (TaskCategoryBean[]) v_opps.toArray(new TaskCategoryBean[0]);
	}

	public static HashMap<Integer, TaskCategoryBean> getTaskCategoryBeansMap() throws PPGPException {

		HashMap<Integer, TaskCategoryBean> v_opps = null;
		TaskCategoryBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new HashMap<Integer, TaskCategoryBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.pgp_sys.get_task_cats; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createTaskCategoryBean(rs);

				v_opps.put(new Integer(eBean.getCategoryID()), eBean);
				
			}
		}
		catch (SQLException e) {
			System.err.println("TaskCategoryManager.TaskCategoryBean[] getTaskCategoryBeans(): " + e);
			throw new PPGPException("Can not extract TaskCategoryBean from DB.", e);
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

	public static TaskCategoryBean getTaskCategoryBean(int category_id) throws PPGPException {

		return (TaskCategoryBean) categories.get(new Integer(category_id));
	}

	public static TaskCategoryBean createTaskCategoryBean(ResultSet rs) {

		TaskCategoryBean aBean = null;
		try {
			aBean = new TaskCategoryBean();

			aBean.setCategoryID(rs.getInt("CAT_ID"));
			aBean.setCategoryTitle(rs.getString("CAT_TITLE"));
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}
}