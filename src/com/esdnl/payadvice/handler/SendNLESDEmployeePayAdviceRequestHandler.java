package com.esdnl.payadvice.handler;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.mail.bean.EmailBean;
import com.awsd.servlet.ControllerServlet;
import com.esdnl.payadvice.bean.NLESDPayAdvicePayGroupBean;
import com.esdnl.payadvice.dao.NLESDPayAdviceCreateStubManager;
import com.esdnl.payadvice.dao.NLESDPayAdviceEmployeeSecurityManager;
import com.esdnl.payadvice.dao.NLESDPayAdvicePayGroupManager;
import com.esdnl.payadvice.worker.NLESDPayAdvicePayStubProcessWorker;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.velocity.VelocityUtils;

public class SendNLESDEmployeePayAdviceRequestHandler extends RequestHandlerImpl {

	public SendNLESDEmployeePayAdviceRequestHandler() {
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
		String email = form.get("email");
		Boolean filecreated = false;
		String foldername = ControllerServlet.CONTEXT_BASE_PATH + NLESDPayAdvicePayStubProcessWorker.DOCUMENT_BASEPATH
				+ "/resent";
		try {

				String returnstring = NLESDPayAdviceCreateStubManager.createStubForEmployee(empid, Integer.parseInt(payid),
						foldername, email, false);
				System.out.println(returnstring + " file created.");
				NLESDPayAdvicePayGroupBean pgbean = NLESDPayAdvicePayGroupManager.getNLESDPayAdvicePayGroupBean(Integer.parseInt(payid));
				//now we send the email
				EmailBean ebean = new EmailBean();
				ebean.setSubject("Pay Advice for Deposit on " + pgbean.getCheckDt());
				ebean.setTo(NLESDPayAdviceEmployeeSecurityManager.getEmployeeEmail(empid));
				ebean.setBody(VelocityUtils.mergeTemplateIntoString("payadvice/pay_advice_email.vm", null));
				File[] f = new File[2];
				//add paystub
				if (returnstring != null) {
					f[0] = new File(returnstring);
					filecreated = true;
				}
				if (filecreated) {
					ebean.setAttachments(f);
					System.out.println("email will be sent");
					ebean.send();
				}
				// generate XML for candidate details.
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<INFORMATION>");
				sb.append("<EMPLOYEE>");
				if (filecreated) {
					sb.append("<MESSAGE>MESSAGE SENT</MESSAGE>");
				}
				else {
					sb.append("<MESSAGE>MESSAGE NOT SENT/MESSAGE>");
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
