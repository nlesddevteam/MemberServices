package com.awsd.financial;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.esdnl.dao.DAOUtils;

public class AccountDB {

	public static Account getAccount(String id) throws FinancialException {

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		Account acc = null;
		String sql = null;

		try {
			sql = new String("SELECT * FROM ACCPAC_GLAMF WHERE ACCTFMTTD LIKE '" + id.trim() + "'");

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			if (rs.next()) {
				acc = new Account(rs.getString("ACCTFMTTD"), rs.getString("ACCTDESC"));

				rs.close();
				stat.close();
				con.close();
			}
			else {
				throw new FinancialException("AccountDB.getAccount(String): account with id=" + id + " not found");
			}

		}
		catch (SQLException e) {
			System.err.println("AccountDB.getAccount(String): " + e);
			throw new FinancialException("Could not retrieve Account from DB");
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

		return acc;
	}

	public static Vector getAccounts() throws FinancialException {

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		Account acc = null;
		String sql = null;
		Vector accounts = null;

		try {
			accounts = new Vector(10);

			sql = new String("SELECT * FROM ACCPAC_GLAMF ORDER BY ACCTFMTTD");

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				acc = new Account(rs.getString("ACCTFMTTD"), rs.getString("ACCTDESC"));

				accounts.add(acc);
			}
			rs.close();
			stat.close();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("AccountDB.getAccounts(): " + e);
			throw new FinancialException("Could not retrieve Account from DB");
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

		return accounts;
	}

	public static Vector getAccountsByAcctSeq(String ACCTSEG) throws FinancialException {

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		Account acc = null;
		String sql = null;
		Vector accounts = null;

		try {
			accounts = new Vector(10);

			sql = new String("SELECT * FROM ACCPAC_GLAMF WHERE ACSEGVAL01 LIKE'" + ACCTSEG + "' ORDER BY ACCTFMTTD");

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				acc = new Account(rs.getString("ACCTFMTTD"), rs.getString("ACCTDESC"));

				accounts.add(acc);
			}
			rs.close();
			stat.close();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("AccountDB.getAccounts(): " + e);
			throw new FinancialException("Could not retrieve Account from DB");
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

		return accounts;
	}

	public static Vector getAccountsByAcctSeqRange(String stACCTSEG, String edACCTSEG) throws FinancialException {

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		Account acc = null;
		String sql = null;
		Vector accounts = null;

		try {
			//System.out.println(stACCTSEG + " - " + edACCTSEG);
			accounts = new Vector(10);

			sql = new String("SELECT * FROM ACCPAC_GLAMF WHERE ACSEGVAL01 >='" + stACCTSEG + "' and  ACSEGVAL01 <='"
					+ edACCTSEG + "' ORDER BY ACCTFMTTD");

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				acc = new Account(rs.getString("ACCTFMTTD"), rs.getString("ACCTDESC"));

				//System.out.println(acc);
				accounts.add(acc);
			}
			rs.close();
			stat.close();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("AccountDB.getAccounts(): " + e);
			throw new FinancialException("Could not retrieve Account from DB");
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

		return accounts;
	}

	public static Account[] getAccounts(String id[]) throws FinancialException {

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		StringBuffer sql = null;
		Account accounts[] = null;

		try {
			accounts = new Account[id.length];

			sql = new StringBuffer("SELECT * FROM ACCPAC_GLAMF WHERE ");

			for (int i = 0; i < id.length - 1; i++) {
				sql.append("ACCTFMTTD LIKE '" + id[i] + "' OR ");
			}
			sql.append("ACCTFMTTD LIKE '" + id[id.length - 1] + "'");

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql.toString());

			for (int i = 0; (rs.next() && (i < accounts.length)); i++) {
				accounts[i] = new Account(rs.getString("ACCTFMTTD"), rs.getString("ACCTDESC"));
				//System.err.println(accounts[i].getFormatedAccountCode());
			}
			rs.close();
			stat.close();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("AccountDB.getAccounts(String[]): " + e);
			throw new FinancialException("Could not retrieve Account from DB");
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

		return accounts;
	}
}