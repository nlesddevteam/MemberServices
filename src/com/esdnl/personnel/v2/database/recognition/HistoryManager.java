package com.esdnl.personnel.v2.database.recognition;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.TreeMap;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.v2.model.bean.PersonnelException;
import com.esdnl.personnel.v2.model.recognition.bean.HistoryBean;
import com.esdnl.personnel.v2.model.recognition.bean.RecognitionRequestBean;
import com.esdnl.personnel.v2.model.recognition.constant.RequestStatus;

public class HistoryManager {

	public static TreeMap getRecognitionRequestHistory(RecognitionRequestBean req) throws PersonnelException {

		TreeMap map = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			map = new TreeMap(new Comparator() {

				public int compare(Object o1, Object o2) {

					RequestStatus r1 = (RequestStatus) o1;
					RequestStatus r2 = (RequestStatus) o2;

					if (r1.getId() < r2.getId())
						return -1;
					else if (r1.getId() == r2.getId())
						return 0;
					else
						return 1;
				}
			});

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_recognition.get_request_history(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, req.getId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				map.put(RequestStatus.get(rs.getInt("STATUS_ID")), createHistoryBean(rs));

		}
		catch (SQLException e) {
			System.err.println("RecognitionRequestBean[] getRecognitionRequestBeans(String sin): " + e);
			throw new PersonnelException("Can not extract RecognitionRequestBean from DB.", e);
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

		return map;
	}

	public static HistoryBean createHistoryBean(ResultSet rs) {

		HistoryBean abean = null;

		try {
			abean = new HistoryBean();

			abean.setActionDate(new java.util.Date(rs.getDate("HISTORY_DATE").getTime()));
			abean.setComments(rs.getString("COMMENT"));
			abean.setId(rs.getInt("HISTORY_ID"));
			abean.setRequestId(rs.getInt("REQUEST_ID"));
			abean.setStatus(RequestStatus.get(rs.getInt("STATUS_ID")));
			abean.setActionedById(rs.getInt("PERSONNEL_ID"));
		}
		catch (SQLException e) {
			e.printStackTrace();
			abean = null;
		}

		return abean;
	}
}