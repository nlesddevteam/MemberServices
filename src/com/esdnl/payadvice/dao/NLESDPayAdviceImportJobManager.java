package com.esdnl.payadvice.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.payadvice.bean.NLESDPayAdviceException;
import com.esdnl.payadvice.bean.NLESDPayAdviceImportJobBean;

public class NLESDPayAdviceImportJobManager {

	public static Integer addNLESDPayAdviceImportJobBean(NLESDPayAdviceImportJobBean bean) {

		Connection con = null;
		CallableStatement stat = null;
		int id = 0;
		try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(true);
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.add_pay_advice_import_job(?,?,?,?,?,?,?,?); end;");
				stat.registerOutParameter(1, OracleTypes.INTEGER);
				stat.setString(2, bean.getSubmittedBy());
				stat.setInt(3, bean.getPayrollFile());
				stat.setString(4, bean.getPayrollStatus());
				stat.setInt(5, bean.getMappingFile());
				stat.setString(6, bean.getMappingStatus());
				stat.setInt(7, bean.getHistoryFile());
				stat.setString(8, bean.getHistoryStatus());
				stat.setInt(9, bean.getPayGroup());
				stat.execute();
				id = ((CallableStatement) stat).getInt(1);
		}
		catch (SQLException e) {
				e.printStackTrace();
				try {
					con.rollback();
				}
				catch (Exception ex) {}
				System.err.println("Integer addNLESDPayAdviceImportJobBean(NLESDPayAdviceImportJobBean bean) " + e);
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

	public static Collection<NLESDPayAdviceImportJobBean> getNLESDPayAdviceImportJobBeans()
			throws NLESDPayAdviceException {

		Collection<NLESDPayAdviceImportJobBean> documents = null;
		NLESDPayAdviceImportJobBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
				documents = new ArrayList<NLESDPayAdviceImportJobBean>();
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.get_running_import_jobs(); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()) {
					eBean = createNLESDPayAdviceImportJobBean(rs);
					documents.add(eBean);
				}
		}
		catch (SQLException e) {
				System.err.println("NLESDPayrollDocumentBean[] getNLESDPayrollDocumentBeans(): " + e);
				throw new NLESDPayAdviceException("Can not extract getNLESDPayrollDocumentBeans from DB.", e);
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
		return documents;
	}
	public static void updateNLESDPayAdviceImportJobMappingStatus(String status, Integer jobid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(true);
				stat = con.prepareCall("begin awsd_user.payroll_advice_pkg.update_pay_advice_map_status(?,?); end;");
				stat.setString(1, status);
				stat.setInt(2, jobid);
				stat.execute();
		}
		catch (SQLException e) {
				e.printStackTrace();
				try {
					con.rollback();
				}
				catch (Exception ex) {}
				System.err.println("Integer updateNLESDPayAdviceImportJobMappingStatus(String status,Integer jobid) " + e);
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

	public static void updateNLESDPayAdviceImportJobHistoryStatus(String status, Integer jobid) {

		Connection con = null;
		CallableStatement stat = null;
		try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(true);
				stat = con.prepareCall("begin awsd_user.payroll_advice_pkg.update_pay_advice_his_status(?,?); end;");
				stat.setString(1, status);
				stat.setInt(2, jobid);
				stat.execute();
		}
		catch (SQLException e) {
				e.printStackTrace();
				try {
					con.rollback();
				}
				catch (Exception ex) {}
				System.err.println("Integer updateNLESDPayAdviceImportJobHistoryStatus(String status,Integer jobid) " + e);
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

	public static void updateNLESDPayAdviceImportJobPayrollStatus(String status, Integer jobid) {

		Connection con = null;
		CallableStatement stat = null;
		try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(true);
				stat = con.prepareCall("begin awsd_user.payroll_advice_pkg.update_pay_advice_pay_status(?,?); end;");
				stat.setString(1, status);
				stat.setInt(2, jobid);
				stat.execute();
		}
		catch (SQLException e) {
				e.printStackTrace();
				try {
					con.rollback();
				}
				catch (Exception ex) {}
				System.err.println("Integer updateNLESDPayAdviceImportJobPayrollStatus(String status,Integer jobid) " + e);
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

	public static void updateNLESDPayAdviceImportJobComplete(Integer jobid) {

		Connection con = null;
		CallableStatement stat = null;
		try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(true);
				stat = con.prepareCall("begin awsd_user.payroll_advice_pkg.update_pay_advice_job_complete(?); end;");
				stat.setInt(1, jobid);
				stat.execute();
		}
		catch (SQLException e) {
				e.printStackTrace();
				try {
					con.rollback();
				}
				catch (Exception ex) {}
	
				System.err.println("void updateNLESDPayAdviceImportJobComplete(Integer jobid) " + e);
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

	public static void updateNLESDPayAdviceFileProcessed(Integer jobid) {

		Connection con = null;
		CallableStatement stat = null;
		try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(true);
				stat = con.prepareCall("begin awsd_user.payroll_advice_pkg.update_pay_advice_file_st(?); end;");
				stat.setInt(1, jobid);
				stat.execute();
		}
		catch (SQLException e) {
				e.printStackTrace();
				try {
					con.rollback();
				}
				catch (Exception ex) {}
	
				System.err.println("updateNLESDPayAdviceFileProcessed(Integer jobid) " + e);
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

	public static NLESDPayAdviceImportJobBean createNLESDPayAdviceImportJobBean(ResultSet rs) {

		NLESDPayAdviceImportJobBean aBean = null;
		try {
				aBean = new NLESDPayAdviceImportJobBean();
				aBean.setId(rs.getInt("ID"));
				aBean.setSubmittedBy(rs.getString("SUBMITTED_BY"));
				if (rs.getDate("START_TIME") != null) {
					aBean.setStartTime(new java.util.Date(rs.getTimestamp("START_TIME").getTime()));
				}
				else {
					aBean.setStartTime(null);
				}
				if (rs.getDate("END_TIME") != null) {
					aBean.setEndTime(new java.util.Date(rs.getTimestamp("END_TIME").getTime()));
				}
				else {
					aBean.setEndTime(null);
				}
				aBean.setPayrollFile(rs.getInt("PAYROLL_FILE"));
				aBean.setPayrollStatus(rs.getString("PAYROLL_STATUS"));
				aBean.setPayrollFileName(rs.getString("PAYFILE"));
				aBean.setMappingFile(rs.getInt("MAPPING_FILE"));
				aBean.setMappingStatus(rs.getString("MAPPING_STATUS"));
				aBean.setMappingFileName(rs.getString("MAPFILE"));
				aBean.setHistoryFile(rs.getInt("HISTORY_FILE"));
				aBean.setHistoryStatus(rs.getString("HISTORY_STATUS"));
				aBean.setHistoryFileName(rs.getString("HISTORYFILE"));
				aBean.setPayGroup(rs.getInt("PAY_GROUP"));
				aBean.setJobCompleted(rs.getInt("JOB_COMPLETED"));
		}
		catch (SQLException e) {
				aBean = null;
				e.printStackTrace();
		}
		return aBean;
	}
}
