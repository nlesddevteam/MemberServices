package com.nlesd.school.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.awsd.school.SchoolException;
import com.esdnl.dao.DAOUtils;
import com.nlesd.school.bean.SchoolStreamDetailsBean;

public class SchoolStreamDetailsService {

	public static SchoolStreamDetailsBean getSchoolStreamDetailsBean(
			int schoolId) {
		SchoolStreamDetailsBean bean = null;
			Connection con = null;
			CallableStatement stat = null;
			ResultSet rs = null;
			try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_school_streams_details(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, schoolId);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()) {
					bean = createSchoolStreamDetailsBean(rs);
				}
			}
			catch (SQLException e) {
				System.err.println("SchoolStreamDetailsBean getSchoolStreamDetailsBean(int schoolId) " + e);
				try {
					throw new SchoolException("Can not extract SchoolStreamDetails from DB: " + e);
				} catch (SchoolException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
			return bean;
		}
	public static int addSchoolStreamDetails(SchoolStreamDetailsBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.add_school_streams_details(?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setInt(2, ebean.getSchoolId());
			stat.setString(3, ebean.getStreamNotes());
			stat.setString(4, ebean.getAddedBy());
			stat.execute();
			id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("int addSchoolStreamDetails(SchoolStreamDetailsBean ebean) " + e);
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
	public static int updateSchoolStreamDetails(SchoolStreamDetailsBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.schools_pkg.update_school_streams_details(?,?,?,?); end;");
			stat.setInt(1, ebean.getSchoolId());
			stat.setString(2, ebean.getStreamNotes());
			stat.setString(3, ebean.getAddedBy());
			stat.setInt(4, ebean.getId());
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println(" int updateSchoolStreamDetails(SchoolStreamDetailsBean ebean) " + e);
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
	public static SchoolStreamDetailsBean createSchoolStreamDetailsBean(ResultSet rs) {
			SchoolStreamDetailsBean abean = null;
			try {
				abean = new SchoolStreamDetailsBean();
				abean.setId(rs.getInt("ID"));
				abean.setSchoolId(rs.getInt("SCHOOL_ID"));
				abean.setStreamNotes(rs.getString("STREAM_NOTES"));
				abean.setAddedBy(rs.getString("ADDED_BY"));
				abean.setDateAdded(new java.util.Date(rs.getTimestamp("DATE_ADDED").getTime()));
			}
			catch (SQLException e) {
				abean = null;
			}
			return abean;
		}	

}
