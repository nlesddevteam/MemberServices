package com.esdnl.payadvice.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.esdnl.payadvice.dao.NLESDPayAdviceEmployeeSecurityManager;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.velocity.VelocityUtils;

public class UpdateEmployeePasswordRequestHandler extends RequestHandlerImpl{
	public UpdateEmployeePasswordRequestHandler() {

	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
		super.handleRequest(request, response);
		String payrollid = form.get("payid");
		String newpassword = form.get("password");
		
		try {
				NLESDPayAdviceEmployeeSecurityManager.updateNLESDPayAdviceEmployeeSecurity(payrollid, newpassword);
				//send email to confirm new password
				String email = usr.getPersonnel().getEmailAddress();
				if(email != null){
					HashMap<String, Object> model = new HashMap<String, Object>();
					// set values to be used in template
					model.put("password", newpassword);
					EmailBean emailbean = new EmailBean();
					emailbean.setSubject("Updated Password information for NLESD Pay Advice system");
					emailbean.setTo(email);
					emailbean.setBody(VelocityUtils.mergeTemplateIntoString("payadvice/pay_advice_email_p.vm",model));
					try {
						emailbean.send();
					} catch (EmailException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// generate XML for candidate details.
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<EMPLOYEES>");
				sb.append("<EMPLOYEE>");
				sb.append("<MESSAGE>SUCCESS</MESSAGE>");
				sb.append("</EMPLOYEE>");
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
