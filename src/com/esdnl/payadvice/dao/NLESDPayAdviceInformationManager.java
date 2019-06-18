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
import com.esdnl.payadvice.bean.NLESDPayAdviceInformationBean;
public class NLESDPayAdviceInformationManager {
	public static void addNLESDPayAdviceInformation(NodeList nl, Integer paygroupid, String empnumber,Integer empinfoid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(false);
				stat = con.prepareCall("begin awsd_user.payroll_advice_pkg.add_pay_advice_information(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
				Element employeeinfo =(Element) nl.item(0);
				//need to check to see if ytd_hrs exist
				stat.setString(1,employeeinfo.getElementsByTagName("bnk_chkdt").item(0).getTextContent());
				stat.setString(2,employeeinfo.getElementsByTagName("bnk_chkno").item(0).getTextContent());
				stat.setString(3,employeeinfo.getElementsByTagName("bnk_payamt").item(0).getTextContent());
				stat.setString(4,employeeinfo.getElementsByTagName("chk_pye_name").item(0).getTextContent());
				if(employeeinfo.getElementsByTagName("chk_pye_adl1").item(0) == null){
					stat.setString(5," ");
				}else{
					stat.setString(5,employeeinfo.getElementsByTagName("chk_pye_adl1").item(0).getTextContent());
				}
				String fulladdressline2 ="";
				if(employeeinfo.getElementsByTagName("chk_pye_adl3").item(0) == null){
					fulladdressline2 = employeeinfo.getElementsByTagName("chk_pye_adl2").item(0).getTextContent();
					
				}else{
					fulladdressline2 = employeeinfo.getElementsByTagName("chk_pye_adl2").item(0).getTextContent() + " " +
					employeeinfo.getElementsByTagName("chk_pye_adl3").item(0).getTextContent();
				}
				stat.setString(6,fulladdressline2);	
				stat.setString(7,employeeinfo.getElementsByTagName("chk_pye_locn").item(0).getTextContent());
				stat.setString(12,employeeinfo.getElementsByTagName("dd_net_pay").item(0).getTextContent());
				NodeList nll = employeeinfo.getElementsByTagName("dd_distrib");
				Element dd = (Element)nll.item(0);
				stat.setString(8,dd.getElementsByTagName("dd_advno").item(0).getTextContent());
				stat.setString(9,dd.getElementsByTagName("dd_acct_typ").item(0).getTextContent());
				stat.setString(10,dd.getElementsByTagName("dd_acct_num").item(0).getTextContent());
				stat.setString(11,dd.getElementsByTagName("dd_acct_amt").item(0).getTextContent());	
				stat.setInt(13,paygroupid);
				stat.setString(14,empnumber);
				stat.setInt(15,empinfoid);
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
				System.err.println("void addNLESDPayAdviceInformation(NodeList nl, Integer paygroupid, String empnumber) " + e);
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
	public static NLESDPayAdviceInformationBean getNLESDPayAdviceInformationBean(int paygroupid, String empnumber,Integer empinfoid) throws NLESDPayAdviceException {
		NLESDPayAdviceInformationBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.get_pay_advice_information(?,?,?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, paygroupid);
				stat.setString(3, empnumber);
				stat.setInt(4, empinfoid);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()){
					eBean = createNLESDPayAdviceInformationBean(rs);
				}
		}
		catch (SQLException e) {
				System.err.println("NLESDPayAdviceInformationBean getNLESDPayAdviceInformationBean(int paygroupid, String empnumber) " + e);
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
	public static NLESDPayAdviceInformationBean createNLESDPayAdviceInformationBean(ResultSet rs) {
		NLESDPayAdviceInformationBean abean = null;
		try {
				abean = new NLESDPayAdviceInformationBean();
				abean.setId(rs.getInt("ID"));
				abean.setBnkChDt(rs.getString("BNK_CHDT"));
				abean.setBnkChkNo(rs.getString("BNK_CHKNO"));
				abean.setBnkPayAmt(rs.getString("BNK_PAYAMT"));
				abean.setChkPyeName(rs.getString("CHK_PYE_NAME"));
				abean.setChkPyeAdl1(rs.getString("CHK_PYE_ADL1"));
				abean.setChkPyeAdl2(rs.getString("CHK_PYE_ADL2"));
				abean.setChkPyeLocn(rs.getString("CHK_PYE_LOCN"));
				abean.setDdAdvNo(rs.getString("DD_ADVNO"));
				abean.setDdAcctTyp(rs.getString("DD_ACCT_TYP"));
				abean.setDdAcctNum(rs.getString("DD_ACCT_NUM"));
				abean.setDdAcctAmt(rs.getString("DD_ACCT_AMT"));
				abean.setDdNetPay(rs.getString("DD_NET_PAY"));
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
