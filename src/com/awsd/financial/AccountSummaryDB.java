package com.awsd.financial;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.esdnl.dao.DAOUtils;

public class AccountSummaryDB {

	public static AccountSummary getAccountSummary(String acctfrmt) throws FinancialException {

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		AccountSummary summary = null;
		String sql;

		try {
			sql = "SELECT ACCTDESC, ACCTFRMT, nvl(BUDGET, 0) BUDGET, nvl(ENCUMBRANCE, 0) ENCUMBRANCE, nvl(ACTUAL, 0) ACTUAL, ACCTSEG,"
					+ "nvl(DEPT, 'UNKNOWN') DEPT, "
					+ "nvl(PROJ, 'UNKNOWN') PROJ "
					+ "FROM ACCPAC_FULL_SUM WHERE ACCTFRMT='"
					+ acctfrmt + "'";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			if (rs.next()) {
				summary = new AccountSummary(rs.getString("ACCTDESC"), rs.getString("ACCTFRMT"), rs.getDouble("BUDGET"), rs.getDouble("ENCUMBRANCE"), rs.getDouble("ACTUAL"), rs.getString("ACCTSEG"), rs.getString("DEPT"), rs.getString("PROJ"));

				rs.close();
				stat.close();
				con.close();
			}
			else {
				//throw new FinancialException("Account Summary for " + acctfrmt + " does not exist.");
				System.err.println("Account Summary for " + acctfrmt + " does not exist.");
				summary = null;
			}
		}
		catch (SQLException e) {
			System.err.println("AccountSummaryDB.getAccountSummary(String): " + e);
			throw new FinancialException("Could not retrieve Account Summary from DB");
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
		return summary;
	}
}