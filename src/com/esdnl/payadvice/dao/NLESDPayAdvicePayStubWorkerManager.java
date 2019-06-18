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
import com.esdnl.payadvice.bean.NLESDPayAdvicePayStubWorkerBean;

public class NLESDPayAdvicePayStubWorkerManager {

	public static ArrayList<NLESDPayAdvicePayStubWorkerBean> getPayStubInformation(int paygroupid) throws NLESDPayAdviceException {
		NLESDPayAdvicePayStubWorkerBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<NLESDPayAdvicePayStubWorkerBean> list = new ArrayList<NLESDPayAdvicePayStubWorkerBean>();
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.get_pay_stub_information(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, paygroupid);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()){
					eBean = createNLESDPayAdvicePayStubWorkerBean(rs);
					list.add(eBean);
				}
		}
		catch (SQLException e) {
				System.err.println("ArrayList<NLESDPayAdvicePayStubWorkerBean> getPayStubInformation(int paygroupid) " + e);
				throw new NLESDPayAdviceException("Can not extract NLESDPayAdvicePayStubWorkerBean from DB.", e);
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
	public static NLESDPayAdvicePayStubWorkerBean createNLESDPayAdvicePayStubWorkerBean(ResultSet rs) {
		NLESDPayAdvicePayStubWorkerBean abean = null;
		try {
				abean = new NLESDPayAdvicePayStubWorkerBean();
				abean.setEmpId(rs.getString("EMP_ID"));
				abean.setSin(rs.getString("SIN"));
				abean.setLastName(rs.getString("LASTNAME"));
				abean.setFirstName(rs.getString("FIRSTNAME"));
				abean.setPayGroupId(rs.getInt("PAY_GROUP_ID"));
				abean.setEmail(rs.getString("EMAIL"));
				abean.setEmpNumber(rs.getString("EMP_NUMBER"));
				abean.setEmployeeId(rs.getString("EMPLOYEE_ID"));
		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}
}
