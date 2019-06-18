package com.esdnl.mrs;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;

public class RequestTypeDB {

	public static Vector getRequestTypes() throws RequestException {

		Vector types = null;
		RequestType type = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			types = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.maint_req_sys.get_all_request_types; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				type = new RequestType(rs.getString("TYPE_ID"));

				types.add(type);
			}
		}
		catch (SQLException e) {
			System.err.println("RequestTypeDB.getRequestTypes(): " + e);
			throw new RequestException("Can not extract requesttypes notes from DB: " + e);
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
		return types;
	}

	public static boolean addRequestType(RequestType type) throws RequestException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.maint_req_sys.insert_request_type(?); end;");
			stat.setString(1, type.getRequestTypeID());
			stat.execute();
			check = true;
		}
		catch (SQLException e) {
			check = false;
			System.err.println("RequestTypeDB.addRequestType(): " + e);
			throw new RequestException("Can not add requesttype notes to DB: " + e);
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

	public static boolean deleteRequestType(RequestType type) throws RequestException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.maint_req_sys.delete_request_type(?); end;");
			stat.setString(1, type.getRequestTypeID());
			stat.execute();
			check = true;
		}
		catch (SQLException e) {
			check = false;
			System.err.println("RequestTypeDB.deleteRequestType(): " + e);
			throw new RequestException("Can not delete requesttype notes to DB: " + e);
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