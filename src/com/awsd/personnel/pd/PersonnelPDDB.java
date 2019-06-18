package com.awsd.personnel.pd;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.personnel.Personnel;
import com.esdnl.dao.DAOUtils;

public class PersonnelPDDB {

	public static PersonnelPD addPD(Personnel who, PersonnelPD pd) throws PDException {

		Connection con = null;
		CallableStatement stat = null;
		int cid = -1;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_pd_sys.insert_personnel_pd(?, ?, ?, ?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, who.getPersonnelID());
			stat.setString(3, pd.getTitle());
			stat.setString(4, pd.getDescription());
			stat.setDate(5, new java.sql.Date(pd.getStartDate().getTime()));
			stat.setDate(6, new java.sql.Date(pd.getFinishDate().getTime()));
			stat.execute();

			cid = ((OracleCallableStatement) stat).getInt(1);

			pd.setID(cid);
		}
		catch (SQLException e) {
			pd.setID(-1);
			System.err.println("PersonnelPDDB.addPD(): " + e);
			throw new PDException("Can not add Personnel PD to DB: " + e);
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
		return (pd);
	}

	public static PersonnelPD getPD(int pd_id) throws PDException {

		PersonnelPD pd = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_pd_sys.get_pd(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, pd_id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				pd = new PersonnelPD(rs.getInt("PD_ID"), rs.getString("PD_TITLE"), rs.getString("PD_DESCRIPTION"), new java.util.Date(rs.getDate(
						"PD_STARTDATE").getTime()), new java.util.Date(rs.getDate("PD_FINISHDATE").getTime()));
			}
			else {
				pd = null;
			}
		}
		catch (SQLException e) {
			System.err.println("PersonnelPDDB.getPD(int): " + e);
			throw new PDException("Can not personnel pd from DB: " + e);
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
		return pd;
	}

	public static boolean deletePD(int pd_id) throws PDException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.personnel_pd_sys.delete_pd(?); end;");
			stat.setInt(1, pd_id);
			stat.execute();
			check = true;
		}
		catch (SQLException e) {
			System.err.println("PersonnelPDDB.deletePD(int): " + e);
			throw new PDException("Can not delete personnel pd from DB: " + e);
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