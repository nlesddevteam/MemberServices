package com.awsd.personnel;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;

public class SecretQuestionDB {

	public static SecretQuestion getSecretQuestion(Personnel p) throws PersonnelException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		SecretQuestion sq = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.ms_login.get_secret_question(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, p.getPersonnelID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				sq = new SecretQuestion(rs.getString("SECRET_QUESTION"), rs.getString("SECRET_ANSWER"));
			}
			else {
				sq = null;
			}
		}
		catch (SQLException e) {
			System.err.println("PersonnelDB.getSecretQuestion(SecretQuestion): " + e);
			throw new PersonnelException("Can not extract SecretQuestion from DB: " + e);
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
		return sq;
	}

	public static boolean setSecretQuestion(Personnel p, SecretQuestion sq) throws PersonnelException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;
		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.ms_login.insert_secret_question(?, ?, ?); end;");
			stat.setInt(1, p.getPersonnelID());
			stat.setString(2, sq.getQuestion());
			stat.setString(3, sq.getAnswer());

			stat.execute();
			check = true;
		}
		catch (SQLException e) {
			System.err.println("PersonnelDB.setSecretQuestion(Personnel, SecretQuestion): " + e);
			throw new PersonnelException("Can not add SecretQuestion from DB: " + e);
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

	public static boolean updateSecretQuestion(Personnel p, SecretQuestion sq) throws PersonnelException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;
		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.ms_login.update_secret_question(?, ?, ?); end;");
			stat.setInt(1, p.getPersonnelID());
			stat.setString(2, sq.getQuestion());
			stat.setString(3, sq.getAnswer());

			stat.execute();
			check = true;
		}
		catch (SQLException e) {
			System.err.println("PersonnelDB.updateSecretQuestion(): " + e);
			throw new PersonnelException("Can not add SecretQuestion from DB: " + e);
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

	public static boolean deleteSecretQuestion(Personnel p) throws PersonnelException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;
		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.ms_login.delete_secret_question(?); end;");

			stat.setInt(1, p.getPersonnelID());

			stat.execute();
			check = true;
		}
		catch (SQLException e) {
			System.err.println("PersonnelDB.deleteSecretQuestion(Personnel): " + e);
			throw new PersonnelException("Can not add SecretQuestion from DB: " + e);
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
}