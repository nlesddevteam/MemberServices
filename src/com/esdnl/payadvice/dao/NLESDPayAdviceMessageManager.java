package com.esdnl.payadvice.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.payadvice.bean.NLESDPayAdviceMessageBean;
import com.esdnl.personnel.jobs.bean.NLESDReferenceException;

public class NLESDPayAdviceMessageManager {
	public static NLESDPayAdviceMessageBean getNLESDPayAdviceMessageBean(int paygroupid) throws NLESDReferenceException {
		NLESDPayAdviceMessageBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.get_pay_advice_message(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, paygroupid);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()){
					eBean = createMessageBean(rs);
				}
		}
		catch (SQLException e) {
				System.err.println("NLESDPayAdviceMessageBean getNLESDPayAdviceMessageBean(int paygroupid)  " + e);
				throw new NLESDReferenceException("Can not extract NLESDPayAdviceEarningsBean from DB.", e);
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
	public static NLESDPayAdviceMessageBean createMessageBean(ResultSet rs) {
		NLESDPayAdviceMessageBean abean = null;
		try {
				abean = new NLESDPayAdviceMessageBean();
				abean.setId(rs.getInt("ID"));
				abean.setMessage(rs.getString("MESSAGE_TEXT"));
				abean.setPayGroup(rs.getInt("PAY_GROUP_ID"));
		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}
}
