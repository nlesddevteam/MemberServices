package com.awsd.personnel.profile;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.personnel.Personnel;
import com.esdnl.dao.DAOUtils;

public class ProfileDB {

	public static Profile addProfile(Profile profile) throws ProfileException {

		Connection con = null;
		CallableStatement stat = null;
		Profile p = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_profile_pkg.add_profile(?, ?, ?, ?, ?, ?, ?, ?, ?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, profile.getPersonnel().getPersonnelID());
			stat.setString(3, profile.getStreetAddress());
			stat.setString(4, profile.getCommunity());
			stat.setString(5, profile.getProvince());
			stat.setString(6, profile.getPostalCode());
			stat.setString(7, profile.getPhoneNumber());
			stat.setString(8, profile.getFaxNumber());
			stat.setString(9, profile.getCellPhoneNumber());
			stat.setString(10, profile.getGender());
			stat.setString(11, profile.getSIN());
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);
			if (rs.next()) {
				p = new Profile(profile.getPersonnel(), rs.getString("CUR_STR_ADDR"), rs.getString("CUR_COMMUNITY"), rs.getString("CUR_PROVINCE"), rs.getString("CUR_POSTALCODE"), rs.getString("PHONE_1"), rs.getString("FAX_1"), rs.getString("CELL_1"), rs.getString("GENDER"), rs.getString("SIN"));
			}
			else {
				p = null;
			}
		}
		catch (SQLException e) {
			p = null;
			System.err.println("ProfileDB.addProfile(): " + e);
			throw new ProfileException("Can not add personnel profile to DB: " + e);
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
		return p;
	}

	public static Profile updateProfile(Profile profile) throws ProfileException {

		Connection con = null;
		CallableStatement stat = null;
		Profile p = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_profile_pkg.update_profile(?, ?, ?, ?, ?, ?, ?, ?, ?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, profile.getPersonnel().getPersonnelID());
			stat.setString(3, profile.getStreetAddress());
			stat.setString(4, profile.getCommunity());
			stat.setString(5, profile.getProvince());
			stat.setString(6, profile.getPostalCode());
			stat.setString(7, profile.getPhoneNumber());
			stat.setString(8, profile.getFaxNumber());
			stat.setString(9, profile.getCellPhoneNumber());
			stat.setString(10, profile.getGender());
			stat.setString(11, profile.getSIN());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				p = new Profile(profile.getPersonnel(), rs.getString("CUR_STR_ADDR"), rs.getString("CUR_COMMUNITY"), rs.getString("CUR_PROVINCE"), rs.getString("CUR_POSTALCODE"), rs.getString("PHONE_1"), rs.getString("FAX_1"), rs.getString("CELL_1"), rs.getString("GENDER"), rs.getString("SIN"));
			}
			else {
				p = null;
			}
		}
		catch (SQLException e) {
			p = null;
			System.err.println("ProfileDB.updateProfile(): " + e);
			throw new ProfileException("Can not update personnel profile to DB: " + e);
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
		return p;
	}

	public static Profile getProfile(Personnel p) throws ProfileException {

		Profile profile = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_profile_pkg.get_profile(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, p.getPersonnelID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				profile = new Profile(p, rs.getString("CUR_STR_ADDR"), rs.getString("CUR_COMMUNITY"), rs.getString("CUR_PROVINCE"), rs.getString("CUR_POSTALCODE"), rs.getString("PHONE_1"), rs.getString("FAX_1"), rs.getString("CELL_1"), rs.getString("GENDER"), rs.getString("SIN"));
			}
			else {
				profile = null;
			}
		}
		catch (SQLException e) {
			System.err.println("ProfileDB.getProfile(int): " + e);
			throw new ProfileException("Can not extract profile from DB: " + e);
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
		return profile;
	}
}