package com.awsd.strike;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelException;
import com.awsd.school.School;
import com.esdnl.dao.DAOUtils;

public class SchoolStrikeGroupDB {

	public static SchoolStrikeGroup getSchoolStrikeGroup(int gid) throws StrikeException {

		SchoolStrikeGroup group = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT * FROM STRIKE_SCHOOL_GROUP WHERE GROUP_ID=" + gid;

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			if (rs.next()) {
				try {
					group = new SchoolStrikeGroup(rs.getInt("GROUP_ID"), rs.getInt("PERSONNEL_ID"));
				}
				catch (PersonnelException e) {
					System.err.println("SchoolStrikeGroupDB.getSchoolStrikeGroup(int): " + e);
					throw new StrikeException("STRIKE GROUP COORDINATOR ID IS INVALID");
				}
			}
			else {
				throw new StrikeException("No SchoolStrikeGroup matching ID: " + gid);
			}
			rs.close();
			stat.close();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("SchoolStrikeGroupDB.getSchoolStrikeGroup(int): " + e);
			throw new StrikeException("Can not extract SchoolStrikeGroup from DB: " + e);
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
		return group;
	}

	public static SchoolStrikeGroup getSchoolStrikeGroup(Personnel p) throws StrikeException {

		SchoolStrikeGroup group = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT * FROM STRIKE_SCHOOL_GROUP WHERE PERSONNEL_ID=" + p.getPersonnelID();

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			if (rs.next()) {
				group = new SchoolStrikeGroup(rs.getInt("GROUP_ID"), p);
			}
			else {
				//throw  new StrikeException("No SchoolStrikeGroup matching coordinator ID: " + p.getPersonnelID());
				group = null;
			}
			rs.close();
			stat.close();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("SchoolStrikeGroupDB.getSchoolStrikeGroup(int): " + e);
			throw new StrikeException("Can not extract SchoolStrikeGroup from DB: " + e);
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
		return group;
	}

	public static SchoolStrikeGroup getSchoolStrikeGroup(School s) throws StrikeException {

		SchoolStrikeGroup group = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		StringBuffer sql = null;

		try {

			sql = new StringBuffer("SELECT STRIKE_SCHOOL_GROUP.GROUP_ID, STRIKE_SCHOOL_GROUP.PERSONNEL_ID FROM SCHOOL, STRIKE_SCHOOL_GROUP, STRIKE_SCHOOL_GROUP_SCHOOLS WHERE ");
			sql.append("STRIKE_SCHOOL_GROUP.GROUP_ID=STRIKE_SCHOOL_GROUP_SCHOOLS.GROUP_ID ");
			sql.append("AND SCHOOL.SCHOOL_ID=STRIKE_SCHOOL_GROUP_SCHOOLS.SCHOOL_ID ");
			sql.append("AND SCHOOL.SCHOOL_ID=" + s.getSchoolID());

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql.toString());

			if (rs.next()) {
				try {
					group = new SchoolStrikeGroup(rs.getInt("GROUP_ID"), rs.getInt("PERSONNEL_ID"));
				}
				catch (PersonnelException e) {
					System.err.println("SchoolStrikeGroupDB.getSchoolStrikeGroup(int): " + e);
					throw new StrikeException("STRIKE GROUP COORDINATOR ID IS INVALID");
				}
			}
			else {
				//throw  new StrikeException("No SchoolStrikeGroup matching school ID: " + s.getSchoolID());
				group = null;
			}
			rs.close();
			stat.close();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("SchoolStrikeGroupDB.getSchoolStrikeGroup(int): " + e);
			throw new StrikeException("Can not extract SchoolStrikeGroup from DB: " + e);
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
		return group;
	}

	public static Vector getAllSchoolStrikeGroups() throws StrikeException {

		Vector groups = new Vector(5);
		SchoolStrikeGroup group = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT * FROM STRIKE_SCHOOL_GROUP";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				try {
					group = new SchoolStrikeGroup(rs.getInt("GROUP_ID"), rs.getInt("PERSONNEL_ID"));
					groups.add(group);
				}
				catch (PersonnelException e) {
					System.err.println("SchoolStrikeGroupDB.getSchoolStrikeGroup(int): " + e);
					throw new StrikeException("STRIKE GROUP COORDINATOR ID IS INVALID");
				}
			}

			rs.close();
			stat.close();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("SchoolStrikeGroupDB.getSchoolStrikeGroup(int): " + e);
			throw new StrikeException("Can not extract SchoolStrikeGroup from DB: " + e);
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
		return groups;
	}

	public static int addSchoolStrikeGroup(SchoolStrikeGroup s, String school_id[])
			throws StrikeException,
				PersonnelException {

		Connection con = null;
		PreparedStatement stat = null;
		Statement stat2 = null;
		ResultSet rs = null;
		String sql;
		int check = 0, id = -1;

		try {
			sql = "INSERT INTO STRIKE_SCHOOL_GROUP VALUES(STRIKE_SCHOOL_GROUP_SEQ.nextval, ?)";

			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareStatement(sql);
			stat.clearParameters();
			stat.setInt(1, s.getGroupCoordinatorID());

			check = stat.executeUpdate();
			//con.commit();
			stat.close();
			stat = null;

			if (check == 1) {
				stat2 = con.createStatement();
				rs = stat2.executeQuery("SELECT STRIKE_SCHOOL_GROUP_SEQ.CURRVAL group_id FROM DUAL");

				if (rs.next()) {
					id = rs.getInt("group_id");

					for (int i = 0; i < school_id.length; i++) {
						sql = "INSERT INTO STRIKE_SCHOOL_GROUP_SCHOOLS VALUES(" + id + ", " + school_id[i] + ")";
						check = stat2.executeUpdate(sql);

						if (check != 1) {
							con.rollback();
							throw new StrikeException("Could not add School Strike Group School to DB.");
						}
					}
				}
				else {
					id = -1;
					con.rollback();
					throw new StrikeException("Could not retrieve School Strike Group ID from DB.");
				}
				rs.close();
				rs = null;
				stat2.close();
				stat2 = null;
			}
			else {
				con.rollback();
				throw new StrikeException("Could not add School Strike Group to DB.");
			}

			con.commit();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("SchoolStrikeGroupDB.addSchoolSystem(): " + e);

			try {
				con.rollback();
			}
			catch (Exception ex) {
				System.err.println("COULD NOT ROLLBACK TRANSACTION.");
			}
			throw new StrikeException("Could not add school strike group to DB: " + e);
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			rs = null;
			try {
				stat.close();
			}
			catch (Exception e) {}
			stat = null;
			try {
				stat2.close();
			}
			catch (Exception e) {}
			stat2 = null;
			try {
				con.close();
			}
			catch (Exception e) {}
			con = null;
		}
		return (id);
	}

	public static boolean addSchoolStrikeGroupSchool(int ssg_id, int school_id) throws StrikeException {

		Connection con = null;
		PreparedStatement stat = null;
		Statement stat2 = null;
		ResultSet rs = null;
		String sql;
		int check = 0, id = -1, params = 0;

		try {
			sql = "INSERT INTO STRIKE_SCHOOL_GROUP_SCHOOLS VALUES(?, ?)";

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareStatement(sql);
			stat.clearParameters();
			stat.setInt(1, ssg_id);
			stat.setInt(2, school_id);

			check = stat.executeUpdate();
			con.commit();
			stat.close();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("SchoolStrikeGroupDB.addSchoolStrikeGroupSchool(): " + e);
			throw new StrikeException("Could not add school strike group school to DB: " + e);
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

	public static boolean deleteSchoolStrikeGroup(int group_id) throws StrikeException {

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;
		int check = 0, id = -1;

		try {
			sql = "DELETE FROM STRIKE_SCHOOL_GROUP WHERE GROUP_ID=" + group_id;

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.createStatement();
			check = stat.executeUpdate(sql);

			con.commit();
		}
		catch (SQLException e) {
			System.err.println("SchoolStrikeGroupDB.deleteSchoolStrikeGroup(): " + e);
			throw new StrikeException("Could not delete school strike group from DB: " + e);
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

	public static boolean deleteSchoolStrikeGroupSchool(int group_id, int school_id) throws StrikeException {

		Connection con = null;
		PreparedStatement pstat = null;
		String sql;
		int check = 0;

		try {
			sql = "DELETE FROM STRIKE_SCHOOL_GROUP_SCHOOLS WHERE GROUP_ID=? AND SCHOOL_ID=?";

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			pstat = con.prepareStatement(sql);
			pstat.setInt(1, group_id);
			pstat.setInt(2, school_id);
			check = pstat.executeUpdate();

			con.commit();
		}
		catch (SQLException e) {
			System.err.println("SchoolStrikeGroupDB.deleteSchoolStrikeGroupSchool(): " + e);
			throw new StrikeException("Could not delete school strike group school from DB: " + e);
		}
		finally {
			try {
				pstat.close();
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