package com.awsd.efile;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.esdnl.dao.DAOUtils;

public class DocumentTypeDB {

	public static Vector getDocumentTypes() throws DocumentTypeException {

		Vector types = null;
		DocumentType dt = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			types = new Vector(10);

			sql = "SELECT DocType_ID, DocType_NAME " + "FROM DocType ORDER BY DocType_NAME";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				dt = new DocumentType(rs.getInt("DocType_ID"), rs.getString("DocType_NAME"));

				types.add(dt);
			}
			rs.close();
			stat.close();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("DocumentTypeDB.getDocumentTypes(): " + e);
			throw new DocumentTypeException("Can not extract DocumentTypes from DB: " + e);
		}
		finally {
			try {
				rs.close();
				stat.close();
				con.close();
			}
			catch (Exception e) {}
		}
		return types;
	}

	public static DocumentType getDocumentType(int id) throws DocumentTypeException {

		DocumentType dt = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT DocType_ID, DocType_NAME FROM DocType " + "WHERE DocType_ID=" + id;

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			if (rs.next()) {
				dt = new DocumentType(rs.getInt("DocType_ID"), rs.getString("DocType_NAME"));
			}
			else {
				throw new DocumentTypeException("No DocumentType matching ID: " + id);
			}
			rs.close();
			stat.close();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("DocumentTypeDB.getDocumentType(): " + e);
			throw new DocumentTypeException("Can not extract DocumentType from DB: " + e);
		}
		finally {
			try {
				rs.close();
				stat.close();
				con.close();
			}
			catch (Exception e) {}
		}
		return dt;
	}
}