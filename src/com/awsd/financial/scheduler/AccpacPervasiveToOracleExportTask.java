package com.awsd.financial.scheduler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;
import java.util.TimerTask;

import com.awsd.servlet.ControllerServlet;
import com.esdnl.dao.DAOUtils;

public class AccpacPervasiveToOracleExportTask extends TimerTask {

	public AccpacPervasiveToOracleExportTask() {

		super();
	}

	public void run() {

		Connection pCon = null;
		Connection oCon = null;
		Statement stat = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;

		try {
			//create connection to Data warehouse
			pCon = DAOUtils.getConnection();
			oCon.setAutoCommit(false);

			//Create Statement to remove all records from financial data warehouse tables.
			if (ControllerServlet.ACCPAC_SUMMARY_DETAIL_GENERATION_ONLY.equalsIgnoreCase("OFF")) {
				System.err.println("FINANCIAL EXPORT STARTED AT " + (new Date(System.currentTimeMillis())));

				System.err.println("DELETEING PREVIOUSLY EXPORTED FINANCIAL DATA.");
				stat = oCon.createStatement();
				//***ADD ALL DELETE STATEMENTS HERE

				stat.addBatch("DELETE FROM ACCPAC_APATR");
				stat.addBatch("DELETE FROM ACCPAC_APOBL");
				stat.addBatch("DELETE FROM ACCPAC_APVEN");
				stat.addBatch("DELETE FROM ACCPAC_GLAFS");
				stat.addBatch("DELETE FROM ACCPAC_GLAMF");
				stat.addBatch("DELETE FROM ACCPAC_GLPOST");
				stat.addBatch("DELETE FROM ACCPAC_POCRNAH");
				stat.addBatch("DELETE FROM ACCPAC_POCRNH1");
				stat.addBatch("DELETE FROM ACCPAC_POCRNL");
				stat.addBatch("DELETE FROM ACCPAC_POINVAH");
				stat.addBatch("DELETE FROM ACCPAC_POINVH1");
				stat.addBatch("DELETE FROM ACCPAC_POINVL");
				stat.addBatch("DELETE FROM ACCPAC_POPORH1");
				stat.addBatch("DELETE FROM ACCPAC_POPORL");
				stat.addBatch("DELETE FROM ACCPAC_PORCPH1");
				stat.addBatch("DELETE FROM ACCPAC_PORCPL");
				stat.addBatch("DELETE FROM ACCPAC_PORETH1");
				stat.addBatch("DELETE FROM ACCPAC_PORETL");
				stat.addBatch("DELETE FROM ACCPAC_FULL_SUM");
				stat.addBatch("DELETE FROM ACCPAC_FULL_DETAIL");
				//***END OF DELETE STATEMENTS
				stat.executeBatch();
				oCon.commit();
				stat.close();

				//create connection to financial DB
				pCon = DAOUtils.getConnection();

				//Export data from ACCPAC to data warehouse.

				exportTable(oCon, pCon, "APATR", "ACCPAC_APATR", "FISCYR=?", new Object[] {
					ControllerServlet.FINANCIAL_FISCAL_YEAR
				});

				exportTable(oCon, pCon, "APOBL", "ACCPAC_APOBL", "FISCYR=?", new Object[] {
					ControllerServlet.FINANCIAL_FISCAL_YEAR
				});

				exportTable(oCon, pCon, "APVEN", "ACCPAC_APVEN", "", null);

				exportTable(oCon, pCon, "GLAFS", "ACCPAC_GLAFS", "FSCSYR=?", new Object[] {
					ControllerServlet.FINANCIAL_FISCAL_YEAR
				});

				exportTable(oCon, pCon, "GLAMF", "ACCPAC_GLAMF", "", null);

				exportTable(oCon, pCon, "GLPOST", "ACCPAC_GLPOST", "FISCALYR=?", new Object[] {
					ControllerServlet.FINANCIAL_FISCAL_YEAR
				});

				exportTable(oCon, pCon, "POCRNAH", "ACCPAC_POCRNAH", "FISCYEAR=?", new Object[] {
					ControllerServlet.FINANCIAL_FISCAL_YEAR
				});

				exportTable(oCon, pCon, "POCRNH1", "ACCPAC_POCRNH1", "FISCYEAR=?", new Object[] {
					ControllerServlet.FINANCIAL_FISCAL_YEAR
				});

				exportTable(oCon, pCon, "POCRNL", "ACCPAC_POCRNL", "", null);

				exportTable(oCon, pCon, "POINVAH", "ACCPAC_POINVAH", "FISCYEAR=?", new Object[] {
					ControllerServlet.FINANCIAL_FISCAL_YEAR
				});

				exportTable(oCon, pCon, "POINVH1", "ACCPAC_POINVH1", "FISCYEAR=?", new Object[] {
					ControllerServlet.FINANCIAL_FISCAL_YEAR
				});

				exportTable(oCon, pCon, "POINVL", "ACCPAC_POINVL", "", null);

				exportTable(oCon, pCon, "POPORH1", "ACCPAC_POPORH1", "", null);

				exportTable(oCon, pCon, "POPORL", "ACCPAC_POPORL", "", null);

				exportTable(oCon, pCon, "PORCPH1", "ACCPAC_PORCPH1", "FISCYEAR=?", new Object[] {
					ControllerServlet.FINANCIAL_FISCAL_YEAR
				});

				exportTable(oCon, pCon, "PORCPL", "ACCPAC_PORCPL", "", null);

				exportTable(oCon, pCon, "PORETH1", "ACCPAC_PORETH1", "FISCYEAR=?", new Object[] {
					ControllerServlet.FINANCIAL_FISCAL_YEAR
				});

				exportTable(oCon, pCon, "PORETL", "ACCPAC_PORETL", "", null);

				//close pervasive connection
				pCon.close();
				System.err.println("FINANCIAL EXPORT FINISHED AT " + (new Date(System.currentTimeMillis())));
			}
			else {
				System.err.println("DELETEING SUMMARY/DETAIL FINANCIAL DATA.");
				stat = oCon.createStatement();
				//***ADD ALL DELETE STATEMENTS HERE
				stat.addBatch("DELETE FROM ACCPAC_FULL_SUM");
				stat.addBatch("DELETE FROM ACCPAC_FULL_DETAIL");
				//***END OF DELETE STATEMENTS
				stat.executeBatch();
				oCon.commit();
				stat.close();
			}

			//generate sum and detail data
			System.err.println("FINANICAL SUMMARY DATA GENERATION STARTED AT " + (new Date(System.currentTimeMillis())));
			pstat = oCon.prepareStatement("INSERT INTO ACCPAC_FULL_SUM VALUES(?,?,?,?,?,?,?,?)");

			stat = oCon.createStatement();
			rs = stat.executeQuery("SELECT * FROM FULL_SUM_VIEW");
			oCon.setAutoCommit(false);
			while (rs.next()) {
				pstat.clearParameters();
				pstat.setString(1, rs.getString("ACCTDESC"));
				pstat.setString(2, rs.getString("ACCTFRMT"));
				pstat.setDouble(3, rs.getDouble("BUDGET"));
				pstat.setDouble(4, rs.getDouble("ENCUMBRANCE"));
				pstat.setDouble(5, rs.getDouble("ACTUAL"));
				pstat.setString(6, rs.getString("ACCTSEG"));
				pstat.setString(7, rs.getString("DEPT"));
				pstat.setString(8, rs.getString("PROJ"));
				pstat.executeUpdate();
			}
			oCon.commit();
			pstat.close();
			rs.close();
			stat.close();
			System.err.println("FINANICAL SUMMARY DATA GENERATION FINISHED AT " + (new Date(System.currentTimeMillis())));

			System.err.println("FINANICAL DETAIL DATA GENERATION STARTED AT " + (new Date(System.currentTimeMillis())));
			pstat = oCon.prepareStatement("INSERT INTO ACCPAC_FULL_DETAIL VALUES(?,?,?,?,?,?,?,?,?,?,?)");
			stat = oCon.createStatement();
			rs = stat.executeQuery("SELECT * FROM FULL_DETAIL_VIEW");
			oCon.setAutoCommit(false);
			while (rs.next()) {
				pstat.clearParameters();
				pstat.setString(1, rs.getString("ACCTDESC"));
				pstat.setString(2, rs.getString("ACCTSEG"));
				pstat.setInt(3, rs.getInt("DATE"));
				pstat.setString(4, rs.getString("VENDOR"));
				pstat.setString(5, rs.getString("INVNUM"));
				pstat.setString(6, rs.getString("PONUM"));
				pstat.setDouble(7, rs.getDouble("ACTUAL"));
				pstat.setDouble(8, rs.getDouble("ENCUM"));
				pstat.setString(9, rs.getString("ACCT"));
				pstat.setString(10, rs.getString("DEPT"));
				pstat.setString(11, rs.getString("PROJ"));
				pstat.executeUpdate();
			}
			oCon.commit();
			pstat.close();
			rs.close();
			stat.close();
			oCon.close();
			System.err.println("FINANICAL DETAIL DATA GENERATION FINISHED AT " + (new Date(System.currentTimeMillis())));
		}
		catch (SQLException e) {
			System.err.println(e);
			try {
				oCon.rollback();
			}
			catch (Exception ex) {}
		}
		finally {
			try {
				oCon.close();
			}
			catch (Exception e) {}
			try {
				pCon.close();
			}
			catch (Exception e) {}
		}
	}

	private void exportTable(Connection oCon, Connection pCon, String source, String destination, String condition,
														Object values[]) throws SQLException {

		PreparedStatement pstat = null;
		PreparedStatement cstat = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		StringBuffer sql = new StringBuffer("INSERT INTO " + destination + " VALUES(");

		System.err.println("EXPORTING " + source + " TO " + destination + " AT " + (new Date(System.currentTimeMillis())));

		if (condition.equals("")) {
			cstat = pCon.prepareStatement("SELECT * FROM " + source);
		}
		else {
			cstat = pCon.prepareStatement("SELECT * FROM " + source + " WHERE (" + condition + ")");

			if (condition.indexOf("?") > -1) {
				for (int i = 0; i < values.length; i++) {
					if (values[i] instanceof String)
						cstat.setString(i + 1, (String) values[i]);
					else if (values[i] instanceof Integer)
						cstat.setInt(i + 1, ((Integer) values[i]).intValue());
					else if (values[i] instanceof Double)
						pstat.setDouble(i + 1, ((Double) values[i]).doubleValue());
					else
						System.err.println("exportTable(): UNKNOWN TYPE IN CONDITION");
				}
			}
		}
		rs = cstat.executeQuery();

		rsmd = rs.getMetaData();

		//System.err.println("No. Columns: " + rsmd.getColumnCount());

		for (int i = 1; i < rsmd.getColumnCount(); i++) {
			//System.err.println(rsmd.getColumnName(i) + " : " + rsmd.getColumnTypeName(i));
			sql.append("?,");
		}
		sql.append("?)");

		pstat = oCon.prepareStatement(sql.toString());

		while (rs.next()) {
			pstat.clearParameters();

			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				switch (rsmd.getColumnType(i)) {
				case Types.CHAR:
					pstat.setString(i, rs.getString(i).trim());
					break;
				case Types.SMALLINT:
				case Types.INTEGER:
					pstat.setInt(i, rs.getInt(i));
					break;
				case Types.DECIMAL:
					if (rsmd.getScale(i) > 0)
						pstat.setDouble(i, rs.getDouble(i));
					else
						pstat.setInt(i, rs.getInt(i));
					break;
				default:
					System.err.println("******* " + rsmd.getColumnTypeName(i) + " NOT CHECKED!!!!");
				}
			}
			pstat.executeUpdate();
		}

		oCon.commit();
		rs.close();
		pstat.close();
		cstat.close();
	}
}