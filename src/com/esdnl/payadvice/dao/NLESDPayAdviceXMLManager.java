package com.esdnl.payadvice.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map.Entry;
import java.util.TreeMap;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.awsd.servlet.ControllerServlet;
import com.esdnl.dao.DAOUtils;
import com.esdnl.payadvice.bean.NLESDPayAdviceCompanyInfoBean;
import com.esdnl.payadvice.bean.NLESDPayAdvicePayGroupBean;
import com.esdnl.payadvice.bean.NLESDPayrollDocumentBean;

public class NLESDPayAdviceXMLManager {
	private static final String COMMA_DELIMITER = ",";
	
	public static NLESDPayAdvicePayGroupBean getPayGroupBeanByName(String filename)
	{	
		NLESDPayAdvicePayGroupBean bean = new NLESDPayAdvicePayGroupBean();
		try {
				boolean foundT4C = false;
				boolean foundT4D = false;
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				factory.setNamespaceAware(false);
				factory.setValidating(false);
				factory.setFeature("http://xml.org/sax/features/namespaces", false);
				factory.setFeature("http://xml.org/sax/features/validation", false);
				factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
				factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
				Document doc = factory.newDocumentBuilder().parse(ControllerServlet.CONTEXT_BASE_PATH + NLESDPayrollDocumentBean.DOCUMENT_BASEPATH + filename);
				//retrieve the company, payroll start and end dates
				NodeList nodeslist =  doc.getElementsByTagName("PAYGROUP_INFO");
				for(int i=0; i<nodeslist.getLength();i++) {
					Element e = (Element) nodeslist.item(i);
					System.out.println(e.getElementsByTagName("pay_gp").item(0).getTextContent());
					if(!foundT4C || !foundT4D) {
						//need to keep searching not all values found
						if(e.getElementsByTagName("pay_gp").item(0).getTextContent().contains("T4C") && !foundT4C) {
							//we need to populate the regular teaching payroll dates
							bean.setPayGp(e.getElementsByTagName("pay_gp").item(0).getTextContent());
							bean.setPayBgDt(e.getElementsByTagName("pay_beg_dt").item(0).getTextContent());
							bean.setPayEndDt(e.getElementsByTagName("pay_end_dt").item(0).getTextContent());
							bean.setBusUnit(e.getElementsByTagName("bus_unit").item(0).getTextContent());
							bean.setCheckNum(e.getElementsByTagName("check_num").item(0).getTextContent());
							bean.setCheckDt(e.getElementsByTagName("check_dt").item(0).getTextContent());
							foundT4C=true;
						}else if (e.getElementsByTagName("pay_gp").item(0).getTextContent().contains("T4D") && !foundT4D) {
							//we need to populate the deferred teacher payroll values
							bean.setPayBgDtD(e.getElementsByTagName("pay_beg_dt").item(0).getTextContent());
							bean.setPayEndDtD(e.getElementsByTagName("pay_end_dt").item(0).getTextContent());
							bean.setCheckDtD(e.getElementsByTagName("check_dt").item(0).getTextContent());
							foundT4D=true;
						}else {
							//we found all values
							break;
						}
					}else {
						//we found all values
						break;
					}
					
					
				}
				
			} catch (SAXException e) {
				e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return bean; 
	}
	
	public static void loadMappingFile(String filename,Integer paygroupid)
	{	
		Connection con = null;
		CallableStatement stat = null;
		try {
				BufferedReader fileReader = null;
				fileReader = new BufferedReader(new FileReader(ControllerServlet.CONTEXT_BASE_PATH + NLESDPayrollDocumentBean.DOCUMENT_BASEPATH + filename));
				String line = "";
				con = DAOUtils.getConnection();
				con.setAutoCommit(false);
				stat = con.prepareCall("begin awsd_user.payroll_advice_pkg.add_pay_advice_mapping_rec(?,?,?); end;");
				while ((line = fileReader.readLine()) != null) {
					String[] tokens = line.split(COMMA_DELIMITER);
					if (tokens.length > 0) {
						stat.setString(1, tokens[0]);
						stat.setString(2, tokens[1]);
						stat.setInt(3, paygroupid);
						stat.addBatch();
					}
				}
				stat.executeBatch();
				con.commit();
			} catch (IOException e) {
				e.printStackTrace();
		} catch (SQLException e) {
				e.printStackTrace();
		}
	}
	public static void loadHistoryFile(String filename,Integer paygroupid)
	{	
		Connection con = null;
		CallableStatement stat = null;
		try {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				factory.setNamespaceAware(false);
				factory.setValidating(false);
				factory.setFeature("http://xml.org/sax/features/namespaces", false);
				factory.setFeature("http://xml.org/sax/features/validation", false);
				factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
				factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
				Document doc = factory.newDocumentBuilder().parse(ControllerServlet.CONTEXT_BASE_PATH + NLESDPayrollDocumentBean.DOCUMENT_BASEPATH + filename);
				//retrieve the company, payroll start and end dates
				NodeList nodeslist =  doc.getElementsByTagName("EMPLOYEE");
				con = DAOUtils.getConnection();
				con.setAutoCommit(false);
				stat = con.prepareCall("begin awsd_user.payroll_advice_pkg.add_pay_advice_work_history(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
				for(int i=0; i < nodeslist.getLength(); i++){
					Element e = (Element) nodeslist.item(i);
					stat.setString(1,e.getElementsByTagName("EMPLID").item(0).getTextContent());
					stat.setInt(2,Integer.parseInt(e.getElementsByTagName("EMPL_RCD").item(0).getTextContent()));
					stat.setString(3,e.getElementsByTagName("NAME").item(0).getTextContent());
					stat.setString(4,e.getElementsByTagName("DUR").item(0).getTextContent());
					stat.setString(5,e.getElementsByTagName("TRC").item(0).getTextContent());
					stat.setDouble(6,Double.parseDouble(e.getElementsByTagName("TL_QUANTITY").item(0).getTextContent()));
					stat.setDouble(7,Double.parseDouble(e.getElementsByTagName("OVERRIDE_RATE").item(0).getTextContent()));
					stat.setString(8,e.getElementsByTagName("LOCATION").item(0).getTextContent());
					stat.setString(9,e.getElementsByTagName("DESCR").item(0).getTextContent());
					stat.setString(10,e.getElementsByTagName("USER_FIELD_1").item(0).getTextContent());
					stat.setString(11,e.getElementsByTagName("NAME1").item(0).getTextContent());
					stat.setDouble(12,Double.parseDouble(e.getElementsByTagName("OTH_HRS").item(0).getTextContent()));
					stat.setDouble(13,Double.parseDouble(e.getElementsByTagName("OTH_EARNS").item(0).getTextContent()));
					stat.setDouble(14,Double.parseDouble(e.getElementsByTagName("COMPRATE").item(0).getTextContent()));
					stat.setDouble(15,Double.parseDouble(e.getElementsByTagName("COMPRATE_USED").item(0).getTextContent()));
					stat.setInt(16,paygroupid);
					stat.addBatch();
				}
				stat.executeBatch();
				con.commit();
			} catch (IOException e) {
				e.printStackTrace();
		} catch (SQLException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
		} catch (SAXException e) {
				e.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}
	}
	public static void loadPayrollFile(String filename,Integer paygroupid)
	{
		//now we process the payroll file
		//first parse the company info and save once
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(false);
		factory.setValidating(false);
		try {
				factory.setFeature("http://xml.org/sax/features/namespaces", false);
				factory.setFeature("http://xml.org/sax/features/validation", false);
				factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
				factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
				Document doc = factory.newDocumentBuilder().parse(ControllerServlet.CONTEXT_BASE_PATH + NLESDPayrollDocumentBean.DOCUMENT_BASEPATH + filename);
				//retrieve the company, payroll start and end dates
				NodeList nodeslist =  doc.getElementsByTagName("COMPANY_INFO");
				Element e = (Element) nodeslist.item(0);
				NLESDPayAdviceCompanyInfoBean bean = getCompanyInfo(e,paygroupid);
				NLESDPayAdviceCompanyInfoManager.addNLESDPayAdviceWorkHistoryBean(bean);
				//now we pass this to the manager to loop through and save the data
				nodeslist =  doc.getElementsByTagName("G_EMP_DATA");
				for(int i=0; i < nodeslist.getLength();i++){
					Element ee = (Element) nodeslist.item(i);
					//now we save the employee information
					TreeMap<Integer,String> returnvalues=NLESDPayAdviceEmployeeInfoManager.addNLESDPayAdviceEmployeeInfo(ee, paygroupid);
					Entry<Integer,String>em = returnvalues.firstEntry();
					Integer empinfoid = em.getKey();
					String empnumber = em.getValue();
					//now we save the earnings
					NodeList elist =  ee.getElementsByTagName("hrs_earns");
					NLESDPayAdviceEarningsManager.addNLESDPayAdviceEarnings(elist, paygroupid, empnumber,empinfoid);
					//now we save the earnings totals
					elist =  ee.getElementsByTagName("EARNINGS_TOTAL");
					NLESDPayAdviceEarningsTotalsManager.addNLESDPayAdviceEarningsTotals(elist, paygroupid, empnumber,empinfoid);
					//now we aave the taxes
					elist =  ee.getElementsByTagName("taxes_paid");
					NLESDPayAdviceTaxesManager.addNLESDPayAdviceTaxes(elist, paygroupid, empnumber,empinfoid);
					//now we aave the taxes totals
					elist =  ee.getElementsByTagName("TAXES_TOTAL");
					NLESDPayAdviceTaxesTotalsManager.addNLESDPayAdviceTaxesTotals(elist, paygroupid, empnumber,empinfoid);
					//now we save the bef taxes deductions
					elist =  ee.getElementsByTagName("bef_taxes_ded");
					NLESDPayAdviceBefTaxesDedManager.addNLESDPayAdviceBefTaxesDed(elist, paygroupid, empnumber,empinfoid);
					//now we aave the bef taxes ded  totals
					elist =  ee.getElementsByTagName("BEF_TAXES_TOTAL_DEDUCTIONS");
					NLESDPayAdviceBefTaxesDedTotalsManager.addNLESDPayAdviceBefTaxesDedTotals(elist, paygroupid, empnumber,empinfoid);
					//now we save the aft taxes deductions
					elist =  ee.getElementsByTagName("aft_taxes_ded");
					NLESDPayAdviceAftTaxesDedManager.addNLESDPayAdviceAftTaxesDed(elist, paygroupid, empnumber,empinfoid);
					//now we aave the aft taxes ded  totals
					elist =  ee.getElementsByTagName("AFT_TAXES_TOTAL_DEDUCTIONS");
					NLESDPayAdviceAftTaxesDedTotalsManager.addNLESDPayAdviceAftTaxesDedTotals(elist, paygroupid, empnumber,empinfoid);
					//now we save the emp paid benefits
					elist =  ee.getElementsByTagName("emp_paid_ben");
					NLESDPayAdviceEmpPaidBenefitsManager.addNLESDPayAdviceEmpPaidBenefits(elist, paygroupid, empnumber,empinfoid);
					//now we aave the current totals
					elist =  ee.getElementsByTagName("CURRENT_TOTALS");
					NLESDPayAdviceCurrentTotalsManager.addNLESDPayAdviceCurrentTotals(elist, paygroupid, empnumber,empinfoid);
					//now we aave the YTD totals
					elist =  ee.getElementsByTagName("YEARTODATE_TOTALS");
					NLESDPayAdviceYTDTotalsManager.addNLESDPayAdviceYTDTotals(elist, paygroupid, empnumber,empinfoid);
					//now we aave the pto sick hours
					elist =  ee.getElementsByTagName("PTO_SICK_HOURS");
					NLESDPayAdvicePtoSickHoursManager.addNLESDPayAdvicePtoSickHours(elist, paygroupid, empnumber,empinfoid);
					//now we aave the advice information
					elist =  ee.getElementsByTagName("ADVICE_INFO");
					NLESDPayAdviceInformationManager.addNLESDPayAdviceInformation(elist, paygroupid, empnumber,empinfoid);
					System.out.println("completed employee " + empnumber);
				}
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
}
	public static NLESDPayAdviceCompanyInfoBean getCompanyInfo(Element e, Integer paygroup)
	{
		NLESDPayAdviceCompanyInfoBean bean= new NLESDPayAdviceCompanyInfoBean();
		bean.setCompany(e.getElementsByTagName("company").item(0).getTextContent());
		bean.setCoAddrL1(e.getElementsByTagName("co_addr_l1").item(0).getTextContent());
		bean.setCoAddrL2(e.getElementsByTagName("co_addr_l2").item(0).getTextContent());
		bean.setPayGroup(paygroup);
		return bean;
	}
}
