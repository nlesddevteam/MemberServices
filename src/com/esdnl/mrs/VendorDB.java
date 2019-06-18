package com.esdnl.mrs;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;

public class VendorDB {

	public static Vector<Vendor> getVendors() throws RequestException {

		Vector<Vendor> vendors = null;
		Vendor vendor = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			vendors = new Vector<Vendor>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.maint_req_sys.get_all_vendors; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				vendor = new Vendor(rs.getInt("VENDOR_ID"), rs.getString("VENDOR_NAME"));

				vendors.add(vendor);
			}
		}
		catch (SQLException e) {
			System.err.println("VendorDB.getVendors(): " + e);
			throw new RequestException("Can not extract Vendors notes from DB: " + e);
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
		return vendors;
	}

	public static boolean addVendor(Vendor code) throws RequestException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.maint_req_sys.insert_vendor(?); end;");
			stat.setString(1, code.getVendorName());
			stat.execute();
			check = true;
		}
		catch (SQLException e) {
			check = false;
			System.err.println("VendorDB.addVendor(): " + e);
			throw new RequestException("Can not add Vendor notes to DB: " + e);
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

	public static boolean deleteVendor(int vendor_id) throws RequestException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.maint_req_sys.delete_vendor(?); end;");
			stat.setInt(1, vendor_id);
			stat.execute();
			check = true;
		}
		catch (SQLException e) {
			check = false;
			System.err.println("VendorDB.deleteVendor(): " + e);
			throw new RequestException("Can not delete Vendor notes to DB: " + e);
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

	public static HashMap<Integer, RequestAssignment> getRequestAssignedVendors(int req) throws RequestException {

		HashMap<Integer, RequestAssignment> vendors = null;
		Vendor vendor = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			vendors = new HashMap<Integer, RequestAssignment>(3);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.maint_req_sys.get_maint_req_vendors(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, req);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				vendor = new Vendor(rs.getInt("VENDOR_ID"), rs.getString("VENDOR_NAME"));

				vendors.put(new Integer(vendor.getVendorID()),
						new RequestAssignment(vendor, new java.util.Date(rs.getDate("DATE_ASSIGNED").getTime())));
			}
		}
		catch (SQLException e) {
			System.err.println("VendorDB.getVendors(): " + e);
			throw new RequestException("Can not extract Vendors notes from DB: " + e);
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
		return vendors;
	}
}