package com.esdnl.complaint.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.PersonnelException;
import com.esdnl.complaint.model.bean.ComplaintBean;
import com.esdnl.complaint.model.bean.ComplaintException;
import com.esdnl.complaint.model.bean.ComplaintHistoryBean;
import com.esdnl.complaint.model.constant.ComplaintStatus;
import com.esdnl.dao.DAOUtils;

public class ComplaintHistoryManager {

	public static ComplaintHistoryBean[] getComplaintHistoryBean(ComplaintBean complaint) throws ComplaintException {

		Vector history = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			history = new Vector();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.complaint_sys.get_complaint_history(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, complaint.getId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				history.add(createComplaintHistoryBean(rs, complaint));

			System.out.println("History Tree Size: " + history.size());

		}
		catch (SQLException e) {
			System.err.println("ComplaintHistoryBean[] getComplaintHistoryBean(ComplaintBean complaint): " + e);
			throw new ComplaintException("Can not extract ComplaintHistoryBean from DB.", e);
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

		return (ComplaintHistoryBean[]) history.toArray(new ComplaintHistoryBean[0]);
	}

	public static ComplaintHistoryBean createComplaintHistoryBean(ResultSet rs, ComplaintBean complaint) {

		ComplaintHistoryBean abean = null;

		try {
			abean = new ComplaintHistoryBean();

			abean.setId(rs.getInt("HISTORY_ID"));
			abean.setComplaint(complaint);
			abean.setHistoryDate(new java.util.Date(rs.getTimestamp("HISTORY_DATE").getTime()));
			try {
				if (rs.getInt("BY_WHO") > 0)
					abean.setByWho(PersonnelDB.getPersonnel(rs.getInt("BY_WHO")));
			}
			catch (PersonnelException e) {
				e.printStackTrace();
				abean.setByWho(null);
			}

			try {
				if (rs.getInt("TO_WHO") > 0)
					abean.setToWho(PersonnelDB.getPersonnel(rs.getInt("TO_WHO")));
			}
			catch (PersonnelException e) {
				e.printStackTrace();
				abean.setToWho(null);
			}

			abean.setAction(ComplaintStatus.get(rs.getInt("STATUS")));
		}
		catch (SQLException e) {
			e.printStackTrace();
			abean = null;
		}
		catch (Exception e) {
			e.printStackTrace();
			abean = null;
		}

		if (abean == null)
			System.out.println("HISTORY BEAN IS NULL!");

		// System.out.println(abean);

		return abean;
	}
}