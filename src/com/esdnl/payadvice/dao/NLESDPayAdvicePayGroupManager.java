package com.esdnl.payadvice.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.payadvice.bean.NLESDPayAdviceException;
import com.esdnl.payadvice.bean.NLESDPayAdvicePayGroupBean;
public class NLESDPayAdvicePayGroupManager {
	public static Integer addNLESDPayAdvicePayGroupBean(NLESDPayAdvicePayGroupBean bean) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(true);
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.add_pay_advice_pay_group(?,?,?,?,?,?,?,?,?); end;");
				stat.registerOutParameter(1, OracleTypes.INTEGER);
				stat.setString(2, bean.getPayGp());
				stat.setString(3, bean.getPayBgDt());
				stat.setString(4, bean.getPayEndDt());
				stat.setString(5, bean.getBusUnit());
				stat.setString(6, bean.getCheckNum());
				stat.setString(7, bean.getCheckDt());
				stat.setString(8, bean.getPayBgDtD());
				stat.setString(9, bean.getPayEndDtD());
				stat.setString(10, bean.getCheckDtD());
				stat.execute();
				id=((CallableStatement) stat).getInt(1);
		}
		catch (SQLException e) {
				e.printStackTrace();
				try {
					con.rollback();
				}
				catch (Exception ex) {}
					System.err.println("Integer addNLESDPayAdvicePayGroupBean(NLESDPayAdvicePayGroupBean bean) " + e);
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
	public static NLESDPayAdvicePayGroupBean getNLESDPayAdvicePayGroupBean(int paygroupid) throws NLESDPayAdviceException {
		NLESDPayAdvicePayGroupBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.get_pay_advice_pay_group(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, paygroupid);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				if (rs.next())
					eBean = createNLESDPayAdvicePayGroupBean(rs);
		}
		catch (SQLException e) {
				System.err.println("NLESDPayAdvicePayGroupBean getNLESDPayAdviceEmployeeInfoBean(int paygroupid, String empnumber) " + e);
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
	public static NLESDPayAdvicePayGroupBean createNLESDPayAdvicePayGroupBean(ResultSet rs) {
		NLESDPayAdvicePayGroupBean abean = null;
		try {
				abean = new NLESDPayAdvicePayGroupBean();
				abean.setId(rs.getInt("ID"));
				abean.setPayGp(rs.getString("PAY_GP"));
				abean.setPayBgDt(rs.getString("PAY_BG_DT"));
				abean.setPayEndDt(rs.getString("PAY_END_DT"));
				abean.setBusUnit(rs.getString("BUS_UNIT"));
				abean.setCheckNum(rs.getString("CHECK_NUM"));
				abean.setCheckDt(rs.getString("CHECK_DT"));
				abean.setPayBgDtD(rs.getString("PAY_BG_DTD"));
				abean.setPayEndDtD(rs.getString("PAY_END_DTD"));
				abean.setCheckDtD(rs.getString("CHECK_DTD"));
		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}
}
