package com.awsd.efile.equestion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;

import com.awsd.efile.SearchResults;
import com.awsd.school.Course;
import com.awsd.school.CourseException;
import com.awsd.school.Courses;
import com.awsd.school.Grade;
import com.awsd.school.GradeDB;
import com.awsd.school.GradeException;
import com.awsd.school.Grades;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.esdnl.dao.DAOUtils;

public class QuestionDB {

	public static int addQuestion(Question q) throws QuestionException {

		Connection con = null;
		PreparedStatement stat = null;
		Statement stat2 = null;
		ResultSet rs = null;
		StringBuffer sql = null;
		;
		int check = 0, id = -1;
		QuestionOption option = null;
		Iterator iter = null;

		try {
			sql = new StringBuffer("INSERT INTO QUESTIONSTEM(STEM_ID, TYPE_ID, "
					+ "SUBJECT_ID, GRADE_ID, COURSE_ID, PERSONNEL_ID, SUBMIT_DATE, STEM, "
					+ "UNIT_NUMBER, ANSWER, IMAGE) VALUES(QUESTIONSTEM_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			//System.err.println(sql.toString());

			con = DAOUtils.getConnection();

			con.setAutoCommit(false);

			stat = con.prepareStatement(sql.toString());
			stat.clearParameters();
			stat.setInt(1, q.getQuestionTypeID());
			stat.setInt(2, q.getSubjectID());
			stat.setInt(3, q.getGradeID());
			if (q.getCourseID() == -1) {
				stat.setNull(4, Types.INTEGER);
			}
			else {
				stat.setInt(4, q.getCourseID());
			}
			stat.setInt(5, q.getSubmittedByID());
			stat.setString(6, (new SimpleDateFormat("dd-MMM-yyyy")).format(Calendar.getInstance().getTime()));
			stat.setString(7, q.getStem());
			stat.setString(8, q.getUnitNumber());
			if ((q.getCorrectAnswer() == null) || (q.getCorrectAnswer().equals(""))) {
				stat.setNull(9, Types.VARCHAR);
			}
			else {
				stat.setString(9, q.getCorrectAnswer());
			}
			stat.setNull(10, Types.BLOB);

			check = stat.executeUpdate();
			//con.commit();
			stat.close();

			if (check == 1) {
				stat2 = con.createStatement();
				rs = stat2.executeQuery("SELECT QUESTIONSTEM_SEQ.CURRVAL pid FROM DUAL");
				if (rs.next()) {
					id = rs.getInt("pid");

					q.setQuestionID(id);
					rs.close();
					stat2.close();

					sql = new StringBuffer("INSERT INTO QUESTIONMULTIPLECHOICEOPTION(CHOICE_ID, STEM_ID, OPTION_TEXT, ISCORRECT) "
							+ "VALUES(QUESTONMULTICHOICEOPTION_SEQ.NEXTVAL, " + id + ", ?, ?)");

					//System.err.println(sql.toString());

					stat = con.prepareStatement(sql.toString());

					iter = q.getOptions().iterator();
					while (iter.hasNext()) {
						option = (QuestionOption) iter.next();

						//System.err.println("Option: " + option.getOption() + " - " + option.isCorrect());

						stat.clearParameters();
						stat.setString(1, option.getOption());
						stat.setString(2, Boolean.toString(option.isCorrect()));
						check = stat.executeUpdate();
						if (check < 1) {
							con.rollback();
							q.setQuestionID(-1);
							break;
						}
					}
					stat.close();
				}
				else {
					con.rollback();
				}
			}
			else {
				con.rollback();
			}
			con.commit();
		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("QuestionDB.addQuestion(): " + e);
			throw new QuestionException("Could not add question to DB: " + e);
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
		return (q.getQuestionID());
	}

	public static Vector getQuestionOptions(Question q) throws QuestionException {

		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql = null;
		;
		QuestionOption option = null;
		Vector options = null;

		try {
			sql = "SELECT * FROM QUESTIONMULTIPLECHOICEOPTION WHERE STEM_ID=?";

			options = new Vector();

			con = DAOUtils.getConnection();

			con.setAutoCommit(false);

			stat = con.prepareStatement(sql);
			stat.clearParameters();
			stat.setInt(1, q.getQuestionID());

			rs = stat.executeQuery();

			while (rs.next()) {
				option = new QuestionOption(rs.getInt("choice_id"), rs.getString("option_text"), rs.getString("iscorrect").trim().equalsIgnoreCase(
						"TRUE"));

				//System.err.println(rs.getString("iscorrect") + " : " + option.isCorrect());

				options.add(option);
			}

		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("QuestionDB.addQuestion(): " + e);
			throw new QuestionException("Could not add question to DB: " + e);
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
		return (options);
	}

	public static Vector getQuestionTypes() throws QuestionException {

		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql = null;
		;
		QuestionType type = null;
		Vector types = null;

		try {
			sql = "SELECT * FROM QUESTIONTYPE";

			types = new Vector();

			con = DAOUtils.getConnection();

			con.setAutoCommit(false);

			stat = con.prepareStatement(sql);

			rs = stat.executeQuery();

			while (rs.next()) {
				type = new QuestionType(rs.getInt("type_id"), rs.getString("type_name"));

				types.add(type);
			}
		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("QuestionDB.getQuestionTypes(): " + e);
			throw new QuestionException("Could not get types from DB: " + e);
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
		return (types);
	}

	public static SearchResults search(User usr, String days, String types[], String grades[], String subjects[],
																			String courses[], int num_docs_per_page) throws QuestionException {

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		ArrayList pages = null;
		Vector page = null;
		Iterator iter = null;
		StringBuffer sql = null;
		Grades usr_grades = null;
		Grade g = null;
		String gname;
		Courses usr_courses = null;
		QuestionTypes all_types = null;
		int cnt = 0;
		Calendar searchDate = null;
		SimpleDateFormat sdf = null;

		boolean isUnrestricted = false;
		boolean isLevelI = false, isLevelII = false, isLevelIII = false;

		try {
			pages = new ArrayList(10);

			isUnrestricted = usr.getUserPermissions().containsKey("EFILE-UNRESTRICTED-SEARCH");

			sql = new StringBuffer("SELECT DISTINCT STEM_ID, STEM, TYPE_ID, SUBJECT_ID, GRADE_ID, PERSONNEL_ID, SUBMIT_DATE, nvl(COURSE_ID, -1) COURSE_ID, UNIT_NUMBER, ANSWER FROM QUESTIONSTEM WHERE ");

			if (days != null) {
				sdf = new SimpleDateFormat("dd-MMM-yy");
				searchDate = Calendar.getInstance();

				sql.append("QUESTIONSTEM.STEM_ID IN (SELECT DISTINCT STEM_ID FROM QUESTIONSTEM WHERE ");

				if (days.equals("0")) {
					sql.append("TRUNC(SUBMIT_DATE) = '" + sdf.format(searchDate.getTime()) + "'");
				}
				else if (days.equals("1")) {
					searchDate.add(Calendar.DATE, -1);
					sql.append("TRUNC(SUBMIT_DATE) >= '" + sdf.format(searchDate.getTime()) + "'");
				}
				else if (days.equals("7")) {
					searchDate.add(Calendar.DATE, -7);
					sql.append("TRUNC(SUBMIT_DATE) >= '" + sdf.format(searchDate.getTime()) + "'");
				}
				else if (days.equals("31")) {
					searchDate.add(Calendar.MONTH, -1);
					sql.append("TRUNC(SUBMIT_DATE) >= '" + sdf.format(searchDate.getTime()) + "'");
				}
				else if (days.equals("365")) {
					searchDate.add(Calendar.YEAR, -1);
					sql.append("TRUNC(SUBMIT_DATE) >= '" + sdf.format(searchDate.getTime()) + "'");
				}

				sql.append(") AND ");
			}

			if (types != null) {
				sql.append("QUESTIONSTEM.STEM_ID IN (SELECT DISTINCT STEM_ID FROM QUESTIONSTEM WHERE ");
				for (int i = 0; i < types.length; i++) {
					sql.append("TYPE_ID = " + types[i]);

					if (i < types.length - 1) {
						sql.append(" OR ");
					}
				}
				sql.append(") AND ");
			}

			sql.append("QUESTIONSTEM.STEM_ID IN (SELECT DISTINCT STEM_ID FROM QUESTIONSTEM WHERE ");

			if (grades == null) {
				if (isUnrestricted) {
					usr_grades = new Grades(true);
				}
				else {
					usr_grades = new Grades(usr);
				}

				iter = usr_grades.iterator();
				while (iter.hasNext()) {
					g = (Grade) iter.next();
					sql.append("GRADE_ID = " + g.getGradeID());

					if (iter.hasNext()) {
						sql.append(" OR ");
					}

					gname = g.getGradeName();
					if (gname.equalsIgnoreCase("LEVEL I")) {
						isLevelI = true;
					}
					else if (gname.equalsIgnoreCase("LEVEL II")) {
						isLevelII = true;
					}
					else if (gname.equalsIgnoreCase("LEVEL III")) {
						isLevelIII = true;
					}
				}
				sql.append(") ");
			}
			else {
				for (int i = 0; i < grades.length; i++) {
					sql.append("GRADE_ID = " + grades[i]);

					if (i < grades.length - 1) {
						sql.append(" OR ");
					}

					g = GradeDB.getGrade(Integer.parseInt(grades[i]));
					gname = g.getGradeName();

					if (gname.equalsIgnoreCase("LEVEL I")) {
						isLevelI = true;
					}
					else if (gname.equalsIgnoreCase("LEVEL II")) {
						isLevelII = true;
					}
					else if (gname.equalsIgnoreCase("LEVEL III")) {
						isLevelIII = true;
					}
				}
				sql.append(") ");
			}

			if (subjects != null) {
				sql.append("AND QUESTIONSTEM.STEM_ID IN (SELECT DISTINCT QUESTIONSTEM.STEM_ID FROM QUESTIONSTEM WHERE ");

				for (int i = 0; i < subjects.length; i++) {
					sql.append("QUESTIONSTEM.SUBJECT_ID = " + subjects[i]);

					if (i < subjects.length - 1) {
						sql.append(" OR ");
					}
				}

				sql.append(")");
			}

			if (courses != null) {
				sql.append("AND QUESTIONSTEM.STEM_ID IN (SELECT DISTINCT QUESTIONSTEM.STEM_ID FROM QUESTIONSTEM WHERE ");

				for (int i = 0; i < courses.length; i++) {
					sql.append("QUESTIONSTEM.COURSE_ID = " + courses[i]);

					if (i < courses.length - 1) {
						sql.append(" OR ");
					}
				}

				sql.append(")");
			}
			else if ((isLevelI || isLevelII || isLevelIII) && !isUnrestricted) {
				sql.append("AND QUESTIONSTEM.STEM_ID IN (SELECT DISTINCT QUESTIONSTEM.STEM_ID FROM QUESTIONSTEM WHERE ");

				usr_courses = new Courses(usr);

				iter = usr_courses.iterator();
				while (iter.hasNext()) {
					sql.append("QUESTIONSTEM.COURSE_ID = " + ((Course) iter.next()).getCourseID());

					if (iter.hasNext()) {
						sql.append(" OR ");
					}
				}
				sql.append(")");
			}

			sql.append(" ORDER BY TYPE_ID, GRADE_ID, SUBJECT_ID");

			//System.err.println(sql.toString());

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.createStatement();
			//System.err.print("Starting Query...");
			rs = stat.executeQuery(sql.toString());

			page = new Vector(num_docs_per_page);
			while (rs.next()) {
				cnt++;
				if (page.size() == num_docs_per_page) {
					pages.add(page);
					page = new Vector(num_docs_per_page);
				}
				page.add(new Question(rs.getInt("STEM_ID"), rs.getInt("TYPE_ID"), rs.getInt("PERSONNEL_ID"), rs.getDate("SUBMIT_DATE"), rs.getString("STEM"), rs.getInt("SUBJECT_ID"), rs.getInt("GRADE_ID"), rs.getInt("COURSE_ID"), rs.getString("UNIT_NUMBER"), rs.getString("ANSWER")));
			}
			if (page.size() > 0) {
				pages.add(page);
			}

			//System.err.println("Finished (" + docs.size()+")");
			rs.close();
			stat.close();
			con.close();
		}
		catch (GradeException e) {
			System.err.println("DocumentDB.search(): " + e);
			throw new QuestionException("Could not extract documents to DB: " + e);
		}
		catch (CourseException e) {
			System.err.println("DocumentDB.search(): " + e);
			throw new QuestionException("Could not extract documents to DB: " + e);
		}
		catch (SecurityException e) {
			System.err.println("DocumentDB.search(): " + e);
			throw new QuestionException("Could not extract documents to DB (NO PERMISSIONS FOUND): " + e);
		}
		catch (SQLException e) {
			System.err.println("DocumentDB.search(): " + e);
			throw new QuestionException("Could not extract documents to DB: " + e);
		}
		finally {
			try {
				rs.close();
				stat.close();
				con.close();
			}
			catch (Exception e) {}
		}

		return (new SearchResults(pages, cnt));
	}

	public static boolean deleteQuestion(Question doc) throws QuestionException {

		Connection con = null;
		PreparedStatement stat = null;
		StringBuffer sql = null;
		int check = 0;
		final int id = doc.getQuestionID();

		try {
			sql = new StringBuffer("DELETE FROM QUESTIONSTEM WHERE STEM_ID=?");

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareStatement(sql.toString());
			stat.clearParameters();
			stat.setInt(1, id);

			check = stat.executeUpdate();
			con.commit();
			stat.close();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("QuestionDB.deleteQuestion(): " + e);
			throw new QuestionException("Could not delete question from DB: " + e);
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

	public static Question getQuestion(int id) throws QuestionException {

		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		StringBuffer sql = null;
		Question q = null;

		try {
			sql = new StringBuffer("SELECT STEM_ID, STEM, TYPE_ID, SUBJECT_ID, GRADE_ID, PERSONNEL_ID, SUBMIT_DATE, nvl(COURSE_ID, -1) COURSE_ID, UNIT_NUMBER, ANSWER FROM QUESTIONSTEM WHERE STEM_ID=?");

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareStatement(sql.toString());
			stat.clearParameters();
			stat.setInt(1, id);

			rs = stat.executeQuery();

			if (rs.next()) {
				q = new Question(rs.getInt("STEM_ID"), rs.getInt("TYPE_ID"), rs.getInt("PERSONNEL_ID"), rs.getDate("SUBMIT_DATE"), rs.getString("STEM"), rs.getInt("SUBJECT_ID"), rs.getInt("GRADE_ID"), rs.getInt("COURSE_ID"), rs.getString("UNIT_NUMBER"), rs.getString("ANSWER"));
			}
			else {
				//throw new QuestionException("getQuestion: Question with id=" + id + " does not exist.");
				q = null;
			}
		}
		catch (SQLException e) {
			System.err.println("QuestionDB.getQuestion(): " + e);
			throw new QuestionException("Could not get question to DB: " + e);
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
		return q;
	}
}