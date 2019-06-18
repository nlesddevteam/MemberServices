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
import com.esdnl.payadvice.bean.NLESDPayAdviceException;
import com.esdnl.payadvice.bean.NLESDPayAdviceTaxesTotalsBean;
public class NLESDPayAdviceTaxesTotalsManager {
	public static void addNLESDPayAdviceTaxesTotals(NodeList nl, Integer paygroupid, String empnumber,Integer empinfoid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(false);
				stat = con.prepareCall("begin awsd_user.payroll_advice_pkg.add_pay_advice_taxes_totals(?,?,?,?,?); end;");
				Element employeeinfo =(Element) nl.item(0);
				//need to check to see if ytd_hrs exist
				stat.setDouble(1,Double.parseDouble(employeeinfo.getElementsByTagName("tx_ttl_cur").item(0).getTextContent().replaceAll(",", "")));
				stat.setDouble(2,Double.parseDouble(employeeinfo.getElementsByTagName("tx_ttl_ytd").item(0).getTextContent().replaceAll(",", "")));	
				stat.setInt(3,paygroupid);
				stat.setString(4,empnumber);
				stat.setInt(5,empinfoid);
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
				System.err.println("void addNLESDPayAdviceTaxesTotals(NodeList nl, Integer paygroupid, String empnumber) " + e);
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
	public static NLESDPayAdviceTaxesTotalsBean getNLESDPayAdviceTaxesTotalsBean(int paygroupid, String empnumber,Integer empinfoid) throws NLESDPayAdviceException {
		NLESDPayAdviceTaxesTotalsBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.get_pay_advice_taxes_totals(?,?,?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, paygroupid);
				stat.setString(3, empnumber);
				stat.setInt(4, empinfoid);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()){
					eBean = createNLESDPayAdviceTaxesTotalsBean(rs);
				}
		}
		catch (SQLException e) {
				System.err.println("NLESDPayAdviceTaxesTotalsBean getNLESDPayAdviceTaxesTotalsBean(int paygroupid, String empnumber) " + e);
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
	public static NLESDPayAdviceTaxesTotalsBean createNLESDPayAdviceTaxesTotalsBean(ResultSet rs) {
		NLESDPayAdviceTaxesTotalsBean abean = null;
		try {
				abean = new NLESDPayAdviceTaxesTotalsBean();
				abean.setId(rs.getInt("ID"));
				abean.setTxTtlCur(Double.parseDouble(rs.getString("TX_TTL_CUR")));
				abean.setTxTtlYtd(Double.parseDouble(rs.getString("TX_TTL_YTD")));
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
