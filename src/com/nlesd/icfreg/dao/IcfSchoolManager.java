package com.nlesd.icfreg.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;

import com.esdnl.dao.DAOUtils;
import com.nlesd.icfreg.bean.IcfSchoolBean;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class IcfSchoolManager {
	public static int addIcfSchoolBean(IcfSchoolBean atbean) {
		Connection con = null;
		CallableStatement stat = null;
		int test=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			
			stat = con.prepareCall("begin ? :=awsd_user.icf_reg_pkg.add_new_icf_school(?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2,atbean.getIcfSchCap());
			stat.setInt(3,atbean.getIcfRegPerId());
			stat.setInt(4,atbean.getIcfSchSchoolId());
			stat.execute();
			test = stat.getInt(1);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static int addIcfSchoolBean(IcfSchoolBean atbean): "
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
		return test;
	}
	public static ArrayList<IcfSchoolBean> getRegistrationPeriodSchools(int pid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		
		ArrayList<IcfSchoolBean> alist = new ArrayList<>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.icf_reg_pkg.get_registration_per_schools(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, pid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				IcfSchoolBean ebean = new IcfSchoolBean();
				ebean = createIcfSchoolBeanBean(rs);
				alist.add(ebean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static ArrayList<IcfSchoolBean> getRegistrationPeriodSchools(int pid): "
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
		return alist;
	}
	public static TreeMap<String,Integer>getAllSchools()  {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<String,Integer> schoollist = new TreeMap<String,Integer>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.icf_reg_pkg.get_schools; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while(rs.next()) {
				schoollist.put(rs.getString("SCHOOL_NAME"),rs.getInt("SCHOOL_ID"));
			}
		}
		catch (SQLException e) {
			System.err.println("static TreeMap<String,Integer>getSchools()  " + e);
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
	public static void updateIcfSchoolBean(IcfSchoolBean atbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			
			stat = con.prepareCall("begin awsd_user.icf_reg_pkg.update_reg_school(?,?); end;");
			stat.setInt(1,atbean.getIcfSchCap());
			stat.setInt(2,atbean.getIcfSchId());
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static void updateIcfSchoolBean(IcfSchoolBean atbean): "
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
	public static String getSchoolNameById(int sid)  {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		String sname="NOT FOUND";
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.icf_reg_pkg.get_school_name(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, sid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while(rs.next()) {
				sname = rs.getString("SCHOOL_NAME");
			}
		}
		catch (SQLException e) {
			System.err.println("static String getSchoolNameById(int sid) " + e);
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
		return sname;
	}
	public static void deletePeriodSchool(int pid, int sid)  {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.icf_reg_pkg.delete_reg_per_school(?,?); end;");
			stat.setInt(1, pid);
			stat.setInt(2, sid);
			stat.execute();
			
		}
		catch (SQLException e) {
			System.err.println("static void eletePeriodSchool(int pid, int sid)  " + e);
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
	public static IcfSchoolBean createIcfSchoolBeanBean(ResultSet rs) {
		IcfSchoolBean abean = null;
		try {
				abean = new IcfSchoolBean();
				abean.setIcfSchId(rs.getInt("ICF_SCH_ID"));
				abean.setIcfSchCap(rs.getInt("ICF_SCH_CAP"));
				abean.setIcfRegPerId(rs.getInt("ICF_REG_PER_ID"));
				abean.setIcfSchSchoolId(rs.getInt("ICF_SCH_SCHOOL"));
				abean.setIcfSchSchool(rs.getString("SCHOOL_NAME"));
				//extra field, might not be in all sql procedures
				try {	
						abean.setIcfSchCount(rs.getInt("REGCOUNT"));
				}
				catch (SQLException e) {
					abean.setIcfSchCount(0);
				}
		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}
}
