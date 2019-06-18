package com.esdnl.payadvice.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.esdnl.dao.DAOUtils;
import com.esdnl.payadvice.bean.NLESDPayAdviceEmployeeSecurityBean;
import com.esdnl.payadvice.bean.NLESDPayAdviceException;
import com.esdnl.velocity.VelocityUtils;

public class NLESDPayAdviceEmployeeSecurityManager {

	public static Integer addNLESDPayAdviceEmployeeSecurity(NLESDPayAdviceEmployeeSecurityBean bean) {

		Connection con = null;
		CallableStatement stat = null;
		int id = 0;
		try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(true);
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.add_pay_advice_emp_security(?,?,?); end;");
				stat.registerOutParameter(1, OracleTypes.INTEGER);
				stat.setString(2, bean.getPayrollId());
				stat.setString(3, bean.getEmployeeId());
				stat.setString(4, bean.getPassword());
				stat.execute();
				id = ((CallableStatement) stat).getInt(1);
		}
		catch (SQLException e) {
				e.printStackTrace();
				try {
					con.rollback();
				}
				catch (Exception ex) {}
				System.err.println("Integer addNLESDPayAdviceEmployeeSecurity(NLESDPayAdviceEmployeeSecurityBean bean" + e);
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

	public static void updateNLESDPayAdviceEmployeeSecurity(NLESDPayAdviceEmployeeSecurityBean bean) {

		Connection con = null;
		CallableStatement stat = null;
		try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(true);
				stat = con.prepareCall("begin awsd_user.payroll_advice_pkg.update_pay_advice_emp_security(?,?); end;");
				stat.setString(1, bean.getEmployeeId());
				stat.setString(2, bean.getPassword());
				stat.execute();
		}
		catch (SQLException e) {
				e.printStackTrace();
				try {
					con.rollback();
				}
				catch (Exception ex) {}
				System.err.println("updateNLESDPayAdviceEmployeeSecurity(NLESDPayAdviceEmployeeSecurityBean bean) " + e);
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

	public static void updateNLESDPayAdviceEmployeeSecurity(String payrollid, String password) {

		Connection con = null;
		CallableStatement stat = null;
		try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(true);
				stat = con.prepareCall("begin awsd_user.payroll_advice_pkg.update_pay_advice_emp_sec_e(?,?); end;");
				stat.setString(1, payrollid);
				stat.setString(2, password);
				stat.execute();
		}
		catch (SQLException e) {
				e.printStackTrace();
				try {
					con.rollback();
				}
				catch (Exception ex) {}
				System.err.println("void updateNLESDPayAdviceEmployeeSecurity(String payrollid, String password)) " + e);
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

	public static NLESDPayAdviceEmployeeSecurityBean getNLESDPayAdviceEmployeeSecurityBean(String employeeid)
			throws NLESDPayAdviceException {

		NLESDPayAdviceEmployeeSecurityBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.get_pay_advice_emp_security(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setString(2, employeeid);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				if (rs.next())
					eBean = createNLESDPayAdviceEmployeeSecurityBean(rs);
		}
		catch (SQLException e) {
				System.err.println("NLESDPayAdviceEmployeeSecurityBean getNLESDPayAdviceEmployeeSecurityBean(String employeeid) "
						+ e);
				throw new NLESDPayAdviceException("Can not extract NLESDPayAdviceEmployeeSecurityBean from DB.", e);
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
		return eBean;
	}

	public static String createNewPassword(NLESDPayAdviceEmployeeSecurityBean ebean, String email) {

		//create a six character password
		StringBuilder randomString = new StringBuilder();
		String chars = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789";
		int length = chars.length();
		for (int i = 0; i < 6; i++) {
			randomString.append(chars.split("")[(int) (Math.random() * (length - 1))]);
		}
		ebean.setPassword(randomString.toString());
		//now we save it to the db
		NLESDPayAdviceEmployeeSecurityManager.addNLESDPayAdviceEmployeeSecurity(ebean);
		//now we send an email to the person with the password
		if (email != null) {
			HashMap<String, Object> model = new HashMap<String, Object>();
			// set values to be used in template
			model.put("password", randomString.toString());
			EmailBean emailbean = new EmailBean();
			emailbean.setSubject("User information for NLESD Pay Advice system");
			emailbean.setTo(email);
			emailbean.setBody(VelocityUtils.mergeTemplateIntoString("payadvice/pay_advice_email_p.vm", model));

			try {
				emailbean.send();
			}
			catch (EmailException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//now return password
		return randomString.toString();
	}
	public static String resetPassword(NLESDPayAdviceEmployeeSecurityBean ebean, String email) {
		//create a six character password
		StringBuilder randomString = new StringBuilder();
		String chars = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789";
		int length = chars.length();
		for (int i = 0; i < 6; i++) {
			randomString.append(chars.split("")[(int) (Math.random() * (length - 1))]);
		}
		ebean.setPassword(randomString.toString());
		//now we save it to the db
		NLESDPayAdviceEmployeeSecurityManager.updateNLESDPayAdviceEmployeeSecurity(ebean);
		//now we send an email to the person with the password
		if (email != null) {
			HashMap<String, Object> model = new HashMap<String, Object>();
			// set values to be used in template
			model.put("password", randomString.toString());
			EmailBean emailbean = new EmailBean();
			emailbean.setSubject("User information for NLESD Pay Advice system");
			emailbean.setTo(email);
			emailbean.setBody(VelocityUtils.mergeTemplateIntoString("payadvice/pay_advice_email_p.vm", model));

			try {
				emailbean.send();
			}
			catch (EmailException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		//now return password
		return randomString.toString();

	}

	public static String getEmployeeEmail(String payrollid) {

		Connection con = null;
		CallableStatement stat = null;
		String emailaddress = "";
		ResultSet rs = null;
		try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(true);
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.get_pay_advice_employee_email(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setString(2, payrollid);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				if (rs.next()) {
					emailaddress = rs.getString("email");
				}
		}
		catch (SQLException e) {
				e.printStackTrace();
				try {
					con.rollback();
				}
				catch (Exception ex) {}
				System.err.println("getEmployeeEmail(String payrollid)" + e);
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
		return emailaddress;

	}

	public static String getEmployeeSIN(String email) {
		Connection con = null;
		CallableStatement stat = null;
		String empsin = "";
		ResultSet rs = null;
		try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(true);
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.get_pay_advice_emp_sin(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setString(2, email);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				if (rs.next()) {
					empsin = rs.getString("sin");
				}
		}
		catch (SQLException e) {
				e.printStackTrace();
				try {
					con.rollback();
				}
				catch (Exception ex) {}
				System.err.println("String getEmployeeSIN(Integer employeeid)" + e);
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
		return empsin;

	}
	public static HashMap<String,String> getAllEmployeesPasswords() {

		Connection con = null;
		CallableStatement stat = null;
		HashMap<String,String>list = new HashMap<String,String>();
		ResultSet rs = null;
		try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(true);
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.get_pay_advice_passwords; end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()) {
					list.put(rs.getString("email"),rs.getString("password"));
				}
		}
		catch (SQLException e) {
				e.printStackTrace();
				try {
					con.rollback();
				}
				catch (Exception ex) {}
				System.err.println("String getEmployeeSIN(Integer employeeid)" + e);
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
	public static void resendPassword(String password, String email) {

		//create a six character password
			
			HashMap<String, Object> model = new HashMap<String, Object>();
			// set values to be used in template
			model.put("password", password);
			EmailBean emailbean = new EmailBean();
			emailbean.setSubject("User information for NLESD Pay Advice system");
			emailbean.setTo(email);
			emailbean.setBody(VelocityUtils.mergeTemplateIntoString("payadvice/pay_advice_email_p.vm", model));

			try {
				emailbean.send();
			}
			catch (EmailException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}	
	public static NLESDPayAdviceEmployeeSecurityBean createNLESDPayAdviceEmployeeSecurityBean(ResultSet rs) {

		NLESDPayAdviceEmployeeSecurityBean aBean = null;
		try {
				aBean = new NLESDPayAdviceEmployeeSecurityBean();
				aBean.setId(rs.getInt("PSID"));
				aBean.setPayrollId(rs.getString("PMPID"));
				aBean.setEmployeeId(rs.getString("PMEID"));
				aBean.setPassword(rs.getString("PASSWORD"));
				aBean.setWasEmailed(rs.getInt("WASEMAILED"));
		}
		catch (SQLException e) {
				aBean = null;
				e.printStackTrace();
		}
		return aBean;
	}
}