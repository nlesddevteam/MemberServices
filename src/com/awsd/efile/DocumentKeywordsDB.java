package com.awsd.efile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.esdnl.dao.DAOUtils;

public class DocumentKeywordsDB {

	public static boolean addDocumentKeywords(Document doc) throws DocumentKeywordException {

		Connection con = null;
		PreparedStatement stat = null;
		Statement stat2 = null;
		ResultSet rs = null;
		String sql, keywords[];
		int check = 0, id = -1;

		try {
			keywords = doc.getKeywords();

			sql = "INSERT INTO DOCKEYWORDS VALUES(?, ?)";

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareStatement(sql);
			stat.clearParameters();
			stat.setInt(1, doc.getDocumentID());
			for (int i = 0; i < keywords.length; i++) {
				if ((keywords[i] != null) && !keywords[i].equals("")) {
					stat.setString(2, keywords[i].toUpperCase());
					check = stat.executeUpdate();
				}
			}
			stat.close();
			con.close();
		}
		catch (EFileException e) {
			System.err.println("DocumentDB.addDocumentKeywords(): " + e);
			throw new DocumentKeywordException("Could not add document keyword to DB: " + e);
		}
		catch (SQLException e) {
			System.err.println("DocumentDB.addDocumentKeywords(): " + e);
			throw new DocumentKeywordException("Could not add document keyword to DB: " + e);
		}
		finally {
			try {
				stat.close();
				con.close();
			}
			catch (Exception e) {}
		}

		return (check == 1);
	}

	public static String[] getDocumentKeywords(Document doc) throws DocumentKeywordException {

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sqlcnt, sql, keywords[] = null;
		int check = 0, id = -1, cnt;

		try {
			sqlcnt = "SELECT count(*) cnt FROM DOCKEYWORDS WHERE DOC_ID=" + doc.getDocumentID();
			sql = "SELECT * FROM DOCKEYWORDS WHERE DOC_ID=" + doc.getDocumentID();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.createStatement();
			rs = stat.executeQuery(sqlcnt);
			if (rs.next()) {
				cnt = rs.getInt("cnt");
				keywords = new String[cnt];
				try {
					rs.close();
				}
				catch (Exception e) {
					System.err.println(e);
				}
			}
			else {
				throw new DocumentKeywordException("No Keywords Found.");
			}

			rs = stat.executeQuery(sql);
			for (int i = 0; rs.next(); i++) {
				keywords[i] = rs.getString("dockeyword_word");
			}

			rs.close();
			stat.close();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("DocumentDB.getDocumentKeywords(): " + e);
			throw new DocumentKeywordException("Could not extract document keyword to DB: " + e);
		}
		finally {
			try {
				rs.close();
				stat.close();
				con.close();
			}
			catch (Exception e) {}
		}

		return keywords;
	}

	public static boolean deleteDocumentKeywords(Document doc) throws DocumentKeywordException {

		Connection con = null;
		PreparedStatement stat = null;
		String sql;
		int check = 0;

		try {
			sql = "DELETE FROM DOCKEYWORDS WHERE DOC_ID=?";

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareStatement(sql);
			stat.clearParameters();
			stat.setInt(1, doc.getDocumentID());
		}
		catch (SQLException e) {
			System.err.println("DocumentDB.addDocumentKeywords(): " + e);
			throw new DocumentKeywordException("Could not add document keyword to DB: " + e);
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
