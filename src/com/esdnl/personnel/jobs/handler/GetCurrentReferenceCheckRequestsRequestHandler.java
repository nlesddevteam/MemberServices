package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.ReferenceCheckRequestBean;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.ReferenceCheckRequestManager;

public class GetCurrentReferenceCheckRequestsRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		String path = null;
		HttpSession session = null;
		User usr = null;

		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("usr") != null)) {
			usr = (User) session.getAttribute("usr");
			if (!(usr.getUserPermissions().containsKey("PERSONNEL-ADMIN-VIEW") || usr.getUserPermissions().containsKey(
					"PERSONNEL-PRINCIPAL-VIEW"))) {
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
			}
		}
		else {
			throw new SecurityException("User login required.");
		}

		try {
			String uid = request.getParameter("uid");
			JobOpportunityBean job = (JobOpportunityBean) session.getAttribute("JOB");
			ApplicantProfileBean profile = ApplicantProfileManager.getApplicantProfileBean(uid);

			Collection<ReferenceCheckRequestBean> beans = ReferenceCheckRequestManager.getReferenceCheckRequestBeans(job,
					profile);

			//generate XML for candidate details.
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<SEND-REF-CHK-REQ-RESPONSE>");
			sb.append("<REFERENCE-CHECK-REQUESTS>");
			for (ReferenceCheckRequestBean req : beans)
				sb.append(req.toXML());
			sb.append("</REFERENCE-CHECK-REQUESTS>");
			sb.append("<RESPONSE-MSG>Request sent successfully.</RESPONSE-MSG>");
			sb.append("</SEND-REF-CHK-REQ-RESPONSE>");
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
			request.setAttribute("msg", e.getMessage());
			path = null;
		}

		return path;
	}
}