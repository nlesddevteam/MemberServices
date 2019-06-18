package com.esdnl.sds;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.school.School;
import com.esdnl.dao.DAOUtils;

public class SDSSchoolInfoDB {

	public static SDSSchoolInfo getSDSSchoolInfo(School who) throws SDSException {

		SDSSchoolInfo info = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.sds_sys.get_school_sds_info(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, who.getSchoolID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				info = new SDSSchoolInfo(rs.getInt("SCHOOL_ID"), rs.getString("PD_ACCT"));
			else
				info = null;
		}
		catch (SQLException e) {
			System.err.println("SDSSchoolInfoDB.getSDSSchoolInfo(School): " + e);
			throw new SDSException("Can not extract sds info from DB: " + e);
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
		return info;
	}
}