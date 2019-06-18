package com.esdnl.payadvice.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.TreeMap;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.payadvice.bean.NLESDPayAdviceException;
import com.esdnl.payadvice.bean.NLESDPayAdviceImportJobBean;
import com.esdnl.payadvice.bean.NLESDPayAdvicePayGroupBean;
import com.esdnl.payadvice.bean.NLESDPayAdvicePayrollProcessBean;

public class NLESDPayAdvicePayrollProcessManager {
	public static NLESDPayAdvicePayrollProcessBean getNLESDPayAdvicePayrollProcessBean(int paygroupid) throws NLESDPayAdviceException {
		NLESDPayAdvicePayrollProcessBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.get_pay_advice_pay_process(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, paygroupid);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				if (rs.next())
					eBean = createNLESDSPayAdvicePayrollProcessBean(rs,true);
		}
		catch (SQLException e) {
				System.err.println("NLESDSPayAdvicePayrollProcessBean getNLESDPayAdvicePayrollProcessBean(int paygroupid)" + e);
				throw new NLESDPayAdviceException("Can not extract NLESDPayAdviceEmployeeInfoBean from DB.", e);
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
	public static TreeMap<Integer,NLESDPayAdvicePayrollProcessBean> getNLESDPayAdvicePayrollProcessList(Boolean usetotals) throws NLESDPayAdviceException {
		NLESDPayAdvicePayrollProcessBean eBean = null;
		TreeMap<Integer,NLESDPayAdvicePayrollProcessBean> list = new TreeMap<Integer,NLESDPayAdvicePayrollProcessBean>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.get_pay_advice_pay_process_l; end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()){
					eBean = createNLESDSPayAdvicePayrollProcessBean(rs,usetotals);
					list.put(eBean.getPayGroupId(), eBean);
				}
		}
		catch (SQLException e) {
				System.err.println("TreeMap<Integer,NLESDSPayAdvicePayrollProcessBean> getNLESDPayAdvicePayrollProcessList(int paygroupid)" + e);
				throw new NLESDPayAdviceException("Can not extract NLESDPayAdviceEmployeeInfoBean from DB.", e);
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
	public static void updateNLESDPayAdvicePayrollProcessStarted(int paygroupid,String empname) throws NLESDPayAdviceException {
		Connection con = null;
		CallableStatement stat = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin awsd_user.payroll_advice_pkg.update_pay_advice_pp_s(?,?); end;");
				stat.setString(1, empname);
				stat.setInt(2, paygroupid);
				stat.execute();
		}
		catch (SQLException e) {
				System.err.println("updateNLESDPayAdvicePayrollProcessStarted(int paygroupid,String empname)" + e);
				throw new NLESDPayAdviceException("Can not extract NLESDPayAdviceEmployeeInfoBean from DB.", e);
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
	public static void updateNLESDPayAdvicePayrollProcessFinished(int paygroupid) throws NLESDPayAdviceException {
		Connection con = null;
		CallableStatement stat = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin awsd_user.payroll_advice_pkg.update_pay_advice_pp_f(?); end;");
				stat.setInt(1, paygroupid);
				stat.execute();
		}
		catch (SQLException e) {
				System.err.println("void updateNLESDPayAdvicePayrollProcessFinished(int paygroupid)" + e);
				throw new NLESDPayAdviceException("Can not extract NLESDPayAdviceEmployeeInfoBean from DB.", e);
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
	public static void updateNLESDPayAdvicePayrollProcessEmailStarted(int paygroupid,String empname) throws NLESDPayAdviceException {
		Connection con = null;
		CallableStatement stat = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin awsd_user.payroll_advice_pkg.update_pay_advice_ppe_s(?,?); end;");
				stat.setString(1, empname);
				stat.setInt(2, paygroupid);
				stat.execute();
		}
		catch (SQLException e) {
				System.err.println("updateNLESDPayAdvicePayrollProcessEmailStarted(int paygroupid,String empname)" + e);
				throw new NLESDPayAdviceException("Can not extract NLESDPayAdviceEmployeeInfoBean from DB.", e);
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
	public static void updateNLESDPayAdvicePayrollProcessEmailFinished(int paygroupid) throws NLESDPayAdviceException {
		Connection con = null;
		CallableStatement stat = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin awsd_user.payroll_advice_pkg.update_pay_advice_ppe_f(?); end;");
				stat.setInt(1, paygroupid);
				stat.execute();
		}
		catch (SQLException e) {
				System.err.println("void updateNLESDPayAdvicePayrollProcessEmailFinished(int paygroupid)" + e);
				throw new NLESDPayAdviceException("Can not extract NLESDPayAdviceEmployeeInfoBean from DB.", e);
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
	public static void updateNLESDPayAdvicePayrollProcessEmailed(int paygroupid) throws NLESDPayAdviceException {
		Connection con = null;
		CallableStatement stat = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin awsd_user.payroll_advice_pkg.update_pay_advice_ps_email(?); end;");
				stat.setInt(1, paygroupid);
				stat.execute();
		}
		catch (SQLException e) {
				System.err.println("void updateNLESDPayAdvicePayrollProcessEmailed(int paygroupid)" + e);
				throw new NLESDPayAdviceException("Can not extract NLESDPayAdviceEmployeeInfoBean from DB.", e);
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
	public static void  closeNLESDPayAdvicePayPeriod(Integer paygroupid,Date started, Date finished, String closedby) throws NLESDPayAdviceException 
	{
		Connection con = null;
		CallableStatement stat = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin awsd_user.payroll_advice_pkg.update_pay_advice_pay_p_closed(?,?,?,?); end;");
				stat.setInt(1, paygroupid);
				stat.setDate(2, new java.sql.Date(started.getTime()));
				stat.setDate(3, new java.sql.Date(finished.getTime()));
				stat.setString(4, closedby);
				stat.execute();
		}
		catch (SQLException e) {
				System.err.println("closeNLESDPayAdvicePayPeriod(Integer paygroupid,Date started, Date finished, String closedby)" + e);
				throw new NLESDPayAdviceException("Can not extract NLESDPayAdviceEmployeeInfoBean from DB.", e);
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
	public static void getNLESDPayAdvicePayrollProcessTotals(NLESDPayAdvicePayrollProcessBean ebean) throws NLESDPayAdviceException {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.get_pay_advice_email_totals(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, ebean.getPayGroupId());
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()){
					ebean.setTotalPayStubs(rs.getInt("totalcount"));
					ebean.setTotalPayStubsNotSent(rs.getInt("failed"));
					ebean.setTotalPayStubsSent(rs.getInt("success"));
				}
		}
		catch (SQLException e) {
				System.err.println("void getNLESDPayAdvicePayrollProcessTotals(NLESDPayAdvicePayrollProcessBean ebean)" + e);
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
	}
	public static void updateNLESDPayAdviceManualFilename(int paygroupid,String filename) throws NLESDPayAdviceException {
		Connection con = null;
		CallableStatement stat = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin awsd_user.payroll_advice_pkg.update_pay_advice_manual_file(?,?); end;");
				stat.setInt(1, paygroupid);
				stat.setString(2, filename);
				stat.execute();
		}
		catch (SQLException e) {
				System.err.println("void updateNLESDPayAdviceManualFilename(int paygroupid,String filename))" + e);
				throw new NLESDPayAdviceException("Can not update NLESDPayAdvicePayrollProcessBean.", e);
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
	public static NLESDPayAdvicePayrollProcessBean createNLESDSPayAdvicePayrollProcessBean(ResultSet rs, Boolean usetotals) {
		NLESDPayAdvicePayrollProcessBean abean = null;
		try {
				abean = new NLESDPayAdvicePayrollProcessBean();
				NLESDPayAdvicePayGroupBean pgbean = NLESDPayAdvicePayGroupManager.createNLESDPayAdvicePayGroupBean(rs);
				abean.setPayGroupBean(pgbean);
				NLESDPayAdviceImportJobBean ibean = NLESDPayAdviceImportJobManager.createNLESDPayAdviceImportJobBean(rs);
				abean.setImportJobBean(ibean);
				abean.setPayGroupId(rs.getInt("ID"));
				abean.setStubCreationStatus(rs.getString("STUB_CREATION_STATUS"));
				if(rs.getTimestamp("STUB_CREATION_STARTED") != null)
				{
				abean.setStubCreationStarted(new java.util.Date(rs.getTimestamp("STUB_CREATION_STARTED").getTime()));
				}
				if(rs.getTimestamp("STUB_CREATION_FINISHED") != null)
				{
				abean.setStubCreationFinished(new java.util.Date(rs.getTimestamp("STUB_CREATION_FINISHED").getTime()));
				}
				abean.setStubCreationBy(rs.getString("STUB_CREATION_BY"));
				abean.setClosedStatus(rs.getString("CLOSED_STATUS"));
				if(rs.getTimestamp("CLOSED_STARTED") != null){
					abean.setClosedStarted(new java.util.Date(rs.getTimestamp("CLOSED_STARTED").getTime()));
				}
				if(rs.getTimestamp("CLOSE_FINISHED") != null){
					abean.setClosedFinished(new java.util.Date(rs.getTimestamp("CLOSE_FINISHED").getTime()));
				}
				abean.setClosedBy(rs.getString("CLOSED_BY"));		
				abean.setProcessFinished(rs.getInt("PROCESS_FINISHED"));
				abean.setCheckForErrors();
				abean.setManualFilename(rs.getString("MANUAL_FILE_NAME"));
				//now we get the totals
				if(usetotals){
					getNLESDPayAdvicePayrollProcessTotals(abean);
				}
				
		}
		catch (Exception e) {
				abean = null;
				e.printStackTrace();
		}
		return abean;
	}
}
