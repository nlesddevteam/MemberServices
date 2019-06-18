package com.esdnl.payadvice.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.payadvice.bean.NLESDPayAdviceCompanyInfoBean;

public class NLESDPayAdviceCompanyInfoManager {
	public static Integer addNLESDPayAdviceWorkHistoryBean(NLESDPayAdviceCompanyInfoBean bean) {
		Connection con = null;
		CallableStatement stat = null;
		Integer id=0;
		try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(true);
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.add_pay_advice_company_info(?,?,?,?); end;");
				stat.registerOutParameter(1, OracleTypes.INTEGER);
				stat.setString(2, bean.getCompany());
				stat.setString(3, bean.getCoAddrL1());
				stat.setString(4, bean.getCoAddrL2());
				stat.setInt(5, bean.getPayGroup());
				stat.execute();
				id=((CallableStatement) stat).getInt(1);
			}
		catch (SQLException e) {
				e.printStackTrace();
				try {
					con.rollback();
				}
				catch (Exception ex) {}
				System.err.println("Integer addNLESDPayAdviceWorkHistoryBean(NLESDPayAdviceCompanyInfoBean bean) " + e);
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
}
