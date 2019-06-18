package com.esdnl.personnel.jobs.handler.ajax;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.servlet.LoginNotRequiredRequestHandler;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.util.StringUtils;
public class CheckApplicantEmailAjaxRequestHandler implements LoginNotRequiredRequestHandler {

	public CheckApplicantEmailAjaxRequestHandler() {

	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {

		String msg="";
		
		try {
				String email = request.getParameter("email");
				if(ApplicantProfileManager.getApplicantProfileBeanByEmail(email) == null){
					msg="NOTFOUND";
				}else{
					msg="FOUND";
				}
					
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<APPLICANT>");
				sb.append("<ECHECK>");
				sb.append("<MESSAGE>" + msg + "</MESSAGE>");
				sb.append("</ECHECK>");
				sb.append("</APPLICANT>");
				xml = StringUtils.encodeXML(sb.toString());

				System.out.println(xml);

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
				sb.append("<APPLICANT>");
				sb.append("<ECHECK>");
				sb.append("<MESSAGE>" + StringUtils.encodeHTML2(e.getMessage()) + "</MESSAGE>");
				sb.append("</ECHECK>");
				sb.append("</APPLICANT>");
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
