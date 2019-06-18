package com.awsd.efile.edocument;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;

import com.awsd.efile.EFileException;
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

public class DocumentDB {

	public static int addDocument(Document doc) throws DocumentException {

		Connection con = null;
		PreparedStatement stat = null;
		Statement stat2 = null;
		ResultSet rs = null;
		StringBuffer sql = null;
		;
		int check = 0, id = -1;
		Course c = null;

		try {
			sql = new StringBuffer("INSERT INTO DOCUMENT(DOC_ID, DOCTYPE_ID, "
					+ "SUBJECT_ID, GRADE_ID, PERSONNEL_ID, DOC_UPLOAD_DATE");

			c = doc.getCourse();
			if (c != null) {
				sql.append(",COURSE_ID");
			}

			sql.append(") VALUES(DOC_SEQ.nextval, ?, ?, ?, ?, ?");

			if (c != null) {
				sql.append(",?");
			}
			sql.append(")");

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareStatement(sql.toString());
			stat.clearParameters();
			stat.setInt(1, doc.getDocumentType().getDocumentTypeID());
			stat.setInt(2, doc.getSubject().getSubjectID());
			stat.setInt(3, doc.getGrade().getGradeID());
			stat.setInt(4, doc.getPersonnel().getPersonnelID());
			stat.setString(5, (new SimpleDateFormat("dd-MMM-yyyy")).format(Calendar.getInstance().getTime()));

			if (c != null) {
				stat.setInt(6, c.getCourseID());
			}

			check = stat.executeUpdate();
			con.commit();
			stat.close();

			if (check == 1) {
				stat2 = con.createStatement();
				rs = stat2.executeQuery("SELECT DOC_SEQ.CURRVAL pid FROM DUAL");
				if (rs.next()) {
					id = rs.getInt("pid");
				}
				else {
					id = -1;
				}

				doc.setDocumentID(id);

				DocumentKeywordsDB.addDocumentKeywords(doc);

				rs.close();
				stat2.close();
			}

			con.close();
		}
		catch (EFileException e) {
			System.err.println("DocumentDB.addDocument(): " + e);
			throw new DocumentException("Could not add document to DB: " + e);
		}
		catch (DocumentKeywordException e) {
			System.err.println("DocumentDB.addDocument(): " + e);
			throw new DocumentException("Could not add document to DB: " + e);
		}
		catch (SQLException e) {
			System.err.println("DocumentDB.addDocument(): " + e);
			throw new DocumentException("Could not add document to DB: " + e);
		}
		finally {
			try {
				stat.close();
				con.close();
			}
			catch (Exception e) {}
		}
		return (id);
	}

	public static SearchResults search(User usr, String days, String keywords[], String types[], String grades[],
																			String subjects[], String courses[], int num_docs_per_page)
			throws DocumentException {

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
		DocumentTypes all_types = null;
		int cnt = 0;
		Calendar searchDate = null;
		SimpleDateFormat sdf = null;

		boolean isUnrestricted = false;
		boolean isLevelI = false, isLevelII = false, isLevelIII = false;

		try {
			pages = new ArrayList(10);

			isUnrestricted = usr.getUserPermissions().containsKey("EFILE-UNRESTRICTED-SEARCH");

			sql = new StringBuffer("SELECT DISTINCT DOC_ID, DOC_DESC, DOCTYPE_ID, SUBJECT_ID, GRADE_ID, PERSONNEL_ID, DOC_UPLOAD_DATE, DOC_LAST_VIEWED, nvl(COURSE_ID, -1) COURSE_ID FROM DOCUMENT WHERE ");

			if (days != null) {
				sdf = new SimpleDateFormat("dd-MMM-yy");
				searchDate = Calendar.getInstance();

				sql.append("DOCUMENT.DOC_ID IN (SELECT DISTINCT DOC_ID FROM DOCUMENT WHERE ");

				if (days.equals("0")) {
					sql.append("TRUNC(DOC_UPLOAD_DATE) = '" + sdf.format(searchDate.getTime()) + "'");
				}
				else if (days.equals("1")) {
					searchDate.add(Calendar.DATE, -1);
					sql.append("TRUNC(DOC_UPLOAD_DATE) >= '" + sdf.format(searchDate.getTime()) + "'");
				}
				else if (days.equals("7")) {
					searchDate.add(Calendar.DATE, -7);
					sql.append("TRUNC(DOC_UPLOAD_DATE) >= '" + sdf.format(searchDate.getTime()) + "'");
				}
				else if (days.equals("31")) {
					searchDate.add(Calendar.MONTH, -1);
					sql.append("TRUNC(DOC_UPLOAD_DATE) >= '" + sdf.format(searchDate.getTime()) + "'");
				}
				else if (days.equals("365")) {
					searchDate.add(Calendar.YEAR, -1);
					sql.append("TRUNC(DOC_UPLOAD_DATE) >= '" + sdf.format(searchDate.getTime()) + "'");
				}

				sql.append(") AND ");
			}

			if (keywords.length > 0) {
				sql.append("DOCUMENT.DOC_ID IN (SELECT DISTINCT DOCKEYWORDS.DOC_ID FROM DOCKEYWORDS WHERE ");
				for (int i = 0; i < keywords.length; i++) {
					sql.append("DOCKEYWORD_WORD LIKE '%" + keywords[i].toUpperCase() + "%'");

					if (i < keywords.length - 1) {
						sql.append(" OR ");
					}
				}
				sql.append(") AND ");
			}

			if (types != null) {
				sql.append("DOCUMENT.DOC_ID IN (SELECT DISTINCT DOC_ID FROM DOCUMENT WHERE ");
				for (int i = 0; i < types.length; i++) {
					sql.append("DOCTYPE_ID = " + types[i]);

					if (i < types.length - 1) {
						sql.append(" OR ");
					}
				}
				sql.append(") AND ");
			}

			sql.append("DOCUMENT.DOC_ID IN (SELECT DISTINCT DOC_ID FROM DOCUMENT WHERE ");

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
				sql.append("AND DOCUMENT.DOC_ID IN (SELECT DISTINCT DOCUMENT.DOC_ID FROM DOCUMENT WHERE ");

				for (int i = 0; i < subjects.length; i++) {
					sql.append("DOCUMENT.SUBJECT_ID = " + subjects[i]);

					if (i < subjects.length - 1) {
						sql.append(" OR ");
					}
				}

				sql.append(")");
			}

			if (courses != null) {
				sql.append("AND DOCUMENT.DOC_ID IN (SELECT DISTINCT DOCUMENT.DOC_ID FROM DOCUMENT WHERE ");

				for (int i = 0; i < courses.length; i++) {
					sql.append("DOCUMENT.COURSE_ID = " + courses[i]);

					if (i < courses.length - 1) {
						sql.append(" OR ");
					}
				}

				sql.append(")");
			}
			else if ((isLevelI || isLevelII || isLevelIII) && !isUnrestricted) {
				sql.append("AND DOCUMENT.DOC_ID IN (SELECT DISTINCT DOCUMENT.DOC_ID FROM DOCUMENT WHERE ");

				usr_courses = new Courses(usr);

				iter = usr_courses.iterator();
				while (iter.hasNext()) {
					sql.append("DOCUMENT.COURSE_ID = " + ((Course) iter.next()).getCourseID());

					if (iter.hasNext()) {
						sql.append(" OR ");
					}
				}
				sql.append(")");
			}

			sql.append(" ORDER BY DOCTYPE_ID, GRADE_ID, SUBJECT_ID");

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
				page.add(new Document(rs.getInt("DOC_ID"), rs.getString("DOC_DESC"), rs.getInt("DOCTYPE_ID"), rs.getInt("SUBJECT_ID"), rs.getInt("GRADE_ID"), rs.getInt("COURSE_ID"), rs.getInt("PERSONNEL_ID"), rs.getDate("DOC_UPLOAD_DATE")));
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
			throw new DocumentException("Could not extract documents to DB: " + e);
		}
		/*catch(SubjectException e)
		{
		  System.err.println("DocumentDB.search(): " + e);
		  throw new DocumentException("Could not extract documents to DB: " + e);
		}*/
		catch (CourseException e) {
			System.err.println("DocumentDB.search(): " + e);
			throw new DocumentException("Could not extract documents to DB: " + e);
		}
		catch (SecurityException e) {
			System.err.println("DocumentDB.search(): " + e);
			throw new DocumentException("Could not extract documents to DB (NO PERMISSIONS FOUND): " + e);
		}
		catch (SQLException e) {
			System.err.println("DocumentDB.search(): " + e);
			throw new DocumentException("Could not extract documents to DB: " + e);
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

	public static boolean deleteDocument(Document doc) throws DocumentException {

		Connection con = null;
		PreparedStatement stat = null;
		StringBuffer sql = null;
		int check = 0;
		final int id = doc.getDocumentID();

		try {
			sql = new StringBuffer("DELETE FROM DOCUMENT WHERE DOC_ID=?");

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
			System.err.println("DocumentDB.deleteDocument(): " + e);
			throw new DocumentException("Could not delete document from DB: " + e);
		}
		finally {
			try {
				stat.close();
				con.close();
			}
			catch (Exception e) {}
		}
		return (check == 1);
	}

	public static Document getDocument(int id) throws DocumentException {

		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		StringBuffer sql = null;
		Document doc = null;

		try {
			sql = new StringBuffer("SELECT DOC_ID, DOC_DESC, DOCTYPE_ID, SUBJECT_ID, GRADE_ID, PERSONNEL_ID, DOC_UPLOAD_DATE, DOC_LAST_VIEWED, nvl(COURSE_ID, -1) COURSE_ID FROM DOCUMENT WHERE DOC_ID=?");

			con = DAOUtils.getConnection();

			con.setAutoCommit(true);

			stat = con.prepareStatement(sql.toString());
			stat.clearParameters();
			stat.setInt(1, id);

			rs = stat.executeQuery();

			if (rs.next()) {
				doc = new Document(rs.getInt("DOC_ID"), rs.getString("DOC_DESC"), rs.getInt("DOCTYPE_ID"), rs.getInt("SUBJECT_ID"), rs.getInt("GRADE_ID"), rs.getInt("COURSE_ID"), rs.getInt("PERSONNEL_ID"), rs.getDate("DOC_UPLOAD_DATE"));
			}
			else {
				throw new DocumentException("getDocument: Document with id=" + id + " does not exist.");
			}
		}
		catch (SQLException e) {
			System.err.println("DocumentDB.addDocument(): " + e);
			throw new DocumentException("Could not add document to DB: " + e);
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
		return doc;
	}
}