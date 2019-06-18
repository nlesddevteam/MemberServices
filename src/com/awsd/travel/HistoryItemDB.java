package com.awsd.travel;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;

public class HistoryItemDB {

	public static Vector getClaimHistoryItems(TravelClaim claim) throws TravelClaimException {

		Vector items = null;
		HistoryItem item = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			items = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claim_history_items(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, claim.getClaimID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				item = new HistoryItem(rs.getInt("history_id"), rs.getInt("personnel_id"), new Date(rs.getDate("history_date").getTime()), rs.getString("action"));

				items.add(item);
			}
		}
		catch (SQLException e) {
			System.err.println("HistoryItemDB.getClaimHistoryItems(): " + e);
			throw new TravelClaimException("Can not extract history items from DB: " + e);
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
		return items;
	}
}