package com.esdnl.payadvice.dao;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.awsd.servlet.ControllerServlet;
import com.esdnl.dao.DAOUtils;
import com.esdnl.payadvice.bean.NLESDPayAdviceException;
import com.esdnl.payadvice.bean.NLESDPayrollDocumentBean;
import com.esdnl.payadvice.bean.NLESDPayrollDocumentPreviewBean;
import com.esdnl.payadvice.bean.NLESDSubWorkHistoryPreviewBean;
import com.esdnl.payadvice.constants.NLESDPayrollDocumentType;
public class NLESDPayrollDocumentManager {
	public static Integer addNLESDPayrollDocumentBean(NLESDPayrollDocumentBean bean) {
		Connection con = null;
		CallableStatement stat = null;
		Integer fileGroup=0;
		try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(true);
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.add_pay_advice_file(?,?,?,?,?,?); end;");
				stat.registerOutParameter(1, OracleTypes.INTEGER);
				stat.setString(2, bean.getFilename());
				stat.setString(3, bean.getUploadedBy());
				stat.setString(4, bean.getNotes());
				stat.setInt(5, bean.getType().getValue());
				stat.setString(6, bean.getOriginalFileName());
				stat.setInt(7, bean.getFileGroup());
				stat.execute();
				fileGroup = ((OracleCallableStatement) stat).getInt(1);
			}
		catch (SQLException e) {
				e.printStackTrace();
				try {
					con.rollback();
				}
				catch (Exception ex) {}
				System.err.println("void addNLESDPayrollDocumentBean(NLESDPayrollDocumentBean bean): " + e);
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
		return fileGroup;
	}
	public static Collection<NLESDPayrollDocumentBean>  getNLESDPayrollDocumentBeans() throws NLESDPayAdviceException {
		Collection<NLESDPayrollDocumentBean> documents = null;
		NLESDPayrollDocumentBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
				documents = new ArrayList<NLESDPayrollDocumentBean>();
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.get_unproc_pay_advice_files(); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()) {
					eBean = createNLESDPayrollDocumentBean(rs);
					documents.add(eBean);
				}
		}
		catch (SQLException e) {
				System.err.println("NLESDPayrollDocumentBean[] getNLESDPayrollDocumentBeans(): " + e);
				throw new NLESDPayAdviceException("Can not extract getNLESDPayrollDocumentBeans from DB.", e);
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

		return documents;
		
	}
	public static void deleteNLESDPayrollDocumentBean(String filename, Integer fileid, String username) {
		Connection con = null;
		CallableStatement stat = null;
		try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(true);
				Date now = new Date();
				stat = con.prepareCall("begin awsd_user.payroll_advice_pkg.delete_pay_advice_file(?,?,?); end;");
				stat.setString(1, filename);
				stat.setInt(2, fileid);
				stat.setString(3, "Document deleted by " + username + " on " + now);
				stat.execute();
		}
		catch (SQLException e) {
				e.printStackTrace();
				try {
					con.rollback();
				}
				catch (Exception ex) {}
				System.err.println("void deleteNLESDPayrollDocumentBean(String filename, Integer fileid, String username): " + e);
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
	public static ArrayList<NLESDPayrollDocumentBean>  getNLESDPayrollDocumentByID(Integer fileid) throws NLESDPayAdviceException {
		NLESDPayrollDocumentBean eBean = null;
		ArrayList<NLESDPayrollDocumentBean> list = new ArrayList<NLESDPayrollDocumentBean>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.get_pay_advice_fileby_id(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, fileid);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()) {
					eBean = createNLESDPayrollDocumentBean(rs);
					list.add(eBean);
				}
		}
		catch (SQLException e) {
				System.err.println("NLESDPayrollDocumentBean getNLESDPayrollDocumentByID(Integer fileid): " + e);
				throw new NLESDPayAdviceException("Can not extract getNLESDPayrollDocumentById from DB.", e);
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
	public static NLESDPayrollDocumentPreviewBean getNumberOfPayrollRecords(String filename,String element)
	{
		NLESDPayrollDocumentPreviewBean bean = new NLESDPayrollDocumentPreviewBean();
		try {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				factory.setNamespaceAware(false);
				factory.setValidating(false);
				factory.setFeature("http://xml.org/sax/features/namespaces", false);
				factory.setFeature("http://xml.org/sax/features/validation", false);
				factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
				factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
				Document doc = factory.newDocumentBuilder().parse(ControllerServlet.CONTEXT_BASE_PATH + NLESDPayrollDocumentBean.DOCUMENT_BASEPATH + filename);
				NodeList nodes = doc.getElementsByTagName(element);
				//get the record count
				bean.setPayrollRecordsCount(nodes.getLength());
				//retrieve the company, payroll start and end dates
				NodeList nodeslist = doc.getElementsByTagName("COMPANY_INFO");
				Element e = (Element) nodeslist.item(0);
				bean.setCompany(e.getElementsByTagName("company").item(0).getTextContent());
				nodeslist = doc.getElementsByTagName("PAYGROUP_INFO");
				e = (Element) nodeslist.item(0);
				bean.setPayrollGroup(e.getElementsByTagName("pay_gp").item(0).getTextContent());
				bean.setPayrollStartDate(e.getElementsByTagName("pay_beg_dt").item(0).getTextContent());
				bean.setPayrollEndDate(e.getElementsByTagName("pay_end_dt").item(0).getTextContent());
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return bean; 
	}
	public static Integer getNumberOfLinesMappingFile(String filename)
	{
		Integer linenumber = 0;
    	try{
    		File file =new File(ControllerServlet.CONTEXT_BASE_PATH + NLESDPayrollDocumentBean.DOCUMENT_BASEPATH + filename);
    		if(file.exists()){
    			FileReader fr = new FileReader(file);
    		    LineNumberReader lnr = new LineNumberReader(fr);
    		    	while (lnr.readLine() != null){
    		    		linenumber++;
    	            }
    		    lnr.close();
    	        }else{
    	        	System.out.println("File does not exists!");
    	        }
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    	return linenumber;
		
	}
	public static NLESDSubWorkHistoryPreviewBean getNumberOfWorkHistoryRecords(String filename)
	{
		NLESDSubWorkHistoryPreviewBean bean = new NLESDSubWorkHistoryPreviewBean();
		try {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				Document doc = factory.newDocumentBuilder().parse(ControllerServlet.CONTEXT_BASE_PATH + NLESDPayrollDocumentBean.DOCUMENT_BASEPATH + filename);
				NodeList nodes = doc.getElementsByTagName("EMPLOYEE");
				//get the record count
				bean.setWorkHistoryRecordsCount(nodes.getLength());
				//retrieve the company, payroll start and end dates
				NodeList nodeslist = doc.getElementsByTagName("COMPANYNAME");
				Element e = (Element) nodeslist.item(0);
				bean.setCompany(e.getTextContent());
				nodeslist = doc.getElementsByTagName("DEPTID");
				e = (Element) nodeslist.item(0);
				bean.setDeptId(e.getTextContent());
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return bean; 
	}
	public static NLESDPayrollDocumentBean createNLESDPayrollDocumentBean(ResultSet rs) {

		NLESDPayrollDocumentBean aBean = null;
		try {
				aBean = new NLESDPayrollDocumentBean();
				aBean.setDocumentId(rs.getInt("ID"));
				aBean.setFilename(rs.getString("FILE_NAME"));
				aBean.setUploadedBy(rs.getString("UPLOADED_BY"));
				aBean.setNotes(rs.getString("NOTES"));
				aBean.setType(NLESDPayrollDocumentType.get(rs.getInt("FILE_TYPE")));
				aBean.setIsProcessed(rs.getString("IS_PROCESSED"));
				aBean.setCreatedDate(new java.util.Date(rs.getDate("DATE_UPLOADED").getTime()));
				aBean.setOriginalFileName(rs.getString("ORIGINAL_NAME"));
			
		}
		catch (SQLException e) {
				aBean = null;
		}
		return aBean;
	}
}
