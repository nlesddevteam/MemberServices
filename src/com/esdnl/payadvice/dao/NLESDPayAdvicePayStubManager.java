package com.esdnl.payadvice.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.payadvice.bean.NLESDPayAdviceException;
import com.esdnl.payadvice.bean.NLESDPayAdvicePayStubBean;

public class NLESDPayAdvicePayStubManager {
	public static void addNLESDPayAdvicePayStub(NLESDPayAdvicePayStubBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.add_pay_advice_pay_stub(?,?,?,?,?); end;");
				stat.registerOutParameter(1, OracleTypes.INTEGER);
				stat.setString(2,ebean.getPayrollId());
				stat.setInt(3,ebean.getPaygroupId());
				stat.setString(4, ebean.getFileName());
				stat.setInt(5,ebean.getEmailed());
				stat.setString(6, ebean.getStubError());
				stat.execute();
			}
		catch (Exception e) {
				e.printStackTrace();
				try {
					con.rollback();
				}
				catch (Exception ex) {}
				System.err.println("void addNLESDPayAdvicePayStub(NLESDPayAdvicePayStubBean ebean) " + e);
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
	public static ArrayList<NLESDPayAdvicePayStubBean>getNLESDPayAdvicePayStubList(Integer paygroupid) throws NLESDPayAdviceException {
		NLESDPayAdvicePayStubBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<NLESDPayAdvicePayStubBean> list = new ArrayList<NLESDPayAdvicePayStubBean>();
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.get_pay_advice_pay_stubs(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, paygroupid);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()){
					eBean = createPayAdvicePayStubBean(rs);
					list.add(eBean);
				}
		}
		catch (SQLException e) {
				System.err.println("ArrayList<NLESDPayAdvicePayStubBean>getNLESDPayAdvicePayStubList(Integer paygroupid)" + e);
				throw new NLESDPayAdviceException("Can not extract NLESDPayAdviceEarningsBean from DB.", e);
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
	public static NLESDPayAdvicePayStubBean createPayAdvicePayStubBean(ResultSet rs) {
		NLESDPayAdvicePayStubBean abean = null;
		try {
				abean = new NLESDPayAdvicePayStubBean();
				abean.setId(rs.getInt("ID"));
				abean.setPayrollId(rs.getString("PAYROLLID"));
				abean.setId(rs.getInt("PAYGROUPID"));
				abean.setFileName(rs.getString("STUBFILENAME"));
				abean.setEmailed(rs.getInt("EMAILED"));
				abean.setStubError(rs.getString("ERROR"));
		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}	
}
