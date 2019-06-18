package com.nlesd.school.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.school.School;
import com.awsd.school.SchoolException;
import com.esdnl.dao.DAOUtils;
import com.nlesd.school.bean.SchoolDirectoryDetailsBean;

public class SchoolDirectoryDetailsService {

	public static SchoolDirectoryDetailsBean getSchoolDirectoryDetailsBean(School s) throws SchoolException {

		SchoolDirectoryDetailsBean directory = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_school_directory_details(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, s.getSchoolID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				directory = createSchoolDirectoryDetailsBean(rs);
			}
		}
		catch (SQLException e) {
			System.err.println("SchoolDirectoryBean getSchoolDirectoryDetails(School s): " + e);
			throw new SchoolException("Can not extract SchoolDirectoryBean from DB: " + e);
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

		return directory;
	}

	public static SchoolDirectoryDetailsBean addSchoolDirectoryDetailsBean(SchoolDirectoryDetailsBean s)
			throws SchoolException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.add_school_directory_details(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");

			stat.registerOutParameter(1, OracleTypes.NUMBER);

			stat.setInt(2, s.getSchoolId());
			stat.setInt(3, s.getElectorialZone());
			stat.setBoolean(4, s.isAccessible());
			stat.setBoolean(5, s.isFrenchImmersion());
			stat.setString(6, s.getBusRoutesFilename());
			stat.setString(7, s.getCatchmentAreaFilename());
			stat.setString(8, s.getSchoolReportFilename());
			stat.setString(9, s.getSchoolPhotoFilename());
			stat.setString(10, s.getSchoolCrestFilename());
			stat.setString(11, s.getYoutubeUrl());
			stat.setString(12, s.getTwitterUrl());
			stat.setString(13, s.getFacebookUrl());
			stat.setString(14, s.getSchoolStartTime());
			stat.setString(15, s.getSchoolEndTime());
			stat.setString(16, s.getSchoolOpening());
			stat.setString(17, s.getKindergartenTimes());
			stat.setString(18, s.getKinderstartTimes());
			stat.setString(19, s.getOtherInfo());
			stat.setString(20, s.getSecretaries());
			stat.setString(21, s.getGoogleMapUrl());
			stat.setString(22, s.getCatchmentMapUrl());

			stat.execute();

			int id = stat.getInt(1);

			s.setDirectoryId(id);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("int addSchoolDirectoryDetailsBean(SchoolDirectoryDetailsBean s): " + e);
			throw new SchoolException("Can not add SchoolDirectoryDetailsBean to DB.");
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

		return s;
	}

	public static boolean updateSchoolDirectoryDetailsBean(SchoolDirectoryDetailsBean s) throws SchoolException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {

			con = DAOUtils.getConnection();

			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.schools_pkg.mod_school_directory_details(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");

			stat.setInt(1, s.getDirectoryId());
			stat.setInt(2, s.getElectorialZone());
			stat.setBoolean(3, s.isAccessible());
			stat.setBoolean(4, s.isFrenchImmersion());
			stat.setString(5, s.getBusRoutesFilename());
			stat.setString(6, s.getCatchmentAreaFilename());
			stat.setString(7, s.getSchoolReportFilename());
			stat.setString(8, s.getSchoolPhotoFilename());
			stat.setString(9, s.getSchoolCrestFilename());
			stat.setString(10, s.getYoutubeUrl());
			stat.setString(11, s.getTwitterUrl());
			stat.setString(12, s.getFacebookUrl());
			stat.setString(13, s.getSchoolStartTime());
			stat.setString(14, s.getSchoolEndTime());
			stat.setString(15, s.getSchoolOpening());
			stat.setString(16, s.getKindergartenTimes());
			stat.setString(17, s.getKinderstartTimes());
			stat.setString(18, s.getOtherInfo());
			stat.setString(19, s.getSecretaries());
			stat.setString(20, s.getGoogleMapUrl());
			stat.setString(21, s.getCatchmentMapUrl());

			stat.execute();

			check = (stat.getUpdateCount() == 1);
		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("SchoolDB.updateSchool(): " + e);
			throw new SchoolException("Could not update school in DB: " + e);
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

	public static SchoolDirectoryDetailsBean createSchoolDirectoryDetailsBean(ResultSet rs) {

		SchoolDirectoryDetailsBean abean = null;

		try {
			abean = new SchoolDirectoryDetailsBean();

			abean.setDirectoryId(rs.getInt("DIRECTORY_ID"));
			abean.setSchoolId(rs.getInt("SCHOOL_ID"));
			abean.setAccessible(rs.getBoolean("ACCESSIBLE"));
			abean.setFrenchImmersion(rs.getBoolean("FRENCH_IMMERSION"));
			abean.setBusRoutesFilename(rs.getString("BUS_ROUTES_FILENAME"));
			abean.setCatchmentAreaFilename(rs.getString("CATCHMENT_AREA_FILENAME"));
			abean.setSchoolReportFilename(rs.getString("SCHOOL_REPORT_FILENAME"));
			abean.setSchoolPhotoFilename(rs.getString("SCHOOL_PHOTO_FILENAME"));
			abean.setSchoolCrestFilename(rs.getString("SCHOOL_CREST_FILENAME"));
			abean.setYoutubeUrl(rs.getString("YOUTUBE_URL"));
			abean.setTwitterUrl(rs.getString("TWITTER_URL"));
			abean.setFacebookUrl(rs.getString("FACEBOOK_URL"));
			abean.setSchoolStartTime(rs.getString("SCHOOL_START_TIME"));
			abean.setSchoolEndTime(rs.getString("SCHOOL_END_TIME"));
			abean.setSchoolOpening(rs.getString("SCHOOL_OPENING"));
			abean.setKindergartenTimes(rs.getString("KINDERGARTEN_TIMES"));
			abean.setKinderstartTimes(rs.getString("KINDERSTART_TIMES"));
			abean.setOtherInfo(rs.getString("OTHER_INFO"));
			abean.setSecretaries(rs.getString("SECRETARY"));
			abean.setGoogleMapUrl(rs.getString("GOOGLE_MAP_URL"));
			abean.setCatchmentMapUrl(rs.getString("CATCHMENT_MAP_URL"));

			//ELECTORIAL_ZONE may not be available.
			try {
				abean.setElectorialZone(rs.getInt("ELECTORIAL_ZONE"));
			}
			catch (Exception e) {}
		}
		catch (Exception e) {
			abean = null;
		}

		return abean;
	}
}
