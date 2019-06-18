package com.awsd.school;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.awsd.personnel.Personnel;
import com.esdnl.dao.DAOUtils;

public class GradeDB {

	private static HashMap preloaded = null;

	static {
		try {
			preloaded = GradeDB.getGradesMap();
		}
		catch (GradeException e) {
			preloaded = null;
		}
	}

	public static Vector getGrades() throws GradeException {

		Vector grades = null;
		Grade s = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			grades = new Vector(10);

			sql = "SELECT Grade_ID, Grade_NAME " + "FROM Grade";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				s = new Grade(rs.getInt("Grade_ID"), rs.getString("Grade_NAME"));

				grades.add(s);
			}
		}
		catch (SQLException e) {
			System.err.println("GradeDB.getGrades(): " + e);
			throw new GradeException("Can not extract Grades from DB: " + e);
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
		return grades;
	}

	public static HashMap getGradesMap() throws GradeException {

		HashMap grades = null;
		Grade s = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			grades = new HashMap();

			sql = "SELECT Grade_ID, Grade_NAME " + "FROM Grade";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				s = new Grade(rs.getInt("Grade_ID"), rs.getString("Grade_NAME"));

				grades.put(new Integer(s.getGradeID()), s);
			}

		}
		catch (SQLException e) {
			System.err.println("GradeDB.getGrades(): " + e);
			throw new GradeException("Can not extract Grades from DB: " + e);
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
		return grades;
	}

	public static Vector getGrades(Personnel p) throws GradeException {

		Vector grades = null;
		Grade s = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			grades = new Vector(10);

			sql = "SELECT GRADE.Grade_ID, Grade_NAME " + "FROM Grade, GRADEPERSONNEL, PERSONNEL WHERE "
					+ "GRADE.GRADE_ID=GRADEPERSONNEL.GRADE_ID " + "AND GRADEPERSONNEL.PERSONNEL_ID = PERSONNEL.PERSONNEL_ID "
					+ "AND PERSONNEL.PERSONNEL_ID=" + p.getPersonnelID();

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				s = new Grade(rs.getInt("Grade_ID"), rs.getString("Grade_NAME"));

				grades.add(s);
			}
		}
		catch (SQLException e) {
			System.err.println("GradeDB.getGrades(): " + e);
			throw new GradeException("Can not extract Grades from DB: " + e);
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
		return grades;
	}

	public static Grade getGrade(int id) throws GradeException {

		Grade s = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		Integer id_obj = new Integer(id);

		if ((preloaded != null) && (preloaded.containsKey(id_obj)))
			s = (Grade) preloaded.get(id_obj);
		else {
			try {
				sql = "SELECT Grade_ID, Grade_NAME FROM Grade WHERE Grade_ID=" + id;

				con = DAOUtils.getConnection();
				stat = con.createStatement();
				rs = stat.executeQuery(sql);

				if (rs.next())
					s = new Grade(rs.getInt("Grade_ID"), rs.getString("Grade_NAME"));
				else
					throw new GradeException("No Grade matching ID: " + id);
			}
			catch (SQLException e) {
				System.err.println("GradeDB.getGrade(): " + e);
				throw new GradeException("Can not extract Grade from DB: " + e);
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
		}
		return s;
	}

	public static boolean addGradePersonnel(Grades grades, Personnel p) throws GradeException {

		Connection con = null;
		PreparedStatement stat = null;
		Iterator iter = null;
		Grade g = null;
		String sql;
		int check = 0, id = -1;

		try {
			iter = grades.iterator();

			sql = "INSERT INTO GRADEPERSONNEL VALUES(?, ?)";

			con = DAOUtils.getConnection();

			con.setAutoCommit(false);
			stat = con.prepareStatement(sql);

			while (iter.hasNext()) {
				g = (Grade) iter.next();
				stat.clearParameters();
				stat.setInt(2, g.getGradeID());
				stat.setInt(1, p.getPersonnelID());
				stat.executeUpdate();
			}
			con.commit();

		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("GradeDB.addGradePersonnel(): " + e);

			throw new GradeException("Could not add GradePersonnel to DB: " + e);
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

	public static boolean deleteGradePersonnel(Grade g, Personnel p) throws GradeException {

		Connection con = null;
		PreparedStatement stat = null;
		String sql;
		int check = 0, id = -1;

		try {

			sql = "DELETE FROM GRADEPERSONNEL WHERE GRADE_ID=? AND PERSONNEL_ID=?";

			con = DAOUtils.getConnection();

			con.setAutoCommit(false);
			stat = con.prepareStatement(sql);

			stat.setInt(1, g.getGradeID());
			stat.setInt(2, p.getPersonnelID());
			stat.executeUpdate();
			con.commit();

		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("GrdaeDB.deleteGradePersonnel(): " + e);

			throw new GradeException("Could not delete GradePersonnel to DB: " + e);
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
