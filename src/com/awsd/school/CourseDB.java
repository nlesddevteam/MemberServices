package com.awsd.school;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Vector;

import com.awsd.personnel.Personnel;
import com.esdnl.dao.DAOUtils;

public class CourseDB {

	public static Vector<Course> getCourses() throws CourseException {

		Vector<Course> courses = null;
		Course s = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			courses = new Vector<Course>(10);

			sql = "SELECT Course_ID, Course_NAME " + "FROM Course WHERE ACTIVE=1 ORDER BY Course_NAME";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				s = new Course(rs.getInt("Course_ID"), rs.getString("Course_NAME"));

				courses.add(s);
			}
		}
		catch (SQLException e) {
			System.err.println("CourseDB.getCourses(): " + e);
			throw new CourseException("Can not extract Courses from DB: " + e);
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
		return courses;
	}

	public static Vector<Course> getCourses(Personnel p) throws CourseException {

		Vector<Course> courses = null;
		Course s = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			courses = new Vector<Course>(10);

			sql = "SELECT COURSE.Course_ID, Course_NAME " + "FROM Course, CoursePERSONNEL, PERSONNEL WHERE "
					+ "Course.Course_ID=CoursePERSONNEL.Course_ID "
					+ "AND CoursePERSONNEL.PERSONNEL_ID = PERSONNEL.PERSONNEL_ID " + "AND PERSONNEL.PERSONNEL_ID="
					+ p.getPersonnelID() + " ORDER BY Course_NAME";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				s = new Course(rs.getInt("Course_ID"), rs.getString("Course_NAME"));

				courses.add(s);
			}
		}
		catch (SQLException e) {
			System.err.println("CourseDB.getCourses(): " + e);
			throw new CourseException("Can not extract Courses from DB: " + e);
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
		return courses;
	}

	public static Vector<Course> getCourses(Subject p) throws CourseException {

		Vector<Course> courses = null;
		Course s = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			courses = new Vector<Course>(10);

			sql = "SELECT Course_ID, Course_NAME " + "FROM Course, SUBJECTCOURSE, SUBJECT WHERE "
					+ "Course.Course_ID=SUBJECTCOURSE.Course_ID " + "AND SUBJECTCOURSE.SUBJECT_ID = SUBJECT.SUBJECT_ID "
					+ "AND SUBJECT.SUBJECT_ID=" + p.getSubjectID() + " AND ACTIVE=1 ORDER BY Course_NAME";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				s = new Course(rs.getInt("Course_ID"), rs.getString("Course_NAME"));

				courses.add(s);
			}
		}
		catch (SQLException e) {
			System.err.println("CourseDB.getCourses(): " + e);
			throw new CourseException("Can not extract Courses from DB: " + e);
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
		return courses;
	}

	public static Vector<Course> getCourses(Grade g) throws CourseException {

		Vector<Course> courses = null;
		Course s = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			courses = new Vector<Course>(10);

			sql = "SELECT Course_ID, Course_NAME " + "FROM Course WHERE " + "AND GRADE_ID=" + g.getGradeID()
					+ " AND ACTIVE=1 ORDER BY Course_NAME";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				s = new Course(rs.getInt("Course_ID"), rs.getString("Course_NAME"));

				courses.add(s);
			}
		}
		catch (SQLException e) {
			System.err.println("CourseDB.getCourses(): " + e);
			throw new CourseException("Can not extract Courses from DB: " + e);
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
		return courses;
	}

	public static Vector<Course> getCourses(Subject[] subs, Grade[] grds) throws CourseException {

		Vector<Course> courses = null;
		Course s = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		StringBuffer sql = null;
		boolean check = false;

		try {
			courses = new Vector<Course>(10);

			sql = new StringBuffer("SELECT COURSE.COURSE_ID, COURSE_NAME FROM COURSE WHERE (");

			for (int i = 0; i < grds.length; i++) {
				if (grds[i] == null) {
					continue;
				}
				sql.append("COURSE.GRADE_ID = " + grds[i].getGradeID());

				check = false;
				for (int j = i + 1; j < grds.length; j++) {
					if (grds[j] != null) {
						check = true;
						break;
					}
				}
				if (check) {
					sql.append(" OR ");
				}
			}

			sql.append(") AND (");
			for (int i = 0; i < subs.length; i++) {
				if (subs[i] == null) {
					continue;
				}
				sql.append("COURSE.SUBJECT_ID=" + subs[i].getSubjectID());

				if (i < subs.length - 1) {
					sql.append(" OR ");
				}
			}

			sql.append(") AND ACTIVE=1 ORDER BY Grade_ID, Course_NAME");

			// System.err.println(sql.toString());

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql.toString());

			while (rs.next()) {
				s = new Course(rs.getInt("Course_ID"), rs.getString("Course_NAME"));

				courses.add(s);
			}
		}
		catch (SQLException e) {
			System.err.println("CourseDB.getCourses(): " + e);
			throw new CourseException("Can not extract Courses from DB: " + e);
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
		return courses;
	}

	public static Course getCourse(int id) throws CourseException {

		Course s = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT Course_ID, Course_NAME FROM Course " + "WHERE Course_ID=" + id;

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			if (rs.next()) {
				s = new Course(rs.getInt("Course_ID"), rs.getString("Course_NAME"));
			}
			else {
				throw new CourseException("No Course matching ID: " + id);
			}
		}
		catch (SQLException e) {
			System.err.println("CourseDB.getCourse(): " + e);
			throw new CourseException("Can not extract Course from DB: " + e);
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
		return s;
	}

	@SuppressWarnings("unchecked")
	public static boolean addCoursePersonnel(Courses courses, Personnel p) throws CourseException {

		Connection con = null;
		PreparedStatement stat = null;
		Iterator<Course> iter = null;
		Course c = null;
		String sql;
		int check = 0;

		try {
			iter = (Iterator<Course>) courses.iterator();

			sql = "INSERT INTO COURSEPERSONNEL VALUES(?, ?)";

			con = DAOUtils.getConnection();

			con.setAutoCommit(false);
			stat = con.prepareStatement(sql);

			while (iter.hasNext()) {
				c = (Course) iter.next();
				stat.clearParameters();
				stat.setInt(1, c.getCourseID());
				stat.setInt(2, p.getPersonnelID());
				stat.executeUpdate();
			}
			con.commit();

		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("DocumentDB.addGradePersonnel(): " + e);

			throw new CourseException("Could not add CoursePersonnel to DB: " + e);
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

	public static boolean deleteCoursePersonnel(Course c, Personnel p) throws GradeException {

		Connection con = null;
		PreparedStatement stat = null;
		String sql;
		int check = 0;

		try {

			sql = "DELETE FROM COURSEPERSONNEL WHERE COURSE_ID=? AND PERSONNEL_ID=?";

			con = DAOUtils.getConnection();

			con.setAutoCommit(false);
			stat = con.prepareStatement(sql);

			stat.setInt(1, c.getCourseID());
			stat.setInt(2, p.getPersonnelID());
			stat.executeUpdate();
			con.commit();
		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("CourseDB.deleteCoursePersonnel(): " + e);

			throw new GradeException("Could not delete CoursePersonnel to DB: " + e);
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

	public static boolean deleteCoursePersonnel(Grade g, Personnel p) throws GradeException {

		Connection con = null;
		PreparedStatement stat = null;
		String sql;
		int check = 0;

		try {

			sql = "DELETE FROM COURSEPERSONNEL WHERE PERSONNEL_ID=? AND COURSE_ID IN "
					+ "(SELECT COURSE_ID FROM COURSE WHERE GRADE_ID=?)";

			con = DAOUtils.getConnection();

			con.setAutoCommit(false);
			stat = con.prepareStatement(sql);

			stat.setInt(1, p.getPersonnelID());
			stat.setInt(2, g.getGradeID());
			stat.executeUpdate();
			con.commit();
		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("CourseDB.deleteCoursePersonnel(): " + e);

			throw new GradeException("Could not delete CoursePersonnel to DB: " + e);
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