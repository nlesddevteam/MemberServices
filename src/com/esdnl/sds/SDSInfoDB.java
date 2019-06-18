package com.esdnl.sds;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.personnel.Personnel;
import com.esdnl.dao.DAOUtils;

public class SDSInfoDB {

	public static boolean addSDSInfo(Personnel who, SDSInfo info) throws SDSException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = true;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.sds_sys.insert_personnel_sds_info(?, ?, ?); end;");
			stat.setInt(1, who.getPersonnelID());
			stat.setString(2, info.getVendorNumber());
			stat.setString(3, info.getAccountCode());
			stat.execute();
		}
		catch (SQLException e) {
			check = false;
			System.err.println("SDSInfoDB.addClaim(): " + e);
			throw new SDSException("Can not add SDS Info to DB: " + e);
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

	public static boolean updateSDSInfo(Personnel who, SDSInfo info) throws SDSException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = true;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.sds_sys.update_personnel_sds_info(?, ?, ?); end;");
			stat.setInt(1, who.getPersonnelID());
			stat.setString(2, info.getVendorNumber());
			stat.setString(3, info.getAccountCode());
			stat.execute();
		}
		catch (SQLException e) {
			check = false;
			System.err.println("SDSInfoDB.updateSDSInfo(): " + e);
			throw new SDSException("Can not Update SDS Info to DB: " + e);
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

	public static SDSInfo getSDSInfo(Personnel who) throws SDSException {

		SDSInfo info = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.sds_sys.get_personnel_sds_info(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, who.getPersonnelID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				info = new SDSInfo(rs.getString("VEN_NUM"), rs.getString("ACCT_CODE"));
			else
				info = null;
		}
		catch (SQLException e) {
			System.err.println("SDSInfoDB.getSDSInfo(Personnel): " + e);
			throw new SDSException("Can not extract sds info from DB: " + e);
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
		return info;
	}
}