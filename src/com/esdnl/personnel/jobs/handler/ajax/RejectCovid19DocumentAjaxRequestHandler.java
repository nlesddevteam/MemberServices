package com.esdnl.personnel.jobs.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.mail.bean.EmailBean;
import com.esdnl.personnel.jobs.dao.ApplicantCovid19LogManager;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.util.StringUtils;
import com.esdnl.velocity.VelocityUtils;

public class RejectCovid19DocumentAjaxRequestHandler extends RequestHandlerImpl {

	public RejectCovid19DocumentAjaxRequestHandler() {
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
				int rid = form.getInt("id");
				String rnotes = form.get("rnotes");
				
				ApplicantCovid19LogManager.rejectCovid19Doc(rid,usr.getLotusUserFullName(),rnotes);
				//now we need to get the email of the applicant
				String emaila[] = ApplicantCovid19LogManager.getRejectEmail(rid);
				//covid19_reject_email.vm
				//send email to app
				EmailBean ebean = new EmailBean();
				ebean.setTo(emaila[0]);
				ebean.setFrom("ms@nlesd.ca");
				ebean.setSubject("COVID19 Vaccination Document Rejected");
				HashMap<String, Object> model = new HashMap<String, Object>();
				model.put("ename", emaila[1]);
				model.put("ereason", rnotes);
				ebean.setBody(VelocityUtils.mergeTemplateIntoString("personnel/covid19_reject_email.vm", model));
				ebean.send();
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<COVID19>");
				sb.append("<REJECT>");
				sb.append("<STATUS>SUCCESS</STATUS>");
				sb.append("<RBY>" + usr.getLotusUserFullName() + "</RBY>");
				Date d = new Date();
				DateFormat dt = new SimpleDateFormat("dd-MMMM-yyyy"); 
				sb.append("<RDATE>" + dt.format(d) + "</RDATE>");
				sb.append("<RNOTES>" + rnotes + "</RNOTES>");
				sb.append("</REJECT>");
				sb.append("</COVID19>");
				
				xml = StringUtils.encodeXML(sb.toString());

				PrintWriter out = response.getWriter();

				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}
			catch (Exception e) {
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");

				sb.append("<COVID19>");
				sb.append("<REJECT>");
				sb.append("<STATUS>" + e.getMessage() + "</STATUS>");
				sb.append("</REJECT>");
				sb.append("/<COVID19>");

				xml = StringUtils.encodeXML(sb.toString());

				System.out.println(xml);

				PrintWriter out = response.getWriter();

				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}
		

		return null;
	}

}