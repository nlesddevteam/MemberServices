package com.nlesd.school.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.awsd.school.SchoolException;
import com.esdnl.dao.DAOUtils;
import com.nlesd.school.bean.SchoolStreamSchoolsBean;

public class SchoolStreamSchoolsService {
	public static ArrayList<SchoolStreamSchoolsBean> getSchoolStreamSchoolsEnglishBean(
			int streamId) {
			ArrayList<SchoolStreamSchoolsBean> schoollist = new ArrayList<SchoolStreamSchoolsBean>();
			SchoolStreamSchoolsBean bean = new SchoolStreamSchoolsBean();
			Connection con = null;
			CallableStatement stat = null;
			ResultSet rs = null;
			try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_school_streams_schools(?,?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, streamId);
				stat.setInt(3, 1);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()) {
					bean = createSchoolStreamSchoolsBean(rs);
					schoollist.add(bean);
				}
			}
			catch (SQLException e) {
				System.err.println("static ArrayList<SchoolStreamSchoolsBean> getSchoolStreamSchoolsEnglishBean(int schoolId) " + e);
				try {
					throw new SchoolException("Can not extract SchoolStreamSchools from DB: " + e);
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
			return schoollist;
		}
	public static ArrayList<SchoolStreamSchoolsBean> getSchoolStreamSchoolsFrenchBean(
			int streamId) {
			ArrayList<SchoolStreamSchoolsBean> schoollist = new ArrayList<SchoolStreamSchoolsBean>();
			SchoolStreamSchoolsBean bean = new SchoolStreamSchoolsBean();
			Connection con = null;
			CallableStatement stat = null;
			ResultSet rs = null;
			try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_school_streams_schools(?,?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, streamId);
				stat.setInt(3, 2);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()) {
					bean = createSchoolStreamSchoolsBean(rs);
					schoollist.add(bean);
				}
			}
			catch (SQLException e) {
				System.err.println("static ArrayList<SchoolStreamSchoolsBean> getSchoolStreamSchoolsFrecnhBean(int schoolId) " + e);
				try {
					throw new SchoolException("Can not extract SchoolStreamSchools from DB: " + e);
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
			return schoollist;
		}
	public static int addSchoolStreamSchools(SchoolStreamSchoolsBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.add_school_streams_schools(?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setInt(2, ebean.getSchoolId());
			stat.setInt(3, ebean.getStreamType());
			stat.setInt(4, ebean.getStreamId());
			stat.execute();
			id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("addSchoolStreamSchools(SchoolStreamSchoolsBean ebean)" + e);
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
	public static void deleteSchoolStreamDetails(Integer streamId) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.schools_pkg.delete_school_streams_schools(?); end;");
			stat.setInt(1, streamId);
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void deleteSchoolStreamDetails(Integer streamId) " + e);
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
		public static SchoolStreamSchoolsBean createSchoolStreamSchoolsBean(ResultSet rs) {
			SchoolStreamSchoolsBean abean = null;
			try {
				abean = new SchoolStreamSchoolsBean();
				abean.setId(rs.getInt("ID"));
				abean.setSchoolId(rs.getInt("SCHOOL_ID"));
				abean.setStreamType(rs.getInt("STREAM_TYPE"));
				abean.setStreamId(rs.getInt("STREAM_ID"));
				abean.setSchoolName(rs.getString("SCHOOL_NAME"));
			}
			catch (SQLException e) {
				abean = null;
			}
			return abean;
		}	

}