package com.esdnl.payadvice.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.esdnl.dao.DAOUtils;

public class NLESDPayAdviceYTDTotalsManager {
	public static void addNLESDPayAdviceYTDTotals(NodeList nl, Integer paygroupid, String empnumber,Integer empinfoid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(false);
				stat = con.prepareCall("begin awsd_user.payroll_advice_pkg.add_pay_advice_ytd_totals(?,?,?,?,?,?,?,?); end;");
				Element employeeinfo =(Element) nl.item(0);
				//need to check to see if ytd_hrs exist
				stat.setDouble(1,Double.parseDouble(employeeinfo.getElementsByTagName("ytd_ttl_grs").item(0).getTextContent().replaceAll(",", "")));
				stat.setDouble(2,Double.parseDouble(employeeinfo.getElementsByTagName("ytd_fed_txbl").item(0).getTextContent().replaceAll(",", "")));
				stat.setDouble(3,Double.parseDouble(employeeinfo.getElementsByTagName("ytd_ttl_tx").item(0).getTextContent().replaceAll(",", "")));
				stat.setDouble(4,Double.parseDouble(employeeinfo.getElementsByTagName("ytd_ttl_ded").item(0).getTextContent().replaceAll(",", "")));	
				stat.setDouble(5,Double.parseDouble(employeeinfo.getElementsByTagName("ytd_ttl_net").item(0).getTextContent().replaceAll(",", "")));
				stat.setInt(6,paygroupid);
				stat.setString(7,empnumber);
				stat.setInt(8,empinfoid);
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
				System.err.println("void addNLESDPayAdviceYTDTotals(NodeList nl, Integer paygroupid, String empnumber) " + e);
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
}
