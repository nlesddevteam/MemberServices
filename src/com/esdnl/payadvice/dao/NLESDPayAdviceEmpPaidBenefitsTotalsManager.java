package com.esdnl.payadvice.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.payadvice.bean.NLESDPayAdviceEmpPaidBenefitsTotalsBean;
import com.esdnl.payadvice.bean.NLESDPayAdviceException;
public class NLESDPayAdviceEmpPaidBenefitsTotalsManager {
	public static NLESDPayAdviceEmpPaidBenefitsTotalsBean getNLESDPayAdviceEmpPaidBenefitsTotalsBean(int paygroupid, String empnumber,Integer empinfoid) throws NLESDPayAdviceException {
		NLESDPayAdviceEmpPaidBenefitsTotalsBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.get_pay_advice_emp_p_b_tot(?,?,?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, paygroupid);
				stat.setString(3, empnumber);
				stat.setInt(4, empinfoid);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()){
					eBean = createNLESDPayAdviceEmpPaidBenefitsTotalsBean(rs);
				}
		}
		catch (SQLException e) {
				System.err.println("NLESDPayAdviceEmployeeInfoBean getNLESDPayAdviceEmployeeInfoBean(int paygroupid, String empnumber) " + e);
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
	public static NLESDPayAdviceEmpPaidBenefitsTotalsBean createNLESDPayAdviceEmpPaidBenefitsTotalsBean(ResultSet rs) {
		NLESDPayAdviceEmpPaidBenefitsTotalsBean abean = null;
		try {
				abean = new NLESDPayAdviceEmpPaidBenefitsTotalsBean();
				abean.setEpbTtlCur(Double.parseDouble(rs.getString("curtotal")));
				abean.setEpbTtlYtd(Double.parseDouble(rs.getString("ytdtotal")));
				abean.setEmpNumber(rs.getString("EMP_NUMBER"));
				abean.setPayGroupId(Integer.parseInt(rs.getString("PAY_GROUP_ID")));
				abean.setEmpInfoId(rs.getInt("EMP_INFO_ID"));
		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}
}