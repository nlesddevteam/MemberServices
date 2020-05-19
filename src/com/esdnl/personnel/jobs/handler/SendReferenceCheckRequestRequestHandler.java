package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.mail.bean.EmailBean;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.ReferenceCheckRequestBean;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.ReferenceCheckRequestManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class SendReferenceCheckRequestRequestHandler extends RequestHandlerImpl {
	public SendReferenceCheckRequestRequestHandler() {
		requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-VIEW", "PERSONNEL-PRINCIPAL-VIEW", "PERSONNEL-VICEPRINCIPAL-VIEW"
		};
		validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("uid"),
			new RequiredFormElement("email"),
			new RequiredFormElement("reftype")
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		path = "view_nlesd_admin_reference.jsp";
		if (validate_form()) {
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
				
				if(bean.getReferenceType().equals("ADMIN")){
					refTypeUrl="https://www.nlesd.ca/MemberServices/Personnel/addNLESDAdminReference.html?reftype=admin";
				}else if(bean.getReferenceType().equals("GUIDE")){
					refTypeUrl="https://www.nlesd.ca/MemberServices/Personnel/addNLESDGuideReference.html?reftype=guide";
				}else if(bean.getReferenceType().equals("TEACHER")){
					refTypeUrl="https://www.nlesd.ca/MemberServices/Personnel/addNLESDTeacherReference.html?reftype=teacher";
				}else if(bean.getReferenceType().equals("EXTERNAL")){
					refTypeUrl="https://www.nlesd.ca/MemberServices/Personnel/externalNLESDReferenceCheckRequestApp.html?reftype=external";
				}else if(bean.getReferenceType().equals("MANAGE")){
					refTypeUrl="https://www.nlesd.ca/MemberServices/Personnel/externalNLESDReferenceCheckRequestApp.html?reftype=manage";
				}else if(bean.getReferenceType().equals("SUPPORT")){
					refTypeUrl="https://www.nlesd.ca/MemberServices/Personnel/externalNLESDReferenceCheckRequestApp.html?reftype=support";
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
				path="index.jsp";
			}
		}else {
			request.setAttribute("FORM", form);
			request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));
			path="admin_index.jsp";
		}
		return path;
	}
}