package com.esdnl.payadvice.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.payadvice.bean.*;
import com.esdnl.payadvice.dao.NLESDPayAdviceSearchManager;
public class GetNLESDPayAdviceSearchEmployeesRequestHandler extends RequestHandlerImpl{
	public GetNLESDPayAdviceSearchEmployeesRequestHandler() {
		this.requiredPermissions= new String[] {
				"PAY-ADVICE-ADMIN"
			};
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
		super.handleRequest(request, response);
		String searchby = form.get("sby");
		String searchtext = form.get("stx");
		String searchschool = form.get("ssc");
		TreeMap<String,NLESDPayAdviceSearchBean> map = new TreeMap<String,NLESDPayAdviceSearchBean >();
			try {
					if(searchby.equals("NAME")){
						map = NLESDPayAdviceSearchManager.searchNLESDPayAdviceByName(searchtext);
					}
					else if(searchby.equals("SIN")){
						map = NLESDPayAdviceSearchManager.searchNLESDPayAdviceBySIN(searchtext);
					}else{
						map = NLESDPayAdviceSearchManager.searchNLESDPayAdviceBySchool(searchschool);
					}
					// generate XML for candidate details.
					String xml = null;
					StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
					sb.append("<EMPLOYEES>");
					if(! map.isEmpty()){
						for(Map.Entry<String,NLESDPayAdviceSearchBean> entry : map.entrySet()) {
							NLESDPayAdviceSearchBean ebean = (NLESDPayAdviceSearchBean) entry.getValue();
							sb.append("<EMPLOYEE>");
							sb.append("<EMPNAME>" + ebean.getEmpName()+ "</EMPNAME>");
							sb.append("<SCHOOL>" + ebean.getLocnCode() + "</SCHOOL>");
							sb.append("<SIN>" + ebean.getEmployeeId() + "</SIN>");
							sb.append("<ID>" + ebean.getPayrollId() + "</ID>");
							sb.append("<MESSAGE>LISTFOUND</MESSAGE>");
							sb.append("</EMPLOYEE>");
						}
					}else{
						sb.append("<EMPLOYEE>");
						sb.append("<MESSAGE>NOLIST</MESSAGE>");
						sb.append("</EMPLOYEE>");
					}
					sb.append("</EMPLOYEES>");
					xml = sb.toString().replaceAll("&", "&amp;");
					System.out.println(xml);
					PrintWriter out = response.getWriter();
					response.setContentType("text/xml");
					response.setHeader("Cache-Control", "no-cache");
					out.write(xml);
					out.flush();
					out.close();
					path = null;
			}
			catch (Exception e) {
					e.printStackTrace();
					path = null;
			}
		return path;
		}
	
}
