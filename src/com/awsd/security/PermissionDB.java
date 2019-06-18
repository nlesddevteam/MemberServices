package com.awsd.security;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.esdnl.dao.DAOUtils;

public class PermissionDB {

	public static boolean addPermission(Permission p) throws PermissionException {

		Connection con = null;
		Statement stat = null;
		String sql;
		int check = 0;

		try {
			sql = "INSERT INTO PERMISSION VALUES('" + p.getPermissionUID().toUpperCase() + "', '"
					+ p.getPermissionDescription() + "')";

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.createStatement();
			check = stat.executeUpdate(sql);
		}
		catch (SQLException e) {
			System.err.println("PermissionDB.addPermission(): " + e);
			throw new PermissionException(p, "Could not added permission to DB: " + e);
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

	public static boolean updatePermission(String ouid, Permission p) throws PermissionException {

		Connection con = null;
		Statement stat = null;
		String sql;
		int check = 0;

		try {
			sql = "UPDATE PERMISSION SET PERMISSION_ID='" + p.getPermissionUID() + "', PERMISSION_DESC='"
					+ p.getPermissionDescription() + "' WHERE PERMISSION_ID='" + ouid + "'";

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.createStatement();
			check = stat.executeUpdate(sql);
		}
		catch (SQLException e) {
			System.err.println("PermissionDB.addPermission(): " + e);
			throw new PermissionException(p, "Could not added permission to DB: " + e);
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

	public static boolean deletePermission(String uid) throws PermissionException {

		Connection con = null;
		Statement stat = null;
		String sql;
		int check = 0;

		try {
			sql = "DELETE FROM PERMISSION WHERE PERMISSION_ID='" + uid + "'";

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.createStatement();
			check = stat.executeUpdate(sql);
		}
		catch (SQLException e) {
			System.err.println("PermissionDB.deleteEvent(): " + e);
			throw new PermissionException("Could not remove permission from DB.");
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

	public static Vector<Permission> getPermissions() throws PermissionException {

		Vector<Permission> permissions = null;
		Permission p = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			permissions = new Vector<Permission>(10);

			sql = "SELECT * FROM PERMISSION ORDER BY PERMISSION_ID";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				p = new Permission(rs.getString("PERMISSION_ID"), rs.getString("PERMISSION_DESC"));

				permissions.add(p);
			}
		}
		catch (SQLException e) {
			System.err.println("PermissionDB.getPermissions(): " + e);
			throw new PermissionException("Can not extract permissions from DB: " + e);
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
		return permissions;
	}

	public static Permission getPermission(String uid) throws PermissionException {

		Permission p = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT * FROM PERMISSION WHERE PERMISSION_ID='" + uid + "'";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			if (rs.next()) {
				p = new Permission(rs.getString("PERMISSION_ID"), rs.getString("PERMISSION_DESC"));
			}
		}
		catch (SQLException e) {
			System.err.println("PermissionDB.getPermission(): " + e);
			throw new PermissionException("Can not extract permission from DB: " + e);
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

	public static Map<String, Permission> getPermissions(Role r) throws PermissionException {

		Map<String, Permission> permissions = null;
		Permission p = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			permissions = new HashMap<String, Permission>(10);

			sql = "SELECT PERMISSION.PERMISSION_ID, PERMISSION.PERMISSION_DESC " + "FROM PERMISSION, ROLE, ROLEPERMISSION "
					+ "WHERE PERMISSION.PERMISSION_ID=ROLEPERMISSION.PERMISSION_ID " + "AND ROLE.ROLE_ID=ROLEPERMISSION.ROLE_ID "
					+ "AND ROLE.ROLE_ID='" + r.getRoleUID() + "' " + "ORDER BY PERMISSION.PERMISSION_ID";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				p = new Permission(rs.getString("PERMISSION_ID"), rs.getString("PERMISSION_DESC"));

				permissions.put(rs.getString("PERMISSION_ID"), p);
			}
		}
		catch (SQLException e) {
			System.err.println("PermissionDB.getPermissions(Role): " + e);
			throw new PermissionException("Can not extract permissions from DB: " + e);
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
		return permissions;
	}

	public static Map<String, Permission> getPermissions(User usr) throws PermissionException {

		Map<String, Permission> permissions = null;
		Permission p = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			permissions = new HashMap<String, Permission>(10);

			sql = "SELECT PERMISSION.PERMISSION_ID, PERMISSION.PERMISSION_DESC "
					+ "FROM PERMISSION, ROLE, ROLEPERMISSION, PERSONNEL, ROLEPERSONNEL "
					+ "WHERE PERMISSION.PERMISSION_ID=ROLEPERMISSION.PERMISSION_ID " + "AND ROLE.ROLE_ID=ROLEPERMISSION.ROLE_ID "
					+ "AND ROLE.ROLE_ID=ROLEPERSONNEL.ROLE_ID " + "AND PERSONNEL.PERSONNEL_ID=ROLEPERSONNEL.PERSONNEL_ID "
					+ "AND PERSONNEL.PERSONNEL_ID=" + usr.getPersonnel().getPersonnelID();

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				p = new Permission(rs.getString("PERMISSION_ID"), rs.getString("PERMISSION_DESC"));

				permissions.put(rs.getString("PERMISSION_ID"), p);
			}
		}
		catch (SQLException e) {
			System.err.println("PermissionDB.getPermissions(User): " + e);
			throw new PermissionException("Can not extract permissions from DB: " + e);
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
		return permissions;
	}
}