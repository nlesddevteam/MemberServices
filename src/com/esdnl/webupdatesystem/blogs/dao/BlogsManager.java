package com.esdnl.webupdatesystem.blogs.dao;

import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.webupdatesystem.blogs.bean.BlogsBean;
import com.esdnl.webupdatesystem.blogs.bean.BlogsException;
import com.esdnl.webupdatesystem.blogs.constants.BlogStatus;

public class BlogsManager {
	public static int addBlog(BlogsBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.add_new_blog(?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setString(2, ebean.getBlogTitle());
			stat.setTimestamp(3, new Timestamp(ebean.getBlogDate().getTime()));
			if (ebean.getBlogContent() != null) {
				Clob clobdesc = con.createClob();
				clobdesc.setString(1, ebean.getBlogContent());
				((OracleCallableStatement) stat).setClob(4, clobdesc);
				
			}
			else
			{
				stat.setNull(4, OracleTypes.CLOB);
			}
			stat.setString(5, ebean.getBlogPhoto());
			stat.setString(6, ebean.getBlogDocument());
			stat.setInt(7, ebean.getBlogStatus().getValue());
			stat.setString(8, ebean.getAddedBy());
			stat.setString(9, ebean.getBlogPhotoCaption());
			
			stat.execute();
			id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("addBlog(BlogsBean ebean) " + e);
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
	public static Vector<BlogsBean> getBlogs() throws BlogsException {
		Vector<BlogsBean> mms = null;
		BlogsBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			mms = new Vector<BlogsBean>(5);
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_blogs; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createBlogsBean(rs);
				mms.add(mm);
			}
		}
		catch (SQLException e) {
			System.err.println("static Vector getBlogs() throws BlogsException " + e);
			throw new BlogsException("Can not extract Blogs from DB: " + e);
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
	public static BlogsBean getBlogById(int id) throws BlogsException {
		BlogsBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_blog_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createBlogsBean(rs);
			}
		}
		catch (SQLException e) {
			System.err.println("static BlogsBean getBlogById(int id) throws BlogsException " + e);
			throw new BlogsException("Can not extract Blog from DB: " + e);
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
	public static void updateBlog(BlogsBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.web_update_system_pkg.update_blog(?,?,?,?,?,?,?,?,?); end;");
			stat.setString(1, ebean.getBlogTitle());
			stat.setTimestamp(2, new Timestamp(ebean.getBlogDate().getTime()));
			if (ebean.getBlogContent() != null) {
				Clob clobdesc = con.createClob();
				clobdesc.setString(1, ebean.getBlogContent());
				((OracleCallableStatement) stat).setClob(3, clobdesc);
				
			}
			else
			{
				stat.setNull(3, OracleTypes.CLOB);
			}
			stat.setString(4, ebean.getBlogPhoto());
			stat.setString(5, ebean.getBlogDocument());
			stat.setInt(6, ebean.getBlogStatus().getValue());
			stat.setString(7, ebean.getAddedBy());
			stat.setInt(8, ebean.getId());
			stat.setString(9, ebean.getBlogPhotoCaption());
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static void updateBlog(BlogsBean ebean) " + e);
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
	public static void deleteBlog(Integer tid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.web_update_system_pkg.delete_blog(?); end;");
			stat.setInt(1, tid);
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static void deleteBlog(Integer tid) " + e);
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
	public static BlogsBean createBlogsBean(ResultSet rs) {
		BlogsBean abean = null;
		try {
			abean = new BlogsBean();
			abean.setId(rs.getInt("ID"));
			abean.setBlogTitle(rs.getString("BLOG_TITLE"));
			abean.setBlogDate(new java.util.Date(rs.getTimestamp("BLOG_DATE").getTime()));
		    Clob clob = rs.getClob("BLOG_CONTENT");
		    abean.setBlogContent(clob.getSubString(1, (int) clob.length()));
			abean.setBlogPhoto(rs.getString("BLOG_PHOTO"));
			abean.setBlogDocument(rs.getString("BLOG_DOCUMENT"));
			abean.setBlogStatus(BlogStatus.get(rs.getInt("BLOG_STATUS")));
			abean.setAddedBy(rs.getString("ADDED_BY"));
			abean.setDateAdded(new java.util.Date(rs.getTimestamp("DATE_ADDED").getTime()));
			abean.setOtherBlogFiles(BlogFileManager.getBlogsFiles(abean.getId()));
			abean.setBlogPhotoCaption(rs.getString("BLOG_PHOTO_CAPTION"));
		}
		catch (SQLException e) {
			abean = null;
		} catch (BlogsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return abean;
	}	
}
