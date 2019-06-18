package com.esdnl.payadvice.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.payadvice.bean.NLESDPayAdviceException;
import com.esdnl.payadvice.bean.NLESDPayAdviceSearchBean;

public class NLESDPayAdviceSearchManager {
	public static TreeMap<String,NLESDPayAdviceSearchBean> searchNLESDPayAdviceByName(String name) throws NLESDPayAdviceException {
		NLESDPayAdviceSearchBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<String,NLESDPayAdviceSearchBean> tmap = new TreeMap<String,NLESDPayAdviceSearchBean>();
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.search_pay_advice_by_name(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setString(2, name.toUpperCase());
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()){
					eBean = createNLESDPayAdviceSearchBean(rs);
					tmap.put(eBean.getEmpName(), eBean);
				}
		}
		catch (SQLException e) {
				System.err.println("TreeMap<Integer,NLESDPayAdviceSearchBean> searchNLESDPayAdviceByName(String name)" + e);
				throw new NLESDPayAdviceException("Can not extract NLESDPayAdviceSearchBean from DB.", e);
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
		return tmap;
	}
	public static TreeMap<String,NLESDPayAdviceSearchBean> searchNLESDPayAdviceBySIN(String sin) throws NLESDPayAdviceException {
		NLESDPayAdviceSearchBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<String,NLESDPayAdviceSearchBean> tmap = new TreeMap<String,NLESDPayAdviceSearchBean>();
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.search_pay_advice_by_sin(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setString(2, sin);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()){
					eBean = createNLESDPayAdviceSearchBean(rs);
					tmap.put(eBean.getEmpName(), eBean);
				}
		}
		catch (SQLException e) {
				System.err.println("TreeMap<Integer,NLESDPayAdviceSearchBean> searchNLESDPayAdviceBySIN(String name)" + e);
				throw new NLESDPayAdviceException("Can not extract NLESDPayAdviceSearchBean from DB.", e);
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
		return tmap;
	}
	public static TreeMap<String,NLESDPayAdviceSearchBean> searchNLESDPayAdviceBySchool(String school) throws NLESDPayAdviceException {
		NLESDPayAdviceSearchBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<String,NLESDPayAdviceSearchBean> tmap = new TreeMap<String,NLESDPayAdviceSearchBean>();
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.search_pay_advice_by_school(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setString(2, school);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()){
					eBean = createNLESDPayAdviceSearchBean(rs);
					tmap.put(eBean.getEmpName(), eBean);
					
				}
		}
		catch (SQLException e) {
				System.err.println("TreeMap<Integer,NLESDPayAdviceSearchBean> searchNLESDPayAdviceBySchoolString name)" + e);
				throw new NLESDPayAdviceException("Can not extract NLESDPayAdviceSearchBean from DB.", e);
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
		return tmap;
	}
	public static NLESDPayAdviceSearchBean createNLESDPayAdviceSearchBean(ResultSet rs) {
		NLESDPayAdviceSearchBean abean = null;
		try {
				abean = new NLESDPayAdviceSearchBean();
				abean.setEmployeeId(rs.getString("EMPLOYEE_ID"));
				abean.setEmpName(rs.getString("EMP_NAME"));
				abean.setLocnCode(rs.getString("LOCN_CODE"));
				abean.setEmpNumber(rs.getString("EMP_NUMBER"));
				abean.setPayrollId(rs.getString("PAYROLL_ID"));
			}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}
}
