package com.esdnl.payadvice.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.esdnl.dao.DAOUtils;
import com.esdnl.payadvice.bean.NLESDPayAdviceEmployeeInfoBean;
import com.esdnl.payadvice.bean.NLESDPayAdviceException;
public class NLESDPayAdviceEmployeeInfoManager {
	public static TreeMap<Integer,String>addNLESDPayAdviceEmployeeInfo(Element ee, Integer paygroupid) {
		Connection con = null;
		CallableStatement stat = null;
		TreeMap<Integer,String> returnmap=new TreeMap<Integer,String>();
		String empnumber="";
		Integer id=0;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.add_pay_advice_employee_info(?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
				//get the employee id
				empnumber=ee.getElementsByTagName("emp_id").item(0).getTextContent();
				//now get the employee node and parse it
				NodeList enodelist = ee.getElementsByTagName("EMPLOYEE_INFO");
				Element employeeinfo =(Element) enodelist.item(0);
				stat.registerOutParameter(1, OracleTypes.INTEGER);
				stat.setString(2,employeeinfo.getElementsByTagName("emp_name").item(0).getTextContent());
				if(employeeinfo.getElementsByTagName("emp_addr_l1").item(0) == null){
					stat.setString(3," ");
				}else{
					stat.setString(3,employeeinfo.getElementsByTagName("emp_addr_l1").item(0).getTextContent());
				}
				String fulladdressline2 ="";
				if(employeeinfo.getElementsByTagName("emp_addr_l3").item(0) == null){
					fulladdressline2 = employeeinfo.getElementsByTagName("emp_addr_l2").item(0).getTextContent();
					
				}else{
					fulladdressline2 = employeeinfo.getElementsByTagName("emp_addr_l2").item(0).getTextContent() + " " +
					employeeinfo.getElementsByTagName("emp_addr_l3").item(0).getTextContent();
				}
				stat.setString(4,fulladdressline2);
				stat.setString(5,employeeinfo.getElementsByTagName("emp_dept").item(0).getTextContent());
				stat.setString(6,employeeinfo.getElementsByTagName("locn_code").item(0).getTextContent());
				stat.setString(7,employeeinfo.getElementsByTagName("job_title").item(0).getTextContent());
				stat.setString(8,employeeinfo.getElementsByTagName("pay_rt").item(0).getTextContent());
				if(employeeinfo.getElementsByTagName("net_clm_amt").item(0) == null)
				{
					stat.setDouble(9,0);
				}else{
					stat.setDouble(9,Double.parseDouble(employeeinfo.getElementsByTagName("net_clm_amt").item(0).getTextContent().replaceAll(",", "")));
				}
				
				stat.setString(10,employeeinfo.getElementsByTagName("emp_prov").item(0).getTextContent());
				if(employeeinfo.getElementsByTagName("prov_nclm_amt").item(0) == null)
				{stat.setDouble(11,0);
					
				}else{
					stat.setDouble(11,Double.parseDouble(employeeinfo.getElementsByTagName("prov_nclm_amt").item(0).getTextContent().replaceAll(",", "")));
				}
				
				stat.setString(12,empnumber);
				stat.setInt(13,paygroupid);
				if(employeeinfo.getElementsByTagName("fed_addl_amt").item(0) == null){
					stat.setString(14,"");
				}else{
					stat.setString(14,employeeinfo.getElementsByTagName("fed_addl_amt").item(0).getTextContent());
				}
				stat.execute();
				id=((CallableStatement) stat).getInt(1);
				returnmap.put(id,empnumber);
			}
		catch (Exception e) {
				e.printStackTrace();
				try {
					con.rollback();
				}
				catch (Exception ex) {}
				System.err.println("void addNLESDPayAdviceEmployeeInfo(NodeList nl, Integer paygroupid) " + e);
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
		
		return returnmap;
	}
	public static NLESDPayAdviceEmployeeInfoBean getNLESDPayAdviceEmployeeInfoBean(int paygroupid, String empnumber,Integer empinfoid) throws NLESDPayAdviceException {
		NLESDPayAdviceEmployeeInfoBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.get_pay_advice_emp_info(?,?,?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, paygroupid);
				stat.setString(3, empnumber);
				stat.setInt(4, empinfoid);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				if (rs.next())
					eBean = createNLESDPayAdviceEmployeeInfoBean(rs);
		}
		catch (SQLException e) {
				System.err.println("NLESDPayAdviceEmployeeInfoBean getNLESDPayAdviceEmployeeInfoBean(int paygroupid, String empnumber) " + e);
				throw new NLESDPayAdviceException("Can not extract NLESDPayAdviceEmployeeInfoBean from DB.", e);
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
	public static NLESDPayAdviceEmployeeInfoBean getNLESDPayAdviceEmployeeInfoBean(int paygroupid, String empnumber) throws NLESDPayAdviceException {
		//used to email stub, we do not need the empinfoid
		NLESDPayAdviceEmployeeInfoBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.get_pay_advice_emp_info_s(?,?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, paygroupid);
				stat.setString(3, empnumber);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				if (rs.next())
					eBean = createNLESDPayAdviceEmployeeInfoBean(rs);
		}
		catch (SQLException e) {
				System.err.println("NLESDPayAdviceEmployeeInfoBean getNLESDPayAdviceEmployeeInfoBean(int paygroupid, String empnumber)" + e);
				throw new NLESDPayAdviceException("NLESDPayAdviceEmployeeInfoBean getNLESDPayAdviceEmployeeInfoBean(int paygroupid, String empnumber)", e);
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
	public static ArrayList<String> getNLESDPayAdviceEmployeeInfoLocations() throws NLESDPayAdviceException {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<String>list = new ArrayList<String>();
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.get_pay_advice_locations; end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while(rs.next())
					list.add(rs.getString(1));
		}
		catch (SQLException e) {
				System.err.println("ArrayList<String> getNLESDPayAdviceEmployeeInfoLocations() " + e);
				throw new NLESDPayAdviceException("getNLESDPayAdviceEmployeeInfoLocations() from DB.", e);
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
	public static TreeMap<Integer,String> getNLESDPayAdviceEmployeePayStubs(String empnumber,Integer paygroupid) throws NLESDPayAdviceException {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<Integer,String>list = new TreeMap<Integer,String>();
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.get_employee_ps_by_period(?,?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setString(2, empnumber);
				stat.setInt(3, paygroupid);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while(rs.next())
					list.put(rs.getInt("id"), rs.getString("emp_number"));
		}
		catch (SQLException e) {
				System.err.println("TreeMap<Integer,String> getNLESDPayAdviceEmployeePayStubs(String empnumber,Integer paygroupid) " + e);
				throw new NLESDPayAdviceException("TreeMap<Integer,String> getNLESDPayAdviceEmployeePayStubs(String empnumber,Integer paygroupid) from DB.", e);
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
	public static NLESDPayAdviceEmployeeInfoBean createNLESDPayAdviceEmployeeInfoBean(ResultSet rs) {
		NLESDPayAdviceEmployeeInfoBean abean = null;
		try {
				abean = new NLESDPayAdviceEmployeeInfoBean();
				abean.setId(rs.getInt("ID"));
				abean.setEmpName(rs.getString("EMP_NAME"));
				abean.setEmpAddrL1(rs.getString("EMP_ADDR_L1"));
				abean.setEmpAddrL2(rs.getString("EMP_ADDR_L2"));
				abean.setEmpDept(rs.getString("EMP_DEPT"));
				abean.setLocnCode(rs.getString("LOCN_CODE"));
				abean.setJobTitle(rs.getString("JOB_TITLE"));
				abean.setPayRt(rs.getString("PAY_RT"));
				abean.setNetClmAmt(Double.parseDouble(rs.getString("NET_CLM_AMT")));
				abean.setEmpProv(rs.getString("EMP_PROV"));
				abean.setProvNclmAmt(Double.parseDouble(rs.getString("PROV_NCLM_AMT")));
				abean.setEmpNumber(rs.getString("EMP_NUMBER"));
				abean.setPayGroupId(Integer.parseInt(rs.getString("PAY_GROUP_ID")));
				abean.setFedAddlAmt(rs.getString("FED_ADDL_AMT"));

		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}
	
}
