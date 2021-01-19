package com.esdnl.webupdatesystem.schoolreviews.dao;

import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.esdnl.dao.DAOUtils;
import com.esdnl.webupdatesystem.schoolreviews.bean.SchoolReviewSectionOptionBean;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class SchoolReviewSectionOptionManager {
	public static int addSchoolReviewSectionOption(SchoolReviewSectionOptionBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.add_new_review_option(?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setString(2, ebean.getSectionOptionTitle());
			if (ebean.getSectionOptionEmbed() != null) {
				Clob clobdesc = con.createClob();
				clobdesc.setString(1, ebean.getSectionOptionEmbed());
				((OracleCallableStatement) stat).setClob(3, clobdesc);
			}
			else
			{
				stat.setNull(3, OracleTypes.CLOB);
			}
			stat.setString(4, ebean.getSectionOptionLink());

			stat.setInt(5, ebean.getSectionOptionSectionId());
			stat.setString(6, ebean.getSectionOptionAddedBy());
			stat.setString(7, ebean.getSectionOptionType());
			stat.execute();
			id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("int addSchoolReviewSectionOption(SchoolReviewSectionOptionBean ebean) " + e);
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
	public static ArrayList<SchoolReviewSectionOptionBean> getSchoolReviewSectionOptions(int sectionid,String sectiontype) {
		ArrayList<SchoolReviewSectionOptionBean> mms = null;
		SchoolReviewSectionOptionBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			mms = new ArrayList<SchoolReviewSectionOptionBean>();
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_section_options(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,sectionid);
			stat.setString(3, sectiontype);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createSchoolReviewSectionOptionBean(rs);
				mms.add(mm);
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<SchoolReviewSectionOptionBean> getSchoolReviewSectionOptions(int sectionid,String sectiontype) " + e);
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
	public static void deleteSectionOption(int optionid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.WEB_UPDATE_SYSTEM_PKG.delete_section_option(?); end;");
			stat.setInt(1, optionid);
			stat.execute();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void deleteSectionOption(int optionid): "
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
	public static SchoolReviewSectionOptionBean getSchoolReviewSectionOptionById(int sectionid) {
		SchoolReviewSectionOptionBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_section_option_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,sectionid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createSchoolReviewSectionOptionBean(rs);
			}
		}
		catch (SQLException e) {
			System.err.println("SchoolReviewSectionOptionBean getSchoolReviewSectionOptionById(int sectionid) " + e);
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
	public static void updateSchoolReviewSectionOption(SchoolReviewSectionOptionBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin  awsd_user.web_update_system_pkg.update_review_option(?,?,?,?); end;");
			stat.setString(1, ebean.getSectionOptionTitle());
			if (ebean.getSectionOptionEmbed() != null) {
				Clob clobdesc = con.createClob();
				clobdesc.setString(1, ebean.getSectionOptionEmbed());
				((OracleCallableStatement) stat).setClob(2, clobdesc);
			}
			else
			{
				stat.setNull(2, OracleTypes.CLOB);
			}
			stat.setString(3, ebean.getSectionOptionLink());

			stat.setInt(4, ebean.getSectionOptionId());
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void updateSchoolReviewSectionOption(SchoolReviewSectionOptionBean ebean)" + e);
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
	public static SchoolReviewSectionOptionBean createSchoolReviewSectionOptionBean(ResultSet rs) {
		SchoolReviewSectionOptionBean abean = null;
		try {
			abean = new SchoolReviewSectionOptionBean();
			abean.setSectionOptionId(rs.getInt("SO_ID"));
			abean.setSectionOptionTitle(rs.getString("SO_TITLE"));
			Clob clob = rs.getClob("SO_EMBED");
			abean.setSectionOptionEmbed(clob.getSubString(1, (int) clob.length()) == null?"":clob.getSubString(1, (int) clob.length()));
			abean.setSectionOptionLink(rs.getString("SO_LINK") == null?"":rs.getString("SO_LINK"));
			abean.setSectionOptionSectionId(rs.getInt("SO_SECTION_ID"));
			abean.setSectionOptionAddedBy(rs.getString("SO_ADDED_BY"));
			abean.setSectionOptionDateAdded(new java.util.Date(rs.getTimestamp("SO_DATE_ADDED").getTime()));
			abean.setSectionOptionType(rs.getString("SO_TYPE"));
		}
		catch (SQLException e) {
			abean = null;
		} 
		return abean;
	}
}
