package com.esdnl.webupdatesystem.blogs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.webupdatesystem.blogs.bean.BlogFileBean;
import com.esdnl.webupdatesystem.blogs.bean.BlogsException;

public class BlogFileManager {
	public static int addBlogFile(BlogFileBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.add_new_blogs_file(?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setString(2, ebean.getBfTitle());
			stat.setString(3, ebean.getBfDoc());
			stat.setString(4, ebean.getAddedBy());
			stat.setInt(5, ebean.getBlogId());
			stat.execute();
			id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("int addBlogFile(BlogFileBean ebean)" + e);
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
	public static ArrayList<BlogFileBean> getBlogsFiles(Integer blogid) throws BlogsException {
		ArrayList<BlogFileBean> mms = null;
		BlogFileBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			mms = new ArrayList<BlogFileBean>();
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_blogs_files(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, blogid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createBlogFileBean(rs);
				mms.add(mm);
			}
		}
		catch (SQLException e) {
			System.err.println("Vector getBlogsFiles() throws BlogsException " + e);
			throw new BlogsException("Can not extract Blogs Files from DB: " + e);
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
	public static void deleteBlogFile(Integer tid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.web_update_system_pkg.delete_other_blog_file(?); end;");
			stat.setInt(1, tid);
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("deleteBlogFile(Integer tid) " + e);
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
	public static BlogFileBean createBlogFileBean(ResultSet rs) {
		BlogFileBean abean = null;
		try {
			abean = new BlogFileBean();
			abean.setId(rs.getInt("ID"));
			abean.setBfTitle(rs.getString("BF_TITLE"));
			abean.setBfDoc(rs.getString("BF_DOC"));
			abean.setAddedBy(rs.getString("ADDED_BY"));
			abean.setDateAdded(new java.util.Date(rs.getTimestamp("DATE_ADDED").getTime()));
			abean.setBlogId(rs.getInt("BLOG_ID"));
		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}
}
