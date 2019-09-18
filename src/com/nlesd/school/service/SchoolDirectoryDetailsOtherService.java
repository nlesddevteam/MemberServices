package com.nlesd.school.service;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.awsd.school.SchoolException;
import com.esdnl.dao.DAOUtils;
import com.nlesd.school.bean.SchoolDirectoryDetailsOtherBean;
public class SchoolDirectoryDetailsOtherService {
	public static Integer addSchoolDirectoryDetailsOtherBean(SchoolDirectoryDetailsOtherBean s)
	throws SchoolException {

		Connection con = null;
		CallableStatement stat = null;
		int id;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.add_directory_details_o(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2, s.getGoogleMapEmbed());
			stat.setString(3, s.getSchoolCatchmentEmbed());
			stat.setString(4, s.getDescription());
			stat.setString(5, s.getInstagramLink());
			stat.setString(6, s.getSchoolEmail());
			stat.setString(7, s.getSchoolGuidanceSupport());
			stat.setString(8, s.getTwitterFeedWidgetId());
			stat.setString(9, s.getTwitterFeedScreenName());
			stat.setString(10, s.getImportantNotice());
			stat.setString(11, s.getSchoolEnrollment());
			stat.setInt(12, s.getSchoolDirectory());
			stat.setString(13, s.getAddedBy());
			stat.setString(14, s.getTwitterEmbed());
			stat.setString(15, null);
			if(s.getSurveillanceCamera()) {
				stat.setInt(16, 1);
			}else {
				stat.setInt(16, 0);
			}
			stat.execute();
		
			id = stat.getInt(1);
		
			
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
		
			System.err.println("Integer addSchoolDirectoryDetailsBean(SchoolDirectoryDetailsOtherBean s) " + e);
			throw new SchoolException("Can not add SchoolDirectoryDetailsOtherBean to DB.");
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
		
		return id;
		}
	public static void updateSchoolDirectoryDetailsOtherBean(SchoolDirectoryDetailsOtherBean s) throws SchoolException {

		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.schools_pkg.update_directory_details_o(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.setString(1, s.getGoogleMapEmbed());
			stat.setString(2, s.getSchoolCatchmentEmbed());
			stat.setString(3, s.getDescription());
			stat.setString(4, s.getInstagramLink());
			stat.setString(5, s.getSchoolEmail());
			stat.setString(6, s.getSchoolGuidanceSupport());
			stat.setString(7, s.getTwitterFeedWidgetId());
			stat.setString(8, s.getTwitterFeedScreenName());
			stat.setString(9, s.getImportantNotice());
			stat.setString(10, s.getSchoolEnrollment());
			stat.setInt(11, s.getSchoolDirectory());
			stat.setString(12, s.getAddedBy());
			stat.setInt(13, s.getId());
			stat.setString(14, s.getTwitterEmbed());
			stat.setString(15, null);
			if(s.getSurveillanceCamera()) {
				stat.setInt(16, 1);
			}else {
				stat.setInt(16, 0);
			}
			stat.execute();
		
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
		
			System.err.println("void updateSchoolDirectoryDetailsOtherBean(SchoolDirectoryDetailsOtherBean s) " + e);
			throw new SchoolException("Can not update SchoolDirectoryDetailsOtherBean to DB.");
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
		}
	public static SchoolDirectoryDetailsOtherBean getSchoolDirectoryDetailsOtherBean(Integer id) throws SchoolException {

		SchoolDirectoryDetailsOtherBean directory = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_school_directory_details_o(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				directory = createSchoolDirectoryDetailsOtherBean(rs);
			}
		}
		catch (SQLException e) {
			System.err.println("SchoolDirectoryDetailsOtherBean getSchoolDirectoryDetailsOtherBean(Integer id) " + e);
			throw new SchoolException("Can not extract SchoolDirectoryDetailsOtherBean from DB: " + e);
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
	public static SchoolDirectoryDetailsOtherBean createSchoolDirectoryDetailsOtherBean(ResultSet rs) {

		SchoolDirectoryDetailsOtherBean abean = null;

		try {
			abean = new SchoolDirectoryDetailsOtherBean();

			abean.setId(rs.getInt("ID"));
			abean.setGoogleMapEmbed(rs.getString("GOOGLE_MAP_EMBED"));
			abean.setSchoolCatchmentEmbed(rs.getString("SCHOOL_CATCHMENT_EMBED"));
			abean.setDescription(rs.getString("DESCRIPTION"));
			abean.setInstagramLink(rs.getString("INSTAGRAM_LINK"));
			abean.setSchoolEmail(rs.getString("SCHOOL_EMAIL"));
			abean.setSchoolGuidanceSupport(rs.getString("SCHOOL_GUIDANCE_SUPPORT"));
			abean.setTwitterFeedWidgetId(rs.getString("TWITTER_FEED_WIDGET_ID"));
			abean.setTwitterFeedScreenName(rs.getString("TWITTER_FEED_SCREEN_NAME"));
			abean.setImportantNotice(rs.getString("IMPORTANT_NOTICE"));
			abean.setSchoolEnrollment(rs.getString("SCHOOL_ENROLLMENT"));
			abean.setSchoolDirectory(rs.getInt("FK_SCHOOL_DIRECTORY"));
			abean.setAddedBy(rs.getString("ADDED_BY"));
			abean.setDateAdded(new java.util.Date(rs.getTimestamp("DATE_ADDED").getTime()));
			abean.setTwitterEmbed(rs.getString("TWITTER_EMBED"));
			if(rs.getInt("SURVEILLANCE_CAMERA") == 1) {
				abean.setSurveillanceCamera(true);
			}else {
				abean.setSurveillanceCamera(false);
			}
		}
		catch (Exception e) {
			abean = null;
			System.err.println("SchoolDirectoryDetailsOtherBean createSchoolDirectoryDetailsOtherBean(ResultSet rs) " + e);
		}

		return abean;
	}
}
