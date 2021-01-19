package com.esdnl.webupdatesystem.schoolreviews.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.esdnl.dao.DAOUtils;
import com.esdnl.webupdatesystem.schoolreviews.bean.SchoolReviewSchoolBean;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class SchoolReviewSchoolManager {
	public static int addSchoolReviewSchool(SchoolReviewSchoolBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.add_new_review_school(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setInt(2, ebean.getReviewId());
			stat.setInt(3, ebean.getSchoolId());
			stat.execute();
			id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static int addSchoolReviewSchool(SchoolReviewSchoolBean ebean) " + e);
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
	public static ArrayList<SchoolReviewSchoolBean> getSchoolReviewSchoolsById(int id) {
		SchoolReviewSchoolBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<SchoolReviewSchoolBean> list = new ArrayList<SchoolReviewSchoolBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_schools(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createSchoolReviewSchoolBean(rs);
				list.add(mm);
			}
		}
		catch (SQLException e) {
			System.err.println("SchoolReviewSchoolBean getSchoolReviewSchoolsById(int id) " + e);
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
	public static void deleteReviewSchools(int reviewid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.WEB_UPDATE_SYSTEM_PKG.delete_review_schools(?); end;");
			stat.setInt(1, reviewid);
			stat.execute();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static void deleteReviewSchools(int reviewid): "
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
	public static SchoolReviewSchoolBean createSchoolReviewSchoolBean(ResultSet rs) {
		SchoolReviewSchoolBean abean = null;
		try {
			abean = new SchoolReviewSchoolBean();
			abean.setId(rs.getInt("ID"));
			abean.setReviewId(rs.getInt("REVIEW_ID"));
			abean.setSchoolId(rs.getInt("SCHOOL_ID"));
			abean.setSchoolName(rs.getString("SCHOOL_NAME"));
			if(rs.getInt("REVIEW_ID") > 0) {
				abean.setSelected(true);
			}else {
				abean.setSelected(false);
			}
		}
		catch (SQLException e) {
			abean = null;
		} 
		return abean;
	}
	public static SchoolReviewSchoolBean createSchoolReviewSchoolBeanDisplay(ResultSet rs) {
		SchoolReviewSchoolBean abean = null;
		try {
			abean = new SchoolReviewSchoolBean();
			abean.setId(rs.getInt("SCHOOL_RID"));
			abean.setReviewId(rs.getInt("REVIEW_ID"));
			abean.setSchoolId(rs.getInt("SCHOOL_ID"));
			abean.setSchoolName(rs.getString("SCHOOL_NAME"));
		}
		catch (SQLException e) {
			abean = null;
		} 
		return abean;
	}
}
