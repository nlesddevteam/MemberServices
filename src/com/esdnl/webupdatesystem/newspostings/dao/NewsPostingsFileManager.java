package com.esdnl.webupdatesystem.newspostings.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.webupdatesystem.newspostings.bean.NewsPostingFileBean;
import com.esdnl.webupdatesystem.newspostings.bean.NewsPostingsException;

public class NewsPostingsFileManager {
	public static int addNewsFile(NewsPostingFileBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.add_new_news_file(?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setString(2, ebean.getNfTitle());
			stat.setString(3, ebean.getNfDoc());
			stat.setString(4, ebean.getAddedBy());
			stat.setInt(5, ebean.getNewId());
			stat.execute();
			id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("int addNewsFile(NewsPostingFileBean ebean)" + e);
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
	public static ArrayList<NewsPostingFileBean> getNewsFiles(Integer newid) throws NewsPostingsException {
		ArrayList<NewsPostingFileBean> mms = null;
		NewsPostingFileBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			mms = new ArrayList<NewsPostingFileBean>();
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_news_files(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, newid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createNewsPostingFileBean(rs);
				mms.add(mm);
			}
		}
		catch (SQLException e) {
			System.err.println("Vector getNewsFiles() throws NewsPostingsException " + e);
			throw new NewsPostingsException("Can not extract News Postings Files from DB: " + e);
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
	public static void deleteNewsPostingsFile(Integer tid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.web_update_system_pkg.delete_other_news_file(?); end;");
			stat.setInt(1, tid);
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("deleteNewsPostingsFile(Integer tid) " + e);
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
	public static NewsPostingFileBean createNewsPostingFileBean(ResultSet rs) {
		NewsPostingFileBean abean = null;
		try {
			abean = new NewsPostingFileBean();
			abean.setId(rs.getInt("ID"));
			abean.setNfTitle(rs.getString("NF_TITLE"));
			abean.setNfDoc(rs.getString("NF_DOC"));
			abean.setAddedBy(rs.getString("ADDED_BY"));
			abean.setDateAdded(new java.util.Date(rs.getTimestamp("DATE_ADDED").getTime()));
			abean.setNewId(rs.getInt("NEW_ID"));
		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}
}