package com.esdnl.webupdatesystem.schoolreviews.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import com.esdnl.dao.DAOUtils;
import com.esdnl.webupdatesystem.schoolreviews.bean.SchoolReviewFileBean;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class SchoolReviewFileManager {
	public static int addSchoolReviewFile(SchoolReviewFileBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.add_new_review_file(?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setString(2, ebean.getFileTitle());
			stat.setTimestamp(3, new Timestamp(ebean.getFileDate().getTime()));
			stat.setString(4, ebean.getFilePath());
			stat.setString(5, ebean.getFileType());
			stat.setInt(6, ebean.getFileReviewId());
			stat.setString(7, ebean.getFileAddedBy());
			stat.setInt(8, ebean.getIsActive());
			stat.execute();
			id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("int addSchoolReviewFile(SchoolReviewFileBean ebean) " + e);
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
	public static ArrayList<SchoolReviewFileBean> getSchoolReviewFiles(int reviewid,String filetype) {
		ArrayList<SchoolReviewFileBean> mms = null;
		SchoolReviewFileBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			mms = new ArrayList<SchoolReviewFileBean>();
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_review_files(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,reviewid);
			stat.setString(3, filetype);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createSchoolReviewFileBean(rs);
				mms.add(mm);
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<SchoolReviewFileBean> getSchoolReviewFiles(int reviewid,String filetype) " + e);
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
		return mms;
	}
	public static void deleteFileManager(int fileid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.WEB_UPDATE_SYSTEM_PKG.delete_section_file(?); end;");
			stat.setInt(1, fileid);
			stat.execute();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void deleteFileManager(int fileid): "
					+ e);
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
	public static SchoolReviewFileBean getSchoolReviewFileById(int fileid) {
		SchoolReviewFileBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_review_file_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,fileid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createSchoolReviewFileBean(rs);
				
			}
		}
		catch (SQLException e) {
			System.err.println("SchoolReviewFileBean getSchoolReviewFileById(int fileid) " + e);
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
		return mm;
	}
	public static void updateSchoolReviewFile(SchoolReviewFileBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.web_update_system_pkg.update_review_file(?,?,?); end;");
			stat.setString(1, ebean.getFileTitle());
			stat.setTimestamp(2, new Timestamp(ebean.getFileDate().getTime()));
			stat.setInt(3, ebean.getId());
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("int updateSchoolReviewFile(SchoolReviewFileBean ebean) " + e);
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
	public static SchoolReviewFileBean createSchoolReviewFileBean(ResultSet rs) {
		SchoolReviewFileBean abean = null;
		try {
			abean = new SchoolReviewFileBean();
			abean.setId(rs.getInt("FILE_ID"));
			abean.setFileTitle(rs.getString("FILE_TITLE"));
			abean.setFileDate(new java.util.Date(rs.getTimestamp("FILE_DATE").getTime()));
			abean.setFilePath(rs.getString("FILE_PATH"));
			abean.setFileType(rs.getString("FILE_TYPE"));
			abean.setFileReviewId(rs.getInt("FILE_REVIEW_ID"));
			abean.setFileAddedBy(rs.getString("FILE_ADDED_BY"));
			abean.setFileDateAdded(new java.util.Date(rs.getTimestamp("FILE_DATE_ADDED").getTime()));
			abean.setIsActive(rs.getInt("FILE_IS_ACTIVE"));
		}
		catch (SQLException e) {
			abean = null;
		} 
		return abean;
	}
}
