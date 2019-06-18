package com.esdnl.webupdatesystem.banners.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.webupdatesystem.banners.bean.BannersBean;
import com.esdnl.webupdatesystem.banners.bean.BannersException;

public class BannersManager {
	public static int addBanner(BannersBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.add_new_banner(?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setString(2, ebean.getBannerFile());
			stat.setInt(3, ebean.getBannerRotation());
			stat.setString(4, ebean.getBannerLink());
			stat.setInt(5, ebean.getBannerStatus());
			stat.setInt(6, ebean.getBannerShowPublic());
			stat.setInt(7, ebean.getBannerShowStaff());
			stat.setInt(8, ebean.getBannerShowBusiness());
			stat.setString(9, ebean.getBannerCode());
			stat.setString(10, ebean.getAddedBy());
			stat.execute();
			id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("int addBanner(BannersBean ebean) " + e);
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
	public static Vector<BannersBean> getBanners() throws BannersException {
		Vector<BannersBean> mms = null;
		BannersBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			mms = new Vector<BannersBean>(5);
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_banners; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createBannersBean(rs);
				mms.add(mm);
			}
		}
		catch (SQLException e) {
			System.err.println("BannersManager.getBanners: " + e);
			throw new BannersException("Can not extract Banners from DB: " + e);
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
	public static BannersBean getBannerById(int id) throws BannersException {
		BannersBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_banner_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createBannersBean(rs);
			}
		}
		catch (SQLException e) {
			System.err.println("BannersManager.getBannerById: " + e);
			throw new BannersException("Can not extract Banner from DB: " + e);
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
	public static void updateBanner(BannersBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.web_update_system_pkg.update_banner(?,?,?,?,?,?,?,?,?,?); end;");
			stat.setString(1, ebean.getBannerFile());
			stat.setInt(2, ebean.getBannerRotation());
			stat.setString(3, ebean.getBannerLink());
			stat.setInt(4, ebean.getBannerStatus());
			stat.setInt(5, ebean.getBannerShowPublic());
			stat.setInt(6, ebean.getBannerShowStaff());
			stat.setInt(7, ebean.getBannerShowBusiness());
			stat.setString(8, ebean.getBannerCode());
			stat.setString(9, ebean.getAddedBy());
			stat.setInt(10, ebean.getId());
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void updateBanner(BannersBean ebean) " + e);
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
	public static void deleteBanner(Integer tid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.web_update_system_pkg.delete_banner(?); end;");
			stat.setInt(1, tid);
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void deleteBanner(Integer tid)" + e);
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
	public static BannersBean createBannersBean(ResultSet rs) {
		BannersBean abean = null;
		try {
			abean = new BannersBean();
			abean.setId(rs.getInt("ID"));
			abean.setBannerFile(rs.getString("BANNER_FILE"));
			abean.setBannerRotation(rs.getInt("BANNER_ROTATION"));
			abean.setBannerLink(rs.getString("BANNER_LINK"));
			abean.setBannerStatus(rs.getInt("BANNER_STATUS"));
			abean.setBannerShowPublic(rs.getInt("BANNER_SHOW_PUBLIC"));
			abean.setBannerShowStaff(rs.getInt("BANNER_SHOW_STAFF"));
			abean.setBannerShowBusiness(rs.getInt("BANNER_SHOW_BUSINESS"));
			abean.setBannerCode(rs.getString("BANNER_CODE"));
			abean.setAddedBy(rs.getString("ADDED_BY"));
			abean.setDateAdded(new java.util.Date(rs.getTimestamp("DATE_ADDED").getTime()));
		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}	
}
