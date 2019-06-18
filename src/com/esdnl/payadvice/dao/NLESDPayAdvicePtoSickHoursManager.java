package com.esdnl.payadvice.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.esdnl.dao.DAOUtils;

public class NLESDPayAdvicePtoSickHoursManager {
	public static void addNLESDPayAdvicePtoSickHours(NodeList nl, Integer paygroupid, String empnumber,Integer empinfoid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(false);
				stat = con.prepareCall("begin awsd_user.payroll_advice_pkg.add_pay_advice_pto_sick_hours(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
				Element employeeinfo =(Element) nl.item(0);
				//need to check to see if ytd_hrs exist
				stat.setDouble(1,Double.parseDouble(employeeinfo.getElementsByTagName("start_bal").item(0).getTextContent().replaceAll(",", "")));
				stat.setDouble(2,Double.parseDouble(employeeinfo.getElementsByTagName("earned").item(0).getTextContent().replaceAll(",", "")));
				stat.setDouble(3,Double.parseDouble(employeeinfo.getElementsByTagName("bought").item(0).getTextContent().replaceAll(",", "")));
				stat.setDouble(4,Double.parseDouble(employeeinfo.getElementsByTagName("taken").item(0).getTextContent().replaceAll(",", "")));	
				stat.setDouble(5,Double.parseDouble(employeeinfo.getElementsByTagName("sold").item(0).getTextContent().replaceAll(",", "")));
				stat.setDouble(6,Double.parseDouble(employeeinfo.getElementsByTagName("adjustments").item(0).getTextContent().replaceAll(",", "")));
				stat.setDouble(7,Double.parseDouble(employeeinfo.getElementsByTagName("pto_bal").item(0).getTextContent().replaceAll(",", "")));
				stat.setDouble(8,Double.parseDouble(employeeinfo.getElementsByTagName("sick_begbal").item(0).getTextContent().replaceAll(",", "")));
				stat.setDouble(9,Double.parseDouble(employeeinfo.getElementsByTagName("sick_earned").item(0).getTextContent().replaceAll(",", "")));	
				stat.setDouble(10,Double.parseDouble(employeeinfo.getElementsByTagName("sick_bought").item(0).getTextContent().replaceAll(",", "")));
				stat.setDouble(11,Double.parseDouble(employeeinfo.getElementsByTagName("sick_taken").item(0).getTextContent().replaceAll(",", "")));
				stat.setDouble(12,Double.parseDouble(employeeinfo.getElementsByTagName("sick_sold").item(0).getTextContent().replaceAll(",", "")));
				stat.setDouble(13,Double.parseDouble(employeeinfo.getElementsByTagName("sick_adjust").item(0).getTextContent().replaceAll(",", "")));
				stat.setDouble(14,Double.parseDouble(employeeinfo.getElementsByTagName("sick_bal").item(0).getTextContent().replaceAll(",", "")));	
				stat.setInt(15,paygroupid);
				stat.setString(16,empnumber);
				stat.setInt(17,empinfoid);
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
				System.err.println("void addNLESDPayAdvicePtoSickHours(NodeList nl, Integer paygroupid, String empnumber)  " + e);
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
