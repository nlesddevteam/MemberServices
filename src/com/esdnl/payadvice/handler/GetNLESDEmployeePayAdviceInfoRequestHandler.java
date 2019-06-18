package com.esdnl.payadvice.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.payadvice.bean.NLESDPayAdviceEmployeeInfoBean;
import com.esdnl.payadvice.bean.NLESDPayAdvicePayGroupBean;
import com.esdnl.payadvice.dao.NLESDPayAdviceEmployeeInfoManager;
import com.esdnl.payadvice.dao.NLESDPayAdviceEmployeeSecurityManager;
import com.esdnl.payadvice.dao.NLESDPayAdvicePayGroupManager;
import com.esdnl.servlet.RequestHandlerImpl;
public class GetNLESDEmployeePayAdviceInfoRequestHandler  extends RequestHandlerImpl{
	public GetNLESDEmployeePayAdviceInfoRequestHandler() {
		this.requiredPermissions= new String[] {
				"PAY-ADVICE-ADMIN"
			};
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
		super.handleRequest(request, response);
		String empid = form.get("empid");
		String payid = form.get("payid");
			try {
					NLESDPayAdviceEmployeeInfoBean ebean = NLESDPayAdviceEmployeeInfoManager.getNLESDPayAdviceEmployeeInfoBean(Integer.parseInt(payid),empid);
					NLESDPayAdvicePayGroupBean pbean = NLESDPayAdvicePayGroupManager.getNLESDPayAdvicePayGroupBean(Integer.parseInt(payid));
					// generate XML for candidate details.
					String xml = null;
					StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
					sb.append("<INFORMATION>");
					sb.append("<EMPLOYEE>");
					if(!(ebean == null)){
							sb.append("<EMPNAME>" + ebean.getEmpName()+ "</EMPNAME>");
							sb.append("<EMAIL>" + NLESDPayAdviceEmployeeSecurityManager.getEmployeeEmail(empid)  + "</EMAIL>");
							sb.append("<PPERIOD>" + pbean.getPayBgDt() + " - " + pbean.getPayEndDt() + "</PPERIOD>");
							sb.append("<EID>" + ebean.getEmpNumber() + "</EID>");
							sb.append("<PID>" + pbean.getId().toString() + "</PID>");
							sb.append("<MESSAGE>NO ERROR</MESSAGE>");
					}else{
						sb.append("<MESSAGE>NO ERROR</MESSAGE>");
					}
					sb.append("</EMPLOYEE>");
					sb.append("</INFORMATION>");
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
