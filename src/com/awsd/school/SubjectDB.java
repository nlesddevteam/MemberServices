package com.awsd.school;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Vector;

import com.awsd.personnel.Personnel;
import com.esdnl.dao.DAOUtils;

public class SubjectDB {

	private static HashMap<Integer, Subject> subjectMap = null;

	// optomize subject lookup
	static {
		subjectMap = new HashMap<Integer, Subject>();
		try {
			for (Subject tmp : getSubjects()) {
				subjectMap.put(tmp.getSubjectID(), tmp);
			}
		}
		catch (SubjectException e) {
			e.printStackTrace(System.err);
		}
	}

	public static Vector<Subject> getSubjects() throws SubjectException {

		Vector<Subject> subjects = null;
		Subject s = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			subjects = new Vector<Subject>(10);

			sql = "SELECT SUBJECT_ID, SUBJECT_NAME " + "FROM SUBJECT ORDER BY SUBJECT_NAME";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				s = new Subject(rs.getInt("SUBJECT_ID"), rs.getString("SUBJECT_NAME"));

				subjects.add(s);
			}

		}
		catch (SQLException e) {
			System.err.println("SubjectDB.getSubjects(): " + e);
			throw new SubjectException("Can not extract subjects from DB: " + e);
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
		return subjects;
	}

	public static Vector<Subject> getSubjects(Personnel p) throws SubjectException {

		Vector<Subject> subjects = null;
		Subject s = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			subjects = new Vector<Subject>(10);

			sql = "SELECT SUBJECT.SUBJECT_ID, SUBJECT_NAME " + "FROM SUBJECT, SUBJECTPERSONNEL, PERSONNEL WHERE "
					+ "SUBJECT.SUBJECT_ID=SUBJECTPERSONNEL.SUBJECT_ID "
					+ "AND SUBJECTPERSONNEL.PERSONNEL_ID = PERSONNEL.PERSONNEL_ID " + "AND PERSONNEL.PERSONNEL_ID="
					+ p.getPersonnelID() + " ORDER BY SUBJECT_NAME";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				s = new Subject(rs.getInt("SUBJECT_ID"), rs.getString("SUBJECT_NAME"));

				subjects.add(s);
			}
		}
		catch (SQLException e) {
			System.err.println("SubjectDB.getSubjects(): " + e);
			throw new SubjectException("Can not extract subjects from DB: " + e);
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
		return subjects;
	}

	public static Subject getSubject(int id) throws SubjectException {

		Subject s = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			if (subjectMap.containsKey(id)) {
				s = subjectMap.get(id);
			}
			else {
				sql = "SELECT SUBJECT_ID, SUBJECT_NAME FROM SUBJECT " + "WHERE SUBJECT_ID=" + id;

				con = DAOUtils.getConnection();
				stat = con.createStatement();
				rs = stat.executeQuery(sql);

				if (rs.next()) {
					s = new Subject(rs.getInt("SUBJECT_ID"), rs.getString("SUBJECT_NAME"));

					subjectMap.put(s.getSubjectID(), s);
				}
				else {
					throw new SubjectException("No Subject matching ID: " + id);
				}
			}
		}
		catch (SQLException e) {
			System.err.println("SubjectDB.getSubject(): " + e);
			throw new SubjectException("Can not extract subject from DB: " + e);
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

	public static Subject createSubjectBean(ResultSet rs) {

		Subject aBean = null;

		try {
			aBean = new Subject(rs.getInt("SUBJECT_ID"), rs.getString("SUBJECT_NAME"));
		}
		catch (SQLException e) {
			aBean = null;
		}

		return aBean;
	}
}