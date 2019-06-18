package com.awsd.personnel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Vector;

import com.esdnl.dao.DAOUtils;

public class PersonnelCategoryDB {

	private static HashMap<Integer, PersonnelCategory> intCache;
	private static TreeMap<String, PersonnelCategory> strCache;

	static {
		intCache = new HashMap<Integer, PersonnelCategory>();
		strCache = new TreeMap<String, PersonnelCategory>();

		try {
			for (PersonnelCategory cat : getPersonnelCategories()) {
				intCache.put(cat.getPersonnelCategoryID(), cat);
				strCache.put(cat.getPersonnelCategoryName(), cat);
			}
		}
		catch (PersonnelCategoryException e) {
			System.err.println("PersonnelCategoryDB: " + e.getMessage());
		}
	}

	public static Vector<PersonnelCategory> getPersonnelCategories() throws PersonnelCategoryException {

		Vector<PersonnelCategory> pers = null;
		PersonnelCategory p = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			pers = new Vector<PersonnelCategory>(10);

			if (strCache != null && strCache.size() > 0) {
				pers.addAll(strCache.values());
			}
			else {
				// NOTE TYPO IN TABLE NAME
				sql = "SELECT * FROM PERSONNELCATEGOGY ORDER BY PERSONNELCATEGORY_NAME";

				con = DAOUtils.getConnection();
				stat = con.createStatement();
				rs = stat.executeQuery(sql);

				while (rs.next()) {
					p = new PersonnelCategory(rs.getInt("PERSONNELCATEGORY_ID"), rs.getString("PERSONNELCATEGORY_NAME"), rs.getString("PERSONNELCATEGORY_DESC"));

					pers.add(p);

					//add to caches
					intCache.put(p.getPersonnelCategoryID(), p);
					strCache.put(p.getPersonnelCategoryName(), p);
				}
			}
		}
		catch (SQLException e) {
			System.err.println("PersonnelCategoryDB.getPersonnelCategories(): " + e);
			throw new PersonnelCategoryException("Can not extract personnel categories from DB: " + e);
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
		return pers;
	}

	public static PersonnelCategory getPersonnelCategory(String cat) throws PersonnelCategoryException {

		PersonnelCategory p = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {

			if (strCache.containsKey(cat)) {
				p = strCache.get(cat);
			}
			else {
				// NOTE TYPO IN TABLE NAME
				sql = "SELECT * FROM PERSONNELCATEGOGY WHERE PERSONNELCATEGORY_NAME='" + cat.toUpperCase() + "'";

				con = DAOUtils.getConnection();
				stat = con.createStatement();
				rs = stat.executeQuery(sql);

				if (rs.next()) {
					p = new PersonnelCategory(rs.getInt("PERSONNELCATEGORY_ID"), rs.getString("PERSONNELCATEGORY_NAME"), rs.getString("PERSONNELCATEGORY_DESC"));

					//add to caches
					intCache.put(p.getPersonnelCategoryID(), p);
					strCache.put(p.getPersonnelCategoryName(), p);
				}
				else {
					throw new PersonnelCategoryException("PersonnelCategory does not exist.");
				}
			}
		}
		catch (SQLException e) {
			System.err.println("PersonnelCategoryDB.getPersonnelCategory(): " + e);
			e.printStackTrace();
			throw new PersonnelCategoryException("Can not extract personnel category from DB: " + e);
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
		return p;
	}

	public static PersonnelCategory getPersonnelCategory(int id) throws PersonnelCategoryException {

		PersonnelCategory p = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {

			if (intCache.containsKey(id)) {
				p = intCache.get(id);
			}
			else {
				// NOTE TYPO IN TABLE NAME
				sql = "SELECT * FROM PERSONNELCATEGOGY WHERE PERSONNELCATEGORY_ID=" + id;

				con = DAOUtils.getConnection();
				stat = con.createStatement();
				rs = stat.executeQuery(sql);

				if (rs.next()) {
					p = new PersonnelCategory(rs.getInt("PERSONNELCATEGORY_ID"), rs.getString("PERSONNELCATEGORY_NAME"), rs.getString("PERSONNELCATEGORY_DESC"));

					//add to caches
					intCache.put(p.getPersonnelCategoryID(), p);
					strCache.put(p.getPersonnelCategoryName(), p);
				}
				else {
					throw new PersonnelCategoryException("PersonnelCategory does not exist.");
				}
			}
		}
		catch (SQLException e) {
			System.err.println("PersonnelCategoryDB.getPersonnelCategory(): " + e);
			e.printStackTrace();
			throw new PersonnelCategoryException("Can not extract personnel category from DB: " + e);
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
		return p;
	}

}