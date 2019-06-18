package com.awsd.financial;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.esdnl.dao.DAOUtils;

public class AccountTransactionDB {

	public static Vector getAccountTransactions(Account acc) throws FinancialException {

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		AccountTransaction tran = null;
		Vector trans = null;
		StringBuffer sql = null;

		try {
			trans = new Vector(100);
			sql = new StringBuffer("SELECT * FROM ACCPAC_FULL_DETAIL WHERE ACCT LIKE '" + acc.getAccountSegment() + "'");

			if ((acc.getDepartment() != null) && !acc.getDepartment().equals("")) {
				sql.append(" AND DEPT LIKE '" + acc.getDepartment() + "'");
			}
			else {
				sql.append(" AND DEPT IS NULL");
			}

			if ((acc.getProject() != null) && !acc.getProject().equals("")) {
				sql.append(" AND PROJ LIKE '" + acc.getProject() + "'");
			}
			else {
				sql.append(" AND PROJ IS NULL");
			}

			sql.append(" ORDER BY 3 DESC");

			System.err.println(sql.toString());

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql.toString());

			while (rs.next()) {
				tran = new AccountTransaction(rs.getString("ACCTDESC"), rs.getString("ACCTSEG"), rs.getInt("DATE"), rs.getString("VENDOR"), rs.getString("INVNUM"), rs.getString("PONUM"), rs.getDouble("ACTUAL"), rs.getDouble("ENCUM"), rs.getString("ACCT"), rs.getString("DEPT"), rs.getString("PROJ"));

				trans.add(tran);
			}
			rs.close();
			stat.close();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("AccountTransactionDB.getAccountTransactions(String): " + e);
			throw new FinancialException("Could not retrieve Account Transactions from DB");
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
		return trans;
	}
}