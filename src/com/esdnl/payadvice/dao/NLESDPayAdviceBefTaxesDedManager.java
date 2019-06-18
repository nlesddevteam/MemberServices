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
import com.esdnl.payadvice.bean.NLESDPayAdviceBefTaxesDedBean;
import com.esdnl.payadvice.bean.NLESDPayAdviceException;

public class NLESDPayAdviceBefTaxesDedManager {
	public static void addNLESDPayAdviceBefTaxesDed(NodeList nl, Integer paygroupid, String empnumber,Integer empinfoid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(false);
				stat = con.prepareCall("begin awsd_user.payroll_advice_pkg.add_pay_advice_bef_taxes_ded(?,?,?,?,?,?,?); end;");
				//now loop through the hrs_earns nodes
				for(int i=0; i < nl.getLength(); i++){
					Element employeeinfo =(Element) nl.item(i);
					stat.setString(1,employeeinfo.getElementsByTagName("btxd_descr").item(0).getTextContent());
					stat.setDouble(2,Double.parseDouble(employeeinfo.getElementsByTagName("btxd_cur").item(0).getTextContent().replaceAll(",", "")));
					stat.setDouble(3,Double.parseDouble(employeeinfo.getElementsByTagName("btxd_ytd").item(0).getTextContent().replaceAll(",", "")));
					stat.setInt(4,paygroupid);
					stat.setString(5,empnumber);
					stat.setInt(6,i);
					stat.setInt(7,empinfoid);
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
					System.err.println("void addNLESDPayAdviceBefTaxesDed(NodeList nl, Integer paygroupid, String empnumber) " + e);
			
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
	public static TreeMap<Integer,NLESDPayAdviceBefTaxesDedBean> getNLESDPayAdviceBefTaxesDedBean(int paygroupid, String empnumber,Integer empinfoid) throws NLESDPayAdviceException {
		NLESDPayAdviceBefTaxesDedBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<Integer,NLESDPayAdviceBefTaxesDedBean> tmap = new TreeMap<Integer,NLESDPayAdviceBefTaxesDedBean>();
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.get_pay_advice_b_tax_ded(?,?,?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, paygroupid);
				stat.setString(3, empnumber);
				stat.setInt(4, empinfoid);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()){
					eBean = createPayAdviceBefTaxesDedBean(rs);
					tmap.put(eBean.getSortOrder(), eBean);
				}
		}
		catch (SQLException e) {
			System.err.println("TreeMap<Integer,NLESDPayAdviceBefTaxesDedBean> getNLESDPayAdviceBefTaxesDedBean(int paygroupid, String empnumber) " + e);
			throw new NLESDPayAdviceException("Can not extract NLESDPayAdviceBefTaxesDedBean from DB.", e);
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
	public static NLESDPayAdviceBefTaxesDedBean createPayAdviceBefTaxesDedBean(ResultSet rs) {
		NLESDPayAdviceBefTaxesDedBean abean = null;
		try {
				abean = new NLESDPayAdviceBefTaxesDedBean();
				abean.setId(rs.getInt("ID"));
				abean.setBtxdDescr(rs.getString("BTXD_DESCR"));
				abean.setBtxdCur(Double.parseDouble(rs.getString("BTXD_CUR")));
				abean.setBtxdYtd(Double.parseDouble(rs.getString("BTXD_YTD")));
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
