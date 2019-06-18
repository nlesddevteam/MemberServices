package com.esdnl.nicep.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.esdnl.dao.DAOUtils;
import com.esdnl.nicep.beans.AgencyStudentAssocBean;
import com.esdnl.nicep.beans.NICEPException;

public class AgencyStudentAssocManager {

	public static void addAgencyStudentAssocBean(AgencyStudentAssocBean abean) throws NICEPException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.nicep.add_agency_student(?,?); end;");

			stat.setInt(1, abean.getAgency().getAgencyId());
			stat.setInt(2, abean.getStudent().getStudentId());

			stat.execute();

			con.commit();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addAgencyDemographicsBean(AgencyDemographicsBean abean): " + e);
			throw new NICEPException("Can not add AgencyDemographicsBean to DB.", e);
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