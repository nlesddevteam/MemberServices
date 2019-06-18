package com.esdnl.payadvice.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.payadvice.bean.NLESDPayAdviceException;
import com.esdnl.payadvice.bean.NLESDPayAdvicePayrollProcessErrorBean;
public class NLESDPayAdvicePayrollProcessErrorManager {
	public static TreeMap<String,NLESDPayAdvicePayrollProcessErrorBean> getNLESDPayAdvicePayrollProcessErrors(Integer paygroupid) throws NLESDPayAdviceException {
		NLESDPayAdvicePayrollProcessErrorBean eBean = new NLESDPayAdvicePayrollProcessErrorBean();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<String,NLESDPayAdvicePayrollProcessErrorBean> tmap = new TreeMap<String,NLESDPayAdvicePayrollProcessErrorBean>();
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.get_pay_advice_errors(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, paygroupid);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()){
					eBean = createNLESDPayAdviceTeacherListBean(rs);
					tmap.put(eBean.getEmpName(), eBean);
				}
		}
		catch (SQLException e) {
				System.err.println("TreeMap<String,NLESDPayAdviceTeacherListBean> getNLESDPayAdvicePayrollProcessErrors(Integer paygroupid)" + e);
				throw new NLESDPayAdviceException("Can not extract NLESDPayAdvicePayrollProcessErrorBean from DB.", e);
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
	public static boolean checkNLESDPayAdvicePayrollProcessErrors(Integer paygroupid) throws NLESDPayAdviceException {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		boolean hasErrors =false;
		
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.get_pay_advice_errors(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, paygroupid);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()){
					hasErrors=true;
				}
		}
		catch (SQLException e) {
				System.err.println("checkNLESDPayAdvicePayrollProcessErrors(Integer paygroupid)" + e);
				throw new NLESDPayAdviceException("Can not extract NLESDPayAdvicePayrollProcessErrorBean from DB.", e);
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
		return hasErrors;
	}

	public static String getNLESDPayAdvicePayrollProcessErrorsCSV(Integer id) throws NLESDPayAdviceException {
		StringBuilder csv = new StringBuilder();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.get_pay_advice_errors_csv(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, id);
				stat.execute();
				//add header column
				csv.append("SIN,LAST,FIRST" + "\n");
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()){
					csv.append(rs.getString("EMPLOYEE_ID"));
					csv.append("," + rs.getString("FNAME"));
					csv.append("," + rs.getString("LNAME"));
					csv.append("\n");
					
				}
		}
		catch (SQLException e) {
				System.err.println("String getNLESDPayAdvicePayrollProcessErrorsCSV(NLESDPayAdvicePayrollProcessBean ebean)" + e);
				throw new NLESDPayAdviceException("Can not extract NLESDPayAdvicePayrollProcessBean from DB.", e);
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
		return csv.toString();
	}
	public static ArrayList<String> getNLESDPayAdvicePayrollProcessManualPrint(Integer id) throws NLESDPayAdviceException {
		ArrayList<String> list = new ArrayList<String>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.get_pay_advice_errors_csv(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, id);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()){
					list.add(rs.getString("PAYROLL_ID"));
				}
		}
		catch (SQLException e) {
				System.err.println("ArrayList<String> getNLESDPayAdvicePayrollProcessManualPrint(Integer id)" + e);
				throw new NLESDPayAdviceException("Can not extract NLESDPayAdvicePayrollProcessBean from DB.", e);
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
	public static NLESDPayAdvicePayrollProcessErrorBean createNLESDPayAdviceTeacherListBean(ResultSet rs) {
		NLESDPayAdvicePayrollProcessErrorBean abean = null;
		try {
				abean = new NLESDPayAdvicePayrollProcessErrorBean();
				abean.setEmpName(rs.getString("EMP_NAME"));
				abean.setLocnCode(rs.getString("LOCN_CODE"));
				abean.setError(rs.getString("ERROR"));
		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}
}
