package com.esdnl.webupdatesystem.schoolreviews.dao;

import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;
import com.esdnl.dao.DAOUtils;
import com.esdnl.webupdatesystem.schoolreviews.bean.SchoolReviewSectionBean;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class SchoolReviewSectionManager {
	public static TreeMap<Integer,SchoolReviewSectionBean> getSchoolReviewSections(int rid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<Integer,SchoolReviewSectionBean> list = new TreeMap<Integer,SchoolReviewSectionBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_review_sections(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, rid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				SchoolReviewSectionBean mm = createSchoolReviewSectionBean(rs);
				list.put(mm.getSecId(),mm);
			}
		}
		catch (SQLException e) {
			System.err.println("TreeMap<Integer,String> getSchoolReviewSchoolsById() " + e);
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
		return list;
	}
	public static int addSchoolReviewSection(SchoolReviewSectionBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.add_review_section(?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setInt(2, ebean.getSecReviewId());
			stat.setInt(3, ebean.getSecType());
			stat.setString(4, ebean.getSecTitle());
			stat.setInt(5, ebean.getSecStatus());
			if (ebean.getSecDescription() != null) {
				Clob clobdesc = con.createClob();
				clobdesc.setString(1, ebean.getSecDescription());
				((OracleCallableStatement) stat).setClob(6, clobdesc);
			}
			else
			{
				stat.setNull(6, OracleTypes.CLOB);
			}
			stat.setString(7, ebean.getSecAddedBy());
			stat.setInt(8, ebean.getSecSortId());
			stat.execute();
			id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("int addSchoolReviewSection(SchoolReviewSectionBean ebean) " + e);
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
	public static TreeMap<String,Integer> getSchoolReviewSchoolsById() {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<String,Integer> list = new TreeMap<String,Integer>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_section_types; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				list.put(rs.getString("SECTION_NAME"),rs.getInt("ID"));
			}
		}
		catch (SQLException e) {
			System.err.println("TreeMap<Integer,String> getSchoolReviewSchoolsById() " + e);
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
		return list;
	}
	public static ArrayList<SchoolReviewSectionBean> getSchoolReviewSectionsList(int rid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<SchoolReviewSectionBean> list = new ArrayList<SchoolReviewSectionBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_review_sections(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, rid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				SchoolReviewSectionBean mm = createSchoolReviewSectionBean(rs);
				list.add(mm);
			}
		}
		catch (SQLException e) {
			System.err.println("TreeMap<Integer,String> getSchoolReviewSchoolsById() " + e);
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
		return list;
	}
	public static void deleteSchoolReviewSection(int rid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.WEB_UPDATE_SYSTEM_PKG.delete_review_section(?); end;");
			stat.setInt(1, rid);
			stat.execute();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void deleteSchoolReviewSection(int rid): "
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
	public static SchoolReviewSectionBean getSchoolReviewSectionById(int sid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		SchoolReviewSectionBean mm = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_review_section_details(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, sid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createSchoolReviewSectionBean(rs);
				
			}
		}
		catch (SQLException e) {
			System.err.println("SchoolReviewSectionBean getSchoolReviewSectionById(int sid) " + e);
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
	public static void updateSchoolReviewSection(SchoolReviewSectionBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.web_update_system_pkg.update_review_section(?,?,?,?,?,?); end;");
			stat.setInt(1, ebean.getSecType());
			stat.setString(2, ebean.getSecTitle());
			stat.setInt(3, ebean.getSecStatus());
			if (ebean.getSecDescription() != null) {
				Clob clobdesc = con.createClob();
				clobdesc.setString(1, ebean.getSecDescription());
				((OracleCallableStatement) stat).setClob(4, clobdesc);
			}
			else
			{
				stat.setNull(4, OracleTypes.CLOB);
			}
			stat.setInt(5, ebean.getSecId());
			stat.setInt(6, ebean.getSecSortId());
			stat.execute();
			
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void updateSchoolReviewSection(SchoolReviewSectionBean ebean) " + e);
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
	public static SchoolReviewSectionBean createSchoolReviewSectionBean(ResultSet rs) {
		SchoolReviewSectionBean abean = null;
		try {
			abean = new SchoolReviewSectionBean();
			abean.setSecId(rs.getInt("SEC_ID"));
			abean.setSecReviewId(rs.getInt("SEC_REVIEW_ID"));
			Clob clob = rs.getClob("SEC_DESCRIPTION");
			abean.setSecDescription(clob.getSubString(1, (int) clob.length()));
			abean.setSecTitle(rs.getString("SEC_TITLE"));
			abean.setSecStatus(rs.getInt("SEC_STATUS"));
			abean.setSecType(rs.getInt("SEC_TYPE"));
			abean.setSecTypeText(rs.getString("SECTION_NAME"));
			abean.setSecAddedBy(rs.getString("SEC_ADDED_BY"));
			abean.setSecDateAdded(new java.util.Date(rs.getTimestamp("SEC_DATE_ADDED").getTime()));
			abean.setSecSortId(rs.getInt("SEC_SORT_ID"));
			try {
				abean.setFileCount(rs.getInt("FILECOUNT"));
			}
			catch (Exception e) {
				//query does not have value
				abean.setFileCount(0);
			}
		}
		catch (SQLException e) {
			abean = null;
		} 
		return abean;
	}
}
