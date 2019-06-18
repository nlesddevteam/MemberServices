package com.esdnl.mrs;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;

public class StatusCodeDB {

	public static Vector getStatusCodes() throws RequestException {

		Vector codes = null;
		StatusCode code = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			codes = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.maint_req_sys.get_all_status_codes; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				code = new StatusCode(rs.getString("CODE_ID"));

				codes.add(code);
			}
		}
		catch (SQLException e) {
			System.err.println("StatusCodeDB.getStatusCodes(): " + e);
			throw new RequestException("Can not extract StatusCodes notes from DB: " + e);
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
		return codes;
	}

	public static boolean addStatusCode(StatusCode code) throws RequestException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.maint_req_sys.insert_status_code(?); end;");
			stat.setString(1, code.getStatusCodeID());
			stat.execute();
			check = true;
		}
		catch (SQLException e) {
			check = false;
			System.err.println("StatusCodeDB.addStatusCode(): " + e);
			throw new RequestException("Can not add StatusCode notes to DB: " + e);
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
		return (check);
	}

	public static boolean deleteStatusCode(StatusCode code) throws RequestException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.maint_req_sys.delete_status_code(?); end;");
			stat.setString(1, code.getStatusCodeID());
			stat.execute();
			check = true;
		}
		catch (SQLException e) {
			check = false;
			System.err.println("StatusCodeDB.deleteStatusCode(): " + e);
			throw new RequestException("Can not delete StatusCode notes to DB: " + e);
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
		return (check);
	}
}