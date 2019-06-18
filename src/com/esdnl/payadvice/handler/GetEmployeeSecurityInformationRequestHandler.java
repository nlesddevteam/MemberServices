package com.esdnl.payadvice.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.payadvice.bean.NLESDPayAdviceEmployeeSecurityBean;
import com.esdnl.payadvice.dao.NLESDPayAdviceEmployeeSecurityManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class GetEmployeeSecurityInformationRequestHandler extends RequestHandlerImpl{
	public GetEmployeeSecurityInformationRequestHandler() {

	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
		super.handleRequest(request, response);
		String payrollid = form.get("payid");
		
		try {
				NLESDPayAdviceEmployeeSecurityBean secbean = NLESDPayAdviceEmployeeSecurityManager.getNLESDPayAdviceEmployeeSecurityBean(payrollid);
				// generate XML for candidate details.
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<EMPLOYEES>");
				if(secbean != null){
					if(secbean.getPassword().length() > 0)
					{
						sb.append("<EMPLOYEE>");
						sb.append("<PAYROLLID>" + payrollid+ "</PAYROLLID>");
						sb.append("<PASSWORD>" + secbean.getPassword() + "</PASSWORD>");
						sb.append("<MESSAGE>SUCCESS</MESSAGE>");
						sb.append("</EMPLOYEE>");
					}else{
						sb.append("<EMPLOYEE>");
						sb.append("<MESSAGE>NOCURRENTPASSWORD</MESSAGE>");
						sb.append("</EMPLOYEE>");
					}
				}else{
					sb.append("<EMPLOYEE>");
					sb.append("<MESSAGE>NOUSERFOUND</MESSAGE>");
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
