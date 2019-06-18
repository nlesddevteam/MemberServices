package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.mail.bean.EmailBean;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.ReferenceCheckRequestBean;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.ReferenceCheckRequestManager;

public class SendReferenceCheckRequestRequestHandler implements RequestHandler {

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
			String email = request.getParameter("email");
			String reftype=request.getParameter("reftype");
			
			JobOpportunityBean job = (JobOpportunityBean) session.getAttribute("JOB");
			ApplicantProfileBean profile = ApplicantProfileManager.getApplicantProfileBean(uid);

			//add new request
			ReferenceCheckRequestBean bean = new ReferenceCheckRequestBean();

			bean.setCandidateId(uid);
			bean.setCompetitionNumber(job.getCompetitionNumber());
			bean.setReferredEmail(email);
			bean.setCheckRequester(usr.getPersonnel());
			bean.setReferenceType(reftype);

			bean = ReferenceCheckRequestManager.addReferenceCheckRequestBean(bean);

			//send email
			//determine what type of reference it is
			String refTypeUrl="";
			
			if(bean.getReferenceType().equals("ADMIN"))
			{
				refTypeUrl="http://www.nlesd.ca/MemberServices/Personnel/externalNLESDReferenceCheckRequest.html?reftype=admin";
			}else if(bean.getReferenceType().equals("GUIDE"))
			{
				refTypeUrl="http://www.nlesd.ca/MemberServices/Personnel/externalNLESDReferenceCheckRequest.html?reftype=guide";
			}else if(bean.getReferenceType().equals("TEACHER")){
				refTypeUrl="http://www.nlesd.ca/MemberServices/Personnel/externalNLESDReferenceCheckRequest.html?reftype=teacher";
			}else if(bean.getReferenceType().equals("EXTERNAL")){
				refTypeUrl="http://www.nlesd.ca/MemberServices/Personnel/externalNLESDReferenceCheckRequest.html?reftype=external";
			}else if(bean.getReferenceType().equals("MANAGE")){
				refTypeUrl="http://www.nlesd.ca/MemberServices/Personnel/externalNLESDReferenceCheckRequest.html?reftype=manage";
			}else if(bean.getReferenceType().equals("SUPPORT")){
				refTypeUrl="http://www.nlesd.ca/MemberServices/Personnel/externalNLESDReferenceCheckRequest.html?reftype=support";
			}

			EmailBean ebean = new EmailBean();
			ebean.setTo(new String[] {
				email
			});
			ebean.setFrom("ms@nlesd.ca");
			ebean.setSubject("Reference Check Requested By " + usr.getPersonnel().getFullNameReverse());
			ebean.setBody(usr.getPersonnel().getFullNameReverse() + " is requesting a reference check on "
					+ profile.getFullName() + " for competition " + job.getCompetitionNumber() + " : " + job.getPositionTitle()
					+ ".<BR><BR>" + " To submit a reference click the link below to view the Reference Check Form.<br><br>"
					+ "<a href='" + refTypeUrl + "&id=" +
					+ bean.getRequestId() + "'><B>CLICK HERE</B></a><br><br>" + "PLEASE DO NOT REPLY TO THIS MESSAGE.<br><br>"
					+ "Member Services");
			ebean.send();

			Collection<ReferenceCheckRequestBean> beans = ReferenceCheckRequestManager.getReferenceCheckRequestBeans(job,
					profile);

			//generate XML for candidate details.
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<SEND-REF-CHK-REQ-RESPONSE>");
			sb.append("<REFERENCE-CHECK-REQUESTS>");
			for (ReferenceCheckRequestBean rbean : beans)
				sb.append(rbean.toXML());
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