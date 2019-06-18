package com.awsd.financial;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelException;
import com.esdnl.dao.DAOUtils;

public class ReportDB {

	public static int addReport(String name) throws FinancialException {

		Connection con = null;
		PreparedStatement stat = null;
		Statement stat2 = null;
		ResultSet rs = null;
		String sql;
		int check = 0, id = -1;

		try {
			sql = "INSERT INTO FINANCIAL_REPORT(REPORT_ID, REPORT_NAME) VALUES(FINANCIAL_REPORT_SEQ.nextval, ?)";

			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareStatement(sql);
			stat.clearParameters();
			stat.setString(1, name.toUpperCase());

			check = stat.executeUpdate();
			stat.close();

			if (check == 1) {
				try {
					stat2 = con.createStatement();
					rs = stat2.executeQuery("SELECT FINANCIAL_REPORT_SEQ.CURRVAL pid FROM DUAL");
					if (rs.next()) {
						id = rs.getInt("pid");
					}
					else {
						id = -1;
					}
					rs.close();
					stat2.close();
				}
				catch (SQLException e) {
					try {
						con.rollback();
					}
					catch (Exception ex) {}
					System.err.println("ReportDB.addReport(): " + e);
					throw new FinancialException("Could not retrieve report id for new report added from DB: " + e);
				}
				finally {
					try {
						rs.close();
					}
					catch (Exception e) {}
					try {
						stat2.close();
					}
					catch (Exception e) {}
				}
			}
			con.commit();
			con.close();
		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ReportDB.addReport(): " + e);
			throw new FinancialException("Could not add report to DB: " + e);
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
		return (id);
	}

	public static boolean deleteReport(Report rpt) throws FinancialException {

		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql;
		int check;

		try {
			sql = "DELETE FROM FINANCIAL_REPORT WHERE REPORT_ID=?";

			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareStatement(sql);
			stat.clearParameters();
			stat.setInt(1, rpt.getReportID());

			check = stat.executeUpdate();
			stat.close();

			con.commit();
			con.close();
		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ReportDB.deleteReport(): " + e);
			throw new FinancialException("Could not delete report from DB: " + e);
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
		return (check == 1);
	}

	public static Report getReport(int report_id) throws FinancialException {

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		Report rpt = null;
		String sql;

		try {
			sql = "SELECT report_id, report_name FROM FINANCIAL_REPORT WHERE report_id = " + report_id;

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			if (rs.next()) {
				rpt = new Report(rs.getInt("REPORT_ID"), rs.getString("REPORT_NAME"));

				rs.close();
				stat.close();
				con.close();
			}
			else {
				throw new FinancialException("Report does not exist.");
			}
		}
		catch (SQLException e) {
			System.err.println("ReportDB.getReport(int): " + e);
			throw new FinancialException("Could not retrieve Report from DB");
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
		return rpt;
	}

	public static Vector getReports() throws FinancialException {

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		Report rpt = null;
		Vector reports = null;
		String sql;

		try {
			reports = new Vector(10);

			sql = "SELECT report_id, report_name FROM FINANCIAL_REPORT ORDER BY report_name";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				rpt = new Report(rs.getInt("REPORT_ID"), rs.getString("REPORT_NAME"));

				reports.add(rpt);
			}
			rs.close();
			stat.close();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("ReportDB.getReports(): " + e);
			throw new FinancialException("Could not retrieve Reports from DB");
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
		return reports;
	}

	public static Vector getPersonnelReports(Personnel p) throws FinancialException {

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		Report rpt = null;
		StringBuffer sql = null;
		Vector reports = null;

		try {
			reports = new Vector(10);

			sql = new StringBuffer("SELECT * FROM FINANCIAL_REPORT, FINANCIAL_REPORTPERSONNEL, PERSONNEL WHERE ");
			sql.append("FINANCIAL_REPORT.REPORT_ID=FINANCIAL_REPORTPERSONNEL.REPORT_ID ");
			sql.append("AND FINANCIAL_REPORTPERSONNEL.PERSONNEL_ID=PERSONNEL.PERSONNEL_ID ");
			sql.append("AND PERSONNEL.PERSONNEL_ID=" + p.getPersonnelID());
			sql.append(" ORDER BY REPORT_NAME");

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql.toString());

			while (rs.next()) {
				rpt = new Report(rs.getInt("REPORT_ID"), rs.getString("REPORT_NAME"));

				reports.add(rpt);
			}
			rs.close();
			stat.close();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("ReportDB.getPersonnelReports(Personnel): " + e);
			throw new FinancialException("Could not retrieve Reports from DB");
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
		return reports;
	}

	public static Map getPersonnelReportsMap(Personnel p) throws FinancialException {

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		Report rpt = null;
		StringBuffer sql = null;
		Map reports = null;

		try {
			reports = new HashMap(10);

			sql = new StringBuffer("SELECT * FROM FINANCIAL_REPORT, FINANCIAL_REPORTPERSONNEL, PERSONNEL WHERE ");
			sql.append("FINANCIAL_REPORT.REPORT_ID=FINANCIAL_REPORTPERSONNEL.REPORT_ID ");
			sql.append("AND FINANCIAL_REPORTPERSONNEL.PERSONNEL_ID=PERSONNEL.PERSONNEL_ID ");
			sql.append("AND PERSONNEL.PERSONNEL_ID=" + p.getPersonnelID());

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql.toString());

			while (rs.next()) {
				rpt = new Report(rs.getInt("REPORT_ID"), rs.getString("REPORT_NAME"));

				reports.put(new Integer(rpt.getReportID()), rpt);
			}
			rs.close();
			stat.close();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("ReportDB.getPersonnelReports(Personnel): " + e);
			throw new FinancialException("Could not retrieve Reports from DB");
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
		return reports;
	}

	public static Vector getReportAccounts(Report rpt) throws FinancialException {

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		Account acc = null;
		StringBuffer sql = null;
		Vector accounts = null;

		try {
			accounts = new Vector(5);

			sql = new StringBuffer("SELECT * FROM FINANCIAL_REPORT, FINANCIAL_REPORTACCOUNTS, ACCPAC_GLAMF WHERE ");
			sql.append("FINANCIAL_REPORT.REPORT_ID=FINANCIAL_REPORTACCOUNTS.REPORT_ID ");
			sql.append("AND FINANCIAL_REPORTACCOUNTS.ACCOUNT_ID LIKE ACCPAC_GLAMF.ACCTFMTTD ");
			sql.append("AND FINANCIAL_REPORT.REPORT_ID=" + rpt.getReportID());

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql.toString());

			while (rs.next()) {
				acc = new Account(rs.getString("ACCTFMTTD"), rs.getString("ACCTDESC"));
				accounts.add(acc);
			}
			rs.close();
			stat.close();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("ReportDB.getReportAccounts(int): " + e);
			throw new FinancialException("Could not retrieve ReportAccounts from DB");
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

	public static boolean addReportAccount(Report r, Account a) throws FinancialException {

		Connection con = null;
		Statement stat = null;
		String sql;
		int check = 0;

		try {
			sql = "INSERT INTO FINANCIAL_REPORTACCOUNTS VALUES(" + r.getReportID() + ", '" + a.getFormatedAccountCode()
					+ "')";

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.createStatement();
			check = stat.executeUpdate(sql);
			stat.close();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("ReportDB.addReportAccount(): " + e);
			throw new FinancialException("Could not added account[" + a.getFormatedAccountCode() + "]  to report in DB: " + e);
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
		return (check == 1);
	}

	public static boolean addReportAccount(Report r, Account a[]) throws FinancialException {

		Connection con = null;
		Statement stat = null;
		String sql;
		int check = 0;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);
			stat = con.createStatement();

			for (int i = 0; i < a.length; i++) {

				sql = "INSERT INTO FINANCIAL_REPORTACCOUNTS VALUES(" + r.getReportID() + ", '" + a[i].getFormatedAccountCode()
						+ "')";

				check = stat.executeUpdate(sql);

				if (!(check == 1)) {
					con.rollback();
					throw new FinancialException("ReportDB.addReportAccount(Report, Account[]): one or more accounts could not be added.");
				}
			}
			stat.close();
			con.commit();
			con.close();
		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ReportDB.addReportAccount(): " + e);
			throw new FinancialException("Could not added one or more accounts to report in DB: " + e);
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
		return (check == 1);
	}

	public static boolean deleteReportAccount(Report r, Account a) throws FinancialException {

		Connection con = null;
		Statement stat = null;
		String sql;
		int check = 0;

		try {
			sql = "DELETE FROM FINANCIAL_REPORTACCOUNTS WHERE REPORT_ID=" + r.getReportID() + " AND ACCOUNT_ID LIKE '"
					+ a.getFormatedAccountCode() + "'";

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.createStatement();
			check = stat.executeUpdate(sql);
			stat.close();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("ReportDB.deleteReportAccount(Report, Account): " + e);
			throw new FinancialException("Could not remove account[" + a.getFormatedAccountCode() + "] from report in DB.");
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
		return (check == 1);
	}

	public static boolean deleteReportAccount(Report r, Account a[]) throws FinancialException {

		Connection con = null;
		Statement stat = null;
		String sql;
		int check = 0;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);
			stat = con.createStatement();

			for (int i = 0; i < a.length; i++) {
				sql = "DELETE FROM FINANCIAL_REPORTACCOUNTS WHERE REPORT_ID=" + r.getReportID() + " AND ACCOUNT_ID LIKE '"
						+ a[i].getFormatedAccountCode() + "'";

				check = stat.executeUpdate(sql);

				if (!(check == 1)) {
					con.rollback();
					throw new FinancialException("ReportDB.deleteReportAccount(Report, Account[]): one or more accounts could not be deleted.");
				}
			}
			stat.close();
			con.commit();
			con.close();
		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ReportDB.deleteReportAccount(Report, Account[]): " + e);
			throw new FinancialException("Could not remove one or more from report in DB.");
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

		return (check == 1);
	}

	public static Map getReportAccountsMap(Report rpt) throws FinancialException {

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		Account acc = null;
		StringBuffer sql = null;
		Map accounts = null;

		try {
			accounts = new TreeMap();

			sql = new StringBuffer("SELECT * FROM FINANCIAL_REPORT, FINANCIAL_REPORTACCOUNTS, ACCPAC_GLAMF WHERE ");
			sql.append("FINANCIAL_REPORT.REPORT_ID=FINANCIAL_REPORTACCOUNTS.REPORT_ID ");
			sql.append("AND FINANCIAL_REPORTACCOUNTS.ACCOUNT_ID LIKE ACCPAC_GLAMF.ACCTFMTTD ");
			sql.append("AND FINANCIAL_REPORT.REPORT_ID=" + rpt.getReportID());

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql.toString());

			while (rs.next()) {
				acc = new Account(rs.getString("ACCTFMTTD"), rs.getString("ACCTDESC"));
				accounts.put(rs.getString("ACCTFMTTD"), acc);
			}
			rs.close();
			stat.close();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("ReportDB.getReportAccountsMap(int): " + e);
			throw new FinancialException("Could not retrieve ReportAccounts from DB");
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

	public static Vector getReportPersonnel(Report rpt) throws FinancialException, PersonnelException {

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		Personnel p = null;
		StringBuffer sql = null;
		Vector users = null;

		try {
			users = new Vector(5);

			sql = new StringBuffer("SELECT * FROM FINANCIAL_REPORT, FINANCIAL_REPORTPERSONNEL, PERSONNEL WHERE ");
			sql.append("FINANCIAL_REPORT.REPORT_ID=FINANCIAL_REPORTPERSONNEL.REPORT_ID ");
			sql.append("AND FINANCIAL_REPORTPERSONNEL.PERSONNEL_ID=PERSONNEL.PERSONNEL_ID ");
			sql.append("AND FINANCIAL_REPORT.REPORT_ID=" + rpt.getReportID());

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql.toString());

			while (rs.next()) {
				p = new Personnel(rs.getInt("PERSONNEL_ID"), rs.getString("PERSONNEL_USERNAME"), rs.getString("PERSONNEL_PASSWORD"), rs.getString("PERSONNEL_FIRSTNAME"), rs.getString("PERSONNEL_LASTNAME"), rs.getString("PERSONNEL_EMAIL"), rs.getInt("PERSONNEL_CATEGORYID"), rs.getInt("PERSONNEL_SUPERVISOR_ID"), rs.getInt("SCHOOL_ID"));
				users.add(p);
			}
			rs.close();
			stat.close();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("ReportDB.getReportPersonnel(int): " + e);
			throw new FinancialException("Could not retrieve ReportPersonnel from DB");
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
		return users;
	}

	public static Map getReportPersonnelMap(Report rpt) throws FinancialException, PersonnelException {

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		Personnel p = null;
		StringBuffer sql = null;
		HashMap users = null;

		try {
			users = new HashMap(10);

			sql = new StringBuffer("SELECT * FROM FINANCIAL_REPORT, FINANCIAL_REPORTPERSONNEL, PERSONNEL WHERE ");
			sql.append("FINANCIAL_REPORT.REPORT_ID=FINANCIAL_REPORTPERSONNEL.REPORT_ID ");
			sql.append("AND FINANCIAL_REPORTPERSONNEL.PERSONNEL_ID=PERSONNEL.PERSONNEL_ID ");
			sql.append("AND FINANCIAL_REPORT.REPORT_ID=" + rpt.getReportID());

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql.toString());

			while (rs.next()) {
				p = new Personnel(rs.getInt("PERSONNEL_ID"), rs.getString("PERSONNEL_USERNAME"), rs.getString("PERSONNEL_PASSWORD"), rs.getString("PERSONNEL_FIRSTNAME"), rs.getString("PERSONNEL_LASTNAME"), rs.getString("PERSONNEL_EMAIL"), rs.getInt("PERSONNEL_CATEGORYID"), rs.getInt("PERSONNEL_SUPERVISOR_ID"), rs.getInt("SCHOOL_ID"));
				users.put(new Integer(p.getPersonnelID()), p);
			}
			rs.close();
			stat.close();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("ReportDB.getReportPersonnel(int): " + e);
			throw new FinancialException("Could not retrieve ReportPersonnel from DB");
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
		return users;
	}

	public static boolean addReportPersonnel(Report r, Personnel p) throws FinancialException, PersonnelException {

		Connection con = null;
		Statement stat = null;
		String sql;
		int check = 0;

		try {
			sql = "INSERT INTO FINANCIAL_REPORTPERSONNEL VALUES(" + r.getReportID() + ", " + p.getPersonnelID() + ")";

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.createStatement();
			check = stat.executeUpdate(sql);
			stat.close();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("ReportDB.addReportPersonnel(): " + e);
			throw new FinancialException("Could not add Personnel[" + p.getFullNameReverse() + "]  to report in DB: " + e);
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
		return (check == 1);
	}

	public static boolean addReportPersonnel(Report r, Personnel a[]) throws FinancialException, PersonnelException {

		Connection con = null;
		Statement stat = null;
		String sql;
		int check = 0;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);
			stat = con.createStatement();

			for (int i = 0; i < a.length; i++) {

				sql = "INSERT INTO FINANCIAL_REPORTPERSONNEL VALUES(" + r.getReportID() + ", " + a[i].getPersonnelID() + ")";

				check = stat.executeUpdate(sql);

				if (!(check == 1)) {
					con.rollback();
					throw new FinancialException("ReportDB.addReportPersonnel(Report, Personnel[]): one or more Users could not be added.");
				}
			}
			stat.close();
			con.commit();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("ReportDB.addReportPersonnel(): " + e);
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			throw new FinancialException("Could not added one or more Users to report in DB: " + e);
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
		return (check == 1);
	}

	public static boolean deleteReportPersonnel(Report r, Personnel a) throws FinancialException, PersonnelException {

		Connection con = null;
		Statement stat = null;
		String sql;
		int check = 0;

		try {
			sql = "DELETE FROM FINANCIAL_REPORTPERSONNEL WHERE REPORT_ID=" + r.getReportID() + " AND PERSONNEL_ID="
					+ a.getPersonnelID();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.createStatement();
			check = stat.executeUpdate(sql);
			stat.close();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("ReportDB.deleteReportPersonnel(Report, Personnel): " + e);
			throw new FinancialException("Could not remove User[" + a.getFullNameReverse() + "] from report in DB.");
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
		return (check == 1);
	}

	public static boolean deleteReportPersonnel(Report r, Personnel a[]) throws FinancialException, PersonnelException {

		Connection con = null;
		Statement stat = null;
		String sql;
		int check = 0;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);
			stat = con.createStatement();

			for (int i = 0; i < a.length; i++) {
				sql = "DELETE FROM FINANCIAL_REPORTPERSONNEL WHERE REPORT_ID=" + r.getReportID() + " AND PERSONNEL_ID="
						+ a[i].getPersonnelID();

				check = stat.executeUpdate(sql);

				if (!(check == 1)) {
					con.rollback();
					throw new FinancialException("ReportDB.deleteReportPersonnel(Report, Personnel[]): one or more users could not be deleted.");
				}
			}
			stat.close();
			con.commit();
			con.close();
		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ReportDB.deleteReportPersonnel(Report, Personnel[]): " + e);
			throw new FinancialException("Could not remove one or more users from report in DB.");
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

		return (check == 1);
	}
}