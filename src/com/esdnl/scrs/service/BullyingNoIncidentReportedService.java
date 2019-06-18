package com.esdnl.scrs.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.esdnl.dao.DAOUtils;
import com.esdnl.scrs.domain.BullyingException;
import com.esdnl.scrs.domain.BullyingNoIncidentReportedBean;

public class BullyingNoIncidentReportedService {

	public static void addBullyingNoIncidentReportedBean(BullyingNoIncidentReportedBean abean) throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.bullying_pkg.add_no_incident_reported(?); end;");
			stat.setInt(1, abean.getSchool().getSchoolID());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addBullyingNoIncidentReportedBean(BullyingNoIncidentReportedBean abean): " + e);
			throw new BullyingException("Can not add 'BullyingNoIncidentReportedBean' to DB.", e);
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
	}

}
