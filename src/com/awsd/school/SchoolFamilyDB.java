package com.awsd.school;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Vector;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelException;
import com.esdnl.dao.DAOUtils;

public class SchoolFamilyDB {

	public static Vector getSchoolFamilies() throws SchoolFamilyException {

		Vector sfs = null;
		SchoolFamily sf = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			sfs = new Vector(10);

			sql = "SELECT * FROM SCHOOL_FAMILY order by family_name asc";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				sf = new SchoolFamily(rs.getInt("family_id"), rs.getInt("ps_id"), rs.getString("family_name"));

				sfs.add(sf);
			}
		}
		catch (SQLException e) {
			System.err.println("SchoolFamilyDB.getSchoolFamilies(): " + e);
			throw new SchoolFamilyException("Can not extract families from DB: " + e);
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
		return sfs;
	}

	public static SchoolFamily getSchoolFamily(int family_id) throws SchoolFamilyException {

		SchoolFamily sys = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT * FROM SCHOOL_FAMILY WHERE FAMILY_ID=" + family_id;

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			if (rs.next()) {
				sys = new SchoolFamily(rs.getInt("family_id"), rs.getInt("ps_id"), rs.getString("family_name"));
			}
			else {
				throw new SchoolFamilyException("Can not extract School Family[" + family_id + "] from DB ");
			}
		}
		catch (SQLException e) {
			System.err.println("SchoolFamilyDB.getSchoolFamily(family_id): " + e);
			throw new SchoolFamilyException("Can not extract school family from DB: " + e);
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
		return sys;
	}

	public static SchoolFamily getSchoolFamily(Personnel ps) throws SchoolFamilyException {

		SchoolFamily sys = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT * FROM SCHOOL_FAMILY WHERE PS_ID=" + ps.getPersonnelID();

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			if (rs.next()) {
				sys = new SchoolFamily(rs.getInt("family_id"), rs.getInt("ps_id"), rs.getString("family_name"));
			}
			else {
				//throw  new SchoolFamilyException("Can not extract School Syste from DB ");
				sys = null;
			}
		}
		catch (SQLException e) {
			System.err.println("SchoolFamilyDB.getSchoolFamily(Personnel ps): " + e);
			throw new SchoolFamilyException("Can not extract school family from DB: " + e);
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
		return sys;
	}

	public static SchoolFamily getSchoolFamily(School school) throws SchoolFamilyException {

		SchoolFamily sys = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT * FROM SCHOOL_FAMILY, SCHOOL_FAMILY_SCHOOLS WHERE ";
			sql += "SCHOOL_FAMILY.FAMILY_ID=SCHOOL_FAMILY_SCHOOLS.FAMILY_ID ";
			sql += "AND SCHOOL_FAMILY_SCHOOLS.SCHOOL_ID=" + school.getSchoolID();

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			if (rs.next()) {
				sys = new SchoolFamily(rs.getInt("family_id"), rs.getInt("ps_id"), rs.getString("family_name"));
			}
			else {
				throw new SchoolFamilyException("Can not extract School Family from DB ");
			}
		}
		catch (SQLException e) {
			System.err.println("SchoolFamilyDB.getSchoolFamily(School s): " + e);
			throw new SchoolFamilyException("Can not extract school family from DB: " + e);
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
		return sys;
	}

	public static boolean updateSchoolFamily(SchoolFamily s) throws SchoolFamilyException, PersonnelException {

		Connection con = null;
		PreparedStatement stat = null;
		String sql;
		int check = 0;

		try {
			sql = "UPDATE SCHOOL_FAMILY SET FAMILY_NAME=?, PS_ID=? WHERE FAMILY_ID=?";

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareStatement(sql);
			stat.setString(1, s.getSchoolFamilyName());
			stat.setInt(2, s.getProgramSpecialist().getPersonnelID());
			stat.setInt(3, s.getSchoolFamilyID());

			check = stat.executeUpdate();
		}
		catch (SQLException e) {
			System.err.println("SchoolFamilyDB.updateSchoolFamily(SchoolFamily s): " + e);
			throw new SchoolFamilyException("Could not update school family in DB: " + e);
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

	public static boolean updateSchoolFamily(SchoolFamily family, String schools[])
			throws SchoolFamilyException,
				PersonnelException {

		Connection con = null;
		PreparedStatement stat = null;
		String sql;
		int check = 0;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			sql = "UPDATE SCHOOL_FAMILY SET FAMILY_NAME=?, PS_ID=? WHERE FAMILY_ID=?";
			stat = con.prepareStatement(sql);
			stat.setString(1, family.getSchoolFamilyName());
			stat.setInt(2, family.getProgramSpecialist().getPersonnelID());
			stat.setInt(3, family.getSchoolFamilyID());

			check = stat.executeUpdate();

			if (check == 1) {
				sql = "DELETE FROM SCHOOL_FAMILY_SCHOOLS WHERE FAMILY_ID=?";

				stat = con.prepareStatement(sql);
				stat.clearParameters();
				stat.setInt(1, family.getSchoolFamilyID());

				stat.executeUpdate();

				sql = "INSERT INTO SCHOOL_FAMILY_SCHOOLS VALUES(?, ?)";
				stat = con.prepareStatement(sql);

				for (int i = 0; i < schools.length; i++) {
					stat.clearParameters();
					stat.setInt(1, Integer.parseInt(schools[i]));
					stat.setInt(2, family.getSchoolFamilyID());

					stat.executeUpdate();
				}
			}
			else {
				con.rollback();
				throw new SchoolFamilyException("Could not modify family");
			}

			con.commit();
		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {
				System.err.println("COULD NOT ROLLBACK TRANSACTION!!!");
			}
			System.err.println("SchoolFamilyDB.updateSchoolFamily(SchoolFamily s): " + e);
			throw new SchoolFamilyException("Could not update school family in DB: " + e);
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

	public static int addSchoolFamily(SchoolFamily s, String school_id[])
			throws SchoolFamilyException,
				PersonnelException {

		Connection con = null;
		PreparedStatement stat = null;
		Statement stat2 = null;
		ResultSet rs = null;
		String sql;
		int check = 0, id = -1;

		try {
			sql = "INSERT INTO SCHOOL_FAMILY VALUES(SCHOOL_FAMILY_SEQ.nextval, ?, ?)";

			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareStatement(sql);
			stat.clearParameters();

			if (s.getProgramSpecialist() != null) {
				stat.setInt(1, s.getProgramSpecialist().getPersonnelID());
			}
			else {
				stat.setNull(1, Types.NUMERIC);
			}

			stat.setString(2, s.getSchoolFamilyName());

			check = stat.executeUpdate();

			System.out.println("BASIC FAMILY ADDED FOR " + s.getProgramSpecialist().getFullNameReverse());

			try {
				stat.close();
				stat = null;
			}
			catch (Exception e) {
				stat = null;
			}

			if (check == 1) {
				stat2 = con.createStatement();
				rs = stat2.executeQuery("SELECT SCHOOL_FAMILY_SEQ.CURRVAL family_id FROM DUAL");

				if (rs.next()) {
					id = rs.getInt("family_id");

					System.out.println("FAMILY_ID: " + id);

					for (int i = 0; i < school_id.length; i++) {
						sql = "INSERT INTO SCHOOL_FAMILY_SCHOOLS VALUES(" + school_id[i] + ", " + id + ")";
						System.out.println(sql);
						check = stat2.executeUpdate(sql);

						if (check != 1) {
							con.rollback();
							throw new SchoolFamilyException("Could not add School Family Schools to DB.");
						}
					}
				}
				else {
					id = -1;
					con.rollback();
					throw new SchoolFamilyException("Could not retrieve School Family ID from DB after ADD attempt.");
				}
				try {
					rs.close();
					rs = null;
				}
				catch (Exception e) {
					rs = null;
				}
				try {
					stat2.close();
					stat2 = null;
				}
				catch (Exception e) {
					stat2 = null;
				}
			}
			else {
				con.rollback();
				throw new SchoolFamilyException("Could not add School Family to DB.");
			}

			con.commit();

			con.close();
		}
		catch (SQLException e) {
			System.err.println("SchoolFamilyDB.addSchoolFamily(): " + e);

			try {
				con.rollback();
			}
			catch (Exception ex) {
				System.err.println("COULD NOT ROLLBACK TRANSACTION.");
			}
			throw new SchoolFamilyException("Could not add school family to DB: " + e);
		}
		finally {
			try {
				rs.close();
				rs = null;
			}
			catch (Exception e) {
				rs = null;
			}

			try {
				stat.close();
				stat = null;
			}
			catch (Exception e) {
				stat = null;
			}

			try {
				stat2.close();
				stat2 = null;
			}
			catch (Exception e) {
				stat2 = null;
			}

			try {
				con.close();
				con = null;
			}
			catch (Exception e) {
				con = null;
			}
		}
		return (id);
	}

	public static boolean addSchoolFamilySchool(int family_id, int school_id) throws SchoolFamilyException {

		Connection con = null;
		PreparedStatement stat = null;
		Statement stat2 = null;
		ResultSet rs = null;
		String sql;
		int check = 0, id = -1, params = 0;

		try {
			sql = "INSERT INTO SCHOOL_FAMILY_SCHOOLS VALUES(?, ?)";

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareStatement(sql);
			stat.clearParameters();
			stat.setInt(1, family_id);
			stat.setInt(2, school_id);

			check = stat.executeUpdate();
		}
		catch (SQLException e) {
			System.err.println("SchoolFamilyDB.addSchoolFamily(): " + e);
			throw new SchoolFamilyException("Could not add school family school to DB: " + e);
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

	public static boolean deleteSchoolFamily(int family_id) throws SchoolFamilyException {

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;
		int check = 0, id = -1;

		try {
			sql = "DELETE FROM SCHOOL_FAMILY WHERE FAMILY_ID=" + family_id;

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.createStatement();
			check = stat.executeUpdate(sql);
		}
		catch (SQLException e) {
			System.err.println("SchoolFamilyDB.deleteSchoolFamily(): " + e);
			throw new SchoolFamilyException("Could not delete school family to DB: " + e);
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

	public static boolean deleteSchoolFamilySchool(int family_id, int school_id) throws SchoolFamilyException {

		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql;
		int check = 0, id = -1;

		try {
			sql = "DELETE FROM SCHOOL_FAMILY_SCHOOLS WHERE FAMILY_ID=? AND SCHOOL_ID=?";

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareStatement(sql);
			stat.setInt(1, family_id);
			stat.setInt(2, school_id);
			check = stat.executeUpdate();
		}
		catch (SQLException e) {
			System.err.println("SchoolFamilyDB.deleteSchoolFamilySchool(): " + e);
			throw new SchoolFamilyException("Could not delete school family school to DB: " + e);
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