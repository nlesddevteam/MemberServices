package com.esdnl.personnel.v2.database.recognition;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.v2.database.sds.EmployeeManager;
import com.esdnl.personnel.v2.model.recognition.bean.RecognitionRequestBean;
import com.esdnl.personnel.v2.model.recognition.constant.RequestType;

public class EntityManager {

	public static Vector getEntityBean(RecognitionRequestBean req) throws Exception {

		Vector beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_recognition.get_request_entities(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, req.getId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if (req.getType().equals(RequestType.EMPLOYEE) || req.getType().equals(RequestType.EMPLOYEE_GROUP))
					beans.add(EmployeeManager.createEmployeeBean(rs));
			}

		}
		catch (SQLException e) {
			System.err.println("RecognitionRequestBean[] getRecognitionRequestBeans(String sin): " + e);
			throw new Exception("Can not extract RecognitionRequestBean from DB.", e);
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
}