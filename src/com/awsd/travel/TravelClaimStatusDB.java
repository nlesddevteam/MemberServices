package com.awsd.travel;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;

public class TravelClaimStatusDB {

	public static HashMap getAllStatuses() throws TravelClaimException {

		HashMap statuses = null;
		TravelClaimStatus s = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			statuses = new HashMap(7);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_all_statuses; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				s = new TravelClaimStatus(rs.getInt("CODE_ID"), rs.getString("CODE_DESC"));

				statuses.put(new Integer(s.getID()), s);
			}
		}
		catch (SQLException e) {
			System.err.println("TravelClaimStatusDB.getAllStatues(): " + e);
			throw new TravelClaimException("Can not extract travel claim status codes from DB: " + e);
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
		return statuses;
	}
}