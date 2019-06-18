package com.nlesd.bcs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.BussingContractorSystemRouteRunSchoolBean;
public class BussingContractorSystemRouteRunSchoolManager {
	public static BussingContractorSystemRouteRunSchoolBean addBussingContractorRouteRunSchool(BussingContractorSystemRouteRunSchoolBean vbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.add_new_rr_school(?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, vbean.getRouteRunId());
			stat.setInt(3, vbean.getSchoolId());
			stat.setInt(4, vbean.getSchoolOrder());
			stat.execute();
			Integer sid= ((OracleCallableStatement) stat).getInt(1);
			vbean.setId(sid);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static BussingContractorSystemRouteRunSchoolBean addBussingContractorRouteRunSchool(BussingContractorSystemRouteRunSchoolBean vbean): "
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
		return vbean;
	}
	public static String getRunSchools(int runid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		String schools="";
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_run_schools(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, runid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				if(schools.length() > 0){
					schools += ", " + rs.getString("SCHOOL_NAME");
				}else{
					schools=rs.getString("SCHOOL_NAME");
				}
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("String getRunSchools(int runid) : "
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
		return schools;
	}
	public static HashMap<Integer,String> getRunSchoolsDD(int runid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		HashMap<Integer,String> list = new HashMap<Integer,String>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_run_schools(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, runid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				list.put(rs.getInt("SCHOOL_ID"), rs.getString("SCHOOL_NAME"));
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("String getRunSchools(int runid) : "
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
		return list;
	}
	public static boolean deleteRouteRunSchools(Integer vid)  {

		Connection con = null;
		CallableStatement stat = null;
		boolean check=false;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.bcs_pkg.delete_rr_schools(?); end;");
			stat.setInt(1, vid);
			stat.execute();
			check=true;
		}
		catch (SQLException e) {
			System.err.println("static boolean deleteRouteRunSchools(Integer vid):" + e);
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
		return check;
	}	
	public static BussingContractorSystemRouteRunSchoolBean createBussingContractorSystemRouteRunSchoolBean(ResultSet rs) {
		BussingContractorSystemRouteRunSchoolBean abean = null;
		try {
				abean = new BussingContractorSystemRouteRunSchoolBean();
				abean.setId(rs.getInt("ID"));
				abean.setRouteRunId(rs.getInt("ROUTERUNID"));
				abean.setSchoolId(rs.getInt("SCHOOLID"));
				abean.setSchoolOrder(rs.getInt("SCHOOLORDER"));
				
		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}
}
