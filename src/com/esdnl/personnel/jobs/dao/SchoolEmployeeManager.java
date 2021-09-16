package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.SchoolEmployeeBean;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class SchoolEmployeeManager {
	public static ArrayList<SchoolEmployeeBean> getPermEmployees(String syear, String locationid){

		SchoolEmployeeBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<SchoolEmployeeBean> alist = new ArrayList<SchoolEmployeeBean>();

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.PERSONNEL_JOBS_PKG.get_school_perm_emps(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, syear);
			stat.setString(3, locationid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createSchoolEmployeeBean(rs);
				alist.add(eBean);
			}
		}
		catch (Exception e) {
			System.err.println("ArrayList<SchoolEmployeeBean> getPermEmployees(String syear, String locationid)" + e);
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

		return alist;
	}
	public static ArrayList<SchoolEmployeeBean> getVacEmployees(String syear, String locationid){

		SchoolEmployeeBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<SchoolEmployeeBean> alist = new ArrayList<SchoolEmployeeBean>();

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.PERSONNEL_JOBS_PKG.get_school_vac_emps(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, syear);
			stat.setString(3, locationid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createSchoolEmployeeBean(rs);
				alist.add(eBean);
			}
		}
		catch (Exception e) {
			System.err.println("ArrayList<SchoolEmployeeBean> getVacEmployees(String syear, String locationid)" + e);
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

		return alist;
	}	
	public static SchoolEmployeeBean createSchoolEmployeeBean(ResultSet rs) {

		SchoolEmployeeBean aBean = null;
		try {
			aBean = new SchoolEmployeeBean();

			aBean.setSchoolYear(rs.getString("SYEAR"));
			aBean.setLocationId(rs.getString("LOCID"));
			aBean.setFirstName(rs.getString("FNAME"));
			aBean.setLastName(rs.getString("LNAME"));
			aBean.setCurrentAssignment(rs.getString("CASS"));
			aBean.setCurrentFTE(rs.getString("CUNIT"));
			}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}	
}
