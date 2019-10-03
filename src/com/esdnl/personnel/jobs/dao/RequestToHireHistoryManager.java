package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.RequestToHireHistoryBean;
import com.esdnl.personnel.jobs.constants.RequestToHireStatus;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class RequestToHireHistoryManager {
	public static RequestToHireHistoryBean addRequestToHireHistoryBean(RequestToHireHistoryBean abean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.add_rth_history(?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, abean.getRequestToHireId());
			stat.setInt(3, abean.getStatusId().getValue());
			stat.setString(4, abean.getNotes());
			stat.execute();
			abean.setId(((OracleCallableStatement) stat).getInt(1));
			stat.close();
			con.commit();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("RequestToHireHistoryBean addRequestToHireHistoryBean(RequestToHireHistoryBean abean): " + e);
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

		return abean;
	}
	public static TreeMap<Integer,RequestToHireHistoryBean> getRequestsToHireHistory(int rthid)  {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<Integer,RequestToHireHistoryBean> beans = new TreeMap<Integer,RequestToHireHistoryBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_rth_history(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, rthid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				RequestToHireHistoryBean rhis = new RequestToHireHistoryBean();
				rhis = createRequestToHireHistoryBean(rs);
				beans.put(rhis.getId(),rhis);
			}

		}
		catch (SQLException e) {
			System.err.println("RequestToHireBean[] getRequestsToHireByStatus(int status): " + e);
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

		return beans;
	}	
	public static RequestToHireHistoryBean createRequestToHireHistoryBean(ResultSet rs) {

		RequestToHireHistoryBean abean = null;
		try {
			abean = new RequestToHireHistoryBean();
			abean.setId(rs.getInt("ID"));
			abean.setRequestToHireId(rs.getInt("REQUEST_TO_HIRE_ID"));
			abean.setStatusId(RequestToHireStatus.get(rs.getInt("STATUS_ID")));
			if (rs.getDate("HISTORY_DATE") != null)
				abean.setHistoryDate(new java.util.Date(rs.getDate("HISTORY_DATE").getTime()));
			
			abean.setNotes(rs.getString("NOTES"));
			abean.setHistoryDateTime(rs.getString("HISDT"));
		}
		catch (Exception e) {
			abean = null;
		}

		return abean;
	}	
}
