package com.nlesd.bcs.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.bean.BussingContractorEmployeeBean;
import com.nlesd.bcs.constants.EmployeeStatusConstant;
import com.nlesd.bcs.dao.BussingContractorEmployeeManager;
public class GetContractorEmployeesAjaxRequestHandler extends RequestHandlerImpl {
	public GetContractorEmployeesAjaxRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		try {
			int contractorid = form.getInt("cid");
			ArrayList<BussingContractorEmployeeBean> list = new ArrayList<BussingContractorEmployeeBean>();
			
			list = BussingContractorEmployeeManager.getContractorsEmployees(contractorid);
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<CONTRACTORS>");
			if(list.size() > 0){
				for(BussingContractorEmployeeBean ebean : list){
					sb.append("<EMPLOYEE>");
					sb.append("<CONTRACTOR>" + ebean.getBcBean().getLastName() + ebean.getBcBean().getLastName() + "</CONTRACTOR>");
					sb.append("<EMPLOYEEPOSITION>" + ebean.getEmployeePositionText() + "</EMPLOYEEPOSITION>");
					sb.append("<STARTDATE>" + ebean.getStartDate()+ "</STARTDATE>");
					sb.append("<CSERVICE>" + ebean.getContinuousService() + "</CSERVICE>");
					sb.append("<LASTNAME>" + ebean.getLastName()+ "</LASTNAME>");
					sb.append("<FIRSTNAME>" + ebean.getFirstName() + "</FIRSTNAME>");
					sb.append("<MIDDLENAME>" + ebean.getMiddleName() + "</MIDDLENAME>");
					sb.append("<EMAIL>" + ebean.getEmail() + "</EMAIL>");
					sb.append("<ADDRESS1>" + ebean.getAddress1() + "</ADDRESS1>");
					sb.append("<ADDRESS2>" + ebean.getAddress2() + "</ADDRESS2>");
					sb.append("<CITY>" + ebean.getCity() + "</CITY>");
					sb.append("<PROVINCE>" + ebean.getProvince() + "</PROVINCE>");
					sb.append("<POSTALCODE>" + ebean.getPostalCode() + "</POSTALCODE>");
					sb.append("<HOMEPHONE>" + ebean.getHomePhone() + "</HOMEPHONE>");
					sb.append("<CELLPHONE>" + ebean.getCellPhone() + "</CELLPHONE>");
					sb.append("<DLNUMBER>" + ebean.getDlNumber() + "</DLNUMBER>");
					sb.append("<DLCLASS>" + ebean.getDlClassText() + "</DLCLASS>");
					sb.append("<DARUNDATE>" + ebean.getDaRunDateFormatted() + "</DARUNDATE>");
					sb.append("<DACONVICTIONS>" + ebean.getDaConvictions() + "</DACONVICTIONS>");
					sb.append("<FAEXPIRYDATE>" + ebean.getFaExpiryDateFormatted() + "</FAEXPIRYDATE>");
					sb.append("<PCCDATE>" + ebean.getPccDateFormatted() + "</PCCDATE>");
					sb.append("<SCADATE>" + ebean.getScaDateFormatted() + "</SCADATE>");
					sb.append("<FINDINGSOFGUILT>" + ebean.getFindingsOfGuilt() + "</FINDINGSOFGUILT>");
					sb.append("<DLFRONT>" + ebean.getDlFront() + "</DLFRONT>");
					sb.append("<DLBACK>" + ebean.getDlBack() + "</DLBACK>");
					sb.append("<DADOCUMENT>" + ebean.getDaDocument() + "</DADOCUMENT>");
					sb.append("<FADOCUMENT>" + ebean.getFaDocument() + "</FADOCUMENT>");
					sb.append("<PRCVSQDOCUMENT>" + ebean.getPrcvsqDocument() + "</PRCVSQDOCUMENT>");
					sb.append("<PCCDOCUMENT>" + ebean.getPccDocument() + "</PCCDOCUMENT>");
					sb.append("<SCADOCUMENT>" + ebean.getScaDocument() + "</SCADOCUMENT>");
					sb.append("<STATUS>" + EmployeeStatusConstant.get(ebean.getStatus()).getDescription() + "</STATUS>");
					sb.append("<MESSAGE>LISTFOUND</MESSAGE>");
					sb.append("</EMPLOYEE>");
				}
			}else{
				sb.append("<EMPLOYEE>");
				sb.append("<MESSAGE>No employees found</MESSAGE>");
				sb.append("</EMPLOYEE>");
			}
			
			sb.append("</CONTRACTORS>");
			xml = sb.toString().replaceAll("&", "&amp;");
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
			path = null;

			
		}
		catch (Exception e) {
			// generate XML for candidate details.
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<CONTRACTORS>");
			sb.append("<EMPLOYEE>");
			sb.append("<MESSAGE>ERROR SEARCHING CONTRACTORS</MESSAGE>");
			sb.append("</EMPLOYEE>");
			sb.append("</CONTRACTORS>");
			xml = sb.toString().replaceAll("&", "&amp;");
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
			path = null;

		}
		return path;
	}

}
