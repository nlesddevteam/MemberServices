package com.esdnl.payadvice.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.esdnl.dao.DAOUtils;
import com.esdnl.payadvice.bean.NLESDPayAdviceEarningsTotalsBean;
import com.esdnl.payadvice.bean.NLESDPayAdviceException;

public class NLESDPayAdviceEarningsTotalsManager {
	public static void addNLESDPayAdviceEarningsTotals(NodeList nl, Integer paygroupid, String empnumber,Integer empinfoid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(false);
				stat = con.prepareCall("begin awsd_user.payroll_advice_pkg.add_pay_advice_earnings_totals(?,?,?,?,?,?,?); end;");
				Element employeeinfo =(Element) nl.item(0);
				//need to check to see if ytd_hrs exist
				stat.setDouble(1,Double.parseDouble(employeeinfo.getElementsByTagName("ttl_cur_hrs").item(0).getTextContent().replaceAll(",", "")));
				stat.setDouble(2,Double.parseDouble(employeeinfo.getElementsByTagName("ttl_cur_ern").item(0).getTextContent().replaceAll(",", "")));	
				stat.setDouble(3,Double.parseDouble(employeeinfo.getElementsByTagName("ttl_ytd_hrs").item(0).getTextContent().replaceAll(",", "")));
				stat.setDouble(4,Double.parseDouble(employeeinfo.getElementsByTagName("ttl_ytd_ern").item(0).getTextContent().replaceAll(",", "")));
				stat.setInt(5,paygroupid);
				stat.setString(6,empnumber);
				stat.setInt(7,empinfoid);
				stat.addBatch();
				stat.executeBatch();
				con.setAutoCommit(true);
			}
		catch (Exception e) {
				e.printStackTrace();
				try {
					con.rollback();
				}
				catch (Exception ex) {}
				System.err.println("void addNLESDPayAdviceEarningsTotals(NodeList nl, Integer paygroupid, String empnumber) " + e);
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
	public static NLESDPayAdviceEarningsTotalsBean getNLESDPayAdviceEarningsTotalsBean(int paygroupid, String empnumber,Integer empinfoid) throws NLESDPayAdviceException {
		NLESDPayAdviceEarningsTotalsBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.get_pay_advice_earnings_totals(?,?,?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, paygroupid);
				stat.setString(3, empnumber);
				stat.setInt(4, empinfoid);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()){
					eBean = createEarningsTotalsBean(rs);
				}
		}
		catch (SQLException e) {
				System.err.println("NLESDPayAdviceEarningsTotalsBean getNLESDPayAdviceEarningsTotalsBean(int paygroupid, String empnumber)  " + e);
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
		return eBean;
	}
	public static NLESDPayAdviceEarningsTotalsBean createEarningsTotalsBean(ResultSet rs) {
		NLESDPayAdviceEarningsTotalsBean abean = null;
		try {
				abean = new NLESDPayAdviceEarningsTotalsBean();
				abean.setId(rs.getInt("ID"));
				abean.setTtlCurHrs(Double.parseDouble(rs.getString("TTL_CUR_HRS")));
				abean.setTtlCurErn(Double.parseDouble(rs.getString("TTL_CUR_ERN")));
				abean.setTtlYtdHrs(Double.parseDouble(rs.getString("TTL_YTD_HRS")));
				abean.setTtlYtdErn(Double.parseDouble(rs.getString("TTL_YTD_ERN")));
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
