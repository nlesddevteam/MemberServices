package com.esdnl.webupdatesystem.newspostings.dao;

import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.TreeMap;
import java.util.Vector;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.v2.database.sds.LocationManager;
import com.esdnl.personnel.v2.model.sds.bean.LocationException;
import com.esdnl.webupdatesystem.newspostings.bean.NewsPostingsBean;
import com.esdnl.webupdatesystem.newspostings.bean.NewsPostingsException;
import com.esdnl.webupdatesystem.newspostings.constants.NewsCategory;
import com.esdnl.webupdatesystem.newspostings.constants.NewsStatus;

public class NewsPostingsManager {
	public static int addNewsPostings(NewsPostingsBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.add_new_news_postings(?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setInt(2, ebean.getNewsCategory().getValue());
			if(ebean.getNewsLocation() == null)
			{
				stat.setInt(3,-1);
			}else
			{
				stat.setInt(3, Integer.parseInt(ebean.getNewsLocation().getLocationId()));
			}
			stat.setString(4, ebean.getNewsTitle());
			if (ebean.getNewsDescription() != null) {
				Clob clobdesc = con.createClob();
				clobdesc.setString(1, ebean.getNewsDescription());
				((OracleCallableStatement) stat).setClob(5, clobdesc);
				
			}
			else
			{
				stat.setNull(5, OracleTypes.CLOB);
			}
			stat.setString(6, ebean.getNewsPhoto());
			stat.setString(7, ebean.getNewsDocumentation());
			stat.setString(8, ebean.getNewsExternalLink());
			stat.setString(9,ebean.getNewsExternalLinkTitle());
			stat.setInt(10, ebean.getNewsStatus().getValue());
			stat.setTimestamp(11, new Timestamp(ebean.getNewsDate().getTime()));
			stat.setString(12, ebean.getAddedBy());
			stat.setString(13, ebean.getNewsPhotoCaption());
			stat.execute();
			id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("int addNewsPostings(NewsPostingsBean ebean) " + e);
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
	public static Vector<NewsPostingsBean> getNewsPostings() throws NewsPostingsException {
		Vector<NewsPostingsBean> mms = null;
		NewsPostingsBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			mms = new Vector<NewsPostingsBean>(5);
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_news_postings; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createNewsPostingsBean(rs);
				mms.add(mm);
			}
		}
		catch (SQLException e) {
			System.err.println("Vector getNewsPostings() " + e);
			throw new NewsPostingsException("Can not extract News Postings from DB: " + e);
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
	public static NewsPostingsBean getNewsPostingsById(int id) throws NewsPostingsException {
		NewsPostingsBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_news_postings_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createNewsPostingsBean(rs);
			}
		}
		catch (SQLException e) {
			System.err.println("NewsPostingsBean getNewsPostingsById(int id)" + e);
			throw new NewsPostingsException("Can not extract News Postings from DB: " + e);
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
	public static void updateNewsPostings(NewsPostingsBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.web_update_system_pkg.update_news_postings(?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.setInt(1, ebean.getNewsCategory().getValue());
			if(ebean.getNewsLocation() == null)
			{
				stat.setInt(2,-1);
			}else
			{
				stat.setInt(2, Integer.parseInt(ebean.getNewsLocation().getLocationId()));
			}
			stat.setString(3, ebean.getNewsTitle());
			if (ebean.getNewsDescription() != null) {
				Clob clobdesc = con.createClob();
				clobdesc.setString(1, ebean.getNewsDescription());
				((OracleCallableStatement) stat).setClob(4, clobdesc);
				
			}
			else
			{
				stat.setNull(4, OracleTypes.CLOB);
			}
			stat.setString(5, ebean.getNewsPhoto());
			stat.setString(6, ebean.getNewsDocumentation());
			stat.setString(7, ebean.getNewsExternalLink());
			stat.setString(8,ebean.getNewsExternalLinkTitle());
			stat.setInt(9, ebean.getNewsStatus().getValue());
			stat.setTimestamp(10, new Timestamp(ebean.getNewsDate().getTime()));
			stat.setString(11, ebean.getAddedBy());
			stat.setInt(12, ebean.getId());
			stat.setString(13, ebean.getNewsPhotoCaption());
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void updateNewsPostings(NewsPostingsBean ebean) " + e);
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
	public static void deleteNewsPostings(Integer tid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.web_update_system_pkg.delete_news_postings(?); end;");
			stat.setInt(1, tid);
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void deleteNewsPostings(Integer tid) " + e);
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
	public static Vector<NewsPostingsBean> getNewsPostingsByCat(int categoryid,int numberofitems,int newsstatus ) throws NewsPostingsException {
		Vector<NewsPostingsBean> mms = null;
		NewsPostingsBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			mms = new Vector<NewsPostingsBean>(5);
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_news_postings_by_c(?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, categoryid);
			stat.setInt(3, numberofitems);
			stat.setInt(4, newsstatus);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createNewsPostingsBean(rs);
				mms.add(mm);
			}
		}
		catch (SQLException e) {
			System.err.println("NewsPostingsBean getNewsPostingsById(int id)" + e);
			throw new NewsPostingsException("Can not extract News Postings from DB: " + e);
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
	public static TreeMap<Integer, Integer> getNewsPostingsSettings() {
		TreeMap<Integer, Integer> tmap = new TreeMap<Integer, Integer>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_wus_settings; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				tmap.put(rs.getInt("CATEGORY_ID"), rs.getInt("ROW_LIMIT"));
			}
		}
		catch (SQLException e) {
			System.err.println("TreeMap<Integer, Integer> getNewsPostingsSettings()" + e);
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
		return tmap;
	}
	public static NewsPostingsBean createNewsPostingsBean(ResultSet rs) {
		NewsPostingsBean abean = null;
		try {
			abean = new NewsPostingsBean();
			abean.setId(rs.getInt("ID"));
			abean.setNewsCategory(NewsCategory.get(rs.getInt("NEWS_CATEGORY")));
			if(rs.getInt("NEWS_LOCATION") ==-1)
			{
				abean.setNewsLocation(null);
			}else{
				if(rs.getInt("NEWS_LOCATION") > 0 && rs.getInt("NEWS_LOCATION") <100){
					//need to adjust for the leading zeros for numbers under 100
					StringBuilder sb = new StringBuilder();
					sb.append("00");
					sb.append(rs.getInt("NEWS_LOCATION"));
					abean.setNewsLocation(LocationManager.getLocationBeanByString(sb.toString()));
				}else{
					abean.setNewsLocation(LocationManager.getLocationBean(rs.getInt("NEWS_LOCATION")));
				}
				
			}
			
			abean.setNewsTitle(rs.getString("NEWS_TITLE"));
			Clob clob = rs.getClob("NEWS_DESCRIPTION");
		    abean.setNewsDescription(clob.getSubString(1, (int) clob.length()));
			abean.setNewsPhoto(rs.getString("NEWS_PHOTO"));
			abean.setNewsDocumentation(rs.getString("NEWS_DOCUMENTATION"));
			abean.setNewsExternalLink(rs.getString("NEWS_EXTERNAL_LINK"));
			abean.setNewsExternalLinkTitle(rs.getString("NEWS_EXTERNAL_LINK_TITLE"));
			abean.setNewsStatus(NewsStatus.get(rs.getInt("NEWS_STATUS")));
			abean.setNewsDate(new java.util.Date(rs.getTimestamp("NEWS_DATE").getTime()));
			abean.setAddedBy(rs.getString("ADDED_BY"));
			abean.setDateAdded(new java.util.Date(rs.getTimestamp("DATE_ADDED").getTime()));
			abean.setOtherNewsFiles(NewsPostingsFileManager.getNewsFiles(abean.getId()));
			abean.setNewsPhotoCaption(rs.getString("NEWS_PHOTO_CAPTION"));
		}
		catch (SQLException e) {
			abean = null;
		} catch (LocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NewsPostingsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return abean;
	}	
}
