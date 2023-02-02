package com.nlesd.icfreg.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.esdnl.dao.DAOUtils;
import com.nlesd.icfreg.bean.IcfRegApplicantBean;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class IcfApplicantManager {
	public static int addIcfRegApplicantBean(IcfRegApplicantBean atbean) {
		Connection con = null;
		CallableStatement stat = null;
		int test=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			
			stat = con.prepareCall("begin ? :=awsd_user.icf_reg_pkg.add_new_icf_app(?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2,atbean.getIcfAppEmail());
			stat.setString(3,atbean.getIcfAppFullName());
			stat.setInt(4,atbean.getIcfAppSchool());
			stat.setString(5,atbean.getIcfAppGuaFullName());
			stat.setString(6,atbean.getIcfAppContact1());
			stat.setString(7,atbean.getIcfAppContact2());
			stat.setInt(8,atbean.getIcfAppStatus());
			stat.setInt(9,atbean.getIcfAppRegPer());
			stat.setTimestamp(10, new Timestamp(atbean.getIcfAppDateSubmitted().getTime()));
			stat.execute();
			test = stat.getInt(1);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static int addIcfRegApplicantBean(IcfRegApplicantBean atbean): "
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
	public static int adminAddIcfRegApplicantBean(IcfRegApplicantBean atbean) {
		Connection con = null;
		CallableStatement stat = null;
		int test=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			
			stat = con.prepareCall("begin ? :=awsd_user.icf_reg_pkg.add_new_icf_app(?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2,atbean.getIcfAppEmail());
			stat.setString(3,atbean.getIcfAppFullName());
			stat.setInt(4,atbean.getIcfAppSchool());
			stat.setString(5,atbean.getIcfAppGuaFullName());
			stat.setString(6,atbean.getIcfAppContact1());
			stat.setString(7,atbean.getIcfAppContact2());
			stat.setInt(8,atbean.getIcfAppStatus());
			stat.setInt(9,atbean.getIcfAppRegPer());
			stat.setTimestamp(10, new Timestamp(new Date().getTime()));
			stat.execute();
			test = stat.getInt(1);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static int addIcfRegApplicantBean(IcfRegApplicantBean atbean): "
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
	public static ArrayList<IcfRegApplicantBean> getPeriodApplicants(int pid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		IcfRegApplicantBean ebean = new IcfRegApplicantBean();
		ArrayList<IcfRegApplicantBean> alist = new ArrayList<>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.icf_reg_pkg.get_reg_per_applicants(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, pid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createIcfRegApplicantBean(rs);
				alist.add(ebean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static ArrayList<IcfRegApplicantBean> getPeriodApplicants(int pid): "
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
	public static IcfRegApplicantBean getApplicantById(int pid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		IcfRegApplicantBean ebean = new IcfRegApplicantBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.icf_reg_pkg.get_applicant_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, pid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createIcfRegApplicantBean(rs);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static IcfRegApplicantBean getApplicantById(int pid): "
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
	public static ArrayList<IcfRegApplicantBean> getPeriodApplicantsBySchool(int pid,int sid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		IcfRegApplicantBean ebean = new IcfRegApplicantBean();
		ArrayList<IcfRegApplicantBean> alist = new ArrayList<>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.icf_reg_pkg.get_reg_per_apps_by_sch(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, pid);
			stat.setInt(3, sid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createIcfRegApplicantBean(rs);
				alist.add(ebean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static ArrayList<IcfRegApplicantBean> getPeriodApplicantsBySchool(int pid,int sid): "
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
	public static void updateIcfRegApplicantBean(IcfRegApplicantBean atbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			
			stat = con.prepareCall("begin awsd_user.icf_reg_pkg.update_icf_app(?,?,?,?,?,?,?); end;");
			stat.setString(1,atbean.getIcfAppEmail());
			stat.setString(2,atbean.getIcfAppFullName());
			stat.setInt(3,atbean.getIcfAppSchool());
			stat.setString(4,atbean.getIcfAppGuaFullName());
			stat.setString(5,atbean.getIcfAppContact1());
			stat.setString(6,atbean.getIcfAppContact2());
			stat.setInt(7,atbean.getIcfAppId());
			
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void updateIcfRegApplicantBean(IcfRegApplicantBean atbean): "
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
	public static void updateRegistrantStatus(int rid,int sid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.icf_reg_pkg.update_reg_status(?,?); end;");
			stat.setInt(1, rid);
			stat.setInt(2, sid);
			stat.execute();
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static void updateRegistrantStatus(int rid,int sid): "
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
	public static void deleteRegistrant(int rid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.icf_reg_pkg.delete_registrant(?); end;");
			stat.setInt(1, rid);
			stat.execute();
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static void deleteRegistrant(int rid): "
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
	public static IcfRegApplicantBean createIcfRegApplicantBean(ResultSet rs) {
		IcfRegApplicantBean abean = null;
		try {
				abean = new IcfRegApplicantBean();
				abean.setIcfAppId(rs.getInt("ICF_APP_ID"));
				abean.setIcfAppEmail(rs.getString("ICF_APP_EMAIL"));
				abean.setIcfAppFullName(rs.getString("ICF_APP_FULL_NAME"));
				abean.setIcfAppSchool(rs.getInt("ICF_APP_SCHOOL"));
				abean.setIcfAppGuaFullName(rs.getString("ICF_APP_GUA_FULL_NAME"));
				abean.setIcfAppContact1(rs.getString("ICF_APP_CONTACT_1"));
				abean.setIcfAppContact2(rs.getString("ICF_APP_CONTACT_2"));
				//two fields used in full query return of data, if there then they will be used.
				Timestamp ts= rs.getTimestamp("ICF_APP_DATE_SUBMITTED");
				if(ts != null){
					abean.setIcfAppDateSubmitted(new java.util.Date(rs.getTimestamp("ICF_APP_DATE_SUBMITTED").getTime()));
				}
				abean.setIcfAppStatus(rs.getInt("ICF_APP_STATUS"));
				abean.setIcfAppRegPer(rs.getInt("ICF_APP_REG_PER"));
				
				//extra field, might not be in all sql procedures
				try {	
						abean.setIcfAppSchoolName(rs.getString("SCHOOL_NAME"));
				}
				catch (SQLException e) {
					abean.setIcfAppSchoolName("SCHOOL ERROR");
				}
				
		}
		catch (Exception e) {
				abean = null;
		}
		return abean;
	}
}
