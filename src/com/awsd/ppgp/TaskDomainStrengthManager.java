package com.awsd.ppgp;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
public class TaskDomainStrengthManager {
	
	public static TaskDomainStrengthBean[] getTaskDomainStrengthBeansById(Integer did) throws PPGPException {

	Vector<TaskDomainStrengthBean> v_opps = null;
	TaskDomainStrengthBean eBean = null;
	Connection con = null;
	CallableStatement stat = null;
	ResultSet rs = null;

	try {
		v_opps = new Vector<TaskDomainStrengthBean>(5);

		con = DAOUtils.getConnection();
		stat = con.prepareCall("begin ? := awsd_user.pgp_sys.get_task_domains_str(?); end;");
		stat.registerOutParameter(1, OracleTypes.CURSOR);
		stat.setInt(2, did);
		stat.execute();
		rs = ((OracleCallableStatement) stat).getCursor(1);

		while (rs.next()) {
			eBean = createTaskDomainStrengthBean(rs);

			v_opps.add(eBean);
		}
	}
	catch (SQLException e) {
		System.err.println("TaskDomainStrengthBean[] getTaskDomainStrengthBeansById(Integer did): " + e);
		throw new PPGPException("Can not extract TaskDomainStrengthBean from DB.", e);
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

	return (TaskDomainStrengthBean[]) v_opps.toArray(new TaskDomainStrengthBean[0]);
}
public static TaskDomainStrengthBean getTaskDomainStrenthBeanById(Integer id) throws PPGPException {

	TaskDomainStrengthBean eBean = null;
	Connection con = null;
	CallableStatement stat = null;
	ResultSet rs = null;

	try {
		con = DAOUtils.getConnection();
		stat = con.prepareCall("begin ? := awsd_user.pgp_sys.get_strength_by_id(?); end;");
		stat.registerOutParameter(1, OracleTypes.CURSOR);
		stat.setInt(2, id);
		stat.execute();
		rs = ((OracleCallableStatement) stat).getCursor(1);

		while (rs.next()) {
			eBean = createTaskDomainStrengthBean(rs);

		
		}
	}
	catch (SQLException e) {
		System.err.println("static TaskDomainStrengthBean getTaskDomainStrenthBeanById(Integer id): " + e);
		throw new PPGPException("Can not extract TaskDomainStrengthBean from DB.", e);
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


public static TaskDomainStrengthBean createTaskDomainStrengthBean(ResultSet rs) {

	TaskDomainStrengthBean aBean = null;
	try {
		aBean = new TaskDomainStrengthBean();

		aBean.setDomainID(rs.getInt("DOMAINID"));
		aBean.setStrengthTitle(rs.getString("STRENGTHTITLE"));
		aBean.setStrengthID(rs.getInt("ID"));
	}
	catch (SQLException e) {
		aBean = null;
	}
	return aBean;
}
}