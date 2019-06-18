package com.awsd.security;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelException;
import com.esdnl.dao.DAOUtils;

public class RoleDB {

	public static boolean addRole(Role r) throws RoleException {

		Connection con = null;
		Statement stat = null;
		String sql;
		int check = 0;

		try {
			sql = "INSERT INTO ROLE VALUES('" + r.getRoleUID().toUpperCase() + "', '" + r.getRoleDescription() + "')";

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.createStatement();
			check = stat.executeUpdate(sql);

		}
		catch (SQLException e) {
			System.err.println("RoleDB.addRole(): " + e);
			throw new RoleException(r, "Could not added Role to DB: " + e);
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

	public static boolean updateRole(String ouid, Role r) throws RoleException {

		Connection con = null;
		Statement stat = null;
		String sql;
		int check = 0;

		try {
			sql = "UPDATE ROLE SET ROLE_ID='" + r.getRoleUID() + "', ROLE_DESC='" + r.getRoleDescription()
					+ "' WHERE ROLE_ID='" + ouid + "'";

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.createStatement();
			check = stat.executeUpdate(sql);
		}
		catch (SQLException e) {
			System.err.println("RoleDB.addRole(): " + e);
			throw new RoleException(r, "Could not added Role to DB: " + e);
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

	public static boolean deleteRole(String uid) throws RoleException {

		Connection con = null;
		Statement stat = null;
		String sql;
		int check = 0;

		try {
			sql = "DELETE FROM ROLE WHERE ROLE_ID='" + uid + "'";

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.createStatement();
			check = stat.executeUpdate(sql);
		}
		catch (SQLException e) {
			System.err.println("RoleDB.deleteEvent(): " + e);
			throw new RoleException("Could not remove Role from DB.");
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

	public static Vector<Role> getRoles() throws RoleException, PermissionException, PersonnelException {

		Vector<Role> roles = null;
		Role r = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			roles = new Vector<Role>(10);

			sql = "SELECT * FROM ROLE ORDER BY ROLE_ID";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				r = new Role(rs.getString("ROLE_ID"), rs.getString("ROLE_DESC"));

				roles.add(r);
			}
		}
		catch (SQLException e) {
			System.err.println("RoleDB.getRoles(): " + e);
			throw new RoleException("Can not extract Roles from DB: " + e);
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
		return roles;
	}

	public static Role getRole(String uid) throws RoleException, PermissionException, PersonnelException {

		Role r = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT * FROM ROLE WHERE ROLE_ID='" + uid + "'";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			if (rs.next()) {
				r = new Role(rs.getString("ROLE_ID"), rs.getString("ROLE_DESC"));
			}
		}
		catch (SQLException e) {
			System.err.println("RoleDB.getRole(): " + e);
			throw new RoleException("Can not extract Role from DB: " + e);
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
		return r;
	}

	public static boolean addRoleMembership(Role r, Personnel p) throws RoleException {

		Connection con = null;
		Statement stat = null;
		String sql;
		int check = 0;

		try {
			sql = "INSERT INTO ROLEPERSONNEL VALUES('" + r.getRoleUID() + "', " + p.getPersonnelID() + ")";

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.createStatement();
			check = stat.executeUpdate(sql);
		}
		catch (SQLException e) {
			System.err.println("RoleDB.addRoleMembership(): " + e);
			throw new RoleException(r, "Could not added RoleMembership to DB: " + e);
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

	public static boolean deleteRoleMembership(Role r, Personnel p) throws RoleException {

		Connection con = null;
		Statement stat = null;
		String sql;
		int check = 0;

		try {
			sql = "DELETE FROM ROLEPERSONNEL WHERE ROLE_ID='" + r.getRoleUID() + "' AND PERSONNEL_ID=" + p.getPersonnelID();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.createStatement();
			check = stat.executeUpdate(sql);

		}
		catch (SQLException e) {
			System.err.println("RoleDB.deleteRoleMembership(): " + e);
			throw new RoleException(r, "Could not delete RoleMembership from DB: " + e);
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

	public static boolean addRolePermission(Role r, Permission p) throws RoleException {

		Connection con = null;
		Statement stat = null;
		String sql;
		int check = 0;

		try {
			sql = "INSERT INTO ROLEPERMISSION VALUES('" + r.getRoleUID() + "', '" + p.getPermissionUID() + "')";

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.createStatement();
			check = stat.executeUpdate(sql);

		}
		catch (SQLException e) {
			System.err.println("RoleDB.addRolePermission(): " + e);
			throw new RoleException(r, "Could not added RolePermission to DB: " + e);
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

	public static boolean deleteRolePermission(Role r, Permission p) throws RoleException {

		Connection con = null;
		Statement stat = null;
		String sql;
		int check = 0;

		try {
			sql = "DELETE FROM ROLEPERMISSION WHERE ROLE_ID='" + r.getRoleUID() + "' AND PERMISSION_ID='"
					+ p.getPermissionUID() + "'";

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.createStatement();
			check = stat.executeUpdate(sql);

		}
		catch (SQLException e) {
			System.err.println("RoleDB.deleteRolePermission(): " + e);
			throw new RoleException(r, "Could not delete RolePermission from DB: " + e);
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

	public static Map<String, Role> getRoles(User usr) throws RoleException, PersonnelException, PermissionException {

		Map<String, Role> roles = null;
		Role r = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			roles = new HashMap<String, Role>(10);

			sql = "SELECT ROLE.ROLE_ID, ROLE.ROLE_DESC " + "FROM ROLE, PERSONNEL, ROLEPERSONNEL "
					+ "WHERE ROLE.ROLE_ID=ROLEPERSONNEL.ROLE_ID " + "AND PERSONNEL.PERSONNEL_ID=ROLEPERSONNEL.PERSONNEL_ID "
					+ "AND PERSONNEL.PERSONNEL_ID=" + usr.getPersonnel().getPersonnelID();

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				r = new Role(rs.getString("ROLE_ID"), rs.getString("ROLE_DESC"));

				roles.put(rs.getString("ROLE_ID"), r);
			}
		}
		catch (SQLException e) {
			System.err.println("RoleDB.getRoles(User): " + e);
			throw new RoleException("Can not extract roles from DB: " + e);
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
		return roles;
	}

	public static Map<String, Role> getRoles(Personnel p) throws RoleException, PersonnelException, PermissionException {

		Map<String, Role> roles = null;
		Role r = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			roles = new HashMap<String, Role>(10);

			sql = "SELECT ROLE.ROLE_ID, ROLE.ROLE_DESC " + "FROM ROLE, PERSONNEL, ROLEPERSONNEL "
					+ "WHERE ROLE.ROLE_ID=ROLEPERSONNEL.ROLE_ID " + "AND PERSONNEL.PERSONNEL_ID=ROLEPERSONNEL.PERSONNEL_ID "
					+ "AND PERSONNEL.PERSONNEL_ID=" + p.getPersonnelID();

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				r = new Role(rs.getString("ROLE_ID"), rs.getString("ROLE_DESC"));

				roles.put(rs.getString("ROLE_ID"), r);
			}
		}
		catch (SQLException e) {
			System.err.println("RoleDB.getRoles(User): " + e);
			throw new RoleException("Can not extract roles from DB: " + e);
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
		return roles;
	}
}