package com.awsd.personnel;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.awsd.school.School;
import com.awsd.security.Permission;
import com.awsd.security.Role;
import com.esdnl.dao.DAOUtils;
import com.esdnl.mrs.PersonnelType;
import com.esdnl.mrs.RequestAssignment;
import com.esdnl.util.StringUtils;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class PersonnelDB {

	public static boolean deletePersonnel(int id) throws PersonnelException {

		Connection con = null;
		PreparedStatement stat = null;
		String sql = null;
		boolean check = false;

		try {
			sql = "DELETE FROM AWSD_USER.PERSONNEL WHERE PERSONNEL_ID=?";

			con = DAOUtils.getConnection();

			stat = con.prepareStatement(sql);
			stat.setInt(1, id);

			check = stat.execute();
		}
		catch (SQLException e) {
			System.err.println("PersonnelDB.deletePersonnel(int): " + e);
			throw new PersonnelException("Personnel ID Could not deleted from DB");
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
		return check;
	}

	public static Personnel getPersonnel(String username) throws PersonnelException, SQLException {

		Personnel p = null;
		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT PERSONNEL_ID, PERSONNEL_USERNAME, PERSONNEL_PASSWORD, PERSONNEL_FIRSTNAME, "
					+ "PERSONNEL_LASTNAME, PERSONNEL_EMAIL, PERSONNEL_CATEGORYID, "
					+ "NVL(PERSONNEL_SUPERVISOR_ID, 0) PERSONNEL_SUPERVISOR_ID, " + "NVL(SCHOOL_ID, 0) SCHOOL_ID FROM PERSONNEL "
					+ "WHERE PERSONNEL_USERNAME=? ORDER BY PERSONNEL_ID ASC";

			con = DAOUtils.getConnection();

			stat = con.prepareStatement(sql);
			stat.setString(1, username.toLowerCase());

			rs = stat.executeQuery();

			if (rs.next()) {
				p = new Personnel(rs.getInt("PERSONNEL_ID"), rs.getString("PERSONNEL_USERNAME"), rs.getString(
						"PERSONNEL_PASSWORD"), rs.getString(
								"PERSONNEL_FIRSTNAME"), rs.getString("PERSONNEL_LASTNAME"), rs.getString("PERSONNEL_EMAIL"), rs.getInt(
										"PERSONNEL_CATEGORYID"), rs.getInt("PERSONNEL_SUPERVISOR_ID"), rs.getInt("SCHOOL_ID"));
			}
			else {
				throw new PersonnelException("No Personnel record found matching username [" + username + "].");
			}
		}
		catch (SQLException e) {
			System.err.println("Personnel getPersonnel(String username): " + e);
			throw e;
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

	public static Personnel getPersonnelByEmail(String email) throws PersonnelException, SQLException {

		Personnel p = null;
		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT PERSONNEL_ID, PERSONNEL_USERNAME, PERSONNEL_PASSWORD, PERSONNEL_FIRSTNAME, "
					+ "PERSONNEL_LASTNAME, PERSONNEL_EMAIL, PERSONNEL_CATEGORYID, "
					+ "NVL(PERSONNEL_SUPERVISOR_ID, 0) PERSONNEL_SUPERVISOR_ID, " + "NVL(SCHOOL_ID, 0) SCHOOL_ID FROM PERSONNEL "
					+ "WHERE lower(PERSONNEL_EMAIL)=? ORDER BY PERSONNEL_ID ASC";

			con = DAOUtils.getConnection();

			stat = con.prepareStatement(sql);
			stat.setString(1, email.toLowerCase());
			rs = stat.executeQuery();

			if (rs.next()) {
				p = new Personnel(rs.getInt("PERSONNEL_ID"), rs.getString("PERSONNEL_USERNAME"), rs.getString(
						"PERSONNEL_PASSWORD"), rs.getString(
								"PERSONNEL_FIRSTNAME"), rs.getString("PERSONNEL_LASTNAME"), rs.getString("PERSONNEL_EMAIL"), rs.getInt(
										"PERSONNEL_CATEGORYID"), rs.getInt("PERSONNEL_SUPERVISOR_ID"), rs.getInt("SCHOOL_ID"));
			}
			else {
				throw new PersonnelException("No Personnel record found matching email [" + email + "].");
			}
		}
		catch (SQLException e) {
			System.err.println("Personnel getPersonnelByEmail(String email): " + e);
			throw e;
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

	public static Personnel getPersonnel(int personnelID) throws PersonnelException {

		Personnel p = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT PERSONNEL_ID, PERSONNEL_USERNAME, PERSONNEL_PASSWORD, PERSONNEL_FIRSTNAME, "
					+ "PERSONNEL_LASTNAME, PERSONNEL_EMAIL, PERSONNEL_CATEGORYID, "
					+ "nvl(PERSONNEL_SUPERVISOR_ID, 0) PERSONNEL_SUPERVISOR_ID, " + "nvl(SCHOOL_ID, 0) SCHOOL_ID FROM PERSONNEL "
					+ "WHERE PERSONNEL_ID=" + personnelID;

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);
			if (rs.next()) {
				p = new Personnel(rs.getInt("PERSONNEL_ID"), rs.getString("PERSONNEL_USERNAME"), rs.getString(
						"PERSONNEL_PASSWORD"), rs.getString(
								"PERSONNEL_FIRSTNAME"), rs.getString("PERSONNEL_LASTNAME"), rs.getString("PERSONNEL_EMAIL"), rs.getInt(
										"PERSONNEL_CATEGORYID"), rs.getInt("PERSONNEL_SUPERVISOR_ID"), rs.getInt("SCHOOL_ID"));
			}
			else {
				throw new PersonnelException("No Personnel record found matching personnel id [" + personnelID + "].");
			}
		}
		catch (SQLException e) {
			System.err.println("PersonnelDB.getPersonnelID(): " + e);
			throw new PersonnelException("Personnel ID Could not retrieve event from DB");
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

	public static Personnel[] getPersonnel(String id[]) throws PersonnelException {

		Personnel p[] = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		StringBuffer sql = null;

		try {
			p = new Personnel[id.length];

			sql = new StringBuffer("SELECT PERSONNEL_ID, PERSONNEL_USERNAME, PERSONNEL_PASSWORD, PERSONNEL_FIRSTNAME, "
					+ "PERSONNEL_LASTNAME, PERSONNEL_EMAIL, PERSONNEL_CATEGORYID, "
					+ "nvl(PERSONNEL_SUPERVISOR_ID, 0) PERSONNEL_SUPERVISOR_ID, "
					+ "nvl(SCHOOL_ID, 0) SCHOOL_ID FROM PERSONNEL WHERE PERSONNEL_ID IN ( ");

			for (int i = 0; i < id.length - 1; i++) {
				if (StringUtils.isEmpty(id[i]))
					continue;

				sql.append(id[i] + ", ");
			}
			sql.append(id[id.length - 1] + ")");

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql.toString());
			for (int i = 0; (rs.next() && (i < p.length)); i++) {
				p[i] = new Personnel(rs.getInt("PERSONNEL_ID"), rs.getString("PERSONNEL_USERNAME"), rs.getString(
						"PERSONNEL_PASSWORD"), rs.getString(
								"PERSONNEL_FIRSTNAME"), rs.getString("PERSONNEL_LASTNAME"), rs.getString("PERSONNEL_EMAIL"), rs.getInt(
										"PERSONNEL_CATEGORYID"), rs.getInt("PERSONNEL_SUPERVISOR_ID"), rs.getInt("SCHOOL_ID"));
			}
		}
		catch (SQLException e) {
			System.err.println("PersonnelDB.getPersonnelID(String[]): " + e);
			e.printStackTrace(System.err);
			throw new PersonnelException("Personnel ID Could not retrieve event from DB");
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

	public static String getViewOnNextLogon(Personnel p) throws PersonnelException {

		String app;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT VIEW_ON_NEXT_LOGON FROM PERSONNEL " + "WHERE PERSONNEL_ID=" + p.getPersonnelID();

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);
			if (rs.next()) {
				app = rs.getString("VIEW_ON_NEXT_LOGON");
			}
			else {
				throw new PersonnelException("No Personnel record found matching personnel id [" + p.getPersonnelID() + "].");
			}
		}
		catch (SQLException e) {
			System.err.println("PersonnelDB.getPersonnelID(): " + e);
			throw new PersonnelException("Personnel ID Could not retrieve event from DB");
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
		return app;
	}

	public static Vector<Personnel> getDistrictPersonnel() throws PersonnelException {

		Vector<Personnel> personnels = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			personnels = new Vector<Personnel>(10);

			sql = "SELECT PERSONNEL_ID, PERSONNEL_USERNAME, PERSONNEL_PASSWORD, PERSONNEL_FIRSTNAME, "
					+ "PERSONNEL_LASTNAME, PERSONNEL_EMAIL, PERSONNEL_CATEGORYID, "
					+ "nvl(PERSONNEL_SUPERVISOR_ID, 0) PERSONNEL_SUPERVISOR_ID, " + "nvl(SCHOOL_ID, 0) SCHOOL_ID FROM PERSONNEL "
					+ "ORDER BY PERSONNEL_LASTNAME, PERSONNEL_FIRSTNAME";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next())
				personnels.add(PersonnelDB.createPersonnelBean(rs));
		}
		catch (SQLException e) {
			System.err.println("PersonnelDB.getDistrictPersonnel(): " + e);
			throw new PersonnelException("Can not extract personnel from DB: " + e);
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
		return personnels;
	}

	public static ArrayList<Vector<Personnel>> getDistrictPersonnelAlphabetized() throws PersonnelException {

		ArrayList<Vector<Personnel>> alpha = null;
		Personnel p = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			alpha = new ArrayList<Vector<Personnel>>(26);
			for (int i = 0; i < 26; i++)
				alpha.add(new Vector<Personnel>(100));

			sql = "SELECT PERSONNEL_ID, PERSONNEL_USERNAME, PERSONNEL_PASSWORD, PERSONNEL_FIRSTNAME, "
					+ "PERSONNEL_LASTNAME, PERSONNEL_EMAIL, PERSONNEL_CATEGORYID, "
					+ "nvl(PERSONNEL_SUPERVISOR_ID, 0) PERSONNEL_SUPERVISOR_ID, " + "nvl(SCHOOL_ID, 0) SCHOOL_ID FROM PERSONNEL "
					+ "ORDER BY PERSONNEL_LASTNAME, PERSONNEL_FIRSTNAME";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				p = PersonnelDB.createPersonnelBean(rs);

				((Vector<Personnel>) alpha.get(p.getLastName().trim().toUpperCase().charAt(0) - 'A')).add(p);
			}
		}
		catch (SQLException e) {
			System.err.println("PersonnelDB.getDistrictPersonnel(): " + e);

			System.err.println("ID: " + p.getPersonnelID() + " -- " + p.toString());

			throw new PersonnelException("Can not extract personnel from DB: " + e);
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
		return alpha;
	}

	public static Vector<Personnel> getSchoolPrincipalsNotSchoolSystemAdmins() throws PersonnelException {

		Vector<Personnel> personnels = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			personnels = new Vector<Personnel>(10);

			sql = "SELECT PERSONNEL_ID, PERSONNEL_USERNAME, PERSONNEL_PASSWORD, PERSONNEL_FIRSTNAME, "
					+ "PERSONNEL_LASTNAME, PERSONNEL_EMAIL, PERSONNEL_CATEGORYID, "
					+ "nvl(PERSONNEL_SUPERVISOR_ID, 0) PERSONNEL_SUPERVISOR_ID, "
					+ "nvl(SCHOOL_ID, 0) SCHOOL_ID FROM PERSONNEL P WHERE P.PERSONNEL_ID NOT IN ("
					+ "SELECT DISTINCT SS_ADMIN FROM SCHOOL_SYSTEM) AND P.PERSONNEL_ID NOT IN ("
					+ "SELECT DISTINCT SS_ADMIN_BCKUP FROM SCHOOL_SYSTEM) AND ((PERSONNEL_CATEGORYID=28) OR (PERSONNEL_CATEGORYID=29)) "
					+ "ORDER BY PERSONNEL_LASTNAME, PERSONNEL_FIRSTNAME";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next())
				personnels.add(PersonnelDB.createPersonnelBean(rs));
		}
		catch (SQLException e) {
			System.err.println("PersonnelDB.getDistrictPersonnel(): " + e);
			throw new PersonnelException("Can not extract personnel from DB: " + e);
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
		return personnels;
	}

	public static Vector<Personnel> getProgramSpecialistNotAssignedSchoolFamily() throws PersonnelException {

		Vector<Personnel> personnels = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			personnels = new Vector<Personnel>(10);

			sql = "SELECT P.PERSONNEL_ID, PERSONNEL_USERNAME, PERSONNEL_PASSWORD, PERSONNEL_FIRSTNAME, "
					+ "PERSONNEL_LASTNAME, PERSONNEL_EMAIL, PERSONNEL_CATEGORYID, "
					+ "nvl(PERSONNEL_SUPERVISOR_ID, 0) PERSONNEL_SUPERVISOR_ID, "
					+ "nvl(SCHOOL_ID, 0) SCHOOL_ID FROM PERSONNEL P WHERE NOT EXISTS ("
					+ "SELECT PS_ID FROM SCHOOL_FAMILY SF WHERE P.PERSONNEL_ID = SF.PS_ID" + ") AND EXISTS ("
					+ "SELECT PERSONNEL_ID FROM ROLEPERSONNEL RP WHERE P.PERSONNEL_ID=RP.PERSONNEL_ID "
					+ "AND ROLE_ID='PROGRAM SPECIALISTS'" + ") ORDER BY PERSONNEL_LASTNAME, PERSONNEL_FIRSTNAME";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next())
				personnels.add(PersonnelDB.createPersonnelBean(rs));
		}
		catch (SQLException e) {
			System.err.println("PersonnelDB.getDistrictPersonnel(): " + e);
			throw new PersonnelException("Can not extract personnel from DB: " + e);
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
		return personnels;
	}

	public static Map<Integer, Personnel> getPersonnel(Role r) throws PersonnelException {

		Map<Integer, Personnel> members = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			members = new HashMap<Integer, Personnel>(10);

			sql = "SELECT PERSONNEL.PERSONNEL_ID, PERSONNEL_USERNAME, PERSONNEL_PASSWORD, PERSONNEL_FIRSTNAME, "
					+ "PERSONNEL_LASTNAME, PERSONNEL_EMAIL, PERSONNEL_CATEGORYID, "
					+ "nvl(PERSONNEL_SUPERVISOR_ID, 0) PERSONNEL_SUPERVISOR_ID, " + "nvl(SCHOOL_ID, 0) SCHOOL_ID "
					+ "FROM PERSONNEL, ROLE, ROLEPERSONNEL " + "WHERE PERSONNEL.PERSONNEL_ID=ROLEPERSONNEL.PERSONNEL_ID "
					+ "AND ROLE.ROLE_ID=ROLEPERSONNEL.ROLE_ID " + "AND ROLE.ROLE_ID='" + r.getRoleUID() + "' "
					+ "ORDER BY PERSONNEL_LASTNAME, PERSONNEL_FIRSTNAME";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next())
				members.put(new Integer(rs.getInt("PERSONNEL_ID")), PersonnelDB.createPersonnelBean(rs));
		}
		catch (SQLException e) {
			System.err.println("PersonnelDB.getPersonnel(Role): " + e);
			throw new PersonnelException("Can not extract personnel from DB: " + e);
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
		return members;
	}

	public static Vector<Personnel> getPersonnelList(Role r) throws PersonnelException {

		Vector<Personnel> members = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			members = new Vector<Personnel>(100, 100);

			sql = "SELECT PERSONNEL.PERSONNEL_ID, PERSONNEL_USERNAME, PERSONNEL_PASSWORD, PERSONNEL_FIRSTNAME, "
					+ "PERSONNEL_LASTNAME, PERSONNEL_EMAIL, PERSONNEL_CATEGORYID, "
					+ "nvl(PERSONNEL_SUPERVISOR_ID, 0) PERSONNEL_SUPERVISOR_ID, " + "nvl(SCHOOL_ID, 0) SCHOOL_ID "
					+ "FROM PERSONNEL, ROLE, ROLEPERSONNEL " + "WHERE PERSONNEL.PERSONNEL_ID=ROLEPERSONNEL.PERSONNEL_ID "
					+ "AND ROLE.ROLE_ID=ROLEPERSONNEL.ROLE_ID " + "AND ROLE.ROLE_ID='" + r.getRoleUID() + "' "
					+ "ORDER BY PERSONNEL_LASTNAME, PERSONNEL_FIRSTNAME";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next())
				members.add(PersonnelDB.createPersonnelBean(rs));
		}
		catch (SQLException e) {
			System.err.println("PersonnelDB.getPersonnelList(Role): " + e);
			throw new PersonnelException("Can not extract personnel from DB: " + e);
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
		return members;
	}

	public static Personnel[] getPersonnelByRole(String role_uid) throws PersonnelException {

		Vector<Personnel> members = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			members = new Vector<Personnel>(100, 100);

			sql = "SELECT PERSONNEL.PERSONNEL_ID, PERSONNEL_USERNAME, PERSONNEL_PASSWORD, PERSONNEL_FIRSTNAME, "
					+ "PERSONNEL_LASTNAME, PERSONNEL_EMAIL, PERSONNEL_CATEGORYID, "
					+ "nvl(PERSONNEL_SUPERVISOR_ID, 0) PERSONNEL_SUPERVISOR_ID, " + "nvl(SCHOOL_ID, 0) SCHOOL_ID "
					+ "FROM PERSONNEL, ROLE, ROLEPERSONNEL " + "WHERE PERSONNEL.PERSONNEL_ID=ROLEPERSONNEL.PERSONNEL_ID "
					+ "AND ROLE.ROLE_ID=ROLEPERSONNEL.ROLE_ID " + "AND ROLE.ROLE_ID='" + role_uid + "' "
					+ "ORDER BY PERSONNEL_LASTNAME, PERSONNEL_FIRSTNAME";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next())
				members.add(PersonnelDB.createPersonnelBean(rs));
		}
		catch (SQLException e) {
			System.err.println("PersonnelDB.getPersonnelList(Role): " + e);
			throw new PersonnelException("Can not extract personnel from DB: " + e);
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
		return (Personnel[]) members.toArray(new Personnel[0]);
	}

	public static Vector<Personnel> getPersonnelList(Permission perm) throws PersonnelException {

		Vector<Personnel> members = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			members = new Vector<Personnel>(100, 100);

			sql = "SELECT DISTINCT PERSONNEL.PERSONNEL_ID, PERSONNEL_USERNAME, PERSONNEL_PASSWORD, PERSONNEL_FIRSTNAME, "
					+ "PERSONNEL_LASTNAME, PERSONNEL_EMAIL, PERSONNEL_CATEGORYID, "
					+ "nvl(PERSONNEL_SUPERVISOR_ID, 0) PERSONNEL_SUPERVISOR_ID, " + "nvl(SCHOOL_ID, 0) SCHOOL_ID "
					+ "FROM PERSONNEL, ROLEPERSONNEL, ROLEPERMISSION "
					+ "WHERE PERSONNEL.PERSONNEL_ID=ROLEPERSONNEL.PERSONNEL_ID "
					+ "AND ROLEPERSONNEL.ROLE_ID=ROLEPERMISSION.ROLE_ID " + "AND ROLEPERMISSION.PERMISSION_ID='"
					+ perm.getPermissionUID() + "' " + "ORDER BY PERSONNEL_LASTNAME, PERSONNEL_FIRSTNAME";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next())
				members.add(PersonnelDB.createPersonnelBean(rs));
		}
		catch (SQLException e) {
			System.err.println("PersonnelDB.getPersonnelList(Permission): " + e);
			throw new PersonnelException("Can not extract personnel from DB: " + e);
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
		return members;
	}

	public static Personnel[] getPersonnelByPermission(String perm) throws PersonnelException {

		ArrayList<Personnel> members = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			members = new ArrayList<Personnel>(10);

			sql = "SELECT DISTINCT PERSONNEL.PERSONNEL_ID, PERSONNEL_USERNAME, PERSONNEL_PASSWORD, PERSONNEL_FIRSTNAME, "
					+ "PERSONNEL_LASTNAME, PERSONNEL_EMAIL, PERSONNEL_CATEGORYID, "
					+ "nvl(PERSONNEL_SUPERVISOR_ID, 0) PERSONNEL_SUPERVISOR_ID, " + "nvl(SCHOOL_ID, 0) SCHOOL_ID "
					+ "FROM PERSONNEL, ROLEPERSONNEL, ROLEPERMISSION "
					+ "WHERE PERSONNEL.PERSONNEL_ID=ROLEPERSONNEL.PERSONNEL_ID "
					+ "AND ROLEPERSONNEL.ROLE_ID=ROLEPERMISSION.ROLE_ID " + "AND ROLEPERMISSION.PERMISSION_ID='" + perm + "' "
					+ "ORDER BY PERSONNEL_LASTNAME, PERSONNEL_FIRSTNAME";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next())
				members.add(PersonnelDB.createPersonnelBean(rs));
		}
		catch (SQLException e) {
			System.err.println("PersonnelDB.getPersonnelList(Permission): " + e);
			throw new PersonnelException("Can not extract personnel from DB: " + e);
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
		return (Personnel[]) members.toArray(new Personnel[0]);
	}

	public static Map<Integer, Personnel> getPersonnel(School s) throws PersonnelException {

		Map<Integer, Personnel> members = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			members = new HashMap<Integer, Personnel>(10);

			sql = "SELECT PERSONNEL.PERSONNEL_ID, PERSONNEL_USERNAME, PERSONNEL_PASSWORD, PERSONNEL_FIRSTNAME, "
					+ "PERSONNEL_LASTNAME, PERSONNEL_EMAIL, PERSONNEL_CATEGORYID, "
					+ "nvl(PERSONNEL_SUPERVISOR_ID, 0) PERSONNEL_SUPERVISOR_ID, " + "nvl(SCHOOL.SCHOOL_ID, 0) SCHOOL_ID "
					+ "FROM PERSONNEL, SCHOOL " + "WHERE PERSONNEL.SCHOOL_ID=SCHOOL.SCHOOL_ID " + "AND SCHOOL.SCHOOL_ID="
					+ s.getSchoolID() + " ORDER BY PERSONNEL_LASTNAME, PERSONNEL_FIRSTNAME";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next())
				members.put(new Integer(rs.getInt("PERSONNEL_ID")), PersonnelDB.createPersonnelBean(rs));
		}
		catch (SQLException e) {
			System.err.println("PersonnelDB.getPersonnel(School): " + e);
			throw new PersonnelException("Can not extract personnel from DB: " + e);
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
		return members;
	}

	public static Vector<Personnel> getPersonnelList(School s) throws PersonnelException {

		Vector<Personnel> members = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			members = new Vector<Personnel>(100, 100);

			sql = "SELECT PERSONNEL.PERSONNEL_ID, PERSONNEL_USERNAME, PERSONNEL_PASSWORD, PERSONNEL_FIRSTNAME, "
					+ "PERSONNEL_LASTNAME, PERSONNEL_EMAIL, PERSONNEL_CATEGORYID, "
					+ "nvl(PERSONNEL_SUPERVISOR_ID, 0) PERSONNEL_SUPERVISOR_ID, " + "nvl(SCHOOL.SCHOOL_ID, 0) SCHOOL_ID "
					+ "FROM PERSONNEL, SCHOOL " + "WHERE PERSONNEL.SCHOOL_ID=SCHOOL.SCHOOL_ID " + "AND SCHOOL.SCHOOL_ID="
					+ s.getSchoolID() + " ORDER BY PERSONNEL_LASTNAME, PERSONNEL_FIRSTNAME";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next())
				members.add(PersonnelDB.createPersonnelBean(rs));
		}
		catch (SQLException e) {
			System.err.println("PersonnelDB.getPersonnel(School): " + e);
			throw new PersonnelException("Can not extract personnel from DB: " + e);
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
		return members;
	}

	public static Personnel[] searchPersonnel(String name) throws PersonnelException {

		Vector<Personnel> members = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			String[] names = name.trim().split(" ");

			members = new Vector<Personnel>(10, 10);

			sql = "SELECT PERSONNEL_ID, PERSONNEL_USERNAME, PERSONNEL_PASSWORD, PERSONNEL_FIRSTNAME, "
					+ "PERSONNEL_LASTNAME, PERSONNEL_EMAIL, PERSONNEL_CATEGORYID, "
					+ "nvl(PERSONNEL_SUPERVISOR_ID, 0) PERSONNEL_SUPERVISOR_ID, nvl(SCHOOL_ID, 0) SCHOOL_ID "
					+ "FROM AWSD_USER.PERSONNEL WHERE";

			// System.out.println("name.length: " + names.length);

			if (names.length > 1)
				sql += " PERSONNEL_FIRSTNAME LIKE '%" + names[0].toUpperCase() + "%' AND PERSONNEL_LASTNAME LIKE '%"
						+ names[1].toUpperCase() + "%'";
			else
				sql += " PERSONNEL_FIRSTNAME LIKE '%" + names[0].toUpperCase() + "%' OR PERSONNEL_LASTNAME LIKE '%"
						+ names[0].toUpperCase() + "%' OR PERSONNEL_EMAIL LIKE '%" + names[0].toLowerCase() + "%'";

			sql += " ORDER BY PERSONNEL_LASTNAME, PERSONNEL_FIRSTNAME";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next())
				members.add(createPersonnelBean(rs));
		}
		catch (SQLException e) {
			System.err.println("Personnel[] searchPersonnel(String name): " + e);
			throw new PersonnelException("Can not extract personnel from DB: " + e);
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
		return (Personnel[]) members.toArray(new Personnel[0]);
	}

	public static int addPersonnel(Personnel p) throws PersonnelException {

		Connection con = null;
		PreparedStatement stat = null;
		Statement stat2 = null;
		ResultSet rs = null;
		String sql;
		int check = 0, id = -1, params = 0;
		Personnel supervisor = null;
		School s = null;

		try {
			sql = "INSERT INTO PERSONNEL(PERSONNEL_ID, PERSONNEL_FIRSTNAME, PERSONNEL_LASTNAME, "
					+ "PERSONNEL_CATEGORYID, PERSONNEL_USERNAME, PERSONNEL_PASSWORD";

			supervisor = p.getSupervisor();
			if (supervisor != null) {
				System.err.println(p.getUserName() + ": " + supervisor.getPersonnelID());
				sql += ", PERSONNEL_SUPERVISOR_ID";
			}
			else {
				System.err.println(p.getUserName() + ": NO SUPERVISOR");
			}

			sql += ", PERSONNEL_EMAIL";

			s = p.getSchool();
			if (s != null) {
				sql += ", SCHOOL_ID";
				System.err.println(p.getUserName() + ": " + s.getSchoolID());
			}
			else {
				System.err.println(p.getUserName() + ": NO SCHOOL");
			}

			sql += ") VALUES(PERSONNEL_SEQ.nextval, ?, ?, ?, ?, ?,";

			if (supervisor != null) {
				sql += "?, ";
			}

			sql += "? ";

			if (s != null) {
				sql += ", ?";
			}
			sql += ")";

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareStatement(sql);
			stat.clearParameters();
			stat.setString(1, p.getFirstName().trim().toUpperCase());
			stat.setString(2, p.getLastName().trim().toUpperCase());
			stat.setInt(3, p.getPersonnelCategory().getPersonnelCategoryID());
			stat.setString(4, p.getUserName().trim().toLowerCase());

			if (!StringUtils.isEmpty(p.getPassword())) {
				stat.setString(5, p.getPassword().trim());
			}
			else {
				stat.setNull(5, OracleTypes.NULL);
			}

			params = 6;

			if (supervisor != null) {
				stat.setInt(params++, supervisor.getPersonnelID());

			}

			stat.setString(params++, p.getEmailAddress().trim());

			if (s != null) {
				stat.setInt(params++, s.getSchoolID());
			}

			check = stat.executeUpdate();
			con.commit();
			stat.close();

			if (check == 1) {
				stat2 = con.createStatement();
				rs = stat2.executeQuery("SELECT PERSONNEL_SEQ.CURRVAL pid FROM DUAL");
				if (rs.next()) {
					id = rs.getInt("pid");
				}
				else {
					id = -1;
				}
				rs.close();
				stat2.close();
			}

			con.close();
		}
		catch (SQLException e) {
			System.err.println("PersonnelDB.addPersonnel(): " + e);
			throw new PersonnelException("Could not add personnel to DB: " + e);
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

	public static boolean updatePersonnel(Personnel p) throws PersonnelException {

		Connection con = null;
		PreparedStatement stat = null;
		String sql;
		int check = 0, params = 0;
		Personnel supervisor = null;
		School s = null;

		try {
			sql = "UPDATE PERSONNEL SET PERSONNEL_FIRSTNAME=?," + " PERSONNEL_LASTNAME=?," + " PERSONNEL_USERNAME=?,"
					+ " PERSONNEL_PASSWORD=?," + " PERSONNEL_EMAIL=?," + " PERSONNEL_CATEGORYID=?";

			supervisor = p.getSupervisor();
			if (supervisor != null) {
				sql += ", PERSONNEL_SUPERVISOR_ID=?";
			}
			else {
				sql += ", PERSONNEL_SUPERVISOR_ID=null";
			}

			s = p.getSchool();
			if (s != null) {
				sql += ", SCHOOL_ID=?";
			}
			else {
				sql += ", SCHOOL_ID=null";
			}

			sql += " WHERE PERSONNEL_ID=?";

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareStatement(sql);

			stat.setString(1, p.getFirstName().trim().toUpperCase());
			stat.setString(2, p.getLastName().trim().toUpperCase());
			stat.setString(3, p.getUserName().trim().toLowerCase());

			if (!StringUtils.isEmpty(p.getPassword())) {
				stat.setString(4, p.getPassword().trim());
			}
			else {
				stat.setNull(4, OracleTypes.NULL);
			}

			stat.setString(5, p.getEmailAddress().trim());
			stat.setInt(6, p.getPersonnelCategory().getPersonnelCategoryID());

			params = 7;

			supervisor = p.getSupervisor();
			if (supervisor != null) {
				stat.setInt(params++, supervisor.getPersonnelID());
			}

			s = p.getSchool();
			if (s != null) {
				stat.setInt(params++, s.getSchoolID());
			}

			stat.setInt(params, p.getPersonnelID());

			check = stat.executeUpdate();
			stat.close();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("PersonnelDB.updatePersonnel(): " + e);
			throw new PersonnelException("Could not add personnel to DB: " + e);
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

	public static boolean setViewOnNextLogon(Personnel p, String app) throws PersonnelException {

		Connection con = null;
		PreparedStatement stat = null;
		String sql;
		int check;

		try {
			sql = "UPDATE PERSONNEL SET VIEW_ON_NEXT_LOGON=? WHERE PERSONNEL_ID=?";

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareStatement(sql);

			stat.setString(1, app);
			stat.setInt(2, p.getPersonnelID());

			check = stat.executeUpdate();
		}
		catch (SQLException e) {
			System.err.println("PersonnelDB.setViewOnNextLogon: " + e);
			throw new PersonnelException("Could not add personnel to DB: " + e);
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

	public static boolean setViewOnNextLogon(PersonnelCategory cat, String app) throws PersonnelException {

		Connection con = null;
		PreparedStatement stat = null;
		String sql;
		int check;

		try {
			sql = "UPDATE PERSONNEL SET VIEW_ON_NEXT_LOGON=? WHERE PERSONNEL_CATEGORYID=?";

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareStatement(sql);

			stat.setString(1, app);
			stat.setInt(2, cat.getPersonnelCategoryID());

			check = stat.executeUpdate();
		}
		catch (SQLException e) {
			System.err.println("PersonnelDB.setViewOnNextLogon: " + e);
			throw new PersonnelException("Could not add personnel to DB: " + e);
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

	public static boolean setDistrictCalendarUpdated(Personnel p, String value) throws PersonnelException {

		Connection con = null;
		PreparedStatement stat = null;
		String sql;
		int check;

		try {
			sql = "UPDATE PERSONNEL SET DISTRICT_CALENDAR_UPDATED=? WHERE PERSONNEL_ID=?";

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareStatement(sql);

			stat.setString(1, value);
			stat.setInt(2, p.getPersonnelID());

			check = stat.executeUpdate();
		}
		catch (SQLException e) {
			System.err.println("PersonnelDB.setDistrictCalendarUpdated: " + e);
			throw new PersonnelException("Could not update district_calendar_update field.: " + e);
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

	public static String getDistrictCalendarUpdated(Personnel p) throws PersonnelException {

		String app;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT DISTRICT_CALENDAR_UPDATED FROM PERSONNEL " + "WHERE PERSONNEL_ID=" + p.getPersonnelID();

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);
			if (rs.next()) {
				app = rs.getString("DISTRICT_CALENDAR_UPDATED");
			}
			else {
				throw new PersonnelException("No Personnel record found matching personnel id [" + p.getPersonnelID() + "].");
			}
		}
		catch (SQLException e) {
			System.err.println("PersonnelDB.getDistrictCalendarUpdated(): " + e);
			throw new PersonnelException("Personnel ID Could not retrieve event from DB");
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
		return app;
	}

	public static Vector<Personnel> getPotentialSupervisors() throws PersonnelException {

		Vector<Personnel> supervisors = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			supervisors = new Vector<Personnel>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_package.get_all_potential_supervisors; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				supervisors.add(PersonnelDB.createPersonnelBean(rs));
		}
		catch (SQLException e) {
			System.err.println("PersonnelDB.getPotential(): " + e);
			throw new PersonnelException("Can not extract supervisors from DB: " + e);
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
		return supervisors;
	}

	public static HashMap<Integer, RequestAssignment> getMaintenanceRequestAssignedPersonnel(int req)
			throws PersonnelException {

		HashMap<Integer, RequestAssignment> assigned = null;
		Personnel tmp = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			assigned = new HashMap<Integer, RequestAssignment>(3);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.maint_req_sys.get_maint_req_personnel(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, req);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if (rs.getInt("PID") > 0) {
					tmp = new Personnel(rs.getInt("PERSONNEL_ID"), rs.getString("PERSONNEL_USERNAME"), rs.getString(
							"PERSONNEL_PASSWORD"), rs.getString("PERSONNEL_FIRSTNAME"), rs.getString(
									"PERSONNEL_LASTNAME"), rs.getString("PERSONNEL_EMAIL"), rs.getInt(
											"PERSONNEL_CATEGORYID"), rs.getInt("PERSONNEL_SUPERVISOR_ID"), rs.getInt("SCHOOL_ID"));

					assigned.put(new Integer(tmp.getPersonnelID()),
							new RequestAssignment(tmp, new java.util.Date(rs.getDate("DATE_ASSIGNED").getTime())));
				}
				else
					assigned.put(new Integer(rs.getInt("PID")), new RequestAssignment(PersonnelType.get(
							rs.getInt("PID")), new java.util.Date(rs.getDate("DATE_ASSIGNED").getTime())));
			}
		}
		catch (SQLException e) {
			System.err.println("PersonnelDB.getPotential(): " + e);
			throw new PersonnelException("Can not extract supervisors from DB: " + e);
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
		return assigned;
	}

	public static Personnel[] getMaintenanceAssigmentPersonnel() throws PersonnelException {

		Vector<Personnel> ass = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			ass = new Vector<Personnel>(50);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.maint_req_sys.get_potential_maint_personnel; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				ass.add(createPersonnelBean(rs));

		}
		catch (SQLException e) {
			System.err.println("PersonnelDB.getPotential(): " + e);
			throw new PersonnelException("Can not extract supervisors from DB: " + e);
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
		return (Personnel[]) ass.toArray(new Personnel[0]);
	}

	public static HashMap<Integer, Personnel> getSchoolAssistantPrincipals(School s) throws PersonnelException {

		HashMap<Integer, Personnel> aps = null;
		Personnel tmp = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			aps = new HashMap<Integer, Personnel>(3);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_school_aps(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, s.getSchoolID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				tmp = PersonnelDB.createPersonnelBean(rs);

				aps.put(new Integer(tmp.getPersonnelID()), tmp);
			}
		}
		catch (SQLException e) {
			System.err.println("PersonnelDB.getPotential(): " + e);
			throw new PersonnelException("Can not extract supervisors from DB: " + e);
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
		return aps;
	}

	public static void userLoggedOn(Personnel p) throws PersonnelException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.ms_login.user_logon(?); end;");
			stat.setInt(1, p.getPersonnelID());
			stat.execute();

			con.commit();
		}
		catch (SQLException e) {
			System.err.println("getUserLoggedOn(Personnel p): " + e);
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			throw new PersonnelException("cannot logon user in db: " + e);
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
	}

	public static Personnel createPersonnelBean(ResultSet rs) {

		Personnel abean = null;

		try {
			abean = new Personnel(rs.getInt("PERSONNEL_ID"), rs.getString("PERSONNEL_USERNAME"), rs.getString(
					"PERSONNEL_PASSWORD"), rs.getString("PERSONNEL_FIRSTNAME"), rs.getString("PERSONNEL_LASTNAME"), rs.getString(
							"PERSONNEL_EMAIL"), rs.getInt(
									"PERSONNEL_CATEGORYID"), rs.getInt("PERSONNEL_SUPERVISOR_ID"), rs.getInt("SCHOOL_ID"));
		}
		catch (SQLException e) {
			e.printStackTrace();
			abean = null;
		}
		catch (Exception e) {
			e.printStackTrace();
			abean = null;
		}

		return abean;
	}

	public static Personnel createAssistantPrincipalPersonnelBean(ResultSet rs) {

		Personnel abean = null;

		try {
			abean = new Personnel(rs.getInt("AP_PERSONNEL_ID"), rs.getString("AP_PERSONNEL_USERNAME"), rs.getString(
					"AP_PERSONNEL_PASSWORD"), rs.getString("AP_PERSONNEL_FIRSTNAME"), rs.getString(
							"AP_PERSONNEL_LASTNAME"), rs.getString("AP_PERSONNEL_EMAIL"), rs.getInt(
									"AP_PERSONNEL_CATEGORYID"), rs.getInt("AP_PERSONNEL_SUPERVISOR_ID"), rs.getInt("SCHOOL_ID"));
		}
		catch (SQLException e) {
			e.printStackTrace();
			abean = null;
		}
		catch (Exception e) {
			e.printStackTrace();
			abean = null;
		}

		return abean;
	}

	public static Personnel getPersonnelByEmailNoCase(String email) throws PersonnelException, SQLException {

		Personnel p = null;
		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT PERSONNEL_ID, PERSONNEL_USERNAME, PERSONNEL_PASSWORD, PERSONNEL_FIRSTNAME, "
					+ "PERSONNEL_LASTNAME, PERSONNEL_EMAIL, PERSONNEL_CATEGORYID, "
					+ "NVL(PERSONNEL_SUPERVISOR_ID, 0) PERSONNEL_SUPERVISOR_ID, " + "NVL(SCHOOL_ID, 0) SCHOOL_ID FROM PERSONNEL "
					+ "WHERE lower(PERSONNEL_EMAIL)=? ORDER BY PERSONNEL_ID ASC";

			con = DAOUtils.getConnection();

			stat = con.prepareStatement(sql);
			stat.setString(1, email.toLowerCase());

			rs = stat.executeQuery();

			if (rs.next()) {
				p = new Personnel(rs.getInt("PERSONNEL_ID"), rs.getString("PERSONNEL_USERNAME"), rs.getString(
						"PERSONNEL_PASSWORD"), rs.getString(
								"PERSONNEL_FIRSTNAME"), rs.getString("PERSONNEL_LASTNAME"), rs.getString("PERSONNEL_EMAIL"), rs.getInt(
										"PERSONNEL_CATEGORYID"), rs.getInt("PERSONNEL_SUPERVISOR_ID"), rs.getInt("SCHOOL_ID"));
			}
			else {
				throw new PersonnelException("No Personnel record found matching email [" + email + "].");
			}
		}
		catch (SQLException e) {
			System.err.println("Personnel getPersonnelByEmail(String email): " + e);
			throw e;
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