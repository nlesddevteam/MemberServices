package com.awsd.ppgp;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
public class TaskDomainManager {
	public static TaskDomainBean[] getTaskDomainBeans() throws PPGPException {

		Vector<TaskDomainBean> v_opps = null;
		TaskDomainBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<TaskDomainBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.pgp_sys.get_task_domains; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createTaskDomainBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("static TaskDomainBean[] getTaskDomainBeans(): " + e);
			throw new PPGPException("Can not extract TaskDomainBean from DB.", e);
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

		return (TaskDomainBean[]) v_opps.toArray(new TaskDomainBean[0]);
	}
	public static TaskDomainBean getTaskDomainBeanById(Integer id) throws PPGPException {

		TaskDomainBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.pgp_sys.get_domain_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createTaskDomainBean(rs);

			
			}
		}
		catch (SQLException e) {
			System.err.println("TaskDomainBean getTaskDomainBeanById(Integer id) " + e);
			throw new PPGPException("Can not extract TaskDomainBean from DB.", e);
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


	public static TaskDomainBean createTaskDomainBean(ResultSet rs) {

		TaskDomainBean aBean = null;
		try {
			aBean = new TaskDomainBean();

			aBean.setDomainID(rs.getInt("ID"));
			aBean.setDomainTitle(rs.getString("DOMAINTITLE"));
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}
}
