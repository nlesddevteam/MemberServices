package com.nlesd.icfreg.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import com.esdnl.dao.DAOUtils;
import com.nlesd.icfreg.bean.IcfRegistrationPeriodBean;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class IcfRegistrationPeriodManager {
	public static int addIcfRegistrationPeriodBean(IcfRegistrationPeriodBean atbean) {
		Connection con = null;
		CallableStatement stat = null;
		int test=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			
			stat = con.prepareCall("begin ? :=awsd_user.icf_reg_pkg.add_new_icf_reg_per(?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2,atbean.getIcfRegPerSchoolYear());
			stat.setTimestamp(3, new Timestamp(atbean.getIcfRegStartDate().getTime()));
			stat.setTimestamp(4, new Timestamp(atbean.getIcfRegEndDate().getTime()));
			stat.execute();
			test = stat.getInt(1);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static int addIcfRegistrationPeriodBean(IcfRegistrationPeriodBean atbean): "
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
	public static ArrayList<IcfRegistrationPeriodBean> getRegistrationPeriods() {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		
		ArrayList<IcfRegistrationPeriodBean> alist = new ArrayList<>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.icf_reg_pkg.get_registration_periods; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				IcfRegistrationPeriodBean ebean = new IcfRegistrationPeriodBean();
				ebean = createIcfRegistrationPeriodBean(rs);
				alist.add(ebean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static IcfRegistrationPeriodBean getRegistrationPeriods(): "
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
	public static IcfRegistrationPeriodBean getRegistrationPeriodById(int pid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		IcfRegistrationPeriodBean ebean = new IcfRegistrationPeriodBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.icf_reg_pkg.get_registration_period_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, pid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createIcfRegistrationPeriodBean(rs);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static IcfRegistrationPeriodBean getRegistrationPeriodById(int pid): "
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
		return ebean;
	}
	public static ArrayList<IcfRegistrationPeriodBean> getFutureRegistrationPeriods() {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		
		ArrayList<IcfRegistrationPeriodBean> alist = new ArrayList<>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.icf_reg_pkg.get_future_reg_periods; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				IcfRegistrationPeriodBean ebean = new IcfRegistrationPeriodBean();
				ebean = createIcfRegistrationPeriodBean(rs);
				alist.add(ebean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static ArrayList<IcfRegistrationPeriodBean> getFutureRegistrationPeriods(): "
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
	public static IcfRegistrationPeriodBean getCurrentRegistrationPeriod() {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		IcfRegistrationPeriodBean ebean = new IcfRegistrationPeriodBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.icf_reg_pkg.get_current_reg_period; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createIcfRegistrationPeriodBean(rs);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static IcfRegistrationPeriodBean getCurrentRegistrationPeriod(): "
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
		return ebean;
	}
	public static ArrayList<IcfRegistrationPeriodBean> getRegistrationPeriodsBySchool(int sid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		
		ArrayList<IcfRegistrationPeriodBean> alist = new ArrayList<>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.icf_reg_pkg.get_registration_periods_s(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, sid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				IcfRegistrationPeriodBean ebean = new IcfRegistrationPeriodBean();
				ebean = createIcfRegistrationPeriodBean(rs);
				alist.add(ebean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static ArrayList<IcfRegistrationPeriodBean> getRegistrationPeriodsBySchool(int sid): "
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
	public static IcfRegistrationPeriodBean createIcfRegistrationPeriodBean(ResultSet rs) {
		IcfRegistrationPeriodBean abean = null;
		try {
				abean = new IcfRegistrationPeriodBean();
				abean.setIcfRegPerId(rs.getInt("ICF_REG_PER_ID"));
				//two fields used in full query return of data, if there then they will be used.
				Timestamp ts= rs.getTimestamp("ICF_REG_PER_START_DATE");
				if(ts != null){
					abean.setIcfRegStartDate(new java.util.Date(rs.getTimestamp("ICF_REG_PER_START_DATE").getTime()));
				}
				ts= rs.getTimestamp("ICF_REG_PER_END_DATE");
				if(ts != null){
					abean.setIcfRegEndDate(new java.util.Date(rs.getTimestamp("ICF_REG_PER_END_DATE").getTime()));
				}
				abean.setIcfRegPerSchoolYear(rs.getString("ICF_REG_PER_SCHOOL_YEAR"));
				//extra field, might not be in all sql procedures
				try {	
						abean.setIcfRegCount(rs.getInt("REGCOUNT"));
				}
				catch (SQLException e) {
					abean.setIcfRegCount(0);
				}
				
		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}
}
