package com.awsd.survey;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelException;
import com.esdnl.dao.DAOUtils;

public class SurveyDB {

	public static Vector getUntakenSurveys(Personnel p) throws SurveyException, PersonnelException {

		Vector surveys = null;
		Survey s = null;
		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		StringBuffer sql = null;

		try {
			surveys = new Vector(10);
			sql = new StringBuffer();

			sql.append("SELECT * FROM SCHOOLSURVEY ");
			sql.append("WHERE ((SCHOOL_ID=?) AND (TRUNC(SYSDATE) <= END_DATE) AND (SURVEY_ID NOT IN(SELECT SURVEY_ID ");
			sql.append("FROM SURVEYPERSONNEL WHERE PERSONNEL_ID=?)))");

			con = DAOUtils.getConnection();
			stat = con.prepareStatement(sql.toString());

			stat.setInt(1, p.getSchool().getSchoolID());
			stat.setInt(2, p.getPersonnelID());

			rs = stat.executeQuery();

			while (rs.next()) {
				s = new Survey(rs.getInt("SURVEY_ID"), rs.getInt("SCHOOL_ID"), rs.getString("SURVEY_NAME"), rs.getString("SURVEY_URL"), rs.getString("LOGO_IMAGE"));

				surveys.add(s);
			}
			rs.close();
			stat.close();
			con.close();
		}
		catch (SQLException e) {
			System.err.println("SurveyDB.getUntakenSurveys(): " + e);
			throw new SurveyException("Can not extract surveys from DB: " + e);
		}
		finally {
			try {
				rs.close();
				stat.close();
				con.close();
			}
			catch (Exception e) {}
		}
		return surveys;
	}

	public static boolean surveyTaken(Survey s, Personnel p) throws SurveyException, PersonnelException {

		Connection con = null;
		PreparedStatement stat = null;
		StringBuffer sql = null;
		int check = 0;

		try {
			sql = new StringBuffer();

			sql.append("INSERT INTO SURVEYPERSONNEL VALUES(?, ?)");

			con = DAOUtils.getConnection();
			stat = con.prepareStatement(sql.toString());

			stat.setInt(1, s.getSurveyID());
			stat.setInt(2, p.getPersonnelID());

			check = stat.executeUpdate();
		}
		catch (SQLException e) {
			System.err.println("SurveyDB.getUntakenSurveys(): " + e);
			throw new SurveyException("Can not extract surveys from DB: " + e);
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