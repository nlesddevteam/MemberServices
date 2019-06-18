package com.esdnl.payadvice.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.esdnl.dao.DAOUtils;
import com.esdnl.payadvice.bean.NLESDPayAdviceEarningsBean;
import com.esdnl.payadvice.bean.NLESDPayAdviceException;

public class NLESDPayAdviceEarningsManager {
	public static void addNLESDPayAdviceEarnings(NodeList nl, Integer paygroupid, String empnumber,Integer empinfoid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(false);
				stat = con.prepareCall("begin awsd_user.payroll_advice_pkg.add_pay_advice_earnings(?,?,?,?,?,?,?,?); end;");
				//now loop through the hrs_earns nodes
				for(int i=0; i < nl.getLength(); i++){
					Element employeeinfo =(Element) nl.item(i);
					stat.setString(1,employeeinfo.getElementsByTagName("he_descr").item(0).getTextContent());
					//need to check to see if ytd_hrs exist
					stat.setDouble(2,Double.parseDouble(employeeinfo.getElementsByTagName("cur_ern").item(0).getTextContent().replaceAll(",", "")));
					if((employeeinfo.getElementsByTagName("ytd_hrs").item(0) != null)){
						stat.setDouble(3,Double.parseDouble(employeeinfo.getElementsByTagName("ytd_hrs").item(0).getTextContent().replaceAll(",", "")));	
					}
					else{
						stat.setDouble(3,0);
					}
					stat.setDouble(4,Double.parseDouble(employeeinfo.getElementsByTagName("ytd_ern").item(0).getTextContent().replaceAll(",", "")));
					stat.setString(5,empnumber);
					stat.setInt(6,paygroupid);
					stat.setInt(7,i);
					stat.setInt(8,empinfoid);
					stat.addBatch();
				}
				stat.executeBatch();
				con.setAutoCommit(true);
			}
		catch (Exception e) {
				e.printStackTrace();
				try {
					con.rollback();
				}
				catch (Exception ex) {}
		
				System.err.println("void addNLESDPayAdviceEmployeeInfo(NodeList nl, Integer paygroupid, String empnumber) " + e);
			
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
	public static TreeMap<Integer,NLESDPayAdviceEarningsBean> getNLESDPayAdviceEarningsBean(int paygroupid, String empnumber,Integer empinfoid) throws NLESDPayAdviceException {
		NLESDPayAdviceEarningsBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<Integer,NLESDPayAdviceEarningsBean> tmap = new TreeMap<Integer,NLESDPayAdviceEarningsBean>();
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.get_pay_advice_earnings(?,?,?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, paygroupid);
				stat.setString(3, empnumber);
				stat.setInt(4, empinfoid);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()){
					eBean = createPayAdviceEarningsBean(rs);
					tmap.put(eBean.getSortOrder(), eBean);
					
				}
		}
		catch (SQLException e) {
				System.err.println("TreeMap<Integer,NLESDPayAdviceEarningsBean> getNLESDPayAdviceEarningsBean(int paygroupid, String empnumber) " + e);
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
		return tmap;
	}
	public static NLESDPayAdviceEarningsBean createPayAdviceEarningsBean(ResultSet rs) {
		NLESDPayAdviceEarningsBean abean = null;
		try {
				abean = new NLESDPayAdviceEarningsBean();
				abean.setId(rs.getInt("ID"));
				abean.setHeDesc(rs.getString("HE_DESC"));
				abean.setCurErn(Double.parseDouble(rs.getString("CUR_ERN")));
				abean.setYtdHrs(Double.parseDouble(rs.getString("YTD_HRS")));
				abean.setYtdErn(Double.parseDouble(rs.getString("YTD_ERN")));
				abean.setEmpNumber(rs.getString("EMP_NUMBER"));
				abean.setPayGroupId(Integer.parseInt(rs.getString("PAY_GROUP_ID")));
				abean.setSortOrder(rs.getInt("SORT_ORDER"));
				abean.setEmpInfoId(rs.getInt("EMP_INFO_ID"));
		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}	
}
