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
import com.esdnl.payadvice.bean.NLESDPayAdviceAftTaxesDedTotalsBean;
import com.esdnl.payadvice.bean.NLESDPayAdviceException;

public class NLESDPayAdviceAftTaxesDedTotalsManager {
	public static void addNLESDPayAdviceAftTaxesDedTotals(NodeList nl, Integer paygroupid, String empnumber,Integer empinfoid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(false);
				stat = con.prepareCall("begin awsd_user.payroll_advice_pkg.add_pay_advice_aft_tx_ded_tot(?,?,?,?,?); end;");
				Element employeeinfo =(Element) nl.item(0);
				//need to check to see if ytd_hrs exist
				stat.setDouble(1,Double.parseDouble(employeeinfo.getElementsByTagName("atxd_ttl_cur").item(0).getTextContent().replaceAll(",", "")));
				stat.setDouble(2,Double.parseDouble(employeeinfo.getElementsByTagName("atxd_ttl_ytd").item(0).getTextContent().replaceAll(",", "")));	
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
		
				System.err.println("void addNLESDPayAdviceAefTaxesDedTotals(NodeList nl, Integer paygroupid, String empnumber) " + e);
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
	public static NLESDPayAdviceAftTaxesDedTotalsBean getNLESDPayAdviceBefTaxesAftTotalsBean(int paygroupid, String empnumber,Integer empinfoid) throws NLESDPayAdviceException {
		NLESDPayAdviceAftTaxesDedTotalsBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.get_pay_advice_aft_tx_d_totals(?,?,?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, paygroupid);
				stat.setString(3, empnumber);
				stat.setInt(4, empinfoid);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()){
					eBean = createNLESDPayAdviceAftTaxesDedTotalsBean(rs);
				}
		}
		catch (SQLException e) {
				System.err.println("NLESDPayAdviceAftTaxesDedTotalsBean getNLESDPayAdviceAftTaxesDedBean(int paygroupid, String empnumber) " + e);
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
	public static NLESDPayAdviceAftTaxesDedTotalsBean createNLESDPayAdviceAftTaxesDedTotalsBean(ResultSet rs) {
		NLESDPayAdviceAftTaxesDedTotalsBean abean = null;
		try {
				abean = new NLESDPayAdviceAftTaxesDedTotalsBean();
				abean.setId(rs.getInt("ID"));
				abean.setAtxdTtlCur(Double.parseDouble(rs.getString("ATXD_TTL_CUR")));
				abean.setAtxdTtlYtd(Double.parseDouble(rs.getString("ATXD_TTL_YTD")));
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